package self.learning.backend.service;

import org.springframework.stereotype.Service;
import self.learning.backend.dto.course.CourseResponseDTO;
import self.learning.backend.dto.enrollment.EnrollmentRequestDTO;
import self.learning.backend.dto.enrollment.EnrollmentResponseDTO;
import self.learning.backend.model.Enrollment;
import self.learning.backend.repository.EnrollmentRepository;

import java.util.List;

@Service
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public List<EnrollmentResponseDTO> getEnrollmentBySpecificStudent(Long studentId){
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);

        return enrollments.stream().map(enrollment -> new EnrollmentResponseDTO(
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
    }

//    public void createEnrollment(EnrollmentRequestDTO request){
//
//    }
}
