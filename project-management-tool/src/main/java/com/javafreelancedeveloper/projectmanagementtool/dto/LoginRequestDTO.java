package com.javafreelancedeveloper.projectmanagementtool.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequestDTO {

    @NotBlank(message = "Username is a required.")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;
}
