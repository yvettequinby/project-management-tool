package com.javafreelancedeveloper.projectmanagementtool.web;

import com.javafreelancedeveloper.projectmanagementtool.dto.LoginRequestDTO;
import com.javafreelancedeveloper.projectmanagementtool.dto.LoginSuccessResponseDTO;
import com.javafreelancedeveloper.projectmanagementtool.dto.UserDTO;
import com.javafreelancedeveloper.projectmanagementtool.security.JwtTokenProvider;
import com.javafreelancedeveloper.projectmanagementtool.security.SecurityConstants;
import com.javafreelancedeveloper.projectmanagementtool.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/user")
@AllArgsConstructor
@CrossOrigin
public class UserController extends BaseController {

    private final UserService userService;

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequest,
                                              BindingResult bindingResult) {
        validate(bindingResult);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = SecurityConstants.TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new LoginSuccessResponseDTO(jwt));
    }


    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserDTO user,
                                                BindingResult bindingResult) {
        validate(bindingResult);
        UserDTO registeredUser = userService.registerUser(user);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }
}
