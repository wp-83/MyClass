package self.learning.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "courses")
@NoArgsConstructor
@Getter
@Setter
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "credit")
    private Integer credit;

    @Column(name = "min_semester")
    private Integer minSemester;

    @Column(name = "capacity")
    private Integer capacity;

    @ManyToMany(mappedBy = "courses")
    private List<Lecturer> lecturers;

    @OneToMany(mappedBy = "course")
    private List<Enrollment> enrollments;
}
