package self.learning.backend.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import self.learning.backend.dto.schedule.ScheduleRequestDTO;
import self.learning.backend.dto.schedule.ScheduleResponseDTO;
import self.learning.backend.model.Schedule;
import self.learning.backend.repository.ScheduleRepository;

import java.util.Comparator;
import java.util.List;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public List<ScheduleResponseDTO> allSchedules(){
        return this.scheduleRepository.findAll().stream()
                .sorted(Comparator.comparing(Schedule::getDayOfWeek)
                        .thenComparing(Schedule::getRoom)
                        .thenComparing(Schedule::getStartTime))
                .map(schedule -> new ScheduleResponseDTO(schedule.getId(), schedule.getRoom(), schedule.getCapacity(), schedule.getDayOfWeek(), schedule.getStartTime(), schedule.getEndTime()))
                .toList();
    }

    public ScheduleResponseDTO getScheduleById(Long id){
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The schedule is not found."));
        return new ScheduleResponseDTO(schedule.getId(), schedule.getRoom(), schedule.getCapacity(), schedule.getDayOfWeek(), schedule.getStartTime(), schedule.getEndTime());
    }

    public void createSchedule(ScheduleRequestDTO request){
        Schedule schedule = new Schedule();

        schedule.setRoom(request.getRoom());
        schedule.setCapacity(request.getCapacity());
        schedule.setDayOfWeek(request.getDayOfWeek());
        schedule.setStartTime(request.getStartTime());
        schedule.setEndTime(request.getEndTime());

        scheduleRepository.save(schedule);
    }

    public void updateSchedule(Long id, ScheduleRequestDTO request){
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The schedule is not found."));

        schedule.setRoom(request.getRoom());
        schedule.setCapacity(request.getCapacity());
        schedule.setDayOfWeek(request.getDayOfWeek());
        schedule.setStartTime(request.getStartTime());
        schedule.setEndTime(request.getEndTime());

        scheduleRepository.save(schedule);
    }

    public void deleteSchedule(Long id){
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The schedule is not found."));
        scheduleRepository.delete(schedule);
    }
}
