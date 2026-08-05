package com.point.hr.controller;

import com.point.hr.dto.LeaveRequestDetailsDTO;
import com.point.hr.dto.LeaveRequestFormDTO;
import com.point.hr.entity.LeaveRequest;
import com.point.hr.entity.LeaveType;
import com.point.hr.security.SecurityUtils;
import com.point.hr.service.LeaveRequestService;
import com.point.hr.service.LeaveRequestStatusService;
import com.point.hr.service.LeaveTypeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class LeaveRequestCtrl {

    private final LeaveTypeService leaveTypeService;
    private final LeaveRequestService leaveRequestService;
    private final LeaveRequestStatusService leaveRequestStatusService;
    private final SecurityUtils securityUtils;

    public LeaveRequestCtrl(LeaveTypeService leaveTypeService,
                            LeaveRequestService leaveRequestService,
                            LeaveRequestStatusService leaveRequestStatusService,
                            SecurityUtils securityUtils) {
        this.leaveTypeService = leaveTypeService;
        this.leaveRequestService = leaveRequestService;
        this.leaveRequestStatusService = leaveRequestStatusService;
        this.securityUtils = securityUtils;
    }

    @RequestMapping("")
    public String leaveRequestList(@RequestParam(required = false) String keyword,
                                   Model theModel) {

        List<LeaveRequest> theLeaveRequestsList = leaveRequestService.showAllLeaveRequests(keyword);


        List<LeaveRequestDetailsDTO> listWithStatus = theLeaveRequestsList.stream()
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

        LeaveRequestFormDTO theLeaveRequest = new LeaveRequestFormDTO();
        theLeaveRequest.setPersonId(perId);
        theLeaveRequest.setStartDate(LocalDate.now());
        theLeaveRequest.setEndDate(LocalDate.now());

        List<LeaveType> leaveTypeList = leaveTypeService.findAll();

        theModel.addAttribute("leaveTypeList", leaveTypeList);
        theModel.addAttribute("leaveRequest", theLeaveRequest);
        theModel.addAttribute("perId", perId);

        log.debug("Opening a leave request form: perId={}, startDate={}, endDate={}, leaveTypeListSize={}",
                perId, theLeaveRequest.getStartDate(), theLeaveRequest.getEndDate(), leaveTypeList.size());


        return "leaveRequestAddForm";
    }

    @PostMapping("/addLeaveRequestProcess")
    public String addLeaveRequestProcess(@Valid @ModelAttribute("leaveRequest") LeaveRequestFormDTO theLeaveRequest,
                                         BindingResult theBindRes,
                                         Model theModel) {

        Integer perId = theLeaveRequest.getPersonId();
        Integer theLoggedInUserId = securityUtils.getLoggedInUserId();

        log.debug("Processing a leave request form: perId={}, loggedInUserId={}", perId, theLoggedInUserId);

        List<LeaveType> leaveTypes = leaveTypeService.findAll();

        theModel.addAttribute("leaveTypeList", leaveTypes);

        if (theBindRes.hasErrors()) {
            log.debug("Leave request validation errors: {}", theBindRes.getAllErrors());

            return "leaveRequestAddForm";
        }

        log.debug("Leave request form for insert: {}", theLeaveRequest);

        leaveRequestService.addLeaveRequest(theLeaveRequest, theLoggedInUserId);

        // redirectAttributes.addFlashAttribute("successMessage", "Leave request added successfully."); // TODO: LATER

        //return showPersonGet(perId, theModel); // TODO: LATER
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
