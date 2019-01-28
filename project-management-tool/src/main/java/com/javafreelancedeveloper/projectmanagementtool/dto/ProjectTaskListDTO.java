package com.javafreelancedeveloper.projectmanagementtool.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProjectTaskListDTO {

    private ProjectDTO project;
    private List<ProjectTaskDTO> projectTasks;
}
