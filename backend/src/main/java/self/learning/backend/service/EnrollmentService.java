package self.learning.backend.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import self.learning.backend.dto.course.CourseResponseDTO;
import self.learning.backend.dto.enrollment.StudentEnrollmentResponseDTO;
import self.learning.backend.dto.enrollment.EnrollmentRequestDTO;
import self.learning.backend.dto.enrollment.EnrollmentResponseDTO;
import self.learning.backend.dto.student.StudentResponseDTO;
import self.learning.backend.model.*;
import self.learning.backend.repository.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final StudentDetailRepository studentDetailRepository;
    private final CourseRepository courseRepository;
    private final LecturerCourseRepository lecturerCourseRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, StudentRepository studentRepository, StudentDetailRepository studentDetailRepository, CourseRepository courseRepository, LecturerCourseRepository lecturerCourseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.studentDetailRepository = studentDetailRepository;
        this.courseRepository = courseRepository;
        this.lecturerCourseRepository = lecturerCourseRepository;
    }

    public List<StudentEnrollmentResponseDTO> allEnrollmentByStudent(){
        List<Enrollment> enrollments = enrollmentRepository.findAll();
        Map<Long, List<Enrollment>> enrollmentByStudents = enrollments.stream().collect(
                Collectors.groupingBy(enrollment -> enrollment.getStudent().getId())
        );

        return enrollmentByStudents.entrySet().stream().map(
                entry -> {
                    Long studentId = entry.getKey();
                    Student student = studentRepository.findById(studentId).orElseThrow();
                    StudentDetail studentDetail = studentDetailRepository.findByStudentId(studentId);

                    StudentResponseDTO studentResponse = new StudentResponseDTO(
                            student.getId(),
                            student.getUsername(),
                            student.getEmail(),
                            studentDetail
                    );

                    List<EnrollmentResponseDTO> enrollmentList = entry.getValue().stream().map(
                            enrollment -> new EnrollmentResponseDTO(
                                    enrollment.getId(),
                                    enrollment.getStatus(),
                                    enrollment.getEnrollAt(),
                                    enrollment.getLastUpdate(),
                                    enrollment.getLecturerCourse()
                            )
                    ).toList();

                    return new StudentEnrollmentResponseDTO(
                            studentResponse,
                            enrollmentList
                    );
                }).toList();
    }

    public StudentEnrollmentResponseDTO getEnrollmentBySpecificStudent(Long studentId){
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "The student data is invalid."));
        StudentDetail studentDetail = studentDetailRepository.findByStudentId(studentId);

        StudentResponseDTO studentResponseDTO = new StudentResponseDTO(
                student.getId(),
                student.getUsername(),
                student.getEmail(),
                studentDetail
        );

        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);
        List<EnrollmentResponseDTO> enrollmentResponseDTOS = enrollments.stream().map(enrollment -> new EnrollmentResponseDTO(
                enrollment.getId(),
                enrollment.getStatus(),
                enrollment.getEnrollAt(),
                enrollment.getLastUpdate(),
                enrollment.getLecturerCourse()
        )).toList();

        return new StudentEnrollmentResponseDTO(
                studentResponseDTO,
                enrollmentResponseDTOS
        );
    }

    public void createEnrollment(EnrollmentRequestDTO request){
        Student student = studentRepository.findById(request.getStudentId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student data is not valid."));
        List<Course> courses = courseRepository.findAllById(request.getLecturerCourses().stream()
                        .map(lecturerCourse -> lecturerCourse.getCourse().getId())
                        .toList()
                ).stream().filter(course ->
                course.getMinSemester() <= student.getStudentDetail().getSemester()
        ).toList();

        int totalCourseCredits;
        totalCourseCredits = courses.stream().mapToInt(Course::getCredit).sum();

        // validate the valid course and the maximum credits
        if (courses.size() < request.getLecturerCourses().size() || totalCourseCredits > student.getStudentDetail().getMaxCredit()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some courses cannot be enrolled by you.");
        }

        List<LecturerCourse> lecturerCourses = lecturerCourseRepository.findAllById(request.getLecturerCourses().stream().map(LecturerCourse::getId).toList());
        lecturerCourses = lecturerCourses.stream().sorted(
                Comparator.comparing((LecturerCourse lecturerCourse) -> lecturerCourse.getSchedule().getDayOfWeek())
                        .thenComparing(lecturerCourse -> lecturerCourse.getSchedule().getStartTime())
                        .thenComparing(lecturerCourse -> lecturerCourse.getSchedule().getEndTime())
        ).toList();

        // validate the slot of the course schedule
        for (LecturerCourse lc : lecturerCourses){
            Boolean isReachLimit = ((lc.getSchedule().getTotalEnroll() + 1) > (lc.getSchedule().getCapacity()));

            if (isReachLimit.equals(Boolean.TRUE)){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("The capacity of %s course is reached the limit.", lc.getCourse().getName()));
            }
        }

        // validate the time for all courses that enrolled
        int iterations = lecturerCourses.size() - 1;
        for (int i = 0; i <= iterations; i++){
            Schedule firstSchedule = lecturerCourses.get(i).getSchedule();
            Schedule secondSchedule = lecturerCourses.get(i + 1).getSchedule();

            String firtCourseName = lecturerCourses.get(i).getCourse().getName();
            String secondCourseName = lecturerCourses.get(i + 1).getCourse().getName();

            if (firstSchedule.getDayOfWeek().equals(secondSchedule.getDayOfWeek())){
                if (firstSchedule.getStartTime().equals(secondSchedule.getStartTime()) || firstSchedule.getEndTime().isAfter(secondSchedule.getStartTime())){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Schedule of %s and %s is overlap. You cannot take them together.", firtCourseName, secondCourseName));
                }
            }
        }

        for (LecturerCourse lc : lecturerCourses){
            Enrollment enrollment = new Enrollment();

            enrollment.setStudent(student);
            enrollment.setLecturerCourse(lc);

            enrollmentRepository.save(enrollment);
        }
    }

    /*
        The update concept:
            There will be a button in the frontend. If someone click it, all the enrollment status will be updated into DONE.
            Student can't do the modification of selected courses or adding new course. Student must re-enroll if there is any changes.
            The changes must start with removing all the enrolled courses by the admin.
     */
    public void changeEnrollmentStatus(Long studentId){
        List<Enrollment> enrollments = enrollmentRepository.findAllByStudentId(studentId);

        if (enrollments.size() <= 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No enrollment is done by the student.");
        }

        for (Enrollment enrollment : enrollments){
            enrollment.setStatus(EnrollmentStatus.COMPLETED);
            this.enrollmentRepository.save(enrollment);
        }

        Student student = studentRepository.findById(studentId).orElseThrow();
        StudentDetail studentDetail = student.getStudentDetail();
        studentDetail.setSemester(studentDetail.getSemester() + 1);
        studentDetailRepository.save(studentDetail);
    }

    /*
        The delete concept:
            All enrollments that is on-going will be deleted.
     */
    public void deleteEnrollment(Long studentId){
        List<Enrollment> enrollments = enrollmentRepository.findAllByStudentId(studentId);

        if (enrollments.size() <= 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No enrollment by the student.");
        }

        enrollmentRepository.deleteAllById(enrollments.stream().map(Enrollment::getId).toList());
    }
}
