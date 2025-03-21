package com.point.hr.controller;

import com.point.hr.dao.CountryDAO;
import com.point.hr.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeCtrl {

    @Autowired
    private CountryDAO countryDAO;

    @RequestMapping("/home")
    public String home(Model theModel) {

        theModel.addAttribute("thePerson", new Person());
        theModel.addAttribute("countryList", countryDAO.findAll());

        return "personAddForm";
    }

}
