## 🚚 Load Booking System

A Spring Boot application for managing loads and bookings for a transport company. Supports operations with status transitions, filtering, pagination, and API documentation via Swagger.

## 🛠️ Tech Stack
- Java 17
- Spring Boot
- PostgreSQL
- JPA & Hibernate
- Spring Validation
- Swagger (springdoc-openapi)
- JUnit & Mockito


## ⚙️ Setup Instructions

### Prerequisites
- Java 17+
- Maven
- PostgreSQL

### 1. Clone the repository
```bash
git clone https://github.com/your-username/cargopro-task.git
cd cargopro-task
```
### 2. Configure DB
- spring.datasource.url=jdbc:postgresql://localhost:5432/load_booking
- spring.datasource.username=your_username
- spring.datasource.password=your_password

### 3. Run the application 
- ./mvnw spring-boot:run

### 4. Swagger UI 
- Access Swagger: http://localhost:8080/swagger-ui.html

### 5.OpenAPI Docs:
http://localhost:8080/v3/api-docs

#### 6. **API Endpoints**
```md
## 📘 API Endpoints

### Load Management
- `POST   /load` → Create new load
- `GET    /load?shipperId=&truckType=&status=&page=1&size=10`
- `GET    /load/{loadId}`
- `PUT    /load/{loadId}`
- `DELETE /load/{loadId}`

### Booking Management
- `POST   /booking`
- `GET    /booking?loadId=&transporterId=&status=`
- `GET    /booking/{bookingId}`
- `PUT    /booking/{bookingId}`
- `DELETE /booking/{bookingId}`

```

## 🧠 Assumptions
- A load is `POSTED` by default.
- A load becomes `BOOKED` if at least one booking exists.
- If all bookings are deleted/rejected, load reverts to `POSTED`.
- A load cannot accept bookings if `CANCELLED`.
- Booking status can be `PENDING`, `ACCEPTED`, or `REJECTED`.

#### Note
This project is implemented as per the assignment tasks and all required rules and endpoints are complete.

