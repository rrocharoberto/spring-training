package com.roberto.ecom.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.roberto.ecom.domain.Address;
import com.roberto.ecom.domain.Customer;
import com.roberto.ecom.domain.Order;
import com.roberto.ecom.domain.OrderItem;
import com.roberto.ecom.domain.Payment;
import com.roberto.ecom.domain.Product;
import com.roberto.ecom.domain.enums.PaymentStatus;
import com.roberto.ecom.dto.OrderDTO;
import com.roberto.ecom.dto.OrderItemDTO;
import com.roberto.ecom.dto.PaymentBulletDTO;
import com.roberto.ecom.dto.PaymentCardDTO;
import com.roberto.ecom.dto.PaymentDTO;
import com.roberto.ecom.repositories.AddressRepository;
import com.roberto.ecom.repositories.OrderItemRepository;
import com.roberto.ecom.repositories.OrderRepository;
import com.roberto.ecom.repositories.PaymentRepository;
import com.roberto.ecom.security.EcomUserPrincipal;
import com.roberto.ecom.services.exceptions.AuthorizationException;
import com.roberto.ecom.services.exceptions.ECommerceException;
import com.roberto.ecom.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repo;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AddressRepository addressRepo;

    @Autowired
    private BulletService bulletService;

    @Autowired
    private PaymentRepository paymentRepo;

    @Autowired
    private CardService cardService;
    
    @Autowired
    private ProductService productService;

    @Autowired
    private OrderItemRepository itemRepo;

    public static final Double DEFAULT_DISCOUNT = 0.0;

    public List<OrderDTO> findAll() {
        return repo.findAll().stream().map(o -> new OrderDTO(o)).collect(Collectors.toList());
    }

    public OrderDTO findById(Integer id) {
        Optional<Order> obj = repo.findById(id);
        Order order = obj.orElseThrow(() -> new ObjectNotFoundException("Order " + id + " not found."));
        return new OrderDTO(order);
    }

    public Page<Order> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {

        EcomUserPrincipal user = UserService.getAuthenticated();
        if (user == null) {
            throw new AuthorizationException("Access denied: customer with different id of user logged in.");
        }

        Customer customer = customerService.findById(user.getId());

        Pageable pageable = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return repo.findByCustomer(customer, pageable);
    }

    public Order createOrder(OrderDTO orderDTO) {

        Order order = this.toOrderEntity(orderDTO);
        repo.save(order);
        paymentRepo.save(order.getPayment());

        for (OrderItemDTO itemDTO: orderDTO.getItems()) {
            Product product = productService.findById(itemDTO.getProductId());

            OrderItem item = new OrderItem(order, product, DEFAULT_DISCOUNT, itemDTO.getAmount(), product.getPrice());
            itemRepo.save(item);
        }
        return order;
    }

    public Order toOrderEntity(@Valid OrderDTO orderDTO) {
        Customer customer = customerService.findById(orderDTO.getCustomerId());
        Address shippingAddress = 
            addressRepo.findById(orderDTO.getShipAddressId())
            .orElseThrow(() -> new ObjectNotFoundException("Address not found: " + orderDTO.getShipAddressId()));

        Order order = new Order(null, LocalDateTime.now(), customer, shippingAddress);
        Payment payment = toPaymentEntity(orderDTO.getPayment(), order);

        order.setPayment(payment);
        return order;
    }

    private Payment toPaymentEntity(PaymentDTO paymentDTO, Order order) {
        Payment payment;
        if(paymentDTO instanceof PaymentBulletDTO) {
            payment = bulletService.createBullet((PaymentBulletDTO) paymentDTO, order);
        } else if(paymentDTO instanceof PaymentCardDTO) {
            payment = cardService.createCard((PaymentCardDTO) paymentDTO, order);
        } else { //acho que não precisa deste else por causa da valiação do JSON
            throw new ECommerceException("Payment has an invalid type: " + paymentDTO);
        }
        payment.setStatus(PaymentStatus.PENDING);
        return payment;
    }
}
