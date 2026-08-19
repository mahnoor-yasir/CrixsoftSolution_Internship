package com.elm.ui;

import com.elm.AppContext;
import com.elm.model.Role;
import com.elm.model.User;
import com.elm.ui.admin.AdminPanel;
import com.elm.ui.employee.EmployeePanel;
import com.elm.ui.manager.ManagerPanel;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {
    private JPanel contentPanel;
    private JLabel userInfoLabel;

    public DashboardFrame() {
        setTitle("Employee Leave Management System - Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        initComponents();
        loadDashboard();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Top panel with user info
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(0, 51, 102));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel titleLabel = new JLabel("Employee Leave Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);

        User currentUser = AppContext.getInstance().getCurrentUser();
        userInfoLabel = new JLabel("Welcome, " + currentUser.getFullName() + 
            " | Role: " + currentUser.getRole().getDisplayName() +
            " | ID: " + currentUser.getEmployeeId());
        userInfoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userInfoLabel.setForeground(Color.WHITE);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(Color.WHITE);
        logoutButton.setForeground(new Color(0, 51, 102));
        logoutButton.addActionListener(e -> handleLogout());

        // Change Password Button
        JButton changePasswordButton = new JButton("Change Password");
        changePasswordButton.setBackground(Color.WHITE);
        changePasswordButton.setForeground(new Color(0, 51, 102));
        changePasswordButton.addActionListener(e -> showChangePasswordDialog());

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);
        rightPanel.add(userInfoLabel);
        rightPanel.add(changePasswordButton);
        rightPanel.add(logoutButton);

        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(rightPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // Content panel
        contentPanel = new JPanel(new CardLayout());
        add(contentPanel, BorderLayout.CENTER);
    }

    private void loadDashboard() {
        User currentUser = AppContext.getInstance().getCurrentUser();
        
        JPanel panel = null;
        switch (currentUser.getRole()) {
            case ADMIN:
                panel = new AdminPanel();
                break;
            case MANAGER:
                panel = new ManagerPanel();
                break;
            case EMPLOYEE:
                panel = new EmployeePanel();
                break;
        }

        if (panel != null) {
            contentPanel.removeAll();
            contentPanel.add(panel, "main");
            contentPanel.revalidate();
            contentPanel.repaint();
        }
    }

    private void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to logout?", "Logout",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            AppContext.getInstance().logout();
            this.dispose();
            new LoginFrame().setVisible(true);
        }
    }

    private void showChangePasswordDialog() {
        JDialog dialog = new JDialog(this, "Change Password", true);
        dialog.setLayout(new GridBagLayout());
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPasswordField currentPassField = new JPasswordField(20);
        JPasswordField newPassField = new JPasswordField(20);
        JPasswordField confirmPassField = new JPasswordField(20);

        // Current Password
        gbc.gridx = 0;
        gbc.gridy = 0;
        dialog.add(new JLabel("Current Password:"), gbc);
        gbc.gridx = 1;
        dialog.add(currentPassField, gbc);

        // New Password
        gbc.gridx = 0;
        gbc.gridy = 1;
        dialog.add(new JLabel("New Password:"), gbc);
        gbc.gridx = 1;
        dialog.add(newPassField, gbc);

        // Confirm Password
        gbc.gridx = 0;
        gbc.gridy = 2;
        dialog.add(new JLabel("Confirm Password:"), gbc);
        gbc.gridx = 1;
        dialog.add(confirmPassField, gbc);

        // Buttons
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        JButton saveButton = new JButton("Save");
        saveButton.setBackground(new Color(0, 51, 102));
        saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(e -> {
            String current = new String(currentPassField.getPassword());
            String newPass = new String(newPassField.getPassword());
            String confirm = new String(confirmPassField.getPassword());

            User currentUser = AppContext.getInstance().getCurrentUser();

            if (!currentUser.getPassword().equals(current)) {
                JOptionPane.showMessageDialog(dialog, "Current password is incorrect.");
                return;
            }

            if (newPass.length() < 6) {
                JOptionPane.showMessageDialog(dialog, "New password must be at least 6 characters.");
                return;
            }

            if (!newPass.equals(confirm)) {
                JOptionPane.showMessageDialog(dialog, "New password and confirmation do not match.");
                return;
            }

            currentUser.setPassword(newPass);
            com.elm.service.DataStore.getInstance().saveData();
            
            JOptionPane.showMessageDialog(dialog, "Password changed successfully!");
            dialog.dispose();
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        dialog.add(buttonPanel, gbc);

        dialog.setVisible(true);
    }

    public void refreshDashboard() {
        loadDashboard();
    }
}
