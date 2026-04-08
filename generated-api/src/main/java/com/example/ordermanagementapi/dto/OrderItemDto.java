package com.example.ordermanagementapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Data Transfer Object for Order Item responses.
 *
 * @author Java API Contract Agent
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {

    private Long id;

    private String productName;

    private Integer quantity;

    private BigDecimal unitPrice;
}
