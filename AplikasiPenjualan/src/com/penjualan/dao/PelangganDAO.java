package com.penjualan.dao;

import com.penjualan.db.KoneksiDatabase;
import com.penjualan.model.Pelanggan;

import java.sql.*;
import java.util.List;

public class PelangganDAO extends BaseDAO<Pelanggan> {
    private static final String SQL_CARI =
            "SELECT * FROM pelanggan WHERE kode_pelanggan LIKE ? OR nama_pelanggan LIKE ? ORDER BY id_pelanggan";

    public List<Pelanggan> cari(String keyword) { return cari(SQL_CARI, keyword); }

    @Override protected Pelanggan mapRow(ResultSet rs) throws SQLException {
        return new Pelanggan(rs.getInt("id_pelanggan"), rs.getString("kode_pelanggan"),
                rs.getString("nama_pelanggan"), rs.getString("alamat"), rs.getString("no_telp"));
    }

    public void simpan(Pelanggan p) throws SQLException {
        if (p.getId() == 0) {
            KoneksiDatabase.eksekusi("INSERT INTO pelanggan (kode_pelanggan,nama_pelanggan,alamat,no_telp) VALUES (?,?,?,?)",
                    p.getKode(), p.getNama(), p.getAlamat(), p.getNoTelp());
        } else {
            KoneksiDatabase.eksekusi("UPDATE pelanggan SET kode_pelanggan=?,nama_pelanggan=?,alamat=?,no_telp=? WHERE id_pelanggan=?",
                    p.getKode(), p.getNama(), p.getAlamat(), p.getNoTelp(), p.getId());
        }
    }

    public void hapus(int id) throws SQLException { KoneksiDatabase.eksekusi("DELETE FROM pelanggan WHERE id_pelanggan=?", id); }
}
