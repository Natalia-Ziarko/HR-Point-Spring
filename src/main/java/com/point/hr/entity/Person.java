package com.point.hr.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="people")
@Data
@Builder
@AllArgsConstructor // Lombok generates a constructor with all fields
@NoArgsConstructor // Lombok generates an empty constructor (for JPA)
public class Person {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="perId")
    private Integer id;

    @Column(name="perSocialNo")
    private Integer socialNo;

    @Column(name="perLastName")
    private String lastName;

    @Column(name="perFirstName")
    private String firstName;

    @Column(name="perCountryId")
    private Integer countryId;

    @Column(name="perCity")
    private String city;

    @Column(name="perZipCode")
    private String zipCode;

    @Column(name="perStreet")
    private String street;

    @Column(name="perBuildNo")
    private String buildNo;

    @Column(name="perApartNo")
    private String apartNo;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setSocialNo(Integer socialNo) {
        this.socialNo = socialNo;
    }

    public Integer getSocialNo() {
        return socialNo;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuildNo() {
        return buildNo;
    }

    public void setBuildNo(String buildNo) {
        this.buildNo = buildNo;
    }

    public String getApartNo() {
        return apartNo;
    }

    public void setApartNo(String apartNo) {
        this.apartNo = apartNo;
    }

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
