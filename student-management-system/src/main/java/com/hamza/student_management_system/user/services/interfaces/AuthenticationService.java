package com.hamza.student_management_system.user.services.interfaces;

import com.hamza.student_management_system.core.security.requests.AuthRequest;
import com.hamza.student_management_system.core.security.requests.AuthResponse;
import com.hamza.student_management_system.core.security.requests.RefreshTokenRequest;
import com.hamza.student_management_system.user.datamodels.RegisterUserDto;
import com.hamza.student_management_system.user.datamodels.UserDto;

public interface AuthenticationService {
    AuthResponse authenticate(AuthRequest authRequest);
    UserDto registerUser(RegisterUserDto userDto);
    AuthResponse generateTokenWithToken(RefreshTokenRequest refreshAuthRequest);
}
