package com.aedinger.employeeservice.daos;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "employees")
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String longitude;
    private String latitude;
}
