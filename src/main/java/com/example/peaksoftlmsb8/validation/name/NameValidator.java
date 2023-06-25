package com.example.peaksoftlmsb8.validation.name;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class NameValidator implements ConstraintValidator<NameValid, String> {
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-ZА-Я][a-zа-я]+$");

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if (name == null || name.length() < 2) {
            return false;
        }

        return NAME_PATTERN.matcher(name).matches();
    }
}