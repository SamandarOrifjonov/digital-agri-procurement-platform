# Digital Procurement Platform for Agriculture & Food Production

A sector-wide digital infrastructure enabling transparent market participation across farming and food-production sectors.

## Architecture

This platform uses a **hybrid microservices and event-driven architecture** with the following core services:

- **Procurement Service**: Manages procurement opportunities and bid submissions
- **Supplier Registry Service**: Handles supplier qualifications and compliance
- **Contract Management Service**: Manages contract lifecycle and amendments
- **Market Data Service**: Aggregates and serves market demand data
- **Compliance & Reporting Service**: Generates regulatory reports and audit trails
- **API Gateway**: Single entry point for external clients
- **IAM Service**: Authentication and authorization

## Technology Stack

- **Language**: Java 17
- **Framework**: Spring Boot 3.2.0, Spring Cloud
- **Database**: PostgreSQL 16 (transactional), TimescaleDB (time-series)
- **Cache**: Redis 7
- **Message Broker**: Apache Kafka 3.6
- **Search**: Elasticsearch 8.11
- **Monitoring**: Prometheus, Grafana, Jaeger
- **Resilience**: Resilience4j

## Prerequisites

- Java 17+
- Maven 3.8+
- Docker & Docker Compose
- 8GB RAM minimum

## Quick Start

### 1. Start Infrastructure Services

```bash
docker-compose up -d
```

This starts PostgreSQL, Redis, Kafka, Elasticsearch, TimescaleDB, Jaeger, Prometheus, and Grafana.

### 2. Build the Project

```bash
mvn clean install
```

### 3. Run Services

```bash
# Start API Gateway
cd api-gateway && mvn spring-boot:run

# Start Procurement Service
cd procurement-service && mvn spring-boot:run

# Start other services as needed
```

## Service Ports

- API Gateway: 8080
- Procurement Service: 8081
- Supplier Registry Service: 8082
- Contract Management Service: 8083
- Market Data Service: 8084
- Compliance Service: 8085
- Notification Service: 8086
- Search Service: 8087
- Analytics Service: 8088
- IAM Service: 8089

## Infrastructure Ports

- PostgreSQL: 5432
- TimescaleDB: 5433
- Redis: 6379
- Kafka: 9092
- Elasticsearch: 9200
- Jaeger UI: 16686
- Prometheus: 9090
- Grafana: 3000

## Development

### Running Tests

```bash
# Unit tests
mvn test

# Integration tests
mvn verify -P integration-tests

# Property-based tests
mvn test -P property-tests
```

### Code Quality

```bash
# Run checkstyle
mvn checkstyle:check

# Run spotbugs
mvn spotbugs:check
```

## API Documentation

Once services are running, access API documentation at:
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI Spec: http://localhost:8080/v3/api-docs

## Monitoring

- **Grafana Dashboards**: http://localhost:3000 (admin/admin)
- **Jaeger Tracing**: http://localhost:16686
- **Prometheus**: http://localhost:9090

## Architecture Decisions

See [Architecture Decision Records](docs/adr/) for detailed design decisions.

## Contributing

1. Create a feature branch
2. Make your changes
3. Run tests and quality checks
4. Submit a pull request

## License

Proprietary - All rights reserved
