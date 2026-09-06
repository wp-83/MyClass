package self.learning.backend.dto.student;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStudentDTO {
    @NotBlank
    @Size(min = 1, max = 25)
    private String username;

    @NotBlank
    @Email
    private String email;

    @Valid
    private StudentDetailDTO studentDetail;
}
