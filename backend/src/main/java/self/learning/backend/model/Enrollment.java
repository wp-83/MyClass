package self.learning.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments")
@NoArgsConstructor
@Getter
@Setter
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status;

    @Column(name = "enroll_at")
    private LocalDateTime enrollAt;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "lecturer_course_id")
    private LecturerCourse lecturerCourse;

    @PrePersist
    protected void onCreate(){
        LocalDateTime now = LocalDateTime.now();

        this.enrollAt = now;
        this.lastUpdate = now;
        this.status = EnrollmentStatus.ACTIVE;
    }

    @PreUpdate
    protected void onUpdate(){
        this.lastUpdate = LocalDateTime.now();
    }
}
