package com.klinik;

import com.klinik.gui.LoginForm;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Gagal set Look and Feel, pakai default aja: " + e.getMessage());
        }

        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}
