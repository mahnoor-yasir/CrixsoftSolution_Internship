package com.elm.ui.employee;

import com.elm.AppContext;
import com.elm.model.*;
import com.elm.service.DataStore;
import com.elm.service.LeaveService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class EmployeePanel extends JPanel {
    private LeaveService leaveService;
    private JTable requestTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> leaveTypeCombo;
    private JTextField startDateField;
    private JTextField endDateField;
    private JTextArea reasonArea;
    private JLabel workingDaysLabel;
    private JPanel dashboardPanel;

    public EmployeePanel() {
        leaveService = new LeaveService();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE);
        initComponents();
        loadRequestHistory();
    }

    private void initComponents() {
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(550);
        splitPane.setResizeWeight(0.4);

        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.setBackground(Color.WHITE);

        dashboardPanel = createDashboardPanel();
        leftPanel.add(dashboardPanel, BorderLayout.NORTH);

        JPanel requestPanel = createRequestFormPanel();
        leftPanel.add(requestPanel, BorderLayout.CENTER);

        splitPane.setLeftComponent(leftPanel);

        JPanel rightPanel = createHistoryPanel();
        splitPane.setRightComponent(rightPanel);

        add(splitPane, BorderLayout.CENTER);
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 4, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(0, 51, 102)), 
            "My Leave Balances", 
            TitledBorder.CENTER, 
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14),
            Color.BLACK
        ));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(0, 220));

        User currentUser = AppContext.getInstance().getCurrentUser();
        List<LeaveBalance> balances = DataStore.getInstance().getAppData()
                .getLeaveBalances().get(currentUser.getEmployeeId());

        if (balances != null) {
            int count = 0;
            for (LeaveBalance balance : balances) {
                if (count >= 8) break;
                JPanel card = new JPanel(new GridLayout(2, 1));
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200)),
                    BorderFactory.createEmptyBorder(8, 8, 8, 8)
                ));
                card.setBackground(Color.WHITE);

                String typeName = balance.getLeaveTypeCode();
                for (LeaveType type : DataStore.getInstance().getAppData().getLeaveTypes()) {
                    if (type.getCode().equals(balance.getLeaveTypeCode())) {
                        typeName = type.getName();
                        break;
                    }
                }

                JLabel nameLabel = new JLabel(typeName);
                nameLabel.setFont(new Font("Arial", Font.BOLD, 11));
                nameLabel.setForeground(Color.BLACK);

                int remaining = balance.getRemaining();
                JLabel balanceLabel = new JLabel("Remaining: " + remaining);
                balanceLabel.setFont(new Font("Arial", Font.PLAIN, 12));
                balanceLabel.setForeground(Color.BLACK);
                if (remaining < 3 && remaining >= 0) {
                    balanceLabel.setForeground(Color.RED);
                } else if (remaining < 5) {
                    balanceLabel.setForeground(new Color(255, 153, 0));
                } else {
                    balanceLabel.setForeground(new Color(0, 153, 0));
                }

                card.add(nameLabel);
                card.add(balanceLabel);
                panel.add(card);
                count++;
            }
        }

        return panel;
    }

    private JPanel createRequestFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(0, 51, 102)),
            "Submit Leave Request",
            TitledBorder.CENTER,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14),
            Color.BLACK
        ));
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Leave Type
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        JLabel typeLabel = new JLabel("Leave Type:");
        typeLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        typeLabel.setForeground(Color.BLACK);
        panel.add(typeLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        leaveTypeCombo = new JComboBox<>();
        leaveTypeCombo.setFont(new Font("Arial", Font.PLAIN, 13));
        leaveTypeCombo.setBackground(Color.WHITE);
        leaveTypeCombo.setForeground(Color.BLACK);
        for (LeaveType type : DataStore.getInstance().getAppData().getLeaveTypes()) {
            leaveTypeCombo.addItem(type.getCode() + " - " + type.getName());
        }
        panel.add(leaveTypeCombo, gbc);

        // Start Date
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        JLabel startLabel = new JLabel("Start Date (YYYY-MM-DD):");
        startLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        startLabel.setForeground(Color.BLACK);
        panel.add(startLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        startDateField = new JTextField(LocalDate.now().plusDays(1).toString());
        startDateField.setFont(new Font("Arial", Font.PLAIN, 13));
        startDateField.setForeground(Color.BLACK);
        startDateField.setBackground(Color.WHITE);
        startDateField.setCaretColor(Color.BLACK);
        panel.add(startDateField, gbc);

        // End Date
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        JLabel endLabel = new JLabel("End Date (YYYY-MM-DD):");
        endLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        endLabel.setForeground(Color.BLACK);
        panel.add(endLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        endDateField = new JTextField(LocalDate.now().plusDays(2).toString());
        endDateField.setFont(new Font("Arial", Font.PLAIN, 13));
        endDateField.setForeground(Color.BLACK);
        endDateField.setBackground(Color.WHITE);
        endDateField.setCaretColor(Color.BLACK);
        panel.add(endDateField, gbc);

        // Calculate button
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        JButton calcButton = new JButton("Calculate Working Days");
        calcButton.setBackground(new Color(0, 102, 204));
        calcButton.setForeground(Color.WHITE);
        calcButton.setFont(new Font("Arial", Font.BOLD, 13));
        calcButton.setFocusPainted(false);
        calcButton.addActionListener(e -> calculateWorkingDays());
        panel.add(calcButton, gbc);

        // Working days - BLACK text
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        workingDaysLabel = new JLabel("Working Days: 0");
        workingDaysLabel.setFont(new Font("Arial", Font.BOLD, 14));
        workingDaysLabel.setForeground(Color.BLACK);
        panel.add(workingDaysLabel, gbc);

        // Reason
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        JLabel reasonLabel = new JLabel("Reason:");
        reasonLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        reasonLabel.setForeground(Color.BLACK);
        panel.add(reasonLabel, gbc);

        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 0.5;
        reasonArea = new JTextArea(4, 20);
        reasonArea.setLineWrap(true);
        reasonArea.setWrapStyleWord(true);
        reasonArea.setFont(new Font("Arial", Font.PLAIN, 13));
        reasonArea.setForeground(Color.BLACK);
        reasonArea.setBackground(Color.WHITE);
        reasonArea.setCaretColor(Color.BLACK);
        reasonArea.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
        JScrollPane scrollPane = new JScrollPane(reasonArea);
        scrollPane.getViewport().setBackground(Color.WHITE);
        panel.add(scrollPane, gbc);

        // Submit button
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;
        gbc.gridwidth = 3;
        JButton submitButton = new JButton("Submit Request");
        submitButton.setBackground(new Color(0, 153, 0));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setPreferredSize(new Dimension(0, 40));
        submitButton.setFocusPainted(false);
        submitButton.addActionListener(e -> submitRequest());
        panel.add(submitButton, gbc);

        return panel;
    }

    private JPanel createHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(0, 51, 102)),
            "My Request History",
            TitledBorder.CENTER,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14),
            Color.BLACK
        ));
        panel.setBackground(Color.WHITE);

        String[] columns = {"Request ID", "Leave Type", "Start", "End", "Days", 
                           "Reason", "Status", "Reviewer", "Comment"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        requestTable = new JTable(tableModel);
        requestTable.setRowHeight(30);
        requestTable.setForeground(Color.BLACK);
        requestTable.setBackground(Color.WHITE);
        requestTable.setSelectionBackground(new Color(200, 220, 240));
        requestTable.setSelectionForeground(Color.BLACK);
        requestTable.setGridColor(new Color(200, 200, 200));
        
        requestTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        requestTable.getTableHeader().setForeground(Color.BLACK);
        requestTable.getTableHeader().setBackground(new Color(220, 220, 220));
        requestTable.getTableHeader().setOpaque(true);
        requestTable.setFont(new Font("Arial", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(requestTable);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        // Cancel Selected Request button
        JButton cancelButton = new JButton("Cancel Selected Request");
        cancelButton.setBackground(new Color(200, 50, 50));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 12));
        cancelButton.setFocusPainted(false);
        cancelButton.addActionListener(e -> cancelRequest());
        buttonPanel.add(cancelButton);

        // Refresh button
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBackground(new Color(0, 51, 102));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFont(new Font("Arial", Font.BOLD, 12));
        refreshButton.setFocusPainted(false);
        refreshButton.addActionListener(e -> {
            loadRequestHistory();
            loadDashboard();
        });
        buttonPanel.add(refreshButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void calculateWorkingDays() {
        try {
            LocalDate start = LocalDate.parse(startDateField.getText().trim());
            LocalDate end = LocalDate.parse(endDateField.getText().trim());

            int workingDays = leaveService.calculateWorkingDays(start, end);
            workingDaysLabel.setText("Working Days: " + workingDays);
            workingDaysLabel.setForeground(Color.BLACK);

            String selected = (String) leaveTypeCombo.getSelectedItem();
            String code = selected.split(" - ")[0];

            User currentUser = AppContext.getInstance().getCurrentUser();
            LeaveBalance balance = leaveService.getLeaveBalance(currentUser.getEmployeeId(), code);

            if (balance != null && !code.equals("UNPAID")) {
                if (workingDays > 0 && workingDays > balance.getRemaining()) {
                    workingDaysLabel.setForeground(Color.RED);
                    workingDaysLabel.setText("Working Days: " + workingDays + " (Insufficient! Remaining: " + balance.getRemaining() + ")");
                } else if (workingDays > 0) {
                    workingDaysLabel.setForeground(new Color(0, 153, 0));
                    workingDaysLabel.setText("Working Days: " + workingDays + " (Available: " + balance.getRemaining() + " days)");
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Please enter valid dates in format YYYY-MM-DD",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void submitRequest() {
        try {
            String selected = (String) leaveTypeCombo.getSelectedItem();
            String code = selected.split(" - ")[0];
            String name = selected.split(" - ")[1];

            LocalDate start = LocalDate.parse(startDateField.getText().trim());
            LocalDate end = LocalDate.parse(endDateField.getText().trim());
            String reason = reasonArea.getText().trim();

            User currentUser = AppContext.getInstance().getCurrentUser();

            LeaveRequest request = new LeaveRequest(
                null, 
                currentUser.getEmployeeId(),
                currentUser.getFullName(),
                code,
                name,
                start,
                end,
                0,
                reason
            );

            String result = leaveService.submitLeaveRequest(request);

            if (result.equals("SUCCESS")) {
                JOptionPane.showMessageDialog(this, 
                    "Leave request submitted successfully!\nRequest ID: " + request.getRequestId(),
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                loadRequestHistory();
                loadDashboard();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, 
                    result,
                    "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error submitting request: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelRequest() {
        int selectedRow = requestTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select a request to cancel.",
                "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String requestId = (String) tableModel.getValueAt(selectedRow, 0);
        String status = (String) tableModel.getValueAt(selectedRow, 6);

        if (!status.equals("PENDING")) {
            JOptionPane.showMessageDialog(this,
                "Only pending requests can be cancelled.",
                "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to cancel this request?",
            "Confirm Cancellation",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            User currentUser = AppContext.getInstance().getCurrentUser();
            String result = leaveService.cancelLeaveRequest(requestId, currentUser.getEmployeeId());

            if (result.equals("SUCCESS")) {
                JOptionPane.showMessageDialog(this,
                    "Request cancelled successfully.",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                loadRequestHistory();
                loadDashboard();
            } else {
                JOptionPane.showMessageDialog(this,
                    result,
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadDashboard() {
        if (dashboardPanel != null) {
            removeAll();
            initComponents();
            revalidate();
            repaint();
        }
    }

    private void loadRequestHistory() {
        tableModel.setRowCount(0);
        User currentUser = AppContext.getInstance().getCurrentUser();
        List<LeaveRequest> requests = leaveService.getRequestsByEmployee(currentUser.getEmployeeId());

        for (LeaveRequest request : requests) {
            Object[] row = {
                request.getRequestId(),
                request.getLeaveTypeName(),
                request.getStartDate().toString(),
                request.getEndDate().toString(),
                request.getWorkingDays(),
                request.getReason().length() > 30 ? 
                    request.getReason().substring(0, 27) + "..." : 
                    request.getReason(),
                request.getStatus().getDisplayName(),
                request.getReviewerName() != null ? request.getReviewerName() : "-",
                request.getReviewerComment() != null ? 
                    (request.getReviewerComment().length() > 30 ? 
                        request.getReviewerComment().substring(0, 27) + "..." : 
                        request.getReviewerComment()) : "-"
            };
            tableModel.addRow(row);
        }
    }

    private void clearForm() {
        startDateField.setText(LocalDate.now().plusDays(1).toString());
        endDateField.setText(LocalDate.now().plusDays(2).toString());
        reasonArea.setText("");
        workingDaysLabel.setText("Working Days: 0");
        workingDaysLabel.setForeground(Color.BLACK);
    }
}
