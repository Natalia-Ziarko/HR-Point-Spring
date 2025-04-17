package com.point.hr.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ColumnLengthValidator implements ConstraintValidator<ColumnLength, String> {

    private String tableName;
    private String columnName;

    @Autowired
    private ColumnLengthRetrieve columnLengthRetrieve;

    public ColumnLengthValidator() {
    }

    @Override
    public void initialize(ColumnLength constraintAnnotation) {
        this.tableName = constraintAnnotation.tableName();
        this.columnName = constraintAnnotation.columnName();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null)
            return true;

        int maxLength = columnLengthRetrieve.getColumnLength(tableName, columnName);
        if (maxLength == ColumnLengthRetrieve.WRONG_COLUMN_LENGTH)
            return true;

        if (value.length() > maxLength) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(String.format("invalid length: max %d chars", maxLength))
                    .addConstraintViolation();

            return false;
        }

        return true;
    }
}
