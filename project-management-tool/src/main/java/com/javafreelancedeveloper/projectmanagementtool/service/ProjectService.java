package com.javafreelancedeveloper.projectmanagementtool.service;

import com.javafreelancedeveloper.projectmanagementtool.domain.Project;
import com.javafreelancedeveloper.projectmanagementtool.dto.CreateProjectRequest;
import com.javafreelancedeveloper.projectmanagementtool.dto.CreateProjectResponse;
import com.javafreelancedeveloper.projectmanagementtool.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public CreateProjectResponse saveProject(CreateProjectRequest createProjectRequest) {
        // TODO: logic
        Project project = Project.builder()
                .projectIdentifier(createProjectRequest.getProjectIdentifier())
                .projectName(createProjectRequest.getProjectName())
                .description(createProjectRequest.getProjectDescription())
                .startDate(createProjectRequest.getProjectStartDate())
                .endDate(createProjectRequest.getProjectEndDate())
                .build();
        Project savedProject = projectRepository.save(project);
        CreateProjectResponse createProjectResponse = CreateProjectResponse.builder().projectCreatedTimestamp(savedProject.getCreatedTimestamp())
                .projectDescription(savedProject.getDescription())
                .projectEndDate(savedProject.getEndDate())
                .projectIdentifier(savedProject.getProjectIdentifier())
                .projectName(savedProject.getProjectName())
                .projectStartDate(savedProject.getStartDate())
                .projectUpdatedTimestamp(savedProject.getUpdatedTimestamp())
                .projectId(savedProject.getId())
                .build();
        return createProjectResponse;

    }

}
