package com.point.hr.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PerSocNumConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD}) // INFO: Where can be applied
@Retention(RetentionPolicy.RUNTIME) // INFO: Retain in the Java class and process it at runtime
public @interface PersonSocialNumber {

    String value() default "666";

    String message() default "must start with 666";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {}; // INFO: Provide custom details about validation failure e.g. error code
}
