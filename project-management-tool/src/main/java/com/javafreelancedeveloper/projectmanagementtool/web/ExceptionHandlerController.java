package com.javafreelancedeveloper.projectmanagementtool.web;

import com.javafreelancedeveloper.projectmanagementtool.dto.FieldValidationErrorResponseDTO;
import com.javafreelancedeveloper.projectmanagementtool.dto.HandledExceptionResponseDTO;
import com.javafreelancedeveloper.projectmanagementtool.exception.HandledException;
import com.javafreelancedeveloper.projectmanagementtool.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestController
@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<FieldValidationErrorResponseDTO> handleValidationException(ValidationException exception, WebRequest webRequest) {
        return new ResponseEntity<>(exception.getFieldValidationErrorResponse(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<HandledExceptionResponseDTO> handleHandledException(HandledException handledException, WebRequest webRequest) {
        HandledExceptionResponseDTO handledExceptionResponseDTO = new HandledExceptionResponseDTO(handledException.getMessage());
        return new ResponseEntity<>(handledExceptionResponseDTO, HttpStatus.BAD_REQUEST);
    }
}
