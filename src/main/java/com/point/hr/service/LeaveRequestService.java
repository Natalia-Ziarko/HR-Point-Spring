package com.point.hr.service;

import com.point.hr.entity.LeaveRequest;

import java.util.List;

public interface LeaveRequestService {

    LeaveRequest addLeaveRequest(LeaveRequest theLeaveRequest);

    LeaveRequest changeLeaveRequest(LeaveRequest theLeaveRequest);
    LeaveRequest changeLeaveRequest(LeaveRequest theLeaveRequest, Integer newStatusId, Integer whoAddedId);

    List<LeaveRequest> showPersonLeaveRequests(Integer thePersonId);

    List<LeaveRequest> showAllLeaveRequests();
}
