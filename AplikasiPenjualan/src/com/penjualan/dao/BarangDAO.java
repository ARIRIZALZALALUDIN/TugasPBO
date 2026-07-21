package com.penjualan.dao;

import com.penjualan.db.KoneksiDatabase;
import com.penjualan.model.Barang;

import java.sql.*;
import java.util.List;

public class BarangDAO extends BaseDAO<Barang> {
    private static final String SQL_CARI =
            "SELECT * FROM barang WHERE kode_barang LIKE ? OR nama_barang LIKE ? ORDER BY id_barang";

    public List<Barang> cari(String keyword) { return cari(SQL_CARI, keyword); } // null/kosong = tampilkan semua

    @Override protected Barang mapRow(ResultSet rs) throws SQLException {
        return new Barang(rs.getInt("id_barang"), rs.getString("kode_barang"), rs.getString("nama_barang"),
                rs.getString("kategori"), rs.getDouble("harga"), rs.getInt("stok"));
    }

    public void simpan(Barang b) throws SQLException {
        if (b.getId() == 0) {
            KoneksiDatabase.eksekusi("INSERT INTO barang (kode_barang,nama_barang,kategori,harga,stok) VALUES (?,?,?,?,?)",
                    b.getKode(), b.getNama(), b.getKategori(), b.getHarga(), b.getStok());
        } else {
            KoneksiDatabase.eksekusi("UPDATE barang SET kode_barang=?,nama_barang=?,kategori=?,harga=?,stok=? WHERE id_barang=?",
                    b.getKode(), b.getNama(), b.getKategori(), b.getHarga(), b.getStok(), b.getId());
        }
    }

    public void hapus(int id) throws SQLException { KoneksiDatabase.eksekusi("DELETE FROM barang WHERE id_barang=?", id); }
}
