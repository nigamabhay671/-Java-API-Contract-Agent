# ☕ Java API Contract Agent

**Specialized Spring Boot REST API Generator from OpenAPI Contracts**

A dedicated AI agent system that generates complete, production-ready **Spring Boot** REST APIs from OpenAPI/Swagger specifications following Java best practices and Spring Boot conventions.

[![Java](https://img.shields.io/badge/Java-21%20|%2017-orange.svg)](https://adoptium.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

---

## ✨ Features

- ☕ **Java 21/17** - Modern Java LTS versions (21 recommended)
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

## 📁 Project Structure

```
Java-API-Contract-Agent/
├── bin/                    # Executable scripts
│   ├── generate-java-api.ps1
│   └── generate-java-api.sh
├── templates/              # AI prompt templates
├── examples/               # Sample OpenAPI contracts
├── config/                 # Configuration files
├── generated/              # Generated projects output
├── docs/                   # Documentation
├── .archive/               # Backup files
├── README.md               # This file
└── PROJECT_STRUCTURE.md    # Detailed structure docs
```

See [PROJECT_STRUCTURE.md](PROJECT_STRUCTURE.md) for detailed folder descriptions.

---

## 🚀 Quick Start

### Prerequisites

- **Java 17+** ([Download](https://adoptium.net/))
- **Maven 3.9+** ([Download](https://maven.apache.org/download.cgi))
- IDE (IntelliJ IDEA, Eclipse, VS Code recommended)

### Generate Your First API

#### **Option 1: Interactive Mode (Easiest)**

```powershell
# Windows
.\bin\generate-java-api.ps1 -Interactive

# Linux/Mac
./bin/generate-java-api.sh --interactive
```

#### **Option 2: Command Line**

```powershell
# Windows
.\bin\generate-java-api.ps1 `
  -ContractPath ".\examples\order-management-api.yaml" `
  -ProjectName "Order Management API"

# Linux/Mac
./bin/generate-java-api.sh \
  --contract ./examples/order-management-api.yaml \
  --project "Order Management API"
```

#### **Option 3: With Full Options**

```powershell
.\bin\generate-java-api.ps1 `
  -ContractPath ".\examples\order-management-api.yaml" `
  -ProjectName "Order Management API" `
  -GroupId "com.mycompany" `
  -ArtifactId "order-api" `
  -JavaVersion "21" `
  -BuildTool "Maven" `
  -OutputPath ".\generated\order-api"
```

### What Happens Next?

1. **Script generates a prompt file**: `generated/last-java-generation-prompt.txt`
2. **Feed prompt to AI** (Claude Code, ChatGPT, etc.)
3. **AI generates complete Spring Boot project** in `generated/your-project-name/`
4. **Build and run:**
   ```bash
   cd generated/your-project-name
   mvn clean install
   mvn spring-boot:run
   ```
5. **Access Swagger UI**: http://localhost:8080/swagger-ui.html

---

## 📖 Documentation

- **[Quick Start Guide](docs/QUICKSTART.md)** - Get started in 5 minutes
- **[Project Structure](PROJECT_STRUCTURE.md)** - Folder organization explained
- **[Java Agent Summary](docs/JAVA_AGENT_SUMMARY.md)** - Detailed agent documentation
- **[Example Contracts](examples/)** - Sample OpenAPI specifications

---

## 🎯 Usage Examples

### Basic CRUD API

```powershell
.\bin\generate-java-api.ps1 `
  -ContractPath ".\examples\order-management-api.yaml" `
  -ProjectName "Order API"
```

**Generates:**
- ✅ Complete Spring Boot project
- ✅ CRUD endpoints (GET, POST, PUT, DELETE)
- ✅ JPA entities and repositories
- ✅ Service layer with business logic
- ✅ DTOs with validation
- ✅ MapStruct mappers
- ✅ Exception handling
- ✅ Unit tests
- ✅ Swagger documentation
- ✅ Sample data

### Custom Configuration

```powershell
.\bin\generate-java-api.ps1 `
  -ContractPath ".\examples\user-service.yaml" `
  -ProjectName "User Service" `
  -GroupId "com.example" `
  -ArtifactId "user-service" `
  -JavaVersion "17" `
  -SpringBootVersion "3.2.0" `
  -BuildTool "Gradle" `
  -OutputPath "C:\Projects\user-service"
```

### Incremental Mode (Add to Existing Project)

```powershell
.\bin\generate-java-api.ps1 `
  -ContractPath ".\examples\new-endpoints.yaml" `
  -ProjectName "My API" `
  -Mode "Incremental" `
  -ExistingProjectPath ".\generated\my-api"
```

---

## 🛠️ Generated Project Structure

```
generated/your-api-name/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/yourapi/
│   │   │       ├── YourApiApplication.java
│   │   │       ├── controller/         # REST endpoints
│   │   │       ├── service/            # Business logic
│   │   │       │   └── impl/
│   │   │       ├── repository/         # Data access
│   │   │       ├── entity/             # JPA entities
│   │   │       ├── dto/                # Request/Response DTOs
│   │   │       ├── mapper/             # MapStruct mappers
│   │   │       ├── exception/          # Exception handling
│   │   │       └── config/             # Configuration
│   │   └── resources/
│   │       ├── application.yml
│   │       ├── application-dev.yml
│   │       ├── application-prod.yml
│   │       └── data.sql                # Sample data
│   └── test/
│       └── java/                       # Comprehensive tests
├── pom.xml                             # Maven config
├── README.md                           # Project documentation
└── .gitignore
```

---

## 🎨 Customization

### Modify Generation Templates

Edit `templates/java-agent-prompt.md` to customize:

- Coding standards
- Architecture patterns
- Naming conventions
- Testing strategies
- Documentation style

### Add New Examples

Place your OpenAPI YAML files in `examples/`:

```bash
examples/
├── order-management-api.yaml
├── user-service-api.yaml        # Add yours here
└── product-catalog-api.yaml     # Add yours here
```

### Configure Default Settings

Edit `config/java-agent-config.json`:

```json
{
  "defaultJavaVersion": "21",
  "defaultSpringBootVersion": "3.2.0",
  "defaultBuildTool": "Maven",
  "includeLombok": true,
  "includeMapStruct": true
}
```

---

## 📊 Generated Code Quality

✅ **Production-Ready**
- Compiles on first attempt
- All tests pass
- No linting errors

✅ **Best Practices**
- Layered architecture
- Dependency injection
- Interface-based design
- Transaction management

✅ **Security**
- Input validation
- SQL injection prevention
- Proper exception handling

✅ **Testing**
- Controller tests (MockMvc)
- Service tests (Mockito)
- >80% code coverage

✅ **Documentation**
- Javadoc comments
- Swagger/OpenAPI docs
- README with examples

---

## 🔧 Troubleshooting

### Script Execution Issues

**Windows PowerShell Execution Policy:**
```powershell
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
```

**Linux/Mac Permissions:**
```bash
chmod +x bin/generate-java-api.sh
```

### Maven Not Found

Ensure Maven is in your PATH:
```bash
mvn -version
```

Download: https://maven.apache.org/download.cgi

### Java Version Issues

Check your Java version:
```bash
java -version
```

Use Java 17 or 21 (LTS versions).

---

## 🤝 Contributing

Contributions are welcome! Please:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 🙏 Acknowledgments

- Spring Boot team for the amazing framework
- OpenAPI Initiative for API specifications
- MapStruct team for type-safe mapping
- Lombok team for reducing boilerplate

---

## 📧 Support

- **Documentation**: See `docs/` folder
- **Issues**: [GitHub Issues](https://github.com/yourusername/java-api-contract-agent/issues)
- **Discussions**: [GitHub Discussions](https://github.com/yourusername/java-api-contract-agent/discussions)

---

## 🚀 What's Next?

- [ ] Add GraphQL support
- [ ] Support for microservices patterns
- [ ] Docker containerization
- [ ] Kubernetes deployment manifests
- [ ] CI/CD pipeline templates
- [ ] MongoDB support
- [ ] PostgreSQL/MySQL production configs

---

**Made with ☕ by the Java API Contract Agent Team**

*Generate Spring Boot APIs in seconds, not days!*
