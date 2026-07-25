package com.klinik.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class KoneksiDatabase {

    private static final String URL = "jdbc:mysql://localhost:3306/klinik_db?useSSL=false&serverTimezone=Asia/Jakarta";
    private static final String USER = "root";
    private static final String PASSWORD = ""; 

    private static Connection koneksi;

    public static Connection getConnection() throws SQLException {
        if (koneksi == null || koneksi.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                koneksi = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver MySQL tidak ditemukan. Cek library mysql-connector-j.", e);
            }
        }
        return koneksi;
    }

    public static void tutupKoneksi() {
        try {
            if (koneksi != null && !koneksi.isClosed()) {
                koneksi.close();
            }
        } catch (SQLException e) {
            System.out.println("Gagal menutup koneksi: " + e.getMessage());
        }
    }
}
