package com.javafreelancedeveloper.projectmanagementtool.dto;

import lombok.Getter;

@Getter
public class FieldExceptionResponseDTO extends AbstractValidationResponseDTO {

    public void addFieldExceptionMessage(String fieldName, String message) {
        super.addFieldError(fieldName, message);
    }
}
