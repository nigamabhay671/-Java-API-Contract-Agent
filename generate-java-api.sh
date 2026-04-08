#!/bin/bash

# Java API Contract Agent - Bash CLI
# Generates Spring Boot REST APIs from OpenAPI contracts

# Colors
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
CYAN='\033[0;36m'
NC='\033[0m' # No Color

# Default values
CONTRACT_PATH=""
PROJECT_NAME=""
GROUP_ID="com.example"
ARTIFACT_ID=""
JAVA_VERSION="21"
SPRING_BOOT_VERSION="3.2.0"
BUILD_TOOL="Maven"
OUTPUT_PATH="./generated-api"
MODE="Full"
EXISTING_PROJECT_PATH=""
INCLUDE_LOMBOK=true
INCLUDE_MAPSTRUCT=true
INTERACTIVE=false

# Help function
show_help() {
    echo -e "${CYAN}═══════════════════════════════════════════════════════════${NC}"
    echo -e "${CYAN}  Java API Contract Agent - Spring Boot Generator${NC}"
    echo -e "${CYAN}═══════════════════════════════════════════════════════════${NC}"
    echo ""
    echo -e "${YELLOW}USAGE:${NC}"
    echo "  ./generate-java-api.sh [OPTIONS]"
    echo ""
    echo -e "${YELLOW}OPTIONS:${NC}"
    echo "  -c, --contract PATH      Path to OpenAPI/Swagger contract file"
    echo "  -p, --project NAME       Project name (e.g., 'Order Management API')"
    echo "  -g, --group ID           Maven group ID (default: com.example)"
    echo "  -a, --artifact ID        Maven artifact ID (generated from project name)"
    echo "  -j, --java VERSION       Java version: 17 or 21 (default: 21)"
    echo "  -s, --spring VERSION     Spring Boot version (default: 3.2.0)"
    echo "  -b, --build TOOL         Maven or Gradle (default: Maven)"
    echo "  -o, --output PATH        Output directory (default: ./generated-api)"
    echo "  -m, --mode MODE          Full or Incremental (default: Full)"
    echo "  -x, --existing PATH      Path to existing project (required for Incremental)"
    echo "  -i, --interactive        Run in interactive mode"
    echo "  -h, --help               Display this help message"
    echo ""
    echo -e "${YELLOW}EXAMPLES:${NC}"
    echo "  # Basic generation"
    echo "  ./generate-java-api.sh -c ./order-api.yaml -p 'Order API'"
    echo ""
    echo "  # With custom group and artifact"
    echo "  ./generate-java-api.sh -c ./api.yaml -p 'My API' -g com.mycompany -a my-api"
    echo ""
    echo "  # Incremental: Add endpoint to existing project"
    echo "  ./generate-java-api.sh -c ./new-endpoint.yaml -p 'My API' -m Incremental -x ./my-api"
    echo ""
    echo "  # Interactive mode"
    echo "  ./generate-java-api.sh -i"
    echo ""
    exit 0
}

# Parse arguments
while [[ $# -gt 0 ]]; do
    case $1 in
        -c|--contract)
            CONTRACT_PATH="$2"
            shift 2
            ;;
        -p|--project)
            PROJECT_NAME="$2"
            shift 2
            ;;
        -g|--group)
            GROUP_ID="$2"
            shift 2
            ;;
        -a|--artifact)
            ARTIFACT_ID="$2"
            shift 2
            ;;
        -j|--java)
            JAVA_VERSION="$2"
            shift 2
            ;;
        -s|--spring)
            SPRING_BOOT_VERSION="$2"
            shift 2
            ;;
        -b|--build)
            BUILD_TOOL="$2"
            shift 2
            ;;
        -o|--output)
            OUTPUT_PATH="$2"
            shift 2
            ;;
        -m|--mode)
            MODE="$2"
            shift 2
            ;;
        -x|--existing)
            EXISTING_PROJECT_PATH="$2"
            shift 2
            ;;
        -i|--interactive)
            INTERACTIVE=true
            shift
            ;;
        -h|--help)
            show_help
            ;;
        *)
            echo -e "${RED}Unknown option: $1${NC}"
            echo "Use --help for usage information"
            exit 1
            ;;
    esac
done

# Banner
echo ""
echo -e "${CYAN}╔════════════════════════════════════════════════════════════╗${NC}"
echo -e "${CYAN}║      Java API Contract Agent - v1.0.0                      ║${NC}"
echo -e "${CYAN}║   Spring Boot REST API Generator                           ║${NC}"
echo -e "${CYAN}╚════════════════════════════════════════════════════════════╝${NC}"
echo ""

