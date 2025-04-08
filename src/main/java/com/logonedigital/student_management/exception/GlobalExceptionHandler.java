package com.logonedigital.student_management.exception;

import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;

import static com.logonedigital.student_management.exception.BusinessErrorCodes.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExceptionResponse> handleException(LockedException exp) {
        return ResponseEntity.status(UNAUTHORIZED).body(
                new ExceptionResponse(
                        ACCOUNT_LOCKED.getCode(),
                        ACCOUNT_LOCKED.getDescription(),
                        exp.getMessage(),
                        null,
                        null
                )
        );
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponse> handleException(DisabledException exp) {
        return ResponseEntity.status(UNAUTHORIZED).body(
                new ExceptionResponse(
                        ACCOUNT_DISABLED.getCode(),
                        ACCOUNT_DISABLED.getDescription(),
                        exp.getMessage(),
                        null,
                        null
                )
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleException(BadCredentialsException exp) {
        return ResponseEntity.status(UNAUTHORIZED).body(
                new ExceptionResponse(
                        BAD_CREDENTIALS.getCode(),
                        BAD_CREDENTIALS.getDescription(),
                        exp.getMessage(),
                        null,
                        null
                )
        );
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ExceptionResponse> handleException(MessagingException exp) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
                new ExceptionResponse(
                        null,
                        null,
                        exp.getMessage(),
                        null,
                        null
                )
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleException(MethodArgumentNotValidException exp) {
        Set<String> errors = new HashSet<>();
        exp.getBindingResult().getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
        return ResponseEntity.status(BAD_REQUEST).body(
                new ExceptionResponse(
                        null,
                        null,
                        null,
                        errors,
                        null
                )
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception exp) {
        exp.printStackTrace();
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
                new ExceptionResponse(
                        null,
                        "Internal Error, contact the admin",
                        exp.getMessage(),
                        null,
                        null
                )
        );
    }

    @ExceptionHandler(OperationNotPermittedException.class)
    public ResponseEntity<ExceptionResponse> handleException(OperationNotPermittedException exp) {
        return ResponseEntity.status(BAD_REQUEST).body(
                new ExceptionResponse(
                        null,
                        null,
                        exp.getMessage(),
                        null,
                        null
                )
        );
    }

    @ExceptionHandler(TokenNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleException(TokenNotValidException exp) {
        return ResponseEntity.status(BAD_REQUEST).body(
                new ExceptionResponse(
                        INVALID_TOKEN.getCode(),
                        INVALID_TOKEN.getDescription(),
                        exp.getMessage(),
                        null,
                        null
                )
        );
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ExceptionResponse> handleException(AuthorizationDeniedException exp) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
                new ExceptionResponse(
                        ACCESS_DENIED.getCode(),
                        ACCESS_DENIED.getDescription(),
                        exp.getMessage(),
                        null,
                        null
                )
        );
    }
}
