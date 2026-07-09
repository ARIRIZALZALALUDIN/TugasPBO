# Aplikasi CLI Toko Retail

Aplikasi CLI (Command Line Interface) sederhana untuk mengelola data barang toko retail, menggunakan Python dan database MySQL `toko_retail`.

## Fitur Menu
1. Tampil Semua Data
2. Tambah Data
3. Cari Data
4. Ubah Data
5. Hapus Data
0. Keluar

## Struktur Project
```
toko_retail_cli/
├── app.py           # Program utama (CLI)
├── schema.sql       # Script pembuatan database & tabel + data contoh
├── requirements.txt # Daftar library Python yang dibutuhkan
└── README.md
```

## Cara Menjalankan

### 1. Siapkan Database
Pastikan MySQL Server sudah terinstall dan berjalan. Import file `schema.sql`:
```bash
mysql -u root -p < schema.sql
```
Ini akan membuat database `toko_retail`, tabel `barang`, dan mengisi 3 data contoh (Roti Tawar, Malkist, Kopi Kapal Api).

### 2. Install Dependency Python
```bash
pip install -r requirements.txt
```

### 3. Sesuaikan Koneksi Database
Buka `app.py`, sesuaikan bagian `DB_CONFIG` sesuai user/password MySQL kamu:
```python
DB_CONFIG = {
    "host": "localhost",
    "user": "root",
    "password": "",   # isi sesuai password MySQL kamu
    "database": "toko_retail"
}
```

### 4. Jalankan Aplikasi
```bash
python app.py
```

## Contoh Tampilan
```
==============================
      MENU TOKO RETAIL
==============================
1. Tampil Semua Data
2. Tambah Data
3. Cari Data
4. Ubah Data
5. Hapus Data
0. Keluar
==============================
Pilihan : 1

DAFTAR BARANG TOKO RETAIL
-----------------------------------------------------------------
#  Kode    Nama Barang                   Harga      Stok
-----------------------------------------------------------------
1  B001    Roti Tawar                    10000       100
2  B002    Malkist                        2000       100
3  B003    Kopi Kapal Api                 3000       100
-----------------------------------------------------------------
Total: 3 barang
```

## Langkah Upload ke GitHub
1. Buat repository baru di GitHub, misal `toko-retail-cli`.
2. Di folder project, jalankan:
```bash
git init
git add .
git commit -m "Aplikasi CLI Toko Retail"
git branch -M main
git remote add origin https://github.com/USERNAME/toko-retail-cli.git
git push -u origin main
```
3. Tambahkan folder `screenshots/` berisi screenshot tampilan menu 1-5.
4. Salin link repository GitHub ke edlink sesuai instruksi tugas.
