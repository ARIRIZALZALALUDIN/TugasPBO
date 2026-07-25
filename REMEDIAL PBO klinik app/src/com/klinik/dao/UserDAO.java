package com.klinik.dao;

import com.klinik.exception.DataTidakDitemukanException;
import com.klinik.model.User;
import com.klinik.util.KoneksiDatabase;

import java.sql.*;

public class UserDAO {

    public User login(String username, String password) throws SQLException, DataTidakDitemukanException {
        String sql = "SELECT * FROM user WHERE username=? AND password=?";
        try (Connection conn = KoneksiDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id_user"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("nama_lengkap"),
                            rs.getString("role")
                    );
                } else {
                    throw new DataTidakDitemukanException("Username atau password salah");
                }
            }
        }
    }
}
