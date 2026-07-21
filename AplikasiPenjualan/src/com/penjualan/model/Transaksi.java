package com.penjualan.model;

import java.util.ArrayList;
import java.util.List;

public class Transaksi {
    private String noTransaksi, tanggal, namaPelanggan, namaKasir;
    private int idPelanggan, idUser;
    private double totalBayar;
    private List<DetailTransaksi> detail = new ArrayList<>();

    public String getNoTransaksi() { return noTransaksi; }
    public void setNoTransaksi(String noTransaksi) { this.noTransaksi = noTransaksi; }
    public String getTanggal() { return tanggal; }
    public void setTanggal(String tanggal) { this.tanggal = tanggal; }
    public String getNamaPelanggan() { return namaPelanggan; }
    public void setNamaPelanggan(String namaPelanggan) { this.namaPelanggan = namaPelanggan; }
    public String getNamaKasir() { return namaKasir; }
    public void setNamaKasir(String namaKasir) { this.namaKasir = namaKasir; }
    public int getIdPelanggan() { return idPelanggan; }
    public void setIdPelanggan(int idPelanggan) { this.idPelanggan = idPelanggan; }
    public int getIdUser() { return idUser; }
    public void setIdUser(int idUser) { this.idUser = idUser; }
    public double getTotalBayar() { return totalBayar; }
    public void setTotalBayar(double totalBayar) { this.totalBayar = totalBayar; }
    public List<DetailTransaksi> getDetail() { return detail; }
    public void tambah(DetailTransaksi d) { detail.add(d); }
}
