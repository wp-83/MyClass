package self.learning.backend.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import self.learning.backend.dto.lecturerCourse.LecturerCourseRequestDTO;
import self.learning.backend.dto.lecturerCourse.LecturerCourseScheduleRequestDTO;
import self.learning.backend.dto.message.ResponseMessageDTO;
import self.learning.backend.model.LecturerCourse;
import self.learning.backend.service.LecturerCourseService;

import java.util.List;

@RestController
@RequestMapping("/lecturer-course")
public class LecturerCourseController {
    private final LecturerCourseService lecturerCourseService;

    public LecturerCourseController(LecturerCourseService lecturerCourseService) {
        this.lecturerCourseService = lecturerCourseService;
    }

    @GetMapping("/{lecturerId}")
    public List<LecturerCourse> viewLecturerCourses(@PathVariable("lecturerId") Long id){
        return lecturerCourseService.findLecturerCourseMapping(id);
    }

    @PostMapping
    public ResponseEntity<ResponseMessageDTO> assignLecturer(@Valid @RequestBody LecturerCourseRequestDTO request){
        lecturerCourseService.assignLecturer(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessageDTO("Some courses are successfully assigned to the lecturer."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessageDTO> deleteLecturerCourseAssign(@PathVariable Long id){
        lecturerCourseService.deleteLecturerCourse(id);

        return ResponseEntity.ok(new ResponseMessageDTO("The lecturer-course mapping is successfully deleted."));
    }

    @PutMapping("/schedule")
    public ResponseEntity<ResponseMessageDTO> setSchedule(@Valid @RequestBody List<@Valid LecturerCourseScheduleRequestDTO> request){
        lecturerCourseService.setSchedule(request);

        return ResponseEntity.ok(new ResponseMessageDTO("The schedule for each courses have been assigned."));
    }

    @PutMapping("/schedule/{id}")
    public ResponseEntity<ResponseMessageDTO> updateScheduleOfLecturerCourse(Long lecturerCourseId, @Valid @RequestBody LecturerCourseScheduleRequestDTO request){
        lecturerCourseService.updateLecturerCourseSchedule(lecturerCourseId, request);

        return ResponseEntity.ok(new ResponseMessageDTO("The schedule of the course is successfully updated."));
    }
}


