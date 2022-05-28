package jun.schoolmission.common.exception;

import lombok.*;

@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class CustomExceptionEntity {

    private ErrorCode errorCode;
    private String identifier;

    public String getIdentifiedMessage() {
        return errorCode.getMessage() + " [" + identifier + "]";
    }
}
