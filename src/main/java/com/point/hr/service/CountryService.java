package com.point.hr.service;

import com.point.hr.entity.Country;

import java.util.List;

public interface CountryService {
    List<Country> findByName(String theName);

    Country findById(Integer theId);

    List<Country> findAll();

}
