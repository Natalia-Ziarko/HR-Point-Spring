package com.point.hr.service;

import com.point.hr.entity.User;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public class UserServiceImpl implements UserService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    @Transactional
    public User save(User theUser) {
        String rawPassword = theUser.getPassword();
        String hashedPassword = hashPassword(rawPassword);
        theUser.setPassword(hashedPassword);

        entityManager.persist(theUser);

        return theUser;
    }

    @Override
    public User findById(Integer theUserId) {
        return entityManager.find(User.class, theUserId);
    }

    @Override
    @Transactional
    public User update(User theUser) {
        String rawPassword = theUser.getPassword();
        String hashedPassword = hashPassword(rawPassword);
        theUser.setPassword(hashedPassword);

        theUser.setUserCreatedDate(LocalDateTime.now());

        entityManager.merge(theUser);

        return theUser;
    }

    @Override
    @Transactional
    public Integer deleteById(Integer theUserId) {
        User theUser = findById(theUserId);

        entityManager.remove(theUser);

        return theUserId;
    }
}
