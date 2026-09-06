package self.learning.backend.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import self.learning.backend.dto.student.CreateStudentDTO;
import self.learning.backend.dto.student.StudentResponseDTO;
import self.learning.backend.dto.student.UpdateStudentDTO;
import self.learning.backend.model.Student;
import self.learning.backend.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/student-portal/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping
    public List<StudentResponseDTO> allStudents(){
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public StudentResponseDTO getStudent(@PathVariable Long id){
        return studentService.getStudentById(id);
    }

    @PostMapping
    public ResponseEntity<?> createStudent(@Valid @RequestBody CreateStudentDTO studentRequest){
        studentService.createNewStudent(studentRequest);

        return ResponseEntity.ok("Student data is created.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @Valid @RequestBody UpdateStudentDTO studentRequest){
        studentService.updateStudent(id, studentRequest);

        return ResponseEntity.ok("Student data successfully updated.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id){
        studentService.deleteStudent(id);

        return ResponseEntity.ok("Student data successfully deleted.");
    }
}
