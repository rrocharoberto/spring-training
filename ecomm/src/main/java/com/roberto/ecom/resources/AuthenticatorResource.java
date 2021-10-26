package com.roberto.ecom.resources;

import javax.servlet.http.HttpServletResponse;

import com.roberto.ecom.security.EcomUserPrincipal;
import com.roberto.ecom.security.JWTUtil;
import com.roberto.ecom.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticatorResource {

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/refresh_token")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void refreshToken(HttpServletResponse response) {
        EcomUserPrincipal user = UserService.getAuthenticated();
        String token = jwtUtil.generateToken(user.getUsername());
        UserService.setAuthorizantionInHeader(response, token);
    }
}