package jun.schoolmission.service.impl;

import jun.schoolmission.common.exception.AlreadyExistException;
import jun.schoolmission.common.exception.ErrorCode;
import jun.schoolmission.domain.SchoolType;
import jun.schoolmission.domain.dto.StudentDto;
import jun.schoolmission.domain.entity.Student;
import jun.schoolmission.domain.entity.Subject;
import jun.schoolmission.domain.repository.StudentRepository;
import jun.schoolmission.domain.repository.SubjectRepository;
import jun.schoolmission.service.StudentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@DisplayName(value = "StudentService 테스트")
@Transactional
class StudentServiceImplTest {

    @Autowired
    StudentService studentService;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @ParameterizedTest(name = "schoolType: {0}")
    @EnumSource(value = SchoolType.class)
    @DisplayName(value = "학생 등록 성공")
    void create_student_success(SchoolType schoolType) {
        // given
        StudentDto studentDto = StudentDto.builder()
                .name("aAzZ이ㅏㄱ10")
                .age(19)
                .schoolType(schoolType.toString())
                .phoneNumber("010-1234-5678")
                .build();

        int subjectSize = 5;
        IntStream.range(0, 5).forEach(this::create_subject);

        // when
        Long createdId = studentService.createStudent(studentDto);
        Student createdStudent = studentRepository.findById(createdId).get();

        // then
        assertThat(createdId).isNotNull();
        assertThat(createdStudent).isNotNull();
        assertThat(createdStudent.getStudentSubjects().size()).isEqualTo(subjectSize);
    }

    @Test
    @DisplayName(value = "학생 등록 실패 - 이미 존재")
    void create_student_duplicated() {
        // given
        String phoneNumber = "010-1234-5678";
        StudentDto studentDto1 = StudentDto.builder()
                .name("aAzZ이ㅏㄱ10")
                .age(19)
                .schoolType(SchoolType.ELEMENTARY.toString())
                .phoneNumber(phoneNumber)
                .build();

        StudentDto studentDto2 = StudentDto.builder()
                .name("asdfasdfasdf")
                .age(19)
                .schoolType(SchoolType.ELEMENTARY.name())
                .phoneNumber(phoneNumber)
                .build();

        // when
        // then
        studentService.createStudent(studentDto1);
        assertThatThrownBy(() -> studentService.createStudent(studentDto2))
                .isInstanceOf(AlreadyExistException.class)
                .hasMessage(ErrorCode.ALREADY_EXIST_STUDENT.getMessage() + " [" + phoneNumber + "]");
    }

    void create_subject(int i) {
        subjectRepository.save(Subject.builder()
                .name("subject" + i)
                .build()
        );
    }
}