# Check prerequisites
echo -e "${YELLOW}🔍 Checking prerequisites...${NC}"

# Check Java
if command -v java &> /dev/null; then
    JAVA_VER=$(java -version 2>&1 | head -n 1)
    echo -e "${GREEN}✓ Java found: $JAVA_VER${NC}"
else
    echo -e "${YELLOW}⚠️  Warning: Java not found in PATH${NC}"
    echo -e "${YELLOW}   Please install Java 17 or 21 from https://adoptium.net/${NC}"
fi

# Check Maven
if command -v mvn &> /dev/null; then
    MVN_VER=$(mvn -version 2>&1 | head -n 1)
    echo -e "${GREEN}✓ Maven found: $MVN_VER${NC}"
else
    echo -e "${YELLOW}⚠️  Warning: Maven not found in PATH${NC}"
    echo -e "${YELLOW}   Maven is required to build generated projects${NC}"
fi

echo ""

# Interactive mode
if [ "$INTERACTIVE" = true ]; then
    echo -e "${GREEN}🤖 Running in Interactive Mode${NC}"
    echo ""

    read -p "Enter path to OpenAPI contract file: " CONTRACT_PATH

    echo ""
    echo -e "${YELLOW}Generation Mode:${NC}"
    echo "  1. Full - Generate complete new project (Recommended)"
    echo "  2. Incremental - Add endpoints to existing project"
    read -p "Select generation mode (1-2, default: 1): " mode_choice
    case $mode_choice in
        2) MODE="Incremental" ;;
        *) MODE="Full" ;;
    esac

    if [ "$MODE" = "Incremental" ]; then
        echo ""
        read -p "Enter path to existing project: " EXISTING_PROJECT_PATH
    fi

    echo ""
    read -p "Enter project name (e.g., 'Order Management API'): " PROJECT_NAME

    echo ""
    read -p "Enter Maven Group ID (default: com.example): " group_input
    if [ -n "$group_input" ]; then
        GROUP_ID="$group_input"
    fi

    echo ""
    echo -e "${YELLOW}Java Version:${NC}"
    echo "  1. Java 21 (LTS) - Recommended"
    echo "  2. Java 17 (LTS)"
    read -p "Select Java version (1-2, default: 1): " java_choice
    case $java_choice in
        2) JAVA_VERSION="17" ;;
        *) JAVA_VERSION="21" ;;
    esac

    echo ""
    echo -e "${YELLOW}Build Tool:${NC}"
    echo "  1. Maven (Recommended)"
    echo "  2. Gradle"
    read -p "Select build tool (1-2, default: 1): " build_choice
    case $build_choice in
        2) BUILD_TOOL="Gradle" ;;
        *) BUILD_TOOL="Maven" ;;
    esac

    echo ""
    read -p "Enter output directory (default: ./generated-api): " output_input
    if [ -n "$output_input" ]; then
        OUTPUT_PATH="$output_input"
    fi
fi

# Validate inputs
if [ -z "$CONTRACT_PATH" ]; then
    echo -e "${RED}❌ Error: Contract path is required${NC}"
    echo "Use --help for usage information"
    exit 1
fi

if [ -z "$PROJECT_NAME" ]; then
    echo -e "${RED}❌ Error: Project name is required${NC}"
    echo "Use --help for usage information"
    exit 1
fi

if [ ! -f "$CONTRACT_PATH" ]; then
    echo -e "${RED}❌ Error: Contract file not found: $CONTRACT_PATH${NC}"
    exit 1
fi

# Validate Java version
if [ "$JAVA_VERSION" != "17" ] && [ "$JAVA_VERSION" != "21" ]; then
    echo -e "${RED}❌ Error: Invalid Java version '$JAVA_VERSION'${NC}"
    echo -e "${YELLOW}Supported versions: 17, 21${NC}"
    exit 1
fi

# Validate incremental mode
if [ "$MODE" = "Incremental" ]; then
    if [ -z "$EXISTING_PROJECT_PATH" ]; then
        echo -e "${RED}❌ Error: ExistingProjectPath is required for Incremental mode${NC}"
        exit 1
    fi

    if [ ! -d "$EXISTING_PROJECT_PATH" ]; then
        echo -e "${RED}❌ Error: Existing project path not found: $EXISTING_PROJECT_PATH${NC}"
        exit 1
    fi

    # Check if it's a Spring Boot project
    if [ ! -f "$EXISTING_PROJECT_PATH/pom.xml" ] && [ ! -f "$EXISTING_PROJECT_PATH/build.gradle" ]; then
        echo -e "${RED}❌ Error: No pom.xml or build.gradle found in: $EXISTING_PROJECT_PATH${NC}"
        echo -e "${YELLOW}This doesn't appear to be a Maven or Gradle project${NC}"
        exit 1
    fi
