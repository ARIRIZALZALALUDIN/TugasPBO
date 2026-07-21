package com.penjualan.model;

public class Pelanggan extends Person {
    private int id;
    private String kode;

    public Pelanggan(int id, String kode, String nama, String alamat, String noTelp) {
        super(nama, alamat, noTelp);
        this.id = id;
        this.kode = kode;
    }

    public int getId() { return id; }
    public String getKode() { return kode; }
    public void setKode(String kode) { this.kode = kode; }

    @Override
    public String getInfo() { return kode + " - " + getNama(); }

    @Override
    public String toString() { return getNama() + " (" + kode + ")"; }
}
