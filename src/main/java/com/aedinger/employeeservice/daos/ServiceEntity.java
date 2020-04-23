package com.aedinger.employeeservice.daos;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "services")
public class ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String date;
    private String longitude;
    private String latitude;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employeeEntity;

}
