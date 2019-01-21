package com.javafreelancedeveloper.projectmanagementtool.web;

import com.javafreelancedeveloper.projectmanagementtool.dto.SavedProjectResponseDTO;
import com.javafreelancedeveloper.projectmanagementtool.dto.ProjectDTO;
import com.javafreelancedeveloper.projectmanagementtool.dto.ProjectListDTO;
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
    public ResponseEntity<SavedProjectResponseDTO> createNewProject(@Valid @RequestBody ProjectDTO project,
                                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            SavedProjectResponseDTO savedProjectResponseDTO = SavedProjectResponseDTO.buildErrorResponse(bindingResult.getFieldErrors(), project);
            return new ResponseEntity<>(savedProjectResponseDTO, HttpStatus.BAD_REQUEST);
        } else {
            SavedProjectResponseDTO savedProjectResponseDTO = projectService.saveProject(project);
            return new ResponseEntity<>(savedProjectResponseDTO, HttpStatus.CREATED);
        }
    }

    @PutMapping
    public ResponseEntity<SavedProjectResponseDTO> updateProject(@Valid @RequestBody ProjectDTO project,
                                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            SavedProjectResponseDTO savedProjectResponseDTO = SavedProjectResponseDTO.buildErrorResponse(bindingResult.getFieldErrors(), project);
            return new ResponseEntity<>(savedProjectResponseDTO, HttpStatus.BAD_REQUEST);
        } else {
            SavedProjectResponseDTO savedProjectResponseDTO = projectService.updateProject(project);
            return new ResponseEntity<>(savedProjectResponseDTO, HttpStatus.ACCEPTED);
        }
    }

    @GetMapping("/{projectCode}")
    public ResponseEntity<ProjectDTO> getProjectByProjectCode(@PathVariable String projectCode) {
        ProjectDTO projectDTO = projectService.findByProjectCode(projectCode.toUpperCase());
        return new ResponseEntity<>(projectDTO, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<ProjectListDTO> listProjects() {
        ProjectListDTO projectListDTO = projectService.listProjects();
        return new ResponseEntity<>(projectListDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{projectCode}")
    public ResponseEntity<Void> deleteProject(@PathVariable String projectCode) {
        projectService.deleteProject(projectCode);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
