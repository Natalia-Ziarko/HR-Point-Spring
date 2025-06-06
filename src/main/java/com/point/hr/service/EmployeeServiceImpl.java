package com.point.hr.service;

import com.point.hr.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeServiceImpl implements EmployeeService {

    private final EntityManager entityManager;

    @Autowired
    public EmployeeServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Employee findById(Integer theId) {
        return entityManager.find(Employee.class, theId);
    }

    @Override
    public List<Employee> findByManagerId(Integer theManagerId) {
        TypedQuery<Employee> theQuery = entityManager.createQuery("FROM Employee WHERE managerId = :theManagerId", Employee.class);
        theQuery.setParameter("theManagerId", theManagerId);

        return theQuery.getResultList();
    }

    @Override
    public List<Employee> findAll() {
        TypedQuery<Employee> theQuery = entityManager.createQuery("FROM Employee", Employee.class);

        return theQuery.getResultList();
    }
}
