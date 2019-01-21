package com.javafreelancedeveloper.projectmanagementtool.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Builder
public class ProjectDTO {

    private Long projectId;
    @NotBlank(message = "Project Name is required")
    private String projectName;
    @NotBlank(message = "Project Identifier is required")
    @Size(min = 4, max = 5, message = "Project Identifier must be 4 or 5 characters long.")
    private String projectIdentifier;
    @NotBlank(message = "Project Description is required")
    private String projectDescription;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date projectStartDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date projectEndDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSSZ")
    private Date projectCreatedTimestamp;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSSZ")
    private Date projectUpdatedTimestamp;
}
