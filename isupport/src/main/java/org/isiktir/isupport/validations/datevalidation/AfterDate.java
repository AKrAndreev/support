package org.isiktir.isupport.validations.datevalidation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = AfterDateVAlidator.class)
public @interface AfterDate {
    String message() default "Period should be at least 1 month!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
