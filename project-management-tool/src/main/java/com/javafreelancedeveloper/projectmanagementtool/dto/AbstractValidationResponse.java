package com.javafreelancedeveloper.projectmanagementtool.dto;

import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public abstract class AbstractValidationResponse {

    protected final Map<String, String> fieldValidationErrorMap = new HashMap<>();

    public void addFieldErrors(List<FieldError> fieldErrors) {
        fieldErrors.stream().forEach(fe -> fieldValidationErrorMap.put(fe.getField(), fe.getDefaultMessage()));
    }

    public boolean isHasFieldValidationErrors() {
        return !fieldValidationErrorMap.isEmpty();
    }
}
