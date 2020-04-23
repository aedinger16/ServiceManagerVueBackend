package com.aedinger.employeeservice.manager;

import com.aedinger.employeeservice.daos.*;
import com.aedinger.employeeservice.database.EmployeeRepository;
import com.aedinger.employeeservice.database.ServiceRepository;
import com.aedinger.employeeservice.exceptions.EmployeeNotFoundException;
import com.aedinger.employeeservice.exceptions.ServiceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class EmployeeServiceManager {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ServiceRepository serviceRepository;


    // ------------------ EMPLOYEE ---------------------

    public List<Employee> getEmployees() {
        List<Employee> result = new ArrayList<>();

        for (EmployeeEntity employeeEntity : employeeRepository.findAll()) {
            result.add(convertEmployeeEntityToEmployee(employeeEntity));
        }

        return result;
    }

    public Employee getEmployee(int employeeId) {
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(employeeId);
        if (serviceRepository.findById(employeeId).isPresent()) {
            return convertEmployeeEntityToEmployee(employeeEntity.get());
        } else {
            throw new ServiceNotFoundException(String.format("The employee with the id %d does not exist", employeeId));
        }
    }

    public Employee addEmployee(Employee newEmployee) {
        EmployeeEntity storedEmployee = employeeRepository.save(convertEmployeeToEmployeeEntity(newEmployee));
        return convertEmployeeEntityToEmployee(storedEmployee);
    }

    public Employee deleteEmployee(int employeeId) {
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(employeeId);
        if (employeeEntity.isPresent()) {
            employeeRepository.deleteById(employeeId);
        } else {
            throw new ServiceNotFoundException(String.format("The employee with the id %d does not exist", employeeId));
        }
        return convertEmployeeEntityToEmployee(employeeEntity.get());
    }

    public Employee updateEmployee(int employeeId, Employee employee) {

        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(employeeId);
        EmployeeEntity editEmployeeEntity;

        if (employeeEntity.isPresent()) {
            editEmployeeEntity = employeeEntity.get();
            editEmployeeEntity.setName(employee.getName());
            editEmployeeEntity.setLatitude(employee.getLatitude());
            editEmployeeEntity.setLongitude(employee.getLongitude());

            EmployeeEntity newEmployee = employeeRepository.save(editEmployeeEntity);
            return convertEmployeeEntityToEmployee(newEmployee);
        } else {
            throw new EmployeeNotFoundException(String.format("The employee with the id %d was not found", employee.getId()));
        }
    }


    // ------------------ SERVICE ---------------------


    public List<Service> getServices() {
        List<Service> result = new ArrayList<>();

        for (ServiceEntity serviceEntity : serviceRepository.findAll()) {
            result.add(convertServiceEntityToService(serviceEntity));
        }

        return result;
    }

    public Service getService(int serviceId) {
        Optional<ServiceEntity> serviceEntity = serviceRepository.findById(serviceId);
        if (serviceRepository.findById(serviceId).isPresent()) {
            return convertServiceEntityToService(serviceEntity.get());
        } else {
            throw new ServiceNotFoundException(String.format("The service with the id %d does not exist", serviceId));
        }
    }

    public Service addService(Service newService) {
        ServiceEntity storedService = serviceRepository.save(convertServiceToServiceEntity(newService));
        return convertServiceEntityToService(storedService);
    }

    public Service deleteService(int serviceId) {
        Optional<ServiceEntity> serviceEntity = serviceRepository.findById(serviceId);
        if (serviceEntity.isPresent()) {
            serviceRepository.deleteById(serviceId);
        } else {
            throw new ServiceNotFoundException(String.format("The service with the id %d does not exist", serviceId));
        }
        return convertServiceEntityToService(serviceEntity.get());
    }

    public Service updateService(int serviceId, Service service) {

        Optional<ServiceEntity> serviceEntity = serviceRepository.findById(serviceId);
        ServiceEntity editServiceEntity;
        if (serviceEntity.isPresent()) {

            editServiceEntity = serviceEntity.get();
            Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(service.getEmployeeId());

            if (employeeEntity.isPresent()) {
                editServiceEntity.setEmployeeEntity(employeeEntity.get());
                editServiceEntity.setDate(service.getDate());
                editServiceEntity.setLatitude(service.getLatitude());
                editServiceEntity.setLongitude(service.getLongitude());
                editServiceEntity.setName(service.getName());

                ServiceEntity newService = serviceRepository.save(editServiceEntity);
                return convertServiceEntityToService(newService);
            } else {
                throw new EmployeeNotFoundException(String.format("The employee with the id %d was not found", service.getEmployeeId()));
            }
        } else {
            throw new ServiceNotFoundException(String.format("The service with the id %d does not exist", serviceId));
        }
    }

    public LongitudeLatitude getServiceAddress(int serviceId) {
        Optional<ServiceEntity> serviceEntity = serviceRepository.findById(serviceId);
        if (serviceRepository.findById(serviceId).isPresent()) {
            return convertServiceEntityToLongitudeLatitude(serviceEntity.get());
        } else {
            throw new ServiceNotFoundException(String.format("The service with the id %d does not exist", serviceId));
        }
    }

    // ------------------ CONVERTER ---------------------

    public LongitudeLatitude convertServiceEntityToLongitudeLatitude(ServiceEntity serviceEntity){
        LongitudeLatitude location = new LongitudeLatitude();
        location.setLongitude(serviceEntity.getLongitude());
        location.setLatitude(serviceEntity.getLatitude());
        return location;
    }

    public Service convertServiceEntityToService(ServiceEntity serviceEntity) {
        Service service = new Service();
        service.setId(serviceEntity.getId());
        service.setEmployeeId(serviceEntity.getEmployeeEntity().getId());
        service.setDate(serviceEntity.getDate());
        service.setName(serviceEntity.getName());
        service.setLatitude(serviceEntity.getLatitude());
        service.setLongitude(serviceEntity.getLongitude());
        return service;
    }

    public ServiceEntity convertServiceToServiceEntity(Service service) {
        ServiceEntity serviceEntity = new ServiceEntity();

        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(service.getEmployeeId());

        if (employeeEntity.isPresent()) {
            serviceEntity.setEmployeeEntity(employeeEntity.get());
            serviceEntity.setDate(service.getDate());
            serviceEntity.setLatitude(service.getLatitude());
            serviceEntity.setLongitude(service.getLongitude());
            serviceEntity.setName(service.getName());
        } else {
            throw new EmployeeNotFoundException(String.format("The employee with the id %d was not found", service.getEmployeeId()));
        }
        return serviceEntity;
    }

    public Employee convertEmployeeEntityToEmployee(EmployeeEntity employeeEntity) {
        Employee employee = new Employee();
        employee.setId(employeeEntity.getId());
        employee.setName(employeeEntity.getName());
        employee.setLatitude(employeeEntity.getLatitude());
        employee.setLongitude(employeeEntity.getLongitude());
        return employee;
    }

    public EmployeeEntity convertEmployeeToEmployeeEntity(Employee employee) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setId(employee.getId());
        employeeEntity.setName(employee.getName());
        employeeEntity.setLatitude(employee.getLatitude());
        employeeEntity.setLongitude(employee.getLongitude());

        return employeeEntity;
    }

    public boolean employeeExists(int employeeId) {
        return employeeRepository.findById(employeeId).isPresent();
    }

}
