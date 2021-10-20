package com.roberto.ecom.domain.support;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.roberto.ecom.domain.enums.CustomerType;

@Converter(autoApply = true)
public class CategoryConverter implements AttributeConverter<CustomerType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(CustomerType customerType) {
        return customerType == null ? null : customerType.getCode();
    }

    @Override
    public CustomerType convertToEntityAttribute(Integer code) {
        return code == null ? null : CustomerType.toEnum(code);
    }
}