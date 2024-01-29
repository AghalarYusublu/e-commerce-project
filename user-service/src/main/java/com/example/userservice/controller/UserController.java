package com.example.userservice.controller;

import com.example.userservice.model.dto.request.AuthenticationRequest;
import com.example.userservice.model.dto.request.UserRequestDto;
import com.example.userservice.model.dto.response.AuthenticationResponse;
import com.example.userservice.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @PostMapping("/auth/registration")
    public ResponseEntity<String> registration(@RequestBody UserRequestDto userRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(userRequestDto));
    }

    @PostMapping("/auth/authentication")
    public ResponseEntity<AuthenticationResponse> authentication(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok().body(userService.authenticateUser(request));
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestHeader(name = "Authorization") String token,@RequestHeader(name = "UserId") Long id){
        return ResponseEntity.ok().body(userService.refreshToken(token,id));
    }
}
