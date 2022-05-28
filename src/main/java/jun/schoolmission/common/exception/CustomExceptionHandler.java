package jun.schoolmission.common.exception;

import jun.schoolmission.common.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

import static jun.schoolmission.common.exception.ErrorCode.BAD_REQUEST;
import static jun.schoolmission.common.exception.ErrorCode.SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ResponseMessage<?>> handleException(Exception e) {
        log.warn("Server Exception : {}", e.getMessage());
        e.printStackTrace();

        return ResponseEntity.internalServerError().body(new ResponseMessage<>().fail(SERVER_ERROR));
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ResponseMessage<?>> handleException(MethodArgumentNotValidException e) {
        List<ObjectError> errors = e.getAllErrors();
        Iterator<ObjectError> iterator = errors.iterator();

        StringBuilder sb = new StringBuilder();
        while (iterator.hasNext()) {
            ObjectError error = iterator.next();
            sb.append(error.getDefaultMessage());

            if (iterator.hasNext()) {
                sb.append(", ");
            }
        }

        CustomExceptionEntity entity = CustomExceptionEntity.builder()
                .errorCode(BAD_REQUEST)
                .explain(sb.toString())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage<>().fail(entity));
    }

    @ExceptionHandler(value = {AlreadyExistException.class})
    public ResponseEntity<ResponseMessage<?>> handleException(AlreadyExistException e) {
        CustomExceptionEntity entity = e.getEntity();
        logClientException(e, entity);

        return ResponseEntity.badRequest().body(new ResponseMessage<>().fail(entity));
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<ResponseMessage<?>> handleException(NotFoundException e) {
        CustomExceptionEntity entity = e.getEntity();
        logClientException(e, entity);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage<>().fail(entity));
    }

    private void logClientException(RuntimeException e, CustomExceptionEntity entity) {
        log.warn("Handle {} : {}", e.getClass().getSimpleName(), entity.getExplainMessage());
    }
}
