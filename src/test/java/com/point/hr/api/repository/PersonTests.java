package com.point.hr.api.repository;

import com.point.hr.dao.*;
import com.point.hr.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Import(PersonDAOImpl.class)
public class PersonTests {

    @Autowired
    private PersonDAO personDAO;

    @Test
    public void save_retId() {
        //Arrange
        Person newPerson = Person.builder()
                .socialNo("666790")
                .lastName("Ziarko")
                .firstName("Natalia")
                .countryId(1)
                .city("Wien")
                .zipCode("1000")
                .street("X")
                .buildNo("1")
                .apartNo(null)
                .build();

        //Act
        Person savedPerson = personDAO.save(newPerson);

        //Assert
        assertNotNull(savedPerson.getId(), "Person ID should not be null");
        assertTrue(savedPerson.getId() > 0, "Person ID should be greater than 0");
    }

    @Test
    public void findById_retPerson() {

        //Arrange
        Person findPerson = Person.builder()
                .socialNo("666791")
                .lastName("Test")
                .firstName("Person")
                .countryId(1)
                .city("Wien")
                .zipCode("1000")
                .street("Y")
                .buildNo("2")
                .build();
        Person savedPerson = personDAO.save(findPerson);
        Integer savedId = savedPerson.getId();

        // Act
        Person foundPerson = personDAO.findById(savedId);

        // Assert
        assertNotNull(foundPerson, "Found person should not be null");
        assertEquals(savedId, foundPerson.getId(), "Person IDs should match");
        assertEquals(findPerson.getSocialNo(), foundPerson.getSocialNo(), "Social numbers should match");
        assertEquals(findPerson.getLastName(), foundPerson.getLastName(), "Last names should match");
        assertEquals(findPerson.getFirstName(), foundPerson.getFirstName(), "First names should match");
    }

    @Test
    public void findById_retNull() {

        // Act
        Integer notExistPersonId = 0;
        Person foundPerson = personDAO.findById(notExistPersonId);

        // Assert
        assertNull(foundPerson, "Should return null for non-existent ID");
    }

    @Test
    public void findByLastName_retPersonList() {

        // Arrange
        personDAO.deleteAll();

        List<Person> people = Arrays.asList(
                Person.builder().socialNo("666794").lastName("Ziarko").firstName("Natalia").countryId(1).city("Wien").zipCode("1100").street("Schleiergasse").buildNo("2").build(),
                Person.builder().socialNo("666795").lastName("Ziarko").firstName("Aneta").countryId(1).city("Wien").zipCode("1100").street("Schleiergasse").buildNo("2").build(),
                Person.builder().socialNo("666796").lastName("Smith").firstName("John").countryId(1).city("Wien").zipCode("1100").street("Schleiergasse").buildNo("2").build()
        );
        personDAO.saveAll(people);

        String personSurname = "Ziarko";

        // Act
        List<Person> foundPeople = personDAO.findByLastName(personSurname)
                .stream()
                .sorted(Comparator.comparing(Person::getId))
                .toList();

        // Assert
        assertEquals(2, foundPeople.size(), "Should find exactly 2 people with last name 'Ziarko'");
        assertTrue(foundPeople.stream().allMatch(p -> "Ziarko".equals(p.getLastName())), "All found people should have last name 'Ziarko'");
    }

    @Test
    public void update_retPerson() {
        // Arrange
        Person person = Person.builder()
                .socialNo("666798")
                .lastName("Ziarko")
                .firstName("Natalia")
                .countryId(1)
                .city("Wien")
                .zipCode("1100")
                .street("Schleiergasse")
                .buildNo("2")
                .build();
        Person savedPerson = personDAO.save(person);
        Integer personId = savedPerson.getId();

        // Act
        Person personToUpdate = personDAO.findById(personId);
        personToUpdate.setLastName("Ziarkos");
        personToUpdate.setFirstName("Aneta");
        personDAO.update(personToUpdate);

        // Assert
        Person updatedPerson = personDAO.findById(personId);
        assertEquals("Ziarkos", updatedPerson.getLastName(), "Last name should be updated");
        assertEquals("Aneta", updatedPerson.getFirstName(), "First name should be updated");
        assertEquals(personId, updatedPerson.getId(), "ID should remain the same");
    }

    @Test
    public void deleteById_retNull() {
        // Arrange
        Person person = Person.builder()
                .socialNo("666798")
                .lastName("Ziarko")
                .firstName("Natalia")
                .countryId(1)
                .city("Wien")
                .zipCode("1100")
                .street("Schleiergasse")
                .buildNo("2")
                .build();
        Person savedPerson = personDAO.save(person);
        Integer personId = savedPerson.getId();

        // Act
        personDAO.deleteById(personId);

        // Assert
        Person deletedPerson = personDAO.findById(personId);
        assertNull(deletedPerson, "Person should be null after deletion");
    }

    @Test
    public void saveAll_retPersonList() {
        // Arrange
        List<Person> people = Arrays.asList(
                Person.builder().socialNo("666794").lastName("Ziarko").firstName("Natalia").countryId(1).city("Wien").zipCode("1100").street("Schleiergasse").buildNo("2").build(),
                Person.builder().socialNo("666795").lastName("Ziarko").firstName("Aneta").countryId(1).city("Wien").zipCode("1100").street("Schleiergasse").buildNo("2").build(),
                Person.builder().socialNo("666796").lastName("Ziarko").firstName("John").countryId(1).city("Wien").zipCode("1100").street("Schleiergasse").buildNo("2").build()
        );

        // Act
        List<Person> savedPeople = personDAO.saveAll(people);

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
                Person.builder().socialNo("666794").lastName("Ziarko").firstName("Natalia").countryId(1).city("Wien").zipCode("1100").street("Schleiergasse").buildNo("2").build(),
                Person.builder().socialNo("666795").lastName("Smith").firstName("John").countryId(1).city("Wien").zipCode("1100").street("Schleiergasse").buildNo("2").build()
        );
        personDAO.saveAll(people);

        // Act
        List<Person> allPeople = personDAO.findAll();

        // Assert
        assertEquals(2, allPeople.size(), "Should return all saved people");
    }

    @Test
    public void deleteAll_retPeopleNo() {
        // Arrange
        List<Person> people = Arrays.asList(
                Person.builder().socialNo("666794").lastName("Ziarko").firstName("Natalia").countryId(1).city("Wien").zipCode("1100").street("Schleiergasse").buildNo("2").build(),
                Person.builder().socialNo("666795").lastName("Ziarko").firstName("Aneta").countryId(1).city("Wien").zipCode("1100").street("Schleiergasse").buildNo("2").build()
        );
        personDAO.saveAll(people);

        // Act
        int deletedCount = personDAO.deleteAll();

        // Assert
        assertEquals(2, deletedCount, "Should delete exactly 2 people");
        assertTrue(personDAO.findByLastName("Ziarko").isEmpty(), "No people with last name 'Ziarko' should remain");
    }

}
