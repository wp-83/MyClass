package self.learning.backend.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import self.learning.backend.dto.course.CourseRequestDTO;
import self.learning.backend.dto.course.CourseResponseDTO;
import self.learning.backend.dto.message.ResponseMessageDTO;
import self.learning.backend.service.CourseService;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<CourseResponseDTO> getAllCourses(){
        return courseService.allCourses();
    }

    @GetMapping("/{id}")
    public CourseResponseDTO getCourseById(@PathVariable Long id){
        return courseService.getCourseById(id);
    }

    @PostMapping
    public ResponseEntity<ResponseMessageDTO> createNewCourse(@Valid @RequestBody CourseRequestDTO request){
        courseService.createNewCourse(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseMessageDTO("Course is successfully created"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessageDTO> updateCourse(@PathVariable Long id, @Valid @RequestBody CourseRequestDTO request){
        courseService.updateCourse(id, request);

        return ResponseEntity.ok(new ResponseMessageDTO("Course data is successfully updated."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessageDTO> deleteCourse(@PathVariable Long id){
        courseService.deleteCourse(id);

        return ResponseEntity.ok(new ResponseMessageDTO("Course data is successfully deleted."));
    }
}
