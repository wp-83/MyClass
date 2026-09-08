package self.learning.backend.dto.course;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseRequestDTO {
    @NotBlank
    @Size(min = 10, max = 10)
    private String code;

    @NotBlank
    private String name;

    @NotNull
    @Min(1)
    @Max(8)
    private Integer credit;

    @NotNull
    @Min(1)
    @Max(8)
    private Integer minSemester;
}
