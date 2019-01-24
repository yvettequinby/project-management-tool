package com.javafreelancedeveloper.projectmanagementtool.service;

import com.javafreelancedeveloper.projectmanagementtool.domain.Project;
import com.javafreelancedeveloper.projectmanagementtool.dto.FieldValidationErrorResponseDTO;
import com.javafreelancedeveloper.projectmanagementtool.dto.ProjectDTO;
import com.javafreelancedeveloper.projectmanagementtool.dto.ProjectListDTO;
import com.javafreelancedeveloper.projectmanagementtool.exception.ProjectNotFoundException;
import com.javafreelancedeveloper.projectmanagementtool.exception.ValidationException;
import com.javafreelancedeveloper.projectmanagementtool.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectDTO saveProject(ProjectDTO projectDTO) {
        Project project = toProject(projectDTO);
        try {
            project.setCode(project.getCode().toUpperCase());
            Project savedProject = projectRepository.save(project);
            ProjectDTO savedProjectDTO = toProjectDTO(savedProject);
            return savedProjectDTO;
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException(new FieldValidationErrorResponseDTO("code", "Project Code must be unique. A project with this code already exists."));
        }
    }

    public ProjectDTO updateProject(ProjectDTO projectDTO) {
        if (projectDTO.getId() == null) {
            throw new ValidationException(new FieldValidationErrorResponseDTO("id", "Project Id is required for updates."));
        }
        Project project = projectRepository.findById(projectDTO.getId())
                .orElseThrow(() -> new ValidationException(new FieldValidationErrorResponseDTO("id", "Could not retrieve existing record for provided Project Id.")));
        if (!project.getCode().equals(projectDTO.getCode())) {
            throw new ValidationException(new FieldValidationErrorResponseDTO("code", "Project Code is inconsistent with id. Invalid input."));
        }
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        project.setStartDate(projectDTO.getStartDate());
        project.setEndDate(projectDTO.getEndDate());
        Project savedProject = projectRepository.save(project);
        ProjectDTO savedProjectDTO = toProjectDTO(savedProject);
        return savedProjectDTO;
    }

    public ProjectDTO findByProjectCode(String projectCode) {
        Project project = projectRepository.findByCode(projectCode);
        if (project == null) {
            throw new ProjectNotFoundException("No project found with code [" + projectCode + "]");
        }
        return toProjectDTO(project);

    }

    public ProjectListDTO listProjects() {
        List<ProjectDTO> projectList = new ArrayList<>();
        Iterable<Project> projects = projectRepository.findAll();
        projects.forEach(p -> projectList.add(toProjectDTO(p)));
        return new ProjectListDTO(projectList);

    }

    public void deleteProject(String projectCode) {
        Project project = projectRepository.findByCode(projectCode.toUpperCase());
        if (project == null) {
            throw new ProjectNotFoundException("No project found with code [" + projectCode + "]");
        }
        projectRepository.delete(project);
    }

    private ProjectDTO toProjectDTO(Project project) {
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

    private Project toProject(ProjectDTO projectDTO) {
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
