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
    private LeaveRequestRepository leaveRequestRepository;

    @Autowired
    private LeaveRequestService leaveRequestService;

    @Autowired
    private LeaveRequestStatusService leaveRequestStatusService;

    @Autowired
    private LeaveTypeRepository leaveTypeRepository;

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
            Person person = personService.findById(perId);
            if (person != null) {
                theModel.addAttribute("person", person);
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

    @RequestMapping("/leaveRequestList")
    public String leaveRequestList(Model theModel) {

        List<LeaveRequest> theLeaveRequestsList = leaveRequestService.showAllLeaveRequests();
        List<LeaveRequestDetailsDTO> listWithStatus = theLeaveRequestsList
                .stream()
                .map(lr -> new LeaveRequestDetailsDTO(lr, leaveRequestStatusService.showLeaveRequestLastStatus(lr.getId())))
                .collect(Collectors.toList());
        theModel.addAttribute("leaveRequestList", listWithStatus);

        return "leaveRequestListView";
    }

    @PostMapping("/addLeaveRequest")
    public String addLeaveRequest(@RequestParam("perId") Integer perId,
                                  Model theModel) {

        LeaveRequest theLeaveRequest = new LeaveRequest();

        if (perId != null) {
            Person thePerson = personService.findById(perId);

            if (thePerson != null) {
                theModel.addAttribute("person", thePerson);
                theLeaveRequest.setPersonId(perId);//.setPerson(thePerson); // FIXME
            } else {
                return "redirect:/people/list"; // INFO: Redirect prevents duplicate submissions
            }
        }

        theModel.addAttribute("perId", perId); // FIXME

        List<LeaveType> leaveTypeList = leaveTypeRepository.findAll();
        //System.out.println("leaveTypeList: " + leaveTypeList); // DEBUG
        theModel.addAttribute("leaveTypeList", leaveTypeList);

        theModel.addAttribute("leaveRequest", theLeaveRequest);

        return "leaveRequestAddForm";
    }

    @PostMapping("/addLeaveRequestProcess")
    public String addLeaveRequestProcess(@Valid @ModelAttribute("leaveRequest") LeaveRequest theLeaveRequest,
                                         BindingResult theBindRes,
                                         Model theModel) {

        Integer perId = theLeaveRequest.getPersonId();

        // DEBUG binding errors to make custom error messages
        //System.out.println("Binding results: " + theBindRes.toString() + "\n");

        if (theBindRes.hasErrors()) {
            theModel.addAttribute("leaveTypeList", leaveTypeRepository.findAll());

            return "leaveRequestAddForm";
        }
        System.out.println("theLeaveRequest: " + theLeaveRequest); // DEBUG

        leaveRequestService.addLeaveRequest(theLeaveRequest);

        return showPersonGet(perId, theModel);
    }

    @PostMapping("/removeLeaveRequestProcess")
    public String removeLeaveRequestProcess(@RequestParam("leaveRequestId") Integer theLeaveRequestId,
                                            Integer perId,
                                            Model theModel) {

        Integer cancelLeaveRequestId = 4; // FIXME

        Optional<LeaveRequest> optionalLeaveRequest = leaveRequestRepository.findById(theLeaveRequestId);
        if (optionalLeaveRequest.isPresent()) {
            LeaveRequest theLeaveRequest = optionalLeaveRequest.get();
            leaveRequestService.changeLeaveRequest(theLeaveRequest, cancelLeaveRequestId, perId);
        }

        return preparePersonView(perId, theModel);
    }
}
