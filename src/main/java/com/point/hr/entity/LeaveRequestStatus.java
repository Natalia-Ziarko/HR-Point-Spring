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
    @NotNull(message = "is required")
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
    private LocalDateTime whenAdded;

    /* Relationships */

    @ManyToOne
    @JoinColumn(name = "lrsWhoAdded", referencedColumnName = "perId", insertable=false, updatable=false)
    @JsonIgnore // INFO: Path in JSON output is not needed
    private Person whoAdded;



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
