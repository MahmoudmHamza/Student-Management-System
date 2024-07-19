package com.hamza.student_management_system.core.aop;

import com.hamza.student_management_system.core.exceptions.ApiErrorResponse;
import com.hamza.student_management_system.core.exceptions.CourseRegistrationException;
import com.hamza.student_management_system.core.exceptions.UnauthorizedException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(Exception exception) {
        return this.logAndRespond(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleEntityNotFoundException(EntityNotFoundException exception) {
        return this.logAndRespond(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    //TODO: consider remove this
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiErrorResponse> handleNotAuthorizedException(UnauthorizedException exception) {
        return this.logAndRespond(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleExpiredJwtException(UsernameNotFoundException exception) {
        return this.logAndRespond(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiErrorResponse> handleExpiredJwtException(ExpiredJwtException exception) {
        return this.logAndRespond(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ApiErrorResponse> handleNullPointerException(NullPointerException exception) {
        return this.logAndRespond(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @ExceptionHandler(CourseRegistrationException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(CourseRegistrationException exception) {
        return this.logAndRespond(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleMissingArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        return this.logAndRespond(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgumentException(IllegalArgumentException exception) {
        return this.logAndRespond(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiErrorResponse> handleRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        return this.logAndRespond(HttpStatus.METHOD_NOT_ALLOWED, exception.getMessage());
    }

    private ResponseEntity<ApiErrorResponse> logAndRespond(HttpStatus httpStatus, String message) {
        log.error(String.format("Exception caught with the message: ((%s))", message));
        ApiErrorResponse error = ApiErrorResponse.builder()
                .status(httpStatus.value())
                .message(message)
                .build();

        return new ResponseEntity<>(error, httpStatus);
    }
}
