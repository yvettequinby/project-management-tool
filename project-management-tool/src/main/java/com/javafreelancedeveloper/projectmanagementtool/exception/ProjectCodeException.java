package com.javafreelancedeveloper.projectmanagementtool.exception;

public class ProjectCodeException extends FieldException {

    public static final String FIELD_NAME = "code";

    public ProjectCodeException(String message) {
        super(message);
    }

    @Override
    public String getFieldName() {
        return FIELD_NAME;
    }

}
