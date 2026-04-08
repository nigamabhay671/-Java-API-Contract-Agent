# ☕ Java API Contract Agent

**Specialized Spring Boot REST API Generator from OpenAPI Contracts**

A dedicated AI agent system that generates complete, production-ready **Spring Boot** REST APIs from OpenAPI/Swagger specifications following Java best practices and Spring Boot conventions.

---

## ✨ Features

- ☕ **Java 17/21** - Modern Java LTS versions
- 🍃 **Spring Boot 3.2.x** - Latest stable framework
- 🏗️ **Layered Architecture** - Controller, Service, Repository, Entity, DTO
- 📦 **Maven/Gradle** - Choose your build tool
- 🔧 **Lombok** - Reduce boilerplate code
- 🗺️ **MapStruct** - Type-safe bean mapping
- ✔️ **Bean Validation** - Hibernate Validator with annotations
- 🛡️ **Global Exception Handling** - @RestControllerAdvice
- 📝 **SLF4J Logging** - Structured logging with Logback
- 📚 **SpringDoc OpenAPI** - Swagger UI integration
- 💾 **Spring Data JPA** - Database access with repositories
- 🗄️ **H2 Database** - In-memory database for demo
- 🧪 **JUnit 5 + Mockito** - Comprehensive unit tests
- 📊 **Spring Boot Actuator** - Health checks and metrics
- 🌐 **RESTful API** - Proper HTTP methods and status codes

---

## 🚀 Quick Start

### Prerequisites

