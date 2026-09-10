package self.learning.backend.dto.enrollment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import self.learning.backend.model.LecturerCourse;

import java.util.List;

@Getter
@Setter
public class EnrollmentRequestDTO {
    @NotNull
    @Positive
    private Long studentId;

    @NotNull
    @Size(min = 1)
    private List<@Positive LecturerCourse> lecturerCourses;
}
