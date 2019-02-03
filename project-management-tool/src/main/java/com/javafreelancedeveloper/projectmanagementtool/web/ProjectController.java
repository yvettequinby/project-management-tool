package com.javafreelancedeveloper.projectmanagementtool.web;

import com.javafreelancedeveloper.projectmanagementtool.dto.ProjectDTO;
import com.javafreelancedeveloper.projectmanagementtool.dto.ProjectListDTO;
import com.javafreelancedeveloper.projectmanagementtool.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("api/project")
@AllArgsConstructor
@CrossOrigin
public class ProjectController extends BaseController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectDTO> createNewProject(@Valid @RequestBody ProjectDTO project,
                                                       BindingResult bindingResult,
                                                       Principal principal) {
        validate(bindingResult);
        ProjectDTO savedProject = projectService.saveProject(project, principal.getName());
        return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ProjectDTO> updateProject(@Valid @RequestBody ProjectDTO project,
                                                    BindingResult bindingResult,
                                                    Principal principal) {
        validate(bindingResult);
        ProjectDTO savedProject = projectService.updateProject(project, principal.getName());
        return new ResponseEntity<>(savedProject, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{projectCode}")
    public ResponseEntity<ProjectDTO> getProjectByProjectCode(@PathVariable String projectCode,
                                                              Principal principal) {
        ProjectDTO projectDTO = projectService.findByProjectCode(projectCode.toUpperCase(), principal.getName());
        return new ResponseEntity<>(projectDTO, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<ProjectListDTO> listProjects(Principal principal) {
        ProjectListDTO projectListDTO = projectService.listProjects(principal.getName());
        return new ResponseEntity<>(projectListDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{projectCode}")
    public ResponseEntity<Void> deleteProject(@PathVariable String projectCode,
                                              Principal principal) {
        projectService.deleteProject(projectCode, principal.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
