package com.point.hr.dto;

import com.point.hr.entity.Person;
import com.point.hr.entity.User;
import lombok.Data;

@Data
public class UserUpdateDTO {

    private Integer id;
    private String password; // INFO: Without @NotNull, so it’s optional for the updates
    private Boolean ifActive;
    private Person person;

    public UserUpdateDTO() {
    }

    public UserUpdateDTO(User theUser) {
        this.id = theUser.getId();
        this.ifActive = theUser.getIfActive();
        this.person = theUser.getPerson() != null ? theUser.getPerson() : new Person();
    }
}