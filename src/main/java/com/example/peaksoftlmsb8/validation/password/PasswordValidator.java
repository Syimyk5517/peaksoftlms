package com.example.peaksoftlmsb8.validation.password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordValid, String> {
    public boolean isValid(String password, int minLength, boolean requireLetter, boolean requireSymbol, boolean requireDigit) {
        if (password == null || password.length() < minLength) {
            return false;
        }

        boolean hasLetter = false;
        boolean hasSymbol = false;
        boolean hasDigit = false;

        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                if (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.CYRILLIC || Character.isLetter(c)) {
                    hasLetter = true;
                }
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else {
                hasSymbol = true;
            }
        }

        boolean letterRequirementMet = !requireLetter || hasLetter;
        boolean symbolRequirementMet = !requireSymbol || hasSymbol;
        boolean digitRequirementMet = !requireDigit || hasDigit;

        return letterRequirementMet && symbolRequirementMet && digitRequirementMet;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}
