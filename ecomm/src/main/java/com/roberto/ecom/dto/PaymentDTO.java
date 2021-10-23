package com.roberto.ecom.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.roberto.ecom.domain.enums.PaymentStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME, 
    include = JsonTypeInfo.As.PROPERTY, 
    property = "paymentType")
@JsonSubTypes(
    { 
        @Type(value = PaymentBulletDTO.class, name = "bullet"),
        @Type(value = PaymentCardDTO.class, name = "card") 
    }
)

@Getter
@Setter
@NoArgsConstructor
@ToString
public abstract class PaymentDTO {

    private Integer status;

    public PaymentDTO(PaymentStatus status) {
        this.status = status.getCode();
    }
}
