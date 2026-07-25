package com.klinik.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogAktivitas {

    private static final String NAMA_FILE = "log_aktivitas.txt";

    public static void catat(String aktivitas) {
        String waktu = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        try (FileWriter fw = new FileWriter(NAMA_FILE, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println("[" + waktu + "] " + aktivitas);
        } catch (IOException e) {
            System.out.println("Gagal menulis log: " + e.getMessage());
        }
    }
}
