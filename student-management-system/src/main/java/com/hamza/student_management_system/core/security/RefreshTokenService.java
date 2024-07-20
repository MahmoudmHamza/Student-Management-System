package com.hamza.student_management_system.core.security;

import com.hamza.student_management_system.core.security.entities.RefreshToken;
import com.hamza.student_management_system.core.security.repositories.RefreshTokenRepository;
import com.hamza.student_management_system.user.entities.User;
import com.hamza.student_management_system.user.services.interfaces.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;
    private final JwtService jwtService;

    public RefreshToken createRefreshToken(String username) {
        User user = userService.findUserByName(username);
        RefreshToken userToken = this.findByUserId(user.getId());

        if (userToken != null) {
            this.refreshTokenRepository.delete(userToken);
        }

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(jwtService.generateRefreshToken(username))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public void removeByToken(String token) {
        RefreshToken tokenObject = this.findByToken(token);
        this.refreshTokenRepository.delete(tokenObject);
    }

    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Something went wrong while fetching refresh token object"));
    }

    public RefreshToken findByUserId(Long userId) {
        return refreshTokenRepository.findByUserId(userId).orElse(null);
    }
}