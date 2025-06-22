package com.point.hr.service;

import com.point.hr.entity.LeaveRequestStatus;
import com.point.hr.entity.Status;
import com.point.hr.repository.LeaveRequestRepository;
import com.point.hr.repository.LeaveRequestStatusRepository;
import com.point.hr.repository.PersonRepository;
import com.point.hr.repository.StatusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LeaveRequestStatusServiceImpl implements LeaveRequestStatusService{

    private final PersonRepository personRepository;
    private final StatusRepository statusRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final LeaveRequestStatusRepository leaveRequestStatusRepository;

    public LeaveRequestStatusServiceImpl(PersonRepository personRepository, StatusRepository statusRepository, LeaveRequestRepository leaveRequestRepository, LeaveRequestStatusRepository leaveRequestStatusRepository) {
        this.personRepository = personRepository;
        this.statusRepository = statusRepository;
        this.leaveRequestRepository = leaveRequestRepository;
        this.leaveRequestStatusRepository = leaveRequestStatusRepository;
    }


    @Override
    public void addLeaveRequestNewStatus(LeaveRequestStatus theLeaveRequestStatus) {
        leaveRequestStatusRepository.save(theLeaveRequestStatus);
    }

    @Override
    public List<LeaveRequestStatus> showAllLeaveRequestStatuses(Integer theLeaveRequestId) {
        return leaveRequestStatusRepository.findByLeaveIdOrderByWhenAddedAsc(theLeaveRequestId);
    }

    @Override
    public String showLeaveRequestLastStatus(Integer theLeaveRequestId) {
        List<LeaveRequestStatus> statuses = showAllLeaveRequestStatuses(theLeaveRequestId);

        if (statuses.isEmpty()) {
            return "No status found";
        }

        Status latestStatus = statuses.getLast().getStatus();

        return latestStatus != null ? latestStatus.getName() : "Unknown status";
    }
}
