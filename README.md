# 💼 Wallet Application - Developer Documentation

This document describes how to use and integrate the **Wallet Application**, which supports two user roles and JWT-based authentication.

---

## 📌 Purpose

The application is a simple wallet service that allows:

- **EMPLOYEES** to perform operations on any customer
- **CUSTOMERS** to perform operations **only on their own data**

JWT tokens are used for secure, stateless authentication.

---

## 🛠️ Technologies Used

- Java 17+
- Spring Boot
- Spring Security
- JSON Web Token (JJWT)
- H2 Database (in-memory)
- Swagger UI (OpenAPI documentation)

---

## 🧑‍💼 User Roles

| Role      | Description                                   |
|-----------|-----------------------------------------------|
| EMPLOYEE  | Can access and manage all customers           |
| CUSTOMER  | Can access and manage only their own account  |

---

## 🚀 Running the Application

### 🧪 Local Development

Run the app using Maven:

```bash
mvn spring-boot:run
```


## 🧪 Running in Test Environment

The application is configured to use an in-memory **H2** database for testing purposes. Here's how to run it in a test environment:

- **Database:**  
  The app connects to an in-memory H2 database (`jdbc:h2:mem:wallet`) with the following credentials:
  - **Username:** `sample`
  - **Password:** `sample`

- **H2 Console:**  
  Available at: [http://localhost:8080/api/v1/h2-console](http://localhost:8080/api/v1/h2-console)  
  JDBC URL to use:
  jdbc:h2:mem:wallet

- **Application Context Path:**  
All API endpoints are served under the context path `/api/v1`. Example routes:
- Customer Login: `POST /api/v1/auth/login/customer`
- Swagger UI: [http://localhost:8080/api/v1/swagger-ui/index.html](http://localhost:8080/api/v1/swagger-ui/index.html)

- **Logging:**  
Spring MVC validation is enabled and set to `DEBUG` level to help debug input validation issues during testing.
