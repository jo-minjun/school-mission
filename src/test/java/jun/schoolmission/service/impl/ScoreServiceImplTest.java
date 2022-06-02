package jun.schoolmission.service.impl;

import jun.schoolmission.domain.SchoolType;
import jun.schoolmission.domain.dto.score.ScoreDto;
import jun.schoolmission.domain.dto.student.StudentDto;
import jun.schoolmission.domain.dto.subject.SubjectDto;
import jun.schoolmission.domain.entity.Score;
import jun.schoolmission.domain.entity.StudentSubject;
import jun.schoolmission.domain.repository.StudentRepository;
import jun.schoolmission.service.ScoreService;
import jun.schoolmission.service.StudentService;
import jun.schoolmission.service.SubjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@DisplayName(value = "ScoreService 테스트")
class ScoreServiceImplTest {

    @Autowired
    ScoreService scoreService;

    @Autowired
    StudentService studentService;

    @Autowired
    SubjectService subjectService;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    EntityManager em;

    Long studentId;
    Long subjectId;

    @BeforeEach
    void before_each() {
        studentId = studentService.createStudent(StudentDto.builder()
                .name("student")
                .age(18)
                .schoolType(SchoolType.HIGH.name())
                .phoneNumber("010-0000-0000")
                .build()
        );

        subjectId = subjectService.createSubject(
                SubjectDto.builder()
                        .name("subject")
                        .build()
        );
    }

    @Test
    @DisplayName(value = "Score 생성")
    void create_score() {
        // given
        int score = 100;
        ScoreDto scoreDto = ScoreDto.builder()
                .score(score)
                .build();
        em.flush();
        em.clear();

        // when
        scoreService.updateScore(studentId, subjectId, scoreDto);
        List<StudentSubject> studentSubjects = studentRepository.findById(studentId).get().getStudentSubjects();
        Score savedScore = studentSubjects.stream().filter(studentSubject ->
                        studentSubject.getSubject().getId().equals(subjectId)
                )
                .findFirst()
                .get().getScore();

        // then
        assertThat(savedScore.getScore()).isEqualTo(score);
    }

    @Test
    @DisplayName(value = "Score 업데이트")
    void update_score() {
        // given
        ScoreDto scoreDto1 = ScoreDto.builder()
                .score(90)
                .build();
        em.flush();
        em.clear();
        scoreService.updateScore(studentId, subjectId, scoreDto1);

        int score = 100;
        ScoreDto scoreDto2 = ScoreDto.builder()
                .score(score)
                .build();

        // when
        scoreService.updateScore(studentId, subjectId, scoreDto2);
        List<StudentSubject> studentSubjects = studentRepository.findById(studentId).get().getStudentSubjects();
        Score savedScore = studentSubjects.stream().filter(studentSubject ->
                        studentSubject.getSubject().getId().equals(subjectId)
                )
                .findFirst()
                .get().getScore();

        // then
        assertThat(savedScore.getScore()).isEqualTo(score);
    }
}