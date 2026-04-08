package com.example.ordermanagementapi.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Request DTO for updating an existing order.
 *
 * All fields are optional to allow partial updates.
 *
 * @author Java API Contract Agent
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderRequest {

    @Size(min = 2, max = 100, message = "Customer name must be between 2 and 100 characters")
    private String customerName;

    private String status;

    @Valid
    private List<CreateOrderItemRequest> items;
}
