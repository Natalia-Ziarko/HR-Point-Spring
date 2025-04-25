package com.point.hr.service;

import com.point.hr.entity.User;
import com.point.hr.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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

        return userRepository.save(theUser);
    }

    @Override
    public Optional<User> findById(Integer theId) {
        return userRepository.findById(theId);
    }

    @Override
    public Optional<User> findByPersonId(Integer thePersonId) {
        return userRepository.findByPersonId(thePersonId);
    }

    @Override
    @Transactional
    public User update(User theUser) {

        String rawPassword = theUser.getPassword();
        String hashedPassword = hashPassword(rawPassword);
        theUser.setPassword(hashedPassword);

        theUser.setUserCreatedDate(LocalDateTime.now());

        return userRepository.save(theUser);
    }

    @Override
    @Transactional
    public Integer deleteById(Integer theUserId) {
        userRepository.deleteById(theUserId);

        return theUserId;
    }
}
