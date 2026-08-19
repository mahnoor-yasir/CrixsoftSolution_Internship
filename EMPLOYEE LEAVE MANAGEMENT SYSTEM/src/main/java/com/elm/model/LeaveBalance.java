package com.elm.model;

import java.io.Serializable;

public class LeaveBalance implements Serializable {
    private static final long serialVersionUID = 1L;

    private String employeeId;
    private String leaveTypeCode;
    private int allocated;
    private int used;
    private int pending;

    public LeaveBalance() {}

    public LeaveBalance(String employeeId, String leaveTypeCode, int allocated) {
        this.employeeId = employeeId;
        this.leaveTypeCode = leaveTypeCode;
        this.allocated = allocated;
        this.used = 0;
        this.pending = 0;
    }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getLeaveTypeCode() { return leaveTypeCode; }
    public void setLeaveTypeCode(String leaveTypeCode) { this.leaveTypeCode = leaveTypeCode; }

    public int getAllocated() { return allocated; }
    public void setAllocated(int allocated) { this.allocated = allocated; }

    public int getUsed() { return used; }
    public void setUsed(int used) { this.used = used; }

    public int getPending() { return pending; }
    public void setPending(int pending) { this.pending = pending; }

    public int getRemaining() {
        return allocated - used - pending;
    }

    @Override
    public String toString() {
        return "Allocated: " + allocated + ", Used: " + used + ", Pending: " + pending + ", Remaining: " + getRemaining();
    }
}