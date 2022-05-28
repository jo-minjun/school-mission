package jun.schoolmission.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    ALREADY_EXIST_STUDENT("이미 존재하는 학생입니다."),
    ALREADY_EXIST_SUBJECT("이미 존재하는 과목입니다."),

    STUDENT_NOT_FOUND("학생을 찾을 수 없습니다."),
    SUBJECT_NOT_FOUND("과목을 찾을 수 없습니다."),

    BAD_REQUEST("잘못된 요청입니다."),

    SERVER_ERROR("서버 에러입니다.");

    private String message;
}
