package com.roberto.ecom.dto;

import java.text.NumberFormat;
import java.util.Locale;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.roberto.ecom.domain.OrderItem;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {

    @NotNull(message = "ProductId can not be null.")
    @Size(min = 0, message = "ProductId must not be negative.")
    private Integer productId;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String productName;

    @NotNull(message = "Amount can not be null.")
    @Size(min = 0, message = "Amunt must must not be negative.")
    private Integer amount;

    private Double price;

    public OrderItemDTO(OrderItem item) {
        this.productId = item.getProduct().getId();
        this.productName = item.getProduct().getName();
        this.amount = item.getAmount();
        this.price = item.getProduct().getPrice();
    }

    public Double getSubTotal() {
        return amount * price;
    }

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    @Override
    public String toString() {
        return productName + ": amount=" + amount 
            + ", price=" + nf.format(price) + ", subTotal="
            + nf.format(getSubTotal()) + "\n";
    }
}