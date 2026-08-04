package com.point.hr.service;

import com.point.hr.dto.LeaveRequestFormDTO;
import com.point.hr.entity.LeaveRequest;

import java.util.List;

public interface LeaveRequestService {

    LeaveRequest addLeaveRequest(LeaveRequestFormDTO theLeaveRequest, Integer whoAddedId);

    LeaveRequest changeLeaveRequest(LeaveRequest theLeaveRequest);
    LeaveRequest changeLeaveRequest(LeaveRequest theLeaveRequest, Integer newStatusId, Integer whoAddedId);

    List<LeaveRequest> showPersonLeaveRequests(Integer thePersonId);

    List<LeaveRequest> showAllLeaveRequests();

    void cancelLeaveRequest(Integer leaveRequestId, Integer whoAddedId);
}
