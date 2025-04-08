package com.logonedigital.student_management.user;

import lombok.Getter;

@Getter
public enum Role {
    ROLE_ADMIN("ADMIN"),
    ROLE_STUDENT("STUDENT"),
    ROLE_TEACHER("TEACHER");

    private Role(String role){}
}
