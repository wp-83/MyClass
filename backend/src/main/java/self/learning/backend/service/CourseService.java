package self.learning.backend.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import self.learning.backend.dto.course.CourseRequestDTO;
import self.learning.backend.dto.course.CourseResponseDTO;
import self.learning.backend.model.Course;
import self.learning.backend.repository.CourseRepository;

import java.util.Comparator;
import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }

    public List<CourseResponseDTO> allCourses(){
        return this.courseRepository.findAll().stream().sorted(Comparator.comparing(Course::getCode).reversed())
                .map(course -> new CourseResponseDTO(
                        course.getId(),
                        course.getCode(),
                        course.getName(),
                        course.getCredit(),
                        course.getMinSemester()
                )).toList();
    }

    public CourseResponseDTO getCourseById(Long id){
        Course course = this.courseRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course data is not found.")
        );

        return new CourseResponseDTO(course.getId(), course.getCode(), course.getName(), course.getCredit(), course.getMinSemester());
    }

    public void createNewCourse(CourseRequestDTO request){
        Course course = new Course();

        course.setCode(request.getCode());
        course.setName(request.getName());
        course.setCredit(request.getCredit());
        course.setMinSemester(request.getMinSemester());

        this.courseRepository.save(course);
    }

    public void updateCourse(Long id, CourseRequestDTO request){
        Course course = courseRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The course data is not found."));

        course.setCode(request.getCode());
        course.setName(request.getName());
        course.setCredit(request.getCredit());
        course.setMinSemester(request.getMinSemester());

        courseRepository.save(course);
    }

    public void deleteCourse(Long id){
        Course course = courseRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The course data is not found."));

        courseRepository.delete(course);
    }
}
