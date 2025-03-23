package com.point.hr.controller;

import com.point.hr.service.CountryService;
import com.point.hr.service.PersonService;
import com.point.hr.entity.Person;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/people")
public class PersonCtrl {

    @Autowired
    private CountryService countryService;

    @Autowired
    private PersonService personService;

    @RequestMapping("/personAddFormView")
    public String home(Model theModel) {

        theModel.addAttribute("thePerson", new Person());
        theModel.addAttribute("countryList", countryService.findAll());

        return "personAddForm";
    }

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
            theModel.addAttribute("countryList", countryService.findAll());

            return "personAddForm";
        }

        personService.save(thePerson);

        // INFO: Redirect prevents duplicate submissions
        return "redirect:/people/list";
    }

    @RequestMapping("/list")
    public String listPeople(Model theModel) {
        theModel.addAttribute("peopleList", personService.findAll());

        return "peopleListView";
    }


}
