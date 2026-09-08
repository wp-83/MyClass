package self.learning.backend.dto.student;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
public class StudentDetailDTO {
    @NotNull
    @Min(1)
    @Max(10)
    private Integer semester;

    @NotNull
    @Min(2)
    @Max(144)
    private Integer maxCredit;

    @NotBlank
    @Size(min = 10, max = 10, message = "The NIM must be 10 characters.")
    private String nim;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String program;
}
