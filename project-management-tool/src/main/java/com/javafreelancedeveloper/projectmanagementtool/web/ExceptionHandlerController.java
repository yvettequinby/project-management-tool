package com.javafreelancedeveloper.projectmanagementtool.web;

import com.javafreelancedeveloper.projectmanagementtool.dto.FieldExceptionResponseDTO;
import com.javafreelancedeveloper.projectmanagementtool.dto.HandledExceptionResponseDTO;
import com.javafreelancedeveloper.projectmanagementtool.exception.FieldException;
import com.javafreelancedeveloper.projectmanagementtool.exception.HandledException;
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
    public ResponseEntity<FieldExceptionResponseDTO> handleFieldException(FieldException fieldException, WebRequest webRequest) {
        FieldExceptionResponseDTO fieldExceptionResponse = new FieldExceptionResponseDTO();
        fieldExceptionResponse.addFieldExceptionMessage(fieldException.getFieldName(), fieldException.getMessage());
        return new ResponseEntity<>(fieldExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<HandledExceptionResponseDTO> handleHandledException(HandledException handledException, WebRequest webRequest) {
        HandledExceptionResponseDTO handledExceptionResponseDTO = new HandledExceptionResponseDTO(handledException.getMessage());
        return new ResponseEntity<>(handledExceptionResponseDTO, HttpStatus.BAD_REQUEST);
    }
}
