package com.klinik.model;

import java.time.LocalDate;

public class Pasien extends Person {

    private String alamat;
    private String noTelp;
    private LocalDate tanggalLahir;
    private String jenisKelamin;

    public Pasien(int id, String nama, String alamat, String noTelp,
                   LocalDate tanggalLahir, String jenisKelamin) {
        super(id, nama); 
        this.alamat = alamat;
        this.noTelp = noTelp;
        this.tanggalLahir = tanggalLahir;
        this.jenisKelamin = jenisKelamin;
    }

    public Pasien(String nama, String alamat, String noTelp,
                   LocalDate tanggalLahir, String jenisKelamin) {
        this(0, nama, alamat, noTelp, tanggalLahir, jenisKelamin);
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public LocalDate getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(LocalDate tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    @Override
    public String getInfo() {
        return "Pasien #" + getId() + " - " + getNama() + " (" + jenisKelamin + ")";
    }

    @Override
    public String toString() {
        return getNama();
    }
}
