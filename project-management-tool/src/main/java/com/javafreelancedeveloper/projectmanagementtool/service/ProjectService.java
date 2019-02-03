package com.javafreelancedeveloper.projectmanagementtool.service;

import com.javafreelancedeveloper.projectmanagementtool.domain.Project;
import com.javafreelancedeveloper.projectmanagementtool.domain.User;
import com.javafreelancedeveloper.projectmanagementtool.dto.FieldValidationErrorResponseDTO;
import com.javafreelancedeveloper.projectmanagementtool.dto.ProjectDTO;
import com.javafreelancedeveloper.projectmanagementtool.dto.ProjectListDTO;
import com.javafreelancedeveloper.projectmanagementtool.exception.ProjectNotFoundException;
import com.javafreelancedeveloper.projectmanagementtool.exception.UserNotFoundException;
import com.javafreelancedeveloper.projectmanagementtool.exception.ValidationException;
import com.javafreelancedeveloper.projectmanagementtool.mapper.ProjectMapper;
import com.javafreelancedeveloper.projectmanagementtool.repository.ProjectRepository;
import com.javafreelancedeveloper.projectmanagementtool.repository.UserRepository;
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
    private final UserRepository userRepository;


    public ProjectDTO saveProject(ProjectDTO projectDTO, String username) {
        Project project = ProjectMapper.map(projectDTO);
        try {
            User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found for provided username."));
            project.setCode(project.getCode().toUpperCase());
            project.setUser(user);
            Project savedProject = projectRepository.save(project);
            ProjectDTO savedProjectDTO = ProjectMapper.map(savedProject);
            return savedProjectDTO;
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException(new FieldValidationErrorResponseDTO("code", "Project Code must be unique. A project with this code already exists."));
        }
    }


    public ProjectDTO updateProject(ProjectDTO projectDTO, String username) {
        if (projectDTO.getId() == null) {
            throw new ValidationException(new FieldValidationErrorResponseDTO("id", "Project Id is required for updates."));
        }
        Project project = projectRepository.findById(projectDTO.getId())
                .orElseThrow(() -> new ValidationException(new FieldValidationErrorResponseDTO("id", "Could not retrieve existing record for provided Project Id.")));
        if (!project.getCode().equals(projectDTO.getCode())) {
            throw new ValidationException(new FieldValidationErrorResponseDTO("code", "Project Code is inconsistent with id. Invalid input."));
        }
        validateAuthorisedUser(project, username);
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        project.setStartDate(projectDTO.getStartDate());
        project.setEndDate(projectDTO.getEndDate());
        Project savedProject = projectRepository.save(project);
        ProjectDTO savedProjectDTO = ProjectMapper.map(savedProject);
        return savedProjectDTO;
    }


    public ProjectDTO findByProjectCode(String projectCode, String username) {
        Project project = projectRepository.findByCode(projectCode);
        if (project == null) {
            throw new ProjectNotFoundException("No project found with code [" + projectCode + "]");
        }
        validateAuthorisedUser(project, username);
        return ProjectMapper.map(project);

    }

    public ProjectListDTO listProjects(String username) {
        List<ProjectDTO> projectList = new ArrayList<>();
        List<Project> projects = projectRepository.findByUser_Username(username);
        projects.forEach(p -> projectList.add(ProjectMapper.map(p)));
        return new ProjectListDTO(projectList);

    }

    public void deleteProject(String projectCode, String username) {
        Project project = projectRepository.findByCode(projectCode.toUpperCase());
        if (project == null) {
            throw new ProjectNotFoundException("No project found with code [" + projectCode + "]");
        }
        validateAuthorisedUser(project, username);
        projectRepository.delete(project);
    }

    private void validateAuthorisedUser(Project project, String username) {
        if (!project.getUser().getUsername().equals(username)) {
            throw new ProjectNotFoundException("Requested project not found for user " + username + ".");
        }
    }


}
