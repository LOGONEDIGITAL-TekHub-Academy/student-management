package com.logonedigital.student_management.email;

import lombok.Getter;

@Getter
public enum EmailTemplateName {
    ACTIVATE_ACCOUNT("activate_account"),
    REPORT("report")
    ;

    EmailTemplateName(String name) {
        this.name = name;
    }

    private final String name;

}
