package self.learning.backend.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import self.learning.backend.dto.lecturer.LecturerRequestDTO;
import self.learning.backend.dto.lecturer.LecturerResponseDTO;
import self.learning.backend.dto.message.ResponseMessageDTO;
import self.learning.backend.service.LecturerService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/lecturer")
public class LecturerController {
    private final LecturerService lecturerService;

    public LecturerController(LecturerService lecturerService) {
        this.lecturerService = lecturerService;
    }

    @GetMapping
    public List<LecturerResponseDTO> getAllLecturers(){
        return lecturerService.getAllLecturers();
    }

    @GetMapping("/{id}")
    public LecturerResponseDTO getLecturerById(@PathVariable Long id){
        return lecturerService.getLecturerById(id);
    }

    @PostMapping
    public ResponseEntity<ResponseMessageDTO> createNewLecturer(@Valid @RequestBody LecturerRequestDTO request){
        lecturerService.createNewLecturer(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseMessageDTO("New lecturer is successfully created."));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessageDTO> updateLecturer(@PathVariable Long id, @Valid @RequestBody LecturerRequestDTO request){
        lecturerService.updateLecturer(id, request);

        return ResponseEntity.ok(new ResponseMessageDTO("Lecturer data is successfully updated."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessageDTO> deleteLecturer(@PathVariable Long id){
        lecturerService.deleteLecturer(id);

        return ResponseEntity.ok(new ResponseMessageDTO("Lecturer data successfully deleted."));
    }
}
