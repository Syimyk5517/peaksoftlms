package peaksoftlms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@Entity
@Table(name = "presentations")
@AllArgsConstructor
@NoArgsConstructor
public class Presentation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "presentation_gen")
    @SequenceGenerator(name = "presentation_gen", sequenceName = "presentation_seq")
    private Long id;
    private String name;
    private String description;
    private String formatPPT;
    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

}