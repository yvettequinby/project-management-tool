package com.javafreelancedeveloper.projectmanagementtool.mapper;

import com.javafreelancedeveloper.projectmanagementtool.domain.User;
import com.javafreelancedeveloper.projectmanagementtool.dto.UserDTO;

public class UserMapper {

    public static UserDTO map(User domain) {
        return UserDTO.builder()
                .id(domain.getId())
                .password(domain.getPassword())
                .username(domain.getUsername())
                .fullName(domain.getFullName())
                .build();
    }

    public static User map(UserDTO dto) {
        return User.builder()
                .id(dto.getId())
                .password(dto.getPassword())
                .username(dto.getUsername())
                .fullName(dto.getFullName())
                .build();
    }
}
