# Hospital Management System (HMS)

## Overview
The **Hospital Management System (HMS)** is a backend–frontend application developed as part of the **Unified Citizen Information Access Portal (UCIAP)** ecosystem. It manages citizen health-related data and securely exposes authorized medical information to UCIAP when permitted.

This system ensures that sensitive medical records are accessed only by authorized parties under strict authentication and consent rules.

---

## Key Features
- Manage patient health records and medical reports
- Secure RESTful APIs for data access
- Role-based access control
- Integration with UCIAP for verified data sharing
- Audit-friendly data handling

---

## Technology Stack
### Backend
- Spring Boot (Java)
- Spring Security
- RESTful APIs
- PostgreSQL / MySQL

### Frontend
- React

### Security
- OAuth 2.0 / OpenID Connect
- JWT-based authentication
- Keycloak (via UCIAP)

---

## System Integration
- Acts as a **data provider** within the UCIAP architecture
- Communicates with UCIAP via secure APIs
- Does NOT expose data directly to external institutions
- All access is validated using Keycloak-issued JWT tokens

---

## Developer
- **Malisa Abhisek**

---

## Purpose
This project was developed for academic purposes to demonstrate:
- Secure healthcare data management
- API-based system integration
- Real-world authentication and authorization practices

---

## License
This project is intended for educational use only.

---

## Local Development

### Prerequisites
- Java 17+
- Maven (or use `./mvnw`)
- MySQL

### Configuration
Update `hospital-management-backend/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hospital_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=hospital_user
spring.datasource.password=hospital123
server.port=8080
ai.service.url=http://localhost:8001
```

### Run
```bash
./mvnw spring-boot:run
```

## AI Analysis Integration
The backend exposes:
- `POST /api/v1/analysis` (multipart)

It forwards uploads to the FastAPI service running at `ai.service.url` and returns the AI summary + specialist recommendations to the UI.
# Nexus-Health-Backend
