package com.javafreelancedeveloper.projectmanagementtool.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public abstract class FieldException extends RuntimeException {

    public FieldException(String message) {
        super(message);
    }

    public abstract String getFieldName();
}
