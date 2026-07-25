package com.klinik.dao;

import com.klinik.model.Pasien;
import com.klinik.util.KoneksiDatabase;
import com.klinik.util.LogAktivitas;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PasienDAO implements Cruddable<Pasien> {

    @Override
    public void tambah(Pasien p) throws SQLException {
        String sql = "INSERT INTO pasien (nama, alamat, no_telp, tanggal_lahir, jenis_kelamin) VALUES (?,?,?,?,?)";
        try (Connection conn = KoneksiDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNama());
            ps.setString(2, p.getAlamat());
            ps.setString(3, p.getNoTelp());
            ps.setDate(4, Date.valueOf(p.getTanggalLahir()));
            ps.setString(5, p.getJenisKelamin());
            ps.executeUpdate();
            LogAktivitas.catat("Tambah data pasien: " + p.getNama());
        }
    }

    @Override
    public void ubah(Pasien p) throws SQLException {
        String sql = "UPDATE pasien SET nama=?, alamat=?, no_telp=?, tanggal_lahir=?, jenis_kelamin=? WHERE id_pasien=?";
        try (Connection conn = KoneksiDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNama());
            ps.setString(2, p.getAlamat());
            ps.setString(3, p.getNoTelp());
            ps.setDate(4, Date.valueOf(p.getTanggalLahir()));
            ps.setString(5, p.getJenisKelamin());
            ps.setInt(6, p.getId());
            ps.executeUpdate();
            LogAktivitas.catat("Ubah data pasien id=" + p.getId());
        }
    }

    @Override
    public void hapus(int id) throws SQLException {
        String sql = "DELETE FROM pasien WHERE id_pasien=?";
        try (Connection conn = KoneksiDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            LogAktivitas.catat("Hapus data pasien id=" + id);
        }
    }

    @Override
    public List<Pasien> tampilkanSemua() throws SQLException {
        List<Pasien> daftar = new ArrayList<>();
        String sql = "SELECT * FROM pasien ORDER BY id_pasien DESC";
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
    public List<Pasien> cari(String kataKunci) throws SQLException {
        List<Pasien> daftar = new ArrayList<>();
        String sql = "SELECT * FROM pasien WHERE nama LIKE ? OR no_telp LIKE ?";
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

    private Pasien mapKeObjek(ResultSet rs) throws SQLException {
        LocalDate tgl = rs.getDate("tanggal_lahir") != null
                ? rs.getDate("tanggal_lahir").toLocalDate() : null;
        return new Pasien(
                rs.getInt("id_pasien"),
                rs.getString("nama"),
                rs.getString("alamat"),
                rs.getString("no_telp"),
                tgl,
                rs.getString("jenis_kelamin")
        );
    }
}
