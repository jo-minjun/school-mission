package jun.schoolmission.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jun.schoolmission.common.exception.AlreadyExistException;
import jun.schoolmission.common.exception.CustomExceptionEntity;
import jun.schoolmission.common.exception.ErrorCode;
import jun.schoolmission.domain.dto.subject.SubjectDto;
import jun.schoolmission.domain.dto.subject.SubjectInputDto;
import jun.schoolmission.domain.entity.Subject;
import jun.schoolmission.service.SubjectService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
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

import static jun.schoolmission.common.exception.ErrorCode.ALREADY_EXIST_SUBJECT;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = SubjectController.class)
@AutoConfigureMockMvc
@DisplayName(value = "SubjectController 테스트")
class SubjectControllerTest {

    final String rootUrl = "/subjects";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    SubjectService subjectService;

    @Test
    @DisplayName(value = "Subject 등록 성공")
    void create_subject_success() throws Exception {
        // given
        Long id = 1L;
        SubjectInputDto subjectInputDto = SubjectInputDto.builder()
                .subject(SubjectDto.builder()
                        .name("abㅏr가나12")
                        .build()
                )
                .build();
        when(subjectService.createSubject(any())).thenReturn(id);

        // when
        ResultActions request = mockMvc.perform(post(rootUrl)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(subjectInputDto))
        );

        // then
        request.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", rootUrl + "/" + id))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.error").isEmpty());
    }

    @Test
    @DisplayName(value = "Subejct 등록 실패 - 이미 존재")
    void create_subject_fail_duplicated() throws Exception {
        // given
        String name = "abㅏr가나12";
        SubjectInputDto subjectInputDto = SubjectInputDto.builder()
                .subject(SubjectDto.builder()
                        .name(name)
                        .build()
                )
                .build();

        CustomExceptionEntity entity = CustomExceptionEntity.builder()
                .errorCode(ALREADY_EXIST_SUBJECT)
                .explain(name)
                .build();
        when(subjectService.createSubject(any())).thenThrow(new AlreadyExistException(entity));

        // when
        ResultActions request = mockMvc.perform(post(rootUrl)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(subjectInputDto))
        );

        // then
        request.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.error").isNotEmpty())
                .andExpect(jsonPath("$.error.code", equalTo(ALREADY_EXIST_SUBJECT.toString())))
                .andExpect(jsonPath("$.error.message",
                        equalTo(entity.getExplainMessage())
                ));
    }

    @ParameterizedTest(name = "name : {0}")
    @ValueSource(strings = {"", "testtesttest1", "test1234567890가나다라", "12345가나다라마바사아자차카타파하"})
    @DisplayName(value = "Subject 등록 실패 - 옳지 않은 name")
    void create_subject_fail_name(String name) throws Exception {
        // given
        SubjectInputDto subjectInputDto = SubjectInputDto.builder()
                .subject(SubjectDto.builder()
                        .name(name)
                        .build()
                )
                .build();

        // when
        ResultActions request = mockMvc.perform(post(rootUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(subjectInputDto))
        );

        // then
        request.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.error").isNotEmpty())
                .andExpect(jsonPath("$.error.code", equalTo(ErrorCode.BAD_REQUEST.toString())))
                .andExpect(jsonPath("$.error.message", startsWith(ErrorCode.BAD_REQUEST.getMessage())))
                .andExpect(jsonPath("$.error.message", containsString("name은 1 ~ 12 자만 가능합니다.")));
    }

    @ParameterizedTest(name = "Saved Subject Size : {0}")
    @ValueSource(ints = {0, 5, 25, 125})
    @DisplayName(value = "Subject 조회")
    void search_subjects(int size) throws Exception {
        // given
        List<SubjectDto> subjects = new ArrayList<>();
        IntStream.range(0, size).forEach(i ->
                subjects.add(SubjectDto.of(create_subject(i)))
        );

        when(subjectService.searchSubjectDtos()).thenReturn(subjects);

        // when
        ResultActions request = mockMvc.perform(get(rootUrl));

        // then
        request.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.subjects").isArray())
                .andExpect(jsonPath("$.data.subjects.length()", equalTo(size)));
    }

    Subject create_subject(int i) {
        return Subject.builder()
                .name("subject" + 1)
                .build();
    }

    @Test
    @DisplayName(value = "Subject 삭제")
    void delete_subject() throws Exception {
        // given
        Long id = 0L;
        doNothing().when(subjectService).deleteSubject(id);

        // when
        ResultActions request = mockMvc.perform(delete(rootUrl + "/" + id));

        // then
        request.andDo(print())
                .andExpect(status().isNoContent());
    }
}