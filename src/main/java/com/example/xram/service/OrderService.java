package com.example.xram.service;

import com.example.xram.enums.OrderStatus;
import com.example.xram.model.*;
import com.example.xram.repository.OrderRepository;
import com.example.xram.dto.OrderRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final Cart cart;

    @Transactional
    public Order createOrder(OrderRequest orderRequest) {
        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        Order order = new Order();
        order.setCustomerName(orderRequest.getCustomerName());
        order.setCustomerEmail(orderRequest.getCustomerEmail());
        order.setCustomerPhone(orderRequest.getCustomerPhone());
        order.setAddress(orderRequest.getAddress());
        order.setTotalAmount(cart.getTotalPrice());

        // Преобразуем элементы корзины в элементы заказа
        for (CartItem cartItem : cart.getItems().values()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            orderItem.setTotalPrice(cartItem.getTotalPrice());
            order.getOrderItems().add(orderItem);
        }

        // Сохраняем заказ в базу данных
        Order savedOrder = orderRepository.save(order);

        // Очищаем корзину после создания заказа
        cart.getItems().clear();
        cart.setTotalPrice(BigDecimal.ZERO);

        return savedOrder;
    }

    // Получить все заказы
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Получить заказ по ID
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Заказ не найден"));
    }

    // Получить заказы по статусу
    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    // Обновить статус заказа
    @Transactional
    public Order updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = getOrderById(orderId);
        order.setStatus(newStatus);
        return orderRepository.save(order);
    }

}