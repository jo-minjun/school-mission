package jun.schoolmission.domain.dto;

import jun.schoolmission.domain.SchoolType;
import jun.schoolmission.domain.entity.Student;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(of = {"phoneNumber"})
public class StudentDto {

    private Long id;
    private String name;
    private Integer age;
    private String schoolType;
    private String phoneNumber;

    public static StudentDto of(Student student) {
        return StudentDto.builder()
                .id(student.getId())
                .name(student.getName())
                .age(student.getAge())
                .schoolType(student.getSchoolType().toString())
                .phoneNumber(student.getPhoneNumber())
                .build();
    }

    public static Student toEntity(StudentDto studentDto) {
        return Student.builder()
                .name(studentDto.getName())
                .age(studentDto.getAge())
                .schoolType(SchoolType.of(studentDto.getSchoolType()))
                .phoneNumber(studentDto.getPhoneNumber())
                .build();
    }
}