package com.example.userservice.service;

import com.example.userservice.model.dto.request.AuthenticationRequest;
import com.example.userservice.model.dto.request.UserRequestDto;
import com.example.userservice.model.dto.response.AuthenticationResponse;

public interface IUserService {
    String saveUser(UserRequestDto request);
    AuthenticationResponse authenticateUser(AuthenticationRequest request);
    AuthenticationResponse refreshToken(String token, Long id);
}
