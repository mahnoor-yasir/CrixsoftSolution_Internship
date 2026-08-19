# README.md

# Employee Leave Management System

A complete Java Swing desktop application for managing employee leave requests, approvals, and tracking.

## Table of Contents
- [README.md](#readmemd)
- [Employee Leave Management System](#employee-leave-management-system)
  - [Table of Contents](#table-of-contents)
  - [Overview](#overview)
  - [Features](#features)
    - [Administrator](#administrator)
    - [Manager](#manager)
    - [Employee](#employee)
    - [Core Features](#core-features)
  - [Technology Stack](#technology-stack)
  - [Installation](#installation)

## Overview

The Employee Leave Management System is a pure Java desktop application built for a Java Programming internship project. It simulates a real organization's internal HR leave-management process with three distinct user roles: Administrator, Manager, and Employee.

## Features

### Administrator
- View dashboard with key metrics
- Add, edit, and activate/deactivate employees
- View all employees and leave requests
- Approve/reject leave requests
- View leave balances
- Generate reports
- View audit logs
- Manage leave types

### Manager
- View dashboard
- View employees
- Approve/reject leave requests
- Add approval/rejection comments
- View reports and audit logs

### Employee
- View personal leave balances
- Submit leave requests
- Select leave types and dates
- Auto-calculate working days (excludes weekends)
- View request history
- Cancel pending requests
- Change password

### Core Features
- Working day calculation (excludes Saturdays and Sundays)
- Automatic leave balance updates
- Overlapping request detection
- Comprehensive audit logging
- Data persistence using Java serialization
compile.bat
## Technology Stack

- **Java 17** or newer
- **Java Swing** for GUI
- **Java Collections Framework**
- **Java File I/O** and **Object Serialization**
- **java.time API** for date handling
- **Java OOP Principles**

## Installation

1. Ensure you have Java 17 or newer installed:
```bash
java -version
