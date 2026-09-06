package self.learning.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import self.learning.backend.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
