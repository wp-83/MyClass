package self.learning.backend.dto.schedule;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class ScheduleRequestDTO {
    @NotBlank
    private String room;

    @NotNull
    @Min(1)
    @Max(150)
    private Integer capacity;

    @NotNull
    @Min(1)
    @Max(6)
    private Integer dayOfWeek;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    @AssertTrue(message = "The end time must be after start time.")
    public Boolean isValidRangeTime(){
        if (this.startTime == null || this.endTime == null) {
            return true;
        }

        return this.startTime.isBefore(this.endTime);
    }
}
