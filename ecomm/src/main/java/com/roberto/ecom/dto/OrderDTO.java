package com.roberto.ecom.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.roberto.ecom.domain.Order;
import com.roberto.ecom.domain.PaymentBullet;
import com.roberto.ecom.domain.PaymentCard;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class OrderDTO {

    //@JsonManagedReference
    //@JsonBackReference // deserialization works fine
    //@JsonIgnore //deserialization does not work, the reference will be null   @NotNull(message = "CustomerId can not be null.")
    @NotEmpty(message = "CustomerId can not be empty.")
    private String customerId;

    @NotNull(message = "ShipAddress can not be null.")
    @Min(0) // , message = "ShipAddress must not be negative.")
    private Integer shipAddressId;

    @NotNull(message = "Payment can not be null.")
    private PaymentDTO payment;

    @NotEmpty(message = "Items can not be empty.")
    private List<OrderItemDTO> items;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dateCreated;

    public OrderDTO(Order order) {
        this.customerId = order.getCustomer().getId();
        this.shipAddressId = order.getShippingAddress().getId();
        this.dateCreated = order.getDateCreated();

        if (order.getPayment() instanceof PaymentBullet) {
            PaymentBullet p = (PaymentBullet) order.getPayment();
            this.payment = new PaymentBulletDTO(p.getPaymentDate(), p.getDueDate());
        } else if (order.getPayment() instanceof PaymentCard) {
            PaymentCard p = (PaymentCard) order.getPayment();
            this.payment = new PaymentCardDTO(p.getNumberOfInstallments());
        }
        this.payment.setStatus(order.getPayment().getStatus().getCode());

        this.items = order.getItems().stream().map(i -> new OrderItemDTO(i)).collect(Collectors.toList());
    }

    public Double getTotalValue() {
        return items.stream().mapToDouble(i -> i.getSubTotal()).sum();
    }
}
