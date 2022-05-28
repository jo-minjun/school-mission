package jun.schoolmission.service;

import jun.schoolmission.domain.dto.StudentDto;

import java.util.List;

public interface StudentService {

    Long createStudent(StudentDto studentDto);
    List<StudentDto> searchStudentDtos();
}
