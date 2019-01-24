package com.javafreelancedeveloper.projectmanagementtool.web;

import com.javafreelancedeveloper.projectmanagementtool.dto.FieldValidationErrorResponseDTO;
import com.javafreelancedeveloper.projectmanagementtool.exception.ValidationException;
import org.springframework.validation.BindingResult;

public abstract class BaseController {

    public void validate(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            final FieldValidationErrorResponseDTO fieldValidationErrorResponse = new FieldValidationErrorResponseDTO();
            bindingResult.getFieldErrors().stream()
                    .forEach(fe -> fieldValidationErrorResponse.addError(fe.getField(), fe.getDefaultMessage()));
            throw new ValidationException(fieldValidationErrorResponse);
        }
    }
}
