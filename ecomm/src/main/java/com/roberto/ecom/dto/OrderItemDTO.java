package com.roberto.ecom.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.roberto.ecom.domain.OrderItem;

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
public class OrderItemDTO {

    @NotNull(message = "ProductId can not be null.")
    @Size(min = 0, message = "ProductId must not be negative.")
    private Integer productId;

    @NotNull(message = "Amount can not be null.")
    @Size(min = 0, message = "Amunt must must not be negative.")
    private Integer amount;

    private Double price;

    public OrderItemDTO(OrderItem item) {
        this.productId = item.getProduct().getId();
        this.amount = item.getAmount();
        this.price = item.getProduct().getPrice();
    }

    public Double getSubTotal() {
        return amount * price;
    }
}
