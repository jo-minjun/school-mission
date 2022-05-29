package jun.schoolmission.domain.dto.subject;

import lombok.*;

import javax.validation.Valid;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SubjectInputDto {

    @Valid
    private SubjectDto subject;
}
