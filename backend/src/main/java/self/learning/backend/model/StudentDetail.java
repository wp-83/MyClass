package self.learning.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "student_details")
@NoArgsConstructor
@Getter
@Setter
public class StudentDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "semester")
    private Integer semester;

    @Column(name = "max_credit")
    private Integer maxCredit;

    @Column(name = "nim", unique = true, length = 10)
    private String nim;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "program")
    private String program;

    @OneToOne
    @JoinColumn(name = "student_id", unique = true)
    private Student student;
}
