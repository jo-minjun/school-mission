package jun.schoolmission.domain.dto.subject;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SubjectOutputDto {

    private List<SubjectDto> subjects;
}
