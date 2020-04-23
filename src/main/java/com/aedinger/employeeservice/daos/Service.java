package com.aedinger.employeeservice.daos;

import lombok.Data;

@Data
public class Service {
    private int id;
    private String name;
    private String date;
    private String longitude;
    private String latitude;
    private int employeeId;
}
