package com.point.hr.service;

import com.point.hr.entity.LeaveRequestStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LeaveRequestStatusService {

    void addLeaveRequestNewStatus(LeaveRequestStatus theLeaveRequestStatus);

    List<LeaveRequestStatus> showAllLeaveRequestStatuses(Integer theLeaveRequestId);

    String showLeaveRequestLastStatus(Integer theLeaveRequestId);
}
