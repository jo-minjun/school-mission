package jun.schoolmission.domain.dto.subject;

import jun.schoolmission.domain.entity.Subject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName(value = "SubjectDto 테스트")
class SubjectDtoTest {

    @Test
    @DisplayName(value = "SubjectDto to Entity")
    void to_entity() {
        // given
        String subjectName = "subjectName";
        SubjectDto subjectDto = SubjectDto.builder()
                .name(subjectName)
                .build();

        // when
        Subject subject = subjectDto.toEntity();

        // then
        assertThat(subject).isNotNull();
        assertThat(subject).isInstanceOf(Subject.class);
        assertThat(subject.getName()).isEqualTo(subjectName);
    }

    @Test
    @DisplayName(value = "Entity to SubjectDto")
    void to_subject_dto() {
        // given
        String subjectName = "subjectName";
        long id = 1L;
        Subject subject = Subject.builder()
                .id(id)
                .name(subjectName)
                .build();

        // when
        SubjectDto subjectDto = SubjectDto.of(subject);

        // then
        assertThat(subjectDto).isNotNull();
        assertThat(subjectDto).isInstanceOf(SubjectDto.class);
        assertThat(subjectDto.getId()).isEqualTo(id);
        assertThat(subjectDto.getName()).isEqualTo(subjectName);
    }
}