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
        return ScoreDto.builder()
                .score(score.getScore())
                .build();
    }

    public Score toEntity() {
        return Score.builder()
                .score(score)
                .build();
    }
}
