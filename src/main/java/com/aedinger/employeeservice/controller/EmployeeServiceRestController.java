package com.aedinger.employeeservice.controller;

import com.aedinger.employeeservice.daos.*;
import com.aedinger.employeeservice.services.EmployeeServiceDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/serviceBackend")
public class EmployeeServiceRestController {

    @Autowired
    private EmployeeServiceDataService empServiceDataService;

    // ---------------------- EMPLOYEES -----------------------

    @RequestMapping(value = "/employees", method = RequestMethod.GET)
    public List<EmployeeResource> getAllEmployees(){
        return empServiceDataService.getEmployees();
    }

    @RequestMapping(value = "/employees", method = RequestMethod.POST)
    public EmployeeResource addEmployee(@RequestBody EmployeeDto employeeDto){
        return empServiceDataService.addEmployee(employeeDto);
    }

    @RequestMapping(value = "/employees/{employeeId}", method = RequestMethod.DELETE)
    public EmployeeResource deleteEmployee(@PathVariable int employeeId){
        return empServiceDataService.deleteEmployee(employeeId);
    }

    @RequestMapping(value = "/employees/{employeeId}", method = RequestMethod.GET)
    public EmployeeResource getEmployee(@PathVariable int employeeId){
        return empServiceDataService.getEmployee(employeeId);
    }

    @RequestMapping(value = "/employees/{employeeId}", method = RequestMethod.PUT)
    public EmployeeResource updateService(@PathVariable int employeeId, @RequestBody EmployeeDto employeeDto){
        return empServiceDataService.updateEmployee(employeeId, employeeDto);
    }

    @RequestMapping(value = "/services", method = RequestMethod.GET)
    public List<ServiceResource> getAllServices(){
        return empServiceDataService.getServices();
    }

    @RequestMapping(value = "/services", method = RequestMethod.POST)
    public ServiceResource addService(@RequestBody ServiceDto serviceDto){
        ServiceResource serviceResource = empServiceDataService.addService(serviceDto);
        return serviceResource;
    }


    @RequestMapping(value = "/services/{serviceId}", method = RequestMethod.DELETE)
    public ServiceResource deleteService(@PathVariable int serviceId){
        return empServiceDataService.deleteService(serviceId);
    }

    @RequestMapping(value = "/services/{serviceId}", method = RequestMethod.GET)
    public ServiceResource getService(@PathVariable int serviceId){
        return empServiceDataService.getService(serviceId);
    }

    @RequestMapping(value = "/services/{serviceId}", method = RequestMethod.PUT)
    public ServiceResource updateService(@PathVariable int serviceId, @RequestBody ServiceDto serviceDto){
        return empServiceDataService.updateService(serviceId, serviceDto);
    }

    @RequestMapping(value = "/services/{serviceId}/address", method = RequestMethod.PUT)
    public AddressResource getServiceAddress(@PathVariable int serviceId){
        return empServiceDataService.getServiceAddress(serviceId);
    }

}
