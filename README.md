# 🌾 Digital Procurement Platform for Agriculture & Food Production

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue.svg)](https://www.postgresql.org/)
[![License](https://img.shields.io/badge/License-Proprietary-red.svg)]()
[![Tests](https://img.shields.io/badge/Tests-39%20Passed-success.svg)]()

A comprehensive digital infrastructure enabling transparent market participation across farming and food-production sectors. This platform streamlines procurement processes, supplier management, bidding, and contract lifecycle management.

## 📋 Table of Contents

- [Features](#-features)
- [Architecture](#-architecture)
- [Technology Stack](#-technology-stack)
- [Getting Started](#-getting-started)
- [API Documentation](#-api-documentation)
- [Database Schema](#-database-schema)
- [Testing](#-testing)
- [Project Structure](#-project-structure)
- [Contributing](#-contributing)

## ✨ Features

### Core Functionality

- **🎯 Procurement Opportunities Management**
  - Create and publish procurement opportunities
  - Deadline validation and business rules
  - Category and region-based filtering
  - Version control with optimistic locking

- **💼 Supplier Registry**
  - Supplier registration and qualification
  - Email uniqueness validation
  - Certification expiry tracking
  - Regional supplier management

- **📝 Bid Submission & Management**
  - Secure bid submission with transaction IDs
  - Deadline enforcement
  - Supplier and opportunity-based filtering
  - Bid status tracking (SUBMITTED, UNDER_REVIEW, ACCEPTED, REJECTED)

- **📄 Contract Management**
  - Contract lifecycle management
  - Buyer and supplier tracking
  - Contract status workflow (DRAFT, ACTIVE, COMPLETED, CANCELLED)

### Technical Features

- ✅ **RESTful API** with comprehensive endpoints
- ✅ **Input Validation** using Jakarta Bean Validation
- ✅ **Exception Handling** with custom exceptions and global handler
- ✅ **DTO Pattern** for clean API contracts
- ✅ **Logging** with SLF4J for debugging and monitoring
- ✅ **API Documentation** with Swagger/OpenAPI
- ✅ **Security** with Spring Security (basic configuration)
- ✅ **Testing** with 39 unit and integration tests (100% pass rate)
- ✅ **Database Migration** with Hibernate auto-update
- ✅ **Business Rules** - Deadline validation, email uniqueness checks
- ✅ **Mapper Pattern** - Clean entity-DTO conversion

## 🏗 Architecture

```
┌─────────────────────────────────────────────────────────┐
│                     REST API Layer                       │
│              (Controllers + Swagger UI)                  │
└────────────────────┬────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────┐
│                   Service Layer                          │
│         (Business Logic + Validation)                    │
└────────────────────┬────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────┐
│                 Repository Layer                         │
│              (Spring Data JPA)                           │
└────────────────────┬────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────┐
│                PostgreSQL Database                       │
└─────────────────────────────────────────────────────────┘
```

### Design Patterns

- **DTO Pattern**: Separation of API contracts from domain models
- **Repository Pattern**: Data access abstraction
- **Service Layer Pattern**: Business logic encapsulation
- **Exception Handling Pattern**: Centralized error handling
- **Mapper Pattern**: Entity-DTO conversion

## 🛠 Technology Stack

### Backend
- **Java 17** - Programming language
- **Spring Boot 3.2.0** - Application framework
- **Spring Data JPA** - Data persistence
- **Spring Security** - Authentication & authorization
- **Spring Validation** - Input validation

### Database
- **PostgreSQL 16** - Primary database
- **Hibernate** - ORM framework

### API Documentation
- **Springdoc OpenAPI 3** - API documentation
- **Swagger UI** - Interactive API explorer

### Testing
- **JUnit 5** - Testing framework
- **Mockito** - Mocking framework
- **Spring Boot Test** - Integration testing
- **H2 Database** - In-memory testing database

### Build & Deployment
- **Maven 3.9+** - Build tool
- **Docker** - Containerization

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.8+
- Docker & Docker Compose (for PostgreSQL)
- 8GB RAM minimum

### Installation

1. **Clone the repository**
```bash
git clone https://github.com/SamandarOrifjonov/digital-agri-procurement-platform.git
cd digital-agri-procurement-platform
```

2. **Start PostgreSQL with Docker**
```bash
docker run -d \
  --name procurement-db \
  -e POSTGRES_DB=procurement_db \
  -e POSTGRES_USER=procurement_user \
  -e POSTGRES_PASSWORD=procurement_pass \
  -p 5432:5432 \
  postgres:16
```

3. **Build the project**
```bash
mvn clean install -f simple-pom.xml
```

4. **Run the application**
```bash
mvn spring-boot:run -f simple-pom.xml
```

The application will start on `http://localhost:8080`

### Quick Start with H2 (In-Memory Database)

For quick testing without Docker:

1. Update `src/main/resources/application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:h2:mem:procurement_db
    driver-class-name: org.h2.Driver
```

2. Run the application:
```bash
mvn spring-boot:run -f simple-pom.xml
```

## 📚 API Documentation

Once the application is running, access the interactive API documentation:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

### API Endpoints

#### Opportunities
```
POST   /api/opportunities              - Create new opportunity
POST   /api/opportunities/{id}/publish - Publish opportunity
GET    /api/opportunities              - Get all opportunities
GET    /api/opportunities/{id}         - Get opportunity by ID
GET    /api/opportunities/category/{category} - Filter by category
GET    /api/opportunities/region/{region}     - Filter by region
DELETE /api/opportunities/{id}         - Delete opportunity
```

#### Bids
```
POST   /api/bids                       - Submit new bid
GET    /api/bids                       - Get all bids
GET    /api/bids/{id}                  - Get bid by ID
GET    /api/bids/supplier/{supplierId} - Get bids by supplier
GET    /api/bids/opportunity/{opportunityId} - Get bids by opportunity
DELETE /api/bids/{id}                  - Delete bid
```

#### Suppliers
```
POST   /api/suppliers                  - Register new supplier
GET    /api/suppliers                  - Get all suppliers
GET    /api/suppliers/{id}             - Get supplier by ID
GET    /api/suppliers/region/{region}  - Get suppliers by region
PUT    /api/suppliers/{id}             - Update supplier
```

#### Contracts
```
POST   /api/contracts                  - Create new contract
GET    /api/contracts                  - Get all contracts
GET    /api/contracts/{id}             - Get contract by ID
GET    /api/contracts/buyer/{buyerId}  - Get contracts by buyer
GET    /api/contracts/supplier/{supplierId} - Get contracts by supplier
```

### Example Request

**Create Opportunity:**
```bash
curl -X POST http://localhost:8080/api/opportunities \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Wheat Purchase - 100 tons",
    "description": "High quality wheat needed for production",
    "buyerId": "buyer-123",
    "productCategory": "GRAINS",
    "region": "Tashkent",
    "minBudget": 50000,
    "maxBudget": 100000,
    "currency": "UZS",
    "submissionDeadline": "2026-03-01T00:00:00Z"
  }'
```

## 🗄 Database Schema

### Tables

- **opportunities** - Procurement opportunities
- **suppliers** - Registered suppliers
- **bids** - Bid submissions
- **contracts** - Contract records

### Entity Relationships

```
Opportunity (1) ──< (N) Bid
Supplier (1) ──< (N) Bid
Contract (N) ──> (1) Opportunity
Contract (N) ──> (1) Bid
```

### Key Features

- UUID primary keys for distributed systems
- Optimistic locking with version control
- Enum-based status management
- Timestamp tracking for audit trails
- Foreign key constraints for data integrity

## 🧪 Testing

The project includes comprehensive test coverage:

- **Unit Tests**: 26 tests for service layer
- **Integration Tests**: 11 tests for API endpoints
- **Mapper Tests**: 2 tests for DTO conversion

### Run Tests

```bash
# Run all tests
mvn test -f simple-pom.xml

# Run specific test class
mvn test -f simple-pom.xml -Dtest=OpportunityServiceTest

# Run with coverage report
mvn test jacoco:report -f simple-pom.xml
```

### Test Results

```
Tests run: 39, Failures: 0, Errors: 0, Skipped: 0
✅ OpportunityServiceTest: 9 tests
✅ BidServiceTest: 10 tests
✅ SupplierServiceTest: 7 tests
✅ OpportunityControllerIntegrationTest: 5 tests
✅ BidControllerIntegrationTest: 6 tests
✅ OpportunityMapperTest: 2 tests
```

## 📁 Project Structure

```
digital-procurement-platform/
├── src/
│   ├── main/
│   │   ├── java/com/agrifood/platform/
│   │   │   ├── config/              # Configuration classes
│   │   │   ├── controller/          # REST controllers
│   │   │   ├── domain/              # Entity models
│   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   ├── exception/           # Custom exceptions
│   │   │   ├── mapper/              # DTO-Entity mappers
│   │   │   ├── repository/          # Data repositories
│   │   │   └── service/             # Business logic
│   │   └── resources/
│   │       ├── application.yml      # Application config
│   │       └── schema.sql           # Database schema
│   └── test/
│       ├── java/                    # Test classes
│       └── resources/
│           └── application-test.yml # Test configuration
├── simple-pom.xml                   # Maven configuration
├── .gitignore                       # Git ignore rules
└── README.md                        # This file
```

## 🔒 Security

The application includes Spring Security with basic configuration:

- CSRF protection disabled for REST API
- All endpoints currently permit all (configurable)
- Ready for JWT authentication integration
- Password encryption support

**Note**: Update security configuration before production deployment.

## ✅ Implemented Features

- ✅ **RESTful API** - Complete CRUD operations for all entities
- ✅ **Exception Handling** - Custom exceptions with global handler
- ✅ **Input Validation** - Jakarta Bean Validation on all DTOs
- ✅ **DTO Pattern** - Clean separation between API and domain models
- ✅ **Logging** - SLF4J logging throughout the application
- ✅ **API Documentation** - Swagger/OpenAPI with interactive UI
- ✅ **Security Configuration** - Spring Security setup (ready for JWT)
- ✅ **Comprehensive Testing** - 39 unit and integration tests
- ✅ **Database Integration** - PostgreSQL with Hibernate ORM
- ✅ **Business Logic** - Deadline validation, email uniqueness, status workflows
- ✅ **Mapper Pattern** - Entity-DTO conversion with dedicated mappers

## 🚧 Future Enhancements

- [ ] JWT Authentication & Authorization
- [ ] Role-based Access Control (BUYER, SUPPLIER, ADMIN)
- [ ] File Upload for documents
- [ ] Email Notifications
- [ ] Pagination & Advanced Filtering
- [ ] Audit Logging
- [ ] Excel/PDF Report Generation
- [ ] Real-time Notifications with WebSocket
- [ ] Elasticsearch Integration
- [ ] Caching with Redis
- [ ] Rate Limiting
- [ ] API Versioning

## 📝 License

This project is proprietary software. All rights reserved.

## 👥 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📧 Contact

**Project Maintainer**: Samandar Orifjonov

**Repository**: [https://github.com/SamandarOrifjonov/digital-agri-procurement-platform](https://github.com/SamandarOrifjonov/digital-agri-procurement-platform)

---

⭐ If you find this project useful, please consider giving it a star!

**Built with ❤️ for the Agriculture & Food Production Industry**
