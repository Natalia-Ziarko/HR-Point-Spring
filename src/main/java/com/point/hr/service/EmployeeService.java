package com.point.hr.service;

import com.point.hr.entity.Employee;

import java.util.List;

public interface EmployeeService {

    Employee findById(Integer theId);

    List<Employee> findByManagerId(Integer theManagerId);

    List<Employee> findAll();

}
