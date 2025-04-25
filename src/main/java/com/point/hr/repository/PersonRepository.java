package com.point.hr.repository;

import com.point.hr.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Integer> {

    Optional<Person> findById(Integer id);

    List<Person> findAll();

    List<Person> findByLastName(String lastName);
}
