-- ============================================
-- Database: toko_retail
-- Jalankan file ini di MySQL sebelum menjalankan app.py
-- Cara pakai:
--   mysql -u root -p < schema.sql
-- ============================================

CREATE DATABASE IF NOT EXISTS toko_retail;
USE toko_retail;

DROP TABLE IF EXISTS barang;

CREATE TABLE barang (
    id INT AUTO_INCREMENT PRIMARY KEY,
    kode VARCHAR(10) NOT NULL UNIQUE,
    nama_barang VARCHAR(100) NOT NULL,
    harga INT NOT NULL,
    stok INT NOT NULL
);

-- Data contoh sesuai tampilan aplikasi
INSERT INTO barang (kode, nama_barang, harga, stok) VALUES
('B001', 'Roti Tawar', 10000, 100),
('B002', 'Malkist', 2000, 100),
('B003', 'Kopi Kapal Api', 3000, 100);
