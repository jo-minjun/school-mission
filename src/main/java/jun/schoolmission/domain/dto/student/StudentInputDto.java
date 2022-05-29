package jun.schoolmission.domain.dto.student;

import lombok.*;

import javax.validation.Valid;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StudentInputDto {

    @Valid
    private StudentDto student;
}
