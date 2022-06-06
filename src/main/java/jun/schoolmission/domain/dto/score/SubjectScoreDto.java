package jun.schoolmission.domain.dto.score;

import jun.schoolmission.domain.dto.student.StudentDto;
import jun.schoolmission.domain.entity.Score;
import jun.schoolmission.domain.entity.StudentSubject;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SubjectScoreDto {

    private Double averageScore;
    private List<StudentDto> students;

    public static SubjectScoreDto of(List<StudentSubject> studentSubjects) {
        List<StudentDto> studentDtos = new ArrayList<>();
        int sum = 0;

        for (StudentSubject studentSubject : studentSubjects) {
            Score score = studentSubject.getScore();
            sum += score == null ? -1 : score.getScore();

            studentDtos.add(StudentDto.of(studentSubject.getStudent(), score == null ? -1 : score.getScore()));
        }

        return SubjectScoreDto.builder()
                .averageScore(sum / (double) studentSubjects.size())
                .students(studentDtos)
                .build();
    }
}
