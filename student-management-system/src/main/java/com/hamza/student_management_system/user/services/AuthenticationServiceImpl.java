package com.hamza.student_management_system.user.services;

import com.hamza.student_management_system.core.exceptions.UnauthorizedException;
import com.hamza.student_management_system.core.security.RefreshTokenService;
import com.hamza.student_management_system.core.security.entities.RefreshToken;
import com.hamza.student_management_system.core.security.requests.AuthRequest;
import com.hamza.student_management_system.core.security.requests.AuthResponse;
import com.hamza.student_management_system.core.security.JwtService;
import com.hamza.student_management_system.core.security.requests.RefreshTokenRequest;
import com.hamza.student_management_system.user.datamodels.RegisterUserDto;
import com.hamza.student_management_system.user.datamodels.UserDto;
import com.hamza.student_management_system.user.facade.interfaces.UserFacade;
import com.hamza.student_management_system.user.services.interfaces.AuthenticationService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;

    private final UserFacade userFacade;

    @Override
    @CacheEvict(value = {"userCourses", "userDetails"})
    public AuthResponse authenticate(AuthRequest authRequest) {

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()
                    )
            );

            if (auth.isAuthenticated()) {

                RefreshToken refreshTokenObject = this.refreshTokenService.createRefreshToken(authRequest.getUsername());
                return AuthResponse.builder()
                        .username(authRequest.getUsername())
                        .token(jwtService.generateToken(authRequest.getUsername()))
                        .refreshToken(refreshTokenObject.getToken())
                        .build();
            } else {
                throw new UsernameNotFoundException("Invalid credentials");
            }

        } catch (Exception e) {
            throw new UsernameNotFoundException("Invalid credentials");
        }
    }

    @Override
    public AuthResponse generateTokenWithToken(RefreshTokenRequest refreshAuthRequest) {
        String username = null;

        try {
            username = jwtService.extractUserNameFromToken(refreshAuthRequest.getRefreshToken());

            if (!jwtService.validateToken(refreshAuthRequest.getRefreshToken(), userDetailsService.loadUserByUsername(username))) {
                this.refreshTokenService.removeByToken(refreshAuthRequest.getRefreshToken());
                throw new UnauthorizedException("Session expired, you need to login again!");
            }

            return AuthResponse.builder()
                    .username(username)
                    .token(jwtService.generateToken(username))
                    .refreshToken(refreshAuthRequest.getRefreshToken())
                    .build();

        } catch (ExpiredJwtException ex) {
            this.refreshTokenService.removeByToken(refreshAuthRequest.getRefreshToken());
            throw new UnauthorizedException("Session expired, you need to login again!");
        }
    }

    @Override
    public UserDto registerUser(RegisterUserDto userDto) {
        return this.userFacade.addNewUser(userDto);
    }

}
