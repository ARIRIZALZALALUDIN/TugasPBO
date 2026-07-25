package com.klinik.util;

public class ValidasiInput {

    public static boolean isKosong(String teks) {
        return teks == null || teks.trim().isEmpty();
    }

    public static boolean isNoTelpValid(String noTelp) {
        if (isKosong(noTelp)) return false;
        String bersih = noTelp.trim();
        return bersih.matches("^[0-9]{10,13}$");
    }

    public static boolean isNamaValid(String nama) {
        if (isKosong(nama)) return false;
        return nama.trim().matches("^[a-zA-Z .]+$");
    }

    public static String toTitleCase(String teks) {
        if (isKosong(teks)) return teks;
        String[] kata = teks.trim().toLowerCase().split("\\s+");
        StringBuilder hasil = new StringBuilder();
        for (String k : kata) {
            hasil.append(Character.toUpperCase(k.charAt(0)))
                 .append(k.substring(1))
                 .append(" ");
        }
        return hasil.toString().trim();
    }

    public static boolean isAngkaValid(String teks) {
        if (isKosong(teks)) return false;
        return teks.trim().matches("^[0-9]+$");
    }
}
