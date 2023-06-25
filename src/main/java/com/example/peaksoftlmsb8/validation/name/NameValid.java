package com.example.peaksoftlmsb8.validation.name;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.METHOD,ElementType.ANNOTATION_TYPE})
@Documented
@Constraint(validatedBy = NameValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public  @interface NameValid {
    String message() default "Invalid name!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
