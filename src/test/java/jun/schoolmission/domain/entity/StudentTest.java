package jun.schoolmission.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName(value = "Student 엔티티 테스트")
class StudentTest {

    @Test
    @DisplayName(value = "Student 빌더")
    void builder() {
        // given
        String name = "student";
        int age = 19;

        // when
        Student student = Student.builder()
                .name(name)
                .age(age)
                .build();

        // then
        assertThat(student).isNotNull();
        assertThat(student.getName()).isEqualTo(name);
        assertThat(student.getAge()).isEqualTo(age);
    }

    @Test
    @DisplayName(value = "Student 같은 id, 다른 필드 Equals")
    public void equals_id() {
        //given
        long id = 1;

        //when
        Student student1 = Student.builder()
                .id(id)
                .name("student1")
                .age(18)
                .phoneNumber("010-0000-0000")
                .build();

        Student student2 = Student.builder()
                .id(id)
                .name("student2")
                .age(19)
                .phoneNumber("010-0000-0001")
                .build();

        //then
        assertThat(student1).isEqualTo(student2);
    }

    @Test
    @DisplayName(value = "Student 다른 id, 같은 필드 Equals")
    public void equals_field() {
        //given
        String name = "student";
        int age = 18;
        String phoneNumber = "010-0000-0000";

        //when
        Student student1 = Student.builder()
                .id(1L)
                .name(name)
                .age(age)
                .phoneNumber(phoneNumber)
                .build();

        Student student2 = Student.builder()
                .id(2L)
                .name(name)
                .age(age)
                .phoneNumber(phoneNumber)
                .build();

        //then
        assertThat(student1).isNotEqualTo(student2);
    }
}