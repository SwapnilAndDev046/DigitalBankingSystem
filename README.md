# 🏦 Digital Banking System

> ⭐ **AI Note:** This README was created with AI assistance and carefully reviewed by the project author to accurately represent the implementation.

A production-inspired(INSPIRED) RESTful Digital Banking System built using **Spring Boot** that simulates core banking operations including customer onboarding, account creation, branch management, deposits, withdrawals, fund transfers, and transaction history.

The project follows a clean layered architecture (Controller → Service → Repository), implements proper DTO mapping, transaction management, exception handling, pagination, and **Flyway database migrations** to reflect real-world backend development practices.

---

# 🚀 Features

## 👤 Customer Management

- Create Customer
- Update Customer
- Delete Customer
- View Customer Details
- View All Customers
- Customer Pagination

---

## 🏦 Branch Management

- Create Branch
- Update Branch
- Delete Branch
- View Branch Details
- View All Branches

---

## 💳 Account Management

- Create Account
- Update Account
- Delete Account
- View All Accounts
- Get Account Details
- Check Account Balance
- Automatic Random Account Number Generation
- Account Status Management (Active / Blocked / Closed)

---

## 💸 Transaction Management

- Deposit Money
- Withdraw Money
- Transfer Money Between Accounts
- View Transaction History
- Transaction Reference Generation

---

## ⚙️ Business Rules

- Prevent transactions on Blocked or Closed accounts
- Validate account existence before every operation
- Validate sufficient balance before withdrawal and transfer
- Update account balance before recording transaction history
- Automatic rollback using `@Transactional`
- Transfer operation records transaction history for both sender and receiver

---

# 🛠 Tech Stack

- Java 17
- Spring Boot
- Spring MVC
- Spring Data JPA
- Hibernate
- PostgreSQL
- Flyway (Database Versioning & Migrations)
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
├── Enum
└── Resources
    └── db
        └── migration
```

---

# 🏗 Architecture

The project follows a layered architecture.

```
                Client
                   │
                   ▼
             REST Controller
                   │
                   ▼
               Service Layer
                   │
                   ▼
            Spring Data JPA
                   │
                   ▼
             PostgreSQL Database
```

This separation keeps the project modular, maintainable, and scalable.

---

# 🗄 Database Design

## Entities

- Customer
- Branch
- Account
- Transaction

---

## Entity Relationships

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

# 🛢 Database Migration (Production Inspired)

Instead of relying on Hibernate's automatic schema updates (`ddl-auto=update`), this project uses **Flyway** for database versioning and migrations.

### Benefits

- Version-controlled database schema
- Safe and repeatable migrations
- Production-ready database evolution
- Prevents accidental schema modifications
- Keeps database synchronized across environments

Migration scripts are stored under:

```
src/main/resources/db/migration
```

Example:

```
V1__Create_Customer_Table.sql

V2__Create_Branch_Table.sql

V3__Create_Account_Table.sql

V4__Create_Transaction_Table.sql
```

---

# 📌 REST APIs

## 👤 Customer APIs

| Method | Endpoint |
|---------|----------|
| POST | `/api/v1/customers` |
| GET | `/api/v1/customers/all` |
| GET | `/api/v1/customers/{id}` |
| PUT | `/api/v1/customers/{id}` |
| DELETE | `/api/v1/customers/{id}` |
| GET | `/api/v1/customers/pagination` |

---

## 🏦 Branch APIs

| Method | Endpoint |
|---------|----------|
| POST | `/api/v1/branches` |
| GET | `/api/v1/branches` |
| GET | `/api/v1/branches/{id}` |
| PUT | `/api/v1/branches/{id}` |
| DELETE | `/api/v1/branches/{id}` |

---

## 💳 Account APIs

| Method | Endpoint |
|---------|----------|
| POST | `/api/v1/accounts` |
| GET | `/api/v1/accounts` |
| GET | `/api/v1/accounts/account` |
| GET | `/api/v1/accounts/balance` |
| PUT | `/api/v1/accounts/{id}` |
| DELETE | `/api/v1/accounts/{id}` |

---

## 💸 Transaction APIs

| Method | Endpoint |
|---------|----------|
| POST | `/api/v1/transactions/deposit` |
| POST | `/api/v1/transactions/withdraw` |
| POST | `/api/v1/transactions/transfer` |
| GET | `/api/v1/transactions/account/history` |

---

# 🔄 Core Banking Workflows

## Deposit

```
Receive Request
      │
      ▼
