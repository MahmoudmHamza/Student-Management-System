package com.hamza.student_management_system.core.exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class ApiErrorResponse {
    private final int status;
    private final String message;
}
