package self.learning.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import self.learning.backend.dto.enrollment.EnrollmentResponseDTO;
import self.learning.backend.service.EnrollmentService;

import java.util.List;

@RestController
@RequestMapping("/enrollment")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("/{id}")
    public List<EnrollmentResponseDTO> getStudentEnrollment(@PathVariable Long id){
        return enrollmentService.getEnrollmentBySpecificStudent(id);
    }

}
