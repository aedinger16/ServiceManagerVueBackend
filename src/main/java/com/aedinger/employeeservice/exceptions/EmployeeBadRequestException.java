package com.aedinger.employeeservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EmployeeBadRequestException extends RuntimeException {
    public EmployeeBadRequestException(String message) {
        super(message);
    }
}