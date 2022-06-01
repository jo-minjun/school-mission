package jun.schoolmission.domain.dto.score;

import jun.schoolmission.domain.entity.Score;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ScoreDto {

    private Integer score;

    public static ScoreDto of(Score score) {
        return null;
    }

    public Score toEntity() {
        return null;
    }
}
