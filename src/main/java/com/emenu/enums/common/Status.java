package com.emenu.enums.common;

import lombok.Getter;

@Getter
public enum Status {
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    DELETED("DELETED");

    private final String description;

    Status(String description) {
        this.description = description;
    }
}