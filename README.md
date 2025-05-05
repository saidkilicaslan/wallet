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
