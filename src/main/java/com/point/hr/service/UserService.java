package com.point.hr.service;

import com.point.hr.entity.User;
import jakarta.validation.Valid;

public interface UserService {
    User save(@Valid User theUser);

    User findById(Integer theUserId);

    User findByPersonId(Integer thePersonId);

    User update(User theUser);

    Integer deleteById(Integer theUserId);

}
