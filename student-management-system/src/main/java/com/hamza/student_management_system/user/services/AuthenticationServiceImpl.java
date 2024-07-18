package com.hamza.student_management_system.user.services;

import com.hamza.student_management_system.core.security.requests.AuthRequest;
import com.hamza.student_management_system.core.security.requests.AuthResponse;
import com.hamza.student_management_system.core.security.JwtService;
import com.hamza.student_management_system.core.security.requests.RefreshAuthRequest;
import com.hamza.student_management_system.user.datamodels.RegisterUserDto;
import com.hamza.student_management_system.user.datamodels.UserDto;
import com.hamza.student_management_system.user.facade.interfaces.UserFacade;
import com.hamza.student_management_system.user.services.interfaces.AuthenticationService;
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

    private final UserFacade userFacade;

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
                    .refreshToken(jwtService.generateRefreshToken(authRequest.getUsername()))
                    .build();
        }
    }

    @Override
    public AuthResponse generateTokenWithToken(RefreshAuthRequest refreshAuthRequest) {

        String username = jwtService.extractUserNameFromToken(refreshAuthRequest.getRefreshToken());

        //TODO: re visit this
//        if (!jwtService.validateToken(refreshToken, username)) {
//            //TODO: throw exception
//        }

        return AuthResponse.builder()
                .username(username)
                .token(jwtService.generateToken(username))
                .refreshToken(jwtService.generateRefreshToken(username))
                .build();
    }

    @Override
    public UserDto registerUser(RegisterUserDto userDto) {
        return this.userFacade.addNewUser(userDto);
    }

}
