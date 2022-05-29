package jun.schoolmission.domain.dto.subject;

import jun.schoolmission.domain.entity.Subject;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SubjectDto {

    private Long id;
    private String name;

    public static SubjectDto of(Subject subject) {
        return SubjectDto.builder()
                .id(subject.getId())
                .name(subject.getName())
                .build();
    }

    public Subject toEntity() {
        return Subject.builder()
                .name(this.name)
                .build();
    }
}
