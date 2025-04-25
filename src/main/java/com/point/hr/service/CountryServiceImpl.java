package com.point.hr.service;

import com.point.hr.entity.Country;
import com.point.hr.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public Optional<Country> findByName(String theName) {
        return countryRepository.findByName(theName);
    }

    @Override
    public Optional<Country>  findById(Integer theId) {
        return countryRepository.findById(theId);
    }

    @Override
    public List<Country> findAll() {
        return countryRepository.findAll();
    }
}
