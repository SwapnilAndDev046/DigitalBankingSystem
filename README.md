# 🏦 Bank Management System

> ⭐ **AI Note:** This README was created with AI assistance and carefully reviewed by the project author to accurately represent the implementation.

A backend RESTful Bank Management System developed using **Java**, **Spring Boot**, **Spring Data JPA**, and **PostgreSQL**. The project simulates core banking operations such as customer management, account creation, deposits, withdrawals, fund transfers, and transaction history while following a layered architecture and REST API design.

---

## 🚀 Features

### 👤 Customer Management
- Create Customer
- Update Customer
- Delete Customer
- View All Customers
- Customer Pagination

### 🏦 Branch Management
- Create Branch
- Update Branch
- Delete Branch
- View All Branches

### 💳 Account Management
- Create Account
- Update Account
- Delete Account
- View All Accounts
- Get Account Details
- Check Account Balance
- Automatic Random Account Number Generation

### 💸 Transaction Management
- Deposit Money
- Withdraw Money
- Transfer Money Between Accounts
- View Transaction History
- Transaction Reference Generation

### ⚙️ Business Rules
- Prevent transactions on Blocked or Closed accounts
- Validate account existence before performing operations
- Update account balance before recording transaction history
- Database transactions handled using `@Transactional`

---

# 🛠 Tech Stack

- Java 17
- Spring Boot
- Spring MVC
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- ModelMapper
- Lombok
- Jakarta Validation

---

# 📂 Project Structure

```
src
│
├── Controller
├── Service
│   └── Impl
├── Repository
├── Entity
├── DTO
├── Exception
├── Config
└── Enum
```

The project follows a standard layered architecture:

```
Client
   │
   ▼
Controller
   │
   ▼
Service
   │
   ▼
Repository
   │
   ▼
PostgreSQL Database
```

---

# 🗄 Database Design

## Entities

- Customer
- Branch
- Account
- Transaction

### Relationships

```
Customer (1)
      │
      │
      └───────────────< Account >───────────────(1) Branch
                              │
                              │
                              └───────────────< Transaction
```

---

# 📌 REST APIs

## Customer APIs

| Method | Endpoint |
|---------|----------|
| POST | `/api/v1/customers` |
| GET | `/api/v1/customers/all` |
| PUT | `/api/v1/customers/{id}` |
| DELETE | `/api/v1/customers/{id}` |
| GET | `/api/v1/customers/pagination` |

---

## Branch APIs

| Method | Endpoint |
|---------|----------|
| POST | `/api/v1/branches` |
| GET | `/api/v1/branches` |
| PUT | `/api/v1/branches/{id}` |
| DELETE | `/api/v1/branches/{id}` |

---

## Account APIs

| Method | Endpoint |
|---------|----------|
| POST | `/api/v1/accounts` |
| GET | `/api/v1/accounts` |
| GET | `/api/v1/accounts/account` |
| GET | `/api/v1/accounts/balance` |
| PUT | `/api/v1/accounts/{id}` |
| DELETE | `/api/v1/accounts/{id}` |

---

## Transaction APIs

| Method | Endpoint |
|---------|----------|
| POST | `/api/v1/transactions/deposit` |
| POST | `/api/v1/transactions/withdraw` |
| POST | `/api/v1/transactions/transfer` |
| GET | `/api/v1/transactions/account/history` |

---

# 🔄 Transaction Workflow

### Deposit

```
Request
   │
Find Account
   │
Validate Account Status
   │
Update Balance
   │
Create Transaction Record
   │
Return Response
```

---

### Withdraw

```
Request
   │
Find Account
   │
Validate Balance
   │
Update Balance
   │
Store Transaction
   │
Return Response
```

---

### Transfer

```
Sender Account
        │
        ▼
Validate Sender
        │
Validate Receiver
        │
Check Balance
        │
Debit Sender
        │
Credit Receiver
        │
Create Transaction History
```

---

# 📚 Spring Boot Concepts Used

- RESTful API Development
- Layered Architecture
- DTO Pattern
- Entity Relationships
- JPA & Hibernate
- One-to-Many Mapping
- Many-to-One Mapping
- ModelMapper
- Bean Validation
- Global Exception Handling
- Transaction Management (`@Transactional`)
- Pagination
- PostgreSQL Integration

---

# ▶️ Getting Started

### 1. Clone Repository

```bash
git clone https://github.com/<your-username>/<repository-name>.git
```

### 2. Navigate to Project

```bash
cd BankManagement
```

### 3. Configure Database

Update `application.properties`

```
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
```

### 4. Run the Application

```
Run BankManagementApplication.java
```

Application runs at

```
http://localhost:8080
```

---

# 🧪 API Testing

The APIs can be tested using:

- Postman
- IntelliJ HTTP Client
- Thunder Client

> 📷 **Screenshots:** 
<img width="1656" height="850" alt="Screenshot 2026-08-01 212411" src="https://github.com/user-attachments/assets/a17f3270-578e-4fe5-a505-4b038f28e604" />



---

# 📈 Future Improvements

- Spring Security
- JWT Authentication
- Swagger/OpenAPI Documentation
- Docker Support
- Account Statements (Date Range)
- Daily Transaction Limit
- Interest Calculation
- Role-Based Authorization

---

# 👨‍💻 Author

**Swapnil**

Java Backend Developer | Spring Boot | PostgreSQL | REST APIs

---

> ⭐ **AI Disclosure:** AI was used to assist in preparing this README. The project architecture, business logic, implementation, debugging, and testing were designed and developed by the author.
