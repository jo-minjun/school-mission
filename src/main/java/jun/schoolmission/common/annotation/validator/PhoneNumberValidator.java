package jun.schoolmission.common.annotation.validator;

import jun.schoolmission.common.annotation.PhoneNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    private PhoneNumber annotation;

    @Override
    public void initialize(PhoneNumber constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern pattern = null;

        if (annotation.hyphen()) {
            pattern = Pattern.compile("\\d{3}-\\d{4}-\\d{4}");
        } else {
            pattern = Pattern.compile("\\d{11}");
        }

        return pattern.matcher(value).matches();
    }
}
