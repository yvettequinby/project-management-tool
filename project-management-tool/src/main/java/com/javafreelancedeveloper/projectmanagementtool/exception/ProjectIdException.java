package com.javafreelancedeveloper.projectmanagementtool.exception;

public class ProjectIdException extends FieldException {

    public static final String FIELD_NAME = "id";

    public ProjectIdException(String message) {
        super(message);
    }

    @Override
    public String getFieldName() {
        return FIELD_NAME;
    }
}
