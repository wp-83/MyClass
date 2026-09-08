package self.learning.backend.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import self.learning.backend.dto.message.ResponseMessageDTO;
import self.learning.backend.dto.schedule.ScheduleRequestDTO;
import self.learning.backend.dto.schedule.ScheduleResponseDTO;
import self.learning.backend.service.ScheduleService;

import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping
    public List<ScheduleResponseDTO> allSchedules(){
        return scheduleService.allSchedules();
    }

    @GetMapping("/{id}")
    public ScheduleResponseDTO getScheduleById(@PathVariable Long id){
        return scheduleService.getScheduleById(id);
    }

    @PostMapping
    public ResponseEntity<ResponseMessageDTO> createSchedule(@Valid @RequestBody ScheduleRequestDTO request){
        scheduleService.createSchedule(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessageDTO("Schedule is successfully created."));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessageDTO> updateSchedule(@PathVariable Long id, @Valid @RequestBody ScheduleRequestDTO request){
        scheduleService.updateSchedule(id, request);

        return ResponseEntity.ok(new ResponseMessageDTO("Schedule is successfully updated."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessageDTO> deleteSchedule(@PathVariable Long id){
        scheduleService.deleteSchedule(id);

        return ResponseEntity.ok(new ResponseMessageDTO("Schedule is successfully deleted."));
    }
}
