package com.point.hr.controller;

import com.point.hr.entity.Person;
import com.point.hr.entity.User;
import com.point.hr.service.PersonService;
import com.point.hr.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserCtrl {

    @Autowired
    private UserService userService;

    @Autowired
    private PersonService personService;

    @RequestMapping("/addUser")
    public String addUser(Model theModel) {

        User theUser = new User();

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
            Integer personId = theUser.getPerson() != null ? theUser.getPerson().getId() : null;
            User existingUser = userService.findByPersonId(personId);
            theBindRes.rejectValue("person.id", "duplicate.person.id",
                    "The user exists with ID: " + existingUser.getId());

            theUser.setId(existingUser.getId()); // INFO: Add userId to the form

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
    public String updateUser(@RequestParam("perId") Integer perId,
                             Model theModel) {

        User theUser = userService.findByPersonId(perId);

        if (theUser == null)
            return "redirect:/home";

        theModel.addAttribute("theUser", theUser);

        return "userUpdateForm";
    }

    @PostMapping("/updateUserProcess")
    public String updateUserProcess(@Valid @ModelAttribute("theUser") User theUser,
                                    BindingResult theBindRes,
                                    Model theModel) {

        if (theBindRes.hasErrors()) {
            theModel.addAttribute("theUser", theUser);

            return "userUpdateForm";
        }

        if (theUser.getPerson() != null && theUser.getPerson().getId() != null) {
            Person person = personService.findById(theUser.getPerson().getId());

            if (person != null) {
                theUser.setPerson(person);
            } else {
                throw new RuntimeException("Person not found");
            }
        }

        userService.update(theUser);

        return "redirect:/home";
    }
}
