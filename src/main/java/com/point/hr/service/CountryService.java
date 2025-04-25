package com.point.hr.service;

import com.point.hr.entity.Country;

import java.util.List;
import java.util.Optional;

public interface CountryService {
    Optional<Country> findByName(String name);

    Optional<Country> findById(Integer id);

    List<Country> findAll();

}
