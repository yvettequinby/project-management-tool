package com.javafreelancedeveloper.projectmanagementtool.dto;

import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.*;

@Getter
public abstract class AbstractValidationResponseDTO {

    protected final Map<String, Collection<String>> fieldValidationErrorMap = new HashMap<>();

    public void addFieldErrors(List<FieldError> fieldErrors) {
        fieldErrors.stream()
                .forEach(fe -> addFieldError(fe.getField(), fe.getDefaultMessage()));
    }

    public void addFieldError(String fieldName, String errorMessage) {
        Collection<String> fieldValidationErrors = fieldValidationErrorMap.get(fieldName);
        if (fieldValidationErrors == null) {
            fieldValidationErrors = new ArrayList<>();
            fieldValidationErrorMap.put(fieldName, fieldValidationErrors);
        }
        fieldValidationErrors.add(errorMessage);
    }

    public boolean isHasFieldValidationErrors() {
        return !fieldValidationErrorMap.isEmpty();
    }
}
