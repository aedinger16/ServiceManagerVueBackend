package com.aedinger.employeeservice.daos;

import lombok.Data;

@Data
public class EmployeeResource {
    private int id;
    private String name;
    private String address;
    private String longitude;
    private String latitude;
}
