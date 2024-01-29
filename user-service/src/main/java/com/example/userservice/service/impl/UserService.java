package com.example.userservice.service.impl;

import com.example.userservice.helper.SecurityHelper;
import com.example.userservice.model.dto.request.AuthenticationRequest;
import com.example.userservice.model.dto.request.UserRequestDto;
import com.example.userservice.model.dto.response.AuthenticationResponse;
import com.example.userservice.model.entity.Role;
import com.example.userservice.model.entity.User;
import com.example.userservice.model.enums.RoleType;
import com.example.userservice.repository.RoleRepository;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService implements UserDetailsService, IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final SecurityHelper securityHelper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByUsernameOrEmail(username);

        return new org.springframework.security.core.userdetails.User(
                user.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username))
                        .getUsername(),
                user.get().getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(user.get().getRole().getName().name())));
    }

    @Override
    public String saveUser(UserRequestDto request) {
        Role role = roleRepository.findRoleByName(RoleType.USER)
                .orElseThrow(() -> new RuntimeException("Role not found!"));
        saveUserToDatabase(request, role);
        log.info("{} -> user created",request.getUsername());
        return "Save is successfully!";
    }

    private void saveUserToDatabase(UserRequestDto request, Role role) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(role)
                .build();
        userRepository.save(user);

    }

    @Override
    public AuthenticationResponse authenticateUser(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            log.error("Authentication failed: {}", e.getMessage());//Userin aktiv olub olmamağını haradan bilir?
            throw new RuntimeException(e);
        }

        Optional<User> user = userRepository.findUserByUsernameOrEmail(request.getUsername());

        String accessToken=jwtService.generateAccessToken(user.orElseThrow());
        String refreshToken=jwtService.generateRefreshToken(user.orElseThrow());

        log.info("{} -> user loging",request.getUsername());
        return AuthenticationResponse.builder()
                .message(user.orElseThrow().getEmail() + " login is successfully")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthenticationResponse refreshToken(String token, Long id) {
        if (!securityHelper.authHeaderIsValid(token)){
            throw new RuntimeException("Token is wrong!");//
        }

        String jwt = token.substring(7);
        String username = jwtService.extractUsername(jwt);
        Optional<User> userById = userRepository.findById(id);

        if (username != null){
            if (userById.orElseThrow(() -> new RuntimeException(id + " id User not found!"))
                    .getUsername().equals(username)){
                if(jwtService.isTokenValid(jwt,userById.orElseThrow())){
                    String accessToken=jwtService.generateAccessToken(userById.get());
                    String refreshToken=jwtService.generateRefreshToken(userById.get());

                    log.info("{} token refreshing is successfully",username);
                    return AuthenticationResponse.builder()
                            .message("Refreshingg is successfully!")
                            .accessToken(accessToken)
                            .refreshToken(refreshToken)
                            .build();
                }
            }else {
                throw new RuntimeException("UserId is wrong!");
            }
        }else {
            throw new RuntimeException("Token Username is null!");
        }
        return null;
    }


}
