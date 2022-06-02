package jun.schoolmission.domain.entity;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Score {

    @Id
    @GeneratedValue
    private Long id;

    @Range(min = 0L, max = 100L)
    private Integer score;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_subject_id", foreignKey = @ForeignKey(name = "FK_SCORE_TO_STUDENT_SUBJECT"))
    private StudentSubject studentSubject;

    public void changeScore(StudentSubject studentSubject, Integer score) {
        this.studentSubject = studentSubject;
        this.score = score;
    }
}
