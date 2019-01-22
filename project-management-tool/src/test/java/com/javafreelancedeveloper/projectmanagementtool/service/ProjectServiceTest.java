package com.javafreelancedeveloper.projectmanagementtool.service;

import com.javafreelancedeveloper.projectmanagementtool.domain.Project;
import com.javafreelancedeveloper.projectmanagementtool.dto.ProjectDTO;
import com.javafreelancedeveloper.projectmanagementtool.dto.SavedProjectResponseDTO;
import com.javafreelancedeveloper.projectmanagementtool.exception.ProjectCodeException;
import com.javafreelancedeveloper.projectmanagementtool.exception.ProjectIdException;
import com.javafreelancedeveloper.projectmanagementtool.repository.ProjectRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;
    @InjectMocks
    private ProjectService projectService;
    @Rule
    public ExpectedException expectedException = ExpectedException.none();


    @Test
    public void testSaveProject_duplicate() {

        ProjectDTO projectDTO = ProjectDTO.builder()
                .description("A Test Project 1")
                .code("TST1")
                .name("Project 1")
                .build();

        Mockito.doThrow(DataIntegrityViolationException.class).when(projectRepository).save(any());
        expectedException.expect(ProjectCodeException.class);

        projectService.saveProject(projectDTO);

        verify(projectRepository).save(any());

    }


    @Test
    public void testSaveProject_happy() {

        Project savedProject = Project.builder()
                .description("A Test Project 1")
                .code("TST1")
                .name("Project 1")
                .id(1L)
                .createdTimestamp(new Date())
                .updatedTimestamp(new Date())
                .build();

        ProjectDTO projectDTO = ProjectDTO.builder()
                .description("A Test Project 1")
                .code("TST1")
                .name("Project 1")
                .build();

        when(projectRepository.save(any())).thenReturn(savedProject);

        SavedProjectResponseDTO savedProjectDTO = projectService.saveProject(projectDTO);

        verify(projectRepository).save(any());

        assertEquals(savedProject.getId(), savedProjectDTO.getProject().getId());
        assertEquals(savedProject.getCode(), savedProjectDTO.getProject().getCode());
        assertEquals(savedProject.getCreatedTimestamp(), savedProjectDTO.getProject().getCreatedTimestamp());
        assertEquals(savedProject.getDescription(), savedProjectDTO.getProject().getDescription());
        assertEquals(savedProject.getEndDate(), savedProjectDTO.getProject().getEndDate());
        assertEquals(savedProject.getName(), savedProjectDTO.getProject().getName());
        assertEquals(savedProject.getStartDate(), savedProjectDTO.getProject().getStartDate());
        assertEquals(savedProject.getUpdatedTimestamp(), savedProjectDTO.getProject().getUpdatedTimestamp());

    }


    @Test
    public void testUpdateProject_happy() {

        Date createdTimeStamp = new Date();

        Project projectBeforeUpdate = Project.builder()
                .description("WooWoo")
                .code("TST1")
                .name("Project 1")
                .id(1L)
                .createdTimestamp(createdTimeStamp)
                .updatedTimestamp(createdTimeStamp)
                .build();

        Project projectAfterUpdate = Project.builder()
                .description("A Test Project 1")
                .code("TST1")
                .name("Project 1")
                .id(1L)
                .createdTimestamp(createdTimeStamp)
                .updatedTimestamp(new Date())
                .build();

        ProjectDTO projectDTO = ProjectDTO.builder()
                .id(1L)
                .description("A Test Project 1")
                .code("TST1")
                .name("Project 1")
                .build();

        when(projectRepository.findById(projectDTO.getId())).thenReturn(Optional.of(projectBeforeUpdate));
        when(projectRepository.save(any())).thenReturn(projectAfterUpdate);

        SavedProjectResponseDTO savedProjectDTO = projectService.updateProject(projectDTO);

        verify(projectRepository).save(any());
        verify(projectRepository).findById(projectDTO.getId());

        assertEquals(projectAfterUpdate.getId(), savedProjectDTO.getProject().getId());
        assertEquals(projectAfterUpdate.getCode(), savedProjectDTO.getProject().getCode());
        assertEquals(projectAfterUpdate.getCreatedTimestamp(), savedProjectDTO.getProject().getCreatedTimestamp());
        assertEquals(projectAfterUpdate.getDescription(), savedProjectDTO.getProject().getDescription());
        assertEquals(projectAfterUpdate.getEndDate(), savedProjectDTO.getProject().getEndDate());
        assertEquals(projectAfterUpdate.getName(), savedProjectDTO.getProject().getName());
        assertEquals(projectAfterUpdate.getStartDate(), savedProjectDTO.getProject().getStartDate());
        assertEquals(projectAfterUpdate.getUpdatedTimestamp(), savedProjectDTO.getProject().getUpdatedTimestamp());

    }


    @Test
    public void testUpdateProject_noId() {

        ProjectDTO projectDTO = ProjectDTO.builder()
                .description("A Test Project 1")
                .code("TST1")
                .name("Project 1")
                .build();

        expectedException.expect(ProjectIdException.class);
        projectService.updateProject(projectDTO);

    }


    @Test
    public void testUpdateProject_wrongCode() {

        Project projectBeforeUpdate = Project.builder()
                .description("WooWoo")
                .code("OHNO")
                .name("Project 1")
                .id(1L)
                .createdTimestamp(new Date())
                .updatedTimestamp(new Date())
                .build();

        ProjectDTO projectDTO = ProjectDTO.builder()
                .id(1L)
                .description("A Test Project 1")
                .code("TST1")
                .name("Project 1")
                .build();

        when(projectRepository.findById(projectDTO.getId())).thenReturn(Optional.of(projectBeforeUpdate));
        expectedException.expect(ProjectCodeException.class);
        projectService.updateProject(projectDTO);
        verify(projectRepository).findById(projectDTO.getId());

    }


    @Test
    public void testUpdateProject_notFound() {

        ProjectDTO projectDTO = ProjectDTO.builder()
                .description("A Test Project 1")
                .id(1L)
                .code("TST1")
                .name("Project 1")
                .build();

        when(projectRepository.findById(projectDTO.getId())).thenReturn(Optional.empty());
        expectedException.expect(ProjectIdException.class);
        projectService.updateProject(projectDTO);
        verify(projectRepository).findById(projectDTO.getId());

    }
}
