package com.example.peaksoftlmsb8.validation.password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordValid, String> {
    @Override
    public boolean isValid(String password,
                           ConstraintValidatorContext constraintValidatorContext) {
        if (password.length() > 5) {
            return password.matches("^[a-zA-Z0-9]{5,14}$");
        } else {
            return false;
        }
    }
}
