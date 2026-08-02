package com.point.hr.controller;

import com.point.hr.dto.LeaveRequestDetailsDTO;
import com.point.hr.entity.LeaveRequest;
import com.point.hr.entity.LeaveType;
import com.point.hr.repository.LeaveRequestRepository;
import com.point.hr.security.SecurityUtils;
import com.point.hr.service.LeaveRequestService;
import com.point.hr.service.LeaveRequestStatusService;
import com.point.hr.service.LeaveTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/leaveRequests")
public class LeaveRequestCtrl {

    @Autowired
    private LeaveRequestService leaveRequestService;

    @Autowired
    private LeaveRequestStatusService leaveRequestStatusService;

    @Autowired
    private LeaveTypeService leaveTypeService;

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    private final SecurityUtils securityUtils;

    public LeaveRequestCtrl(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    @RequestMapping("")
    public String leaveRequestList(@RequestParam(required = false) String keyword,
                                   Model theModel) {

        List<LeaveRequest> theLeaveRequestsList = leaveRequestService.showAllLeaveRequests();

        Stream<LeaveRequest> filteredStream = theLeaveRequestsList.stream();

         if (keyword != null && !keyword.isBlank()) {
         String loweredKeyword = keyword.toLowerCase();

         filteredStream = filteredStream.filter(lr ->
                 (lr.getPerson() != null && lr.getPerson().getLastName().toLowerCase().contains(loweredKeyword)) ||
                         (lr.getLeaveType() != null && lr.getLeaveType().getLongName().toLowerCase().contains(loweredKeyword))
         );
         }

        List<LeaveRequestDetailsDTO> listWithStatus = filteredStream
                .map(lr -> new LeaveRequestDetailsDTO(lr, leaveRequestStatusService.showLeaveRequestLastStatus(lr.getId())))
                .collect(Collectors.toList());

        theModel.addAttribute("leaveRequestList", listWithStatus);
        theModel.addAttribute("keyword", keyword); // INFO: To show current filter in view

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

        Integer theLoggedInUserId = securityUtils.getLoggedInUserId();
        // System.out.println("Processing a leave request form for person with id: " + perId); // DEBUG

        // System.out.println("Binding results: " + theBindRes.toString() + "\n"); // DEBUG binding errors to make custom error messages

        List<LeaveType> leaveTypes = leaveTypeService.findAll();
        // System.out.println("leaveTypes in POST: " + leaveTypes); // DEBUG
        theModel.addAttribute("leaveTypeList", leaveTypes);

        if (theBindRes.hasErrors()) {
            return "leaveRequestAddForm";
        }
        // System.out.println("theLeaveRequest: " + theLeaveRequest); // DEBUG

        leaveRequestService.addLeaveRequest(theLeaveRequest, theLoggedInUserId);

        // redirectAttributes.addFlashAttribute("successMessage", "Leave request added successfully."); // TODO LATER

        //return showPersonGet(perId, theModel);
        return "redirect:/people/showPerson?perId=" + perId;
    }

    @PostMapping("/removeLeaveRequestProcess")
    public String removeLeaveRequestProcess(@RequestParam("leaveRequestId") Integer theLeaveRequestId,
                                            @RequestParam("perId") Integer thePerId,
                                            Model theModel) {

        Integer theLoggedInUserId = securityUtils.getLoggedInUserId();
        leaveRequestService.cancelLeaveRequest(theLeaveRequestId, theLoggedInUserId);

        return "redirect:/people/showPerson?perId=" + thePerId;
    }
}
