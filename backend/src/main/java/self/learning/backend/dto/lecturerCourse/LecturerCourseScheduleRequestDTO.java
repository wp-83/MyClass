package self.learning.backend.dto.lecturerCourse;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import self.learning.backend.model.Course;
import self.learning.backend.model.Lecturer;
import self.learning.backend.model.Schedule;

@Getter
@Setter
public class LecturerCourseScheduleRequestDTO {
    @NotNull
    private long id;

    @NotNull
    private Lecturer lecturer;

    @NotNull
    private Course course;

    @NotNull
    private Schedule schedule;

    @NotNull
    private Boolean isActive;


}
