package self.learning.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import self.learning.backend.model.Enrollment;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findByStudentId(Long studentId);
}
