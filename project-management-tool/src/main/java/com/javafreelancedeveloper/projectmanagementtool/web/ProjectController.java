package com.javafreelancedeveloper.projectmanagementtool.web;

import com.javafreelancedeveloper.projectmanagementtool.dto.CreateProjectResponseDTO;
import com.javafreelancedeveloper.projectmanagementtool.dto.ProjectDTO;
import com.javafreelancedeveloper.projectmanagementtool.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/project")
@AllArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<CreateProjectResponseDTO> createNewProject(@Valid @RequestBody ProjectDTO project,
                                                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            CreateProjectResponseDTO createProjectResponse = CreateProjectResponseDTO.buildErrorResponse(bindingResult.getFieldErrors(), project);
            return new ResponseEntity<>(createProjectResponse, HttpStatus.BAD_REQUEST);
        } else {
            CreateProjectResponseDTO createProjectResponse = projectService.saveProject(project);
            return new ResponseEntity<>(createProjectResponse, HttpStatus.CREATED);
        }
    }

    @GetMapping("/{projectIdentifier}")
    public ResponseEntity<ProjectDTO> getProjectByProjectIdentifier(@PathVariable String projectIdentifier) {
        ProjectDTO projectDTO = projectService.findByProjectIdentifier(projectIdentifier.toUpperCase());
        return new ResponseEntity<>(projectDTO, HttpStatus.OK);
    }
}
