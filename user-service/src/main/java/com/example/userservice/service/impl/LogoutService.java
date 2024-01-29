package com.example.userservice.service.impl;

import com.example.userservice.helper.SecurityHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final SecurityHelper securityHelper;

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {

        String authHeader = request.getHeader("Authorization");
        if (securityHelper.authHeaderIsValid(authHeader)) {
            SecurityContextHolder.clearContext();
        }else {
            throw new RuntimeException("Authorization header is invalid");
        }
    }
}