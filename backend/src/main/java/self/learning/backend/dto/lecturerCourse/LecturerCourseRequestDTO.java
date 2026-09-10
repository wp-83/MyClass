package self.learning.backend.dto.lecturerCourse;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LecturerCourseRequestDTO {
    @NotNull
    private Long lecturerId;

    @NotNull
    @Size(min = 1, message = "Minimum selected one course.")
    private List<Long> courseId;
}
