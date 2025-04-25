package com.point.hr.service;

import com.point.hr.entity.User;
import jakarta.validation.Valid;

import java.util.Optional;

public interface UserService {

    User save(@Valid User user);

    Optional<User> findById(Integer id);

    Optional<User> findByPersonId(Integer personId);

    User update(User user);

    Integer deleteById(Integer userId);

}
