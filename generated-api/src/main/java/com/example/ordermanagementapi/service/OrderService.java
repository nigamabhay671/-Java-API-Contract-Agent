package com.example.ordermanagementapi.service;

import com.example.ordermanagementapi.dto.CreateOrderRequest;
import com.example.ordermanagementapi.dto.OrderDto;
import com.example.ordermanagementapi.dto.UpdateOrderRequest;
import com.example.ordermanagementapi.entity.Order;

import java.util.List;

/**
 * Service interface for Order business logic.
 *
 * Defines the contract for order management operations.
 *
 * @author Java API Contract Agent
 */
public interface OrderService {

    /**
     * Retrieve all orders.
     *
     * @return list of all orders
     */
    List<OrderDto> getAllOrders();

    /**
     * Retrieve an order by ID.
     *
     * @param id the order ID
     * @return the order DTO
     * @throws com.example.ordermanagementapi.exception.ResourceNotFoundException if order not found
     */
    OrderDto getOrderById(Long id);

    /**
     * Create a new order.
     *
     * @param request the create order request
     * @return the created order DTO
     */
    OrderDto createOrder(CreateOrderRequest request);

    /**
     * Update an existing order.
     *
     * @param id the order ID
     * @param request the update order request
     * @return the updated order DTO
     * @throws com.example.ordermanagementapi.exception.ResourceNotFoundException if order not found
     */
    OrderDto updateOrder(Long id, UpdateOrderRequest request);

    /**
     * Delete an order.
     *
     * @param id the order ID
     * @throws com.example.ordermanagementapi.exception.ResourceNotFoundException if order not found
     */
    void deleteOrder(Long id);

    /**
     * Retrieve orders by customer name.
     *
     * @param customerName the customer name
     * @return list of orders for the customer
     */
    List<OrderDto> getOrdersByCustomer(String customerName);

    /**
     * Retrieve orders by status.
     *
     * @param status the order status
     * @return list of orders with the given status
     */
    List<OrderDto> getOrdersByStatus(String status);
}
