package com.penjualan.gui;

import com.penjualan.dao.UserDAO;
import com.penjualan.exception.AplikasiException;
import com.penjualan.util.Session;

import javax.swing.*;
import java.awt.*;

public class LoginForm extends JFrame {

    public LoginForm() {
        setTitle("Login - Aplikasi Penjualan");
        setSize(350, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTextField txtUser = new JTextField(15);
        JPasswordField txtPass = new JPasswordField(15);
        JButton btnLogin = new JButton("Login");

        JPanel p = new JPanel(new GridLayout(3, 2, 5, 10));
        p.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        p.add(new JLabel("Username")); p.add(txtUser);
        p.add(new JLabel("Password")); p.add(txtPass);
        p.add(new JLabel()); p.add(btnLogin);
        add(p);

        btnLogin.addActionListener(e -> {
            try {
                Session.user = new UserDAO().login(txtUser.getText(), new String(txtPass.getPassword()));
                dispose();
                new MainForm().setVisible(true);
            } catch (AplikasiException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Login Gagal", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}
