package com.javafreelancedeveloper.projectmanagementtool.service;

import com.javafreelancedeveloper.projectmanagementtool.domain.Project;
import com.javafreelancedeveloper.projectmanagementtool.domain.ProjectTask;
import com.javafreelancedeveloper.projectmanagementtool.dto.*;
import com.javafreelancedeveloper.projectmanagementtool.exception.ProjectNotFoundException;
import com.javafreelancedeveloper.projectmanagementtool.exception.ProjectTaskNotFoundException;
import com.javafreelancedeveloper.projectmanagementtool.exception.ValidationException;
import com.javafreelancedeveloper.projectmanagementtool.mapper.ProjectMapper;
import com.javafreelancedeveloper.projectmanagementtool.mapper.ProjectTaskMapper;
import com.javafreelancedeveloper.projectmanagementtool.repository.ProjectRepository;
import com.javafreelancedeveloper.projectmanagementtool.repository.ProjectTaskRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ProjectTaskService {

    private final ProjectRepository projectRepository;
    private final ProjectTaskRepository projectTaskRepository;

    public ProjectTaskDTO findByProjectTaskCode(String projectCode, String projectTaskCode, String username) {
        Project project = projectRepository.findByCode(projectCode);
        ProjectTask projectTask = projectTaskRepository.findByCode(projectTaskCode);
        if (projectTask == null) {
            throw new ProjectTaskNotFoundException("No project task found with code [" + projectTaskCode + "]");
        }
        if (!projectTask.getProject().getId().equals(project.getId())) {
            throw new ValidationException(new FieldValidationErrorResponseDTO("project.code", "The project task does not belong to the project [" + projectCode + "]. Invalid request."));
        }
        validateAuthorisedUser(projectTask, username);
        return ProjectTaskMapper.map(projectTask, ProjectMapper.map(project));

    }

    public ProjectTaskListDTO getProjectTaskList(String projectCode, String username) {

        Project project = projectRepository.findByCode(projectCode);
        if (project == null) {
            throw new ProjectNotFoundException("No project found with code [" + projectCode + "]");
        }
        validateAuthorisedUser(project, username);

        ProjectDTO projectDTO = ProjectMapper.map(project);

        List<ProjectTaskLiteDTO> projectTaskDTOs = project.getProjectTasks()
                .stream()
                .map(ProjectTaskMapper::map)
                .collect(Collectors.toList());

        projectTaskDTOs.sort(Comparator.comparing(o -> o.getPriority()));

        return ProjectTaskListDTO.builder()
                .project(projectDTO)
                .projectTasks(projectTaskDTOs)
                .build();

    }

    public ProjectTaskDTO saveProjectTask(String projectCode, ProjectTaskLiteDTO projectTask, String username) {

        Project project = projectRepository.findByCode(projectCode);
        if (project == null) {
            throw new ProjectNotFoundException("No project found with code [" + projectCode + "]");
        }
        validateAuthorisedUser(project, username);
        if (projectTask.getId() == null) {
            // create a project task
            ProjectTask saveMe = ProjectTaskMapper.map(projectTask);
            Integer projectTaskSequence = project.getProjectTasks().stream().map(ProjectTask::getSequence).max(Comparator.comparing(Integer::valueOf)).orElse(0);
            projectTaskSequence++;
            String projectTaskCode = (projectCode + "_" + String.format("%04d", projectTaskSequence)).toUpperCase();
            saveMe.setSequence(projectTaskSequence);
            saveMe.setCode(projectTaskCode);
            setProjectTaskDefaults(saveMe);
            saveMe.setProject(project);
            project.getProjectTasks().add(saveMe);
            try {
                ProjectTask savedProjectTask = projectTaskRepository.save(saveMe);
                return ProjectTaskMapper.map(savedProjectTask, ProjectMapper.map(savedProjectTask.getProject()));
            } catch (DataIntegrityViolationException e) {
                throw new ValidationException(new FieldValidationErrorResponseDTO("code", "Project Task Code must be unique. A project task with this code already exists."));
            }
        } else {
            // update a project task
            ProjectTask staleProjectTask = projectTaskRepository.findById(projectTask.getId()).orElseThrow(() -> new ProjectTaskNotFoundException("No project task found for ID [" + projectTask.getId() + "]."));
            if (!staleProjectTask.getCode().equals(projectTask.getCode())) {
                throw new ValidationException(new FieldValidationErrorResponseDTO("code", "Project Task Code is inconsistent with id. Invalid input."));
            }
            if (!staleProjectTask.getProject().getId().equals(project.getId())) {
                throw new ValidationException(new FieldValidationErrorResponseDTO("project.code", "The project task does not belong to the project [" + projectCode + "]. Invalid request."));
            }
            staleProjectTask.setAcceptanceCriteria(projectTask.getAcceptanceCriteria());
            staleProjectTask.setDueDate(projectTask.getDueDate());
            staleProjectTask.setPriority(projectTask.getPriority());
            staleProjectTask.setStatus(projectTask.getStatus());
            staleProjectTask.setSummary(projectTask.getSummary());
            setProjectTaskDefaults(staleProjectTask);
            ProjectTask savedProjectTask = projectTaskRepository.save(staleProjectTask);
            return ProjectTaskMapper.map(savedProjectTask, ProjectMapper.map(savedProjectTask.getProject()));
        }

    }

    private void setProjectTaskDefaults(ProjectTask projectTask) {
        if (StringUtils.isEmpty(projectTask.getStatus())) {
            projectTask.setStatus("TODO");
        }
        if (projectTask.getPriority() == null) {
            projectTask.setPriority(3);
        }
    }

    public void deleteProjectTask(String projectCode, String projectTaskCode, String username) {

        ProjectTask projectTask = projectTaskRepository.findByCode(projectTaskCode.toUpperCase());
        if (projectTask == null) {
            throw new ProjectTaskNotFoundException("No project task found with code [" + projectTaskCode + "]");
        }
        validateAuthorisedUser(projectTask, username);
        if (!projectTask.getProject().getCode().equals(projectCode)) {
            throw new ValidationException(new FieldValidationErrorResponseDTO("project.code", "The project task does not belong to the project [" + projectCode + "]. Invalid request."));
        }
        projectTaskRepository.delete(projectTask);
    }

    private void validateAuthorisedUser(ProjectTask projectTask, String username) {
        if (!projectTask.getProject().getUser().getUsername().equals(username)) {
            throw new ProjectNotFoundException("Requested project task not found for user " + username + ".");
        }
    }

    private void validateAuthorisedUser(Project project, String username) {
        if (!project.getUser().getUsername().equals(username)) {
            throw new ProjectNotFoundException("Requested project not found for user " + username + ".");
        }
    }


}
