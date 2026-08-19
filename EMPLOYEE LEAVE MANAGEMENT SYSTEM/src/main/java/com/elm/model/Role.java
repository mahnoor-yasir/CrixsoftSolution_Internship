package com.elm.model;

public enum Role {
    ADMIN("Administrator"),
    MANAGER("Manager"),
    EMPLOYEE("Employee");

    private String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}