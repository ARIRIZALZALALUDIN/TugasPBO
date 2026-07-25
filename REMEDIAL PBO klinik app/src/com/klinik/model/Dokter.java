package com.klinik.model;

public class Dokter extends Person {

    private String spesialisasi;
    private String noTelp;

    public Dokter(int id, String nama, String spesialisasi, String noTelp) {
        super(id, nama);
        this.spesialisasi = spesialisasi;
        this.noTelp = noTelp;
    }

    public String getSpesialisasi() {
        return spesialisasi;
    }

    public void setSpesialisasi(String spesialisasi) {
        this.spesialisasi = spesialisasi;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    @Override
    public String getInfo() {
        return "dr. " + getNama() + " - Spesialis " + spesialisasi;
    }

    @Override
    public String toString() {
        return getNama() + " (" + spesialisasi + ")";
    }
}
