package com.example.ordermanagementapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Main application class for Order Management API.
 *
 * This Spring Boot application provides REST APIs for managing customer orders.
 * It includes comprehensive order management functionality with CRUD operations,
 * custom queries, and proper validation.
 *
 * @author Java API Contract Agent
 * @version 1.0.0
 */
@SpringBootApplication
@EnableJpaAuditing
public class OrderManagementApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderManagementApiApplication.class, args);
    }
}
