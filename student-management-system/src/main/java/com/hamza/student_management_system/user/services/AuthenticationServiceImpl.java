package com.hamza.student_management_system.user.services;

import com.hamza.student_management_system.core.security.models.AuthRequest;
import com.hamza.student_management_system.core.security.models.AuthResponse;
import com.hamza.student_management_system.core.security.JwtService;
import com.hamza.student_management_system.user.datamodels.RegisterUserDto;
import com.hamza.student_management_system.user.datamodels.UserDto;
import com.hamza.student_management_system.user.entities.User;
import com.hamza.student_management_system.user.services.interfaces.AuthenticationService;
import com.hamza.student_management_system.user.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    //TODO: replace with user facade
    private final UserService userService;

    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );

        if (!auth.isAuthenticated()) {
            throw new UsernameNotFoundException("Invalid credentials");
        } else {
            return AuthResponse.builder()
                    .username(authRequest.getUsername())
                    .token(jwtService.generateToken(authRequest.getUsername()))
                    .build();
        }
    }

    @Override
    public UserDto registerUser(RegisterUserDto userDto) {
        User fetchedUser = this.userService.createUser(userDto);
        return UserDto.builder()
                .username(fetchedUser.getUsername())
                .email(fetchedUser.getEmail())
                .firstName(fetchedUser.getFirstName())
                .lastName(fetchedUser.getLastName())
                .build();
    }

}
