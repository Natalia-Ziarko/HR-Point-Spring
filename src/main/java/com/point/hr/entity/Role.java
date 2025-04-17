package com.point.hr.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="departments")
@Data // INFO: Lombok generates getters, setters, toString, equals, and hashCode method
@Builder // INFO: Lombok creates a builder pattern implementation for the class
@AllArgsConstructor // INFO: Lombok generates a constructor with all fields as parameters
@NoArgsConstructor // INFO: Lombok generates a default constructor with no parameters (for JPA)
public class Department {

    @Transient
    private final String TABLE_NAME = "departments";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="depId")
    private Integer id;

    @Column(name="depName")
    @NotNull(message = "is required")
    private String name;



    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }

}
