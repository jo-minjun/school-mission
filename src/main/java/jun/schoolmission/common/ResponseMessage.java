package jun.schoolmission.common;

import jun.schoolmission.common.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseMessage<T> {

    private T data;
    private Error error;

    public ResponseMessage<T> success(T object) {
        data = object;
        return this;
    }

    public ResponseMessage<T> fail(ErrorCode errorCode) {
        error = Error.builder()
                .code(errorCode.toString())
                .message(errorCode.getMessage())
                .build();
        return this;
    }

    @Builder
    @Getter
    private static class Error {
        private String code;
        private String message;
    }
}
