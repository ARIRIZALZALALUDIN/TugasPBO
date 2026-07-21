package com.penjualan.db;

import java.sql.*;

public class KoneksiDatabase {
    private static final String URL = "jdbc:mysql://localhost:3306/db_penjualan?useSSL=false&serverTimezone=Asia/Jakarta";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // sesuaikan dengan MySQL kamu

    private static Connection conn;

    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver MySQL tidak ditemukan.", e);
            }
        }
        return conn;
    }

    // Helper agar DAO tidak perlu menulis ulang PreparedStatement untuk INSERT/UPDATE/DELETE
    public static void eksekusi(String sql, Object... params) throws SQLException {
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            ps.executeUpdate();
        }
    }
}
