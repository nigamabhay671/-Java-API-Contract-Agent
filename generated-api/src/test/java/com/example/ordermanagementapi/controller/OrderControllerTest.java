package com.example.ordermanagementapi.controller;

import com.example.ordermanagementapi.dto.CreateOrderItemRequest;
import com.example.ordermanagementapi.dto.CreateOrderRequest;
import com.example.ordermanagementapi.dto.OrderDto;
import com.example.ordermanagementapi.dto.UpdateOrderRequest;
import com.example.ordermanagementapi.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for OrderController.
 *
 * @author Java API Contract Agent
 */
@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    private OrderDto orderDto;
    private CreateOrderRequest createRequest;

    @BeforeEach
    void setUp() {
        orderDto = OrderDto.builder()
                .id(1L)
                .customerName("John Doe")
                .totalAmount(new BigDecimal("299.99"))
                .status("PENDING")
                .orderDate(LocalDateTime.now())
                .items(new ArrayList<>())
                .build();

        CreateOrderItemRequest itemRequest = new CreateOrderItemRequest(
                "Laptop",
                1,
                new BigDecimal("299.99")
        );

        createRequest = new CreateOrderRequest(
                "John Doe",
                List.of(itemRequest)
        );
    }

    @Test
    void getAllOrders_ReturnsOrderList() throws Exception {
        List<OrderDto> orders = List.of(orderDto);
        when(orderService.getAllOrders()).thenReturn(orders);

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].customerName", is("John Doe")));
    }

    @Test
    void getOrderById_WhenExists_ReturnsOrder() throws Exception {
        when(orderService.getOrderById(1L)).thenReturn(orderDto);

        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.customerName", is("John Doe")));
    }

    @Test
    void createOrder_WithValidRequest_ReturnsCreated() throws Exception {
        when(orderService.createOrder(any(CreateOrderRequest.class))).thenReturn(orderDto);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerName", is("John Doe")));
    }

    @Test
    void createOrder_WithInvalidRequest_ReturnsBadRequest() throws Exception {
        CreateOrderRequest invalidRequest = new CreateOrderRequest();
        invalidRequest.setCustomerName(""); // Invalid: blank
        invalidRequest.setItems(List.of()); // Invalid: empty

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateOrder_WithValidRequest_ReturnsUpdatedOrder() throws Exception {
        UpdateOrderRequest updateRequest = new UpdateOrderRequest();
        updateRequest.setCustomerName("Jane Doe");
        updateRequest.setStatus("PROCESSING");

        when(orderService.updateOrder(eq(1L), any(UpdateOrderRequest.class))).thenReturn(orderDto);

        mockMvc.perform(put("/api/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteOrder_WhenExists_ReturnsNoContent() throws Exception {
        doNothing().when(orderService).deleteOrder(1L);

        mockMvc.perform(delete("/api/orders/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getOrdersByCustomer_ReturnsOrderList() throws Exception {
        List<OrderDto> orders = List.of(orderDto);
        when(orderService.getOrdersByCustomer("John Doe")).thenReturn(orders);

        mockMvc.perform(get("/api/orders/customer/John Doe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].customerName", is("John Doe")));
    }

    @Test
    void getOrdersByStatus_ReturnsOrderList() throws Exception {
        List<OrderDto> orders = List.of(orderDto);
        when(orderService.getOrdersByStatus("PENDING")).thenReturn(orders);

        mockMvc.perform(get("/api/orders/status/PENDING"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].status", is("PENDING")));
    }
}
