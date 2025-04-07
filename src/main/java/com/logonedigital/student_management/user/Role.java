package com.logonedigital.student_management.user;

import lombok.Getter;

@Getter
public enum Role {
    ROLE_STUDENT("STUDENT"),
    ROLE_TEACHER("TEACHER"),
    ROLE_ADMIN("ADMIN");

    private Role(String role) {

    }
}
