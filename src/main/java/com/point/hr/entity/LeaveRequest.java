package com.point.hr.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="leaveRequests")
@Data
@Builder
@AllArgsConstructor // INFO: Lombok generates a constructor with all fields
@NoArgsConstructor // INFO: Lombok generates an empty constructor (for JPA)
public class LeaveRequest {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="lrId")
    @NotNull(message = "is required")
    private Integer id;

    @Getter
    @Setter
    @Column(name="lrPersonId")
    @NotNull(message = "is required")
    private Person personId;

    @Getter
    @Setter
    @Column(name="lrLeaveTypeId")
    @NotNull(message = "is required")
    private Integer leaveTypeId;

    @Getter
    @Setter
    @Column(name="lrStartDate")
    @NotNull(message = "is required")
    private LocalDateTime startDate;

    @Getter
    @Setter
    @Column(name="lrEndDate")
    @NotNull(message = "is required")
    private LocalDateTime endDate;

    @Getter
    @Setter
    @Column(name="lrDuration_days")
    @NotNull(message = "is required")
    private Integer durationDays;

    @Getter
    @Setter
    @Column(name="lrWhoAdded")
    @NotNull(message = "is required")
    private Person whoAdded;

    @Getter
    @Setter
    @Column(name="lrWhenAdded")
    @NotNull(message = "is required")
    private LocalDateTime whenAdded;



    @Override
    public String toString() {
        return "LeaveRequest{" +
                "id=" + id +
                ", personId=" + personId +
                ", leaveTypeId=" + leaveTypeId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", durationDays=" + durationDays +
                ", whoAdded=" + whoAdded +
                ", whenAdded=" + whenAdded +
                '}';
    }
}
