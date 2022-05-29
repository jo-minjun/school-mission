package jun.schoolmission.domain.entity;

import jun.schoolmission.domain.SchoolType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName(value = "Subject 엔티티 테스트")
class SubjectTest {

    @Test
    @DisplayName(value = "Subject 빌더")
    void builder() {
        // given
        String name = "subject";

        // when
        Subject subject = Subject.builder()
                .name(name)
                .build();

        // then
        assertThat(subject).isNotNull();
        assertThat(subject.getName()).isEqualTo(name);
    }

    @Test
    @DisplayName(value = "Subject 같은 id, 다른 필드 Equals")
    public void equals_id() {
        //given
        long id = 1;

        //when
        Subject student1 = Subject.builder()
                .id(id)
                .name("subject1")
                .build();

        Subject student2 = Subject.builder()
                .id(id)
                .name("subject2")
                .build();

        //then
        assertThat(student1).isEqualTo(student2);
    }

    @Test
    @DisplayName(value = "Subject 다른 id, 같은 필드 Equals")
    public void equals_field() {
        //given
        String name = "subject";

        //when
        Subject student1 = Subject.builder()
                .id(1L)
                .name(name)
                .build();

        Subject student2 = Subject.builder()
                .id(2L)
                .name(name)
                .build();

        //then
        assertThat(student1).isNotEqualTo(student2);
    }

    @Test
    @DisplayName(value = "Subject 추가시 StudentSubject 추가")
    void register_subject() {
        // given
        Long id = 1L;
        Subject subject = Subject.builder()
                .id(id)
                .name("subject1")
                .build();

        int studentSize = 5;
        List<Student> students = new ArrayList<>();
        IntStream.range(0, studentSize).forEach(i -> students.add(createStudent(i)));

        // when
        subject.registerStudents(students);

        // then
        assertThat(subject.getStudentSubjects().size()).isEqualTo(studentSize);
        assertThat(subject.getStudentSubjects()).filteredOn(studentSubject ->
                        studentSubject.getSubject().getId().equals(id)
                )
                .hasSize(studentSize);
    }

    Student createStudent(int i) {
        return Student.builder()
                .name("student" + i)
                .age(19)
                .schoolType(SchoolType.HIGH)
                .phoneNumber("010-0000-0000")
                .build();
    }
}