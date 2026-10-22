# 🏦 MiniBank — Java Banking Application

**MiniBank** is a Java-based console banking application developed as a semester project for the **Object-Oriented Programming (OOP)** course.

The project demonstrates how fundamental and advanced Java concepts can be combined to develop a structured banking application. It progressively incorporates object-oriented programming, account management, exception handling, collections, generic repositories, transaction processing, multithreading, synchronization, persistence, and reporting.

The application is developed for academic learning and demonstrates banking operations in a simplified environment.

---

## 📚 Project Information

| Information | Details |
|---|---|
| **Project Name** | MiniBank |
| **Course** | Object-Oriented Programming |
| **Subject Code** | CEUE203 |
| **Language** | Java |
| **IDE** | Visual Studio Code |
| **Application Type** | Console-Based Banking System |
| **Package Name** | `MiniBank` |
| **Project Status** | Under Development |

---

## 🎯 Objectives

The main objective of MiniBank is to apply the concepts learned throughout the OOP course by developing a single banking application.

The project demonstrates:

- Classes and objects
- Encapsulation, inheritance, abstraction, and polymorphism
- Interfaces, enums, and records
- Collections Framework
- Generic programming
- Input validation
- Custom exception handling
- Banking transactions
- Multithreading and synchronization
- Concurrent transaction processing
- File-based persistence
- Transaction history and reporting
- Repository and service-layer design
- Unit testing and application packaging

---

## ✨ Features

### 👤 Customer Management

- Create and manage customer information.
- Store customer details.
- Generate customer identifiers.
- Maintain customer address information where supported by the relevant classes.

### 🏦 Account Management

MiniBank supports three types of bank accounts:

- **Savings Account**
- **Current Account**
- **Fixed Deposit Account**

Each account type implements its own interest-rate and withdrawal rules through inheritance and polymorphism.

### 💰 Banking Operations

- Open a bank account.
- Deposit money.
- Withdraw money.
- Transfer money between accounts.
- Display account details.
- Check account balances.
- Generate account statements.
- Display accounts sorted by balance.

### 🔐 Input Validation

The application includes validation for:

- Mobile numbers
- Email addresses
- PAN numbers
- IFSC codes
- Transaction amounts
- Account-related information

Validation helps prevent invalid input from being processed by the banking application.

### ⚠️ Exception Handling

Custom exceptions are used to handle banking errors, including:

- Account not found
- Insufficient funds
- Invalid transaction amount
- Other banking-related errors

This helps separate error handling from normal application execution.

### 🗃️ Collections Framework

The project uses Java collections to manage banking data and transaction history.

Examples include:

- `HashMap` for account storage and lookup
- `ArrayList` for transaction history
- `Comparator` for sorting accounts
- Collection-based processing of account records

### 🧩 Generic Repository

The project introduces a generic `Repository<T>` to demonstrate reusable data-access operations.

The repository supports operations such as:

- Saving objects
- Finding objects by ID
- Retrieving all stored objects
- Deleting objects
- Saving multiple objects
- Calculating averages where applicable

The repository uses the project's `@Id` annotation to identify objects by their IDs.

### 🧵 Multithreading and Concurrency

MiniBank includes demonstrations of concurrent banking operations.

Features include:

- Multiple threads accessing shared account data
- Race-condition demonstrations
- Synchronized account operations
- Concurrent deposits and withdrawals
- `Thread.join()` for coordinating thread execution
- `ExecutorService` for managing transaction tasks
- Producer-consumer demonstration
- Deadlock demonstration
- Transaction processing using `TransactionProcessor`

These practicals help demonstrate the importance of thread safety when multiple operations access shared resources.

### 💾 File-Based Persistence

The application includes file-based account persistence.

The existing implementation supports:

- Saving account objects using Java serialization
- Reloading previously saved account data
- Storing account data under `MiniBank/data/`
- Maintaining transaction logs

The intended next improvement is to load persisted accounts automatically at application startup and save them automatically when the application exits.

### 📊 Transaction History and Reporting

The project includes transaction logging and end-of-day reporting.

Current reporting functionality includes:

- Recording transaction details
- Maintaining transaction logs
- Calculating deposit and withdrawal totals from logged transactions
- Generating an end-of-day report
- Saving the report to a file

The project also includes account sorting by balance.

### 🛠️ Service Layer — Practical 14

