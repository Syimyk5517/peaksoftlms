package com.example.peaksoftlmsb8.validation.password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator  implements ConstraintValidator<PasswordValid, String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null || password.length() < 4) {
            return false;
        }

        boolean hasEnglishLetter = false;
        boolean hasEnglishUppercase = false;
        boolean hasRussianLetter = false;
        boolean hasRussianUppercase = false;
        boolean hasSymbol = false;

        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                if (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.CYRILLIC) {
                    hasRussianLetter = true;
                    if (Character.isUpperCase(c)) {
                        hasRussianUppercase = true;
                    }
                } else {
                    hasEnglishLetter = true;
                    if (Character.isUpperCase(c)) {
                        hasEnglishUppercase = true;
                    }
                }
            } else {
                hasSymbol = true;
            }

            if (hasEnglishLetter && hasEnglishUppercase && hasRussianLetter && hasRussianUppercase && hasSymbol) {
                return true;
            }
        }

        return false;
    }
}
