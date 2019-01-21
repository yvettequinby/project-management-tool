package com.javafreelancedeveloper.projectmanagementtool.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class HandledException extends RuntimeException {

    public HandledException(String message) {
        super(message);
    }
}