The project is being extended with a `BankService` class to centralize banking operations.

The intended service-layer responsibilities include:

- Opening accounts
- Depositing money
- Withdrawing money
- Transferring money
- Generating account statements
- Listing accounts
- Integrating account validation and repository operations

The current development stage includes the creation and successful compilation of `BankService.java`. Further integration with the existing menu and completion of the remaining Practical 14 requirements are still in progress.

---

## 🧱 Project Structure

The following structure represents the main project organization. Additional classes and files may exist as the practicals progress.

```text
D26IT111_OOP_Practicals/
│
└── MiniBank/
    ├── README.md
    ├── MiniBank.java
    │
    ├── model/
    │   ├── Account.java
    │   ├── Customer.java
    │   ├── SavingsAccount.java
    │   ├── CurrentAccount.java
    │   ├── FixedDepositAccount.java
    │   ├── Command.java
    │   ├── TransactionType.java
    │   ├── InterestBearing.java
    │   ├── Transactable.java
    │   ├── Premium.java
    │   └── ...
    │
    ├── service/
    │   └── BankService.java
    │
    ├── store/
    │   ├── Repository.java
    │   └── AccountStore.java
    │
    ├── concurrency/
    │   ├── TransactionProcessor.java
    │   ├── DepositTask.java
    │   ├── WithdrawTask.java
    │   └── ...
    │
    ├── util/
    │   ├── Validator.java
    │   ├── StatementFormatter.java
    │   ├── WithdrawRule.java
    │   └── ...
    │
    ├── exception/
    │   ├── BankException.java
    │   ├── AccountNotFoundException.java
    │   ├── InsufficientFundsException.java
    │   ├── InvalidAmountException.java
    │   └── ...
    │
    └── data/
        ├── accounts.dat
        ├── logs/
        │   └── transactions.txt
        └── end-of-day-report.txt
```

**Note:** The diagram is a documentation overview. Actual file names and locations should match the files present in your repository.

---

## 🧩 Object-Oriented Programming Concepts

### 1. Encapsulation

Encapsulation protects an object's internal state by keeping fields private and providing appropriate methods for accessing or modifying them.

For example, account information is managed through the `Account` class rather than by directly accessing its internal fields.

### 2. Inheritance

Inheritance allows specialized account classes to reuse common functionality from the base `Account` class.

```text
                 Account
                    |
          +---------+---------+
          |         |         |
       Savings   Current   FixedDeposit
       Account   Account      Account
```

### 3. Abstraction

The abstract `Account` class defines common banking behavior while allowing individual account types to provide their own implementations of account-specific rules.

Examples include:

- Interest rates
- Withdrawal eligibility

### 4. Polymorphism

Different account types can be referenced through the common `Account` type.

```java
Account[] accounts;
```

This allows common operations to work with different account implementations through dynamic method dispatch.

### 5. Interfaces

Interfaces define capabilities that can be implemented by appropriate classes.

Examples include:

- `InterestBearing`
- `Transactable`
- `Premium`

### 6. Records

Java records provide a concise way to represent data-oriented objects.

Example:

```java
record Command(TransactionType type, String accountNumber, long amount) {}
```

### 7. Enums

Enums represent a fixed set of named values.

Example:

```java
enum TransactionType {
    DEPOSIT,
    WITHDRAW,
    TRANSFER
}
```

### 8. Exception Handling

Custom checked exceptions provide structured handling for banking errors such as insufficient funds, invalid amounts, and missing accounts.

### 9. Collections

Java collections support account storage, transaction history, searching, and sorting.

The `AccountStore` class uses collections to organize accounts and record transaction history.

### 10. Generics

The generic `Repository<T>` demonstrates how a reusable class can perform common data-management operations on different object types.

### 11. Multithreading and Synchronization

Multithreading demonstrates concurrent task execution. Synchronization helps prevent race conditions when multiple threads modify shared account data.

### 12. Service-Layer Design

The `BankService` class is being introduced to separate banking operations from menu handling and improve the organization and maintainability of the application.

---

## 💳 Account Types

### Savings Account

A Savings Account is intended for regular savings and follows its configured minimum-balance and withdrawal rules.

```text
Interest Rate: 4.0%
Withdrawal: Subject to the account's minimum-balance rules
```

### Current Account

A Current Account supports transactions subject to its configured overdraft limit.

```text
Interest Rate: 0%
Withdrawal: Subject to the account's overdraft rules
```

