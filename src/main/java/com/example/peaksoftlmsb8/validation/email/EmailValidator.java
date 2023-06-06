package com.example.peaksoftlmsb8.validation.email;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<EmailValid, String> {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9]+@[A-Za-z]+\\.com$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
            return email != null && EMAIL_PATTERN.matcher(email).matches() && countOccurrences(email) == 1;
        }

        private static int countOccurrences (String text){
            int count = 0;
            for (char c : text.toCharArray()) {
                if (c == '@') {
                    count++;
                }
            }
            return count;
        }
    }
