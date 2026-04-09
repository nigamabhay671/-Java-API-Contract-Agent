# Generated Projects Directory

This folder contains all generated Spring Boot REST API projects.

## 📂 Contents

Each generated project will appear as a subdirectory here:

```
generated/
├── last-java-generation-prompt.txt    # Most recent prompt (for debugging)
├── order-management-api/              # Example project 1
├── user-service-api/                  # Example project 2
└── product-catalog-api/               # Example project 3
```

## 🚀 Working with Generated Projects

### Build a Project

```bash
cd <project-name>
mvn clean install
```

### Run a Project

```bash
cd <project-name>
mvn spring-boot:run
```

### Test a Project

```bash
cd <project-name>
mvn test
```

### Access Swagger UI

After running the project:
```
http://localhost:8080/swagger-ui.html
```

## 📝 Default Output Location

By default, the generator scripts create projects in this directory. You can change this with the `-OutputPath` parameter:

```powershell
.\bin\generate-java-api.ps1 `
  -ContractPath ".\examples\my-api.yaml" `
  -ProjectName "My API" `
  -OutputPath "C:\custom\location"
```

## 🗑️ Cleaning Up

To remove all generated projects:

```bash
# Windows
Remove-Item -Recurse -Force generated/*/ -Exclude "*.txt","README.md"

# Linux/Mac
rm -rf generated/*/
```

## 📊 Project Statistics

See how many projects you've generated:

```bash
# Count projects
ls -d generated/*/ | wc -l

# List all projects
ls -d generated/*/
```

## ⚠️ Important Notes

- **Generated projects** are `.gitignored` by default
- **Prompt files** (`*.txt`) are tracked for debugging
- Each project is **independent** and self-contained
- Projects include **README.md** with specific instructions

## 🔧 Customization

Generated projects use the following defaults (customizable via script parameters):

- **Java Version**: 21
- **Spring Boot Version**: 3.2.0
- **Build Tool**: Maven
- **Database**: H2 (in-memory)
- **Port**: 8080

See the main [README.md](../README.md) for customization options.
