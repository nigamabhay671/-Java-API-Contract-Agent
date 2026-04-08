# Java API Contract Agent - PowerShell CLI
# Generates Spring Boot REST APIs from OpenAPI contracts

param(
    [Parameter(Mandatory=$false)]
    [string]$ContractPath,

    [Parameter(Mandatory=$false)]
    [string]$ProjectName,

    [Parameter(Mandatory=$false)]
    [string]$GroupId = "com.example",

    [Parameter(Mandatory=$false)]
    [string]$ArtifactId,

    [Parameter(Mandatory=$false)]
    [string]$JavaVersion = "17",

    [Parameter(Mandatory=$false)]
    [string]$SpringBootVersion = "3.2.0",

    [Parameter(Mandatory=$false)]
    [ValidateSet("Maven", "Gradle")]
    [string]$BuildTool = "Maven",

    [Parameter(Mandatory=$false)]
    [string]$OutputPath = "./generated-api",

    [Parameter(Mandatory=$false)]
    [switch]$IncludeLombok = $true,

    [Parameter(Mandatory=$false)]
    [switch]$IncludeMapStruct = $true,

    [Parameter(Mandatory=$false)]
    [switch]$Interactive,

    [Parameter(Mandatory=$false)]
    [switch]$Help
)

# Display help
if ($Help) {
    Write-Host "═══════════════════════════════════════════════════════════" -ForegroundColor Cyan
    Write-Host "  Java API Contract Agent - Spring Boot Generator" -ForegroundColor Cyan
    Write-Host "═══════════════════════════════════════════════════════════" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "USAGE:" -ForegroundColor Yellow
    Write-Host "  .\generate-java-api.ps1 [OPTIONS]"
    Write-Host ""
    Write-Host "PARAMETERS:" -ForegroundColor Yellow
    Write-Host "  -ContractPath       Path to OpenAPI/Swagger contract file"
    Write-Host "  -ProjectName        Project name (e.g., Order Management API)"
    Write-Host "  -GroupId            Maven group ID (default: com.example)"
    Write-Host "  -ArtifactId         Maven artifact ID (default: from ProjectName)"
    Write-Host "  -JavaVersion        Java version: 17 or 21 (default: 17)"
    Write-Host "  -SpringBootVersion  Spring Boot version (default: 3.2.0)"
    Write-Host "  -BuildTool          Maven or Gradle (default: Maven)"
    Write-Host "  -OutputPath         Output directory (default: ./generated-api)"
    Write-Host "  -IncludeLombok      Include Lombok (default: true)"
    Write-Host "  -IncludeMapStruct   Include MapStruct (default: true)"
    Write-Host "  -Interactive        Run in interactive mode"
    Write-Host "  -Help               Display this help message"
    Write-Host ""
    Write-Host "EXAMPLES:" -ForegroundColor Yellow
    Write-Host "  # Basic generation"
    Write-Host "  .\generate-java-api.ps1 -ContractPath .\order-api.yaml -ProjectName 'Order API'"
    Write-Host ""
    Write-Host "  # With custom group and artifact"
    Write-Host "  .\generate-java-api.ps1 -ContractPath .\api.yaml -ProjectName 'My API' -GroupId com.mycompany -ArtifactId my-api"
    Write-Host ""
    Write-Host "  # Interactive mode"
    Write-Host "  .\generate-java-api.ps1 -Interactive"
    Write-Host ""
    Write-Host "  # With Gradle"
    Write-Host "  .\generate-java-api.ps1 -ContractPath .\api.yaml -ProjectName 'Product API' -BuildTool Gradle"
    Write-Host ""
    exit 0
}

# Banner
Write-Host ""
Write-Host "╔════════════════════════════════════════════════════════════╗" -ForegroundColor Cyan
Write-Host "║      Java API Contract Agent - v1.0.0                      ║" -ForegroundColor Cyan
Write-Host "║   Spring Boot REST API Generator                           ║" -ForegroundColor Cyan
Write-Host "╚════════════════════════════════════════════════════════════╝" -ForegroundColor Cyan
Write-Host ""

