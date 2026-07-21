package com.penjualan.dao;

import com.penjualan.db.KoneksiDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Superclass DAO (INHERITANCE). Query umum ditulis sekali di sini, dipakai semua DAO
// turunan lewat method mapRow() yang berbeda-beda tiap tabel (POLIMORFISME).
public abstract class BaseDAO<T> {

    protected List<T> cari(String sqlLike, String keyword) { // query dengan 2 parameter LIKE (fitur pencarian)
        List<T> list = new ArrayList<>();
        try (PreparedStatement ps = KoneksiDatabase.getConnection().prepareStatement(sqlLike)) {
            String key = "%" + (keyword == null ? "" : keyword) + "%";
            ps.setString(1, key); ps.setString(2, key);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    protected List<T> semua(String sql) { // query tanpa parameter (ambil semua / baca VIEW)
        List<T> list = new ArrayList<>();
        try (Statement st = KoneksiDatabase.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    protected abstract T mapRow(ResultSet rs) throws SQLException; // 1 baris ResultSet -> objek model
}
