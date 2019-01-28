package com.javafreelancedeveloper.projectmanagementtool.service;

import com.javafreelancedeveloper.projectmanagementtool.domain.Project;
import com.javafreelancedeveloper.projectmanagementtool.domain.ProjectTask;
import com.javafreelancedeveloper.projectmanagementtool.dto.FieldValidationErrorResponseDTO;
import com.javafreelancedeveloper.projectmanagementtool.dto.ProjectDTO;
import com.javafreelancedeveloper.projectmanagementtool.dto.ProjectTaskDTO;
import com.javafreelancedeveloper.projectmanagementtool.dto.ProjectTaskListDTO;
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

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ProjectTaskService {

    private final ProjectRepository projectRepository;
    private final ProjectTaskRepository projectTaskRepository;


    public ProjectTaskDTO findByProjectTaskCode(String projectTaskCode) {
        ProjectTask projectTask = projectTaskRepository.findByCode(projectTaskCode);
        if (projectTask == null) {
            throw new ProjectTaskNotFoundException("No project task found with code [" + projectTaskCode + "]");
        }
        return ProjectTaskMapper.map(projectTask);

    }

    public ProjectTaskListDTO getProjectTaskList(String projectCode) {

        Project project = projectRepository.findByCode(projectCode);
        if (project == null) {
            throw new ProjectNotFoundException("No project found with code [" + projectCode + "]");
        }

        ProjectDTO projectDTO = ProjectMapper.map(project);

        List<ProjectTaskDTO> projectTaskDTOs = project.getProjectTasks()
                .stream()
                .map(ProjectTaskMapper::map)
                .collect(Collectors.toList());

        return ProjectTaskListDTO.builder()
                .project(projectDTO)
                .projectTasks(projectTaskDTOs)
                .build();

    }

    public ProjectTaskDTO saveProjectTask(String projectCode, ProjectTaskDTO projectTask) {

        Project project = projectRepository.findByCode(projectCode);
        if (project == null) {
            throw new ProjectNotFoundException("No project found with code [" + projectCode + "]");
        }
        if (projectTask.getId() == null) {
            // create a project task
            ProjectTask saveMe = ProjectTaskMapper.map(projectTask);
            Integer projectTaskSequence = project.getProjectTasks().size()+1;
            String projectTaskCode = (projectCode + "_" + String.format("%04d", projectTaskSequence)).toUpperCase();
            saveMe.setCode(projectTaskCode);
            saveMe.setProject(project);
            project.getProjectTasks().add(saveMe);
            try {
                ProjectTask savedProjectTask = projectTaskRepository.save(saveMe);
                return ProjectTaskMapper.map(savedProjectTask);
            } catch (DataIntegrityViolationException e) {
                throw new ValidationException(new FieldValidationErrorResponseDTO("code", "Project Task Code must be unique. A project task with this code already exists."));
            }
        } else {
            // update a project task
            ProjectTask staleProjectTask = projectTaskRepository.findById(projectTask.getId()).orElseThrow(() -> new ProjectTaskNotFoundException("No project task found for ID [" + projectTask.getId() + "]."));
            if(!staleProjectTask.getCode().equals(projectTask.getCode())) {
                throw new ValidationException(new FieldValidationErrorResponseDTO("code", "Project Task Code is inconsistent with id. Invalid input."));
            }
            staleProjectTask.setAcceptanceCriteria(projectTask.getAcceptanceCriteria());
            staleProjectTask.setDueDate(projectTask.getDueDate());
            staleProjectTask.setPriority(projectTask.getPriority());
            staleProjectTask.setStatus(projectTask.getStatus());
            staleProjectTask.setSummary(projectTask.getSummary());
            ProjectTask savedProjectTask = projectTaskRepository.save(staleProjectTask);
            return ProjectTaskMapper.map(savedProjectTask);
        }

    }

    public void deleteProjectTask(String projectTaskCode) {

        ProjectTask projectTask = projectTaskRepository.findByCode(projectTaskCode.toUpperCase());
        if (projectTask == null) {
            throw new ProjectTaskNotFoundException("No project task found with code [" + projectTaskCode + "]");
        }
        projectTaskRepository.delete(projectTask);
    }


}
