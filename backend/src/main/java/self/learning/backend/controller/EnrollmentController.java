package self.learning.backend.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import self.learning.backend.dto.enrollment.EnrollmentRequestDTO;
import self.learning.backend.dto.enrollment.EnrollmentResponseDTO;
import self.learning.backend.dto.enrollment.StudentEnrollmentResponseDTO;
import self.learning.backend.dto.message.ResponseMessageDTO;
import self.learning.backend.service.EnrollmentService;

import java.util.List;

@RestController
@RequestMapping("/enrollment")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public List<StudentEnrollmentResponseDTO> getAllEnrollmentsGroupByStudents(){
        return enrollmentService.allEnrollmentByStudent();
    }

    @GetMapping("/{studentId}")
    public StudentEnrollmentResponseDTO getStudentEnrollment(@PathVariable Long studentId){
        return enrollmentService.getEnrollmentBySpecificStudent(studentId);
    }

    @PostMapping
    public ResponseEntity<ResponseMessageDTO> createNewEnrollment(@Valid @RequestBody EnrollmentRequestDTO request){
        enrollmentService.createEnrollment(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseMessageDTO("The enrollment is successfully registered.")
        );
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<ResponseMessageDTO> updateCurrentEnrollment(@PathVariable("studentId") Long id){
        enrollmentService.changeEnrollmentStatus(id);

        return ResponseEntity.ok(new ResponseMessageDTO("The enrollment is successfully updated."));
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<ResponseMessageDTO> deleteEnrollment(@PathVariable("studentId") Long id){
        enrollmentService.deleteEnrollment(id);

        return ResponseEntity.ok(new ResponseMessageDTO("The enrollment is successfully deleted."));
    }
}
