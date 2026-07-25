package com.klinik.model;

public class Obat {

    private int id;
    private String namaObat;
    private String satuan;
    private double harga;
    private int stok;

    public Obat(int id, String namaObat, String satuan, double harga, int stok) {
        this.id = id;
        this.namaObat = namaObat;
        this.satuan = satuan;
        this.harga = harga;
        this.stok = stok;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaObat() {
        return namaObat;
    }

    public void setNamaObat(String namaObat) {
        this.namaObat = namaObat;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        if (harga < 0) throw new IllegalArgumentException("Harga tidak boleh minus");
        this.harga = harga;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public void kurangiStok(int jumlah) {
        this.stok -= jumlah;
    }

    @Override
    public String toString() {
        return namaObat + " - Rp" + harga + " (" + satuan + ")";
    }
}
