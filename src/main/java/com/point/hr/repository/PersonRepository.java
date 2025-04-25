package com.point.hr.repository;

import com.point.hr.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Integer> {

    Optional<Person> findById(Integer id);

    List<Person> findAll();

    List<Person> findByLastName(String lastName);

    @Modifying
    @Query("DELETE FROM Person")
    int deleteAllPeople();  // INFO: returns the number of rows deleted
}
