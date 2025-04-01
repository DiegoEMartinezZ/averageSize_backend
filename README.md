# Average Size API

Average Size is a RESTful API service for managing the creation of short links and QR codes, built with Spring Boot. This repository contains the backend service only. The frontend application is developed with React + Tailwind.
## Technologies

### Backend (This Repository)

- **Java 17**
- **Spring Boot 3.x**
- **MySQL** (via Railway)
- **Spring Data JPA**
- **Spring Security**
- **Maven**

### Frontend (Pending...)

- **React**
- **JavaScript**
- **Tailwind**

## Project Structure

The project follows a standard Spring Boot architecture:

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── clicknmeet/
│   │           ├── controller/
│   │           ├── entities/
│   │           ├── repository/
│   │           ├── service/
│   │           ├── config/
│   │           └── DemoApplication.java
│   └── resources/
│       ├── application.properties
│       ├── application-dev.properties
│       └── application-prod.properties
└── test/
```
## Initial Setup

1. **Clone the repository**
```bash
git clone https://github.com/DiegoEMartinezZ/averageSize_backend
cd averageSize_backend
```

2. **Configure application.properties**

The application uses different property files for different environments:
- `application.properties`: Common settings
- `application-dev.properties`: Development settings
- `application-prod.properties`: Production settings

3. **Database Configuration**

For local development, configure your database in `application-dev.properties`:
```properties
# Development Database
spring.datasource.url=jdbc:mysql://localhost:3306/averagesize
spring.datasource.username=[YOUR USERNAME]
spring.datasource.password=[YOUR PASSWORD]
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

For production (Render), the database is configured using environment variables:

```properties
spring.datasource.url=${JDBC_DATABASE_URL}
spring.datasource.username=${MYSQL_USER}
spring.datasource.password=${MYSQL_PASSWORD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
# Production settings
spring.jpa.show-sql=false
spring.jpa.hibernate.ddl-auto=update
```

4. **Build the application**

```bash
mvn clean install
```

5. **Run the application locally**

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

The API will be available at `http://localhost:8080/api`

## API Health Check

The application includes an endpoint to verify database connectivity:
```
GET /api/health/database
```
## Deployment

This application is configured for deployment to Render:
1. Set up a MySQL database on Render
2. Configure the following environment variables in Render:
    - `JDBC_DATABASE_URL`: Full JDBC URL to your Render MySQL database
    - `MYSQL_USER`: Database username
    - `MYSQL_PASSWORD`: Database password
    - `SPRING_PROFILES_ACTIVE`: Set to `prod`

## Git Workflow

### Main Branches
- `main`: Stable version ready for production
- `dev`: Development branch where new features are integrated

### Working Branches
- `feature/feature-name`: For new functionality
- `fix/fix-name`: For bug fixes
