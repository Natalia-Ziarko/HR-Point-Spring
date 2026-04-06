package com.point.hr.api.repository;

import com.point.hr.entity.*;
import com.point.hr.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTests extends BaseTest {

    @Autowired
    private PersonService personService;

    // Reusable factory method - eliminates repeated builder boilerplate
    private Person buildPerson(String socialNo, String firstName, String lastName) {
        return Person.builder()
                .socialNo(socialNo)
                .firstName(firstName)
                .lastName(lastName)
                .countryId(1)
                .city("Wien")
                .zipCode("1100")
                .street("Alpengasse")
                .buildNo("3")
                .build();
    }

    @Test
    public void save_retId() {

        //Arrange & Act
        Person savedPerson = personService.save(buildPerson("666790", "Natalia", "Marko"));

        //Assert
        assertNotNull(savedPerson.getId(), "Person ID should not be null");
        assertTrue(savedPerson.getId() > 0, "Person ID should be greater than 0");
    }

    @Test
    public void findById_retPerson() {

        //Arrange
        Person savedPerson = personService.save(buildPerson("666791", "Person", "Test"));
        Integer savedPersonId = savedPerson.getId();

        // Act
        Person foundPerson = personService.findById(savedPersonId);

        // Assert
        assertNotNull(foundPerson, "Found person should be present");

        assertEquals(savedPersonId, foundPerson.getId(), "Person IDs should match");
        assertEquals(savedPerson.getSocialNo(), foundPerson.getSocialNo(), "Social numbers should match");
        assertEquals(savedPerson.getLastName(), foundPerson.getLastName(), "Last names should match");
        assertEquals(savedPerson.getFirstName(), foundPerson.getFirstName(), "First names should match");
    }

    @Test
    public void findById_retNull() {

        // Act
        Integer notExistPersonId = 0;
        Person foundPerson = personService.findById(notExistPersonId);

        // Assert
        assertNull(foundPerson, "Person should not be present");
    }

    @Test
    public void findByLastName_retPersonList() {

        // Arrange
        List<Person> people = Arrays.asList(
                buildPerson("666794", "Natalia", "Marko"),
                buildPerson("666795", "Aneta", "Marko"),
                buildPerson("666796", "John", "Smith")
        );
        personService.saveAll(people);

        String personSurname = "Marko";

        // Act
        List<Person> foundPeople = personService.findByLastName(personSurname)
                .stream()
                .sorted(Comparator.comparing(Person::getId))
                .toList();

        // Assert
        assertEquals(2, foundPeople.size(), "Should find exactly 2 people with last name 'Marko'");
        assertTrue(foundPeople.stream().allMatch(p -> "Marko".equals(p.getLastName())), "All found people should have last name 'Marko'");
    }

    @Test
    public void update_retPerson() {
        // Arrange
        Person savedPerson = personService.save(buildPerson("666798", "Natalia", "Marko"));
        Integer savedPersonId = savedPerson.getId();

        // Act
        Person personToUpdate = personService.findById(savedPersonId);
        personToUpdate.setLastName("Markos");
        personToUpdate.setFirstName("Aneta");
        personService.save(personToUpdate);

        // Assert
        Person updatedPerson = personService.findById(savedPersonId);
        assertEquals("Markos", updatedPerson.getLastName(), "Last name should be updated");
        assertEquals("Aneta", updatedPerson.getFirstName(), "First name should be updated");
        assertEquals(savedPersonId, updatedPerson.getId(), "ID should remain the same");
    }

    @Test
    public void deleteById_retNull() {
        // Arrange
        Person savedPerson = personService.save(buildPerson("666798", "Natalia", "Marko"));
        Integer savedPersonId = savedPerson.getId();

        // Act
        personService.deleteById(savedPersonId);

        // Assert
        Person deletedPerson = personService.findById(savedPersonId);
        assertNull(deletedPerson, "Person should be null after deletion");
    }

    @Test
    public void saveAll_retPersonList() {
        // Arrange
        List<Person> people = Arrays.asList(
                buildPerson("666794", "Natalia", "Marko"),
                buildPerson("666795", "Aneta", "Marko"),
                buildPerson("666796", "John", "Marko")
        );

        // Act
        List<Person> savedPeople = personService.saveAll(people);

        // Assert
        assertNotNull(savedPeople, "Returned list should not be null");
        assertEquals(3, savedPeople.size(), "Should return the same number of people as input");
        assertTrue(savedPeople.stream().allMatch(p -> p.getId() != null && p.getId() > 0),
                "All saved people should have valid IDs");

        for (int i = 0; i < people.size(); i++) {
            Person inputPerson = people.get(i);
            Person savedPerson = savedPeople.get(i);
            assertEquals(inputPerson.getSocialNo(), savedPerson.getSocialNo(), "Social numbers should match");
            assertEquals(inputPerson.getLastName(), savedPerson.getLastName(), "Last names should match");
            assertEquals(inputPerson.getFirstName(), savedPerson.getFirstName(), "First names should match");
        }
    }

    @Test
    public void findAll_retPersonList() {
        // Arrange
        List<Person> people = Arrays.asList(
                buildPerson("666794", "Natalia", "Marko"),
                buildPerson("666795", "John", "Smith")
        );
        personService.saveAll(people);

        // Act
        List<Person> allPeople = personService.findAll();

        // Assert
        assertEquals(2, allPeople.size(), "Should return all saved people");
    }

    @Test
    public void deleteAll_retPeopleNo() {
        // Arrange
        List<Person> people = Arrays.asList(
                buildPerson("666794", "Natalia", "Marko"),
                buildPerson("666795", "Aneta", "Marko")
        );
        personService.saveAll(people);

        // Act
        int deletedCount = personService.deleteAllPeople();

        // Assert
        assertEquals(2, deletedCount, "Should delete exactly 2 people");
        assertTrue(personService.findByLastName("Marko").isEmpty(), "No people with last name 'Marko' should remain");
    }

}