- **Java 17+** ([Download](https://adoptium.net/))
- **Maven 3.9+** or **Gradle 8+**
- IDE (IntelliJ IDEA, Eclipse, VS Code recommended)

### Installation

1. **Navigate to the agent folder:**
   ```bash
   cd java-api-agent
   ```

2. **Make script executable (Linux/Mac):**
   ```bash
   chmod +x generate-java-api.sh
   ```

### Generate Your First API

**Windows (PowerShell):**
```powershell
.\generate-java-api.ps1 -Interactive
```

**Linux/Mac (Bash):**
```bash
./generate-java-api.sh -i
```

Follow the prompts, then paste the generated prompt to your AI assistant!

---

## 📋 Command Line Usage

### Basic Generation

**Windows:**
```powershell
.\generate-java-api.ps1 `
  -ContractPath ".\examples\order-management-api.yaml" `
  -ProjectName "Order Management API"
```

**Linux/Mac:**
```bash
./generate-java-api.sh \
  -c ./examples/order-management-api.yaml \
  -p "Order Management API"
```

### Advanced Options

**With Custom Group and Artifact:**
```powershell
.\generate-java-api.ps1 `
  -ContractPath ".\my-api.yaml" `
  -ProjectName "Product API" `
  -GroupId "com.mycompany" `
  -ArtifactId "product-api" `
  -JavaVersion "17" `
  -BuildTool "Maven"
```

### Using Gradle

```bash
./generate-java-api.sh \
  -c ./api.yaml \
  -p "Inventory API" \
  -b Gradle
```

---

## 📂 Generated Project Structure

```
product-api/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/productapi/
│   │   │       ├── ProductApiApplication.java
│   │   │       ├── controller/
│   │   │       │   └── ProductController.java
│   │   │       ├── service/
│   │   │       │   ├── ProductService.java
│   │   │       │   └── impl/
│   │   │       │       └── ProductServiceImpl.java
│   │   │       ├── repository/
│   │   │       │   └── ProductRepository.java
│   │   │       ├── entity/
│   │   │       │   └── Product.java
│   │   │       ├── dto/
│   │   │       │   ├── ProductDto.java
│   │   │       │   ├── CreateProductRequest.java
│   │   │       │   └── UpdateProductRequest.java
│   │   │       ├── mapper/
│   │   │       │   └── ProductMapper.java
│   │   │       ├── exception/
│   │   │       │   ├── ResourceNotFoundException.java
│   │   │       │   ├── GlobalExceptionHandler.java
│   │   │       │   └── ErrorResponse.java
│   │   │       └── config/
│   │   │           ├── OpenApiConfig.java
│   │   │           └── AppConfig.java
│   │   └── resources/
│   │       ├── application.yml
│   │       ├── application-dev.yml
│   │       └── data.sql
│   └── test/
│       └── java/
│           └── com/example/productapi/
│               ├── controller/
│               │   └── ProductControllerTest.java
│               └── service/
│                   └── ProductServiceImplTest.java
├── pom.xml
├── .gitignore
├── README.md
└── RUN_INSTRUCTIONS.md
```

---

## 🎯 What Gets Generated

### **Source Code**

#### 1. **Main Application Class**
```java
@SpringBootApplication
public class ProductApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductApiApplication.class, args);
    }
}
```

#### 2. **REST Controllers**
```java
@RestController
@RequestMapping("/api/products")
@Validated
@Slf4j
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() { }
    
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody CreateProductRequest request) { }
}
```

#### 3. **Service Layer**
```java
public interface ProductService {
    List<ProductDto> getAllProducts();
    ProductDto createProduct(CreateProductRequest request);
}

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    // Implementation...
}
```

#### 4. **JPA Repositories**
```java
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(String category);
}
```

#### 5. **JPA Entities**
```java
@Entity
@Table(name = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    private BigDecimal price;
}
```

#### 6. **DTOs with Validation**
```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {
    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 100)
    private String name;
    
    @NotNull
    @DecimalMin("0.01")
    private BigDecimal price;
}
```

#### 7. **MapStruct Mappers**
```java
@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDto(Product entity);
    Product toEntity(CreateProductRequest request);
}
```

#### 8. **Global Exception Handler**
```java
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        // Handle exception...
    }
}
```

### **Configuration**

#### pom.xml
- Spring Boot Parent
- All required dependencies
- Maven plugins configured
- Java version set

#### application.yml
- Server configuration
- Database settings
- Logging configuration
- SpringDoc settings
- Profile-specific configs

### **Tests**

#### Controller Tests (MockMvc)
```java
@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private ProductService productService;
    
    @Test
    void createProduct_ReturnsCreated() throws Exception {
        // Test implementation...
    }
}
```

#### Service Tests (Mockito)
```java
@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;
    
    @InjectMocks
    private ProductServiceImpl productService;
    
    @Test
    void getAllProducts_ReturnsProductList() {
        // Test implementation...
    }
}
```

---

## 🔧 Command Line Options

### PowerShell (Windows)

| Parameter | Required | Default | Description |
|-----------|----------|---------|-------------|
| `-ContractPath` | Yes | - | Path to OpenAPI contract file |
| `-ProjectName` | Yes | - | Project name (e.g., "Order API") |
| `-GroupId` | No | `com.example` | Maven group ID |
| `-ArtifactId` | No | Auto-generated | Maven artifact ID |
| `-JavaVersion` | No | `17` | Java version (17 or 21) |
| `-SpringBootVersion` | No | `3.2.0` | Spring Boot version |
| `-BuildTool` | No | `Maven` | Maven or Gradle |
| `-OutputPath` | No | `./generated-api` | Output directory |
| `-Interactive` | No | - | Run in interactive mode |
| `-Help` | No | - | Show help message |

### Bash (Linux/Mac)

| Short | Long | Required | Description |
|-------|------|----------|-------------|
| `-c` | `--contract` | Yes | Path to OpenAPI contract |
| `-p` | `--project` | Yes | Project name |
| `-g` | `--group` | No | Maven group ID |
| `-a` | `--artifact` | No | Maven artifact ID |
| `-j` | `--java` | No | Java version |
| `-s` | `--spring` | No | Spring Boot version |
| `-b` | `--build` | No | Build tool |
| `-o` | `--output` | No | Output directory |
| `-i` | `--interactive` | No | Interactive mode |
| `-h` | `--help` | No | Show help |

---

## 🛠️ Build and Run

After generation:

### Maven
```bash
cd generated-api

# Build the project
mvn clean install

# Run tests
mvn test

# Run the application
mvn spring-boot:run
```

### Gradle
```bash
cd generated-api

# Build the project
./gradlew build

# Run tests
./gradlew test

# Run the application
./gradlew bootRun
```

### Access the API

- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **API Docs**: `http://localhost:8080/api-docs`
- **H2 Console**: `http://localhost:8080/h2-console`
- **Actuator Health**: `http://localhost:8080/actuator/health`

---

## 📚 Technologies Used

| Category | Technology | Version |
|----------|-----------|---------|
| Language | Java | 17 / 21 |
| Framework | Spring Boot | 3.2.x |
| Build Tool | Maven / Gradle | Latest |
| Database | H2 (in-memory) | Latest |
| ORM | Spring Data JPA | 3.2.x |
| Validation | Hibernate Validator | 8.x |
| Mapping | MapStruct | 1.5.x |
| Boilerplate | Lombok | 1.18.x |
| Documentation | SpringDoc OpenAPI | 2.3.x |
| Testing | JUnit 5 | 5.10.x |
| Mocking | Mockito | 5.x |
| Logging | SLF4J + Logback | Latest |

---

## 🎨 Java Best Practices

Generated code follows:

✅ **Naming Conventions**
- Classes: PascalCase
- Methods/Variables: camelCase
- Constants: UPPER_SNAKE_CASE
- Packages: lowercase

✅ **Design Patterns**
- Repository Pattern
- Service Pattern
- DTO Pattern
- Builder Pattern
- Factory Pattern

✅ **Spring Boot Best Practices**
- Constructor injection (not field injection)
- Return ResponseEntity<T> from controllers
- Use @Validated on controllers
- Global exception handling
- Profile-based configuration

✅ **Code Quality**
- Lombok to reduce boilerplate
- MapStruct for type-safe mapping
- Comprehensive Javadoc
- Proper exception handling
- Structured logging

---

## 📖 Examples

See the `examples/` folder for sample OpenAPI contracts:
- `order-management-api.yaml` - Complete order management system

---

## 🔄 Workflow

1. **Prepare** your OpenAPI contract
2. **Run** the agent script
3. **Copy** the generated prompt
4. **Paste** to AI assistant (Claude Code)
5. **Build** the generated project
6. **Test** with Swagger UI
7. **Customize** as needed
8. **Deploy** to production

---

## 🆘 Troubleshooting

### Java Version Issues
```bash
# Check Java version
java -version

# Should be 17 or later
# Download from: https://adoptium.net/
```

### Maven Issues
```bash
# Clean build
mvn clean

# Update dependencies
mvn dependency:purge-local-repository

# Rebuild
mvn install
```

### Port Already in Use
Edit `application.yml`:
```yaml
server:
  port: 8081  # Change port
```

---

## 💡 Tips

1. **Validate contracts** at [editor.swagger.io](https://editor.swagger.io/)
2. **Use meaningful operation IDs** - they become method names
3. **Add descriptions** - they become Javadoc
4. **Review generated code** before production use
5. **Replace H2** with PostgreSQL/MySQL for production
6. **Add security** (Spring Security) before deployment

---

## 📄 License

This agent template is provided for automated Spring Boot API generation.

---

**Start generating production-ready Spring Boot APIs today! ☕🚀**
