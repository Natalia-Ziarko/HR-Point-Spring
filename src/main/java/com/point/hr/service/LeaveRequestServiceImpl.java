package com.point.hr.service;

import com.point.hr.entity.LeaveRequest;
import com.point.hr.repository.LeaveRequestRepository;
import com.point.hr.repository.LeaveRequestStatusRepository;
import com.point.hr.repository.PersonRepository;
import com.point.hr.repository.StatusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService{

    private final LeaveRequestRepository leaveRequestRepository;

    private final PersonRepository personRepository;

    private final PersonService personService;

    private final StatusRepository statusRepository;

    private final LeaveRequestStatusRepository leaveRequestStatusRepository;

    public LeaveRequestServiceImpl(LeaveRequestRepository leaveRequestRepository, PersonRepository personRepository, PersonService personService, StatusRepository statusRepository, LeaveRequestStatusRepository leaveRequestStatusRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.personRepository = personRepository;
        this.personService = personService;
        this.statusRepository = statusRepository;
        this.leaveRequestStatusRepository = leaveRequestStatusRepository;
    }

    @Override
    @Transactional
    public LeaveRequest addLeaveRequest(LeaveRequest theLeaveRequest) {

        Integer durationDays = (int) ChronoUnit.DAYS.between(theLeaveRequest.getStartDate(), theLeaveRequest.getEndDate()) + 1;
        theLeaveRequest.setDurationDays(durationDays);
        theLeaveRequest.setPersonId(19);
        theLeaveRequest.setWhoAdded(19);
        //theLeaveRequest.setWhoAdded(personService.findById(22));

        return leaveRequestRepository.save(theLeaveRequest);
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
    public List<LeaveRequest> showPersonLeaveRequests(Integer thePersonId) {
        return leaveRequestRepository.findByPersonId(thePersonId);
    }

    @Override
    public List<LeaveRequest> showAllLeaveRequests() {
        return leaveRequestRepository.findAll();
    }
}
