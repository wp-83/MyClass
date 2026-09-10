package self.learning.backend.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import self.learning.backend.dto.lecturerCourse.LecturerCourseRequestDTO;
import self.learning.backend.dto.lecturerCourse.LecturerCourseScheduleRequestDTO;
import self.learning.backend.model.Course;
import self.learning.backend.model.Lecturer;
import self.learning.backend.model.LecturerCourse;
import self.learning.backend.model.Schedule;
import self.learning.backend.repository.CourseRepository;
import self.learning.backend.repository.LecturerCourseRepository;
import self.learning.backend.repository.LecturerRepository;
import self.learning.backend.repository.ScheduleRepository;

import java.util.List;

@Service
public class LecturerCourseService {
    private final LecturerCourseRepository lecturerCourseRepository;
    private final LecturerRepository lecturerRepository;
    private final CourseRepository courseRepository;
    private final ScheduleRepository scheduleRepository;

    public LecturerCourseService(LecturerCourseRepository lecturerCourseRepository, LecturerRepository lecturerRepository, CourseRepository courseRepository, ScheduleRepository scheduleRepository) {
        this.lecturerCourseRepository = lecturerCourseRepository;
        this.lecturerRepository = lecturerRepository;
        this.courseRepository = courseRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public List<LecturerCourse> findLecturerCourseMapping(Long lecturerId){
        return lecturerCourseRepository.findAllByLecturerId(lecturerId);
    }

    public void assignLecturer(LecturerCourseRequestDTO request){
        Lecturer lecturer = lecturerRepository.findById(request.getLecturerId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The lecturer data is not found."));
        List<Course> courses = courseRepository.findAllById(request.getCourseId());

        LecturerCourse lecturerCourse = new LecturerCourse();
        lecturerCourse.setLecturer(lecturer);

        for (Course c : courses){
            lecturerCourse.setCourse(c);
            lecturerCourseRepository.save(lecturerCourse);
        }
    }

    public void deleteLecturerCourse(long id){
        LecturerCourse lecturerCourse = lecturerCourseRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The lecturer-course mapping data is not found."));
        lecturerCourseRepository.delete(lecturerCourse);
    }

    public void setSchedule(List<LecturerCourseScheduleRequestDTO> request){
        for (LecturerCourseScheduleRequestDTO r : request){
            if (r.getSchedule().getIsOccupied().equals(Boolean.TRUE)){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("The schedule for %s course is already occupied.", r.getCourse().getName()));
            }
        }

        for (LecturerCourseScheduleRequestDTO r : request){
            LecturerCourse lecturerCourse = lecturerCourseRepository.findById(r.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The lecturer-course data is not found."));
            lecturerCourse.setSchedule(r.getSchedule());

            Schedule schedule = scheduleRepository.findById(r.getSchedule().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The schedule data is not found."));
            schedule.setIsOccupied(Boolean.TRUE);

            scheduleRepository.save(schedule);
            lecturerCourseRepository.save(lecturerCourse);
        }
    }

    public void updateLecturerCourseSchedule(Long lecturerCourseId, LecturerCourseScheduleRequestDTO request){
        LecturerCourse lecturerCourse = lecturerCourseRepository.findById(lecturerCourseId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "The lecturer schedule is not found."
        ));

        if (request.getSchedule().getIsOccupied().equals(Boolean.TRUE)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The schedule has been occupied by other course.");
        }

        Schedule scheduleBefore = lecturerCourse.getSchedule();
        Schedule currentSchedule = request.getSchedule();

        scheduleBefore.setIsOccupied(Boolean.FALSE);
        currentSchedule.setIsOccupied(Boolean.TRUE);

        scheduleRepository.save(scheduleBefore);
        scheduleRepository.save(currentSchedule);

        lecturerCourse.setSchedule(request.getSchedule());
        lecturerCourseRepository.save(lecturerCourse);
    }
}
