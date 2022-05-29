package jun.schoolmission.service.impl;

import jun.schoolmission.common.exception.AlreadyExistException;
import jun.schoolmission.common.exception.CustomExceptionEntity;
import jun.schoolmission.common.exception.ErrorCode;
import jun.schoolmission.domain.dto.StudentDto;
import jun.schoolmission.domain.entity.Student;
import jun.schoolmission.domain.entity.Subject;
import jun.schoolmission.domain.repository.StudentRepository;
import jun.schoolmission.domain.repository.SubjectRepository;
import jun.schoolmission.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    @Override
    @Transactional
    public Long createStudent(StudentDto studentDto) {
        studentRepository.findByPhoneNumber(studentDto.getPhoneNumber()).ifPresent(student -> {
            throw new AlreadyExistException(CustomExceptionEntity.builder()
                    .errorCode(ErrorCode.ALREADY_EXIST_STUDENT)
                    .explain(student.getPhoneNumber())
                    .build());
        });

        Student student = StudentDto.toEntity(studentDto);

        List<Subject> subjects = subjectRepository.findAll();
        student.registerSubjects(subjects);

        return studentRepository.save(student).getId();
    }

    @Override
    public List<StudentDto> searchStudentDtos() {
        return studentRepository.findAll().stream()
                .map(StudentDto::of)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {
        studentRepository.findById(id).ifPresent(
                studentRepository::delete
        );
    }
}
