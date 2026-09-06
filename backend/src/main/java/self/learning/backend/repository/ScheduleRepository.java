package self.learning.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import self.learning.backend.model.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
