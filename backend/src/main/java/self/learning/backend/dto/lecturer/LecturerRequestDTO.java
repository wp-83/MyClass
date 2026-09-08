package self.learning.backend.dto.lecturer;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LecturerRequestDTO {
    @NotBlank
    private String name;

    @NotBlank
    private String department;
}
