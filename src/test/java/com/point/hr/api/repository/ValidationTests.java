package com.point.hr.api.repository;

import com.point.hr.entity.Person;
import com.point.hr.service.PersonService;
import com.point.hr.validation.ColumnLengthRetrieve;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ValidationTests extends BaseTest {

    @Autowired
    private PersonService personService;

    @Autowired
    private ColumnLengthRetrieve columnLengthRetrieve;

    @Test
    public void getColumnLength_retNotEquals() {

        // Arrange
        columnLengthRetrieve.init();

        // Act
        int maxLength = ColumnLengthRetrieve.getColumnLength("people", "perFirstName");

        // Assert
        assertNotEquals(ColumnLengthRetrieve.WRONG_COLUMN_LENGTH, maxLength,
                "Column length should be retrieved successfully from the database");
    }

    @Test
    public void columnLength_retThrow() {
        /*
         * Arrange
         * Simulate MySQL column length constraints in the lengthMap to test validation
         * Use reflection to bypass lengthMap encapsulation
         */

        Map<String, Integer> lengthMap = (Map<String, Integer>) ReflectionTestUtils.getField(columnLengthRetrieve, "lengthMap");
        assert lengthMap != null;
        lengthMap.clear();
        lengthMap.put("people.perFirstName", 63); // INFO: Simulate MySQL length

        Person person = PersonTests.buildPerson("666790", "ThisIsAVeryVeryLongNameThatShouldExceedTheDatabaseColumnLength-67chars", "Marko");

        // Act & Assert
        assertThrows(ConstraintViolationException.class, () -> personService.save(person),
                "Expected ConstraintViolationException for firstName exceeding 63-char limit"
        );
    }


}
