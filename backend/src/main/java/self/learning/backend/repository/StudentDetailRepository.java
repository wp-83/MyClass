package self.learning.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import self.learning.backend.model.StudentDetail;

public interface StudentDetailRepository extends JpaRepository<StudentDetail, Long> {
}
