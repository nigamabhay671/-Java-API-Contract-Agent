# ⚡ Quick Start - Java API Agent

**Generate a Spring Boot API in 5 minutes!**

---

## 🎯 Goal

Generate a complete, production-ready Spring Boot REST API from an OpenAPI contract.

---

## 📝 Step-by-Step

### Step 1: Open Terminal

Navigate to the Java agent folder:

```bash
cd java-api-agent
```

### Step 2: Run the Agent (Interactive Mode)

**Windows:**
```powershell
.\generate-java-api.ps1 -Interactive
```

**Mac/Linux:**
```bash
./generate-java-api.sh -i
```

### Step 3: Answer the Prompts

```
1. Enter path to OpenAPI contract file:
   → Type: examples/order-management-api.yaml

2. Enter project name:
   → Type: Order Management API

3. Enter Maven Group ID:
   → Press Enter (uses com.example)

4. Select Java version:
   → Type: 1 (for Java 17)

5. Select build tool:
   → Type: 1 (for Maven)

6. Enter output directory:
   → Press Enter (uses ./generated-api)
```

### Step 4: Get the Prompt

The agent creates a prompt and saves it to:
```
last-java-generation-prompt.txt
```

### Step 5: Copy the Prompt

**Windows:**
```powershell
Get-Content last-java-generation-prompt.txt | clip
```

**Mac/Linux:**
```bash
cat last-java-generation-prompt.txt | pbcopy   # Mac
cat last-java-generation-prompt.txt | xclip    # Linux
```

### Step 6: Paste to AI Assistant

Paste the prompt to:
- **Claude Code** (recommended)
- **Claude.ai**
- Any AI assistant

### Step 7: Wait for Generation (1-2 minutes)

The AI will generate:
- ✅ Complete Spring Boot project
- ✅ All source code files
- ✅ Unit tests
- ✅ Configuration files
- ✅ Documentation

### Step 8: Build the Project

```bash
cd generated-api
mvn clean install
```

Expected output:
```
[INFO] BUILD SUCCESS
[INFO] Total time: XX s
```

### Step 9: Run the API

```bash
mvn spring-boot:run
```

Expected output:
```
Started OrderManagementApiApplication in X.XXX seconds
```

### Step 10: Test It!

Open your browser:

**Swagger UI:**
```
http://localhost:8080/swagger-ui.html
```

You'll see all your API endpoints ready to test!

**H2 Console:**
```
http://localhost:8080/h2-console
```

---

## 🎉 Success!

You now have a fully functional Spring Boot API with:
- ✅ REST Controllers
- ✅ Service Layer
- ✅ JPA Repositories
- ✅ Entity Classes
- ✅ DTOs with Validation
- ✅ MapStruct Mappers
- ✅ Global Exception Handling
- ✅ Unit Tests
- ✅ Swagger Documentation
- ✅ Sample Data

---

## 🧪 Testing the API

### Using Swagger UI

1. Go to `http://localhost:8080/swagger-ui.html`
2. Click on any endpoint (e.g., `GET /api/orders`)
3. Click "Try it out"
4. Click "Execute"
5. See the response!

### Using curl

**Get all orders:**
```bash
curl http://localhost:8080/api/orders
```

**Create an order:**
```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerName": "John Doe",
    "items": [
      {
        "productName": "Laptop",
        "quantity": 1,
        "unitPrice": 999.99
      }
    ]
  }'
```

**Get order by ID:**
```bash
curl http://localhost:8080/api/orders/1
```

---

## 🔄 Generate Another API

Want to generate more APIs? Just run the agent again!

**Quick generation (non-interactive):**

**Windows:**
```powershell
.\generate-java-api.ps1 `
  -ContractPath "examples\order-management-api.yaml" `
  -ProjectName "Product API"
```

**Mac/Linux:**
```bash
./generate-java-api.sh \
  -c examples/order-management-api.yaml \
  -p "Product API"
```

---

## 📚 Project Structure

Your generated project has:

```
generated-api/
├── src/main/java/          # Source code
│   ├── controller/         # REST endpoints
│   ├── service/            # Business logic
│   ├── repository/         # Data access
│   ├── entity/             # JPA entities
│   ├── dto/                # Request/Response objects
│   ├── mapper/             # MapStruct mappers
│   ├── exception/          # Error handling
│   └── config/             # Configuration
├── src/main/resources/     # Config files
│   ├── application.yml     # Main config
│   └── data.sql            # Sample data
├── src/test/java/          # Unit tests
├── pom.xml                 # Maven config
└── README.md               # Documentation
```

---

## ⚙️ Configuration

### Change Port

Edit `src/main/resources/application.yml`:

```yaml
server:
  port: 8081  # Change from 8080
```

### Add Database

Replace H2 with PostgreSQL in `pom.xml`:

```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
</dependency>
```

Update `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/mydb
    username: postgres
    password: password
```

---

## 🛠️ Common Commands

### Maven

```bash
# Clean build
mvn clean install

# Run tests only
mvn test

# Run application
mvn spring-boot:run

# Package as JAR
mvn package

# Skip tests
mvn install -DskipTests
```

### Gradle

```bash
# Build
./gradlew build

# Run tests
./gradlew test

# Run application
./gradlew bootRun

# Package
./gradlew bootJar
```

---

## 🔍 Verify Generation

After generation, check:

1. ✅ Project compiles: `mvn clean compile`
2. ✅ All tests pass: `mvn test`
3. ✅ Application starts: `mvn spring-boot:run`
4. ✅ Swagger UI works: Open browser to URL
5. ✅ Endpoints respond: Test with curl or Swagger

---

## 📖 Learn More

- **Full Documentation**: See `README.md`
- **Agent Details**: See `java-agent-prompt.md`
- **Configuration**: Edit `config/java-agent-config.json`
- **Examples**: Check `examples/` folder

---

## 💡 Tips

1. **Validate contract** first at [editor.swagger.io](https://editor.swagger.io/)
2. **Use meaningful names** for operations and schemas
3. **Run tests** after generation: `mvn test`
4. **Check logs** if something fails
5. **Review code** before deploying to production

---

## ❓ Need Help?

Run the help command:

**Windows:**
```powershell
.\generate-java-api.ps1 -Help
```

**Mac/Linux:**
```bash
./generate-java-api.sh -h
```

---

## 🚀 Next Steps

After your API is running:

1. ✅ Test all endpoints in Swagger UI
2. ✅ Review the generated code
3. ✅ Run the unit tests
4. ✅ Customize business logic
5. ✅ Add database configuration
6. ✅ Add security (Spring Security)
7. ✅ Deploy to production

---

**Happy Spring Boot Development! ☕🚀**
