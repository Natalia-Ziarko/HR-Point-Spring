package com.point.hr;

import java.util.*;

import com.point.hr.dao.PersonDAO;
import com.point.hr.entity.Person;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HrApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(PersonDAO personDAO) {

		return runner -> {
			List<Person> allPeople = personDAO.findAll()
					.stream()
					.sorted(Comparator.comparing(Person::getLastName))
					.toList();

			for (Person p : allPeople) System.out.println(p);
		};
	}

}
