package com.hamza.student_management_system.core.security.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshAuthRequest {
    private String refreshToken;
}
