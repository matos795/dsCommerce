package com.devsuperior.dsCommerce.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.devsuperior.dsCommerce.Enums.OrderStatus;
import com.devsuperior.dsCommerce.entities.Order;
import com.devsuperior.dsCommerce.entities.OrderItem;

import jakarta.validation.constraints.NotEmpty;

public class OrderDTO {

    private Long id;
    private Instant moment;
    private OrderStatus status;

    private ClientDTO client;
    private PaymentDTO payment;

    @NotEmpty(message = "Deve ter pelo menos 1 item!")
    private List<OrderItemDTO> items = new ArrayList<>();
    
    public OrderDTO() {
    }

    public OrderDTO(Order entity) {
        id = entity.getId();
        moment = entity.getMoment();
        status = entity.getStatus();
        client = new ClientDTO(entity.getClient());
        payment = (entity.getPayment() == null) ? null : new PaymentDTO(entity.getPayment());
        for (OrderItem item : entity.getItems()) {
            items.add(new OrderItemDTO(item));
        }
    }

    public Long getId() {
        return id;
    }

    public Instant getMoment() {
        return moment;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public ClientDTO getClient() {
        return client;
    }

    public PaymentDTO getPayment() {
        return payment;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public Double getTotal(){
        double total=0.0;
        for (OrderItemDTO item : items) {
            total += item.getSubTotal();
        }
        return total;
    }
    
}
