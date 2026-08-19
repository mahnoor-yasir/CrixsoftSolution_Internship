package com.elm.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String employeeId;
    private String fullName;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String department;
    private String designation;
    private LocalDate joiningDate;
    private Role role;
    private String managerId;
    private boolean active;

    public User() {
        this.active = true;
    }

    public User(String employeeId, String fullName, String username, String password,
                String email, String phone, String department, String designation,
                LocalDate joiningDate, Role role, String managerId) {
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.department = department;
        this.designation = designation;
        this.joiningDate = joiningDate;
        this.role = role;
        this.managerId = managerId;
        this.active = true;
    }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public LocalDate getJoiningDate() { return joiningDate; }
    public void setJoiningDate(LocalDate joiningDate) { this.joiningDate = joiningDate; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public String getManagerId() { return managerId; }
    public void setManagerId(String managerId) { this.managerId = managerId; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    @Override
    public String toString() {
        return fullName + " (" + employeeId + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(employeeId, user.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId);
    }
}
