package jun.schoolmission.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName(value = "StudentSubject 엔티티 테스트")
class StudentSubjectTest {

    @Test
    @DisplayName(value = "StudentSubject 빌더")
    void builder() {
        // given
        String studentName = "student";
        Student student = Student.builder()
                .name(studentName)
                .build();

        String subjectName = "subject";
        Subject subject = Subject.builder()
                .name(subjectName)
                .build();

        // when
        StudentSubject studentSubject = StudentSubject.builder()
                .student(student)
                .subject(subject)
                .build();

        // then
        assertThat(student).isNotNull();
        assertThat(student.getName()).isEqualTo(studentName);
        assertThat(subject).isNotNull();
        assertThat(subject.getName()).isEqualTo(subjectName);
        assertThat(studentSubject).isNotNull();
    }

    @Test
    @DisplayName(value = "StudentSubject 같은 id, 다른 필드 Equals")
    public void equals_id() {
        //given
        long id = 1;

        String studentName = "student";
        Student student = Student.builder()
                .name(studentName)
                .build();

        String subjectName1 = "subject1";
        Subject subject1 = Subject.builder()
                .name(subjectName1)
                .build();

        String subjectName2 = "subject2";
        Subject subject2 = Subject.builder()
                .name(subjectName2)
                .build();

        //when
        StudentSubject studentSubject1 = StudentSubject.builder()
                .id(id)
                .student(student)
                .subject(subject1)
                .build();

        StudentSubject studentSubject2 = StudentSubject.builder()
                .id(id)
                .student(student)
                .subject(subject2)
                .build();

        //then
        assertThat(studentSubject1).isEqualTo(studentSubject2);
    }

    @Test
    @DisplayName(value = "StudentSubject 다른 id, 같은 필드 Equals")
    public void equals_field() {
        //given
        String studentName = "student";
        Student student = Student.builder()
                .name(studentName)
                .build();

        String subjectName = "subject";
        Subject subject = Subject.builder()
                .name(subjectName)
                .build();

        //when
        StudentSubject studentSubject1 = StudentSubject.builder()
                .id(1L)
                .student(student)
                .subject(subject)
                .build();

        StudentSubject studentSubject2 = StudentSubject.builder()
                .id(2L)
                .student(student)
                .subject(subject)
                .build();

        //then
        assertThat(studentSubject1).isNotEqualTo(studentSubject2);
    }

    @Test
    @DisplayName(value = "Score 업데이트 - 생성")
    void update_score_new() {
        // given
        int score = 100;
        StudentSubject studentSubject = StudentSubject.builder()
                .student(null)
                .subject(null)
                .build();

        // when
        studentSubject.updateScore(score);

        // then
        assertThat(studentSubject.getScore().getScore()).isEqualTo(score);
    }

    @Test
    @DisplayName(value = "Score 업데이트 - 업데이트")
    void update_score() {
        // given
        long id = 1L;
        int score = 100;
        StudentSubject studentSubject = StudentSubject.builder()
                .student(null)
                .subject(null)
                .score(
                        Score.builder()
                                .id(id)
                                .score(90)
                                .build()
                )
                .build();

        // when
        studentSubject.updateScore(score);

        // then
        assertThat(studentSubject.getScore().getId()).isEqualTo(id);
        assertThat(studentSubject.getScore().getScore()).isEqualTo(score);
    }
}