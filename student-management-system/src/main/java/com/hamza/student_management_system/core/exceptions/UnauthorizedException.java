package com.hamza.student_management_system.core.exceptions;

import lombok.Getter;

@Getter
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
