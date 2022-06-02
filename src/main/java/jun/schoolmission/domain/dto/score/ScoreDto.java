package jun.schoolmission.domain.dto.score;

import jun.schoolmission.domain.entity.Score;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ScoreDto {

    @Min(value = 0, message = "score는 0 ~ 100만 가능합니다.")
    @Max(value = 100, message = "score는 0 ~ 100만 가능합니다.")
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
