package com.point.hr.controller;

import com.point.hr.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeCtrl {

    @Autowired
    private CountryService countryService;

    @RequestMapping("/home")
    public String home() {
        return "redirect:/people/list";
    }
}
