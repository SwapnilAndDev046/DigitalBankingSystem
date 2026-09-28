
# 🏦 Digital Banking System

> ⭐ **AI Note:** This README was prepared with AI assistance and reviewed by the project author to accurately represent the project's implementation.

A **production-inspired RESTful Digital Banking System** built using **Spring Boot** that simulates core banking operations including user registration, authentication, account creation, branch management, deposits, withdrawals, fund transfers, balance checking, and transaction history.

The project follows a clean layered architecture:

**Controller → Service → Repository → PostgreSQL**

It demonstrates real-world backend development concepts including **DTO-based API design, Spring Data JPA, Hibernate, Flyway database migrations, exception handling, transaction management, Spring Security, JWT authentication, role-based authorization, and authenticated-user ownership validation.**

---

# 🚀 Features

## 🔐 Authentication & Security

- User Registration
- User Login
- BCrypt password hashing
- JWT-based authentication
- Stateless authentication
- Spring Security integration
- Role-based authorization
- CUSTOMER and ADMIN roles
- Authenticated-user identification using `SecurityContextHolder`
- Account ownership validation
- Protected banking operations

### JWT Authentication

After successful login, the server generates a JWT.

Protected requests use:

```http
Authorization: Bearer <JWT_TOKEN>
````

The JWT authentication filter validates the token and establishes the authenticated user for the request.

---

# 👤 User Management

* User Registration
* User Login
* Update User Details
* Partial Update of User Details
* Delete User
* View All Users
* User Pagination

The application internally represents customers using the `AppUser` entity.

---

# 🏦 Branch Management

* Create Branch
* Update Branch
* Partial Update Branch
* Delete Branch
* View All Branches
* Branch-to-Account relationship

Branches contain information such as:

* Branch Name
* IFSC Code
* City
* State

---

# 💳 Account Management

* Create Account
* Create multiple accounts for the same customer
* View logged-in customer's accounts
* View all accounts
* Check account balance
* Update account status
* Delete account
* Automatic account number generation
* Account status management

Supported account states:

```text
ACTIVE
BLOCKED
CLOSED
```

## Account Ownership

Customers cannot provide another user's customer ID while creating an account.

The system identifies the authenticated customer from the JWT/Spring Security context and automatically associates the account with that user.

For account-specific operations:

```text
JWT
 │
 ▼
Authenticated User
 │
 ▼
Requested Account
 │
 ▼
Ownership Verification
 │
 ▼
Banking Operation
```

This prevents a customer from directly operating on another customer's account by simply providing another account number.

---

# 💸 Transaction Management

## Deposit

Customers can deposit money into their own accounts.

The system:

1. Identifies the authenticated customer
2. Finds the requested account
3. Verifies account ownership
4. Validates account status
5. Validates the transaction amount
6. Updates the account balance
7. Creates transaction history
8. Returns the transaction result

---

## Withdraw

Customers can withdraw money from their own accounts.

The system:

1. Identifies the authenticated customer
2. Finds the requested account
3. Verifies account ownership
4. Validates account status
5. Validates the transaction amount
6. Checks available balance
7. Updates the account balance
8. Records the transaction

---

## Fund Transfer

Customers can transfer money between accounts.

The system:

1. Identifies the authenticated sender
2. Finds the sender's account
3. Verifies sender ownership
4. Finds the receiver's account
5. Validates both accounts
6. Checks available balance
7. Debits the sender
8. Credits the receiver
9. Creates transaction history for both accounts
10. Returns a common transaction reference

---

## Transaction History

Customers can retrieve transaction history for accounts they own.

Transaction information includes:

* Transaction Type
* Amount
* Transaction Time
* Account
* Reference ID

---

# ⚙️ Business Rules

* JWT authentication is required for protected APIs.
* Customers can create accounts only for themselves.
* Customers can operate only on their own accounts.
* Customers cannot use another customer's account number for deposit or withdrawal.
* Customers can transfer money from their own account to another account.
* Blocked and closed accounts cannot perform transactions.
* Account existence is validated before operations.
* Account ownership is validated before customer account operations.
* Transaction amounts must be valid positive values.
* Withdrawal and transfer operations validate available balance.
* Account balance is updated before transaction history is recorded.
* Banking operations use `@Transactional` for atomic database operations.
* Failed transactional operations can be rolled back.
* Transfer operations record transaction history for both sender and receiver.
* Administrative endpoints are protected using role-based authorization.

---

# 🛠 Tech Stack

## Backend

* Java 17
* Spring Boot
* Spring MVC
* Spring Security
* Spring Data JPA
* Hibernate ORM
* PostgreSQL
* Flyway
* Maven

## Libraries

* ModelMapper
* Lombok
* Jakarta Validation
* JJWT

## API Testing

* Postman
* IntelliJ HTTP Client
* Thunder Client

---

# 🏗 Architecture

The project follows a layered backend architecture.

```text
                    Client
                      │
                      ▼
              Spring Security
                      │
                      ▼
                 JWT Filter
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
                Hibernate ORM
                      │
                      ▼
             PostgreSQL Database