# Interactive mode
if ($Interactive) {
    Write-Host "🤖 Running in Interactive Mode" -ForegroundColor Green
    Write-Host ""

    $ContractPath = Read-Host "Enter path to OpenAPI contract file"

    Write-Host ""
    $ProjectName = Read-Host "Enter project name (e.g., 'Order Management API')"

    Write-Host ""
    $GroupId = Read-Host "Enter Maven Group ID (press Enter for 'com.example')"
    if ([string]::IsNullOrWhiteSpace($GroupId)) {
        $GroupId = "com.example"
    }

    Write-Host ""
    Write-Host "Java Version:" -ForegroundColor Yellow
    Write-Host "  1. Java 17 (LTS) - Recommended"
    Write-Host "  2. Java 21 (LTS)"
    $javaChoice = Read-Host "Select Java version (1-2, default: 1)"
    $JavaVersion = if ($javaChoice -eq "2") { "21" } else { "17" }

    Write-Host ""
    Write-Host "Build Tool:" -ForegroundColor Yellow
    Write-Host "  1. Maven (Recommended)"
    Write-Host "  2. Gradle"
    $buildChoice = Read-Host "Select build tool (1-2, default: 1)"
    $BuildTool = if ($buildChoice -eq "2") { "Gradle" } else { "Maven" }

    Write-Host ""
    $OutputPath = Read-Host "Enter output directory (press Enter for './generated-api')"
    if ([string]::IsNullOrWhiteSpace($OutputPath)) {
        $OutputPath = "./generated-api"
    }
}

# Validate inputs
if ([string]::IsNullOrWhiteSpace($ContractPath)) {
    Write-Host "❌ Error: Contract path is required" -ForegroundColor Red
    Write-Host "Use -Help for usage information" -ForegroundColor Yellow
    exit 1
}

if ([string]::IsNullOrWhiteSpace($ProjectName)) {
    Write-Host "❌ Error: Project name is required" -ForegroundColor Red
    Write-Host "Use -Help for usage information" -ForegroundColor Yellow
    exit 1
}

if (-not (Test-Path $ContractPath)) {
    Write-Host "❌ Error: Contract file not found: $ContractPath" -ForegroundColor Red
    exit 1
}

# Generate artifact ID from project name if not provided
if ([string]::IsNullOrWhiteSpace($ArtifactId)) {
    $ArtifactId = $ProjectName.ToLower() -replace '\s+', '-' -replace '[^a-z0-9-]', ''
}

# Generate package name from group ID and artifact ID
$packageName = "$GroupId.$($ArtifactId -replace '-', '')"

# Display configuration
Write-Host "📋 Configuration:" -ForegroundColor Green
Write-Host "  Contract:          $ContractPath"
Write-Host "  Project Name:      $ProjectName"
Write-Host "  Group ID:          $GroupId"
Write-Host "  Artifact ID:       $ArtifactId"
Write-Host "  Package:           $packageName"
Write-Host "  Java Version:      $JavaVersion"
Write-Host "  Spring Boot:       $SpringBootVersion"
Write-Host "  Build Tool:        $BuildTool"
Write-Host "  Lombok:            $IncludeLombok"
Write-Host "  MapStruct:         $IncludeMapStruct"
Write-Host "  Output Path:       $OutputPath"
Write-Host ""

# Read contract content
Write-Host "📖 Reading contract file..." -ForegroundColor Yellow
$contractContent = Get-Content -Path $ContractPath -Raw

# Load Java agent prompt
$agentPromptPath = Join-Path $PSScriptRoot "java-agent-prompt.md"
if (-not (Test-Path $agentPromptPath)) {
    Write-Host "❌ Error: Agent prompt file not found: $agentPromptPath" -ForegroundColor Red
    exit 1
}
$agentPrompt = Get-Content -Path $agentPromptPath -Raw

# Build the full prompt
$fullPrompt = @"
$agentPrompt

---

## CURRENT GENERATION REQUEST

**OpenAPI Contract:**
``````yaml
$contractContent
``````

**Project Configuration:**
- **Project Name**: $ProjectName
- **Group ID**: $GroupId
- **Artifact ID**: $ArtifactId
- **Base Package**: $packageName
- **Java Version**: $JavaVersion
- **Spring Boot Version**: $SpringBootVersion
- **Build Tool**: $BuildTool
- **Output Path**: $OutputPath
- **Include Lombok**: $IncludeLombok
- **Include MapStruct**: $IncludeMapStruct

