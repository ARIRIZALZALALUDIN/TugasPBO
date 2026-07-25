package com.klinik.gui;

import com.klinik.dao.UserDAO;
import com.klinik.exception.DataTidakDitemukanException;
import com.klinik.model.User;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class LoginForm extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;

    public LoginForm() {
        setTitle("Login - Sistem Klinik Sederhana");
        setSize(350, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Username:"));
        txtUsername = new JTextField();
        panel.add(txtUsername);

        panel.add(new JLabel("Password:"));
        txtPassword = new JPasswordField();
        panel.add(txtPassword);

        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(e -> prosesLogin());
        panel.add(new JLabel()); // spacer
        panel.add(btnLogin);

        add(panel);
    }

    private void prosesLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username dan password wajib diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.login(username, password);
            JOptionPane.showMessageDialog(this, "Login berhasil! Selamat datang, " + user.getNamaLengkap());
            new MenuUtama(user).setVisible(true);
            dispose();
        } catch (DataTidakDitemukanException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Login Gagal", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Gagal koneksi ke database:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
