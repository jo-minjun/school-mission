package jun.schoolmission.common.annotation;

import jun.schoolmission.common.annotation.validator.NameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {NameValidator.class})
@Target(value = {ElementType.FIELD, ElementType.PARAMETER})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Name {

    String message() default "Invalid Student Name Format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
