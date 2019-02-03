package com.javafreelancedeveloper.projectmanagementtool.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javafreelancedeveloper.projectmanagementtool.dto.ProjectDTO;
import com.javafreelancedeveloper.projectmanagementtool.dto.ProjectListDTO;
import com.javafreelancedeveloper.projectmanagementtool.service.ProjectService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Date;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProjectControllerTest {

    private static final String MOCK_USERNAME = "spring";

    @MockBean
    private ProjectService projectService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @WithMockUser(MOCK_USERNAME)
    @Test
    public void testCreateProject() throws Exception {

        ProjectDTO projectDTO = ProjectDTO.builder()
                .description("A Test Project 1")
                .code("TST1")
                .name("Project 1")
                .build();
        String jsonBody = objectMapper.writeValueAsString(projectDTO);

        ProjectDTO savedProjectDTO = ProjectDTO.builder()
                .description("A Test Project 1")
                .code("TST1")
                .name("Project 1")
                .id(1L)
                .createdTimestamp(new Date())
                .updatedTimestamp(new Date())
                .build();
        when(projectService.saveProject(projectDTO, MOCK_USERNAME)).thenReturn(savedProjectDTO);

        mockMvc.perform(post("/api/project")
                .content(jsonBody)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code", equalTo(projectDTO.getCode())))
                .andExpect(jsonPath("$.name", equalTo(projectDTO.getName())))
                .andExpect(jsonPath("$.description", equalTo(projectDTO.getDescription())))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andReturn();

        verify(projectService).saveProject(projectDTO, MOCK_USERNAME);
    }

    @WithMockUser(value = MOCK_USERNAME)
    @Test
    public void testCreateProject_validationErrors() throws Exception {

        ProjectDTO projectDTO = ProjectDTO.builder()
                .description("A Test Project 1")
                .code("OHNONONO")
                .name("")
                .build();
        String jsonBody = objectMapper.writeValueAsString(projectDTO);

        mockMvc.perform(post("/api/project")
                .content(jsonBody)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code[0]", equalTo("Project Code must be 4 or 5 characters long.")))
                .andExpect(jsonPath("$.name[0]", equalTo("Project Name is required")))
                .andReturn();

    }

    @WithMockUser(value = MOCK_USERNAME)
    @Test
    public void testUpdateProject() throws Exception {

        ProjectDTO projectDTO = ProjectDTO.builder()
                .description("A Test Project 1")
                .id(1L)
                .code("TST1")
                .name("Project 1")
                .build();
        String jsonBody = objectMapper.writeValueAsString(projectDTO);

        ProjectDTO savedProjectDTO = ProjectDTO.builder()
                .description("A Test Project 1")
                .code("TST1")
                .name("Project 1")
                .id(1L)
                .createdTimestamp(new Date())
                .updatedTimestamp(new Date())
                .build();
        when(projectService.updateProject(projectDTO, MOCK_USERNAME)).thenReturn(savedProjectDTO);

        mockMvc.perform(put("/api/project")
                .content(jsonBody)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.code", equalTo(projectDTO.getCode())))
                .andExpect(jsonPath("$.name", equalTo(projectDTO.getName())))
                .andExpect(jsonPath("$.description", equalTo(projectDTO.getDescription())))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andReturn();

        verify(projectService).updateProject(projectDTO, MOCK_USERNAME);
    }

    @WithMockUser(value = MOCK_USERNAME)
    @Test
    public void testDeleteProject() throws Exception {

        final String projectCode = "PRJT1";
        final String url = String.format("/api/project/%s", projectCode);
        mockMvc.perform(delete(url))
                .andExpect(status().isOk())
                .andReturn();
        verify(projectService).deleteProject(projectCode, MOCK_USERNAME);
    }

    @WithMockUser(value = MOCK_USERNAME)
    @Test
    public void testGetProject() throws Exception {

        final String projectCode = "PRJT1";

        ProjectDTO projectDTO = ProjectDTO.builder()
                .description("A Test Project 1")
                .code(projectCode)
                .name("Project 1")
                .id(1L)
                .createdTimestamp(new Date())
                .updatedTimestamp(new Date())
                .build();
        when(projectService.findByProjectCode(projectCode, MOCK_USERNAME)).thenReturn(projectDTO);


        final String url = String.format("/api/project/%s", projectCode);

        mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo(projectDTO.getCode())))
                .andExpect(jsonPath("$.name", equalTo(projectDTO.getName())))
                .andExpect(jsonPath("$.description", equalTo(projectDTO.getDescription())))
                .andExpect(jsonPath("$.id", equalTo(projectDTO.getId().intValue())))
                .andReturn();

        verify(projectService).findByProjectCode(projectCode, MOCK_USERNAME);
    }

    @WithMockUser(value = MOCK_USERNAME)
    @Test
    public void testListProjects() throws Exception {

        ProjectDTO projectDTO = ProjectDTO.builder()
                .description("A Test Project 1")
                .code("TST1")
                .name("Project 1")
                .id(1L)
                .createdTimestamp(new Date())
                .updatedTimestamp(new Date())
                .build();
        ProjectListDTO projectList = new ProjectListDTO(Collections.singletonList(projectDTO));

        when(projectService.listProjects(MOCK_USERNAME)).thenReturn(projectList);

        mockMvc.perform(get("/api/project/list")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((jsonPath("$.projects", Matchers.hasSize(1))))
                .andExpect(jsonPath("$.projects[0].code", equalTo(projectDTO.getCode())))
                .andExpect(jsonPath("$.projects[0].name", equalTo(projectDTO.getName())))
                .andExpect(jsonPath("$.projects[0].description", equalTo(projectDTO.getDescription())))
                .andExpect(jsonPath("$.projects[0].id", equalTo(projectDTO.getId().intValue())))
                .andReturn();

        verify(projectService).listProjects(MOCK_USERNAME);
    }

}
