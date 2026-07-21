package com.penjualan.model;

// Superclass untuk User dan Pelanggan (INHERITANCE + ENKAPSULASI)
public abstract class Person {
    private String nama, alamat, noTelp;

    public Person(String nama, String alamat, String noTelp) {
        this.nama = nama;
        this.alamat = alamat;
        this.noTelp = noTelp;
    }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
    public String getNoTelp() { return noTelp; }
    public void setNoTelp(String noTelp) { this.noTelp = noTelp; }

    // POLIMORFISME: wajib di-override oleh subclass
    public abstract String getInfo();
}
