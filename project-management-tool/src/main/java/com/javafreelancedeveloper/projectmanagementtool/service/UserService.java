package com.javafreelancedeveloper.projectmanagementtool.service;

import com.javafreelancedeveloper.projectmanagementtool.domain.User;
import com.javafreelancedeveloper.projectmanagementtool.dto.FieldValidationErrorResponseDTO;
import com.javafreelancedeveloper.projectmanagementtool.dto.UserDTO;
import com.javafreelancedeveloper.projectmanagementtool.exception.HandledException;
import com.javafreelancedeveloper.projectmanagementtool.exception.ValidationException;
import com.javafreelancedeveloper.projectmanagementtool.mapper.UserMapper;
import com.javafreelancedeveloper.projectmanagementtool.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserDTO registerUser(UserDTO userDTO) {
        if (userDTO.getId() == null) {
            try {
                if (!userDTO.getConfirmPassword().equals(userDTO.getPassword())) {
                    throw new ValidationException(new FieldValidationErrorResponseDTO("password", "Confirmation password does not match provided password. Please re-enter password and confirmation."));
                }
                User newUser = UserMapper.map(userDTO);
                newUser.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
                User savedUser = userRepository.save(newUser);
                return UserMapper.map(savedUser);

            } catch (DataIntegrityViolationException e) {
                throw new ValidationException(new FieldValidationErrorResponseDTO("username", "Username must be unique. A project with this code already exists."));
            }
        } else {
            throw new HandledException("Existing user cannot be updated.");
        }
    }

}
