package com.klinik.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pemeriksaan {

    private int idPeriksa;
    private Pasien pasien;
    private Dokter dokter;
    private LocalDate tanggal;
    private String keluhan;
    private String diagnosa;
    private double biayaPeriksa;

    private List<DetailResep> daftarResep = new ArrayList<>();

    public Pemeriksaan(int idPeriksa, Pasien pasien, Dokter dokter, LocalDate tanggal,
                        String keluhan, String diagnosa, double biayaPeriksa) {
        this.idPeriksa = idPeriksa;
        this.pasien = pasien;
        this.dokter = dokter;
        this.tanggal = tanggal;
        this.keluhan = keluhan;
        this.diagnosa = diagnosa;
        this.biayaPeriksa = biayaPeriksa;
    }

    public int getIdPeriksa() {
        return idPeriksa;
    }

    public void setIdPeriksa(int idPeriksa) {
        this.idPeriksa = idPeriksa;
    }

    public Pasien getPasien() {
        return pasien;
    }

    public void setPasien(Pasien pasien) {
        this.pasien = pasien;
    }

    public Dokter getDokter() {
        return dokter;
    }

    public void setDokter(Dokter dokter) {
        this.dokter = dokter;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }

    public void setTanggal(LocalDate tanggal) {
        this.tanggal = tanggal;
    }

    public String getKeluhan() {
        return keluhan;
    }

    public void setKeluhan(String keluhan) {
        this.keluhan = keluhan;
    }

    public String getDiagnosa() {
        return diagnosa;
    }

    public void setDiagnosa(String diagnosa) {
        this.diagnosa = diagnosa;
    }

    public double getBiayaPeriksa() {
        return biayaPeriksa;
    }

    public void setBiayaPeriksa(double biayaPeriksa) {
        this.biayaPeriksa = biayaPeriksa;
    }

    public List<DetailResep> getDaftarResep() {
        return daftarResep;
    }

    public void tambahResep(DetailResep resep) {
        daftarResep.add(resep);
    }

    public double getTotalBiaya() {
        double total = biayaPeriksa;
        for (DetailResep r : daftarResep) {
            total += r.getSubtotal();
        }
        return total;
    }
}
