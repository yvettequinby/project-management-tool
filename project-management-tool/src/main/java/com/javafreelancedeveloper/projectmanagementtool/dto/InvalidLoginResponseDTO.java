package com.javafreelancedeveloper.projectmanagementtool.dto;

import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class InvalidLoginResponseDTO {

    private final List<String> username = Collections.singletonList("Invalid username or password");
    private final List<String> password = Collections.singletonList("Invalid username or password.");
}
