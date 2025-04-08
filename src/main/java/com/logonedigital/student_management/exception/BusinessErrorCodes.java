package com.logonedigital.student_management.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Getter
public enum BusinessErrorCodes {

    ACCOUNT_LOCKED(302, FORBIDDEN,"Account Locked"),
    ACCOUNT_DISABLED(303, FORBIDDEN,"Account Disabled"),
    BAD_CREDENTIALS(304, FORBIDDEN,"Incorrect credentials"),
    INVALID_CREDENTIALS(305, FORBIDDEN,"Invalid credentials"),
    INVALID_TOKEN(306, FORBIDDEN,"Invalid token"),
    ACCESS_DENIED(307, FORBIDDEN,"Not an ADMIN, You are not allowed to access this resource"),
    ;
    private final int code;
    private final String description;
    private final HttpStatus httpStatus;

    BusinessErrorCodes(int code,  HttpStatus httpStatus,String description) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
