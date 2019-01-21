package com.javafreelancedeveloper.projectmanagementtool.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@Builder
public class SavedProjectResponseDTO extends AbstractValidationResponseDTO {

    private ProjectDTO project;

    public static SavedProjectResponseDTO buildErrorResponse(List<FieldError> fieldErrors, ProjectDTO project) {
        SavedProjectResponseDTO createProjectResponse = SavedProjectResponseDTO.builder()
                .project(project)
                .build();
        createProjectResponse.addFieldErrors(fieldErrors);
        return createProjectResponse;

    }
}
