package self.learning.backend.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import self.learning.backend.dto.lecturer.LecturerRequestDTO;
import self.learning.backend.dto.lecturer.LecturerResponseDTO;
import self.learning.backend.model.Lecturer;
import self.learning.backend.repository.LecturerRepository;

import java.util.List;

@Service
public class LecturerService {
    private final LecturerRepository lecturerRepository;

    public LecturerService(LecturerRepository lecturerRepository) {
        this.lecturerRepository = lecturerRepository;
    }

    public List<LecturerResponseDTO> getAllLecturers(){
        return lecturerRepository.findAll().stream().map(lecturer -> new LecturerResponseDTO(
                lecturer.getId(),
                lecturer.getName(),
                lecturer.getDepartment()
        )).toList();
    }

    public LecturerResponseDTO getLecturerById(Long id){
        Lecturer lecturer = lecturerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lecturer data is not found."));

        return new LecturerResponseDTO(lecturer.getId(), lecturer.getName(), lecturer.getDepartment());
    }

    public void createNewLecturer(LecturerRequestDTO request){
        Lecturer lecturer = new Lecturer();

        lecturer.setName(request.getName());
        lecturer.setDepartment(request.getDepartment());

        lecturerRepository.save(lecturer);
    }

    public void updateLecturer(Long id, LecturerRequestDTO request){
        Lecturer lecturer = lecturerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lecturer data is not found."));

        lecturer.setName(request.getName());
        lecturer.setDepartment(request.getDepartment());

        lecturerRepository.save(lecturer);
    }

    public void deleteLecturer(Long id){
        Lecturer lecturer = lecturerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lecturer data is not found."));

        lecturerRepository.delete(lecturer);
    }
}
