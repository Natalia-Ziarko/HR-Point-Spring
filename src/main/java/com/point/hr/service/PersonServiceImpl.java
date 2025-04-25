package com.point.hr.service;

import com.point.hr.entity.Person;
import com.point.hr.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    @Transactional
    public Person save(Person thePerson) {
        return personRepository.save(thePerson);
    }

    @Override
    @Transactional
    public List<Person> saveAll(List<Person> thePeople) {
        for (Person p : thePeople) personRepository.save(p);

        return thePeople;
    }

    @Override
    public Optional<Person> findById(Integer thePersonId) {
        return personRepository.findById(thePersonId);
    }

    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public List<Person> findByLastName(String theLastName) {
        return personRepository.findByLastName(theLastName);
    }

    @Override
    @Transactional
    public Person update(Person thePerson) {
        return personRepository.save(thePerson);
    }

    @Override
    @Transactional
    public Integer deleteById(Integer thePersonId) {

        personRepository.deleteById(thePersonId);

        return thePersonId;
    }


    @Override
    @Transactional
    public int deleteAllPeople() {
        return personRepository.deleteAllPeople();
    }
}
