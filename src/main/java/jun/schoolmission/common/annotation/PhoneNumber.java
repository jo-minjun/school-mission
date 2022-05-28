package jun.schoolmission.common.annotation;


import jun.schoolmission.common.annotation.validator.PhoneNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {PhoneNumberValidator.class})
@Target(value = {ElementType.FIELD, ElementType.PARAMETER})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface PhoneNumber {

    String message() default "Invalid Phone Number Format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    boolean hyphen() default true;
}