**Package Structure:**
``````
$packageName/
├── ${ArtifactId}Application.java
├── controller/
├── service/
│   └── impl/
├── repository/
├── entity/
├── dto/
├── mapper/
├── exception/
└── config/
``````

**Instructions:**
Generate a complete, production-ready Spring Boot REST API following all the Java and Spring Boot best practices defined above.

**Requirements:**
1. Use Java $JavaVersion with Spring Boot $SpringBootVersion
2. Use $BuildTool as the build tool
3. Base package name: $packageName
4. Include all layers: Controller, Service, Repository, Entity, DTO
5. Use MapStruct for entity-DTO mapping
6. Use Lombok to reduce boilerplate
7. Implement global exception handling with @RestControllerAdvice
8. Add Bean Validation on all DTOs
9. Configure SpringDoc OpenAPI for Swagger UI
10. Use H2 in-memory database with sample data
11. Include comprehensive unit tests (JUnit 5 + Mockito)
12. Add proper logging with SLF4J
13. Create application.yml with dev and prod profiles
14. Follow RESTful API conventions
15. Include README.md with build and run instructions

**Expected Output:**
- Complete Maven/Gradle project structure
- All Java source files with full implementation
- Unit tests for all controllers and services
- Configuration files (pom.xml/build.gradle, application.yml)
- Documentation (README.md, Javadoc comments)
- Sample data initialization

Generate production-ready code that compiles and runs on first attempt!
"@

# Save prompt to file
$promptFile = Join-Path $PSScriptRoot "last-java-generation-prompt.txt"
$fullPrompt | Out-File -FilePath $promptFile -Encoding UTF8

Write-Host "✅ Java API generation prompt prepared" -ForegroundColor Green
Write-Host ""
Write-Host "╔════════════════════════════════════════════════════════════╗" -ForegroundColor Yellow
Write-Host "║  NEXT STEPS:                                               ║" -ForegroundColor Yellow
Write-Host "╚════════════════════════════════════════════════════════════╝" -ForegroundColor Yellow
Write-Host ""
Write-Host "The Spring Boot API generation prompt has been prepared:" -ForegroundColor Cyan
Write-Host "  $promptFile" -ForegroundColor White
Write-Host ""
Write-Host "To generate your Spring Boot API:" -ForegroundColor Cyan
Write-Host ""
Write-Host "1. Copy the prompt:" -ForegroundColor Yellow
Write-Host "   Get-Content '$promptFile' | clip" -ForegroundColor White
Write-Host ""
Write-Host "2. Paste it to Claude Code or your AI assistant" -ForegroundColor Yellow
Write-Host ""
Write-Host "3. The AI will generate complete Spring Boot project!" -ForegroundColor Yellow
Write-Host ""
Write-Host "After generation, build and run:" -ForegroundColor Green
Write-Host "  cd $OutputPath" -ForegroundColor White
Write-Host "  mvn clean install" -ForegroundColor White
Write-Host "  mvn spring-boot:run" -ForegroundColor White
Write-Host ""
Write-Host "Access Swagger UI at:" -ForegroundColor Green
Write-Host "  http://localhost:8080/swagger-ui.html" -ForegroundColor White
Write-Host ""

# Optional: Display the prompt
$showPrompt = Read-Host "Would you like to display the prompt now? (y/n)"
if ($showPrompt -eq "y" -or $showPrompt -eq "Y") {
    Write-Host ""
    Write-Host "═══════════════════════════════════════════════════════════" -ForegroundColor Cyan
    Write-Host "SPRING BOOT API GENERATION PROMPT" -ForegroundColor Cyan
    Write-Host "═══════════════════════════════════════════════════════════" -ForegroundColor Cyan
    Write-Host ""
    Write-Host $fullPrompt
    Write-Host ""
    Write-Host "═══════════════════════════════════════════════════════════" -ForegroundColor Cyan
}

Write-Host ""
Write-Host "✨ Java API agent preparation complete!" -ForegroundColor Green
Write-Host ""
