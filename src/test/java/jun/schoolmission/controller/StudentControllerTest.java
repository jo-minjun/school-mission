package jun.schoolmission.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jun.schoolmission.common.exception.AlreadyExistException;
import jun.schoolmission.common.exception.CustomExceptionEntity;
import jun.schoolmission.common.exception.ErrorCode;
import jun.schoolmission.domain.SchoolType;
import jun.schoolmission.domain.dto.StudentDto;
import jun.schoolmission.domain.dto.StudentInputDto;
import jun.schoolmission.domain.entity.Student;
import jun.schoolmission.service.StudentService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static jun.schoolmission.common.exception.ErrorCode.ALREADY_EXIST_STUDENT;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = StudentController.class)
@AutoConfigureMockMvc
@DisplayName(value = "Student 컨트롤러 테스트")
class StudentControllerTest {

    final String rootUrl = "/students";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    StudentService studentService;

    @ParameterizedTest(name = "schoolType: {0}")
    @EnumSource(value = SchoolType.class)
    @DisplayName(value = "학생 등록 성공")
    void create_student_success(SchoolType schoolType) throws Exception {
        //given
        long id = 1L;

        StudentInputDto studentInputDto = StudentInputDto.builder()
                .student(StudentDto.builder()
                        .id(id)
                        .name("aAzZ이ㅏㄱ10")
                        .age(19)
                        .schoolType(schoolType.toString())
                        .phoneNumber("010-1234-5678")
                        .build()
                )
                .build();

        when(studentService.createStudent(studentInputDto.getStudent())).thenReturn(id);

        //when
        ResultActions request = mockMvc.perform(post(rootUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentInputDto))
        );

