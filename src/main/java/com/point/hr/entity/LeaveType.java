package com.point.hr.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name="leaveTypes")
@Data
@Builder
@AllArgsConstructor // INFO: Lombok generates a constructor with all fields
@NoArgsConstructor // INFO: Lombok generates an empty constructor (for JPA)
public class LeaveType {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ltId")
    @NotNull(message = "is required")
    private Integer id;

    @Getter
    @Setter
    @Column(name="ltShortName")
    @NotNull(message = "is required")
    private String shortName;

    @Getter
    @Setter
    @Column(name="ltLongName")
    private String longName;


    @Getter
    @Setter
    @Column(name="ltIfActive")
    private Boolean ifActive;


    @Override
    public String toString() {
        return "LeaveType{" +
                "id=" + id +
                ", shortName='" + shortName + '\'' +
                ", longName='" + longName + '\'' +
                ", ifActive=" + ifActive +
                '}';
    }
}
