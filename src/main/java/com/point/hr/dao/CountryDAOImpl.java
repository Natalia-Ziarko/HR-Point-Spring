package com.point.hr.dao;

import com.point.hr.model.Country;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CountryDAOImpl implements CountryDAO {

    private final EntityManager entityManager;

    @Autowired
    public CountryDAOImpl(EntityManager entityManager) {
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
