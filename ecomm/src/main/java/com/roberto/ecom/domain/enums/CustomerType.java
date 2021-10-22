package com.roberto.ecom.domain.enums;

import java.util.stream.Stream;

import com.roberto.ecom.services.validation.CustomerGroups;

public enum CustomerType {

    NATURAL_PERSON(1, "Pessoa Física", CustomerGroups.CPFGroup.class),
    LEGAL_PERSON(2, "Pessoa Jurídica", CustomerGroups.CNPJGroup.class);

    private int code;
    private String description;
    private Class<?> group;

    private CustomerType(int code, String description, Class<?> group) {
        this.code = code;
        this.description = description;
        this.group = group;
    }

    public static CustomerType toEnum(int code) {

        return Stream.of(CustomerType.values())
          .filter(t -> t.getCode() == code)
          .findFirst()
          .orElseThrow(() -> new IllegalArgumentException("Invalid customer type code: " + code));

        // for (CustomerType cType : CustomerType.values()) {
        //     if(code.equals(cType.code)) {
        //         return cType;
        //     }
        // }
        // throw new IllegalArgumentException("Invalid customer type code: " + code);
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public Class<?> getGroup() {
        return group;
    }
}
