package com.example.peaksoftlmsb8.validation.email;

import com.example.peaksoftlmsb8.validation.email.EmailValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<EmailValid, String> {
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null) {
            return false;
        }

        int atIndex = email.indexOf("@");
        int dotComIndex = email.lastIndexOf(".com");

        return atIndex > 0 && dotComIndex == email.length() - 4 && atIndex < dotComIndex;
    }
}
