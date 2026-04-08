package com.example.ordermanagementapi.service.impl;

import com.example.ordermanagementapi.dto.CreateOrderRequest;
import com.example.ordermanagementapi.dto.OrderDto;
import com.example.ordermanagementapi.dto.UpdateOrderRequest;
import com.example.ordermanagementapi.entity.Order;
import com.example.ordermanagementapi.entity.OrderItem;
import com.example.ordermanagementapi.exception.ResourceNotFoundException;
import com.example.ordermanagementapi.mapper.OrderMapper;
import com.example.ordermanagementapi.repository.OrderRepository;
import com.example.ordermanagementapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of OrderService.
 *
 * Provides business logic for order management operations.
 *
 * @author Java API Contract Agent
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> getAllOrders() {
        log.info("Fetching all orders");
        List<Order> orders = orderRepository.findAll();
        log.debug("Found {} orders", orders.size());
        return orderMapper.toDtoList(orders);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDto getOrderById(Long id) {
        log.info("Fetching order with id: {}", id);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        return orderMapper.toDto(order);
    }

    @Override
    @Transactional
    public OrderDto createOrder(CreateOrderRequest request) {
        log.info("Creating new order for customer: {}", request.getCustomerName());

        // Convert request to entity
        Order order = orderMapper.toEntity(request);

        // Set up bidirectional relationship for order items
        order.getItems().forEach(item -> item.setOrder(order));

        // Calculate total amount
        order.calculateTotalAmount();

        // Save order
        Order savedOrder = orderRepository.save(order);
        log.info("Order created successfully with id: {}", savedOrder.getId());

        return orderMapper.toDto(savedOrder);
    }

    @Override
    @Transactional
    public OrderDto updateOrder(Long id, UpdateOrderRequest request) {
        log.info("Updating order with id: {}", id);

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

        // Update basic fields
        if (request.getCustomerName() != null) {
            order.setCustomerName(request.getCustomerName());
        }

        if (request.getStatus() != null) {
            try {
                order.setStatus(Order.OrderStatus.valueOf(request.getStatus().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid status value: " + request.getStatus());
            }
        }

        // Update items if provided
        if (request.getItems() != null && !request.getItems().isEmpty()) {
            // Clear existing items
            order.getItems().clear();

            // Add new items
            request.getItems().forEach(itemRequest -> {
                OrderItem item = orderMapper.toOrderItem(itemRequest);
                order.addItem(item);
            });

            // Recalculate total amount
            order.calculateTotalAmount();
        }

        Order updatedOrder = orderRepository.save(order);
        log.info("Order updated successfully with id: {}", updatedOrder.getId());

        return orderMapper.toDto(updatedOrder);
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        log.info("Deleting order with id: {}", id);

        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Order not found with id: " + id);
        }

        orderRepository.deleteById(id);
        log.info("Order deleted successfully with id: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> getOrdersByCustomer(String customerName) {
        log.info("Fetching orders for customer: {}", customerName);
        List<Order> orders = orderRepository.findByCustomerName(customerName);
        log.debug("Found {} orders for customer: {}", orders.size(), customerName);
        return orderMapper.toDtoList(orders);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> getOrdersByStatus(String status) {
        log.info("Fetching orders with status: {}", status);

        Order.OrderStatus orderStatus;
        try {
            orderStatus = Order.OrderStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status value: " + status);
        }

        List<Order> orders = orderRepository.findByStatus(orderStatus);
        log.debug("Found {} orders with status: {}", orders.size(), status);
        return orderMapper.toDtoList(orders);
    }
}
