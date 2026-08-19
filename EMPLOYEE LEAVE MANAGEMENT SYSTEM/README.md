# 📋 Employee Leave Management System

> A complete Java Swing desktop application for managing employee leave requests, approvals, and tracking. Built as a Java Programming internship project.

[![Java](https://img.shields.io/badge/Java-17%2B-orange)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
[![Swing](https://img.shields.io/badge/GUI-Java%20Swing-blue)](https://docs.oracle.com/javase/tutorial/uiswing/)
[![License](https://img.shields.io/badge/License-MIT-green)](LICENSE)

---

## 📌 Table of Contents
- [Overview](#-overview)
- [Features](#-features)
- [User Roles](#-user-roles)
- [Technology Stack](#-technology-stack)
- [Installation](#-installation)
- [Running the Application](#-running-the-application)
- [Default Login Credentials](#-default-login-credentials)
- [Project Structure](#-project-structure)
- [Screenshots](#-screenshots)
- [Key Features Explained](#-key-features-explained)
- [OOP Concepts Demonstrated](#-oop-concepts-demonstrated)
- [Contributing](#-contributing)
- [License](#-license)

---

## 📖 Overview

The **Employee Leave Management System** is a pure Java desktop application that simulates a real organization's internal HR leave-management process. It provides a complete workflow from employee login to leave approval with automatic balance updates.

### Complete Workflow:
```
Employee Login → Select Leave Type → Submit Leave Request → System Validation → 
Pending Request → Manager/Admin Review → Approve or Reject → 
Automatically Update Leave Balance → Request History → Reports and Audit Log
```

---

## ✨ Features

### 👑 Administrator
- Login with secure authentication
- View dashboard with key metrics
- Add, edit, and activate/deactivate employees
- View all employees and leave requests
- Approve/reject leave requests with comments
- View all leave balances
- Generate comprehensive reports
- View audit logs
- Manage leave types
- Change password

### 👔 Manager
- Login with secure authentication
- View dashboard with key metrics
- View employees
- View pending leave requests
- Approve/reject leave requests with comments
- View leave balances
- View reports and audit logs
- Change password

### 👤 Employee
- Login with secure authentication
- View personal leave balances
- Submit leave requests
- Select leave type and dates
- Auto-calculate working days (excludes weekends)
- Enter reason for leave
- View request history
- View request status
- Cancel pending requests
- Change password

---

## 👥 User Roles

| Role | Permissions |
|------|-------------|
| **Administrator** | Full system access, employee management, approve/reject all requests |
| **Manager** | View employees, approve/reject requests, view reports |
| **Employee** | Submit requests, view balances, view history, cancel pending requests |

---

## 🛠️ Technology Stack

### ✅ Required Technologies
| Technology | Purpose |
|------------|---------|
| **Java 17+** | Core programming language |
| **Java Swing** | Graphical User Interface |
| **Java Collections Framework** | Data structures and storage |
| **Java File I/O** | File operations |
| **Java Object Serialization** | Data persistence |
| **java.time API** | Date and time handling |
| **Java Exception Handling** | Error management |
| **Java OOP Principles** | Object-oriented design |

### ❌ NOT USED (Pure Java Desktop)
- No HTML/CSS/JavaScript
- No React/Angular/Vue
- No Node.js/Python/PHP
- No MySQL/Firebase/MongoDB
- No Spring Boot
- No external APIs
- No third-party libraries

---

## 📥 Installation

### Prerequisites
- Java 17 or newer installed
- Git (optional, for cloning)

### Step 1: Clone the Repository
```bash
git clone https://github.com/yourusername/employee-leave-management-system.git
cd employee-leave-management-system
```

### Step 2: Verify Java Installation
```bash
java -version
```

### Step 3: Compile the Project

#### Windows (Using compile.bat):
```cmd
compile.bat
```

#### Windows (Manual):
```cmd
mkdir out
javac -d out src/main/java/com/elm/model/*.java
javac -d out -cp out src/main/java/com/elm/AppContext.java
javac -d out -cp out src/main/java/com/elm/service/*.java
javac -d out -cp out src/main/java/com/elm/ui/*.java
javac -d out -cp out src/main/java/com/elm/ui/admin/*.java
javac -d out -cp out src/main/java/com/elm/ui/manager/*.java
javac -d out -cp out src/main/java/com/elm/ui/employee/*.java
javac -d out -cp out src/main/java/com/elm/Main.java
```

#### Linux/Mac:
```bash
mkdir -p out
javac -d out src/main/java/com/elm/model/*.java
javac -d out -cp out src/main/java/com/elm/AppContext.java
javac -d out -cp out src/main/java/com/elm/service/*.java
javac -d out -cp out src/main/java/com/elm/ui/*.java
javac -d out -cp out src/main/java/com/elm/ui/admin/*.java
javac -d out -cp out src/main/java/com/elm/ui/manager/*.java
javac -d out -cp out src/main/java/com/elm/ui/employee/*.java
javac -d out -cp out src/main/java/com/elm/Main.java
```

---

## 🚀 Running the Application

### Run the Application:
```bash
java -cp out com.elm.Main
```

### Or Run with Batch File (Windows):
```cmd
run.bat
```

### First Run:
- The application automatically creates a `data/` directory
- Creates `elm-data.ser` file with sample data
- Login screen appears

---

## 🔑 Default Login Credentials

| Role | Username | Password |
|------|----------|----------|
| 👑 **Administrator** | `admin` | `admin123` |
| 👔 **Manager** | `manager` | `manager123` |
| 👤 **Employee** | `employee` | `employee123` |
| 👤 **Employee** | `alice` | `employee123` |
| 👤 **Employee** | `bob` | `employee123` |
| 👤 **Employee** | `carol` | `employee123` |

---

## 📁 Project Structure

```
employee-leave-management-system/
│
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── elm/
│                   ├── Main.java                 # Application entry point
│                   ├── AppContext.java           # Application context
│                   │
│                   ├── model/                    # Data models
│                   │   ├── User.java
│                   │   ├── Role.java
│                   │   ├── LeaveType.java
│                   │   ├── LeaveBalance.java
│                   │   ├── LeaveRequest.java
│                   │   ├── LeaveStatus.java
│                   │   ├── AuditEntry.java
│                   │   └── AppData.java
│                   │
│                   ├── service/                  # Business logic
│                   │   ├── DataStore.java        # Persistence
│                   │   ├── LeaveService.java     # Leave management
│                   │   └── SeedData.java         # Sample data
│                   │
│                   └── ui/                       # User interface
│                       ├── LoginFrame.java
│                       ├── DashboardFrame.java
│                       ├── admin/
│                       │   └── AdminPanel.java
│                       ├── manager/
│                       │   └── ManagerPanel.java
│                       └── employee/
│                           └── EmployeePanel.java
│
├── data/                       # Created automatically
│   └── elm-data.ser           # Serialized data storage
│
├── compile.bat                 # Windows compilation script
├── run.bat                     # Windows run script
├── .gitignore
└── README.md
```

---

## 📸 Screenshots

### Login Screen
```
┌─────────────────────────────────────────────┐
│  Employee Leave Management System           │
│                                              │
│  Username: [___________]                    │
│  Password: [___________]                    │
│                                              │
│  [  Login  ]  [  Exit  ]                    │
│                                              │
│  ┌─ Demo Accounts ──────────────────┐       │
│  │ Admin: admin / admin123           │       │
│  │ Manager: manager / manager123     │       │
│  │ Employee: employee / employee123  │       │
│  └───────────────────────────────────┘       │
└─────────────────────────────────────────────┘
```

### Employee Dashboard
- Shows leave balances for all leave types
- Submit leave request form with date picker
- Auto-calculate working days
- Request history table with status tracking

### Admin Dashboard
- Dashboard with key metrics
- Employee management (Add/Edit/Toggle Status)
- Leave request approval/rejection
- Reports generation
- Audit log viewer

---

## 🔧 Key Features Explained

### 🗓️ Working Day Calculation
- Uses Java's `LocalDate` API
- Automatically excludes Saturdays and Sundays
- Validates date ranges
- Prevents past leave requests

### 📊 Leave Balance Management
```java
Remaining = Allocated - Used - Pending
```
- Allocated: Annual allowance
- Used: Approved leaves taken
- Pending: Awaiting approval
- Remaining: Available for use

### ✅ Validation Rules
- Dates must be valid and in correct order
- At least one working day required
- Sufficient balance available
- No overlapping requests
- Reason required
- Employee account must be active

### 📝 Audit Log
Records all system events:
- LOGIN / LOGOUT
- EMPLOYEE_CREATED / UPDATED
- LEAVE_SUBMITTED / APPROVED / REJECTED / CANCELLED
- PASSWORD_CHANGED
- BALANCE_UPDATED

---

## 🏗️ OOP Concepts Demonstrated

| Concept | Implementation |
|---------|---------------|
| **Classes & Objects** | User, LeaveRequest, LeaveBalance |
| **Encapsulation** | Private fields with getters/setters |
| **Inheritance** | N/A (composition used instead) |
| **Polymorphism** | Role-specific dashboard panels |
| **Abstraction** | Service layer separates business logic |
| **Composition** | AppData contains all model objects |
| **Enums** | Role, LeaveStatus |
| **Collections** | Lists, Maps for data storage |
| **Exception Handling** | Try-catch with meaningful messages |
| **File I/O** | Object serialization for persistence |
| **Date/Time API** | LocalDate, LocalDateTime |
| **Event-Driven** | Swing event listeners |

---

## 💾 Data Storage

### File Location
```
data/elm-data.ser
```

### Storage Format
- Java Object Serialization
- Automatic directory creation
- Data persists between sessions
- No external database required

### Sample Data Included
- 1 Administrator
- 1 Manager
- 4 Employees
- 9 Leave Types
- Sample leave requests with different statuses
- Audit log entries

---

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📄 License

This project is created for educational purposes as a Java Programming internship project.

---

## 📞 Contact

For any questions or support:
- **Email**: your-email@example.com
- **GitHub**: [YourUsername](https://github.com/YourUsername)

---

## ⭐ Show Your Support

If you found this project helpful, please give it a ⭐ on GitHub!

---

### 📝 Notes

- This application runs **entirely offline** - no internet connection required
- All data is stored **locally** using Java serialization
- No external databases or services needed
- Pure Java desktop application

---

## 🚨 Troubleshooting

### Issue: "javac is not recognized"
**Solution:** Install Java 17+ and add it to your system PATH

### Issue: "ClassNotFoundException"
**Solution:** Make sure you're using `-cp out` when running

### Issue: "Cannot find symbol"
**Solution:** Compile in the correct order (use compile.bat)

### Issue: Login fails
**Solution:** Delete the `data/` folder and restart the application

---

**Made with ❤️ for Java Programming Internship Project**
