package com.elm.ui.admin;

import com.elm.AppContext;
import com.elm.model.*;
import com.elm.service.DataStore;
import com.elm.service.LeaveService;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AdminPanel extends JPanel {
    private LeaveService leaveService;
    private JTabbedPane tabbedPane;
    private JTable employeeTable;
    private DefaultTableModel employeeModel;

    public AdminPanel() {
        leaveService = new LeaveService();
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initComponents();
    }

    private void initComponents() {
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 12));
        tabbedPane.setBackground(Color.WHITE);
        tabbedPane.setForeground(Color.BLACK);

        tabbedPane.addTab("Dashboard", createDashboardPanel());
        tabbedPane.addTab("Employees", createEmployeeManagementPanel());
        tabbedPane.addTab("Leave Requests", createLeaveRequestPanel());
        tabbedPane.addTab("Reports", createReportsPanel());
        tabbedPane.addTab("Audit Log", createAuditLogPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 4, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        AppData appData = DataStore.getInstance().getAppData();
        List<LeaveRequest> requests = appData.getLeaveRequests();

        long totalEmployees = appData.getUsers().stream().filter(User::isActive).count();
        long pendingRequests = requests.stream().filter(r -> r.getStatus() == LeaveStatus.PENDING).count();
        long approvedRequests = requests.stream().filter(r -> r.getStatus() == LeaveStatus.APPROVED).count();
        long rejectedRequests = requests.stream().filter(r -> r.getStatus() == LeaveStatus.REJECTED).count();
        long totalRequests = requests.size();

        addMetric(panel, "Total Active Employees", String.valueOf(totalEmployees), new Color(0, 51, 102));
        addMetric(panel, "Total Leave Requests", String.valueOf(totalRequests), new Color(0, 102, 204));
        addMetric(panel, "Pending Requests", String.valueOf(pendingRequests), new Color(255, 153, 0));
        addMetric(panel, "Approved Requests", String.valueOf(approvedRequests), new Color(0, 153, 0));
        addMetric(panel, "Rejected Requests", String.valueOf(rejectedRequests), new Color(200, 50, 50));

        return panel;
    }

    private void addMetric(JPanel panel, String label, String value, Color color) {
        JPanel card = new JPanel(new GridLayout(2, 1));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setBackground(Color.WHITE);

        JLabel labelLabel = new JLabel(label);
        labelLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        labelLabel.setForeground(Color.BLACK);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));
        valueLabel.setForeground(color);

        card.add(labelLabel);
        card.add(valueLabel);
        panel.add(card);
    }

    private JPanel createEmployeeManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);

        String[] columns = {"Employee ID", "Name", "Username", "Department", "Designation", "Role", "Status"};
        employeeModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        employeeTable = new JTable(employeeModel);
        employeeTable.setRowHeight(30);
        employeeTable.setForeground(Color.BLACK);
        employeeTable.setBackground(Color.WHITE);
        employeeTable.setSelectionBackground(new Color(200, 220, 240));
        employeeTable.setSelectionForeground(Color.BLACK);
        employeeTable.setGridColor(new Color(200, 200, 200));
        employeeTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        employeeTable.getTableHeader().setForeground(Color.BLACK);
        employeeTable.getTableHeader().setBackground(new Color(220, 220, 220));
        employeeTable.setFont(new Font("Arial", Font.PLAIN, 12));

        loadEmployeeData();

        JScrollPane scrollPane = new JScrollPane(employeeTable);
        scrollPane.getViewport().setBackground(Color.WHITE);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton addButton = new JButton("Add Employee");
        addButton.setBackground(new Color(0, 102, 204));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Arial", Font.BOLD, 12));
        addButton.setFocusPainted(false);
        addButton.addActionListener(e -> showAddEmployeeDialog());
        
        JButton editButton = new JButton("Edit Employee");
        editButton.setBackground(new Color(255, 153, 0));
        editButton.setForeground(Color.WHITE);
        editButton.setFont(new Font("Arial", Font.BOLD, 12));
        editButton.setFocusPainted(false);
        editButton.addActionListener(e -> showEditEmployeeDialog());
        
        JButton toggleButton = new JButton("Toggle Status");
        toggleButton.setBackground(new Color(200, 50, 50));
        toggleButton.setForeground(Color.WHITE);
        toggleButton.setFont(new Font("Arial", Font.BOLD, 12));
        toggleButton.setFocusPainted(false);
        toggleButton.addActionListener(e -> toggleEmployeeStatus());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(toggleButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void loadEmployeeData() {
        employeeModel.setRowCount(0);
        AppData appData = DataStore.getInstance().getAppData();
        for (User user : appData.getUsers()) {
            employeeModel.addRow(new Object[]{
                user.getEmployeeId(),
                user.getFullName(),
                user.getUsername(),
                user.getDepartment(),
                user.getDesignation(),
                user.getRole().getDisplayName(),
                user.isActive() ? "Active" : "Inactive"
            });
        }
    }

    private void showAddEmployeeDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add Employee", true);
        dialog.setLayout(new GridBagLayout());
        dialog.setSize(450, 550);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField nameField = new JTextField(20);
        nameField.setForeground(Color.BLACK);
        nameField.setBackground(Color.WHITE);
        nameField.setCaretColor(Color.BLACK);

        JTextField userField = new JTextField(20);
        userField.setForeground(Color.BLACK);
        userField.setBackground(Color.WHITE);
        userField.setCaretColor(Color.BLACK);

        JTextField passField = new JTextField(20);
        passField.setForeground(Color.BLACK);
        passField.setBackground(Color.WHITE);
        passField.setCaretColor(Color.BLACK);

        JTextField emailField = new JTextField(20);
        emailField.setForeground(Color.BLACK);
        emailField.setBackground(Color.WHITE);
        emailField.setCaretColor(Color.BLACK);

        JTextField phoneField = new JTextField(20);
        phoneField.setForeground(Color.BLACK);
        phoneField.setBackground(Color.WHITE);
        phoneField.setCaretColor(Color.BLACK);

        JTextField deptField = new JTextField(20);
        deptField.setForeground(Color.BLACK);
        deptField.setBackground(Color.WHITE);
        deptField.setCaretColor(Color.BLACK);

        JTextField designField = new JTextField(20);
        designField.setForeground(Color.BLACK);
        designField.setBackground(Color.WHITE);
        designField.setCaretColor(Color.BLACK);

        JComboBox<Role> roleCombo = new JComboBox<>(Role.values());
        roleCombo.setForeground(Color.BLACK);
        roleCombo.setBackground(Color.WHITE);

        int y = 0;
        addFormField(dialog, gbc, y++, "Full Name:", nameField);
        addFormField(dialog, gbc, y++, "Username:", userField);
        addFormField(dialog, gbc, y++, "Password:", passField);
        addFormField(dialog, gbc, y++, "Email:", emailField);
        addFormField(dialog, gbc, y++, "Phone:", phoneField);
        addFormField(dialog, gbc, y++, "Department:", deptField);
        addFormField(dialog, gbc, y++, "Designation:", designField);
        addFormField(dialog, gbc, y++, "Role:", roleCombo);

        JButton saveButton = new JButton("Save");
        saveButton.setBackground(new Color(0, 153, 0));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Arial", Font.BOLD, 12));
        saveButton.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 2;
        saveButton.addActionListener(e -> {
            try {
                if (nameField.getText().trim().isEmpty() || userField.getText().trim().isEmpty() ||
                    passField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Name, Username and Password are required.");
                    return;
                }

                String employeeId = "EMP" + String.format("%03d", DataStore.getInstance().getAppData().getUsers().size() + 1);
                User newUser = new User(
                    employeeId,
                    nameField.getText().trim(),
                    userField.getText().trim(),
                    passField.getText().trim(),
                    emailField.getText().trim(),
                    phoneField.getText().trim(),
                    deptField.getText().trim(),
                    designField.getText().trim(),
                    LocalDate.now(),
                    (Role) roleCombo.getSelectedItem(),
                    AppContext.getInstance().getCurrentUser().getEmployeeId()
                );

                DataStore.getInstance().getAppData().getUsers().add(newUser);
                new LeaveService().initializeLeaveBalances(newUser);
                DataStore.getInstance().saveData();

                JOptionPane.showMessageDialog(dialog, "Employee added successfully!");
                dialog.dispose();
                loadEmployeeData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage());
            }
        });

        dialog.add(saveButton, gbc);
        dialog.setVisible(true);
    }

    private void addFormField(JDialog dialog, GridBagConstraints gbc, int y, String label, JComponent component) {
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        JLabel lbl = new JLabel(label);
        lbl.setForeground(Color.BLACK);
        dialog.add(lbl, gbc);
        gbc.gridx = 1;
        dialog.add(component, gbc);
    }

    // ==================== FIXED EDIT EMPLOYEE ====================
    private void showEditEmployeeDialog() {
        int row = employeeTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select an employee to edit.", 
                "Info", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String employeeId = (String) employeeTable.getValueAt(row, 0);
        User userToEdit = findUserById(employeeId);
        
        if (userToEdit == null) {
            JOptionPane.showMessageDialog(this, "Employee not found.");
            return;
        }

        // Create Edit Dialog
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Edit Employee", true);
        dialog.setLayout(new GridBagLayout());
        dialog.setSize(450, 550);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fields with existing data
        JTextField nameField = new JTextField(userToEdit.getFullName(), 20);
        nameField.setForeground(Color.BLACK);
        nameField.setBackground(Color.WHITE);
        nameField.setCaretColor(Color.BLACK);

        JTextField userField = new JTextField(userToEdit.getUsername(), 20);
        userField.setForeground(Color.BLACK);
        userField.setBackground(Color.WHITE);
        userField.setCaretColor(Color.BLACK);
        userField.setEditable(false); // Username can't be changed

        JTextField emailField = new JTextField(userToEdit.getEmail(), 20);
        emailField.setForeground(Color.BLACK);
        emailField.setBackground(Color.WHITE);
        emailField.setCaretColor(Color.BLACK);

        JTextField phoneField = new JTextField(userToEdit.getPhone(), 20);
        phoneField.setForeground(Color.BLACK);
        phoneField.setBackground(Color.WHITE);
        phoneField.setCaretColor(Color.BLACK);

        JTextField deptField = new JTextField(userToEdit.getDepartment(), 20);
        deptField.setForeground(Color.BLACK);
        deptField.setBackground(Color.WHITE);
        deptField.setCaretColor(Color.BLACK);

        JTextField designField = new JTextField(userToEdit.getDesignation(), 20);
        designField.setForeground(Color.BLACK);
        designField.setBackground(Color.WHITE);
        designField.setCaretColor(Color.BLACK);

        JComboBox<Role> roleCombo = new JComboBox<>(Role.values());
        roleCombo.setForeground(Color.BLACK);
        roleCombo.setBackground(Color.WHITE);
        roleCombo.setSelectedItem(userToEdit.getRole());

        // Employee ID (read-only)
        JLabel idLabel = new JLabel(userToEdit.getEmployeeId());
        idLabel.setForeground(Color.BLACK);

        int y = 0;
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        JLabel idLbl = new JLabel("Employee ID:");
        idLbl.setForeground(Color.BLACK);
        dialog.add(idLbl, gbc);
        gbc.gridx = 1;
        dialog.add(idLabel, gbc);
        y++;

        addFormField(dialog, gbc, y++, "Full Name:", nameField);
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        JLabel userLbl = new JLabel("Username (read-only):");
        userLbl.setForeground(Color.BLACK);
        dialog.add(userLbl, gbc);
        gbc.gridx = 1;
        dialog.add(userField, gbc);
        y++;
        
        addFormField(dialog, gbc, y++, "Email:", emailField);
        addFormField(dialog, gbc, y++, "Phone:", phoneField);
        addFormField(dialog, gbc, y++, "Department:", deptField);
        addFormField(dialog, gbc, y++, "Designation:", designField);
        
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        JLabel roleLbl = new JLabel("Role:");
        roleLbl.setForeground(Color.BLACK);
        dialog.add(roleLbl, gbc);
        gbc.gridx = 1;
        dialog.add(roleCombo, gbc);
        y++;

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);

        JButton saveButton = new JButton("Save Changes");
        saveButton.setBackground(new Color(0, 153, 0));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Arial", Font.BOLD, 12));
        saveButton.setFocusPainted(false);
        saveButton.addActionListener(e -> {
            try {
                if (nameField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Name is required.");
                    return;
                }

                // Update user data
                userToEdit.setFullName(nameField.getText().trim());
                userToEdit.setEmail(emailField.getText().trim());
                userToEdit.setPhone(phoneField.getText().trim());
                userToEdit.setDepartment(deptField.getText().trim());
                userToEdit.setDesignation(designField.getText().trim());
                userToEdit.setRole((Role) roleCombo.getSelectedItem());

                DataStore.getInstance().saveData();

                JOptionPane.showMessageDialog(dialog, "Employee updated successfully!");
                dialog.dispose();
                loadEmployeeData();
                JOptionPane.showMessageDialog(this, "Employee updated successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage());
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setBackground(Color.WHITE);
        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 2;
        dialog.add(buttonPanel, gbc);

        dialog.setVisible(true);
    }

    private void toggleEmployeeStatus() {
        int row = employeeTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select an employee to toggle status.", 
                "Info", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String employeeId = (String) employeeTable.getValueAt(row, 0);
        User user = findUserById(employeeId);
        
        if (user == null) {
            JOptionPane.showMessageDialog(this, "Employee not found.");
            return;
        }

        // Don't allow deactivating yourself
        User currentUser = AppContext.getInstance().getCurrentUser();
        if (user.getEmployeeId().equals(currentUser.getEmployeeId())) {
            JOptionPane.showMessageDialog(this, 
                "You cannot deactivate your own account.", 
                "Warning", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to " + (user.isActive() ? "deactivate" : "activate") + 
            " " + user.getFullName() + "?",
            "Confirm",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            user.setActive(!user.isActive());
            DataStore.getInstance().saveData();
            loadEmployeeData();
            JOptionPane.showMessageDialog(this, 
                "Employee " + (user.isActive() ? "activated" : "deactivated") + " successfully.");
        }
    }

    // ==================== LEAVE REQUEST PANEL ====================
    private JPanel createLeaveRequestPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);

        String[] columns = {"Request ID", "Employee", "Department", "Leave Type", "Start", "End", "Days", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.setForeground(Color.BLACK);
        table.setBackground(Color.WHITE);
        table.setGridColor(new Color(200, 200, 200));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.getTableHeader().setForeground(Color.BLACK);
        table.getTableHeader().setBackground(new Color(220, 220, 220));
        table.setFont(new Font("Arial", Font.PLAIN, 12));

        loadPendingRequests(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton approveButton = new JButton("Approve");
        approveButton.setBackground(new Color(0, 153, 0));
        approveButton.setForeground(Color.WHITE);
        approveButton.setFont(new Font("Arial", Font.BOLD, 12));
        approveButton.setFocusPainted(false);
        approveButton.addActionListener(e -> approveRequest(table, model));

        JButton rejectButton = new JButton("Reject");
        rejectButton.setBackground(new Color(200, 50, 50));
        rejectButton.setForeground(Color.WHITE);
        rejectButton.setFont(new Font("Arial", Font.BOLD, 12));
        rejectButton.setFocusPainted(false);
        rejectButton.addActionListener(e -> rejectRequest(table, model));

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBackground(new Color(0, 51, 102));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFont(new Font("Arial", Font.BOLD, 12));
        refreshButton.setFocusPainted(false);
        refreshButton.addActionListener(e -> loadPendingRequests(model));

        buttonPanel.add(approveButton);
        buttonPanel.add(rejectButton);
        buttonPanel.add(refreshButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void loadPendingRequests(DefaultTableModel model) {
        model.setRowCount(0);
        List<LeaveRequest> pending = leaveService.getPendingRequests();

        for (LeaveRequest request : pending) {
            User employee = findUserById(request.getEmployeeId());
            String department = employee != null ? employee.getDepartment() : "N/A";

            model.addRow(new Object[]{
                request.getRequestId(),
                request.getEmployeeName(),
                department,
                request.getLeaveTypeName(),
                request.getStartDate().toString(),
                request.getEndDate().toString(),
                request.getWorkingDays(),
                request.getStatus().getDisplayName()
            });
        }
    }

    private void approveRequest(JTable table, DefaultTableModel model) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a request.");
            return;
        }

        String requestId = (String) table.getValueAt(row, 0);
        String comment = JOptionPane.showInputDialog(this, "Enter approval comment (optional):");

        User currentUser = AppContext.getInstance().getCurrentUser();
        String result = leaveService.approveLeaveRequest(requestId, currentUser.getEmployeeId(), 
            comment != null ? comment : "");

        if (result.equals("SUCCESS")) {
            JOptionPane.showMessageDialog(this, "Request approved successfully.");
            loadPendingRequests(model);
        } else {
            JOptionPane.showMessageDialog(this, result, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void rejectRequest(JTable table, DefaultTableModel model) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a request.");
            return;
        }

        String requestId = (String) table.getValueAt(row, 0);
        String comment = JOptionPane.showInputDialog(this, "Enter rejection reason:");

        if (comment == null || comment.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Rejection reason is required.");
            return;
        }

        User currentUser = AppContext.getInstance().getCurrentUser();
        String result = leaveService.rejectLeaveRequest(requestId, currentUser.getEmployeeId(), comment);

        if (result.equals("SUCCESS")) {
            JOptionPane.showMessageDialog(this, "Request rejected successfully.");
            loadPendingRequests(model);
        } else {
            JOptionPane.showMessageDialog(this, result, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ==================== REPORTS PANEL ====================
    private JPanel createReportsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);

        JTextArea reportArea = new JTextArea();
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        reportArea.setEditable(false);
        reportArea.setForeground(Color.BLACK);
        reportArea.setBackground(Color.WHITE);

        AppData appData = DataStore.getInstance().getAppData();
        List<LeaveRequest> requests = appData.getLeaveRequests();

        StringBuilder report = new StringBuilder();
        report.append("==================== OVERALL LEAVE REPORT ====================\n\n");
        report.append("Total Active Employees: ").append(appData.getUsers().stream().filter(User::isActive).count()).append("\n");
        report.append("Total Leave Requests: ").append(requests.size()).append("\n");
        report.append("Pending: ").append(requests.stream().filter(r -> r.getStatus() == LeaveStatus.PENDING).count()).append("\n");
        report.append("Approved: ").append(requests.stream().filter(r -> r.getStatus() == LeaveStatus.APPROVED).count()).append("\n");
        report.append("Rejected: ").append(requests.stream().filter(r -> r.getStatus() == LeaveStatus.REJECTED).count()).append("\n");
        report.append("Cancelled: ").append(requests.stream().filter(r -> r.getStatus() == LeaveStatus.CANCELLED).count()).append("\n\n");

        report.append("==================== LEAVE TYPE USAGE ====================\n\n");
        for (LeaveType type : appData.getLeaveTypes()) {
            long count = requests.stream()
                .filter(r -> r.getLeaveTypeCode().equals(type.getCode()))
                .filter(r -> r.getStatus() == LeaveStatus.APPROVED)
                .count();
            int totalDays = requests.stream()
                .filter(r -> r.getLeaveTypeCode().equals(type.getCode()))
                .filter(r -> r.getStatus() == LeaveStatus.APPROVED)
                .mapToInt(LeaveRequest::getWorkingDays)
                .sum();
            report.append(String.format("%-20s: %3d requests, %3d days used\n", 
                type.getName(), count, totalDays));
        }

        reportArea.setText(report.toString());
        JScrollPane scrollPane = new JScrollPane(reportArea);
        scrollPane.getViewport().setBackground(Color.WHITE);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    // ==================== AUDIT LOG PANEL ====================
    private JPanel createAuditLogPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);

        String[] columns = {"Timestamp", "Actor", "Action", "Details"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        AppData appData = DataStore.getInstance().getAppData();
        for (AuditEntry entry : appData.getAuditLog()) {
            model.addRow(new Object[]{
                entry.getTimestamp().toString(),
                entry.getActor(),
                entry.getAction(),
                entry.getDetails()
            });
        }

        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.setForeground(Color.BLACK);
        table.setBackground(Color.WHITE);
        table.setGridColor(new Color(200, 200, 200));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.getTableHeader().setForeground(Color.BLACK);
        table.getTableHeader().setBackground(new Color(220, 220, 220));
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private User findUserById(String employeeId) {
        AppData appData = DataStore.getInstance().getAppData();
        for (User user : appData.getUsers()) {
            if (user.getEmployeeId().equals(employeeId)) {
                return user;
            }
        }
        return null;
    }
}