### Fixed Deposit Account

A Fixed Deposit Account provides interest according to its configured rate and withdrawal rules.

```text
Interest Rate: 7.0%
Withdrawal: Restricted according to the account implementation
```

*The rates and account rules above describe the current project configuration and are not intended to represent actual bank products.*

---

## 💻 Requirements

Before running the application, ensure that you have:

- Java Development Kit (JDK) installed
- A terminal or command prompt
- Visual Studio Code or another Java-compatible editor (optional)

Verify Java installation:

```powershell
java -version
javac -version
```

---

## 🚀 Running the Project

Run the commands from the **parent directory** of the `MiniBank` package.

For example, if your project is located at:

```text
D26IT111_OOP_Practicals/
└── MiniBank/
    └── MiniBank.java
```

Open PowerShell in `D26IT111_OOP_Practicals`.

### Step 1: Compile All Java Files

```powershell
javac -d . (Get-ChildItem -Recurse -Filter *.java | ForEach-Object { $_.FullName })
```

This command compiles Java source files in the project and places generated `.class` files according to their package structure.

### Step 2: Run MiniBank

```powershell
java MiniBank.MiniBank
```

The application will display the bank information and interactive console menu.

---

## 🖥️ Application Menu

The current menu includes the following options:

```text
======================================
BankInfo[name=MiniBank, branch=Anand Branch]
======================================

1. Open Account
2. Deposit
3. Withdraw
4. Transfer
5. Show Account Details
6. ExecutorService Transactions
7. Producer-Consumer
8. Deadlock Demonstration
9. Save and Reload Accounts
10. Generate End-of-Day Report
11. Show Accounts Sorted by Balance
0. Exit
```

The menu may change as additional practical requirements are implemented.

---

## 🧵 Concurrency Demonstrations

### Concurrent Transactions

Multiple transaction tasks can operate on account data concurrently.

```text
Transaction Task 1 ──┐
Transaction Task 2 ──┤
Transaction Task 3 ──┤
Transaction Task 4 ──┼──> Shared Account Data
Transaction Task ... ┤
Transaction Task N ──┘
```

When multiple threads access shared mutable data, race conditions may occur if operations are not properly coordinated.

Synchronization and thread-safe data structures are important for maintaining consistent results.

### Producer-Consumer

The producer-consumer demonstration illustrates coordination between tasks that generate work and tasks that consume it.

### Deadlock Demonstration

The deadlock demonstration illustrates how threads can become blocked while waiting for resources held by one another.

These examples are included for educational purposes to demonstrate concurrency concepts.

---

## 💾 Data Storage

MiniBank uses local files for account persistence and transaction-related information.

| File or Directory | Purpose |
|---|---|
| `MiniBank/data/accounts.dat` | Serialized account data |
| `MiniBank/data/logs/transactions.txt` | Transaction history |
| `MiniBank/data/end-of-day-report.txt` | Generated end-of-day report |

The files are created or updated by the relevant application operations.

**Important:** This is an academic demonstration, not a production banking system. Java serialization and plain-text transaction logs should not be treated as secure storage for real financial information.

---

## 📈 Project Learning Progression

The project has evolved through practical exercises that introduce Java and OOP concepts incrementally.

```text
Basic Java Programming
        ↓
Classes and Objects
        ↓
Constructors and Encapsulation
        ↓
Inheritance and Abstraction
        ↓
Polymorphism and Interfaces
        ↓
Enums and Records
        ↓
Validation and Custom Exceptions
        ↓
Banking Operations
        ↓
Collections Framework
        ↓
Generic Repository
        ↓
Multithreading and Synchronization
        ↓
Concurrent Transaction Processing
        ↓
Persistence and Reporting
        ↓
Service-Layer Integration
        ↓
Testing and Packaging
```


## 🎓 Academic Purpose

MiniBank was developed as part of the **CEUE203 — Object-Oriented Programming** course at CSPIT, CHARUSAT.

The project demonstrates how Java concepts can be combined in a single application to model banking operations, organize code into reusable components, handle errors, and explore concurrent execution.

The focus is on understanding and applying programming concepts rather than implementing a production-grade banking system.

---

## 👨‍💻 Author

**Yash K. Paghadar**

Information Technology Student  
CSPIT — CHARUSAT

---

⭐ If you find this project useful, feel free to star the repository!