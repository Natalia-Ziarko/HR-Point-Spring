package com.point.hr.controller;

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

    @RequestMapping("/userUpdateFormView")
    public String home(Model theModel) {

        theModel.addAttribute("theUser", new User());

        return "userUpdateForm";
    }

    @PostMapping("/addTheUser")
    public String addTheUser(@Valid @ModelAttribute("theUser") User theUser,
                                       BindingResult theBindRes,
                                       Model theModel) {

        if (theBindRes.hasErrors()) {
            theModel.addAttribute("theUser", theUser);

            return "/userAddForm";
        }

        return "redirect:/home";
    }

    @PostMapping("/updateTheUser")
    public String updateTheUser(@Valid @ModelAttribute("theUser") User theUser,
                                       BindingResult theBindRes,
                                       Model theModel) {

        if (theBindRes.hasErrors()) {
            theModel.addAttribute("theUser", theUser);

            return "/userUpdateForm";
        }

        userService.update(theUser);

        return "redirect:/home";
    }
}
