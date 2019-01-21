package com.javafreelancedeveloper.projectmanagementtool.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@Builder
public class CreateProjectResponseDTO extends AbstractValidationResponseDTO {

    private ProjectDTO project;

    public static CreateProjectResponseDTO buildErrorResponse(List<FieldError> fieldErrors, ProjectDTO project) {
        CreateProjectResponseDTO createProjectResponse = CreateProjectResponseDTO.builder()
                .project(project)
                .build();
        createProjectResponse.addFieldErrors(fieldErrors);
        return createProjectResponse;

    }
}
