package jun.schoolmission.domain.dto.subject;

import jun.schoolmission.common.annotation.Name;
import jun.schoolmission.domain.entity.Subject;
import lombok.*;

import javax.validation.constraints.Size;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SubjectDto {

    private Long id;

    @Name(message = "name은 한글/영어/숫자만 가능합니다.")
    @Size(min = 1, max = 12, message = "name은 1 ~ 12 자만 가능합니다.")
    private String name;

    private Integer score;

    public static SubjectDto of(Subject subject) {
        return SubjectDto.builder()
                .id(subject.getId())
                .name(subject.getName())
                .build();
    }

    public static SubjectDto of(Subject subject, Integer score) {
        return SubjectDto.builder()
                .id(subject.getId())
                .name(subject.getName())
                .score(score)
                .build();
    }

    public Subject toEntity() {
        return Subject.builder()
                .name(this.name)
                .build();
    }
}
