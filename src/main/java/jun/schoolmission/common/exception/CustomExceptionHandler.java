package jun.schoolmission.common.exception;

import jun.schoolmission.common.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {AlreadyExistException.class})
    public ResponseEntity<ResponseMessage<?>> handleAlreadyExistException(AlreadyExistException e) {
        ErrorCode errorCode = e.getErrorCode();
        logException(e, errorCode);

        return ResponseEntity.badRequest().body(new ResponseMessage<>().fail(errorCode));
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<ResponseMessage<?>> handleNotFoundException(NotFoundException e) {
        ErrorCode errorCode = e.getErrorCode();
        logException(e, errorCode);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage<>().fail(errorCode));
    }

    private void logException(RuntimeException e, ErrorCode errorCode) {
        log.warn("Handle {} : {}", e.getClass().getSimpleName(), errorCode.toString());
    }
}
