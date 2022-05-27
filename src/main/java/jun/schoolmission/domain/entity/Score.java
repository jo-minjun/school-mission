package jun.schoolmission.domain.entity;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
}
