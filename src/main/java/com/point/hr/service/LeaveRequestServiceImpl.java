package com.point.hr.service;

import com.point.hr.dto.LeaveRequestFormDTO;
import com.point.hr.entity.LeaveRequest;
import com.point.hr.entity.LeaveRequestStatus;
import com.point.hr.repository.LeaveRequestRepository;
import com.point.hr.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService{

    private final int CANCEL_LEAVE_REQUEST_ID = 4;
    private final int NEW_LEAVE_REQUEST_ID = 1;
// TODO: instead of final ints
//    public enum LeaveRequestStatusType {
//        NEW("New"),
//        APPROVED("Accepted"),
//        REJECTED("Declined"),
//        CANCELLED("Cancelled");
//
//        private final String code;
//
//        LeaveRequestStatusType(String code) {
//            this.code = code;
//        }
//
//        public String getCode() {
//            return code;
//        }
//    }

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
    public LeaveRequest addLeaveRequest(LeaveRequestFormDTO theLeaveRequest,
                                        Integer whoAddedId) {

        Integer durationDays = (int) ChronoUnit.DAYS.between(
                theLeaveRequest.getStartDate(),
                theLeaveRequest.getEndDate()
        ) + 1;

        LeaveRequest entityToSave = LeaveRequest.builder()
                .personId(theLeaveRequest.getPersonId())
                .leaveTypeId(theLeaveRequest.getLeaveTypeId())
                .startDate(theLeaveRequest.getStartDate())
                .endDate(theLeaveRequest.getEndDate())
                .durationDays(durationDays)
                .whoAddedId(whoAddedId)
                .whenAdded(LocalDateTime.now())
                .build();

        LeaveRequest savedLeaveRequest = leaveRequestRepository.save(entityToSave);

        // Insert leaveRequestStatus

        LeaveRequestStatus theLeaveRequestStatus = new LeaveRequestStatus();
        theLeaveRequestStatus.setLeaveId(savedLeaveRequest.getId());
        theLeaveRequestStatus.setStatusId(NEW_LEAVE_REQUEST_ID);
        theLeaveRequestStatus.setWhoAdded(
                personRepository.findById(whoAddedId)
                        .orElseThrow(() -> new RuntimeException("User not found: " + whoAddedId))
        );

        leaveRequestStatusService.addLeaveRequestNewStatus(theLeaveRequestStatus);

        return savedLeaveRequest;
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

    @Override
    public List<LeaveRequest> showAllLeaveRequests(String keyword) {
        List<LeaveRequest> all = showAllLeaveRequests();

        if (keyword != null && !keyword.isBlank()) {
            String loweredKeyword = keyword.toLowerCase();

            return all.stream()
                    .filter(lr ->
                            (lr.getPerson() != null && lr.getPerson().getLastName().toLowerCase().contains(loweredKeyword))
                            || (lr.getLeaveType() != null && lr.getLeaveType().getLongName().toLowerCase().contains(loweredKeyword))
                    )
                    .collect(Collectors.toList()
            );
        }
        return all;
    }

    public void cancelLeaveRequest(Integer leaveRequestId,
                                   Integer whoAddedId) {

        // System.out.println("LeaveRequestServiceImpl.cancelLeaveRequest whoAddedId = " + whoAddedId); // DEBUG
        leaveRequestRepository
                .findById(leaveRequestId)
                .ifPresent(lr -> changeLeaveRequest(lr, CANCEL_LEAVE_REQUEST_ID, whoAddedId));
    }
}
