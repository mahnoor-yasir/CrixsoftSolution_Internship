// File: src/main/java/com/elm/ui/manager/ManagerPanel.java
package com.elm.ui.manager;

import com.elm.AppContext;
import com.elm.model.*;
import com.elm.service.DataStore;
import com.elm.service.LeaveService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManagerPanel extends JPanel {
    private LeaveService leaveService;
    private JTabbedPane tabbedPane;

    public ManagerPanel() {
        leaveService = new LeaveService();
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Dashboard", createDashboardPanel());
        tabbedPane.addTab("Leave Requests", createLeaveRequestPanel());
        tabbedPane.addTab("Audit Log", createAuditLogPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 4, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        AppData appData = DataStore.getInstance().getAppData();
        List<LeaveRequest> requests = appData.getLeaveRequests();

        long totalEmployees = appData.getUsers().stream().filter(User::isActive).count();
        long pendingRequests = requests.stream().filter(r -> r.getStatus() == LeaveStatus.PENDING).count();

        addMetric(panel, "Total Active Employees", String.valueOf(totalEmployees), new Color(0, 51, 102));
        addMetric(panel, "Total Requests", String.valueOf(requests.size()), new Color(0, 102, 204));
        addMetric(panel, "Pending Requests", String.valueOf(pendingRequests), new Color(255, 153, 0));
        addMetric(panel, "Approved Requests", 
            String.valueOf(requests.stream().filter(r -> r.getStatus() == LeaveStatus.APPROVED).count()), 
            new Color(0, 153, 0));

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
        labelLabel.setForeground(Color.GRAY);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));
        valueLabel.setForeground(color);

        card.add(labelLabel);
        card.add(valueLabel);
        panel.add(card);
    }

    private JPanel createLeaveRequestPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columns = {"Request ID", "Employee", "Department", "Leave Type", "Start", "End", "Days", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(30);

        loadPendingRequests(model);

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton approveButton = new JButton("Approve");
        approveButton.setBackground(new Color(0, 153, 0));
        approveButton.setForeground(Color.WHITE);
        approveButton.addActionListener(e -> approveRequest(table));

        JButton rejectButton = new JButton("Reject");
        rejectButton.setBackground(new Color(200, 50, 50));
        rejectButton.setForeground(Color.WHITE);
        rejectButton.addActionListener(e -> rejectRequest(table));

        JButton refreshButton = new JButton("Refresh");
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

    private void approveRequest(JTable table) {
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
            refreshTab("Leave Requests");
        } else {
            JOptionPane.showMessageDialog(this, result, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void rejectRequest(JTable table) {
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
            refreshTab("Leave Requests");
        } else {
            JOptionPane.showMessageDialog(this, result, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createAuditLogPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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
        JScrollPane scrollPane = new JScrollPane(table);
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

    private void refreshTab(String tabTitle) {
        int index = tabbedPane.indexOfTab(tabTitle);
        if (index != -1) {
            Component component = tabbedPane.getComponentAt(index);
            tabbedPane.remove(index);
            if (tabTitle.equals("Leave Requests")) {
                tabbedPane.insertTab("Leave Requests", null, createLeaveRequestPanel(), null, index);
            }
            tabbedPane.setSelectedIndex(index);
        }
    }
}
