package com.logonedigital.student_management.email;

import lombok.Getter;

public enum EmailTemplateName {
    ACTIVATE_ACCOUNT("activate_account");

    EmailTemplateName(String name) {
        this.name = name;
    }

    private final String name;

    public String getName() {
        return name;
    }
}
