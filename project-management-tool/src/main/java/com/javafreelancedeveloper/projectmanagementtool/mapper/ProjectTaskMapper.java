package com.javafreelancedeveloper.projectmanagementtool.mapper;

import com.javafreelancedeveloper.projectmanagementtool.domain.ProjectTask;
import com.javafreelancedeveloper.projectmanagementtool.dto.ProjectDTO;
import com.javafreelancedeveloper.projectmanagementtool.dto.ProjectTaskDTO;
import com.javafreelancedeveloper.projectmanagementtool.dto.ProjectTaskLiteDTO;

public class ProjectTaskMapper {

    private static void copyInto(ProjectTaskLiteDTO copyInto, ProjectTask copyFrom) {
        copyInto.setAcceptanceCriteria(copyFrom.getAcceptanceCriteria());
        copyInto.setCode(copyFrom.getCode());
        copyInto.setCreatedTimestamp(copyFrom.getCreatedTimestamp());
        copyInto.setDueDate(copyFrom.getDueDate());
        copyInto.setId(copyFrom.getId());
        copyInto.setPriority(copyFrom.getPriority());
        copyInto.setStatus(copyFrom.getStatus());
        copyInto.setSummary(copyFrom.getSummary());
        copyInto.setUpdatedTimestamp(copyFrom.getUpdatedTimestamp());
    }

    public static ProjectTaskDTO map(ProjectTask domain, ProjectDTO project) {
        ProjectTaskDTO projectTaskDTO = new ProjectTaskDTO();
        copyInto(projectTaskDTO, domain);
        projectTaskDTO.setProject(project);
        return projectTaskDTO;
    }

    public static ProjectTaskLiteDTO map(ProjectTask domain) {
        ProjectTaskLiteDTO projectTaskLiteDTO = new ProjectTaskLiteDTO();
        copyInto(projectTaskLiteDTO, domain);
        return projectTaskLiteDTO;
    }

    public static ProjectTask map(ProjectTaskLiteDTO dto) {
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
