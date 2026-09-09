package self.learning.backend.dto.enrollment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import self.learning.backend.dto.student.StudentResponseDTO;

import java.util.List;

@AllArgsConstructor
@Getter
public class StudentEnrollmentResponseDTO {
    private StudentResponseDTO studentResponseDTO;
    private List<EnrollmentResponseDTO> enrollmentResponseDTOS;
}
