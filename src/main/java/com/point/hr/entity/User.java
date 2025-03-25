package com.point.hr.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="users")
//@Data
//@Builder
@AllArgsConstructor // INFO: Lombok generates a constructor with all fields
@NoArgsConstructor // INFO: Lombok generates an empty constructor (for JPA)
public class User {

    @Transient
    private final String TABLE_NAME = "users";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="userId")
    @NotNull(message = "is required")
    private Integer id;

    @Column(name="userPw")
    @NotNull(message = "is required")
    @Size(max = 254, message = "max 254 chars")
    private String password;

    @Column(name="userPerId", unique = true)
    @NotNull(message = "is required")
    private Integer personId;

    @Column(name="userIfActive")
    @NotNull(message = "is required")
    private Boolean ifActive;

    @Column(name="userCreatedDate")
    private LocalDateTime userCreatedDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotNull(message = "is required") @Size(max = 254, message = "max 254 chars") String getPassword() {
        return password;
    }

    public void setPassword(@NotNull(message = "is required") @Size(max = 254, message = "max 254 chars") String password) {
        this.password = password;
    }

    public @NotNull(message = "is required") Integer getPersonId() {
        return personId;
    }

    public void setPersonId(@NotNull(message = "is required") Integer personId) {
        this.personId = personId;
    }

    public @NotNull(message = "is required") Boolean getIfActive() {
        return ifActive;
    }

    public void setIfActive(@NotNull(message = "is required") Boolean ifActive) {
        this.ifActive = ifActive;
    }

    public LocalDateTime getUserCreatedDate() {
        return userCreatedDate;
    }

    public void setUserCreatedDate(LocalDateTime userCreatedDate) {
        this.userCreatedDate = userCreatedDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", personId=" + personId +
                ", ifActive=" + ifActive +
                '}';
    }
}
