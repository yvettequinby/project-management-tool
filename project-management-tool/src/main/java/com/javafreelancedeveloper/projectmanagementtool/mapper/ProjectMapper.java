package com.javafreelancedeveloper.projectmanagementtool.mapper;

import com.javafreelancedeveloper.projectmanagementtool.domain.Project;
import com.javafreelancedeveloper.projectmanagementtool.dto.ProjectDTO;

public class ProjectMapper {

    public static ProjectDTO map(Project project) {
        return ProjectDTO.builder()
                .createdTimestamp(project.getCreatedTimestamp())
                .description(project.getDescription())
                .endDate(project.getEndDate())
                .code(project.getCode())
                .name(project.getName())
                .startDate(project.getStartDate())
                .updatedTimestamp(project.getUpdatedTimestamp())
                .id(project.getId())
                .build();
    }

    public static Project map(ProjectDTO projectDTO) {
        return Project.builder()
                .id(projectDTO.getId())
                .code(projectDTO.getCode())
                .name(projectDTO.getName())
                .description(projectDTO.getDescription())
                .startDate(projectDTO.getStartDate())
                .endDate(projectDTO.getEndDate())
                .build();
    }
}
