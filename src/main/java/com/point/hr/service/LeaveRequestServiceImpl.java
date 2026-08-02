package com.point.hr.service;

import com.point.hr.entity.LeaveRequest;
import com.point.hr.entity.LeaveRequestStatus;
import com.point.hr.repository.LeaveRequestRepository;
import com.point.hr.repository.PersonRepository;
import com.point.hr.repository.StatusRepository;
import com.point.hr.security.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService{

    private final int CANCEL_LEAVE_REQUEST_ID = 4;

    private final PersonRepository personRepository;
    private final LeaveRequestRepository leaveRequestRepository;

    private final LeaveRequestStatusService leaveRequestStatusService;


    public LeaveRequestServiceImpl(PersonRepository personRepository,
                                   LeaveRequestRepository leaveRequestRepository,
                                   LeaveRequestStatusService leaveRequestStatusService) {
        this.personRepository = personRepository;
        this.leaveRequestRepository = leaveRequestRepository;
        this.leaveRequestStatusService = leaveRequestStatusService;
    }


    @Override
    @Transactional
    public LeaveRequest addLeaveRequest(LeaveRequest theLeaveRequest,
                                        Integer whoAddedId) {

        Integer durationDays = (int) ChronoUnit.DAYS.between(theLeaveRequest.getStartDate(),
                theLeaveRequest.getEndDate()) + 1;
        theLeaveRequest.setDurationDays(durationDays);
        theLeaveRequest.setPersonId(theLeaveRequest.getPersonId());
        theLeaveRequest.setWhoAddedId(whoAddedId);

        leaveRequestRepository.save(theLeaveRequest);

        // Insert leaveRequestStatus

        LeaveRequestStatus theLeaveRequestStatus = new LeaveRequestStatus();
        theLeaveRequestStatus.setLeaveId(theLeaveRequest.getId());
        theLeaveRequestStatus.setStatusId(1);
        theLeaveRequestStatus.setWhoAdded(
                personRepository.findById(whoAddedId)
                        .orElseThrow(() -> new RuntimeException("User not found: " + whoAddedId))
        );

        leaveRequestStatusService.addLeaveRequestNewStatus(theLeaveRequestStatus);

        return theLeaveRequest;
    }

    @Override
    @Transactional
    public LeaveRequest changeLeaveRequest(LeaveRequest theLeaveRequest) {
        LeaveRequest originalLeaveRequest = leaveRequestRepository
                .findById(theLeaveRequest.getId())
                .orElseThrow(() -> new IllegalArgumentException("Leave request not found"));

        /*
         * FIXME: check the last status of the leaveRequest
         */

        return leaveRequestRepository.save(theLeaveRequest);
    }

    @Override
    public LeaveRequest changeLeaveRequest(LeaveRequest theLeaveRequest, Integer newStatusId, Integer whoAddedId) {
        LeaveRequest originalLeaveRequest = leaveRequestRepository
                .findById(theLeaveRequest.getId()).orElseThrow(() -> new IllegalArgumentException("Leave request not found"));

        LeaveRequestStatus newLeaveRequestStatus = new LeaveRequestStatus();
        newLeaveRequestStatus.setLeaveId(originalLeaveRequest.getId());
        newLeaveRequestStatus.setWhoAdded(personRepository
                .findById(whoAddedId)
                .orElseThrow(() -> new RuntimeException("User not found: " + whoAddedId)));
        newLeaveRequestStatus.setStatusId(newStatusId);

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

    public void cancelLeaveRequest(Integer leaveRequestId,
                                   Integer whoAddedId) {

        // System.out.println("LeaveRequestServiceImpl.cancelLeaveRequest whoAddedId = " + whoAddedId); // DEBUG
        leaveRequestRepository
                .findById(leaveRequestId)
                .ifPresent(lr -> changeLeaveRequest(lr, CANCEL_LEAVE_REQUEST_ID, whoAddedId));
    }
}
