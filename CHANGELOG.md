# Changelog

All notable changes to the Java API Contract Agent project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [2.0.0] - 2026-04-08

### 🎉 Major Restructure

#### Added
- **New Folder Structure**: Organized project into logical directories
  - `/bin/` - Executable scripts
  - `/templates/` - AI prompt templates
  - `/generated/` - Output directory for generated projects
  - `/docs/` - Documentation
  - `/.archive/` - Backup files
- **Documentation**:
  - `PROJECT_STRUCTURE.md` - Comprehensive folder structure documentation
  - `CHANGELOG.md` - This file
  - Individual READMEs in `bin/` and `generated/` folders
- **Enhanced README**: Updated main README with new structure and badges

#### Changed
- **Scripts Location**: Moved from root to `/bin/`
  - `generate-java-api.ps1` → `bin/generate-java-api.ps1`
  - `generate-java-api.sh` → `bin/generate-java-api.sh`
- **Templates Location**: Moved from root to `/templates/`
  - `java-agent-prompt.md` → `templates/java-agent-prompt.md`
- **Output Location**: Changed default output to `/generated/`
- **Documentation**: Consolidated docs in `/docs/` folder
- **Examples**: Kept in `/examples/` (no change)
- **Config**: Kept in `/config/` (no change)

#### Improved
- **`.gitignore`**: Enhanced with better organization and comments
- **File Organization**: Clearer separation of concerns
- **Documentation**: More comprehensive and easier to navigate
- **User Experience**: Clearer paths and better organization

#### Archived
- Moved backup/old files to `/.archive/`:
  - `generate-java-api-backup.ps1`
  - `generate-java-api-corrupt.ps1.bak`

### 📝 Migration Guide

If you were using version 1.x, update your commands:

**Before (v1.x):**
```powershell
.\generate-java-api.ps1 -ContractPath ".\examples\api.yaml" -ProjectName "My API"
```

**After (v2.0):**
```powershell
.\bin\generate-java-api.ps1 -ContractPath ".\examples\api.yaml" -ProjectName "My API"
```

Or from the `bin/` directory:
```powershell
cd bin
.\generate-java-api.ps1 -ContractPath "..\examples\api.yaml" -ProjectName "My API"
```

---

## [1.0.0] - 2026-04-08

### Initial Release

#### Added
- **Core Functionality**:
  - PowerShell script for Windows (`generate-java-api.ps1`)
  - Bash script for Linux/Mac (`generate-java-api.sh`)
  - Java agent prompt template (`java-agent-prompt.md`)
  - Configuration file (`config/java-agent-config.json`)

- **Features**:
  - Generate Spring Boot 3.2.0 REST APIs
  - Support for Java 21 and Java 17
  - Maven and Gradle build tools
  - Lombok integration
  - MapStruct for entity-DTO mapping
  - Bean Validation
  - Global exception handling
  - SpringDoc OpenAPI (Swagger UI)
  - H2 in-memory database
  - JUnit 5 + Mockito tests
  - Spring Boot Actuator

- **Modes**:
  - Full project generation
  - Incremental mode (add to existing project)
  - Interactive mode

- **Examples**:
  - Order Management API contract
  - Test contract

- **Documentation**:
  - Main README.md
  - QUICKSTART.md
  - JAVA_AGENT_SUMMARY.md

- **Generated Project Structure**:
  - Layered architecture (Controller, Service, Repository, Entity, DTO)
  - MapStruct mappers
  - Exception handling
  - Configuration classes
  - Comprehensive unit tests
  - Sample data initialization

---

## Version History

| Version | Date | Description |
|---------|------|-------------|
| 2.0.0 | 2026-04-08 | Major folder restructure and documentation improvements |
| 1.0.0 | 2026-04-08 | Initial release with core functionality |

---

## Upcoming Features

### [2.1.0] - Planned

- [ ] Docker support
- [ ] Kubernetes manifests
- [ ] PostgreSQL configuration templates
- [ ] MongoDB support
- [ ] GraphQL integration
- [ ] CI/CD pipeline templates
- [ ] Security enhancements (JWT, OAuth2)

### [3.0.0] - Future

- [ ] Microservices architecture patterns
- [ ] Service mesh integration
- [ ] Event-driven architecture
- [ ] Cloud provider templates (AWS, Azure, GCP)
- [ ] API Gateway integration
- [ ] Distributed tracing

---

## Contributing

See [README.md](README.md) for contribution guidelines.

## Support

For questions, issues, or feature requests:
- Open an issue on GitHub
- Check the documentation in `/docs/`
- Read the [PROJECT_STRUCTURE.md](PROJECT_STRUCTURE.md)
