package self.learning.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import self.learning.backend.model.Lecturer;

public interface LecturerRepository extends JpaRepository<Lecturer, Long> {
}
