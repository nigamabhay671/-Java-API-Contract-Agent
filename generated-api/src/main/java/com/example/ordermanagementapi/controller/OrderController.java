package com.example.ordermanagementapi.controller;

import com.example.ordermanagementapi.dto.CreateOrderRequest;
import com.example.ordermanagementapi.dto.OrderDto;
import com.example.ordermanagementapi.dto.UpdateOrderRequest;
import com.example.ordermanagementapi.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Order Management operations.
 *
 * Provides endpoints for CRUD operations on orders.
 *
 * @author Java API Contract Agent
 */
@RestController
@RequestMapping("/api/orders")
@Validated
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Orders", description = "Order management operations")
public class OrderController {

    private final OrderService orderService;

    /**
     * Get all orders.
     *
     * @return list of all orders
     */
    @GetMapping
    @Operation(summary = "Get all orders", description = "Retrieve a list of all orders")
    @ApiResponse(responseCode = "200", description = "List of orders retrieved successfully")
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        log.info("GET /api/orders - Get all orders");
        List<OrderDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    /**
     * Get order by ID.
     *
     * @param id the order ID
     * @return the order
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID", description = "Retrieve a specific order by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        log.info("GET /api/orders/{} - Get order by id", id);
        OrderDto order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    /**
     * Create a new order.
     *
     * @param request the create order request
     * @return the created order
     */
    @PostMapping
    @Operation(summary = "Create a new order", description = "Create a new order with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        log.info("POST /api/orders - Create new order for customer: {}", request.getCustomerName());
        OrderDto createdOrder = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    /**
     * Update an existing order.
     *
     * @param id the order ID
     * @param request the update order request
     * @return the updated order
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update an order", description = "Update an existing order with new details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<OrderDto> updateOrder(
            @PathVariable Long id,
            @Valid @RequestBody UpdateOrderRequest request) {
        log.info("PUT /api/orders/{} - Update order", id);
        OrderDto updatedOrder = orderService.updateOrder(id, request);
        return ResponseEntity.ok(updatedOrder);
    }

    /**
     * Delete an order.
     *
     * @param id the order ID
     * @return no content response
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an order", description = "Delete an order by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Order deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        log.info("DELETE /api/orders/{} - Delete order", id);
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get orders by customer name.
     *
     * @param customerName the customer name
     * @return list of orders for the customer
     */
    @GetMapping("/customer/{customerName}")
    @Operation(summary = "Get orders by customer name", description = "Retrieve all orders for a specific customer")
    @ApiResponse(responseCode = "200", description = "Orders retrieved successfully")
    public ResponseEntity<List<OrderDto>> getOrdersByCustomer(@PathVariable String customerName) {
        log.info("GET /api/orders/customer/{} - Get orders by customer", customerName);
        List<OrderDto> orders = orderService.getOrdersByCustomer(customerName);
        return ResponseEntity.ok(orders);
    }

    /**
     * Get orders by status.
     *
     * @param status the order status
     * @return list of orders with the given status
     */
    @GetMapping("/status/{status}")
    @Operation(summary = "Get orders by status", description = "Retrieve all orders with a specific status")
    @ApiResponse(responseCode = "200", description = "Orders retrieved successfully")
    public ResponseEntity<List<OrderDto>> getOrdersByStatus(@PathVariable String status) {
        log.info("GET /api/orders/status/{} - Get orders by status", status);
        List<OrderDto> orders = orderService.getOrdersByStatus(status);
        return ResponseEntity.ok(orders);
    }
}
