package com.example.ordermanagementapi.service;

import com.example.ordermanagementapi.dto.CreateOrderItemRequest;
import com.example.ordermanagementapi.dto.CreateOrderRequest;
import com.example.ordermanagementapi.dto.OrderDto;
import com.example.ordermanagementapi.dto.UpdateOrderRequest;
import com.example.ordermanagementapi.entity.Order;
import com.example.ordermanagementapi.entity.OrderItem;
import com.example.ordermanagementapi.exception.ResourceNotFoundException;
import com.example.ordermanagementapi.mapper.OrderMapper;
import com.example.ordermanagementapi.repository.OrderRepository;
import com.example.ordermanagementapi.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for OrderServiceImpl.
 *
 * @author Java API Contract Agent
 */
@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order order;
    private OrderDto orderDto;
    private CreateOrderRequest createRequest;

    @BeforeEach
    void setUp() {
        order = Order.builder()
                .id(1L)
                .customerName("John Doe")
                .totalAmount(new BigDecimal("299.99"))
                .status(Order.OrderStatus.PENDING)
                .orderDate(LocalDateTime.now())
                .items(new ArrayList<>())
                .build();

        OrderItem item = OrderItem.builder()
                .id(1L)
                .productName("Laptop")
                .quantity(1)
                .unitPrice(new BigDecimal("299.99"))
                .order(order)
                .build();
        order.getItems().add(item);

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
    void getAllOrders_ReturnsOrderList() {
        List<Order> orders = List.of(order);
        when(orderRepository.findAll()).thenReturn(orders);
        when(orderMapper.toDtoList(orders)).thenReturn(List.of(orderDto));

        List<OrderDto> result = orderService.getAllOrders();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(orderRepository).findAll();
        verify(orderMapper).toDtoList(orders);
    }

    @Test
    void getOrderById_WhenExists_ReturnsOrder() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderMapper.toDto(order)).thenReturn(orderDto);

        OrderDto result = orderService.getOrderById(1L);

        assertNotNull(result);
        assertEquals("John Doe", result.getCustomerName());
        verify(orderRepository).findById(1L);
        verify(orderMapper).toDto(order);
    }

    @Test
    void getOrderById_WhenNotExists_ThrowsException() {
        when(orderRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> orderService.getOrderById(999L));
        verify(orderRepository).findById(999L);
    }

    @Test
    void createOrder_WithValidRequest_ReturnsCreatedOrder() {
        when(orderMapper.toEntity(createRequest)).thenReturn(order);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderMapper.toDto(order)).thenReturn(orderDto);

        OrderDto result = orderService.createOrder(createRequest);

        assertNotNull(result);
        assertEquals("John Doe", result.getCustomerName());
        verify(orderMapper).toEntity(createRequest);
        verify(orderRepository).save(any(Order.class));
        verify(orderMapper).toDto(order);
    }

    @Test
    void updateOrder_WhenExists_ReturnsUpdatedOrder() {
        UpdateOrderRequest updateRequest = new UpdateOrderRequest();
        updateRequest.setCustomerName("Jane Doe");
        updateRequest.setStatus("PROCESSING");

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);
        when(orderMapper.toDto(order)).thenReturn(orderDto);

        OrderDto result = orderService.updateOrder(1L, updateRequest);

        assertNotNull(result);
        assertEquals("Jane Doe", order.getCustomerName());
        assertEquals(Order.OrderStatus.PROCESSING, order.getStatus());
        verify(orderRepository).findById(1L);
        verify(orderRepository).save(order);
    }

    @Test
    void updateOrder_WhenNotExists_ThrowsException() {
        UpdateOrderRequest updateRequest = new UpdateOrderRequest();
        when(orderRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> orderService.updateOrder(999L, updateRequest));
        verify(orderRepository).findById(999L);
    }

    @Test
    void deleteOrder_WhenExists_DeletesSuccessfully() {
        when(orderRepository.existsById(1L)).thenReturn(true);
        doNothing().when(orderRepository).deleteById(1L);

        orderService.deleteOrder(1L);

        verify(orderRepository).existsById(1L);
        verify(orderRepository).deleteById(1L);
    }

    @Test
    void deleteOrder_WhenNotExists_ThrowsException() {
        when(orderRepository.existsById(999L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> orderService.deleteOrder(999L));
        verify(orderRepository).existsById(999L);
        verify(orderRepository, never()).deleteById(999L);
    }

    @Test
    void getOrdersByCustomer_ReturnsOrderList() {
        List<Order> orders = List.of(order);
        when(orderRepository.findByCustomerName("John Doe")).thenReturn(orders);
        when(orderMapper.toDtoList(orders)).thenReturn(List.of(orderDto));

        List<OrderDto> result = orderService.getOrdersByCustomer("John Doe");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(orderRepository).findByCustomerName("John Doe");
    }

    @Test
    void getOrdersByStatus_WithValidStatus_ReturnsOrderList() {
        List<Order> orders = List.of(order);
        when(orderRepository.findByStatus(Order.OrderStatus.PENDING)).thenReturn(orders);
        when(orderMapper.toDtoList(orders)).thenReturn(List.of(orderDto));

        List<OrderDto> result = orderService.getOrdersByStatus("PENDING");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(orderRepository).findByStatus(Order.OrderStatus.PENDING);
    }

    @Test
    void getOrdersByStatus_WithInvalidStatus_ThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> orderService.getOrdersByStatus("INVALID_STATUS"));
    }
}
