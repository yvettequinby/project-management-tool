package com.javafreelancedeveloper.projectmanagementtool.exception;

public class ProjectIdentifierException extends FieldException {

    public static final String FIELD_NAME = "Project Identifier";

    public ProjectIdentifierException(String message) {
        super(message);
    }

    @Override
    public String getFieldName() {
        return FIELD_NAME;
    }

}
