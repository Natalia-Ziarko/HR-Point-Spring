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

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Autowired
    private LeaveTypeService leaveTypeService;

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

    @RequestMapping("/leaveRequestList")
    public String leaveRequestList(@RequestParam(required = false) String keyword,
                                   Model theModel) {

        List<LeaveRequest> theLeaveRequestsList = leaveRequestService.showAllLeaveRequests();

        Stream<LeaveRequest> filteredStream = theLeaveRequestsList.stream();
/**
        if (keyword != null && !keyword.isBlank()) {
            String loweredKeyword = keyword.toLowerCase();

            filteredStream = filteredStream.filter(lr ->
                    lr.getPe().getName().toLowerCase().contains(loweredKeyword) ||
                            lr.getLeaveType().lowe().contains(loweredKeyword)
            );
        }
*/
        List<LeaveRequestDetailsDTO> listWithStatus = filteredStream
                .map(lr -> new LeaveRequestDetailsDTO(lr, leaveRequestStatusService.showLeaveRequestLastStatus(lr.getId())))
                .collect(Collectors.toList());

        theModel.addAttribute("leaveRequestList", listWithStatus);
        theModel.addAttribute("keyword", keyword); // to show current filter in view

        return "leaveRequestListView";
    }

    @GetMapping("/addLeaveRequest")
    public String addLeaveRequest(@RequestParam("perId") Integer perId,
                                  Model theModel) {

        if (perId == null) return "redirect:/people/list"; // INFO: Redirect prevents duplicate submissions

        LeaveRequest theLeaveRequest = new LeaveRequest();
        theLeaveRequest.setPersonId(perId);
        theLeaveRequest.setStartDate(LocalDate.now());
        // System.out.println("StartDate set to: " + theLeaveRequest.getStartDate()); // DEBUG
        theLeaveRequest.setEndDate(LocalDate.now());

        List<LeaveType> leaveTypeList = leaveTypeService.findAll();
        // System.out.println("LeaveTypeList: " + leaveTypeList); // DEBUG

        LeaveType testValLeaveType = new LeaveType(0, "TEST", "TEST", true);

        theModel.addAttribute("leaveTypeList", leaveTypeList != null ? leaveTypeList : List.of(testValLeaveType));
        theModel.addAttribute("leaveRequest", theLeaveRequest);
        // System.out.println("Model leaveRequest: " + theModel.getAttribute("leaveRequest")); // DEBUG
        theModel.addAttribute("perId", perId);

        // System.out.println("Opening a leave request form for person with id: " + perId); // DEBUG

        return "leaveRequestAddForm";
    }

    @PostMapping("/addLeaveRequestProcess")
    public String addLeaveRequestProcess(@Valid @ModelAttribute("leaveRequest") LeaveRequest theLeaveRequest,
                                         BindingResult theBindRes,
                                         Model theModel) {

        Integer perId = theLeaveRequest.getPersonId();
        //System.out.println("Processing a leave request form for person with id: " + perId); // DEBUG

        //System.out.println("Binding results: " + theBindRes.toString() + "\n"); // DEBUG binding errors to make custom error messages

        List<LeaveType> leaveTypes = leaveTypeService.findAll();
        //System.out.println("leaveTypes in POST: " + leaveTypes); // DEBUG
        theModel.addAttribute("leaveTypeList", leaveTypes);

        if (theBindRes.hasErrors()) {
            return "leaveRequestAddForm";
        }
        //System.out.println("theLeaveRequest: " + theLeaveRequest); // DEBUG

        leaveRequestService.addLeaveRequest(theLeaveRequest);

        //return showPersonGet(perId, theModel);
        return "redirect:/people/showPerson?perId=" + perId;
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
