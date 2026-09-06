package self.learning.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import self.learning.backend.model.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
}
