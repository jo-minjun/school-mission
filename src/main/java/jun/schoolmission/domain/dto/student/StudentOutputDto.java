package jun.schoolmission.domain.dto.student;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class StudentOutputDto {

    private List<StudentDto> students;
}
