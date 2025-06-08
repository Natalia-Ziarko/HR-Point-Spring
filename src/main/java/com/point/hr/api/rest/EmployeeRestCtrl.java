package com.point.hr.api.rest;

import com.point.hr.controller.UtilCtrl;
import com.point.hr.entity.Employee;
import com.point.hr.api.ApiNotFoundException;
import com.point.hr.service.EmployeeService;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
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

    @GetMapping("")
    public List<Employee> getEmployees() {
        return theEmployees;
    }

    @GetMapping("/{empId}")
    public Employee getEmployee(@PathVariable Integer empId) {

        Employee employee = employeeService.findById(empId);

        if (employee != null) {
            return employee;
        } else {
            throw new ApiNotFoundException("Employee with ID " + empId + " not found, at " + UtilCtrl.getFormattedTimeStamp(System.currentTimeMillis()));
        }
    }

    @PostMapping("")
    public Employee addEmployee(@RequestBody Employee employee) {

        employee.setId(0); // INFO: Avoiding conflict - update instead of add

        Employee dbEmployee = employeeService.save(employee);

        return dbEmployee;
    }
}
