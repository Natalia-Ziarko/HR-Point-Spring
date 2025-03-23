package com.point.hr.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ColumnLengthValidator.class)
// Retrieve max column lengths
public @interface ColumnLength {

    String tableName();
    String columnName();
    String message() default "invalid length";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
