package com.point.hr.api.repository;

import com.point.hr.entity.Person;
import com.point.hr.entity.User;
import com.point.hr.service.PersonService;
import com.point.hr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Transactional
public class MappingTests {

    @Autowired
    private PersonService personService;

    @Autowired
    private UserService userService;

    @Test
    public void testSavePersonWithRelationship() {

        Person person = Person.builder()
                .socialNo("666790")
                .lastName("Marko")
                .firstName("Natalia")
                .countryId(1)
                .city("Wien")
                .zipCode("1000")
                .street("X")
                .buildNo("1")
                .apartNo(null)
                .build();

        // Save the Person first to generate perId
        //Person savedPerson = personService.save(person);

        //Integer personId = savedPerson.getId(); // Set personId explicitly

        User user = User.builder()
                .password("securePassword123")
                //.personId(personId)
                .ifActive(true)
                .userCreatedDate(LocalDateTime.now())
                .build();

        // Set bidirectional relationship
        person.setUser(user);
        user.setPerson(person);

        // Save the User (or rely on cascade from Person)
       // userService.save(user); // Explicit save, or cascade will handle it

        // Save the Person (cascades to User)
        Person savedPerson = personService.save(person);

        // Retrieve the User
        Optional<User> foundUser = userService.findById(savedPerson.getId());
        assertNotNull(foundUser);
    }
}

