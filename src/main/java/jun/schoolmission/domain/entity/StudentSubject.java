package jun.schoolmission.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class StudentSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", foreignKey = @ForeignKey(name = "FK_STUDENT_SUBJECT_TO_STUDENT"))
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", foreignKey = @ForeignKey(name = "FK_STUDENT_SUBJECT_TO_SUBJECT"))
    private Subject subject;

    @OneToOne(mappedBy = "studentSubject", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Score score;

    public void updateScore(Integer score) {
        if (this.score == null) {
            this.score = Score.builder()
                    .studentSubject(this)
                    .score(score)
                    .build();
            return;
        }

        this.score.changeScore(this, score);
    }
}
