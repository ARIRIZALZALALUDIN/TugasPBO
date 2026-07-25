package com.klinik.dao;

import com.klinik.exception.StokTidakCukupException;
import com.klinik.model.*;
import com.klinik.util.KoneksiDatabase;
import com.klinik.util.LogAktivitas;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PemeriksaanDAO {

    public void simpanTransaksi(Pemeriksaan p) throws SQLException, StokTidakCukupException {
        String sqlHeader = "INSERT INTO pemeriksaan (id_pasien, id_dokter, tanggal, keluhan, diagnosa, biaya_periksa) VALUES (?,?,?,?,?,?)";
        String sqlDetail = "INSERT INTO detail_resep (id_periksa, id_obat, jumlah, subtotal) VALUES (?,?,?,?)";

        Connection conn = null;
        try {
            conn = KoneksiDatabase.getConnection();
            conn.setAutoCommit(false); 
            int idPeriksaBaru;
            try (PreparedStatement ps = conn.prepareStatement(sqlHeader, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, p.getPasien().getId());
                ps.setInt(2, p.getDokter().getId());
                ps.setDate(3, Date.valueOf(p.getTanggal()));
                ps.setString(4, p.getKeluhan());
                ps.setString(5, p.getDiagnosa());
                ps.setDouble(6, p.getBiayaPeriksa());
                ps.executeUpdate();

                try (ResultSet keys = ps.getGeneratedKeys()) {
                    keys.next();
                    idPeriksaBaru = keys.getInt(1);
                }
            }

            ObatDAO obatDAO = new ObatDAO();
            for (DetailResep resep : p.getDaftarResep()) {
                obatDAO.kurangiStok(resep.getObat().getId(), resep.getJumlah());

                try (PreparedStatement ps = conn.prepareStatement(sqlDetail)) {
                    ps.setInt(1, idPeriksaBaru);
                    ps.setInt(2, resep.getObat().getId());
                    ps.setInt(3, resep.getJumlah());
                    ps.setDouble(4, resep.getSubtotal());
                    ps.executeUpdate();
                }
            }

            conn.commit();
            LogAktivitas.catat("Transaksi pemeriksaan baru untuk pasien: " + p.getPasien().getNama());

        } catch (SQLException | StokTidakCukupException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) conn.setAutoCommit(true);
        }
    }

    // untuk laporan sederhana, ambil semua transaksi lengkap dengan join
    public List<String[]> laporanTransaksi(String dariTanggal, String sampaiTanggal) throws SQLException {
        List<String[]> hasil = new ArrayList<>();
        String sql = "SELECT pm.id_periksa, ps.nama AS nama_pasien, dk.nama AS nama_dokter, " +
                "pm.tanggal, pm.diagnosa, pm.biaya_periksa " +
                "FROM pemeriksaan pm " +
                "JOIN pasien ps ON pm.id_pasien = ps.id_pasien " +
                "JOIN dokter dk ON pm.id_dokter = dk.id_dokter " +
                "WHERE pm.tanggal BETWEEN ? AND ? ORDER BY pm.tanggal DESC";

        try (Connection conn = KoneksiDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dariTanggal);
            ps.setString(2, sampaiTanggal);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    hasil.add(new String[]{
                            String.valueOf(rs.getInt("id_periksa")),
                            rs.getString("nama_pasien"),
                            rs.getString("nama_dokter"),
                            rs.getDate("tanggal").toString(),
                            rs.getString("diagnosa"),
                            String.valueOf(rs.getDouble("biaya_periksa"))
                    });
                }
            }
        }
        return hasil;
    }
}
