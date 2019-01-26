package com.javafreelancedeveloper.projectmanagementtool.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Getter
@NoArgsConstructor
public class FieldValidationErrorResponseDTO extends HashMap<String, Collection<String>> {

    public FieldValidationErrorResponseDTO(String fieldName, String errorMessage) {
        addError(fieldName, errorMessage);
    }

    public void addError(String fieldName, String errorMessage) {
        Collection<String> fieldValidationErrors = this.get(fieldName);
        if (fieldValidationErrors == null) {
            fieldValidationErrors = new ArrayList<>();
            this.put(fieldName, fieldValidationErrors);
        }
        fieldValidationErrors.add(errorMessage);
    }

}
