package com.roberto.ecom.domain.enums;

import java.util.stream.Stream;

public enum CustomerType {

    NATURAL_PERSON(1, "Pessoa Física"),
    LEGAL_PERSON(2, "Pessoa Jurídica");

    private int code;
    private String description;

    private CustomerType(int code, String description) {
        this.code = code;
        this.description = description;
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
}
