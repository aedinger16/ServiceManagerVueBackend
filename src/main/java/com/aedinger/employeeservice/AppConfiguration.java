package com.aedinger.employeeservice;

import com.aedinger.employeeservice.services.EmployeeServiceDataService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AppConfiguration {
    @Bean
    @Primary
    public EmployeeServiceDataService createEmployeeServiceDataService() {
        EmployeeServiceDataService d = new EmployeeServiceDataService();
        return d;
    }
}
