package self.learning.backend.dto.schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@AllArgsConstructor
@Getter
public class ScheduleResponseDTO {
    private Long id;
    private String room;
    private Integer capacity;
    private Integer dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
}
