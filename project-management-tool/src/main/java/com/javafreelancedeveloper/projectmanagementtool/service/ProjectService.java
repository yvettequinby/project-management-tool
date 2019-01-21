package com.javafreelancedeveloper.projectmanagementtool.service;

import com.javafreelancedeveloper.projectmanagementtool.domain.Project;
import com.javafreelancedeveloper.projectmanagementtool.dto.ProjectDTO;
import com.javafreelancedeveloper.projectmanagementtool.dto.ProjectListDTO;
import com.javafreelancedeveloper.projectmanagementtool.dto.SavedProjectResponseDTO;
import com.javafreelancedeveloper.projectmanagementtool.exception.ProjectCodeException;
import com.javafreelancedeveloper.projectmanagementtool.exception.ProjectIdException;
import com.javafreelancedeveloper.projectmanagementtool.exception.ProjectNotFoundException;
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

    public SavedProjectResponseDTO saveProject(ProjectDTO projectDTO) {
        Project project = toProject(projectDTO);
        try {
            project.setCode(project.getCode().toUpperCase());
            Project savedProject = projectRepository.save(project);
            ProjectDTO savedProjectDTO = toProjectDTO(savedProject);
            SavedProjectResponseDTO savedProjectResponseDTO = SavedProjectResponseDTO.builder()
                    .project(savedProjectDTO)
                    .build();
            return savedProjectResponseDTO;
        } catch (DataIntegrityViolationException e) {
            throw new ProjectCodeException("Project Code must be unique. A project with this code already exists.");
        }
    }

    public SavedProjectResponseDTO updateProject(ProjectDTO projectDTO) {
        if (projectDTO.getId() == null) {
            throw new ProjectIdException("Project Id is required for updates.");
        }
        Project project = projectRepository.findById(projectDTO.getId())
                .orElseThrow(() -> new ProjectIdException("No existing project found for supplied Project Id. Cannot update."));
        if (!project.getCode().equals(projectDTO.getCode())) {
            throw new ProjectCodeException("Project Code does not match expected value. Invalid input.");
        }
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        project.setStartDate(projectDTO.getStartDate());
        project.setEndDate(projectDTO.getEndDate());
        Project savedProject = projectRepository.save(project);
        ProjectDTO savedProjectDTO = toProjectDTO(savedProject);
        SavedProjectResponseDTO savedProjectResponseDTO = SavedProjectResponseDTO.builder()
                .project(savedProjectDTO)
                .build();
        return savedProjectResponseDTO;
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
