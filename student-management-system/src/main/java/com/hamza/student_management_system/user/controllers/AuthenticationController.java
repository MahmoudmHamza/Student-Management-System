package com.hamza.student_management_system.user.controllers;

import com.hamza.student_management_system.core.security.models.AuthRequest;
import com.hamza.student_management_system.core.security.models.AuthResponse;
import com.hamza.student_management_system.user.datamodels.RegisterUserDto;
import com.hamza.student_management_system.user.datamodels.UserDto;
import com.hamza.student_management_system.user.services.interfaces.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerNewUser(@RequestBody RegisterUserDto registerUserDto) {
        return ResponseEntity.ok(authenticationService.registerUser(registerUserDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest){
        return ResponseEntity.ok(authenticationService.authenticate(authRequest));
    }
}
