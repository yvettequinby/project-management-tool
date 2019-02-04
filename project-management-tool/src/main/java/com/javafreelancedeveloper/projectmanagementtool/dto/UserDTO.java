package com.javafreelancedeveloper.projectmanagementtool.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
public class UserDTO {

    private Long id;
    @Email(message="Username must be a valid email address.")
    @NotBlank(message="Username is required.")
    private String username;
    @NotBlank(message="Password is required.")
    @Size(min = 6, max = 10, message = "Password must between 6 and 10 characters long.")
    private String password;
    @NotBlank(message="Confirm Password is required.")
    private String confirmPassword;
    @NotBlank(message="Full Name is required.")
    private String fullName;

}
