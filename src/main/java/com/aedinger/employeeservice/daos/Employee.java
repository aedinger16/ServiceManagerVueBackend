package com.aedinger.employeeservice.daos;

import lombok.Data;

import java.util.List;

@Data
public class Employee {
    private int id;
    private String name;
    private String longitude;
    private String latitude;
}
