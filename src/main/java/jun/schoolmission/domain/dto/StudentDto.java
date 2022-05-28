package jun.schoolmission.domain.dto;

import jun.schoolmission.common.annotation.Enum;
import jun.schoolmission.common.annotation.StudentName;
import jun.schoolmission.domain.SchoolType;
import jun.schoolmission.domain.entity.Student;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import static jun.schoolmission.domain.SchoolType.exceptionMessage;

@Getter
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(of = {"phoneNumber"})
public class StudentDto {

    private Long id;

    @StudentName(message = "name은 한글/영어/숫자만 가능합니다.")
    @Size(min = 1, max = 16, message = "name은 1 ~ 16 자만 가능합니다.")
    private String name;

    @Min(value = 8, message = "age는 8 ~ 19 만 가능합니다.")
    @Max(value = 19, message = "age는 8 ~ 19 만 가능합니다.")
    private Integer age;

    @Enum(enumClass = SchoolType.class, ignoreCase = true, message = exceptionMessage)
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