```

This separation keeps the application modular, maintainable, and easier to extend.

---

# 📂 Project Structure

```text
src
│
├── main
│   ├── java
│   │   └── com/swapnil/bankmanagement
│   │
│   │       ├── Config
│   │       ├── Controller
│   │       ├── Dto
│   │       ├── Entity
│   │       ├── Enum
│   │       ├── Exception
│   │       ├── Repository
│   │       ├── Security
│   │       └── Service
│   │           └── Impl
│   │
│   └── resources
│       └── db
│           └── migrations
│
└── test
```

---

# 🔐 JWT Authentication Flow

The authentication process follows:

```text
Client
  │
  │ Email + Password
  ▼
Login API
  │
  ▼
AuthenticationManager
  │
  ▼
DaoAuthenticationProvider
  │
  ▼
CustomUserDetailsService
  │
  ▼
UserRepository
  │
  ▼
PasswordEncoder
  │
  ▼
Authentication Success
  │
  ▼
JwtService
  │
  ▼
JWT Token
```

The client then sends the JWT with protected requests:

```http
Authorization: Bearer <JWT_TOKEN>
```

The JWT filter:

1. Reads the `Authorization` header
2. Extracts the Bearer token
3. Extracts the username/email from the JWT
4. Loads the user
5. Validates the JWT
6. Creates an authenticated `Authentication` object
7. Stores it in `SecurityContextHolder`

---

# 🛡️ Authorization Model

The application currently uses two roles:

```text
ADMIN
CUSTOMER
```

## CUSTOMER

Customers can:

* Register
* Login
* Create their own accounts
* View their own accounts
* Check their account balance
* Deposit money
* Withdraw money
* Transfer money
* View transaction history for their accounts
* View branches
* Update their own user information

## ADMIN

Administrators can perform administrative operations such as:

* View all customers
* View customers using pagination
* View all accounts
* Manage branches
* Update account status
* Delete administrative resources

Banking transaction operations remain restricted to customers.

---

# 🗄️ Database Design

## Main Entities

```text
AppUser
Branch
Account
Transaction
```

---

## Entity Relationships

```text
AppUser (1)
    │
    │
    └──────────────< Account >──────────────(1) Branch
                           │
                           │
                           └──────────────< Transaction
```

### Relationship Overview

```text
One AppUser
     │
     └───────< Many Accounts


One Branch
     │
     └───────< Many Accounts


One Account
     │
     └───────< Many Transactions
