package com.point.hr.controller;

import com.point.hr.entity.Person;
import com.point.hr.entity.User;
import com.point.hr.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserCtrl {

    @Autowired
    private UserService userService;

    @RequestMapping("/addUser")
    public String addUser(Model theModel) {

        theModel.addAttribute("theUser", new User());

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

        userService.save(theUser);

        return "redirect:/home";
    }

    @RequestMapping("/updateUser")
    public String updateUser(Model theModel) {

        theModel.addAttribute("theUser", new User());

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

        userService.update(theUser);

        return "redirect:/home";
    }
}
