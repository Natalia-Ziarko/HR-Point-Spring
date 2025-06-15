package com.point.hr.service;

import com.point.hr.entity.Employee;

import java.util.List;

public interface EmployeeService {

    Employee findById(Integer theId);

    Employee findByPersonId(Integer thePersonId);

    List<Employee> findByManagerId(Integer theManagerId);

    List<Employee> findAll();

    Employee save(Employee theEmployee);

    Integer deleteById(Integer theId);

}
