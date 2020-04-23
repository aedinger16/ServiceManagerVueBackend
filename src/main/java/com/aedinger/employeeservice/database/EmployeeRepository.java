package com.aedinger.employeeservice.database;

import com.aedinger.employeeservice.daos.EmployeeEntity;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<EmployeeEntity, Integer> {
}
