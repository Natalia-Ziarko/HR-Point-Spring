package com.point.hr.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="leaveRequestsStatuses")
@Data
@Builder
@AllArgsConstructor // INFO: Lombok generates a constructor with all fields
@NoArgsConstructor // INFO: Lombok generates an empty constructor (for JPA)
public class LeaveRequestStatus {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="lrsId")
    //@NotNull(message = "is required")
    private Integer id;

    @Getter
    @Setter
    @Column(name="lrsLeaveId")
    @NotNull(message = "is required")
    private Integer leaveId;

    @Getter
    @Setter
    @Column(name="lrsStatusId")
    @NotNull(message = "is required")
    private Integer statusId;

    @Getter
    @Setter
    @Column(name="lrsWhenAdded")
    @NotNull(message = "is required")
    private LocalDateTime whenAdded = LocalDateTime.now();

    /* Relationships */

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lrsWhoAdded", nullable = false)
    private Person whoAdded;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lrsLeaveId", insertable = false, updatable = false)
    private LeaveRequest leaveRequest;

    @Getter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lrsStatusId", referencedColumnName = "stId", insertable = false, updatable = false)
    private Status status;



    @Override
    public String toString() {
        return "LeaveRequestStatus{" +
                "id=" + id +
                ", leaveId=" + leaveId +
                ", statusId=" + statusId +
                //", whoAdded=" + whoAdded +
                ", whenAdded=" + whenAdded +
                '}';
    }
}
