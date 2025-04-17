package com.point.hr.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="usersRoles")
@Data
@Builder
@AllArgsConstructor // INFO: Lombok generates a constructor with all fields
@NoArgsConstructor // INFO: Lombok generates an empty constructor (for JPA)
public class UserRole {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="urId")
    private Integer id;

    /* Relationships */

    @ManyToOne
    @JoinColumn(name = "urUserId", referencedColumnName = "userId", insertable=false, updatable=false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "urRoleId", referencedColumnName = "roleId", insertable=false, updatable=false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "urCreatedBy", referencedColumnName = "perId", insertable=false, updatable=false)
    private Person createdBy;

    @ManyToOne
    @JoinColumn(name = "urUpdatedBy", referencedColumnName = "perId", insertable=false, updatable=false)
    private Person updatedBy;



    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                '}';
    }

}
