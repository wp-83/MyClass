package self.learning.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "schedules")
@NoArgsConstructor
@Getter
@Setter
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room")
    @Size(min = 4, max = 5)
    @Pattern(regexp = "^[A-Z][0-9]+$", message = "Room must start with one capital letter and the following room number (3-4 digits).")
    private String room;

    @Column(name = "capacity")
    @Min(1)
    @Max(250)
    private Integer capacity;

    @Column(name = "total_enroll")
    @Min(0)
    @Max(250)
    private Integer totalEnroll;

    @Column(name = "day_of_week")
    @Min(1) // Monday
    @Max(6) // Saturday
    private Integer dayOfWeek;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "is_occupied")
    private Boolean isOccupied;

    @OneToMany(mappedBy = "schedule")
    private List<LecturerCourse> lecturerCourses;

    @PrePersist
    public void beforeCreate(){
        this.totalEnroll = 0;
        this.isOccupied = Boolean.FALSE;
    }
}
