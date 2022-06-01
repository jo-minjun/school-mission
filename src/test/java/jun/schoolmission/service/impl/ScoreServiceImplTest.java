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

    final Long id = 1L;

    @BeforeEach
    void before_each() {
        Long studentId = studentService.createStudent(StudentDto.builder()
                .id(id)
                .name("student")
                .age(18)
                .schoolType(SchoolType.HIGH.name())
                .phoneNumber("010-0000-0000")
                .build()
        );

        subjectService.createSubject(
                SubjectDto.builder()
                        .id(id)
                        .name("subject")
                        .build()
        );

        em.flush();
        em.clear();
        assertThat(studentRepository.findById(studentId).get().getStudentSubjects())
                .isNotEmpty();
    }

    @Test
    @DisplayName(value = "Score 생성")
    void create_score() {
        // given
        int score = 100;
        ScoreDto scoreDto = ScoreDto.builder()
                .score(score)
                .build();

        // when
        Long scoreId = scoreService.updateScore(id, id, scoreDto);
        List<StudentSubject> studentSubjects = studentRepository.findById(id).get().getStudentSubjects();
        Score savedScore = studentSubjects.stream().filter(studentSubject ->
                        studentSubject.getStudent().getId().equals(id)
                )
                .findFirst()
                .get().getScore();

        // then
        assertThat(savedScore.getId()).isEqualTo(scoreId);
        assertThat(savedScore.getScore()).isEqualTo(score);
    }

    @Test
    @DisplayName(value = "Score 업데이트")
    void update_score() {
        // given
        ScoreDto scoreDto1 = ScoreDto.builder()
                .score(90)
                .build();
        scoreService.updateScore(id, id, scoreDto1);

        int score = 100;
        ScoreDto scoreDto2 = ScoreDto.builder()
                .score(score)
                .build();

        // when
        Long scoreId = scoreService.updateScore(id, id, scoreDto2);
        List<StudentSubject> studentSubjects = studentRepository.findById(id).get().getStudentSubjects();
        Score savedScore = studentSubjects.stream().filter(studentSubject ->
                        studentSubject.getStudent().getId().equals(id)
                )
                .findFirst()
                .get().getScore();

        // then
        assertThat(savedScore.getId()).isEqualTo(scoreId);
        assertThat(savedScore.getScore()).isEqualTo(score);
    }
}