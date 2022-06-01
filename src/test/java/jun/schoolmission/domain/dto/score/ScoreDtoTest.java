package jun.schoolmission.domain.dto.score;

import jun.schoolmission.domain.entity.Score;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName(value = "ScoreDto 테스트")
class ScoreDtoTest {

    @Test
    @DisplayName(value = "Entity To DTO")
    void entity_to_dto() {
        // given
        Score score = Score.builder()
                .score(100)
                .build();

        // when
        ScoreDto scoreDto = ScoreDto.of(score);

        // then
        assertThat(scoreDto).isNotNull();
        assertThat(scoreDto.getScore()).isEqualTo(score.getScore());
    }

    @Test
    @DisplayName(value = "DTO To Entity")
    void dto_to_entity() {
        // given
        ScoreDto scoreDto = ScoreDto.builder()
                .score(100)
                .build();

        // when
        Score score = scoreDto.toEntity();

        // then
        assertThat(score).isNotNull();
        assertThat(score.getScore()).isEqualTo(scoreDto.getScore());
    }
}