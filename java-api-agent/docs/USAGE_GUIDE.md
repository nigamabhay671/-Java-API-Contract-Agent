# 📖 Java API Agent - Complete Usage Guide

Comprehensive guide for using the Java API Contract Agent to generate Spring Boot REST APIs.

---

## Table of Contents

1. [Prerequisites](#prerequisites)
2. [Quick Start](#quick-start)
3. [Command Reference](#command-reference)
4. [Configuration](#configuration)
5. [Examples](#examples)
6. [Generated Code Overview](#generated-code-overview)
7. [Testing](#testing)
8. [Customization](#customization)
9. [Troubleshooting](#troubleshooting)
10. [Best Practices](#best-practices)

---

## Prerequisites

### Required Software

1. **Java Development Kit (JDK)**
   - Version: 17 or 21 (LTS versions)
   - Download: [Eclipse Temurin](https://adoptium.net/)
   - Verify: `java -version`

2. **Maven** (if using Maven)
   - Version: 3.9.x or later
   - Download: [Maven](https://maven.apache.org/download.cgi)
   - Verify: `mvn -version`

3. **Gradle** (if using Gradle)
   - Version: 8.5 or later
   - Download: [Gradle](https://gradle.org/install/)
   - Verify: `gradle -version`

4. **IDE** (Optional but recommended)
   - IntelliJ IDEA Community/Ultimate
   - Eclipse IDE for Enterprise Java
   - Visual Studio Code with Java extensions

### Knowledge Prerequisites

- Basic understanding of REST APIs
- Familiarity with OpenAPI/Swagger specifications
- Basic Java programming knowledge
- Understanding of Spring Boot (helpful but not required)

---

## Quick Start

See [QUICKSTART.md](../QUICKSTART.md) for a 5-minute guide.

---

## Command Reference

### Interactive Mode

**Windows (PowerShell):**
```powershell
.\generate-java-api.ps1 -Interactive
```

**Linux/Mac (Bash):**
```bash
./generate-java-api.sh -i
```

The agent will guide you through all options.

### Non-Interactive Mode

**Windows:**
```powershell
.\generate-java-api.ps1 `
  -ContractPath "path\to\contract.yaml" `
  -ProjectName "My API" `
  -GroupId "com.mycompany" `
  -JavaVersion "17" `
  -BuildTool "Maven"
```

**Linux/Mac:**
```bash
./generate-java-api.sh \
  -c path/to/contract.yaml \
  -p "My API" \
  -g com.mycompany \
  -j 17 \
  -b Maven
```

### All Parameters

#### PowerShell Parameters

| Parameter | Type | Required | Default | Description |
|-----------|------|----------|---------|-------------|
| `-ContractPath` | String | Yes | - | Path to OpenAPI YAML/JSON file |
| `-ProjectName` | String | Yes | - | Human-readable project name |
| `-GroupId` | String | No | `com.example` | Maven/Gradle group ID |
| `-ArtifactId` | String | No | Auto-generated | Maven/Gradle artifact ID |
| `-JavaVersion` | String | No | `17` | Java version (17 or 21) |
| `-SpringBootVersion` | String | No | `3.2.0` | Spring Boot version |
| `-BuildTool` | String | No | `Maven` | Build tool (Maven or Gradle) |
| `-OutputPath` | String | No | `./generated-api` | Output directory path |
| `-IncludeLombok` | Switch | No | `true` | Include Lombok dependency |
| `-IncludeMapStruct` | Switch | No | `true` | Include MapStruct dependency |
| `-Interactive` | Switch | No | `false` | Run in interactive mode |
| `-Help` | Switch | No | `false` | Display help message |

#### Bash Parameters

| Short | Long | Required | Description |
|-------|------|----------|-------------|
| `-c` | `--contract` | Yes | Path to OpenAPI contract file |
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

## Configuration

### Agent Configuration

Edit `config/java-agent-config.json` to customize defaults:

```json
{
  "defaultSettings": {
    "javaVersion": "17",
    "springBootVersion": "3.2.0",
    "buildTool": "Maven",
    "packageStructure": "com.example",
    "includeTests": true,
    "includeLombok": true,
    "includeMapStruct": true
  }
}
```

### Project Naming

**Group ID Format:**
- Use reverse domain notation
- Example: `com.mycompany.division`
- All lowercase, no special characters

**Artifact ID Format:**
- Kebab-case (lowercase with hyphens)
- Example: `order-management-api`
- Auto-generated from project name if not provided

**Package Name:**
- Generated from Group ID + Artifact ID
- Example: `com.mycompany.ordermanagementapi`

---

## Examples

### Example 1: Basic Order API

```powershell
.\generate-java-api.ps1 `
  -ContractPath "examples\order-management-api.yaml" `
  -ProjectName "Order Management API"
```

**Result:**
- Group ID: `com.example`
- Artifact ID: `order-management-api`
- Package: `com.example.ordermanagementapi`
- Java: 17
- Build Tool: Maven

### Example 2: Custom Configuration

```bash
./generate-java-api.sh \
  -c /path/to/product-api.yaml \
  -p "Product Catalog API" \
  -g com.retailcompany \
  -a product-catalog-api \
  -j 21 \
  -b Gradle \
  -o /projects/apis/product-api
```

**Result:**
- Custom group and artifact IDs
- Java 21
- Gradle build
- Custom output path

### Example 3: Multiple APIs

Generate multiple APIs in batch:

**PowerShell:**
```powershell
$apis = @(
    @{Name="Order API"; Contract="order-api.yaml"},
    @{Name="Product API"; Contract="product-api.yaml"},
    @{Name="Customer API"; Contract="customer-api.yaml"}
)

foreach ($api in $apis) {
    .\generate-java-api.ps1 `
        -ContractPath $api.Contract `
        -ProjectName $api.Name `
        -OutputPath "./apis/$($api.Name)"
}
```

---

## Generated Code Overview

### Project Structure

```
my-api/
├── src/
│   ├── main/
│   │   ├── java/com/example/myapi/
│   │   │   ├── MyApiApplication.java       # Main class
│   │   │   ├── controller/                  # REST endpoints
│   │   │   ├── service/                     # Business logic interfaces
│   │   │   │   └── impl/                    # Service implementations
│   │   │   ├── repository/                  # Spring Data JPA repositories
│   │   │   ├── entity/                      # JPA entities
│   │   │   ├── dto/                         # DTOs (Request/Response)
│   │   │   ├── mapper/                      # MapStruct mappers
│   │   │   ├── exception/                   # Custom exceptions
│   │   │   └── config/                      # Configuration classes
│   │   └── resources/
│   │       ├── application.yml              # Main configuration
│   │       ├── application-dev.yml          # Dev profile
│   │       ├── application-prod.yml         # Prod profile
│   │       └── data.sql                     # Sample data
│   └── test/
│       └── java/com/example/myapi/
│           ├── controller/                  # Controller tests
│           └── service/                     # Service tests
├── pom.xml                                  # Maven configuration
├── .gitignore                               # Git ignore rules
├── README.md                                # Project documentation
└── RUN_INSTRUCTIONS.md                      # How to run
```

### Key Components

**1. Controllers** - Handle HTTP requests
```java
@RestController
@RequestMapping("/api/resources")
public class ResourceController {
    @GetMapping
    public ResponseEntity<List<ResourceDto>> getAll() { }
}
```

**2. Services** - Business logic
```java
@Service
public class ResourceServiceImpl implements ResourceService {
    // Business logic implementation
}
```

**3. Repositories** - Data access
```java
@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    // Auto-generated CRUD operations
}
```

**4. Entities** - Database models
```java
@Entity
@Table(name = "resources")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
```

**5. DTOs** - Request/Response objects
```java
public class CreateResourceRequest {
    @NotBlank
    private String name;
}
```

**6. Mappers** - Entity-DTO conversion
```java
@Mapper(componentModel = "spring")
public interface ResourceMapper {
    ResourceDto toDto(Resource entity);
}
```

---

## Testing

### Running Tests

**Maven:**
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=OrderControllerTest

# Run with coverage
mvn test jacoco:report
```

**Gradle:**
```bash
# Run all tests
./gradlew test

# Run specific test
./gradlew test --tests OrderControllerTest

# Run with coverage
./gradlew test jacocoTestReport
```

### Test Structure

**Controller Tests:**
```java
@WebMvcTest(OrderController.class)
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private OrderService orderService;
    
    @Test
    void testGetAllOrders() throws Exception {
        mockMvc.perform(get("/api/orders"))
            .andExpect(status().isOk());
    }
}
```

**Service Tests:**
```java
@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;
    
    @InjectMocks
    private OrderServiceImpl orderService;
    
    @Test
    void testCreateOrder() {
        // Test implementation
    }
}
```

---

## Customization

### Adding Security

1. Add Spring Security dependency to `pom.xml`
2. Create `SecurityConfig.java`
3. Configure authentication/authorization

### Database Migration

#### Switch to PostgreSQL:

**pom.xml:**
```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
</dependency>
```

**application.yml:**
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/mydb
    username: dbuser
    password: dbpass
  jpa:
    hibernate:
      ddl-auto: validate
```

#### Switch to MySQL:

**pom.xml:**
```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
</dependency>
```

### Adding Authentication

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/public/**").permitAll()
                .anyRequest().authenticated()
            )
            .httpBasic();
        return http.build();
    }
}
```

---

## Troubleshooting

### Common Issues

#### Issue 1: Java Version Mismatch

**Symptom:**
```
Error: A JNI error has occurred
```

**Solution:**
```bash
# Check Java version
java -version

# Should be 17 or later
# Update JAVA_HOME if needed
```

#### Issue 2: Maven Build Fails

**Symptom:**
```
[ERROR] Failed to execute goal on project
```

**Solution:**
```bash
# Clean and rebuild
mvn clean install -U

# Clear local repository if needed
rm -rf ~/.m2/repository/*
```

#### Issue 3: Port Already in Use

**Symptom:**
```
Port 8080 is already in use
```

**Solution:**
Edit `application.yml`:
```yaml
server:
  port: 8081
```

#### Issue 4: Lombok Not Working

**Solution:**
- Enable annotation processing in IDE
- IntelliJ: Settings → Build → Compiler → Annotation Processors
- Eclipse: Install Lombok plugin

---

## Best Practices

### Contract Design

1. **Use meaningful operation IDs** - They become method names
2. **Add descriptions** - They become Javadoc comments
3. **Define schemas clearly** - Better models = better code
4. **Use validation constraints** - min, max, pattern, etc.
5. **Group related endpoints** - Use tags

### Code Quality

1. **Review generated code** - Understand what was generated
2. **Run tests immediately** - Catch issues early
3. **Check test coverage** - Aim for >80%
4. **Follow naming conventions** - Stay consistent
5. **Add business logic** - Customize service implementations

### Production Readiness

1. **Replace H2 with production database**
2. **Add authentication/authorization**
3. **Configure proper logging**
4. **Set up monitoring** (Actuator + Prometheus)
5. **Use environment-specific profiles**
6. **Add API versioning**
7. **Implement rate limiting**
8. **Add caching** where appropriate

---

## Additional Resources

- **Spring Boot Documentation**: [spring.io/projects/spring-boot](https://spring.io/projects/spring-boot)
- **OpenAPI Specification**: [swagger.io/specification](https://swagger.io/specification/)
- **MapStruct**: [mapstruct.org](https://mapstruct.org/)
- **Lombok**: [projectlombok.org](https://projectlombok.org/)

---

**Need more help? Check README.md or review the example contracts!**
