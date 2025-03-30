package com.point.hr.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="users")
@Data // INFO: Lombok generates getters, setters, toString, equals, and hashCode method
@Builder // INFO: Lombok creates a builder pattern implementation for the class
@AllArgsConstructor // INFO: Lombok generates a constructor with all fields as parameters
@NoArgsConstructor // INFO: Lombok generates a default constructor with no parameters (for JPA)
public class User {

    @Transient
    private final String TABLE_NAME = "users";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="userId")
    private Integer id;

    @Column(name="userPw", nullable = false)
    @NotNull(message = "is required")
    @Size(max = 254, message = "max 254 chars")
    private String password;

    @Column(name="userIfActive", nullable = false)
    @NotNull(message = "is required")
    private Boolean ifActive = true;

    @Column(name="userCreatedDate", nullable = false)
    private LocalDateTime userCreatedDate = LocalDateTime.now();


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", ifActive=" + ifActive +
                ", person=" + (person != null ? person.getId() : null) +
                '}';
    }


    /* Relationships */

    @OneToOne
    @JoinColumn(name = "userPerId", referencedColumnName = "perId", insertable = false, updatable = false)
    private Person person;

}
