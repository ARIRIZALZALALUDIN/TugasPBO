package com.klinik.dao;

import com.klinik.exception.StokTidakCukupException;
import com.klinik.model.Obat;
import com.klinik.util.KoneksiDatabase;
import com.klinik.util.LogAktivitas;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ObatDAO implements Cruddable<Obat> {

    @Override
    public void tambah(Obat o) throws SQLException {
        String sql = "INSERT INTO obat (nama_obat, satuan, harga, stok) VALUES (?,?,?,?)";
        try (Connection conn = KoneksiDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, o.getNamaObat());
            ps.setString(2, o.getSatuan());
            ps.setDouble(3, o.getHarga());
            ps.setInt(4, o.getStok());
            ps.executeUpdate();
            LogAktivitas.catat("Tambah data obat: " + o.getNamaObat());
        }
    }

    @Override
    public void ubah(Obat o) throws SQLException {
        String sql = "UPDATE obat SET nama_obat=?, satuan=?, harga=?, stok=? WHERE id_obat=?";
        try (Connection conn = KoneksiDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, o.getNamaObat());
            ps.setString(2, o.getSatuan());
            ps.setDouble(3, o.getHarga());
            ps.setInt(4, o.getStok());
            ps.setInt(5, o.getId());
            ps.executeUpdate();
            LogAktivitas.catat("Ubah data obat id=" + o.getId());
        }
    }

    @Override
    public void hapus(int id) throws SQLException {
        String sql = "DELETE FROM obat WHERE id_obat=?";
        try (Connection conn = KoneksiDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            LogAktivitas.catat("Hapus data obat id=" + id);
        }
    }

    @Override
    public List<Obat> tampilkanSemua() throws SQLException {
        List<Obat> daftar = new ArrayList<>();
        String sql = "SELECT * FROM obat ORDER BY id_obat DESC";
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
    public List<Obat> cari(String kataKunci) throws SQLException {
        List<Obat> daftar = new ArrayList<>();
        String sql = "SELECT * FROM obat WHERE nama_obat LIKE ?";
        try (Connection conn = KoneksiDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + kataKunci + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    daftar.add(mapKeObjek(rs));
                }
            }
        }
        return daftar;
    }

    public void kurangiStok(int idObat, int jumlah) throws SQLException, StokTidakCukupException {
        String cekSql = "SELECT stok FROM obat WHERE id_obat=?";
        try (Connection conn = KoneksiDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(cekSql)) {
            ps.setInt(1, idObat);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && rs.getInt("stok") < jumlah) {
                    throw new StokTidakCukupException("Stok obat tidak mencukupi untuk id_obat=" + idObat);
                }
            }
        }

        String updateSql = "UPDATE obat SET stok = stok - ? WHERE id_obat=?";
        try (Connection conn = KoneksiDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(updateSql)) {
            ps.setInt(1, jumlah);
            ps.setInt(2, idObat);
            ps.executeUpdate();
        }
    }

    private Obat mapKeObjek(ResultSet rs) throws SQLException {
        return new Obat(
                rs.getInt("id_obat"),
                rs.getString("nama_obat"),
                rs.getString("satuan"),
                rs.getDouble("harga"),
                rs.getInt("stok")
        );
    }
}
