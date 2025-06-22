package com.point.hr.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.point.hr.validation.ColumnLength;
import com.point.hr.validation.PersonSocialNumber;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="people")
@Data // INFO: Lombok generates getters, setters, toString, equals, and hashCode method
@Builder // INFO: Lombok creates a builder pattern implementation for the class
@AllArgsConstructor // INFO: Lombok generates a constructor with all fields as parameters
@NoArgsConstructor // INFO: Lombok generates a default constructor with no parameters (for JPA)
public class Person {

    @Transient
    private final String TABLE_NAME = "people";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="perId")
    private Integer id;

    @Column(name="perSocialNo", nullable = false)
    @NotNull(message = "is required")
    @Pattern(regexp = "^[0-9]+$", message = "only digits")
    @PersonSocialNumber
    @ColumnLength(tableName = TABLE_NAME, columnName = "perSocialNo")
    private String socialNo;

    @Column(name="perLastName", nullable = false)
    @NotNull(message = "is required")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "only chars")
    @ColumnLength(tableName = TABLE_NAME, columnName = "perLastName")
    private String lastName;

    @Column(name="perFirstName", nullable = false)
    @NotNull(message = "is required")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "only chars")
    @ColumnLength(tableName = TABLE_NAME, columnName = "perFirstName")
    private String firstName;

    @Column(name="perCountryId", nullable = false)
    @NotNull(message = "is required")
    private Integer countryId;

    @Column(name="perCity", nullable = false)
    @NotNull(message = "is required")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "only chars")
    @ColumnLength(tableName = TABLE_NAME, columnName = "perCity")
    private String city;

    @Column(name="perZipCode", nullable = false)
    @NotNull(message = "is required")
    @ColumnLength(tableName = TABLE_NAME, columnName = "perZipCode")
    private String zipCode;

    @Column(name="perStreet", nullable = false)
    @NotNull(message = "is required")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "only chars")
    @ColumnLength(tableName = TABLE_NAME, columnName = "perStreet")
    private String street;

    @Column(name="perBuildNo", nullable = false)
    @NotNull(message = "is required")
    @ColumnLength(tableName = TABLE_NAME, columnName = "perBuildNo")
    private String buildNo;

    @Column(name="perApartNo")
    @ColumnLength(tableName = TABLE_NAME, columnName = "perApartNo")
    private String apartNo;

    /* Relationships */

    @OneToOne(mappedBy = "person", cascade=CascadeType.ALL)
    private Employee employee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "perCountryId", insertable = false, updatable = false)
    private Country country;

    @OneToOne(mappedBy = "person", cascade=CascadeType.ALL)
    private User user;



    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", socialNo=" + socialNo +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", countryId=" + countryId +
                ", city='" + city + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", street='" + street + '\'' +
                ", buildNo='" + buildNo + '\'' +
                ", apartNo='" + apartNo + '\'' +
                '}';
    }

}
