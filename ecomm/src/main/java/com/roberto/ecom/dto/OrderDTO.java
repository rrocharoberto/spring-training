package com.roberto.ecom.dto;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.roberto.ecom.domain.Order;
import com.roberto.ecom.domain.PaymentBullet;
import com.roberto.ecom.domain.PaymentCard;
import com.roberto.ecom.domain.enums.PaymentStatus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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

    @JsonIgnore
    private Integer orderId;

    @JsonIgnore
    private String customerName;

    @JsonIgnore
    private String customerEmail;

    public OrderDTO(Order order) {
        this.orderId = order.getId();
        this.customerId = order.getCustomer().getId();
        this.customerName = order.getCustomer().getName();
        this.customerEmail = order.getCustomer().getEmail();
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

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Override
    public String toString() {
        StringBuffer itemsStr = new StringBuffer();
        for (OrderItemDTO i : items) {
            itemsStr.append(i);
        }
        return "Order: " + orderId 
            + "\nDate Created=" + formatter.format(dateCreated) 
            + "\nCustomer=" + customerName
            + "\nPayment Status=" + PaymentStatus.toEnum(payment.getStatus()).getDescription() 
            + "\nDetails:\n" + itemsStr
            + "TotalValue=" + nf.format(getTotalValue());
    }
}