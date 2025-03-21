package com.point.hr.dao;

import com.point.hr.model.Country;

import java.util.List;

public interface CountryDAO {
    List<Country> findByName(String theName);

    Country findById(Integer theId);

    List<Country> findAll();

}
