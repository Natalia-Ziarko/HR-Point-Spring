package com.point.hr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginCtrl {

    @GetMapping("/showLoginPage")
    public String showLoginPage() {

        return "loginView";
    }
}
