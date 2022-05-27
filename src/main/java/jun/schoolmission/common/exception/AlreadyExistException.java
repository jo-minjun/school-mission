package jun.schoolmission.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlreadyExistException extends RuntimeException {

    private final ErrorCode errorCode;
}
