package com.elm.service;

import com.elm.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SeedData {

    public static AppData createSeedData() {
        AppData appData = new AppData();

        // Create leave types
        List<LeaveType> leaveTypes = Arrays.asList(
                new LeaveType("ANNUAL", "Annual Leave", 20, true, false),
                new LeaveType("SICK", "Sick Leave", 12, true, true),
                new LeaveType("CASUAL", "Casual Leave", 10, true, false),
                new LeaveType("UNPAID", "Unpaid Leave", 0, false, false),
                new LeaveType("MATERNITY", "Maternity Leave", 90, true, true),
                new LeaveType("PATERNITY", "Paternity Leave", 15, true, true),
                new LeaveType("EMERGENCY", "Emergency Leave", 5, true, false),
                new LeaveType("BEREAVEMENT", "Bereavement Leave", 5, true, true),
                new LeaveType("WFH", "Work From Home", 20, true, false)
        );
        appData.setLeaveTypes(leaveTypes);

        // Create users
        List<User> users = new ArrayList<>();

        // Admin
        User admin = new User(
                "EMP001", "System Administrator", "admin", "admin123",
                "admin@elm.com", "+1234567890", "Administration", "System Admin",
                LocalDate.of(2020, 1, 1), Role.ADMIN, null
        );
        users.add(admin);

        // Manager
        User manager = new User(
                "EMP002", "John Manager", "manager", "manager123",
                "manager@elm.com", "+1234567891", "Engineering", "Department Manager",
                LocalDate.of(2020, 6, 15), Role.MANAGER, admin.getEmployeeId()
        );
        users.add(manager);

        // Employee 1 - Alice (use this to login as employee)
        User emp1 = new User(
                "EMP003", "Alice Smith", "alice", "employee123",
                "alice@elm.com", "+1234567892", "Engineering", "Software Engineer",
                LocalDate.of(2021, 3, 1), Role.EMPLOYEE, manager.getEmployeeId()
        );
        users.add(emp1);

        // Employee 2 - Bob
        User emp2 = new User(
                "EMP004", "Bob Johnson", "bob", "employee123",
                "bob@elm.com", "+1234567893", "HR", "HR Specialist",
                LocalDate.of(2021, 5, 15), Role.EMPLOYEE, admin.getEmployeeId()
        );
        users.add(emp2);

        // Employee 3 - Carol
        User emp3 = new User(
                "EMP005", "Carol Williams", "carol", "employee123",
                "carol@elm.com", "+1234567894", "Finance", "Financial Analyst",
                LocalDate.of(2021, 8, 1), Role.EMPLOYEE, admin.getEmployeeId()
        );
        users.add(emp3);

        // ALSO add a generic employee account with username "employee"
        User empGeneric = new User(
                "EMP006", "Default Employee", "employee", "employee123",
                "employee@elm.com", "+1234567895", "General", "Staff",
                LocalDate.of(2022, 1, 1), Role.EMPLOYEE, manager.getEmployeeId()
        );
        users.add(empGeneric);

        appData.setUsers(users);

        // Initialize leave balances for all users
        appData.getLeaveBalances().clear();
        for (User user : users) {
            List<LeaveBalance> balances = new ArrayList<>();
            for (LeaveType leaveType : leaveTypes) {
                int allowance = leaveType.getAnnualAllowance();
                if (leaveType.getCode().equals("UNPAID")) {
                    allowance = 0;
                }
                LeaveBalance balance = new LeaveBalance(user.getEmployeeId(), leaveType.getCode(), allowance);
                balances.add(balance);
            }
            appData.getLeaveBalances().put(user.getEmployeeId(), balances);
        }

        // Create some sample leave requests
        List<LeaveRequest> requests = new ArrayList<>();

        LeaveRequest req1 = new LeaveRequest(
                "REQ-001", emp1.getEmployeeId(), emp1.getFullName(),
                "ANNUAL", "Annual Leave",
                LocalDate.now().plusDays(10), LocalDate.now().plusDays(12),
                3, "Family vacation"
        );
        requests.add(req1);

        LeaveRequest req2 = new LeaveRequest(
                "REQ-002", emp2.getEmployeeId(), emp2.getFullName(),
                "SICK", "Sick Leave",
                LocalDate.now().minusDays(5), LocalDate.now().minusDays(3),
                3, "Doctor's appointment"
        );
        req2.setStatus(LeaveStatus.APPROVED);
        req2.setReviewerId(admin.getEmployeeId());
        req2.setReviewerName(admin.getFullName());
        req2.setReviewerComment("Approved - medical certificate provided");
        req2.setReviewedAt(java.time.LocalDateTime.now().minusDays(2));
        requests.add(req2);

        // Update balance for approved request
        for (LeaveBalance balance : appData.getLeaveBalances().get(emp2.getEmployeeId())) {
            if (balance.getLeaveTypeCode().equals("SICK")) {
                balance.setUsed(3);
                break;
            }
        }

        LeaveRequest req3 = new LeaveRequest(
                "REQ-003", emp3.getEmployeeId(), emp3.getFullName(),
                "ANNUAL", "Annual Leave",
                LocalDate.now().plusDays(20), LocalDate.now().plusDays(25),
                4, "Personal trip"
        );
        req3.setStatus(LeaveStatus.REJECTED);
        req3.setReviewerId(admin.getEmployeeId());
        req3.setReviewerName(admin.getFullName());
        req3.setReviewerComment("Insufficient balance. Only 2 days remaining.");
        req3.setReviewedAt(java.time.LocalDateTime.now().minusDays(1));
        requests.add(req3);

        appData.setLeaveRequests(requests);

        // Sample audit entries
        List<AuditEntry> auditLog = new ArrayList<>();

        auditLog.add(new AuditEntry(
                "AUD-001", admin.getUsername(), "LOGIN",
                "Admin logged in"
        ));
        auditLog.add(new AuditEntry(
                "AUD-002", admin.getUsername(), "EMPLOYEE_CREATED",
                "Created employee: " + emp1.getFullName() + " (" + emp1.getEmployeeId() + ")"
        ));
        auditLog.add(new AuditEntry(
                "AUD-003", admin.getUsername(), "EMPLOYEE_CREATED",
                "Created employee: " + emp2.getFullName() + " (" + emp2.getEmployeeId() + ")"
        ));
        auditLog.add(new AuditEntry(
                "AUD-004", admin.getUsername(), "EMPLOYEE_CREATED",
                "Created employee: " + emp3.getFullName() + " (" + emp3.getEmployeeId() + ")"
        ));
        auditLog.add(new AuditEntry(
                "AUD-005", admin.getUsername(), "LEAVE_APPROVED",
                "Approved Sick Leave request for Bob Johnson"
        ));
        auditLog.add(new AuditEntry(
                "AUD-006", admin.getUsername(), "LEAVE_REJECTED",
                "Rejected Annual Leave request for Carol Williams"
        ));

        appData.setAuditLog(auditLog);

        return appData;
    }
}