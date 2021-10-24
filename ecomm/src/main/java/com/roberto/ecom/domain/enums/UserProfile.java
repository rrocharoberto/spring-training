package com.roberto.ecom.domain.enums;

import java.util.stream.Stream;

import lombok.Getter;

@Getter
public enum UserProfile {

    ADMIN(1, "ROLE_ADMIN"),
    CUSTOMER(2, "ROLE_CUSTOMER");

    private int code;
    private String description;

    private UserProfile(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static UserProfile toEnum(int code) {

        return Stream.of(UserProfile.values())
          .filter(t -> t.getCode() == code)
          .findFirst()
          .orElseThrow(() -> new IllegalArgumentException("Invalid customer profile code: " + code));
    }
}
