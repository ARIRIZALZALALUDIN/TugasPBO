DROP DATABASE IF EXISTS db_penjualan;
CREATE DATABASE db_penjualan;
USE db_penjualan;

CREATE TABLE user (
    id_user INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    nama_lengkap VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'kasir'
);

CREATE TABLE barang (
    id_barang INT AUTO_INCREMENT PRIMARY KEY,
    kode_barang VARCHAR(20) NOT NULL UNIQUE,
    nama_barang VARCHAR(100) NOT NULL,
    kategori VARCHAR(50),
    harga DECIMAL(12,2) NOT NULL DEFAULT 0,
    stok INT NOT NULL DEFAULT 0
);

CREATE TABLE pelanggan (
    id_pelanggan INT AUTO_INCREMENT PRIMARY KEY,
    kode_pelanggan VARCHAR(20) NOT NULL UNIQUE,
    nama_pelanggan VARCHAR(100) NOT NULL,
    alamat VARCHAR(200),
    no_telp VARCHAR(20)
);

CREATE TABLE transaksi (
    id_transaksi INT AUTO_INCREMENT PRIMARY KEY,
    no_transaksi VARCHAR(30) NOT NULL UNIQUE,
    id_pelanggan INT NOT NULL,
    id_user INT NOT NULL,
    tanggal DATETIME DEFAULT CURRENT_TIMESTAMP,
    total_bayar DECIMAL(14,2) NOT NULL DEFAULT 0,
    FOREIGN KEY (id_pelanggan) REFERENCES pelanggan(id_pelanggan),
    FOREIGN KEY (id_user) REFERENCES user(id_user)
);

CREATE TABLE detail_transaksi (
    id_detail INT AUTO_INCREMENT PRIMARY KEY,
    id_transaksi INT NOT NULL,
    id_barang INT NOT NULL,
    jumlah INT NOT NULL,
    harga_satuan DECIMAL(12,2) NOT NULL,
    subtotal DECIMAL(14,2) NOT NULL,
    FOREIGN KEY (id_transaksi) REFERENCES transaksi(id_transaksi) ON DELETE CASCADE,
    FOREIGN KEY (id_barang) REFERENCES barang(id_barang)
);

-- data awal
INSERT INTO user (username, password, nama_lengkap, role) VALUES
('admin', 'admin123', 'Administrator', 'admin'),
('kasir1', 'kasir123', 'Budi Kasir', 'kasir');

INSERT INTO barang (kode_barang, nama_barang, kategori, harga, stok) VALUES
('BRG001', 'Indomie Goreng', 'Makanan', 3000, 100),
('BRG002', 'Aqua 600ml', 'Minuman', 4000, 150),
('BRG003', 'Beras 5kg', 'Sembako', 65000, 40);

INSERT INTO pelanggan (kode_pelanggan, nama_pelanggan, alamat, no_telp) VALUES
('PLG001', 'Andi Saputra', 'Jl. Merdeka No. 10', '081234567890'),
('PLG002', 'Siti Aminah', 'Jl. Sudirman No. 5', '081298765432');

-- FUNCTION: hitung total transaksi dari detail
DELIMITER //
CREATE FUNCTION fn_hitung_total_transaksi(p_id INT)
RETURNS DECIMAL(14,2)
DETERMINISTIC READS SQL DATA
BEGIN
    DECLARE v_total DECIMAL(14,2);
    SELECT IFNULL(SUM(subtotal),0) INTO v_total FROM detail_transaksi WHERE id_transaksi = p_id;
    RETURN v_total;
END //
DELIMITER ;

-- STORED PROCEDURE: tambah detail transaksi (validasi stok otomatis)
DELIMITER //
CREATE PROCEDURE sp_tambah_detail_transaksi(IN p_trx INT, IN p_barang INT, IN p_jumlah INT)
BEGIN
    DECLARE v_harga DECIMAL(12,2);
    DECLARE v_stok INT;
    SELECT harga, stok INTO v_harga, v_stok FROM barang WHERE id_barang = p_barang;
    IF v_stok < p_jumlah THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Stok barang tidak mencukupi';
    ELSE
        INSERT INTO detail_transaksi (id_transaksi, id_barang, jumlah, harga_satuan, subtotal)
        VALUES (p_trx, p_barang, p_jumlah, v_harga, v_harga * p_jumlah);
    END IF;
END //
DELIMITER ;

-- TRIGGER: setelah detail ditambah -> kurangi stok & update total_bayar sekaligus
DELIMITER //
CREATE TRIGGER trg_setelah_detail
AFTER INSERT ON detail_transaksi
FOR EACH ROW
BEGIN
    UPDATE barang SET stok = stok - NEW.jumlah WHERE id_barang = NEW.id_barang;
    UPDATE transaksi SET total_bayar = fn_hitung_total_transaksi(NEW.id_transaksi) WHERE id_transaksi = NEW.id_transaksi;
END //
DELIMITER ;

-- VIEW: laporan penjualan
CREATE VIEW view_laporan_penjualan AS
SELECT t.id_transaksi, t.no_transaksi, t.tanggal, p.nama_pelanggan, u.nama_lengkap AS nama_kasir,
       b.nama_barang, d.jumlah, d.harga_satuan, d.subtotal, t.total_bayar
FROM transaksi t
JOIN pelanggan p ON t.id_pelanggan = p.id_pelanggan
JOIN user u ON t.id_user = u.id_user
JOIN detail_transaksi d ON t.id_transaksi = d.id_transaksi
JOIN barang b ON d.id_barang = b.id_barang
ORDER BY t.tanggal DESC;