Find Account
      │
Validate Account Status
      │
Validate Amount
      │
Update Balance
      │
Store Transaction
      │
Return Response
```

---

## Withdraw

```
Receive Request
      │
      ▼
Find Account
      │
Validate Account Status
      │
Check Available Balance
      │
Update Balance
      │
Store Transaction
      │
Return Response
```

---

## Transfer

```
Find Sender
      │
Find Receiver
      │
Validate Accounts
      │
Check Balance
      │
Debit Sender
      │
Credit Receiver
      │
Create Transaction History
      │
Return Response
```

---

# 📚 Spring Boot Concepts Demonstrated

- RESTful API Development
- Layered Architecture
- DTO Pattern
- ModelMapper
- Bean Validation
- Global Exception Handling
- Custom Exceptions
- Transaction Management (`@Transactional`)
- Spring Data JPA
- Hibernate ORM
- One-to-Many Mapping
- Many-to-One Mapping
- Pagination
- PostgreSQL Integration
- Flyway Database Migration

---

# ▶️ Getting Started

## 1. Clone Repository

```bash
git clone https://github.com/SwapnilAndDev046/DigitalBankingSystem
```

---

## 2. Navigate to Project

```bash
cd BankManagement
```

---

## 3. Configure Database

Update your `application.properties`

```properties
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
```

---

## 4. Flyway Migration

The database schema will be created automatically using Flyway migration scripts during application startup.

---

## 5. Run Application

Run

```
BankManagementApplication.java
```

Application starts on

```
http://localhost:8080
```

---

# 🧪 API Testing

The APIs were tested using

- Postman
- IntelliJ HTTP Client
- Thunder Client

---

## 📷 API Screenshots

<img width="1162" height="880" alt="Screenshot 2026-08-01 212233" src="https://github.com/user-attachments/assets/02008e2d-c580-44a5-ac3b-72660ba24847" />

<img width="1221" height="761" alt="Screenshot 2026-08-01 212251" src="https://github.com/user-attachments/assets/8b49cdb0-7479-45c7-b162-316bf6ac8680" />

<img width="1377" height="827" alt="Screenshot 2026-08-01 212311" src="https://github.com/user-attachments/assets/fb2e3efe-0908-4d2f-bc44-26eb0da8e2dc" />

<img width="1265" height="786" alt="Screenshot 2026-08-01 212319" src="https://github.com/user-attachments/assets/9a7f252c-5c07-4a92-9fb9-dd271fe8c574" />

<img width="1250" height="780" alt="Screenshot 2026-08-01 212343" src="https://github.com/user-attachments/assets/9c126fb6-c169-40dc-8358-6860afcca418" />

<img width="1656" height="850" alt="Screenshot 2026-08-01 212411" src="https://github.com/user-attachments/assets/893da16b-636a-4b88-b463-1a9d9adf3167" />

---

# 📈 Future Improvements

- Spring Security
- JWT Authentication
- Swagger / OpenAPI Documentation
- Docker Support
- Redis Caching
- Account Statement (Date Range)
- Daily Transaction Limit
- Interest Calculation
- Role-Based Authorization
- Audit Logging

---

# 👨‍💻 Author

**Swapnil**

Java • Spring Boot • Spring Data JPA • Hibernate • PostgreSQL • Flyway • REST APIs • Maven • ModelMapper

---

> ⭐ **AI Disclosure:** AI was used to assist in preparing this README. The project architecture, database design, business logic, implementation, debugging, testing, and validation were designed, implemented, and verified by the project author.