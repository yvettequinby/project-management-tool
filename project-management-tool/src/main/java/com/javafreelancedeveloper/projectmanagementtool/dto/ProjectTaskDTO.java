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
public class ProjectTaskDTO {

    private Long id;
    private String code;
    @NotBlank(message = "Project Task Summary is required.")
    private String summary;
    private String acceptanceCriteria;
    private String status;
    private Integer priority;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dueDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSZ")
    private Date createdTimestamp;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSZ")
    private Date updatedTimestamp;
}
