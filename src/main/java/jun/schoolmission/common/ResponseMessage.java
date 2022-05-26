package jun.schoolmission.common;

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

    public ResponseMessage<T> fail(String code, String message) {
        error = Error.builder()
                .code(code)
                .message(message)
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
