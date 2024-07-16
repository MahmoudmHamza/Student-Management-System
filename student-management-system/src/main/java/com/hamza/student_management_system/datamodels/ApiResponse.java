package com.hamza.student_management_system.datamodels;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class ApiResponse {
    private final int status;
    private final String message;
}
