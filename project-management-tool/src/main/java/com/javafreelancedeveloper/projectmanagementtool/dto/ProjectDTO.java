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

    private Long id;
    @NotBlank(message = "Project Name is required")
    private String name;
    @NotBlank(message = "Project Code is required")
    @Size(min = 4, max = 5, message = "Project Code must be 4 or 5 characters long.")
    private String code;
    @NotBlank(message = "Project Description is required")
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSZ")
    private Date createdTimestamp;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSZ")
    private Date updatedTimestamp;
}
