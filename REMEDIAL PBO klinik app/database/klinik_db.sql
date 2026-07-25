CREATE DATABASE IF NOT EXISTS klinik_db;
USE klinik_db;

DROP TABLE IF EXISTS detail_resep;
DROP TABLE IF EXISTS pemeriksaan;
DROP TABLE IF EXISTS obat;
DROP TABLE IF EXISTS pasien;
DROP TABLE IF EXISTS dokter;
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
    id_user INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL,
    nama_lengkap VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'admin'
);

CREATE TABLE dokter (
    id_dokter INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(100) NOT NULL,
    spesialisasi VARCHAR(100),
    no_telp VARCHAR(20)
);

CREATE TABLE pasien (
    id_pasien INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(100) NOT NULL,
    alamat VARCHAR(200),
    no_telp VARCHAR(20),
    tanggal_lahir DATE,
    jenis_kelamin VARCHAR(15)
);

CREATE TABLE obat (
    id_obat INT AUTO_INCREMENT PRIMARY KEY,
    nama_obat VARCHAR(100) NOT NULL,
    satuan VARCHAR(20),
    harga DOUBLE NOT NULL DEFAULT 0,
    stok INT NOT NULL DEFAULT 0
);

CREATE TABLE pemeriksaan (
    id_periksa INT AUTO_INCREMENT PRIMARY KEY,
    id_pasien INT NOT NULL,
    id_dokter INT NOT NULL,
    tanggal DATE NOT NULL,
    keluhan VARCHAR(255),
    diagnosa VARCHAR(255),
    biaya_periksa DOUBLE NOT NULL DEFAULT 0,
    FOREIGN KEY (id_pasien) REFERENCES pasien(id_pasien) ON DELETE CASCADE,
    FOREIGN KEY (id_dokter) REFERENCES dokter(id_dokter) ON DELETE CASCADE
);

CREATE TABLE detail_resep (
    id_detail INT AUTO_INCREMENT PRIMARY KEY,
    id_periksa INT NOT NULL,
    id_obat INT NOT NULL,
    jumlah INT NOT NULL,
    subtotal DOUBLE NOT NULL,
    FOREIGN KEY (id_periksa) REFERENCES pemeriksaan(id_periksa) ON DELETE CASCADE,
    FOREIGN KEY (id_obat) REFERENCES obat(id_obat) ON DELETE CASCADE
);

INSERT INTO `user` (username, password, nama_lengkap, role) VALUES
('admin', 'admin123', 'Administrator Klinik', 'admin');

INSERT INTO dokter (nama, spesialisasi, no_telp) VALUES
('dr. Idham Sanjaya', 'Umum', '081234567890'),
('dr. Sinta Marlina', 'Anak', '081234567891');

INSERT INTO obat (nama_obat, satuan, harga, stok) VALUES
('Paracetamol 500mg', 'Strip', 5000, 100),
('Amoxicillin 500mg', 'Strip', 12000, 80),
('Vitamin C', 'Botol', 15000, 50);