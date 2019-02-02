package com.javafreelancedeveloper.projectmanagementtool.dto;

import lombok.Data;

@Data
public class InvalidLoginResponseDTO {

    private final String username = "Invalid username or password";
    private final String password = "Invalid username or password.";
}
