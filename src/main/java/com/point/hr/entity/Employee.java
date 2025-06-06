package com.point.hr.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="employees")
@Data // INFO: Lombok generates getters, setters, toString, equals, and hashCode method
@Builder // INFO: Lombok creates a builder pattern implementation for the class
@AllArgsConstructor // INFO: Lombok generates a constructor with all fields as parameters
@NoArgsConstructor // INFO: Lombok generates a default constructor with no parameters (for JPA)
public class Employee {

    @Transient
    @JsonIgnore
    private final String table_NAME = "employees";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="empId")
    private Integer id;

    @Column(name = "empPerId", nullable = false, unique = true)
    @NotNull(message = "is required")
    private Integer personId;

    @Column(name="empManagerPerId", nullable = true)
    private Integer managerId;

    @Column(name="empDepId", nullable = true)
    private Integer departmentId;

    @Column(name="empCreatedBy", nullable = true)
    @JsonIgnore
    private Integer createdById;

    @Column(name="empCreatedDate", nullable = false)
    @NotNull(message = "is required")
    @JsonIgnore
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name="empUpdatedBy", nullable = true)
    @JsonIgnore
    private Integer updatedById;

    @Column(name="empUpdatedDate", nullable = true)
    @JsonIgnore
    private LocalDateTime updatedDate;

    /* Relationships */

    @OneToOne
    @JoinColumn(name = "empPerId", referencedColumnName = "perId", insertable = false, updatable = false)
    @JsonIgnore // INFO: Path in JSON output is not needed
    private Person person;

    @ManyToOne
    @JoinColumn(name = "empManagerPerId", referencedColumnName = "perId", insertable=false, updatable=false)
    @JsonIgnore // INFO: Path in JSON output is not needed
    private Person managerTeam;

    @ManyToOne
    @JoinColumn(name = "empDepId", referencedColumnName = "depId", insertable=false, updatable=false)
    @JsonIgnore // INFO: Path in JSON output is not needed
    private Department department;

    @ManyToOne
    @JoinColumn(name = "empCreatedBy", referencedColumnName = "perId", insertable=false, updatable=false)
    @JsonIgnore // INFO: Path in JSON output is not needed
    private Person createdBy;

    @ManyToOne
    @JoinColumn(name = "empUpdatedBy", referencedColumnName = "perId", insertable=false, updatable=false)
    @JsonIgnore // INFO: Path in JSON output is not needed
    private Person updatedBy;



    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", personId=" + personId +
                ", managerId=" + managerId +
                ", departmentId=" + departmentId +
                ", createdById=" + createdById +
                ", createdDate=" + createdDate +
                ", updatedById=" + updatedById +
                ", updatedDate=" + updatedDate +
                '}';
    }

}
