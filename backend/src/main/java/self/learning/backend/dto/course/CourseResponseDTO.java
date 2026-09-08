package self.learning.backend.dto.course;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CourseResponseDTO {
    private Long id;
    private String code;
    private String name;
    private Integer credit;
    private Integer minSemester;
}
