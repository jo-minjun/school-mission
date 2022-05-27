package jun.schoolmission.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName(value = "Score 엔티티 테스트")
class ScoreTest {

    @Test
    @DisplayName(value = "Score 빌더")
    void builder() {
        // given
        int scoreValue = 95;

        // when
        Score score = Score.builder()
                .score(scoreValue)
                .build();

        // then
        assertThat(score).isNotNull();
        assertThat(score.getScore()).isEqualTo(scoreValue);
    }

    @Test
    @DisplayName(value = "Score 같은 id, 다른 필드 Equals")
    public void equals_id() {
        //given
        long id = 1;

        //when
        Score score1 = Score.builder()
                .id(id)
                .score(100)
                .build();

        Score score2 = Score.builder()
                .id(id)
                .score(0)
                .build();

        //then
        assertThat(score1).isEqualTo(score2);
    }

    @Test
    @DisplayName(value = "Score 다른 id, 같은 필드 Equals")
    public void equals_field() {
        //given
        int scoreValue = 95;

        //when
        Score score1 = Score.builder()
                .id(1L)
                .score(scoreValue)
                .build();

        Score score2 = Score.builder()
                .id(2L)
                .score(scoreValue)
                .build();

        //then
        assertThat(score1).isNotEqualTo(score2);
    }
}