```

This allows a single customer to maintain multiple bank accounts.

---

# 🛢️ Database Migrations with Flyway

Instead of relying on Hibernate automatic schema updates, the project uses **Flyway** for database versioning and migrations.

Database changes are stored as version-controlled migration scripts.

Migration files are located under:

```text
src/main/resources/db/migrations
```

Example migration files:

```text
V1__baseline.sql
V2__change_constraint.sql
V3__change_name_column_branch.sql
V4__rename_ifsc_column.sql
V5__add_column_app_user.sql
V6__drop_constraint_branch_city_state.sql
```

### Benefits of Flyway

* Version-controlled database schema
* Repeatable database migrations
* Controlled schema evolution
* Migration history
* Consistency across environments
* Prevents relying on automatic schema modification

---

# 📌 REST API Endpoints

## 🔐 Authentication APIs

| Method | Endpoint           | Access |
| ------ | ------------------ | ------ |
| POST   | `/api/v1/register` | Public |
| POST   | `/api/v1/login`    | Public |

---

# 👤 User APIs

| Method | Endpoint                       | Access   |
| ------ | ------------------------------ | -------- |
| GET    | `/api/v1/customers/all`        | ADMIN    |
| GET    | `/api/v1/customers/pagination` | ADMIN    |
| PUT    | `/api/v1/customers/edits/{id}` | CUSTOMER |
| PATCH  | `/api/v1/customers/edit/{id}`  | CUSTOMER |
| DELETE | `/api/v1/customers/{id}`       | ADMIN    |

---

# 🏦 Branch APIs

| Method | Endpoint                | Access           |
| ------ | ----------------------- | ---------------- |
| POST   | `/api/v1/branches`      | ADMIN            |
| GET    | `/api/v1/branches/all`  | CUSTOMER / ADMIN |
| PUT    | `/api/v1/branches/{id}` | ADMIN            |
| PATCH  | `/api/v1/branches/{id}` | ADMIN            |
| DELETE | `/api/v1/branches/{id}` | ADMIN            |

---

# 💳 Account APIs

| Method | Endpoint                                   | Access   |
| ------ | ------------------------------------------ | -------- |
| POST   | `/api/v1/accounts/create`                  | CUSTOMER |
| GET    | `/api/v1/accounts/my-accounts`             | CUSTOMER |
| GET    | `/api/v1/accounts/{accountNumber}/balance` | CUSTOMER |
| GET    | `/api/v1/accounts/all`                     | ADMIN    |
| PUT    | `/api/v1/accounts/{id}`                    | ADMIN    |
| DELETE | `/api/v1/accounts/{id}`                    | ADMIN    |

---

# 💸 Transaction APIs

| Method | Endpoint                                       | Access   |
| ------ | ---------------------------------------------- | -------- |
| POST   | `/api/v1/transactions/deposit`                 | CUSTOMER |
| POST   | `/api/v1/transactions/withdraw`                | CUSTOMER |
| POST   | `/api/v1/transactions/transfer`                | CUSTOMER |
| GET    | `/api/v1/transactions/{accountNumber}/history` | CUSTOMER |

---

# 🔄 Core Banking Workflows

## Account Creation

```text
Customer Login
      │
      ▼
JWT Authentication
      │
      ▼
Create Account Request
      │
      ▼
Identify Current User
      │
      ▼
Validate Branch
      │
      ▼
Generate Account Number
      │
      ▼
Create Account
      │
      ▼
Save to PostgreSQL
```

---

## Deposit

```text
JWT Token
    │
    ▼
Identify Customer
    │
    ▼
Find Account
    │
    ▼
Verify Ownership
    │
    ▼
Validate Account Status
    │
    ▼
Validate Amount
    │
    ▼
Update Balance
    │
    ▼
Create Transaction
```

---

## Withdraw

```text
JWT Token
    │
    ▼
Identify Customer
    │
    ▼
Find Account
    │
    ▼
Verify Ownership
    │
    ▼
Validate Account Status
    │
    ▼
Check Available Balance
    │
    ▼
Update Balance
    │
    ▼
Create Transaction
```

---

## Transfer

```text
JWT Token
      │
      ▼
Identify Sender
      │
      ▼
Find Sender Account
      │
      ▼
Verify Sender Ownership
      │
      ▼
Find Receiver Account
      │
      ▼
Validate Accounts
      │
      ▼
Check Balance
      │
      ▼
Debit Sender
      │
      ▼
Credit Receiver
      │
      ▼
Create Both Transactions
      │
      ▼
