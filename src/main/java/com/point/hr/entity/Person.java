package com.point.hr.model;

import com.point.hr.validation.ColumnLength;
import com.point.hr.validation.PersonSocialNumber;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name="people")
@Data
@Builder
@AllArgsConstructor // INFO: Lombok generates a constructor with all fields
@NoArgsConstructor // INFO: Lombok generates an empty constructor (for JPA)
public class Person {

    @Transient
    private final String TABLE_NAME = "people";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="perId")
    private Integer id;

    @Column(name="perSocialNo")
    @NotNull(message = "is required")
    @Pattern(regexp = "^[0-9]+$", message = "only digits")
    @PersonSocialNumber
    @ColumnLength(tableName = TABLE_NAME, columnName = "perSocialNo")
    private String socialNo;

    @Column(name="perLastName")
    @NotNull(message = "is required")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "only chars")
    @ColumnLength(tableName = TABLE_NAME, columnName = "perLastName")
    private String lastName;

    @Column(name="perFirstName")
    @NotNull(message = "is required")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "only chars")
    @ColumnLength(tableName = TABLE_NAME, columnName = "perFirstName")
    private String firstName;

    @Column(name="perCountryId")
    @NotNull(message = "is required")
    private Integer countryId;

    @Column(name="perCity")
    @NotNull(message = "is required")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "only chars")
    @ColumnLength(tableName = TABLE_NAME, columnName = "perCity")
    private String city;

    @Column(name="perZipCode")
    @NotNull(message = "is required")
    @ColumnLength(tableName = TABLE_NAME, columnName = "perZipCode")
    private String zipCode;

    @Column(name="perStreet")
    @NotNull(message = "is required")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "only chars")
    @ColumnLength(tableName = TABLE_NAME, columnName = "perStreet")
    private String street;

    @Column(name="perBuildNo")
    @NotNull(message = "is required")
    @ColumnLength(tableName = TABLE_NAME, columnName = "perBuildNo")
    private String buildNo;

    @Column(name="perApartNo")
    @ColumnLength(tableName = TABLE_NAME, columnName = "perApartNo")
    private String apartNo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotNull(message = "is required") @Pattern(regexp = "^[0-9]+$", message = "only digits") String getSocialNo() {
        return socialNo;
    }

    public void setSocialNo(@NotNull(message = "is required") @Pattern(regexp = "^[0-9]+$", message = "only digits") String socialNo) {
        this.socialNo = socialNo;
    }

    public @NotNull(message = "is required") @Pattern(regexp = "^[a-zA-Z]+$", message = "only chars") String getLastName() {
        return lastName;
    }

    public void setLastName(@NotNull(message = "is required") @Pattern(regexp = "^[a-zA-Z]+$", message = "only chars") String lastName) {
        this.lastName = lastName;
    }

    public @NotNull(message = "is required") @Pattern(regexp = "^[a-zA-Z]+$", message = "only chars") String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotNull(message = "is required") @Pattern(regexp = "^[a-zA-Z]+$", message = "only chars") String firstName) {
        this.firstName = firstName;
    }

    public @NotNull(message = "is required") Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(@NotNull(message = "is required") Integer countryId) {
        this.countryId = countryId;
    }

    public @NotNull(message = "is required") @Pattern(regexp = "^[a-zA-Z]+$", message = "only chars") String getCity() {
        return city;
    }

    public void setCity(@NotNull(message = "is required") @Pattern(regexp = "^[a-zA-Z]+$", message = "only chars") String city) {
        this.city = city;
    }

    public @NotNull(message = "is required") String getZipCode() {
        return zipCode;
    }

    public void setZipCode(@NotNull(message = "is required") String zipCode) {
        this.zipCode = zipCode;
    }

    public @NotNull(message = "is required") @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "only chars") String getStreet() {
        return street;
    }

    public void setStreet(@NotNull(message = "is required") @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "only chars") String street) {
        this.street = street;
    }

    public @NotNull(message = "is required") String getBuildNo() {
        return buildNo;
    }

    public void setBuildNo(@NotNull(message = "is required") String buildNo) {
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
