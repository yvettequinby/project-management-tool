package com.javafreelancedeveloper.projectmanagementtool.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.Date;
import java.util.List;

@Getter
@Builder
public class CreateProjectResponse extends AbstractValidationResponse {

    private Long projectId;
    private String projectName;
    private String projectIdentifier;
    private String projectDescription;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date projectStartDate;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date projectEndDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSSZ")
    private Date projectCreatedTimestamp;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSSZ")
    private Date projectUpdatedTimestamp;

    public static CreateProjectResponse buildErrorResponse(List<FieldError> fieldErrors, CreateProjectRequest request) {
        CreateProjectResponse createProjectResponse = CreateProjectResponse.builder()
                .projectName(request.getProjectName())
                .projectDescription(request.getProjectDescription())
                .projectIdentifier(request.getProjectIdentifier())
                .projectStartDate(request.getProjectStartDate())
                .projectEndDate(request.getProjectEndDate())
                .build();
        createProjectResponse.addFieldErrors(fieldErrors);
        return createProjectResponse;

    }
}
