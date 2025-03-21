package com.point.hr.dao;

import java.util.List;

import com.point.hr.model.Person;
import jakarta.validation.Valid;

public interface PersonDAO {
    Person save(@Valid Person thePerson);

    List<Person> saveAll(List<Person> thePeople);

    Person findById(Integer thePersonId);

    List<Person> findAll();

    List<Person> findByLastName(String theLastName);

    Person update(Person thePerson);

    Integer deleteById(Integer thePersonId);

    Integer deleteAll();
}
