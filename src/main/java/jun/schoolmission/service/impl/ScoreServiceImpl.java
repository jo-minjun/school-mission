package jun.schoolmission.service.impl;

import jun.schoolmission.common.exception.CustomExceptionEntity;
import jun.schoolmission.common.exception.ErrorCode;
import jun.schoolmission.common.exception.NotFoundException;
import jun.schoolmission.domain.dto.score.ScoreDto;
import jun.schoolmission.domain.dto.score.StudentScoreDto;
import jun.schoolmission.domain.dto.score.SubjectScoreDto;
import jun.schoolmission.domain.entity.Student;
import jun.schoolmission.domain.entity.StudentSubject;
import jun.schoolmission.domain.entity.Subject;
import jun.schoolmission.domain.repository.StudentRepository;
import jun.schoolmission.domain.repository.SubjectRepository;
import jun.schoolmission.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScoreServiceImpl implements ScoreService {

    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    @Override
    @Transactional
    public void updateScore(Long studentId, Long subjectId, ScoreDto scoreDto) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        StudentSubject studentSubject = findStudentSubject(studentOptional, studentId, subjectId);

        Student student = studentOptional.get();
        studentSubject.updateScore(scoreDto.getScore());

        studentRepository.save(student);
    }

    @Override
    @Transactional
    public void deleteScore(Long studentId, Long subjectId) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        StudentSubject studentSubject = findStudentSubject(studentOptional, studentId, subjectId);

        Student student = studentOptional.get();
        studentSubject.deleteScore();

        studentRepository.save(student);
    }

    @Override
    public StudentScoreDto findStudentAvgScore(Long studentId) {
        if (studentRepository.existsById(studentId)) {
            Student student = studentRepository.findByIdFetch(studentId).get();

            return StudentScoreDto.of(student.getStudentSubjects());
        }

        throw new NotFoundException(CustomExceptionEntity.builder()
                .errorCode(ErrorCode.STUDENT_NOT_FOUND)
                .explain(studentId.toString())
                .build());
    }

    @Override
    public SubjectScoreDto findSubjectAvgScore(Long subjectId) {
        if (subjectRepository.existsById(subjectId)) {
            Subject subject = subjectRepository.findByIdFetch(subjectId).get();

            return SubjectScoreDto.of(subject.getStudentSubjects());
        }

        throw new NotFoundException(CustomExceptionEntity.builder()
                .errorCode(ErrorCode.SUBJECT_NOT_FOUND)
                .explain(subjectId.toString())
                .build());
    }

    private StudentSubject findStudentSubject(Optional<Student> studentOptional, Long studentId, Long subjectId) {
        List<StudentSubject> studentSubjects = studentOptional.orElseThrow(() ->
                new NotFoundException(CustomExceptionEntity.builder()
                        .errorCode(ErrorCode.STUDENT_NOT_FOUND)
                        .explain(studentId.toString())
                        .build())
        ).getStudentSubjects();

        return studentSubjects.stream().filter(studentSubject -> studentSubject.getSubject().getId().equals(subjectId))
                .findFirst()
                .orElseThrow(() ->
                        new NotFoundException(CustomExceptionEntity.builder()
                                .errorCode(ErrorCode.SUBJECT_NOT_FOUND)
                                .explain(subjectId.toString())
                                .build())
                );
    }
}
