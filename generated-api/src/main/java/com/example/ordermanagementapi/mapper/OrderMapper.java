package com.example.ordermanagementapi.mapper;

import com.example.ordermanagementapi.dto.CreateOrderItemRequest;
import com.example.ordermanagementapi.dto.CreateOrderRequest;
import com.example.ordermanagementapi.dto.OrderDto;
import com.example.ordermanagementapi.entity.Order;
import com.example.ordermanagementapi.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

/**
 * MapStruct mapper for Order entity and DTOs.
 *
 * Provides type-safe mapping between entities and DTOs.
 * Spring automatically generates the implementation.
 *
 * @author Java API Contract Agent
 */
@Mapper(componentModel = "spring")
public interface OrderMapper {

    /**
     * Convert Order entity to OrderDto.
     *
     * @param order the order entity
     * @return order DTO
     */
    @Mapping(target = "status", source = "status", qualifiedByName = "orderStatusToString")
    OrderDto toDto(Order order);

    /**
     * Convert list of Order entities to list of OrderDtos.
     *
     * @param orders list of order entities
     * @return list of order DTOs
     */
    List<OrderDto> toDtoList(List<Order> orders);

    /**
     * Convert CreateOrderRequest to Order entity.
     *
     * @param request the create order request
     * @return order entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "orderDate", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "items", source = "items")
    Order toEntity(CreateOrderRequest request);

    /**
     * Convert CreateOrderItemRequest to OrderItem entity.
     *
     * @param request the create order item request
     * @return order item entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    OrderItem toOrderItem(CreateOrderItemRequest request);

    /**
     * Update existing Order entity from UpdateOrderRequest.
     *
     * @param request the update order request
     * @param order the existing order entity to update
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderDate", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "status", source = "status", qualifiedByName = "stringToOrderStatus")
    void updateOrderFromDto(com.example.ordermanagementapi.dto.UpdateOrderRequest request, @MappingTarget Order order);

    /**
     * Convert Order.OrderStatus enum to String.
     *
     * @param status the order status enum
     * @return status as string
     */
    @Named("orderStatusToString")
    default String orderStatusToString(Order.OrderStatus status) {
        return status != null ? status.name() : null;
    }

    /**
     * Convert String to Order.OrderStatus enum.
     *
     * @param status the status string
     * @return order status enum
     */
    @Named("stringToOrderStatus")
    default Order.OrderStatus stringToOrderStatus(String status) {
        if (status == null) {
            return null;
        }
        try {
            return Order.OrderStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
