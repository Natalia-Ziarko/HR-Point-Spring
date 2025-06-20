package com.point.hr.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name="statuses")
@Data
@Builder
@AllArgsConstructor // INFO: Lombok generates a constructor with all fields
@NoArgsConstructor // INFO: Lombok generates an empty constructor (for JPA)
public class Status {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="stId")
    @NotNull(message = "is required")
    private Integer id;

    @Getter
    @Setter
    @Column(name="stName")
    private String name;

    @Getter
    @Setter
    @Column(name="stIfActive")
    private Boolean ifActive;


    @Override
    public String toString() {
        return "Status{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ifActive='" + ifActive + '\'' +
                '}';
    }
}
