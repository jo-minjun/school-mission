package jun.schoolmission.domain.dto.score;

import jun.schoolmission.domain.entity.Score;
import jun.schoolmission.domain.entity.Student;
import jun.schoolmission.domain.entity.StudentSubject;
import jun.schoolmission.domain.entity.Subject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName(value = "StudentScore DTO 테스트")
class StudentScoreDtoTest {

    @Test
    @DisplayName(value = "StudentSubject To StudentScoreDto")
    void student_subject_to_student_score_dto() {
        // given
        int size = 10;

        List<StudentSubject> studentSubjects = new ArrayList<>();
        IntStream.range(0, size).forEach(i -> studentSubjects.add(create_student_subject(i)));

        double sum = IntStream.range(0, size).reduce(0, Integer::sum);
        Double avgScore = sum / size;

        // when
        StudentScoreDto studentScoreDto = StudentScoreDto.of(studentSubjects);

        // then
        assertThat(studentScoreDto.getAverageScore()).isEqualTo(avgScore);
        assertThat(studentScoreDto.getSubjects().size()).isEqualTo(size);
    }

    private StudentSubject create_student_subject(int i) {
        return StudentSubject.builder()
                .score(Score.builder()
                        .score(i)
                        .build()
                )
                .student(Student.builder()
                                .id((long) i)
                                .build()
                )
                .subject(Subject.builder()
                        .id((long) i)
                        .build()
                )
                .build();
    }
}