package com.penjualan.dao;

import com.penjualan.db.KoneksiDatabase;
import com.penjualan.exception.AplikasiException;
import com.penjualan.model.DetailTransaksi;
import com.penjualan.model.Transaksi;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TransaksiDAO extends BaseDAO<Transaksi> {

    // Header disimpan manual, tiap item lewat STORED PROCEDURE sp_tambah_detail_transaksi
    // -> TRIGGER trg_setelah_detail otomatis mengurangi stok & update total_bayar
    public void simpan(Transaksi t) throws AplikasiException {
        try {
            Connection conn = KoneksiDatabase.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO transaksi (no_transaksi,id_pelanggan,id_user,total_bayar) VALUES (?,?,?,0)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, t.getNoTransaksi()); ps.setInt(2, t.getIdPelanggan()); ps.setInt(3, t.getIdUser());
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            int idTransaksi = keys.next() ? keys.getInt(1) : 0;

            for (DetailTransaksi d : t.getDetail()) {
                CallableStatement cs = conn.prepareCall("{call sp_tambah_detail_transaksi(?,?,?)}");
                cs.setInt(1, idTransaksi); cs.setInt(2, d.getIdBarang()); cs.setInt(3, d.getJumlah());
                cs.execute();
            }
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            throw new AplikasiException(e.getMessage());
        }
    }

    public List<Transaksi> laporan() { return semua("SELECT * FROM view_laporan_penjualan"); } // baca dari VIEW

    @Override protected Transaksi mapRow(ResultSet rs) throws SQLException {
        Transaksi t = new Transaksi();
        t.setNoTransaksi(rs.getString("no_transaksi"));
        t.setTanggal(rs.getTimestamp("tanggal").toString());
        t.setNamaPelanggan(rs.getString("nama_pelanggan"));
        t.setNamaKasir(rs.getString("nama_kasir"));
        t.setTotalBayar(rs.getDouble("total_bayar"));
        t.tambah(new DetailTransaksi(0, rs.getString("nama_barang"), rs.getInt("jumlah"), rs.getDouble("harga_satuan")));
        return t;
    }

    public double totalSemua() {
        try (Statement st = KoneksiDatabase.getConnection().createStatement();
             ResultSet rs = st.executeQuery("SELECT IFNULL(SUM(total_bayar),0) t FROM transaksi")) {
            if (rs.next()) return rs.getDouble("t");
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public String nomorBaru() { return "TRX" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); }
}
