package jun.schoolmission.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName(value = "Subject 엔티티 테스트")
class SubjectTest {

    @Test
    @DisplayName(value = "Subject 빌더")
    void builder() {
        // given
        String name = "subject";

        // when
        Subject subject = Subject.builder()
                .name(name)
                .build();

        // then
        assertThat(subject).isNotNull();
        assertThat(subject.getName()).isEqualTo(name);
    }

    @Test
    @DisplayName(value = "Subject 같은 id, 다른 필드 Equals")
    public void equals_id() {
        //given
        long id = 1;

        //when
        Subject student1 = Subject.builder()
                .id(id)
                .name("subject1")
                .build();

        Subject student2 = Subject.builder()
                .id(id)
                .name("subject2")
                .build();

        //then
        assertThat(student1).isEqualTo(student2);
    }

    @Test
    @DisplayName(value = "Subject 다른 id, 같은 필드 Equals")
    public void equals_field() {
        //given
        String name = "subject";

        //when
        Subject student1 = Subject.builder()
                .id(1L)
                .name(name)
                .build();

        Subject student2 = Subject.builder()
                .id(2L)
                .name(name)
                .build();

        //then
        assertThat(student1).isNotEqualTo(student2);
    }
}