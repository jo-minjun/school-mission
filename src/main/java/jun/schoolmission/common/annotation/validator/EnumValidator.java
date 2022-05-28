package jun.schoolmission.common.annotation.validator;

import jun.schoolmission.common.annotation.Enum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<Enum, String> {

    private Enum annotation;

    @Override
    public void initialize(Enum constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        var enumValues = this.annotation.enumClass().getEnumConstants();

        for (var enumValue : enumValues) {
            if (annotation.ignoreCase()) {
                if (value.equalsIgnoreCase(enumValue.toString())) {
                    return true;
                }
            } else {
                if (value.equals(enumValue.toString())) {
                    return true;
                }
            }
        }

        return false;
    }
}
