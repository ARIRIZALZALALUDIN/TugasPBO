package com.klinik.model;

public class DetailResep {

    private int idDetail;
    private int idPeriksa;
    private Obat obat;
    private int jumlah;
    private double subtotal;

    public DetailResep(int idDetail, int idPeriksa, Obat obat, int jumlah) {
        this.idDetail = idDetail;
        this.idPeriksa = idPeriksa;
        this.obat = obat;
        this.jumlah = jumlah;
        this.subtotal = obat.getHarga() * jumlah; // auto hitung subtotal
    }

    public int getIdDetail() {
        return idDetail;
    }

    public void setIdDetail(int idDetail) {
        this.idDetail = idDetail;
    }

    public int getIdPeriksa() {
        return idPeriksa;
    }

    public void setIdPeriksa(int idPeriksa) {
        this.idPeriksa = idPeriksa;
    }

    public Obat getObat() {
        return obat;
    }

    public void setObat(Obat obat) {
        this.obat = obat;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
        this.subtotal = obat.getHarga() * jumlah;
    }

    public double getSubtotal() {
        return subtotal;
    }
}
