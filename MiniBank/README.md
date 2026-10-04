# 🏦 MiniBank

**MiniBank** is a Java-based console banking application developed as a semester project for the **Object-Oriented Programming (OOP)** course.

The project demonstrates how core and advanced **Object-Oriented Programming concepts in Java** can be combined to build a structured banking application.

---

## 📚 Project Information

| Information          | Details                      |
| -------------------- | ---------------------------- |
| **Project Name**     | MiniBank                     |
| **Course**           | Object-Oriented Programming  |
| **Subject Code**     | CEUE203                      |
| **Language**         | Java                         |
| **IDE**              | Visual Studio Code           |
| **Application Type** | Console-Based Banking System |

---

## 🎯 Objective

The objective of MiniBank is to develop a simple banking system while applying the Object-Oriented Programming concepts learned throughout the semester.

The project progressively incorporates:

* Classes and Objects
* Encapsulation
* Inheritance
* Abstraction
* Polymorphism
* Interfaces
* Records
* Enums
* Exception Handling
* Validation
* Multithreading
* Synchronization

---

## ✨ Features

### 👤 Customer Management

* Create customer records
* Store customer information
* Generate unique customer IDs
* Store customer address information

### 🏦 Account Management

The system supports different types of bank accounts:

* Savings Account
* Current Account
* Fixed Deposit Account

Each account type follows its own rules and behavior.

### 💰 Banking Operations

* Deposit money
* Withdraw money
* Transfer money
* Display account information
* Check account balance

### 🔐 Validation

The project includes validation for:

* Mobile numbers
* Email addresses
* PAN numbers
* IFSC codes
* Transaction amounts
* Account numbers

### ⚠️ Exception Handling

Custom exceptions are used for different banking errors, including:

* Account not found
* Insufficient funds
* Invalid amount
* Other banking-related errors

### 🧵 Multithreading

The project also demonstrates concurrent operations on a bank account.

It includes:

* Multiple threads accessing the same account
* Race-condition demonstration
* Thread synchronization
* Thread-safe deposit/withdraw operations
* `join()` for waiting for threads to complete

---

## 🧱 Project Structure

```text
MiniBank/
│
├── README.md
│
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
│   ├── Premium.java
│   ├── Transactable.java
│   └── ...
│
└── service/
    ├── Validator.java
    ├── WithdrawRule.java
    └── ...
```

---

## 🧩 OOP Concepts Demonstrated

### 1. Encapsulation

Account and customer data are kept inside their respective classes and accessed through appropriate methods.

### 2. Inheritance

Specialized account classes inherit common behavior from the base `Account` class.

```text
              Account
                 │
       ┌─────────┼─────────┐
       │         │         │
   Savings    Current    FixedDeposit
   Account    Account      Account
```

### 3. Abstraction

`Account` provides common account behavior while allowing individual account types to define their own rules.

For example:

* Interest rate
* Withdrawal rules

### 4. Polymorphism

Different account types can be stored and processed using the common `Account` reference.

```java
Account[] accounts;
```

This allows the program to use dynamic method dispatch.

### 5. Interfaces

Interfaces such as:

```text
InterestBearing
Transactable
Premium
```

are used to define common capabilities that can be implemented by appropriate classes.

### 6. Records

Java records are used where immutable data structures are suitable.

Example:

```java
record Command(TransactionType type, String accountNumber, long amount) {}
```

### 7. Enums

Enums are used to represent fixed sets of values.

Example:

```java
enum TransactionType {
    DEPOSIT,
    WITHDRAW,
    TRANSFER
}
```

### 8. Exception Handling

Custom checked exceptions are used to handle banking errors in a structured way.

### 9. Multithreading

Multiple threads are used to simulate concurrent banking operations.

Synchronization is then applied to prevent inconsistent account balances.

---

## 💳 Account Types

### Savings Account

The Savings Account maintains a minimum balance requirement.

```text
Interest Rate: 4.0%
Withdrawal: Allowed while maintaining minimum balance
```

### Current Account

The Current Account supports an overdraft limit.

```text
Interest Rate: 0%
Withdrawal: Allowed up to the overdraft limit
```

### Fixed Deposit Account

The Fixed Deposit Account provides interest but does not allow normal withdrawals.

```text
Interest Rate: 7.0%
Withdrawal: Not allowed
```



---

## 💻 Running the Project

The project uses Java packages, so it should be compiled and executed from the **parent directory** of the `MiniBank` package.

### Compile

```bash
javac MiniBank/MiniBank.java MiniBank/model/*.java MiniBank/service/*.java
```

### Run

```bash
java MiniBank.MiniBank
```

---

## 🖥️ Example Menu

```text
========== MiniBank ==========
1. Open Account
2. Deposit
3. Withdraw
4. Transfer
5. Show Account Details
6. ExecutorService Transactions
7. Producer-Consumer
8. Deadlock Demonstration
0. Exit
```

The exact menu may evolve as additional practical requirements are integrated into the project.

---

## 🧵 Multithreading Demonstration

One of the later stages of the project demonstrates what happens when multiple threads perform operations on the same bank account.

Without synchronization:

```text
Thread 1 ──┐
Thread 2 ──┤
Thread 3 ──┤
Thread 4 ──┤──> Shared Account
Thread ... ─┤
Thread 10 ──┘
```

Concurrent access can result in a **race condition**, where the final balance may not match the expected result.

Synchronization is then introduced so that critical account operations execute safely.

---

## 📈 Project Learning Progression

```text
Basic Java
    ↓
Classes & Objects
    ↓
Constructors
    ↓
Encapsulation
    ↓
Inheritance
    ↓
Abstraction
    ↓
Polymorphism
    ↓
Interfaces
    ↓
Validation
    ↓
Custom Exceptions
    ↓
Transaction Processing
    ↓
Multithreading
    ↓
Synchronization
    ↓
MiniBank
```

---

## 🎓 Academic Purpose

MiniBank was developed to apply the concepts taught throughout the **CEUE203 Object-Oriented Programming** course in a single practical application.

The project focuses on understanding **how OOP concepts work together in a real-world-style problem** rather than implementing a production banking system.

---

## 👨‍💻 Author

**Yash K. Paghadar**

IT Student | CSPIT | CHARUSAT
---

⭐ If you find this project useful, feel free to **star the repository**.
