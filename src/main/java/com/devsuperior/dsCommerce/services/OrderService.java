package com.devsuperior.dsCommerce.services;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsCommerce.Enums.OrderStatus;
import com.devsuperior.dsCommerce.dto.OrderDTO;
import com.devsuperior.dsCommerce.dto.OrderItemDTO;
import com.devsuperior.dsCommerce.entities.Order;
import com.devsuperior.dsCommerce.entities.OrderItem;
import com.devsuperior.dsCommerce.entities.Product;
import com.devsuperior.dsCommerce.entities.User;
import com.devsuperior.dsCommerce.repositories.OrderItemRepository;
import com.devsuperior.dsCommerce.repositories.OrderRepository;
import com.devsuperior.dsCommerce.repositories.ProductRepository;
import com.devsuperior.dsCommerce.services.exceptions.ResourceNotFoundException;


@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        Order order = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado!"));
        authService.validateSelfOrAdmin(order.getClient().getId());
        return new OrderDTO(order);
    }

    @Transactional
    public OrderDTO insert(OrderDTO dto) {
        
        Order entity =  new Order();

        entity.setMoment(Instant.now());
        entity.setStatus(OrderStatus.WAITING_PAYMENT);

        User user = userService.authenticated();
        entity.setClient(user);

        for (OrderItemDTO itemDTO : dto.getItems()) {
            OrderItem item = new OrderItem();
            Product product = productRepository.getReferenceById(itemDTO.getProductId());

            item.setOrder(entity);
            item.setProduct(product);
            item.setQuantity(itemDTO.getQuantity());
            item.setPrice(product.getPrice());

            entity.getItems().add(item);
        }
        
        repository.save(entity);
        orderItemRepository.saveAll(entity.getItems());

        return new OrderDTO(entity);
     }
}
