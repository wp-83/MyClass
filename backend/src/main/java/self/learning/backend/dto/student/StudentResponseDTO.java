package self.learning.backend.dto.student;

import lombok.AllArgsConstructor;
import lombok.Getter;
import self.learning.backend.model.StudentDetail;

@AllArgsConstructor
@Getter
public class StudentResponseDTO {
    private Long id;
    private String username;
    private String email;
    private StudentDetail studentDetail;
}
