package com.elm.model;

import java.io.Serializable;

public class LeaveType implements Serializable {
    private static final long serialVersionUID = 1L;

    private String code;
    private String name;
    private int annualAllowance;
    private boolean paid;
    private boolean requiresDocument;

    public LeaveType() {}

    public LeaveType(String code, String name, int annualAllowance, boolean paid, boolean requiresDocument) {
        this.code = code;
        this.name = name;
        this.annualAllowance = annualAllowance;
        this.paid = paid;
        this.requiresDocument = requiresDocument;
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAnnualAllowance() { return annualAllowance; }
    public void setAnnualAllowance(int annualAllowance) { this.annualAllowance = annualAllowance; }

    public boolean isPaid() { return paid; }
    public void setPaid(boolean paid) { this.paid = paid; }

    public boolean isRequiresDocument() { return requiresDocument; }
    public void setRequiresDocument(boolean requiresDocument) { this.requiresDocument = requiresDocument; }

    @Override
    public String toString() {
        return name;
    }
}