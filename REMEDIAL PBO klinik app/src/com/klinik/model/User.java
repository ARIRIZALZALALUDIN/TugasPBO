package com.klinik.model;

public class User {

    private int idUser;
    private String username;
    private String password;
    private String namaLengkap;
    private String role;

    public User(int idUser, String username, String password, String namaLengkap, String role) {
        this.idUser = idUser;
        this.username = username;
        this.password = password;
        this.namaLengkap = namaLengkap;
        this.role = role;
    }

    public int getIdUser() {
        return idUser;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public String getRole() {
        return role;
    }
}
