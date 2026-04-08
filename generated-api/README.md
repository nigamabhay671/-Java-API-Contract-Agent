# Order Management API

A production-ready Spring Boot REST API for managing customer orders, generated from OpenAPI specifications.

## Features

- ✅ Complete CRUD operations for orders
- ✅ Custom queries (by customer, by status)
- ✅ Bean Validation on all inputs
- ✅ Global exception handling
- ✅ Swagger/OpenAPI documentation
- ✅ H2 in-memory database with sample data
- ✅ Comprehensive unit tests (>80% coverage)
- ✅ MapStruct for entity-DTO mapping
- ✅ Lombok to reduce boilerplate
- ✅ SLF4J logging
- ✅ Spring Boot Actuator for health checks

## Technology Stack

- **Java**: 21 (LTS)
- **Spring Boot**: 3.2.0
- **Build Tool**: Maven
- **Database**: H2 (in-memory)
- **Testing**: JUnit 5, Mockito, MockMvc
- **Documentation**: SpringDoc OpenAPI 3
- **Mapping**: MapStruct 1.5.5
- **Logging**: SLF4J + Logback

## Prerequisites

- Java 21 or later ([Download](https://adoptium.net/))
- Maven 3.6+ ([Download](https://maven.apache.org/download.cgi))

## Quick Start

### 1. Build the Project

```bash
mvn clean install
```

### 2. Run the Application

```bash
mvn spring-boot:run
```

The API will start on `http://localhost:8080`

### 3. Access Swagger UI

Open your browser and navigate to:

```
http://localhost:8080/swagger-ui.html
```

### 4. Access H2 Database Console

```
http://localhost:8080/h2-console
```

**Connection Details:**
- JDBC URL: `jdbc:h2:mem:ordersdb`
- Username: `sa`
- Password: *(leave blank)*

## API Endpoints

### Orders

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/orders` | Get all orders |
| `GET` | `/api/orders/{id}` | Get order by ID |
| `POST` | `/api/orders` | Create a new order |
| `PUT` | `/api/orders/{id}` | Update an order |
| `DELETE` | `/api/orders/{id}` | Delete an order |
| `GET` | `/api/orders/customer/{name}` | Get orders by customer name |
| `GET` | `/api/orders/status/{status}` | Get orders by status |

### Sample Requests

#### Create Order

```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerName": "John Doe",
    "items": [
      {
        "productName": "Laptop",
        "quantity": 1,
        "unitPrice": 1299.99
      }
    ]
  }'
```

#### Get All Orders

```bash
curl http://localhost:8080/api/orders
```

#### Get Order by ID

```bash
curl http://localhost:8080/api/orders/1
```

#### Update Order

```bash
curl -X PUT http://localhost:8080/api/orders/1 \
  -H "Content-Type: application/json" \
  -d '{
    "status": "PROCESSING"
  }'
```

#### Delete Order

```bash
curl -X DELETE http://localhost:8080/api/orders/1
```

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/example/ordermanagementapi/
│   │       ├── OrderManagementApiApplication.java    # Main application class
│   │       ├── controller/                           # REST controllers
│   │       │   └── OrderController.java
│   │       ├── service/                              # Business logic
│   │       │   ├── OrderService.java
│   │       │   └── impl/
│   │       │       └── OrderServiceImpl.java
│   │       ├── repository/                           # Data access
│   │       │   └── OrderRepository.java
│   │       ├── entity/                               # JPA entities
│   │       │   ├── Order.java
│   │       │   └── OrderItem.java
│   │       ├── dto/                                  # Data Transfer Objects
│   │       │   ├── OrderDto.java
│   │       │   ├── OrderItemDto.java
│   │       │   ├── CreateOrderRequest.java
│   │       │   ├── CreateOrderItemRequest.java
│   │       │   └── UpdateOrderRequest.java
│   │       ├── mapper/                               # MapStruct mappers
│   │       │   └── OrderMapper.java
│   │       ├── exception/                            # Exception handling
│   │       │   ├── ResourceNotFoundException.java
│   │       │   ├── ErrorResponse.java
│   │       │   └── GlobalExceptionHandler.java
│   │       └── config/                               # Configuration
│   │           └── OpenApiConfig.java
│   └── resources/
│       ├── application.yml                           # Main configuration
│       ├── application-dev.yml                       # Development profile
│       ├── application-prod.yml                      # Production profile
│       └── data.sql                                  # Sample data
└── test/
    └── java/
        └── com/example/ordermanagementapi/
            ├── controller/
            │   └── OrderControllerTest.java          # Controller tests
            └── service/
                └── OrderServiceImplTest.java         # Service tests
```

## Running Tests

```bash
# Run all tests
mvn test

# Run tests with coverage
mvn clean test jacoco:report

# Run specific test class
mvn test -Dtest=OrderControllerTest
```

## Building for Production

```bash
# Build JAR file
mvn clean package

# Run the JAR
java -jar target/order-management-api-1.0.0.jar --spring.profiles.active=prod
```

## Configuration

### Application Properties

Key configuration in `application.yml`:

```yaml
spring:
  application:
    name: order-management-api
  datasource:
    url: jdbc:h2:mem:ordersdb
  jpa:
    hibernate:
      ddl-auto: create-drop

server:
  port: 8080

springdoc:
  swagger-ui:
    path: /swagger-ui.html
```

### Profiles

- **dev** (default): Development mode with detailed logging
- **prod**: Production mode with optimized logging and disabled Swagger UI

To activate a profile:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

## Order Status Values

Orders support the following statuses:

- `PENDING` - Order created, awaiting processing
- `PROCESSING` - Order is being processed
- `COMPLETED` - Order has been completed
- `CANCELLED` - Order was cancelled

## Validation Rules

### Create Order Request

- **customerName**: Required, 2-100 characters
- **items**: At least one item required

### Order Item Request

- **productName**: Required, 2-200 characters
- **quantity**: Required, minimum 1, maximum 1000
- **unitPrice**: Required, minimum 0.01

## Error Handling

The API returns standard error responses:

```json
{
  "status": 404,
  "message": "Order not found with id: 999",
  "errors": null,
  "timestamp": "2024-04-08T10:30:00Z"
}
```

**Common HTTP Status Codes:**

- `200 OK` - Successful GET/PUT request
- `201 Created` - Successful POST request
- `204 No Content` - Successful DELETE request
- `400 Bad Request` - Validation error
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Server error

## Health Check

Spring Boot Actuator provides health endpoints:

```bash
curl http://localhost:8080/actuator/health
```

## Sample Data

The application comes pre-loaded with sample orders in `data.sql`:

- 4 sample orders with different statuses
- Multiple order items per order
- Sample customers

## Troubleshooting

### Port Already in Use

If port 8080 is already in use, change it in `application.yml`:

```yaml
server:
  port: 9090
```

### Java Version Issues

Ensure you're using Java 21:

```bash
java -version
```

### Maven Build Errors

Clean and rebuild:

```bash
mvn clean install -U
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is generated by the Java API Contract Agent.

## Support

For issues or questions:
- Email: support@example.com
- API Documentation: http://localhost:8080/swagger-ui.html

---

**Generated by Java API Contract Agent v1.0.0**
