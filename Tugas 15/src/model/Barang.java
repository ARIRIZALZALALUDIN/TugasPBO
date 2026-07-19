package model;
public class Barang {
    private String nama;
    private double harga;

    public Barang(String nama, double harga) {
        this.nama = nama;
        this.harga = harga;
    }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public double getHarga() { return harga; }
    public void setHarga(double harga) { this.harga = harga; }

    public void tampilInfo() {
        System.out.println("Nama Barang: " + nama);
        System.out.println("Harga: Rp" + harga);
    }
}