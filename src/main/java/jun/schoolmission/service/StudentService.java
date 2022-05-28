package jun.schoolmission.service;

import jun.schoolmission.domain.dto.StudentDto;

public interface StudentService {
    Long createStudent(StudentDto studentDto);
}
