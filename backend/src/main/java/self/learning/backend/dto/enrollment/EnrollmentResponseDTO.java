package self.learning.backend.dto.enrollment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import self.learning.backend.dto.course.CourseResponseDTO;
import self.learning.backend.dto.student.StudentResponseDTO;
import self.learning.backend.model.EnrollmentStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class EnrollmentResponseDTO {
    private Long id;
    private EnrollmentStatus status;
    private LocalDateTime enrollAt;
    private LocalDateTime lastUpdate;
    private CourseResponseDTO course;
}
