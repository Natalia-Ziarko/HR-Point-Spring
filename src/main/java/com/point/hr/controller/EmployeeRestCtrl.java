package com.point.hr.controller;

import com.point.hr.entity.Employee;
import com.point.hr.api.ApiErrorResponse;
import com.point.hr.api.ApiNotFoundException;
import com.point.hr.service.EmployeeService;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeRestCtrl {

    private final EmployeeService employeeService;

    public EmployeeRestCtrl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    private List<Employee> theEmployees;

    @PostConstruct // INFO: Loading data only once at the start
    public void loadEmployeeList() {
        theEmployees = new ArrayList<>();
        theEmployees = employeeService.findAll();
    }

    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        return theEmployees;
    }

    @GetMapping("/employee/{empId}")
    public Employee getEmployee(@PathVariable Integer empId) {

        Employee employee = employeeService.findById(empId);

        if (employee != null) {
            return employee;
        } else {
            throw new ApiNotFoundException("Employee not found");
        }
    }
}
