package jun.schoolmission.domain.dto.score;

import jun.schoolmission.domain.dto.subject.SubjectDto;
import jun.schoolmission.domain.entity.Score;
import jun.schoolmission.domain.entity.StudentSubject;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class StudentScoreDto {

    private Double averageScore;
    private List<SubjectDto> subjects;

    public static StudentScoreDto of(List<StudentSubject> studentSubjects) {
        List<SubjectDto> subjectDtos = new ArrayList<>();
        int sum = 0;

        for (StudentSubject studentSubject : studentSubjects) {
            Score score = studentSubject.getScore();
            sum += score == null ? -1 : score.getScore();

            subjectDtos.add(SubjectDto.of(studentSubject.getSubject(), score == null ? -1 : score.getScore()));
        }

        return StudentScoreDto.builder()
                .averageScore(sum / (double) studentSubjects.size())
                .subjects(subjectDtos)
                .build();
    }
}
