package jun.schoolmission.common.exception;

import lombok.*;

@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class CustomExceptionEntity {

    private ErrorCode errorCode;
    private String explain;

    public String getExplainMessage() {
        return errorCode.getMessage() + " [" + explain + "]";
    }
}
