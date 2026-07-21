package com.penjualan.model;

public class DetailTransaksi {
    private int idBarang, jumlah;
    private String namaBarang;
    private double harga, subtotal;

    public DetailTransaksi(int idBarang, String namaBarang, int jumlah, double harga) {
        this.idBarang = idBarang;
        this.namaBarang = namaBarang;
        this.jumlah = jumlah;
        this.harga = harga;
        this.subtotal = jumlah * harga;
    }

    public int getIdBarang() { return idBarang; }
    public String getNamaBarang() { return namaBarang; }
    public int getJumlah() { return jumlah; }
    public double getHarga() { return harga; }
    public double getSubtotal() { return subtotal; }
}
