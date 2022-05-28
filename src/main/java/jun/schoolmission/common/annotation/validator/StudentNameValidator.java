package jun.schoolmission.common.annotation.validator;

import jun.schoolmission.common.annotation.StudentName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class StudentNameValidator implements ConstraintValidator<StudentName, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        Pattern pattern = Pattern.compile("[a-zA-Z가-힣ㄱ-ㅎㅏ-ㅣ0-9]*");
        return pattern.matcher(value).matches();
    }
}
