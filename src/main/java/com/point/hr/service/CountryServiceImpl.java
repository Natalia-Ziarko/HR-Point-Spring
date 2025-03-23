package com.point.hr.service;

import com.point.hr.entity.Country;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CountryServiceImpl implements CountryService {

    private final EntityManager entityManager;

    @Autowired
    public CountryServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Country> findByName(String theName) {
        TypedQuery<Country> theQuery = entityManager.createQuery("FROM Country WHERE ctName=:theName", Country.class);

        theQuery.setParameter("theName", theName);

        return theQuery.getResultList();
    }

    @Override
    public Country findById(Integer theId) {
        return entityManager.find(Country.class, theId);
    }

    @Override
    public List<Country> findAll() {
        TypedQuery<Country> theQuery = entityManager.createQuery("FROM Country", Country.class);

        return theQuery.getResultList();
    }
}
