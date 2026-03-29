package com.point.hr.controller;

import com.point.hr.dto.LeaveRequestDetailsDTO;
import com.point.hr.entity.*;
import com.point.hr.repository.LeaveRequestRepository;
import com.point.hr.repository.LeaveTypeRepository;
import com.point.hr.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/people")
public class PersonCtrl {

    @Autowired
    private CountryService countryService;

    @Autowired
    private PersonService personService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private UserService userService;

    @Autowired
    private LeaveRequestStatusService leaveRequestStatusService;

    @Autowired
    private LeaveTypeRepository leaveTypeRepository;

    @Autowired
    private LeaveRequestService leaveRequestService;


    @GetMapping("/addPerson")
    public String addPerson(Model theModel) {

        theModel.addAttribute("thePerson", new Person());
        theModel.addAttribute("countryList", countryService.findAll());
        System.out.println("countryList: " + countryService.findAll()); // DEBUG

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
        List<Person> thePeople = personService.findAll();
        theModel.addAttribute("peopleList", thePeople);

        Map<Integer, Boolean> userExistenceMap = new HashMap<>();
        for (Person person : thePeople) {
            Optional<User> userOpt = userService.findByPersonId(person.getId());
            userExistenceMap.put(person.getId(), userOpt.isPresent());
        }
        theModel.addAttribute("ifUserExists", userExistenceMap);

        return "peopleListView";
    }

    // INFO: Shared person view logic
    private String preparePersonView(Integer perId, Model theModel) {
        if (perId != null) {
            // System.out.println("Fetching person with id: " + perId); // DEBUG
            Person person = personService.findById(perId);
            // System.out.println("Person fetched: " + person); // DEBUG
            if (person != null) {
                theModel.addAttribute("person", person);
                theModel.addAttribute("perId", perId);
            } else {
                return "redirect:/people/list";
            }
        }

        List<Employee> theEmployeesList = employeeService.findByManagerId(perId);
        theModel.addAttribute("employeesList", theEmployeesList);

        List<LeaveRequest> theLeaveRequestsList = leaveRequestService.showPersonLeaveRequests(perId);
        List<LeaveRequestDetailsDTO> listWithStatus = theLeaveRequestsList.stream()
                .map(lr -> new LeaveRequestDetailsDTO(lr, leaveRequestStatusService.showLeaveRequestLastStatus(lr.getId())))
                .collect(Collectors.toList());
        theModel.addAttribute("leaveRequestList", listWithStatus);

        return "personDetailsView";
    }

    @PostMapping("/showPerson")
    public String showPersonPost(@RequestParam("perId") Integer perId,
                                 Model theModel) {
        return preparePersonView(perId, theModel);
    }

    @GetMapping("/showPerson")
    public String showPersonGet(@RequestParam("perId") Integer perId,
                                Model theModel) {
        return preparePersonView(perId, theModel);
    }
}
