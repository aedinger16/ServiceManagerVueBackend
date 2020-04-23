package com.aedinger.employeeservice.daos;

import lombok.Data;

@Data
public class ServiceResource {
    private int id;
    private String name;
    private Employee employee;
    private String date;
    private String address;
}
