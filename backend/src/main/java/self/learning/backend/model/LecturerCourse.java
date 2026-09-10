package self.learning.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "lecturer_courses")
@NoArgsConstructor
@Getter
@Setter
public class LecturerCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lecturer_id")
    private Lecturer lecturer;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @OneToMany(mappedBy = "lecturerCourse")
    private List<Enrollment> enrollments;

    private Boolean isActive;

    @PrePersist
    public void beforeCreate(){
        this.schedule = null;
        this.isActive = Boolean.TRUE;
    }
}
