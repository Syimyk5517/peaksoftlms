package com.example.peaksoftlmsb8.validation.phoneNumber;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.METHOD,ElementType.ANNOTATION_TYPE})
@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumberValid {
    String message() default "Invalid phone number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
