package com.elm.service;

import com.elm.model.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class LeaveService {
    private DataStore dataStore;

    public LeaveService() {
        this.dataStore = DataStore.getInstance();
    }

    public int calculateWorkingDays(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
            return 0;
        }

        long days = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        int workingDays = 0;

        for (int i = 0; i < days; i++) {
            LocalDate date = startDate.plusDays(i);
            if (date.getDayOfWeek().getValue() < 6) {
                workingDays++;
            }
        }

        return workingDays;
    }

    public LeaveBalance getLeaveBalance(String employeeId, String leaveTypeCode) {
        AppData appData = dataStore.getAppData();
        List<LeaveBalance> balances = appData.getLeaveBalances().get(employeeId);

        if (balances != null) {
            for (LeaveBalance balance : balances) {
                if (balance.getLeaveTypeCode().equals(leaveTypeCode)) {
                    return balance;
                }
            }
        }
        return null;
    }

    public void initializeLeaveBalances(User user) {
        AppData appData = dataStore.getAppData();
        List<LeaveBalance> balances = new ArrayList<>();

        for (LeaveType leaveType : appData.getLeaveTypes()) {
            int allowance = leaveType.getAnnualAllowance();
            if (leaveType.getCode().equals("UNPAID")) {
                allowance = 0;
            }
            LeaveBalance balance = new LeaveBalance(user.getEmployeeId(), leaveType.getCode(), allowance);
            balances.add(balance);
        }

        appData.getLeaveBalances().put(user.getEmployeeId(), balances);
        dataStore.saveData();
    }

    public String submitLeaveRequest(LeaveRequest request) {
        AppData appData = dataStore.getAppData();

        if (request.getStartDate() == null || request.getEndDate() == null) {
            return "Please select both start and end dates.";
        }

        if (request.getStartDate().isAfter(request.getEndDate())) {
            return "End date cannot be before start date.";
        }

        if (request.getStartDate().isBefore(LocalDate.now())) {
            return "Leave cannot start in the past.";
        }

        int workingDays = calculateWorkingDays(request.getStartDate(), request.getEndDate());
        if (workingDays == 0) {
            return "At least one working day must be selected.";
        }
        request.setWorkingDays(workingDays);

        if (request.getReason() == null || request.getReason().trim().isEmpty()) {
            return "Please provide a reason for the leave request.";
        }

        User employee = findUserById(request.getEmployeeId());
        if (employee == null) {
            return "Employee not found.";
        }
        if (!employee.isActive()) {
            return "Employee account is inactive.";
        }

        if (!request.getLeaveTypeCode().equals("UNPAID")) {
            LeaveBalance balance = getLeaveBalance(request.getEmployeeId(), request.getLeaveTypeCode());
            if (balance == null) {
                return "Leave balance not found.";
            }
            if (workingDays > balance.getRemaining()) {
                return "Insufficient " + request.getLeaveTypeName() +
                        " balance. Remaining: " + balance.getRemaining() + " days.";
            }
        }

        List<LeaveRequest> existingRequests = appData.getLeaveRequests().stream()
                .filter(r -> r.getEmployeeId().equals(request.getEmployeeId()))
                .filter(r -> r.getStatus() == LeaveStatus.PENDING || r.getStatus() == LeaveStatus.APPROVED)
                .collect(Collectors.toList());

        for (LeaveRequest existing : existingRequests) {
            if (datesOverlap(request.getStartDate(), request.getEndDate(),
                    existing.getStartDate(), existing.getEndDate())) {
                return "Selected dates overlap an existing leave request.";
            }
        }

        request.setRequestId("REQ-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());

        if (!request.getLeaveTypeCode().equals("UNPAID")) {
            LeaveBalance balance = getLeaveBalance(request.getEmployeeId(), request.getLeaveTypeCode());
            if (balance != null) {
                balance.setPending(balance.getPending() + workingDays);
            }
        }

        appData.getLeaveRequests().add(request);
        addAuditEntry(request.getEmployeeId(), "LEAVE_SUBMITTED",
                "Submitted " + request.getLeaveTypeName() +
                        " request for " + workingDays + " days");

        dataStore.saveData();
        return "SUCCESS";
    }

    public String approveLeaveRequest(String requestId, String reviewerId, String comment) {
        AppData appData = dataStore.getAppData();

        LeaveRequest request = findRequestById(requestId);
        if (request == null) {
            return "Request not found.";
        }

        if (request.getStatus() != LeaveStatus.PENDING) {
            return "Request is not pending.";
        }

        User reviewer = findUserById(reviewerId);
        if (reviewer == null) {
            return "Reviewer not found.";
        }

        if (!request.getLeaveTypeCode().equals("UNPAID")) {
            LeaveBalance balance = getLeaveBalance(request.getEmployeeId(), request.getLeaveTypeCode());
            if (balance == null) {
                return "Leave balance not found.";
            }
            if (request.getWorkingDays() > balance.getRemaining()) {
                return "Insufficient balance. Remaining: " + balance.getRemaining() + " days.";
            }
        }

        if (!request.getLeaveTypeCode().equals("UNPAID")) {
            LeaveBalance balance = getLeaveBalance(request.getEmployeeId(), request.getLeaveTypeCode());
            if (balance != null) {
                balance.setPending(balance.getPending() - request.getWorkingDays());
                balance.setUsed(balance.getUsed() + request.getWorkingDays());
            }
        }

        request.setStatus(LeaveStatus.APPROVED);
        request.setReviewerId(reviewerId);
        request.setReviewerName(reviewer.getFullName());
        request.setReviewerComment(comment);
        request.setReviewedAt(java.time.LocalDateTime.now());

        addAuditEntry(reviewerId, "LEAVE_APPROVED",
                "Approved " + request.getLeaveTypeName() +
                        " request for " + request.getEmployeeName() +
                        " (" + request.getWorkingDays() + " days)");

        dataStore.saveData();
        return "SUCCESS";
    }

    public String rejectLeaveRequest(String requestId, String reviewerId, String comment) {
        AppData appData = dataStore.getAppData();

        LeaveRequest request = findRequestById(requestId);
        if (request == null) {
            return "Request not found.";
        }

        if (request.getStatus() != LeaveStatus.PENDING) {
            return "Request is not pending.";
        }

        User reviewer = findUserById(reviewerId);
        if (reviewer == null) {
            return "Reviewer not found.";
        }

        if (!request.getLeaveTypeCode().equals("UNPAID")) {
            LeaveBalance balance = getLeaveBalance(request.getEmployeeId(), request.getLeaveTypeCode());
            if (balance != null) {
                balance.setPending(balance.getPending() - request.getWorkingDays());
            }
        }

        request.setStatus(LeaveStatus.REJECTED);
        request.setReviewerId(reviewerId);
        request.setReviewerName(reviewer.getFullName());
        request.setReviewerComment(comment);
        request.setReviewedAt(java.time.LocalDateTime.now());

        addAuditEntry(reviewerId, "LEAVE_REJECTED",
                "Rejected " + request.getLeaveTypeName() +
                        " request for " + request.getEmployeeName());

        dataStore.saveData();
        return "SUCCESS";
    }

    public String cancelLeaveRequest(String requestId, String employeeId) {
        AppData appData = dataStore.getAppData();

        LeaveRequest request = findRequestById(requestId);
        if (request == null) {
            return "Request not found.";
        }

        if (!request.getEmployeeId().equals(employeeId)) {
            return "You can only cancel your own requests.";
        }

        if (request.getStatus() != LeaveStatus.PENDING) {
            return "Only pending requests can be cancelled.";
        }

        if (!request.getLeaveTypeCode().equals("UNPAID")) {
            LeaveBalance balance = getLeaveBalance(request.getEmployeeId(), request.getLeaveTypeCode());
            if (balance != null) {
                balance.setPending(balance.getPending() - request.getWorkingDays());
            }
        }

        request.setStatus(LeaveStatus.CANCELLED);
        addAuditEntry(employeeId, "LEAVE_CANCELLED",
                "Cancelled " + request.getLeaveTypeName() + " request");

        dataStore.saveData();
        return "SUCCESS";
    }

    private User findUserById(String employeeId) {
        AppData appData = dataStore.getAppData();
        for (User user : appData.getUsers()) {
            if (user.getEmployeeId().equals(employeeId)) {
                return user;
            }
        }
        return null;
    }

    private LeaveRequest findRequestById(String requestId) {
        AppData appData = dataStore.getAppData();
        for (LeaveRequest request : appData.getLeaveRequests()) {
            if (request.getRequestId().equals(requestId)) {
                return request;
            }
        }
        return null;
    }

    private boolean datesOverlap(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
        return !(end1.isBefore(start2) || start1.isAfter(end2));
    }

    private void addAuditEntry(String actor, String action, String details) {
        AppData appData = dataStore.getAppData();
        String id = "AUD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        AuditEntry entry = new AuditEntry(id, actor, action, details);
        appData.getAuditLog().add(entry);
        dataStore.saveData();
    }

    public List<LeaveRequest> getPendingRequests() {
        AppData appData = dataStore.getAppData();
        return appData.getLeaveRequests().stream()
                .filter(r -> r.getStatus() == LeaveStatus.PENDING)
                .collect(Collectors.toList());
    }

    public List<LeaveRequest> getRequestsByEmployee(String employeeId) {
        AppData appData = dataStore.getAppData();
        return appData.getLeaveRequests().stream()
                .filter(r -> r.getEmployeeId().equals(employeeId))
                .sorted((r1, r2) -> r2.getSubmittedAt().compareTo(r1.getSubmittedAt()))
                .collect(Collectors.toList());
    }

    public List<LeaveRequest> getAllRequests() {
        AppData appData = dataStore.getAppData();
        return new ArrayList<>(appData.getLeaveRequests());
    }
}