package com.hamza.student_management_system.core.exceptions;

import lombok.Getter;

@Getter
public class CourseRegistrationException extends RuntimeException {
    public CourseRegistrationException(String message) {
        super(message);
    }
}
