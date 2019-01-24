package com.javafreelancedeveloper.projectmanagementtool.exception;

import com.javafreelancedeveloper.projectmanagementtool.dto.FieldValidationErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationException extends RuntimeException {

    private final FieldValidationErrorResponseDTO fieldValidationErrorResponse;

    public ValidationException(FieldValidationErrorResponseDTO fieldValidationErrorResponse) {
        super("One or more fields have validation errors.");
        this.fieldValidationErrorResponse = fieldValidationErrorResponse;
    }

    public FieldValidationErrorResponseDTO getFieldValidationErrorResponse() {
        return fieldValidationErrorResponse;
    }

}
