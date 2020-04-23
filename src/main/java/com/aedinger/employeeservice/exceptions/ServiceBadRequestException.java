package com.aedinger.employeeservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ServiceBadRequestException extends RuntimeException {
    public ServiceBadRequestException(String message) {
        super(message);
    }
}
