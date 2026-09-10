package self.learning.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import self.learning.backend.model.LecturerCourse;

import java.util.List;

public interface LecturerCourseRepository extends JpaRepository<LecturerCourse, Long> {
    List<LecturerCourse> findAllByLecturerId(Long id);
}
