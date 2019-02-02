package com.javafreelancedeveloper.projectmanagementtool.security;

import com.google.gson.Gson;
import com.javafreelancedeveloper.projectmanagementtool.dto.InvalidLoginResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException {

        log.info("Unauthorised user!", e);

        // TODO: distinguish between bad credentials, unauthorised and expired tokens...
        String jsonLoginResponse = new Gson().toJson(new InvalidLoginResponseDTO());

        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.getWriter().print(jsonLoginResponse);

    }
}
