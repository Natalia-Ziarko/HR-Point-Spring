package com.point.hr.controller;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

// INFO: Make the class apply globally to all controllers
@ControllerAdvice
public class UtilCtrl {

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        StringTrimmerEditor strTrimEditor = new StringTrimmerEditor(true);

        webDataBinder.registerCustomEditor(String.class, strTrimEditor);
    }
}
