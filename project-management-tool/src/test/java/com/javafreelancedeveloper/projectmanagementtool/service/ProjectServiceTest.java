package com.javafreelancedeveloper.projectmanagementtool.service;

import com.javafreelancedeveloper.projectmanagementtool.domain.Project;
import com.javafreelancedeveloper.projectmanagementtool.domain.User;
import com.javafreelancedeveloper.projectmanagementtool.dto.ProjectDTO;
import com.javafreelancedeveloper.projectmanagementtool.exception.ValidationException;
import com.javafreelancedeveloper.projectmanagementtool.repository.ProjectRepository;
import com.javafreelancedeveloper.projectmanagementtool.repository.UserRepository;
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

    private static final String USERNAME = "username";
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private UserRepository userRepository;
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
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(new User()));
        Mockito.doThrow(DataIntegrityViolationException.class).when(projectRepository).save(any());
        expectedException.expect(ValidationException.class);

        projectService.saveProject(projectDTO, USERNAME);

        verify(projectRepository).save(any());
        verify(userRepository).findByUsername(any());

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

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(new User()));
        when(projectRepository.save(any())).thenReturn(savedProject);

        ProjectDTO savedProjectDTO = projectService.saveProject(projectDTO, USERNAME);

        verify(projectRepository).save(any());
        verify(userRepository).findByUsername(any());

        assertEquals(savedProject.getId(), savedProjectDTO.getId());
        assertEquals(savedProject.getCode(), savedProjectDTO.getCode());
        assertEquals(savedProject.getCreatedTimestamp(), savedProjectDTO.getCreatedTimestamp());
        assertEquals(savedProject.getDescription(), savedProjectDTO.getDescription());
        assertEquals(savedProject.getEndDate(), savedProjectDTO.getEndDate());
        assertEquals(savedProject.getName(), savedProjectDTO.getName());
        assertEquals(savedProject.getStartDate(), savedProjectDTO.getStartDate());
        assertEquals(savedProject.getUpdatedTimestamp(), savedProjectDTO.getUpdatedTimestamp());

    }


    @Test
    public void testUpdateProject_happy() {

        Date createdTimeStamp = new Date();

        User user = User.builder().username(USERNAME).build();

        Project projectBeforeUpdate = Project.builder()
                .description("WooWoo")
                .code("TST1")
                .name("Project 1")
                .id(1L)
                .createdTimestamp(createdTimeStamp)
                .updatedTimestamp(createdTimeStamp)
                .user(user)
                .build();

        Project projectAfterUpdate = Project.builder()
                .description("A Test Project 1")
                .code("TST1")
                .name("Project 1")
                .id(1L)
                .user(user)
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

        ProjectDTO savedProjectDTO = projectService.updateProject(projectDTO, USERNAME);

        verify(projectRepository).save(any());
        verify(projectRepository).findById(projectDTO.getId());

        assertEquals(projectAfterUpdate.getId(), savedProjectDTO.getId());
        assertEquals(projectAfterUpdate.getCode(), savedProjectDTO.getCode());
        assertEquals(projectAfterUpdate.getCreatedTimestamp(), savedProjectDTO.getCreatedTimestamp());
        assertEquals(projectAfterUpdate.getDescription(), savedProjectDTO.getDescription());
        assertEquals(projectAfterUpdate.getEndDate(), savedProjectDTO.getEndDate());
        assertEquals(projectAfterUpdate.getName(), savedProjectDTO.getName());
        assertEquals(projectAfterUpdate.getStartDate(), savedProjectDTO.getStartDate());
        assertEquals(projectAfterUpdate.getUpdatedTimestamp(), savedProjectDTO.getUpdatedTimestamp());

    }


    @Test
    public void testUpdateProject_noId() {

        ProjectDTO projectDTO = ProjectDTO.builder()
                .description("A Test Project 1")
                .code("TST1")
                .name("Project 1")
                .build();

        expectedException.expect(ValidationException.class);
        projectService.updateProject(projectDTO, USERNAME);

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
        expectedException.expect(ValidationException.class);
        projectService.updateProject(projectDTO, USERNAME);
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
        expectedException.expect(ValidationException.class);
        projectService.updateProject(projectDTO, USERNAME);
        verify(projectRepository).findById(projectDTO.getId());

    }
}