fi

# Generate artifact ID from project name if not provided
if [ -z "$ARTIFACT_ID" ]; then
    ARTIFACT_ID=$(echo "$PROJECT_NAME" | tr '[:upper:]' '[:lower:]' | tr ' ' '-' | sed 's/[^a-z0-9-]//g')
fi

# Generate package name
PACKAGE_NAME="$GROUP_ID.$(echo $ARTIFACT_ID | tr '-' '')"

# Analyze existing project if incremental mode
EXISTING_COMPONENTS=""
if [ "$MODE" = "Incremental" ]; then
    echo -e "${YELLOW}📊 Analyzing existing project...${NC}"

    # Find source directory
    SRC_PATH="$EXISTING_PROJECT_PATH/src/main/java"
    if [ -d "$SRC_PATH" ]; then
        CONTROLLERS=$(find "$SRC_PATH" -name "*Controller.java" 2>/dev/null | xargs basename -s .java 2>/dev/null | sed 's/Controller$//' | tr '\n' ', ' | sed 's/,$//')
        SERVICES=$(find "$SRC_PATH" -name "*Service.java" ! -name "*ServiceImpl.java" 2>/dev/null | xargs basename -s .java 2>/dev/null | sed 's/Service$//' | tr '\n' ', ' | sed 's/,$//')
        ENTITIES=$(find "$SRC_PATH" -path "*/entity/*.java" 2>/dev/null | xargs basename -s .java 2>/dev/null | tr '\n' ', ' | sed 's/,$//')
        REPOSITORIES=$(find "$SRC_PATH" -name "*Repository.java" 2>/dev/null | xargs basename -s .java 2>/dev/null | sed 's/Repository$//' | tr '\n' ', ' | sed 's/,$//')

        EXISTING_COMPONENTS="

**Existing Project Components:**
- Controllers: ${CONTROLLERS:-None}
- Services: ${SERVICES:-None}
- Entities: ${ENTITIES:-None}
- Repositories: ${REPOSITORIES:-None}

**Note:** Generate ONLY new components that don't conflict with existing ones."

        CONTROLLER_COUNT=$(find "$SRC_PATH" -name "*Controller.java" 2>/dev/null | wc -l)
        SERVICE_COUNT=$(find "$SRC_PATH" -name "*Service.java" 2>/dev/null | wc -l)
        ENTITY_COUNT=$(find "$SRC_PATH" -path "*/entity/*.java" 2>/dev/null | wc -l)

        echo -e "${GREEN}  Found: $CONTROLLER_COUNT controllers, $SERVICE_COUNT services, $ENTITY_COUNT entities${NC}"
    else
        echo -e "${YELLOW}  ⚠️  Warning: Could not find src/main/java in project${NC}"
    fi
    echo ""
fi

# Display configuration
echo -e "${GREEN}📋 Configuration:${NC}"
echo "  Mode:              $MODE"
if [ "$MODE" = "Incremental" ]; then
    echo "  Existing Project:  $EXISTING_PROJECT_PATH"
fi
echo "  Contract:          $CONTRACT_PATH"
echo "  Project Name:      $PROJECT_NAME"
echo "  Group ID:          $GROUP_ID"
echo "  Artifact ID:       $ARTIFACT_ID"
echo "  Package:           $PACKAGE_NAME"
echo "  Java Version:      $JAVA_VERSION"
echo "  Spring Boot:       $SPRING_BOOT_VERSION"
echo "  Build Tool:        $BUILD_TOOL"
echo "  Output Path:       $OUTPUT_PATH"
echo ""

# Read contract content
echo -e "${YELLOW}📖 Reading contract file...${NC}"
CONTRACT_CONTENT=$(<"$CONTRACT_PATH")

# Get script directory
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# Load Java agent prompt
AGENT_PROMPT_PATH="$SCRIPT_DIR/java-agent-prompt.md"
if [ ! -f "$AGENT_PROMPT_PATH" ]; then
    echo -e "${RED}❌ Error: Agent prompt file not found: $AGENT_PROMPT_PATH${NC}"
    exit 1
fi
AGENT_PROMPT=$(<"$AGENT_PROMPT_PATH")

# Substitute Java version placeholder
AGENT_PROMPT="${AGENT_PROMPT//\{JAVA_VERSION\}/$JAVA_VERSION}"

# Build mode-specific instructions
if [ "$MODE" = "Incremental" ]; then
    INSTRUCTIONS="Analyze the existing project and generate ONLY new components for endpoints not currently implemented in the OpenAPI contract.
