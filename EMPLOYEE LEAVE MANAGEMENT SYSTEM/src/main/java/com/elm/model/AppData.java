package com.elm.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppData implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<User> users = new ArrayList<>();
    private List<LeaveType> leaveTypes = new ArrayList<>();
    private Map<String, List<LeaveBalance>> leaveBalances = new HashMap<>();
    private List<LeaveRequest> leaveRequests = new ArrayList<>();
    private List<AuditEntry> auditLog = new ArrayList<>();

    public List<User> getUsers() { return users; }
    public void setUsers(List<User> users) { this.users = users; }

    public List<LeaveType> getLeaveTypes() { return leaveTypes; }
    public void setLeaveTypes(List<LeaveType> leaveTypes) { this.leaveTypes = leaveTypes; }

    public Map<String, List<LeaveBalance>> getLeaveBalances() { return leaveBalances; }
    public void setLeaveBalances(Map<String, List<LeaveBalance>> leaveBalances) { this.leaveBalances = leaveBalances; }

    public List<LeaveRequest> getLeaveRequests() { return leaveRequests; }
    public void setLeaveRequests(List<LeaveRequest> leaveRequests) { this.leaveRequests = leaveRequests; }

    public List<AuditEntry> getAuditLog() { return auditLog; }
    public void setAuditLog(List<AuditEntry> auditLog) { this.auditLog = auditLog; }
}