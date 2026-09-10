package self.learning.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

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
    @Length(min = 10, max = 10)
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "credit")
    @Max(8)
    private Integer credit;

    @Column(name = "min_semester")
    @Min(1)
    @Max(8)
    private Integer minSemester;

    @OneToMany(mappedBy = "course")
    private List<LecturerCourse> lecturers;
}
