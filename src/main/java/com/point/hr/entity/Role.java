package com.point.hr.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="roles")
@Data // INFO: Lombok generates getters, setters, toString, equals, and hashCode method
@Builder // INFO: Lombok creates a builder pattern implementation for the class
@AllArgsConstructor // INFO: Lombok generates a constructor with all fields as parameters
@NoArgsConstructor // INFO: Lombok generates a default constructor with no parameters (for JPA)
public class Role {

    @Transient
    private final String TABLE_NAME = "roles";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="roleId")
    private Integer id;

    @Column(name="roleName")
    @NotNull(message = "is required")
    private String name;

    @Column(name="roleDesc")
    private String description;

    /* Relationships */

    @OneToMany(mappedBy = "role", cascade=CascadeType.ALL, orphanRemoval = true)
    private List<UserRole> userRoles;



    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name=" + name +
                ", description=" + description +
                '}';
    }

}
