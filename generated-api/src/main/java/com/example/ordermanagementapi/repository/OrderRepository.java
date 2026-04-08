package com.example.ordermanagementapi.repository;

import com.example.ordermanagementapi.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Order entity.
 *
 * Provides CRUD operations and custom queries for Order management.
 * Spring Data JPA automatically generates the implementation.
 *
 * @author Java API Contract Agent
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Find all orders for a specific customer.
     *
     * @param customerName the customer name to search for
     * @return list of orders for the customer
     */
    List<Order> findByCustomerName(String customerName);

    /**
     * Find all orders with a specific status.
     *
     * @param status the order status to filter by
     * @return list of orders with the given status
     */
    List<Order> findByStatus(Order.OrderStatus status);

    /**
     * Find orders by customer name containing the search string (case-insensitive).
     *
     * @param customerName the customer name search string
     * @return list of matching orders
     */
    List<Order> findByCustomerNameContainingIgnoreCase(String customerName);

    /**
     * Find orders with total amount greater than the specified value.
     *
     * @param amount the minimum total amount
     * @return list of orders with total amount greater than specified
     */
    @Query("SELECT o FROM Order o WHERE o.totalAmount > :amount")
    List<Order> findOrdersWithTotalAmountGreaterThan(@Param("amount") java.math.BigDecimal amount);
}
