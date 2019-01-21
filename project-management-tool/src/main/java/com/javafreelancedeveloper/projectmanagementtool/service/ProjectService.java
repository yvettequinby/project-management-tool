package com.javafreelancedeveloper.projectmanagementtool.service;

import com.javafreelancedeveloper.projectmanagementtool.domain.Project;
import com.javafreelancedeveloper.projectmanagementtool.dto.CreateProjectResponseDTO;
import com.javafreelancedeveloper.projectmanagementtool.dto.ProjectDTO;
import com.javafreelancedeveloper.projectmanagementtool.exception.ProjectIdentifierException;
import com.javafreelancedeveloper.projectmanagementtool.exception.ProjectNotFoundException;
import com.javafreelancedeveloper.projectmanagementtool.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public CreateProjectResponseDTO saveProject(ProjectDTO projectDTO) {
        // TODO: logic
        Project project = Project.builder()
                .projectIdentifier(projectDTO.getProjectIdentifier())
                .projectName(projectDTO.getProjectName())
                .description(projectDTO.getProjectDescription())
                .startDate(projectDTO.getProjectStartDate())
                .endDate(projectDTO.getProjectEndDate())
                .build();
        Project savedProject = null;
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            savedProject = projectRepository.save(project);
        } catch (DataIntegrityViolationException e) {
            throw new ProjectIdentifierException("Project Identifier must be unique. A project with this identifier already exists.");
        }
        ProjectDTO savedProjectDTO = toProjectDTO(savedProject);
        CreateProjectResponseDTO createProjectResponse = CreateProjectResponseDTO.builder()
                .project(savedProjectDTO)
                .build();
        return createProjectResponse;

    }

    public ProjectDTO findByProjectIdentifier(String projectIdentifier) {
        Project project = projectRepository.findByProjectIdentifier(projectIdentifier);
        if (project == null) {
            throw new ProjectNotFoundException("No project found with identifier [" + projectIdentifier + "]");
        }
        return toProjectDTO(project);

    }

    private ProjectDTO toProjectDTO(Project project) {
        return ProjectDTO.builder()
                .projectCreatedTimestamp(project.getCreatedTimestamp())
                .projectDescription(project.getDescription())
                .projectEndDate(project.getEndDate())
                .projectIdentifier(project.getProjectIdentifier())
                .projectName(project.getProjectName())
                .projectStartDate(project.getStartDate())
                .projectUpdatedTimestamp(project.getUpdatedTimestamp())
                .projectId(project.getId())
                .build();
    }

}
