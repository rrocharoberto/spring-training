package com.roberto.ecom.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roberto.ecom.dto.CredentialsDTO;
import com.roberto.ecom.resources.exceptions.LoginError;
import com.roberto.ecom.services.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private JWTUtil jwtUtil;

    private CredentialsDTO credentials;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
        setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {
            credentials = new ObjectMapper().readValue(request.getInputStream(), CredentialsDTO.class);
        } catch (IOException e) {
            throw new PreAuthenticatedCredentialsNotFoundException(
                    "Could not retrieve credentials information from request.", e);
        }
        return super.attemptAuthentication(request, response);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

        String userName = ((EcomUserPrincipal) authResult.getPrincipal()).getUsername();
        String token = jwtUtil.generateToken(userName);
        UserService.setAuthorizantionInHeader(response, token);
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        return credentials.getEmail();
    }

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        return credentials.getPassword();
    }

    private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                AuthenticationException exception) throws IOException, ServletException {

            response.setStatus(HttpStatus.UNAUTHORIZED.value()); // 401
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            LoginError error = new LoginError(HttpStatus.UNAUTHORIZED.value(), 
                "Unauthorized", exception.getMessage(), "/login");
            response.getWriter().append(new ObjectMapper().writeValueAsString(error));
        }
    }
}