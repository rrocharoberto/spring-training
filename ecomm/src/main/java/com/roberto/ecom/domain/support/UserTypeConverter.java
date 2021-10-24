package com.roberto.ecom.domain.support;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.roberto.ecom.domain.enums.UserProfile;


@Converter(autoApply = true)
public class UserTypeConverter implements AttributeConverter<UserProfile, Integer> {

    @Override
    public Integer convertToDatabaseColumn(UserProfile profile) {
        return profile == null ? null : profile.getCode();
    }

    @Override
    public UserProfile convertToEntityAttribute(Integer code) {
        return code == null ? null : UserProfile.toEnum(code);
    }
}