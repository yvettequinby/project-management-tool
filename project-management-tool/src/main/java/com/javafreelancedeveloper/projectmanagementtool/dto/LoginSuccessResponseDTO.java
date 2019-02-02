package com.javafreelancedeveloper.projectmanagementtool.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginSuccessResponseDTO {

    private final String jwtToken;
    private final boolean success = true;
}
