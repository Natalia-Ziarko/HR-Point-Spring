package com.point.hr.dao;

import com.point.hr.entity.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class PersonDAOImpl implements PersonDAO {

    private final EntityManager entityManager;

    @Autowired
    public PersonDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Person save(Person thePerson) {
        entityManager.persist(thePerson);

        return thePerson;
    }

    @Override
    @Transactional
    public List<Person> saveAll(List<Person> thePeople) {
        for (Person p : thePeople) entityManager.persist(p);

        return thePeople;
    }

    @Override
    public Person findById(Integer thePersonId) {
        return entityManager.find(Person.class, thePersonId);
    }

    @Override
    public List<Person> findAll() {
        TypedQuery<Person> theQuery = entityManager.createQuery("FROM Person", Person.class);

        return theQuery.getResultList();
    }

    @Override
    public List<Person> findByLastName(String theLastName) {
        TypedQuery<Person> theQuery = entityManager.createQuery("FROM Person WHERE lastName=:lastName", Person.class);

        theQuery.setParameter("lastName", theLastName);

        return theQuery.getResultList();
    }

    @Override
    @Transactional
    public Person update(Person thePerson) {
        entityManager.merge(thePerson);

        return thePerson;
    }

    @Override
    @Transactional
    public Integer deleteById(Integer thePersonId) {
        Person thePerson = findById(thePersonId);

        entityManager.remove(thePerson);

        return thePersonId;
    }

    @Override
    @Transactional
    public Integer deleteAll() {
        return entityManager.createQuery("DELETE FROM Person").executeUpdate();
    }
}
