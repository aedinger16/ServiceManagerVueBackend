package com.aedinger.employeeservice.services;

import com.aedinger.employeeservice.daos.*;
import com.aedinger.employeeservice.exceptions.EmployeeBadRequestException;
import com.aedinger.employeeservice.exceptions.EmployeeNotFoundException;
import com.aedinger.employeeservice.exceptions.ServiceBadRequestException;
import com.aedinger.employeeservice.exceptions.ServiceNotFoundException;
import com.aedinger.employeeservice.manager.EmployeeServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeServiceDataService {

    @Autowired
    private LocationIQDataService locationIQDataService;

    @Autowired
    EmployeeServiceManager employeeServiceManager;

    // ----------------- EMPLOYEES ------------------

    public List<EmployeeResource> getEmployees() {
        List<EmployeeResource> employeeResources = new ArrayList<>();
        for (Employee item : employeeServiceManager.getEmployees()) {
            employeeResources.add(convertEmployeeToEmployeeResource(item));
        }
        return employeeResources;
    }

    public EmployeeResource addEmployee(EmployeeDto employeeDto) {
        return convertEmployeeToEmployeeResource(employeeServiceManager.addEmployee(convertEmployeeDtoToEmployee(employeeDto)));
    }

    public EmployeeResource deleteEmployee(int employeeId) {
        Employee employee = employeeServiceManager.deleteEmployee(employeeId);
        return convertEmployeeToEmployeeResource(employee);
    }

    public EmployeeResource getEmployee(int employeeId) {
        Employee employee = employeeServiceManager.getEmployee(employeeId);
        return convertEmployeeToEmployeeResource(employee);
    }

    public EmployeeResource updateEmployee(int employeeId, EmployeeDto employeeDto) {
        Employee employee = employeeServiceManager.updateEmployee(employeeId, convertEmployeeDtoToEmployee(employeeDto));
        return convertEmployeeToEmployeeResource(employee);
    }

    // ----------------- SERVICES ------------------

    public List<ServiceResource> getServices() {
        List<ServiceResource> serviceResources = new ArrayList<>();
        for (Service item : employeeServiceManager.getServices()) {
            serviceResources.add(convertServiceToServiceResource(item));
        }
        return serviceResources;
    }

    public ServiceResource addService(ServiceDto serviceDto) {
        checkService(serviceDto);
        return convertServiceToServiceResource(employeeServiceManager.addService(convertServiceDtoToService(serviceDto)));
    }

    public ServiceResource deleteService(int serviceId) {
        Service service = employeeServiceManager.deleteService(serviceId);
        return convertServiceToServiceResource(service);
    }

    public ServiceResource getService(int serviceId) {
        Service service = employeeServiceManager.getService(serviceId);
        return convertServiceToServiceResource(service);
    }

    public ServiceResource updateService(int serviceId, ServiceDto serviceDto) {
        checkService(serviceDto);
        Service service = employeeServiceManager.updateService(serviceId, convertServiceDtoToService(serviceDto));
        return convertServiceToServiceResource(service);
    }

    public AddressResource getServiceAddress(int serviceId) {
        LongitudeLatitude location = employeeServiceManager.getServiceAddress(serviceId);
        return convertLongitudeLatitudeToLocation(location);
    }

    // ----------------- CONVERTER ------------------

    private AddressResource convertLongitudeLatitudeToLocation(LongitudeLatitude longitudeLatitude){
        AddressResource location = new AddressResource();
        location.setAddress(locationIQDataService.getAddress(longitudeLatitude.getLongitude(), longitudeLatitude.getLatitude()));
        return location;
    }

    private ServiceResource convertServiceToServiceResource(Service service) {
        ServiceResource serviceResource = new ServiceResource();
        serviceResource.setId(service.getId());
        serviceResource.setAddress(locationIQDataService.getAddress(service.getLongitude(), service.getLatitude()));
        serviceResource.setDate(service.getDate());
        serviceResource.setName(service.getName());
        serviceResource.setLatitude(service.getLatitude());
        serviceResource.setLongitude(service.getLongitude());
        serviceResource.setEmployee(employeeServiceManager.getEmployee(service.getEmployeeId()));

        return serviceResource;
    }

    private Employee convertEmployeeDtoToEmployee(EmployeeDto employeeDto){
        Employee employee = new Employee();
        employee.setName(employeeDto.getName());
        LongitudeLatitude longitudeLatitude = locationIQDataService.getLongitudeLatitudeByAddress(employeeDto.getAddress());
        employee.setLongitude(longitudeLatitude.getLongitude());
        employee.setLatitude(longitudeLatitude.getLatitude());

        return employee;
    }

    private Service convertServiceDtoToService(ServiceDto serviceDto) {
        Service service = new Service();
        service.setEmployeeId(serviceDto.getEmployee_id());
        LongitudeLatitude longitudeLatitude = locationIQDataService.getLongitudeLatitudeByAddress(serviceDto.getAddress());
        service.setName(serviceDto.getName());
        service.setDate(serviceDto.getDate());
        service.setLatitude(longitudeLatitude.getLatitude());
        service.setLongitude(longitudeLatitude.getLongitude());
        return service;
    }

    private EmployeeResource convertEmployeeToEmployeeResource(Employee employee) {
        EmployeeResource employeeResource = new EmployeeResource();
        employeeResource.setId(employee.getId());
        employeeResource.setName(employee.getName());
        employeeResource.setAddress(locationIQDataService.getAddress(employee.getLongitude(), employee.getLatitude()));
        employeeResource.setLatitude(employee.getLatitude());
        employeeResource.setLongitude(employee.getLongitude());

        return employeeResource;
    }

    private void checkService(ServiceDto serviceDto){
        if(isNullOrEmpty(serviceDto.getName())){
            throw new ServiceBadRequestException("Name must be set");
        }
        if(serviceDto.getName().length() <= 4){
            throw new ServiceBadRequestException("The name must be longer");
        }
        if(!employeeServiceManager.employeeExists(serviceDto.getEmployee_id())){
            throw new EmployeeNotFoundException(String.format("The employee with the id %d does not exist", serviceDto.getEmployee_id()));
        }
        if(isNullOrEmpty(serviceDto.getDate())){
            throw new ServiceBadRequestException("The date must be set");
        }
        if(!checkDate(serviceDto.getDate())){
            throw new ServiceNotFoundException("The date must be set correct");
        }
        if(isNullOrEmpty(serviceDto.getAddress())){
            throw new ServiceBadRequestException("The address must be set");
        }
        if(serviceDto.getAddress().length() < 6){
            throw new ServiceBadRequestException("The address must be longer");
        }
    }

    private boolean checkDate(String date) {
        try{
            LocalDateTime ldt = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        }catch (Exception e){
            return false;
        }

        return true;
    }

    private void checkEmployeeDto(EmployeeDto employeeDto){
        if(isNullOrEmpty(employeeDto.getAddress())){
            throw new EmployeeBadRequestException("The address must be set");
        }
        if(employeeDto.getName().length() < 5){
            throw new EmployeeBadRequestException("The name must be longer than 4 characters");
        }
    }

    private boolean isNullOrEmpty(String string) {
        return string == null || string.equals("");
    }
}
