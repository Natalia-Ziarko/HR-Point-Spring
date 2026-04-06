package com.point.hr.api.repository;

import com.point.hr.entity.Country;
import com.point.hr.repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Transactional
public abstract class BaseTest {

    @Autowired
    private CountryRepository countryRepository;

    @BeforeEach
    void setUpBase() {
        if (!countryRepository.existsById(1)) {
            Country austria = Country.builder()
                    .id(1)
                    .name("Austria")
                    .build();
            countryRepository.save(austria);
        }
    }
}
