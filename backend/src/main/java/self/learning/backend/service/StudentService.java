package self.learning.backend.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import self.learning.backend.dto.student.CreateStudentDTO;
import self.learning.backend.dto.student.StudentResponseDTO;
import self.learning.backend.dto.student.UpdateStudentDTO;
import self.learning.backend.model.Student;
import self.learning.backend.model.StudentDetail;
import self.learning.backend.repository.StudentDetailRepository;
import self.learning.backend.repository.StudentRepository;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentDetailRepository studentDetailRepository;
    private final PasswordEncoder passwordEncoder;

    public StudentService(StudentRepository studentRepository, StudentDetailRepository studentDetailRepository, PasswordEncoder passwordEncoder){
        this.studentRepository = studentRepository;
        this.studentDetailRepository = studentDetailRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<StudentResponseDTO> getAllStudents(){
        return studentRepository.findAll().stream().map(
                student -> new StudentResponseDTO(
                        student.getId(),
                        student.getUsername(),
                        student.getEmail(),
                        student.getStudentDetail())
        ).toList();
    }

    public StudentResponseDTO getStudentById(Long id){
        Student student = studentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "THe data is not found."));

        return new StudentResponseDTO(
                student.getId(),
                student.getUsername(),
                student.getEmail(),
                student.getStudentDetail()
        );
    }

    public void createNewStudent(CreateStudentDTO request){
        if (request == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The data is empty.");
        }

        Student student = new Student();

        student.setUsername(request.getUsername());
        student.setEmail(request.getEmail());

        String hashedPassword = passwordEncoder.encode(request.getPassword());
        student.setPassword(hashedPassword);

        studentRepository.save(student);
    }

    public void updateStudent(Long id, UpdateStudentDTO request){
        Student student = studentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The student data is not found."));
        student.setUsername(request.getUsername());
        student.setEmail(request.getEmail());

        StudentDetail detail = student.getStudentDetail();
        if (detail == null){
            detail = new StudentDetail();

            detail.setStudent(student);
            student.setStudentDetail(detail);
        }

        var studentDetailRequest = request.getStudentDetail();
        detail.setSemester(studentDetailRequest.getSemester());
        detail.setProgram(studentDetailRequest.getProgram());
        detail.setMaxCredit(studentDetailRequest.getMaxCredit());
        detail.setNim(studentDetailRequest.getNim());
        detail.setFirstName(studentDetailRequest.getFirstName());
        detail.setLastName(studentDetailRequest.getLastName());

        studentRepository.save(student);
        studentDetailRepository.save(detail);
    }

    public void deleteStudent(Long id){
        Student student = studentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The student data is not found."));
        studentRepository.delete(student);
    }
}
