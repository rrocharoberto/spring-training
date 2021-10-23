package com.roberto.ecom.dto;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentBulletDTO extends PaymentDTO {

    @JsonFormat(pattern = "dd/MM/yyyy")
    private OffsetDateTime paymentDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dueDate;
}
