package jun.schoolmission.service.impl;

import jun.schoolmission.common.exception.AlreadyExistException;
import jun.schoolmission.common.exception.CustomExceptionEntity;
import jun.schoolmission.common.exception.ErrorCode;
import jun.schoolmission.domain.dto.subject.SubjectDto;
import jun.schoolmission.domain.entity.Student;
import jun.schoolmission.domain.entity.Subject;
import jun.schoolmission.domain.repository.StudentRepository;
import jun.schoolmission.domain.repository.SubjectRepository;
import jun.schoolmission.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;

    @Override
    @Transactional
    public Long createSubject(SubjectDto subjectDto) {
        subjectRepository.findByName(subjectDto.getName()).ifPresent(subject -> {
            throw new AlreadyExistException(CustomExceptionEntity.builder()
                    .errorCode(ErrorCode.ALREADY_EXIST_SUBJECT)
                    .explain(subject.getName())
                    .build());
        });

        Subject subject = subjectDto.toEntity();

        List<Student> students = studentRepository.findAll();
        subject.registerStudents(students);

        return subjectRepository.save(subject).getId();
    }

    @Override
    public List<SubjectDto> searchSubjectDtos() {
        return subjectRepository.findAll().stream()
                .map(SubjectDto::of)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteSubject(Long id) {
        subjectRepository.findById(id).ifPresent(
                subjectRepository::delete
        );
    }
}
