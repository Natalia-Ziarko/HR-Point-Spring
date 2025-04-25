package com.point.hr.controller;

import com.point.hr.entity.Employee;
import com.point.hr.service.CountryService;
import com.point.hr.service.EmployeeService;
import com.point.hr.service.PersonService;
import com.point.hr.entity.Person;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/people")
public class PersonCtrl {

    @Autowired
    private CountryService countryService;

    @Autowired
    private PersonService personService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/addPerson")
    public String addPerson(Model theModel) {

        theModel.addAttribute("thePerson", new Person());
        theModel.addAttribute("countryList", countryService.findAll());

        return "personAddForm";
    }

    @PostMapping("/addPersonProcess")
    public String addPersonProcess(@Valid @ModelAttribute("thePerson") Person thePerson,
                                   BindingResult theBindRes,
                                   Model theModel) {

        // DEBUG binding errors to make custom error messages
        // System.out.println("Binding results: " + theBindRes.toString() + "\n");

        if (theBindRes.hasErrors()) {
            theModel.addAttribute("countryList", countryService.findAll());

            return "personAddForm";
        }

        personService.save(thePerson);

        return "redirect:/people/list"; // INFO: Redirect prevents duplicate submissions
    }

    @RequestMapping("/list")
    public String list(Model theModel) {
        theModel.addAttribute("peopleList", personService.findAll());

        return "peopleListView";
    }

    @PostMapping("/showPerson")
    public String showPerson(@RequestParam("perId") Integer perId,
                             Model model) {

        if (perId != null) {
            Optional<Person> person = personService.findById(perId);

            if (person.isPresent()) {
                model.addAttribute("person", person.get());
            } else {
                return "redirect:/people/list"; // INFO: Redirect prevents duplicate submissions
            }
        }

        List<Employee> theEmployeesList = employeeService.findByManagerId(perId);
        model.addAttribute("employeesList", theEmployeesList);

        return "personDetailsView";
    }

}