        //then
        request.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location","/students/" + id))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.error").isEmpty());
    }

    @Test
    @DisplayName(value = "Student 등록 실패 - 이미 존재")
    void create_student_duplicate() throws Exception {
        //given
        String phoneNumber = "010-1234-5678";
        StudentInputDto studentInputDto = StudentInputDto.builder()
                .student(StudentDto.builder()
                        .id(1L)
                        .name("aAzZ이ㅏㄱ10")
                        .age(19)
                        .schoolType(SchoolType.HIGH.toString())
                        .phoneNumber(phoneNumber)
                        .build()
                )
                .build();

        CustomExceptionEntity entity = CustomExceptionEntity.builder()
                .errorCode(ALREADY_EXIST_STUDENT)
                .explain(phoneNumber)
                .build();
        when(studentService.createStudent(studentInputDto.getStudent()))
                .thenThrow(new AlreadyExistException(entity));

        //when
        ResultActions request = mockMvc.perform(post(rootUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentInputDto))
        );

        //then
        request.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.error").isNotEmpty())
                .andExpect(jsonPath("$.error.code", equalTo(ALREADY_EXIST_STUDENT.toString())))
                .andExpect(jsonPath("$.error.message",
                        equalTo(entity.getExplainMessage())
                ));
    }

    @Test
    @DisplayName(value = "Student 등록 실패 - 옳지 않은 SchoolType")
    void create_student_fail_school_type() throws Exception {
        //given
        String phoneNumber = "010-1234-5678";
        StudentInputDto studentInputDto = StudentInputDto.builder()
                .student(StudentDto.builder()
                        .name("aAzZ이ㅏㄱ10")
                        .age(19)
                        .schoolType("고등학교")
                        .phoneNumber(phoneNumber)
                        .build()
                )
                .build();

        //when
        ResultActions request = mockMvc.perform(post(rootUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentInputDto))
        );

        //then
        request.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.error").isNotEmpty())
                .andExpect(jsonPath("$.error.code", equalTo(ErrorCode.BAD_REQUEST.toString())))
                .andExpect(jsonPath("$.error.message", Matchers.startsWith(ErrorCode.BAD_REQUEST.getMessage())))
                .andExpect(jsonPath("$.error.message", Matchers.containsString(SchoolType.exceptionMessage)));
    }

    @ParameterizedTest(name = "age: {0}")
    @ValueSource(ints = {7, 20})
    @DisplayName(value = "Student 등록 실패 - 옳지 않은 age")
    void create_student_fail_age(int age) throws Exception {
        //given
        String phoneNumber = "010-1234-5678";
        StudentInputDto studentInputDto = StudentInputDto.builder()
                .student(StudentDto.builder()
                        .name("aAzZ이ㅏㄱ10")
                        .age(age)
                        .schoolType(SchoolType.HIGH.toString())
                        .phoneNumber(phoneNumber)
                        .build()
                )
                .build();

        //when
        ResultActions request = mockMvc.perform(post(rootUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentInputDto))
        );

        //then
        request.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.error").isNotEmpty())
                .andExpect(jsonPath("$.error.code", equalTo(ErrorCode.BAD_REQUEST.toString())))
                .andExpect(jsonPath("$.error.message", Matchers.startsWith(ErrorCode.BAD_REQUEST.getMessage())))
                .andExpect(jsonPath("$.error.message", Matchers.containsString("age는 8 ~ 19 만 가능합니다.")));
    }

    @ParameterizedTest(name = "name: {0}")
    @ValueSource(strings = {"", "0123456789ABCDEFGH", "가나다라마바사아자차카타파하0123456789"})
    @DisplayName(value = "Student 등록 실패 - 옳지 않은 name")
    void create_student_fail_name(String name) throws Exception {
        //given
        String phoneNumber = "010-1234-5678";
        StudentInputDto studentInputDto = StudentInputDto.builder()
                .student(StudentDto.builder()
                        .name(name)
                        .age(19)
                        .schoolType(SchoolType.HIGH.toString())
                        .phoneNumber(phoneNumber)
                        .build()
                )
                .build();

        //when
        ResultActions request = mockMvc.perform(post(rootUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentInputDto))
        );

        //then
        request.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.error").isNotEmpty())
                .andExpect(jsonPath("$.error.code", equalTo(ErrorCode.BAD_REQUEST.toString())))
                .andExpect(jsonPath("$.error.message", Matchers.startsWith(ErrorCode.BAD_REQUEST.getMessage())))
                .andExpect(jsonPath("$.error.message", Matchers.containsString("name은 1 ~ 16 자만 가능합니다.")));
    }

    @ParameterizedTest(name = "phoneNumber: {0}")
    @ValueSource(strings = {"", "010", "0101-3403", "010-00000-0000", "010-0000-00000", "010-0000-0000-0"})
    @DisplayName(value = "Student 등록 실패 - 옳지 않은 phoneNumber")
    void create_student_fail_phone_number(String phoneNumber) throws Exception {
        //given
        StudentInputDto studentInputDto = StudentInputDto.builder()
                .student(StudentDto.builder()
                        .name("aAzZ이ㅏㄱ10")
                        .age(19)
                        .schoolType(SchoolType.HIGH.toString())
                        .phoneNumber(phoneNumber)
                        .build()
                )
                .build();

        //when
        ResultActions request = mockMvc.perform(post(rootUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentInputDto))
        );

        //then
        request.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.error").isNotEmpty())
                .andExpect(jsonPath("$.error.code", equalTo(ErrorCode.BAD_REQUEST.toString())))
                .andExpect(jsonPath("$.error.message", Matchers.startsWith(ErrorCode.BAD_REQUEST.getMessage())))
                .andExpect(jsonPath("$.error.message", Matchers.containsString("phoneNumber은 000-0000-0000 형식만 가능합니다.")));
    }

    @Test
    @DisplayName(value = "Student 등록 실패 - 옳지 않은 name, age, SchoolType, phoneNumber")
    void create_student_fail_complex() throws Exception {
        //given
        StudentInputDto studentInputDto = StudentInputDto.builder()
                .student(StudentDto.builder()
                        .name("")
                        .age(7)
                        .schoolType("고등")
                        .phoneNumber("01-0000-0000")
                        .build()
                )
                .build();

        //when
        ResultActions request = mockMvc.perform(post(rootUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentInputDto))
        );

        //then
        request.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.error").isNotEmpty())
                .andExpect(jsonPath("$.error.code", equalTo(ErrorCode.BAD_REQUEST.toString())))
                .andExpect(jsonPath("$.error.message", Matchers.startsWith(ErrorCode.BAD_REQUEST.getMessage())))
                .andExpect(jsonPath("$.error.message", Matchers.containsString("name은 1 ~ 16 자만 가능합니다.")))
                .andExpect(jsonPath("$.error.message", Matchers.containsString("age는 8 ~ 19 만 가능합니다.")))
                .andExpect(jsonPath("$.error.message", Matchers.containsString(SchoolType.exceptionMessage)))
                .andExpect(jsonPath("$.error.message", Matchers.containsString("phoneNumber은 000-0000-0000 형식만 가능합니다.")));
    }

    @ParameterizedTest(name = "Saved Student Size: {0}")
    @ValueSource(ints = {0, 5, 25, 125})
    @DisplayName(value = "Student 조회")
    void search_students(int studentSize) throws Exception {
        // given
        List<StudentDto> studentDtos = new ArrayList<>();
        IntStream.range(0, studentSize).forEach(i ->
                studentDtos.add(StudentDto.of(create_student(i)))
        );

        when(studentService.searchStudentDtos()).thenReturn(studentDtos);

        // when
        ResultActions request = mockMvc.perform(get(rootUrl));

        // then
        request.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.students").isArray())
                .andExpect(jsonPath("$.data.students.length()", equalTo(studentSize)));
    }

    Student create_student(int i) {
        return Student.builder()
                .name("student" + i)
                .age(18)
                .schoolType(SchoolType.HIGH)
                .phoneNumber("000-0000-" + String.format("%04d", i))
                .build();
    }

    @Test
    @DisplayName(value = "student 삭제")
    void delete_student() throws Exception {
        // given
        Long id = 0L;
        doNothing().when(studentService).deleteStudent(id);

        // when
        ResultActions request = mockMvc.perform(delete(rootUrl + "/" + id));

        // then
        request.andDo(print())
                .andExpect(status().isNoContent());
    }
}