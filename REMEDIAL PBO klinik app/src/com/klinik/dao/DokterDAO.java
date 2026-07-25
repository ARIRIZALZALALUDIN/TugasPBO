package com.klinik.dao;

import com.klinik.model.Dokter;
import com.klinik.util.KoneksiDatabase;
import com.klinik.util.LogAktivitas;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DokterDAO implements Cruddable<Dokter> {

    @Override
    public void tambah(Dokter d) throws SQLException {
        String sql = "INSERT INTO dokter (nama, spesialisasi, no_telp) VALUES (?,?,?)";
        try (Connection conn = KoneksiDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getNama());
            ps.setString(2, d.getSpesialisasi());
            ps.setString(3, d.getNoTelp());
            ps.executeUpdate();
            LogAktivitas.catat("Tambah data dokter: " + d.getNama());
        }
    }

    @Override
    public void ubah(Dokter d) throws SQLException {
        String sql = "UPDATE dokter SET nama=?, spesialisasi=?, no_telp=? WHERE id_dokter=?";
        try (Connection conn = KoneksiDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getNama());
            ps.setString(2, d.getSpesialisasi());
            ps.setString(3, d.getNoTelp());
            ps.setInt(4, d.getId());
            ps.executeUpdate();
            LogAktivitas.catat("Ubah data dokter id=" + d.getId());
        }
    }

    @Override
    public void hapus(int id) throws SQLException {
        String sql = "DELETE FROM dokter WHERE id_dokter=?";
        try (Connection conn = KoneksiDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            LogAktivitas.catat("Hapus data dokter id=" + id);
        }
    }

    @Override
    public List<Dokter> tampilkanSemua() throws SQLException {
        List<Dokter> daftar = new ArrayList<>();
        String sql = "SELECT * FROM dokter ORDER BY id_dokter DESC";
        try (Connection conn = KoneksiDatabase.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                daftar.add(mapKeObjek(rs));
            }
        }
        return daftar;
    }

    @Override
    public List<Dokter> cari(String kataKunci) throws SQLException {
        List<Dokter> daftar = new ArrayList<>();
        String sql = "SELECT * FROM dokter WHERE nama LIKE ? OR spesialisasi LIKE ?";
        try (Connection conn = KoneksiDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + kataKunci + "%");
            ps.setString(2, "%" + kataKunci + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    daftar.add(mapKeObjek(rs));
                }
            }
        }
        return daftar;
    }

    private Dokter mapKeObjek(ResultSet rs) throws SQLException {
        return new Dokter(
                rs.getInt("id_dokter"),
                rs.getString("nama"),
                rs.getString("spesialisasi"),
                rs.getString("no_telp")
        );
    }
}
