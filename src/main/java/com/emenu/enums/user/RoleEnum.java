package com.emenu.enums.user;

import lombok.Getter;

@Getter
public enum RoleEnum {
    // Platform Roles
    DEVELOPER("Developer", "Full platform control"),
    SUPER_AMIN("Super Admin", "Platform administration"),
    STAFF("Staff", "Staff operations"),
    CUSTOMER("Customer", "Customer operations");

    private final String displayName;
    private final String description;

    RoleEnum(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
}