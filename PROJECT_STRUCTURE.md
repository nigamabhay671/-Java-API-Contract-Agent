# Java API Contract Agent - Project Structure

This document describes the organization of the Java API Contract Agent project.

## 📁 Folder Structure

```
Java-API-Contract-Agent/
│
├── 📂 bin/                          # Executable scripts
│   ├── generate-java-api.ps1        # PowerShell script for Windows
│   └── generate-java-api.sh         # Bash script for Linux/Mac
│
├── 📂 templates/                    # Prompt templates
│   └── java-agent-prompt.md        # Main Java agent system prompt
│
├── 📂 examples/                     # Sample OpenAPI contracts
│   ├── order-management-api.yaml   # Order management example
│   └── test.yaml                   # Test contract
│
├── 📂 config/                       # Configuration files
│   └── java-agent-config.json      # Agent configuration
│
├── 📂 generated/                    # Output directory
│   ├── last-java-generation-prompt.txt  # Last generated prompt
│   └── [generated-projects]/       # Generated Spring Boot projects go here
│
├── 📂 docs/                         # Documentation
│   ├── QUICKSTART.md               # Quick start guide
│   ├── JAVA_AGENT_SUMMARY.md       # Agent summary
│   └── [additional-docs]/          # Other documentation
│
├── 📂 .archive/                     # Archived/backup files
│   ├── generate-java-api-backup.ps1
│   └── generate-java-api-corrupt.ps1.bak
│
├── 📄 README.md                     # Main project README
├── 📄 PROJECT_STRUCTURE.md          # This file
└── 📄 .gitignore                    # Git ignore rules
```

## 📂 Directory Descriptions

### `/bin/` - Executable Scripts

Contains all runnable scripts for generating Java APIs.

**Files:**
- `generate-java-api.ps1` - Windows PowerShell script
- `generate-java-api.sh` - Linux/Mac Bash script

**Usage:**
```powershell
# Windows
.\bin\generate-java-api.ps1 -ContractPath ".\examples\order-management-api.yaml" -ProjectName "My API"

# Linux/Mac
./bin/generate-java-api.sh --contract examples/order-management-api.yaml --project "My API"
```

### `/templates/` - Prompt Templates

Contains AI prompt templates used for code generation.

**Files:**
- `java-agent-prompt.md` - Main system prompt with Java/Spring Boot best practices

These templates define how the AI should generate code, what patterns to follow, and what standards to enforce.

### `/examples/` - Sample OpenAPI Contracts

Contains example OpenAPI/Swagger specification files for testing and reference.

**Files:**
- `order-management-api.yaml` - Complete order management REST API example
- `test.yaml` - Simple test contract

Add your own OpenAPI contracts here to generate APIs from them.

### `/config/` - Configuration Files

Configuration files for the agent behavior and generation settings.

**Files:**
- `java-agent-config.json` - Agent configuration (Java version, dependencies, etc.)

### `/generated/` - Output Directory

**Default output location for all generated content.**

Generated Spring Boot projects will be created in subdirectories here:
```
generated/
├── last-java-generation-prompt.txt    # Last prompt (for debugging)
├── order-management-api/              # Generated project 1
├── user-service-api/                  # Generated project 2
└── product-catalog-api/               # Generated project 3
```

**Can be changed with `-OutputPath` parameter.**

### `/docs/` - Documentation

All project documentation including guides, tutorials, and architecture docs.

**Files:**
- `QUICKSTART.md` - Quick start guide
- `JAVA_AGENT_SUMMARY.md` - Detailed agent documentation
- Additional guides and references

### `/.archive/` - Archived Files

Backup and deprecated files. Not used in active development.

## 🎯 Workflow

### 1. **Prepare Your OpenAPI Contract**

Place your OpenAPI YAML file in `/examples/`:
```
examples/my-api.yaml
```

### 2. **Run the Generator Script**

```powershell
.\bin\generate-java-api.ps1 `
  -ContractPath ".\examples\my-api.yaml" `
  -ProjectName "My API" `
  -OutputPath ".\generated\my-api"
```

### 3. **Generated Prompt Created**

The script creates:
```
generated/last-java-generation-prompt.txt
```

### 4. **AI Generates the Code**

Feed the prompt to AI (Claude Code, ChatGPT, etc.) to generate:
```
generated/my-api/
├── src/
├── pom.xml
├── README.md
└── ...
```

### 5. **Build and Run**

```bash
cd generated/my-api
mvn clean install
mvn spring-boot:run
```

## 🔧 Customization

### Change Output Location

```powershell
.\bin\generate-java-api.ps1 `
  -ContractPath ".\examples\my-api.yaml" `
  -ProjectName "My API" `
  -OutputPath "C:\Projects\my-custom-location"
```

### Modify Generation Template

Edit `templates/java-agent-prompt.md` to customize:
- Java version requirements
- Spring Boot version
- Coding standards
- Architecture patterns
- Testing strategies

### Add New Examples

Simply add `.yaml` or `.yml` OpenAPI files to `/examples/`

## 📊 Project Stats

Run this to see project statistics:

```bash
# Count lines of code in scripts
find bin -name "*.ps1" -o -name "*.sh" | xargs wc -l

# Count example contracts
ls -1 examples/*.yaml | wc -l

# See generated projects
ls -d generated/*/
```

## 🤝 Contributing

When contributing:
1. **Scripts** → `/bin/`
2. **Templates** → `/templates/`
3. **Examples** → `/examples/`
4. **Documentation** → `/docs/`
5. **Tests** → Create `/tests/` if needed

## 📝 Notes

- **Generated projects** in `/generated/` are gitignored (except prompt files)
- **Archive folder** is for backups only
- **Config files** should be documented when changed
- **Templates** are critical - test thoroughly before modifying

---

Last Updated: April 2026  
Version: 2.0 (Restructured)
