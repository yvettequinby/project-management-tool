package com.javafreelancedeveloper.projectmanagementtool.mapper;

import com.javafreelancedeveloper.projectmanagementtool.domain.ProjectTask;
import com.javafreelancedeveloper.projectmanagementtool.dto.ProjectTaskDTO;

public class ProjectTaskMapper {

    public static ProjectTaskDTO map(ProjectTask domain) {
        return ProjectTaskDTO.builder()
                .acceptanceCriteria(domain.getAcceptanceCriteria())
                .code(domain.getCode())
                .createdTimestamp(domain.getCreatedTimestamp())
                .dueDate(domain.getDueDate())
                .id(domain.getId())
                .priority(domain.getPriority())
                .status(domain.getStatus())
                .summary(domain.getSummary())
                .updatedTimestamp(domain.getUpdatedTimestamp())
                .build();
    }

    public static ProjectTask map(ProjectTaskDTO dto) {
        return ProjectTask.builder()
                .acceptanceCriteria(dto.getAcceptanceCriteria())
                .code(dto.getCode())
                .dueDate(dto.getDueDate())
                .id(dto.getId())
                .priority(dto.getPriority())
                .status(dto.getStatus())
                .summary(dto.getSummary())
                .build();
    }
}
