package com.point.hr.service;

import com.point.hr.entity.LeaveRequest;
import com.point.hr.entity.LeaveRequestStatus;
import com.point.hr.entity.Person;
import com.point.hr.repository.LeaveRequestRepository;
import com.point.hr.repository.LeaveRequestStatusRepository;
import com.point.hr.repository.PersonRepository;
import com.point.hr.repository.StatusRepository;
import com.point.hr.security.MyUserDetails;
import com.point.hr.security.SecurityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService{

    private final PersonRepository personRepository;
    private final StatusRepository statusRepository;
    private final LeaveRequestRepository leaveRequestRepository;

    private final PersonService personService;
    private final LeaveRequestStatusService leaveRequestStatusService;

    private final SecurityUtils securityUtils;

    public LeaveRequestServiceImpl(PersonRepository personRepository, StatusRepository statusRepository, LeaveRequestRepository leaveRequestRepository, PersonService personService, LeaveRequestStatusService leaveRequestStatusService, SecurityUtils securityUtils) {
        this.personRepository = personRepository;
        this.statusRepository = statusRepository;
        this.leaveRequestRepository = leaveRequestRepository;
        this.personService = personService;
        this.leaveRequestStatusService = leaveRequestStatusService;
        this.securityUtils = securityUtils;
    }


    @Override
    @Transactional
    public LeaveRequest addLeaveRequest(LeaveRequest theLeaveRequest) {

        Integer durationDays = (int) ChronoUnit.DAYS.between(theLeaveRequest.getStartDate(), theLeaveRequest.getEndDate()) + 1;
        theLeaveRequest.setDurationDays(durationDays);
        theLeaveRequest.setPersonId(theLeaveRequest.getPersonId());
        theLeaveRequest.setWhoAdded(securityUtils.getLoggedInUserId());
        //System.out.println("LeaveRequestServiceImpl.addLeaveRequest whoAdded: " + securityUtils.getLoggedInUserId()); // DEBUG

        leaveRequestRepository.save(theLeaveRequest);

        // Insert leaveRequestStatus

        LeaveRequestStatus theLeaveRequestStatus = new LeaveRequestStatus();
        theLeaveRequestStatus.setLeaveId(theLeaveRequest.getId());
        theLeaveRequestStatus.setStatusId(1);
        theLeaveRequestStatus.setWhoAdded(
                personRepository.findById(securityUtils.getLoggedInUserId())
                        .orElseThrow(() -> new RuntimeException("User not found: " + securityUtils.getLoggedInUserId()))
        );

        leaveRequestStatusService.addLeaveRequestNewStatus(theLeaveRequestStatus);

        return theLeaveRequest;
    }

    @Override
    @Transactional
    public LeaveRequest changeLeaveRequest(LeaveRequest theLeaveRequest) {
        LeaveRequest originalLeaveRequest = leaveRequestRepository.findById(theLeaveRequest.getId()).orElseThrow(() -> new IllegalArgumentException("Leave request not found"));

        /*
         * FIXME: check the last status of the leaveRequest
         */

        return leaveRequestRepository.save(theLeaveRequest);
    }

    @Override
    public LeaveRequest changeLeaveRequest(LeaveRequest theLeaveRequest, Integer newStatusId, Integer whoAddedId) {
        LeaveRequest originalLeaveRequest = leaveRequestRepository.findById(theLeaveRequest.getId()).orElseThrow(() -> new IllegalArgumentException("Leave request not found"));

        LeaveRequestStatus newLeaveRequestStatus = new LeaveRequestStatus();
        newLeaveRequestStatus.setLeaveId(originalLeaveRequest.getId());

        newLeaveRequestStatus.setStatusId(newStatusId);

//        Person whoAdded = personService.findById(whoAddedId);
//        if (whoAdded != null) {
//            newLeaveRequestStatus.setWhoAdded(whoAdded);
//        } else {
//            newLeaveRequestStatus.setWhoAdded(personService.findById(19));
//        }
        whoAddedId = securityUtils.getLoggedInUserId();
        System.out.println("LeaveRequestServiceImpl.changeLeaveRequest whoAdded: " + whoAddedId); // DEBUG


        leaveRequestStatusService.addLeaveRequestNewStatus(newLeaveRequestStatus);

        return null;
    }

    @Override
    public List<LeaveRequest> showPersonLeaveRequests(Integer thePersonId) {
        return leaveRequestRepository.findByPersonId(thePersonId);
    }

    @Override
    public List<LeaveRequest> showAllLeaveRequests() {
        return leaveRequestRepository.findAll();
    }
}
