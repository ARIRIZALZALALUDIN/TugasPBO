# Aplikasi Penjualan Berbasis Java GUI dan MySQL

Project Java Swing + MySQL untuk memenuhi tugas:
Login, CRUD User/Barang/Pelanggan, Pencarian, Transaksi Penjualan, Laporan Penjualan,
dengan penerapan Stored Procedure, Function, Trigger, View, dan konsep OOP lengkap.

> Versi ini dirapikan agar lebih ringkas untuk live coding: form CRUD (Barang/Pelanggan/User)
> dan DAO memakai superclass abstrak (`MasterForm`, `BaseDAO`) sehingga tidak ada kode yang
> diulang tiga kali. Fitur dan output aplikasi tetap sama persis seperti sebelumnya.

## Struktur Folder

```
AplikasiPenjualan/
├── src/com/penjualan/
│   ├── Main.java                 -> Entry point aplikasi
│   ├── model/                    -> Class, Object, Inheritance, Polimorfisme, Enkapsulasi
│   │   ├── Person.java           (abstract class - superclass User & Pelanggan)
│   │   ├── User.java             (extends Person)
│   │   ├── Pelanggan.java        (extends Person)
│   │   ├── Barang.java
│   │   ├── Transaksi.java
│   │   └── DetailTransaksi.java
│   ├── exception/
│   │   └── AplikasiException.java    -> Exception Handling (custom exception)
│   ├── db/
│   │   └── KoneksiDatabase.java  -> Koneksi JDBC ke MySQL
│   ├── dao/                      -> Package terpisah untuk akses data (CRUD)
│   │   ├── BaseDAO.java          (abstract - superclass semua DAO, INHERITANCE+POLIMORFISME)
│   │   ├── UserDAO.java          (extends BaseDAO<User>)
│   │   ├── BarangDAO.java        (extends BaseDAO<Barang>)
│   │   ├── PelangganDAO.java     (extends BaseDAO<Pelanggan>)
│   │   └── TransaksiDAO.java     (extends BaseDAO<Transaksi>, memanggil Stored Procedure & View)
│   ├── util/
│   │   └── Session.java          -> menyimpan user yang sedang login
│   └── gui/                      -> Java Swing
│       ├── LoginForm.java
│       ├── MainForm.java
│       ├── MasterForm.java       (abstract - superclass form CRUD, INHERITANCE+POLIMORFISME)
│       ├── UserForm.java         (extends MasterForm<User>)
│       ├── BarangForm.java       (extends MasterForm<Barang>)
│       ├── PelangganForm.java    (extends MasterForm<Pelanggan>)
│       ├── TransaksiForm.java
│       └── LaporanForm.java
└── database/
    └── db_penjualan.sql          -> Database lengkap (tabel, SP, function, trigger, view)
```

## Cara Menjalankan

### 1. Siapkan Database
1. Buka MySQL (via XAMPP/phpMyAdmin/MySQL Workbench/terminal).
2. Import file `database/db_penjualan.sql`.
   - Ini otomatis membuat database `db_penjualan`, seluruh tabel, data awal,
     1 function (`fn_hitung_total_transaksi`), 1 stored procedure
     (`sp_tambah_detail_transaksi`), 1 trigger (`trg_setelah_detail`),
     dan 1 view (`view_laporan_penjualan`).

### 2. Siapkan Project Java (Eclipse / NetBeans / IntelliJ)
1. Buat project Java baru, lalu copy folder `src/com/penjualan` ke dalam
   folder `src` project kamu (atau import project ini langsung).
2. Download driver **MySQL Connector/J** (file `mysql-connector-j-x.x.x.jar`)
   dari https://dev.mysql.com/downloads/connector/j/ lalu tambahkan ke
   Library/Classpath project kamu (Add External JAR).
3. Sesuaikan kredensial database di `KoneksiDatabase.java` (baris `USER` dan
   `PASSWORD`) sesuai konfigurasi MySQL kamu (default `root` tanpa password).
4. Jalankan class `com.penjualan.Main` (klik kanan -> Run).

### 3. Login
Akun default (dari data awal di SQL):
- Username: `admin`   | Password: `admin123`  (role: admin)
- Username: `kasir1`  | Password: `kasir123`  (role: kasir)

## Pemetaan Ketentuan Tugas ke Kode

| Ketentuan                | Lokasi di Kode                                              |
|---------------------------|---------------------------------------------------------------|
| Login                     | `gui/LoginForm.java`, `dao/UserDAO.login()`                  |
| CRUD Data User            | `gui/UserForm.java` (extends `MasterForm`), `dao/UserDAO.java` |
| CRUD Data Barang          | `gui/BarangForm.java` (extends `MasterForm`), `dao/BarangDAO.java` |
| CRUD Data Pelanggan       | `gui/PelangganForm.java` (extends `MasterForm`), `dao/PelangganDAO.java` |
| Pencarian Data Barang     | `BarangForm.cari()` -> `BarangDAO.cari()`                    |
| Pencarian Data Pelanggan  | `PelangganForm.cari()` -> `PelangganDAO.cari()`               |
| Transaksi Penjualan       | `gui/TransaksiForm.java`, `dao/TransaksiDAO.simpan()`         |
| Laporan Penjualan         | `gui/LaporanForm.java`, `dao/TransaksiDAO.laporan()` (baca dari VIEW) |
| Class & Object             | Seluruh file di `model/`                                      |
| Enkapsulasi                | Semua atribut `private` + getter/setter di seluruh model      |
| Inheritance                | `User`/`Pelanggan` extends `Person`; `BarangForm`/`PelangganForm`/`UserForm` extends `MasterForm`; semua DAO extends `BaseDAO` |
| Polimorfisme               | `getInfo()` di-override beda oleh `User`/`Pelanggan`; method abstrak `MasterForm` & `BaseDAO` (`mapRow`, `simpan`, dll) di-override beda tiap turunan |
| Package                    | `model`, `exception`, `db`, `dao`, `util`, `gui`               |
| Exception Handling         | `exception/AplikasiException.java`, dipakai di DAO & GUI (try-catch) |
| JDBC                       | `db/KoneksiDatabase.java`                                      |
| Stored Procedure           | `sp_tambah_detail_transaksi` (SQL) dipanggil dari `TransaksiDAO` |
| Function                   | `fn_hitung_total_transaksi` (dipakai trigger)                  |
| Trigger                    | `trg_setelah_detail`                                           |
| View                       | `view_laporan_penjualan`                                       |

## Catatan
- Untuk output tugas: source code + file `.sql` diunggah ke GitHub, video live
  coding ke YouTube, laporan PDF (deskripsi, implementasi PBO, screenshot,
  kesimpulan) ke Google Drive, lalu semua link dikirim ke email dosen sesuai
  instruksi soal.
- Saat live coding: cukup ketik `MasterForm`/`BaseDAO` satu kali sambil
  dijelaskan, lalu tiga form CRUD & DAO turunannya tinggal "isi bagian yang
  beda" mengikuti pola yang sama — jauh lebih cepat daripada menulis 3 form
  penuh dari nol satu-satu.
- Silakan tetap dikembangkan lagi (validasi tambahan, styling GUI, cetak
  struk, dll.) agar terlihat sebagai hasil kerja kamu sendiri.