Preserve all existing code. Add new controllers, services, entities, and DTOs for new resources only.
Follow existing patterns and naming conventions found in the existing project.
Show minimal diffs for pom.xml and application.yml updates (add only missing dependencies/config)."

    EXPECTED_OUTPUT="- Analysis report: List existing components vs. new components to generate
- New Java source files ONLY (new controllers, services, repositories, entities, DTOs, mappers)
- New unit tests for new controllers and services
- Diffs for pom.xml (show only additions)
- Diffs for application.yml (show only additions)
- Integration instructions: How to add new code to existing project"
else
    INSTRUCTIONS="Generate a complete, production-ready Spring Boot REST API following all the Java and Spring Boot best practices defined above."

    EXPECTED_OUTPUT="- Complete Maven/Gradle project structure
- All Java source files with full implementation
- Unit tests for all controllers and services
- Configuration files (pom.xml/build.gradle, application.yml)
- Documentation (README.md, Javadoc comments)
- Sample data initialization"
fi

# Build the full prompt
FULL_PROMPT="$AGENT_PROMPT

---

## CURRENT GENERATION REQUEST

**Generation Mode**: $MODE
$EXISTING_COMPONENTS

**OpenAPI Contract:**
\`\`\`yaml
$CONTRACT_CONTENT
\`\`\`

**Project Configuration:**
- **Project Name**: $PROJECT_NAME
- **Group ID**: $GROUP_ID
- **Artifact ID**: $ARTIFACT_ID
- **Base Package**: $PACKAGE_NAME
- **Java Version**: $JAVA_VERSION
- **Spring Boot Version**: $SPRING_BOOT_VERSION
- **Build Tool**: $BUILD_TOOL
- **Output Path**: $OUTPUT_PATH

**Package Structure:**
\`\`\`
$PACKAGE_NAME/
├── ${ARTIFACT_ID}Application.java
├── controller/
├── service/
│   └── impl/
├── repository/
├── entity/
├── dto/
├── mapper/
├── exception/
└── config/
\`\`\`

**Instructions:**
$INSTRUCTIONS

**Expected Output:**
$EXPECTED_OUTPUT

Generate production-ready code that compiles and runs on first attempt!"

# Save prompt to file
PROMPT_FILE="$SCRIPT_DIR/last-java-generation-prompt.txt"
echo "$FULL_PROMPT" > "$PROMPT_FILE"

echo -e "${GREEN}✅ Java API generation prompt prepared${NC}"
echo ""
echo -e "${YELLOW}╔════════════════════════════════════════════════════════════╗${NC}"
echo -e "${YELLOW}║  NEXT STEPS:                                               ║${NC}"
echo -e "${YELLOW}╚════════════════════════════════════════════════════════════╝${NC}"
echo ""
echo -e "${CYAN}The Spring Boot API generation prompt has been prepared:${NC}"
echo -e "  ${NC}$PROMPT_FILE${NC}"
echo ""
echo -e "${CYAN}To generate your Spring Boot API:${NC}"
echo ""
echo -e "${YELLOW}1. View the prompt:${NC}"
echo -e "   ${NC}cat $PROMPT_FILE${NC}"
echo ""
echo -e "${YELLOW}2. Paste it to Claude Code or your AI assistant${NC}"
echo ""
echo -e "${YELLOW}3. The AI will generate complete Spring Boot project!${NC}"
echo ""
echo -e "${GREEN}After generation, build and run:${NC}"
echo -e "  ${NC}cd $OUTPUT_PATH${NC}"
echo -e "  ${NC}mvn clean install${NC}"
echo -e "  ${NC}mvn spring-boot:run${NC}"
echo ""
echo -e "${GREEN}Access Swagger UI at:${NC}"
echo -e "  ${NC}http://localhost:8080/swagger-ui.html${NC}"
echo ""

# Optional: Display the prompt
read -p "Would you like to display the prompt now? (y/n): " show_prompt
if [ "$show_prompt" = "y" ] || [ "$show_prompt" = "Y" ]; then
    echo ""
    echo -e "${CYAN}═══════════════════════════════════════════════════════════${NC}"
    echo -e "${CYAN}SPRING BOOT API GENERATION PROMPT${NC}"
    echo -e "${CYAN}═══════════════════════════════════════════════════════════${NC}"
    echo ""
    cat "$PROMPT_FILE"
    echo ""
    echo -e "${CYAN}═══════════════════════════════════════════════════════════${NC}"
fi

echo ""
echo -e "${GREEN}✨ Java API agent preparation complete!${NC}"
echo ""
