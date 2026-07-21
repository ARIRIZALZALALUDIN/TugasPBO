package com.penjualan.model;

public class Barang {
    private int id;
    private String kode, nama, kategori;
    private double harga;
    private int stok;

    public Barang(int id, String kode, String nama, String kategori, double harga, int stok) {
        this.id = id;
        this.kode = kode;
        this.nama = nama;
        this.kategori = kategori;
        this.harga = harga;
        this.stok = stok;
    }

    public int getId() { return id; }
    public String getKode() { return kode; }
    public void setKode(String kode) { this.kode = kode; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public String getKategori() { return kategori; }
    public void setKategori(String kategori) { this.kategori = kategori; }
    public double getHarga() { return harga; }
    public void setHarga(double harga) { this.harga = harga; }
    public int getStok() { return stok; }
    public void setStok(int stok) { this.stok = stok; }

    @Override
    public String toString() { return nama + " - Rp" + harga + " (Stok:" + stok + ")"; }
}
