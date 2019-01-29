package com.javafreelancedeveloper.projectmanagementtool.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProjectTaskLiteDTO {

    protected Long id;
    protected String code;
    @NotBlank(message = "Project Task Summary is required.")
    protected String summary;
    protected String acceptanceCriteria;
    protected String status;
    protected Integer priority;
    @JsonFormat(pattern = "yyyy-MM-dd")
    protected Date dueDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSZ")
    protected Date createdTimestamp;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSZ")
    protected Date updatedTimestamp;
}
