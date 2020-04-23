package com.aedinger.employeeservice.database;

import com.aedinger.employeeservice.daos.ServiceEntity;
import org.springframework.data.repository.CrudRepository;

public interface ServiceRepository extends CrudRepository<ServiceEntity, Integer> {
}
