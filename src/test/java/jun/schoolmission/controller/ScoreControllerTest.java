package jun.schoolmission.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jun.schoolmission.common.exception.CustomExceptionEntity;
import jun.schoolmission.common.exception.NotFoundException;
import jun.schoolmission.domain.dto.score.ScoreDto;
import jun.schoolmission.domain.dto.score.StudentScoreDto;
import jun.schoolmission.domain.dto.score.SubjectScoreDto;
import jun.schoolmission.domain.dto.student.StudentDto;
import jun.schoolmission.domain.dto.subject.SubjectDto;
import jun.schoolmission.service.ScoreService;
import org.hamcrest.Matchers;
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

import java.util.List;

import static jun.schoolmission.common.exception.ErrorCode.*;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ScoreController.class)
@AutoConfigureMockMvc
@DisplayName(value = "Score 컨트롤러 테스트")
class ScoreControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ScoreService scoreService;

    @Test
    @DisplayName(value = "Score 할당 - 성공")
    void create_score_success() throws Exception {
        // given
        Long id = 1L;
        ScoreDto scoreDto = ScoreDto.builder()
                .score(100)
                .build();

        doNothing().when(scoreService).updateScore(id, id, scoreDto);

        // when
        ResultActions request = mockMvc.perform(post("/students/{studentId}/subjects/{subjectId}/scores", id, id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(scoreDto))
        );

        // then
        request.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.error").isEmpty());
    }

    @ParameterizedTest(name = "score : {0}")
    @ValueSource(ints = {-1, 101})
    @DisplayName(value = "Score 할당 - 값이 유효하지 않은 경우")
    void create_score_fail_score(int score) throws Exception {
        // given
        Long id = 1L;
        ScoreDto scoreDto = ScoreDto.builder()
                .score(score)
                .build();

        doThrow(NotFoundException.class).when(scoreService).updateScore(id, id, scoreDto);

        // when
        ResultActions request = mockMvc.perform(post("/students/{studentId}/subjects/{subjectId}/scores", id, id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(scoreDto))
        );

        // then
        request.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.error.code", Matchers.equalTo(BAD_REQUEST.toString())))
                .andExpect(jsonPath("$.error.message", Matchers.containsString("score는 0 ~ 100만 가능합니다.")));
    }

    @Test
    @DisplayName(value = "Score 수정 - 성공")
    void update_score_success() throws Exception {
        // given
        Long id = 1L;
        ScoreDto scoreDto = ScoreDto.builder()
                .score(100)
                .build();

        doNothing().when(scoreService).updateScore(id, id, scoreDto);

        // when
        ResultActions request = mockMvc.perform(put("/students/{studentId}/subjects/{subjectId}/scores", id, id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(scoreDto))
        );

        // then
        request.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.error").isEmpty());
    }

    @ParameterizedTest(name = "score : {0}")
    @ValueSource(ints = {-1, 101})
    @DisplayName(value = "Score 수정 - 값이 유효하지 않은 경우")
    void update_score_fail_score(int score) throws Exception {
        // given
        Long id = 1L;
        ScoreDto scoreDto = ScoreDto.builder()
                .score(score)
                .build();

        doThrow(NotFoundException.class).when(scoreService).updateScore(id, id, scoreDto);

        // when
        ResultActions request = mockMvc.perform(put("/students/{studentId}/subjects/{subjectId}/scores", id, id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(scoreDto))
        );

        // then
        request.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.error.code", Matchers.equalTo(BAD_REQUEST.toString())))
                .andExpect(jsonPath("$.error.message", Matchers.containsString("score는 0 ~ 100만 가능합니다.")));
    }

    @Test
    @DisplayName(value = "Score 삭제 - 성공")
    void delete_score_success() throws Exception {
        // given
        Long studentId = 1L;
        Long subjectId = 1L;

        doNothing().when(scoreService).deleteScore(studentId, subjectId);

        // when
        ResultActions request = mockMvc.perform(delete("/students/{studentId}/subjects/{subjectId}/scores", studentId, subjectId));

        // then
        request.andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName(value = "Score 삭제 - 실패 - 학생이 없음")
    void delete_score_fail_student() throws Exception {
        // given
        Long studentId = 1L;
        Long subjectId = 1L;

        CustomExceptionEntity entity = CustomExceptionEntity.builder()
                .errorCode(STUDENT_NOT_FOUND)
                .explain(studentId.toString())
                .build();
        doThrow(new NotFoundException(entity)).when(scoreService).deleteScore(studentId, subjectId);

        // when
        ResultActions request = mockMvc.perform(delete("/students/{studentId}/subjects/{subjectId}/scores", studentId, subjectId));

        // then
        request.andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.error.code", Matchers.equalTo(STUDENT_NOT_FOUND.toString())))
                .andExpect(jsonPath("$.error.message", Matchers.containsString(STUDENT_NOT_FOUND.getMessage())));
    }

    @Test
    @DisplayName(value = "Score 삭제 - 실패 - 과목이 없음")
    void delete_score_fail_subject() throws Exception {
        // given
        Long studentId = 1L;
        Long subjectId = 1L;

        CustomExceptionEntity entity = CustomExceptionEntity.builder()
                .errorCode(SUBJECT_NOT_FOUND)
                .explain(subjectId.toString())
                .build();
        doThrow(new NotFoundException(entity)).when(scoreService).deleteScore(studentId, subjectId);

        // when
        ResultActions request = mockMvc.perform(delete("/students/{studentId}/subjects/{subjectId}/scores", studentId, subjectId));

        // then
        request.andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.error.code", Matchers.equalTo(SUBJECT_NOT_FOUND.toString())))
                .andExpect(jsonPath("$.error.message", Matchers.containsString(SUBJECT_NOT_FOUND.getMessage())));
    }

    @Test
    @DisplayName(value = "학생별 평균 점수 조회 - 성공")
    void find_student_score_success() throws Exception {
        // given
        Long studentId = 1L;
        Long subjectId = 1L;
        Integer score = 100;

        List<SubjectDto> subjectDtos = List.of(SubjectDto.builder()
                .id(subjectId)
                .name("subject")
                .score(score)
                .build()
        );

        when(scoreService.findStudentAvgScore(studentId))
                .thenReturn(StudentScoreDto.builder()
                        .averageScore((double) score)
                        .subjects(subjectDtos)
                        .build()
                );

        // when
        ResultActions request = mockMvc.perform(get("/students/{studentId}/average-score", studentId));

        // then
        request.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.averageScore").value(score))
                .andExpect(jsonPath("$.data.subjects").isArray())
                .andExpect(jsonPath("$.data.subjects.length()", equalTo(1)))
                .andExpect(jsonPath("$.error").isEmpty());
    }

    @Test
    @DisplayName(value = "학생별 평균 점수 조회 - 실패 - 학생 없음")
    void find_student_score_fail() throws Exception {
        // given
        Long studentId = 1L;

        CustomExceptionEntity entity = CustomExceptionEntity.builder()
                .errorCode(STUDENT_NOT_FOUND)
                .explain(studentId.toString())
                .build();
        when(scoreService.findStudentAvgScore(studentId)).thenThrow(new NotFoundException(entity));

        // when
        ResultActions request = mockMvc.perform(get("/students/{studentId}/average-score", studentId));

        // then
        request.andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.error.code", equalTo(STUDENT_NOT_FOUND.toString())))
                .andExpect(jsonPath("$.error.message", Matchers.containsString(STUDENT_NOT_FOUND.getMessage())));
    }

    @Test
    @DisplayName(value = "과목별 평균 점수 조회 - 성공")
    void find_subject_score_success() throws Exception {
        // given
        Long studentId = 1L;
        Long subjectId = 1L;
        Integer score = 100;

        List<StudentDto> studentDtos = List.of(StudentDto.builder()
                .id(studentId)
                .name("student")
                .score(score)
                .build()
        );

        when(scoreService.findSubjectAvgScore(subjectId))
                .thenReturn(SubjectScoreDto.builder()
                        .averageScore((double) score)
                        .students(studentDtos)
                        .build()
                );

        // when
        ResultActions request = mockMvc.perform(get("/subjects/{subjectId}/average-score", subjectId));

        // then
        request.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.averageScore").value(score))
                .andExpect(jsonPath("$.data.students").isArray())
                .andExpect(jsonPath("$.data.students.length()", equalTo(1)))
                .andExpect(jsonPath("$.error").isEmpty());
    }

    @Test
    @DisplayName(value = "과목별 평균 점수 조회 - 실패 - 과목 없음")
    void find_subject_score_fail() throws Exception {
        // given
        Long subjectId = 1L;

        CustomExceptionEntity entity = CustomExceptionEntity.builder()
                .errorCode(SUBJECT_NOT_FOUND)
                .explain(subjectId.toString())
                .build();
        when(scoreService.findSubjectAvgScore(subjectId)).thenThrow(new NotFoundException(entity));

        // when
        ResultActions request = mockMvc.perform(get("/subjects/{subjectId}/average-score", subjectId));

        // then
        request.andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.error.code", equalTo(SUBJECT_NOT_FOUND.toString())))
                .andExpect(jsonPath("$.error.message", Matchers.containsString(SUBJECT_NOT_FOUND.getMessage())));
    }
}