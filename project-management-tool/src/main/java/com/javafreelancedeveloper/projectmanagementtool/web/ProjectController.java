package com.javafreelancedeveloper.projectmanagementtool.web;

import com.javafreelancedeveloper.projectmanagementtool.dto.CreateProjectRequest;
import com.javafreelancedeveloper.projectmanagementtool.dto.CreateProjectResponse;
import com.javafreelancedeveloper.projectmanagementtool.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/project")
@AllArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<CreateProjectResponse> createNewProject(@Valid @RequestBody CreateProjectRequest createProjectRequest,
                                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            CreateProjectResponse createProjectResponse = CreateProjectResponse.buildErrorResponse(bindingResult.getFieldErrors(), createProjectRequest);
            return new ResponseEntity<>(createProjectResponse, HttpStatus.BAD_REQUEST);
        } else {
            CreateProjectResponse createProjectResponse = projectService.saveProject(createProjectRequest);
            return new ResponseEntity<>(createProjectResponse, HttpStatus.CREATED);
        }
    }
}