Return Reference ID
```

---

# 📚 Spring Boot & Backend Concepts Demonstrated

* RESTful API Development
* Layered Architecture
* Dependency Injection
* Spring IoC
* Spring MVC
* DTO Pattern
* Entity–DTO Mapping
* ModelMapper
* Bean Validation
* Custom Exceptions
* Global Exception Handling
* Spring Data JPA
* Hibernate ORM
* Repository Query Derivation
* One-to-Many Relationships
* Many-to-One Relationships
* Pagination
* PostgreSQL Integration
* Flyway Database Migration
* Transaction Management
* `@Transactional`
* Spring Security
* Authentication
* Authorization
* `AuthenticationManager`
* `AuthenticationProvider`
* `UserDetailsService`
* BCrypt Password Hashing
* JWT Generation
* JWT Validation
* `OncePerRequestFilter`
* `SecurityContextHolder`
* Stateless Authentication
* Role-Based Access Control
* Authenticated-User Ownership Validation

---

# 🧪 API Testing

The APIs were tested using:

* Postman
* IntelliJ HTTP Client
* Thunder Client

Authentication-protected APIs were tested using JWT Bearer tokens.

Example:

```http
Authorization: Bearer <JWT_TOKEN>
```

---

# 📷 API Screenshots

> Add updated screenshots showing authentication, JWT-protected requests, account creation, deposits, withdrawals, transfers, and transaction history.

Example:


![Login API](path/to/login-screenshot.png)

![Account Creation](path/to/account-screenshot.png)

![Deposit API](path/to/deposit-screenshot.png)

![Withdrawal API](path/to/withdrawal-screenshot.png)

![Transfer API](path/to/transfer-screenshot.png)

![Transaction History](path/to/transaction-history-screenshot.png)

<img width="410" height="682" alt="image" src="https://github.com/user-attachments/assets/49d6a93a-168f-4320-9e57-637bf4fbf718" />

<img width="360" height="457" alt="image" src="https://github.com/user-attachments/assets/f0673fe3-dfa4-48fd-b8df-383051d5d14c" />



---

# ▶️ Getting Started

## 1. Clone Repository

```bash
git clone https://github.com/SwapnilAndDev046/DigitalBankingSystem.git
```

---

## 2. Navigate to Project

```bash
cd DigitalBankingSystem
```

---

## 3. Configure PostgreSQL

Create a PostgreSQL database and configure the application.

Example:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/BankDB
spring.datasource.username=your_username
spring.datasource.password=your_password
```

---

## 4. Configure JWT Secret

Configure the JWT signing secret using an environment variable or another secure configuration mechanism.

Example:

```properties
jwt.secretKey=${JWT_SECRET}
```

> **Do not commit real database credentials or JWT secrets to GitHub.**

---

## 5. Run the Application

Run:

```text
BankManagementApplication.java
```

Or use Maven:

```bash
mvn spring-boot:run
```

The application starts on:

```text
http://localhost:8080
```

---

# 🗃️ Flyway Migration

When the application starts, Flyway automatically executes pending database migrations.

Migration files are located at:

```text
src/main/resources/db/migrations
```

The database schema is then validated by Hibernate.

---

# 🧪 Example API Flow

A typical customer workflow:

```text
1. Register
      ↓
2. Login
      ↓
3. Receive JWT
      ↓
4. Send JWT with protected requests
      ↓
5. Create Account
      ↓
6. View My Accounts
      ↓
7. Deposit / Withdraw
      ↓
8. Transfer Money
      ↓
9. View Transaction History
```

---

# 🔭 Future Improvements

The project can be extended with the following features:

* React Frontend
* Full-stack integration between React and Spring Boot REST APIs
* Google OAuth2 Login
* Refresh Token mechanism
* Swagger / OpenAPI Documentation
* Docker Support
* Account Statements with Date Range Filtering
* Daily Transaction Limits
* Interest Calculation
* Audit Logging
* Automated Integration Testing
* Testcontainers
* Production Deployment

---

# 🎯 Project Goals

This project was developed to practice and demonstrate real-world backend development concepts using the Java and Spring ecosystem.

The overall technology flow is:

```text
Java
  ↓
Spring Boot
  ↓
REST APIs
  ↓
Spring Data JPA / Hibernate
  ↓
PostgreSQL
  ↓
Flyway
  ↓
Spring Security
  ↓
JWT Authentication
  ↓
Role-Based Authorization
  ↓
Account Ownership Validation
```

The primary focus is understanding how these technologies work together to build a structured and secure backend application.

---

# 👨‍💻 Author

**Swapnil**

Java • Spring Boot • Spring Security • JWT • Spring Data JPA • Hibernate • PostgreSQL • Flyway • REST APIs • Maven

---

> ⭐ **AI Disclosure:** AI was used to assist with documentation, explanations, debugging guidance, and README preparation. The project architecture, database design, business logic, implementation, testing, and validation were developed and reviewed by the project author.

```
```
