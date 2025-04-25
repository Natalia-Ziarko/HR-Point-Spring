package com.point.hr.service;

import java.util.List;
import java.util.Optional;

import com.point.hr.entity.Person;
import jakarta.validation.Valid;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface PersonService {
    Person save(@Valid Person thePerson);

    List<Person> saveAll(List<Person> thePeople);

    Optional<Person> findById(Integer thePersonId);

    List<Person> findAll();

    List<Person> findByLastName(String theLastName);

    Person update(Person thePerson);

    Integer deleteById(Integer thePersonId);

    int deleteAllPeople();  // INFO: returns the number of rows deleted
}
