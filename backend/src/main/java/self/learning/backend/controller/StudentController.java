package self.learning.backend.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import self.learning.backend.dto.message.ResponseMessageDTO;
import self.learning.backend.dto.student.CreateStudentDTO;
import self.learning.backend.dto.student.StudentResponseDTO;
import self.learning.backend.dto.student.UpdateStudentDTO;
import self.learning.backend.model.Student;
import self.learning.backend.service.StudentService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/student")
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
    public ResponseEntity<ResponseMessageDTO> createStudent(@Valid @RequestBody CreateStudentDTO studentRequest){
        studentService.createNewStudent(studentRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessageDTO("Student data is created."));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessageDTO> updateStudent(@PathVariable Long id, @Valid @RequestBody UpdateStudentDTO studentRequest){
        studentService.updateStudent(id, studentRequest);

        return ResponseEntity.ok(new ResponseMessageDTO("Student data is successfully updated."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessageDTO> deleteStudent(@PathVariable Long id){
        studentService.deleteStudent(id);

        return ResponseEntity.ok(new ResponseMessageDTO("Student data successfully deleted."));
    }
}
