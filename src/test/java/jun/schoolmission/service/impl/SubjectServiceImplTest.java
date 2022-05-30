package jun.schoolmission.service.impl;

import jun.schoolmission.common.exception.AlreadyExistException;
import jun.schoolmission.domain.SchoolType;
import jun.schoolmission.domain.dto.subject.SubjectDto;
import jun.schoolmission.domain.entity.Student;
import jun.schoolmission.domain.entity.Subject;
import jun.schoolmission.domain.repository.StudentRepository;
import jun.schoolmission.domain.repository.SubjectRepository;
import jun.schoolmission.service.SubjectService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@DisplayName(value = "SubjectService 테스트")
@Transactional
class SubjectServiceImplTest {

    @Autowired
    SubjectService subjectService;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    StudentRepository studentRepository;

    @Test
    @DisplayName(value = "Subject 등록 성공")
    void create_subject_success() {
        // given
        SubjectDto subjectDto = SubjectDto.builder()
                .name("subject1")
                .build();

        int studentSize = 5;
        IntStream.range(0, studentSize).forEach(this::create_student);

        // when
        Long createdId = subjectService.createSubject(subjectDto);
        Subject createdSubject = subjectRepository.findById(createdId).get();

        // then
        assertThat(createdId).isNotNull();
        assertThat(createdSubject).isNotNull();
        assertThat(createdSubject.getStudentSubjects().size()).isEqualTo(studentSize);
    }

    @Test
    @DisplayName(value = "Subject 등록 실패 - 이미 존재")
    void create_subject_duplicated() {
        // given
        String name = "subject";
        SubjectDto subjectDto1 = SubjectDto.builder()
                .name(name)
                .build();

        SubjectDto subjectDto2 = SubjectDto.builder()
                .name(name)
                .build();

        // when
        // then
        subjectService.createSubject(subjectDto1);
        assertThatThrownBy(() -> subjectService.createSubject(subjectDto2))
                .isInstanceOf(AlreadyExistException.class);
    }

    void create_student(int i) {
        studentRepository.save(Student.builder()
                .name("student" + i)
                .age(8 + i)
                .schoolType(SchoolType.HIGH)
                .phoneNumber("010-0000-000" + i)
                .build()
        );
    }
}