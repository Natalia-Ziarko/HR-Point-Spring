package com.point.hr.controller;

import com.point.hr.dao.CountryDAO;
import com.point.hr.model.Person;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PersonCtrl {

    @Autowired
    private CountryDAO countryDAO;

    @PostMapping("/personAddFormProcess")
    public String personAddFormProcess(@Valid @ModelAttribute("thePerson") Person thePerson,
                                       BindingResult theBindRes,
                                       Model theModel) {

        // DEBUG binding errors to make custom error messages
        // System.out.println("Binding results: " + theBindRes.toString() + "\n");

        // DEBUG perSocialNo length
        // if (thePerson.getSocialNo() != null)
        //     System.out.println("Input " + thePerson.getSocialNo() + ", length: " + thePerson.getSocialNo().length());

        if (theBindRes.hasErrors()) {
            theModel.addAttribute("countryList", countryDAO.findAll());

            return "personAddForm";
        }

        return "personAddView";
    }

}
