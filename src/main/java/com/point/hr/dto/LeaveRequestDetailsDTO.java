package com.point.hr.dto;

import com.point.hr.entity.LeaveRequest;
import lombok.Data;

@Data
public class LeaveRequestDetailsDTO {

    private LeaveRequest leaveRequest;
    private String lastStatus;

    public LeaveRequestDetailsDTO(LeaveRequest leaveRequest, String lastStatus) {
        this.leaveRequest = leaveRequest;
        this.lastStatus = lastStatus;
    }

    public LeaveRequest getLeaveRequest() {
        return leaveRequest;
    }

    public String getLastStatus() {
        return lastStatus;
    }
}