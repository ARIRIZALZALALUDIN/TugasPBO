package com.penjualan.exception;

// Custom exception (Exception Handling) untuk error bisnis: login gagal, stok kurang, dll.
public class AplikasiException extends Exception {
    public AplikasiException(String message) {
        super(message);
    }
}
