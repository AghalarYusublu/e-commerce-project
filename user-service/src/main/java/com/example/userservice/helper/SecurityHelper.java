package com.example.userservice.helper;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityHelper {
    public boolean servletPathIsAuth(@NonNull HttpServletRequest httpServletRequest) {
        return httpServletRequest.getServletPath().contains("/auth");
    }

    public boolean authHeaderIsValid(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.startsWith("Bearer ");
    }

    public boolean isJwtUsedFirst(String username) {
        return username != null && SecurityContextHolder.getContext().getAuthentication() == null;
    }
}
