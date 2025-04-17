package com.point.hr.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PerSocNumConstraintValidator implements ConstraintValidator<PersonSocialNumber, String> {

    private String perSocNumPrefix;

    @Override
    public void initialize(PersonSocialNumber personSocialNumber) {
        perSocNumPrefix = personSocialNumber.value();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        boolean result = true;

        if (s != null)
            return s.startsWith(perSocNumPrefix);

        return result;
    }
}
