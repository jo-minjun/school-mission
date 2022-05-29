package jun.schoolmission.domain.dto.student;

import jun.schoolmission.domain.SchoolType;
import jun.schoolmission.domain.entity.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName(value = "StudentDto 테스트")
class StudentDtoTest {

    @Test
    @DisplayName(value = "Student Entity를 DTO로 변환")
    void entity_to_dto() {
        // given
        String name = "name";
        int age = 18;
        SchoolType schoolType = SchoolType.ELEMENTARY;
        String phoneNumber = "010-0000-0000";
        Student studentEntity = Student.builder()
                .name(name)
                .age(age)
                .schoolType(schoolType)
                .phoneNumber(phoneNumber)
                .build();

        // when
        StudentDto studentDto = StudentDto.of(studentEntity);

        // then
        assertThat(studentDto).isInstanceOf(StudentDto.class);
        assertThat(studentDto.getName()).isEqualTo(name);
        assertThat(studentDto.getAge()).isEqualTo(age);
        assertThat(studentDto.getSchoolType()).isEqualToIgnoringCase(schoolType.name());
        assertThat(studentDto.getPhoneNumber()).isEqualTo(phoneNumber);
    }

    @Test
    @DisplayName(value = "StudentDto를 Entity로 변환")
    void dto_to_entity() {
        // given
        String name = "name";
        int age = 18;
        SchoolType schoolType = SchoolType.ELEMENTARY;
        String phoneNumber = "010-0000-0000";
        StudentDto studentDto = StudentDto.builder()
                .name(name)
                .age(age)
                .schoolType(schoolType.name())
                .phoneNumber(phoneNumber)
                .build();

        // when
        Student studentEntity = studentDto.toEntity();

        // then
        assertThat(studentEntity).isInstanceOf(Student.class);
        assertThat(studentEntity.getName()).isEqualTo(name);
        assertThat(studentEntity.getAge()).isEqualTo(age);
        assertThat(studentEntity.getSchoolType()).isEqualTo(schoolType);
        assertThat(studentEntity.getPhoneNumber()).isEqualTo(phoneNumber);
    }
}