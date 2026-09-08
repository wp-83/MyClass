package self.learning.backend.dto.enrollment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public class EnrollmentRequestDTO {
    @NotNull
    @Positive
    private Long studentId;

    @NotNull
    @Positive
    private List<Long> courseId;
}
