package com.penjualan.dao;

import com.penjualan.db.KoneksiDatabase;
import com.penjualan.exception.AplikasiException;
import com.penjualan.model.User;

import java.sql.*;
import java.util.List;

public class UserDAO extends BaseDAO<User> {

    public User login(String username, String password) throws AplikasiException {
        String sql = "SELECT * FROM user WHERE username=? AND password=?";
        try (PreparedStatement ps = KoneksiDatabase.getConnection().prepareStatement(sql)) {
            ps.setString(1, username); ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
            throw new AplikasiException("Username atau password salah!");
        } catch (SQLException e) {
            throw new AplikasiException("Gagal terhubung ke database: " + e.getMessage());
        }
    }

    public List<User> getAll() { return semua("SELECT * FROM user ORDER BY id_user"); }

    @Override protected User mapRow(ResultSet rs) throws SQLException {
        return new User(rs.getInt("id_user"), rs.getString("username"),
                rs.getString("password"), rs.getString("nama_lengkap"), rs.getString("role"));
    }

    public void simpan(User u) throws SQLException { // id==0 -> INSERT, id!=0 -> UPDATE
        if (u.getId() == 0) {
            KoneksiDatabase.eksekusi("INSERT INTO user (username,password,nama_lengkap,role) VALUES (?,?,?,?)",
                    u.getUsername(), u.getPassword(), u.getNama(), u.getRole());
        } else {
            KoneksiDatabase.eksekusi("UPDATE user SET username=?,password=?,nama_lengkap=?,role=? WHERE id_user=?",
                    u.getUsername(), u.getPassword(), u.getNama(), u.getRole(), u.getId());
        }
    }

    public void hapus(int id) throws SQLException { KoneksiDatabase.eksekusi("DELETE FROM user WHERE id_user=?", id); }
}
