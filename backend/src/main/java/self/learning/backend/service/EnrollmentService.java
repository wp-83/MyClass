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
import self.learning.backend.repository.CourseRepository;
import self.learning.backend.repository.EnrollmentRepository;
import self.learning.backend.repository.StudentDetailRepository;
import self.learning.backend.repository.StudentRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final StudentDetailRepository studentDetailRepository;
    private final CourseRepository courseRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, StudentRepository studentRepository, StudentDetailRepository studentDetailRepository, CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.studentDetailRepository = studentDetailRepository;
        this.courseRepository = courseRepository;
    }

    public List<StudentEnrollmentResponseDTO> allEnrollmentByStudent(){
        List<Enrollment> enrollments = enrollmentRepository.findAll();
        Map<Long, List<Enrollment>> enrollmentByStudents = enrollments.stream().collect(
                Collectors.groupingBy(enrollment -> enrollment.getStudent().getId())
        );

        return enrollmentByStudents.entrySet().stream().map(
                entry -> {
                    Long studentId = entry.getKey();
                    Student student = studentRepository.findById(studentId);
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
                                    new CourseResponseDTO(
                                            enrollment.getCourse().getId(),
                                            enrollment.getCourse().getCode(),
                                            enrollment.getCourse().getName(),
                                            enrollment.getCourse().getCredit(),
                                            enrollment.getCourse().getMinSemester()
                                    )
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
                new CourseResponseDTO(
                        enrollment.getCourse().getId(),
                        enrollment.getCourse().getCode(),
                        enrollment.getCourse().getName(),
                        enrollment.getCourse().getCredit(),
                        enrollment.getCourse().getMinSemester()
                )
        )).toList();

        return new StudentEnrollmentResponseDTO(
                studentResponseDTO,
                enrollmentResponseDTOS
        );
    }

    public void createEnrollment(EnrollmentRequestDTO request){
        Student student = studentRepository.findById(request.getStudentId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student data is not valid."));
        List<Course> courses = courseRepository.findAllById(request.getCourseId()).stream().filter(course ->
                course.getMinSemester() <= student.getStudentDetail().getSemester()
        ).toList();

        int totalCourseCredits;
        totalCourseCredits = courses.stream().mapToInt(Course::getCredit).sum();

        if (courses.size() < request.getCourseId().size() || totalCourseCredits > student.getStudentDetail().getMaxCredit()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some courses cannot be enrolled by you.");
        }

        for (Course course : courses){
            Enrollment enrollment = new Enrollment();

            enrollment.setStudent(student);
            enrollment.setCourse(course);

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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No enrollment by the student.");
        }

        for (Enrollment enrollment : enrollments){
            enrollment.setStatus(EnrollmentStatus.COMPLETED);
            this.enrollmentRepository.save(enrollment);
        }

        Student student = studentRepository.findById(studentId);
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
