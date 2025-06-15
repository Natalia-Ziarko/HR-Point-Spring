package com.point.hr.controller;

import com.point.hr.dto.UserUpdateDTO;
import com.point.hr.entity.Person;
import com.point.hr.entity.User;
import com.point.hr.service.PersonService;
import com.point.hr.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserCtrl {

    @Autowired
    private UserService userService;

    @Autowired
    private PersonService personService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @RequestMapping("/addUser")
    public String addUser(Model theModel, @RequestParam("perId") Integer thePersonId) {

        User theUser = new User();

        if (thePersonId != null)
            theUser.setPerson(personService.findById(thePersonId));

        theModel.addAttribute("theUser", theUser);

        return "userAddForm";
    }

    @PostMapping("/addUserProcess")
    public String addUserProcess(@Valid @ModelAttribute("theUser") User theUser,
                                 BindingResult theBindRes,
                                 Model theModel) {

        if (theBindRes.hasErrors()) {
            theModel.addAttribute("theUser", theUser);

            return "userAddForm";
        }

        try {
            userService.save(theUser);
        } catch (DataIntegrityViolationException e) {
            // INFO: Handle duplicate userPerId error
            Integer thePersonId = theUser.getPerson() != null ? theUser.getPerson().getId() : null;
            Optional<User> existingUser = userService.findByPersonId(thePersonId);

            if (existingUser.isPresent()) {
                theBindRes.rejectValue("person.id", "duplicate.person.id",
                        "The user exists with ID: " + existingUser.get().getId());

                theUser.setId(existingUser.get().getId()); // INFO: Add userId to the form
            }

            theModel.addAttribute("theUser", theUser);

            return "userUpdateForm";
        } catch (Exception e) {
            theBindRes.reject("general.error", "An unexpected error occurred. Please try again.");
            theModel.addAttribute("theUser", theUser);

            return "userAddForm";
        }

        return "redirect:/home";
    }

    @RequestMapping("/updateUser")
    public String updateUser(@RequestParam("perId") Integer thePersonId,
                             Model theModel) {

        Optional<User> theUser = userService.findByPersonId(thePersonId);

        if (theUser.isEmpty())
            return "redirect:/home";

        theModel.addAttribute("theUser", theUser.get());

        return "userUpdateForm";
    }

    @PostMapping("/updateUserProcess")
    public String updateUserProcess(@Valid @ModelAttribute("theUser") UserUpdateDTO theUserDTO,
                                    BindingResult theBindRes,
                                    Model theModel) {

        User existingUser = userService.findById(theUserDTO.getId()).orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (theBindRes.hasErrors()) {
            theModel.addAttribute("theUser", theUserDTO);

            return "userUpdateForm";
        }

        if (theUserDTO.getPerson() != null && theUserDTO.getPerson().getId() != null) {
            Person person = personService.findById(theUserDTO.getPerson().getId());

            if (person != null) {
                theUserDTO.setPerson(person);
            } else {
                return "userUpdateForm";
            }
        }

        userService.update(theUserDTO);

        return "redirect:/home";
    }
}
