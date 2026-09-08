package self.learning.backend.dto.lecturer;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LecturerResponseDTO {
    private Long id;
    private String name;
    private String department;
}
