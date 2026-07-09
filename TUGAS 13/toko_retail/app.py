"""
Aplikasi CLI Sederhana - Toko Retail
Database: toko_retail (MySQL)

Cara pakai:
1. Pastikan MySQL sudah berjalan.
2. Import schema.sql ke MySQL:
       mysql -u root -p < schema.sql
3. Install library yang dibutuhkan:
       pip install mysql-connector-python
4. Sesuaikan konfigurasi koneksi di bagian DB_CONFIG di bawah.
5. Jalankan aplikasi:
       python app.py
"""

import mysql.connector
from mysql.connector import Error

# ==============================
# KONFIGURASI KONEKSI DATABASE
# ==============================
DB_CONFIG = {
    "host": "localhost",
    "user": "root",
    "password": "",          
    "database": "toko_retail"
}


def get_connection():
    """Membuat koneksi ke database MySQL."""
    try:
        conn = mysql.connector.connect(**DB_CONFIG)
        return conn
    except Error as e:
        print(f"Gagal terhubung ke database: {e}")
        return None


def clear_screen():
    print("\n" * 2)


def tampilkan_menu():
    print("=" * 30)
    print("      MENU TOKO RETAIL")
    print("=" * 30)
    print("1. Tampil Semua Data")
    print("2. Tambah Data")
    print("3. Cari Data")
    print("4. Ubah Data")
    print("5. Hapus Data")
    print("0. Keluar")
    print("=" * 30)


def cetak_tabel(data):
    """Mencetak data barang dalam bentuk tabel rapi."""
    if not data:
        print("Tidak ada data untuk ditampilkan.\n")
        return

    print("-" * 65)
    print(f"{'#':<3}{'Kode':<8}{'Nama Barang':<25}{'Harga':>10}{'Stok':>10}")
    print("-" * 65)
    for idx, row in enumerate(data, start=1):
        kode, nama, harga, stok = row["kode"], row["nama_barang"], row["harga"], row["stok"]
        print(f"{idx:<3}{kode:<8}{nama:<25}{harga:>10}{stok:>10}")
    print("-" * 65)
    print(f"Total: {len(data)} barang\n")


# ==============================
# 1. TAMPIL SEMUA DATA
# ==============================
def tampil_semua_data(conn):
    cursor = conn.cursor(dictionary=True)
    cursor.execute("SELECT kode, nama_barang, harga, stok FROM barang ORDER BY id")
    data = cursor.fetchall()
    cursor.close()

    print("\nDAFTAR BARANG TOKO RETAIL")
    cetak_tabel(data)


# ==============================
# 2. TAMBAH DATA
# ==============================
def tambah_data(conn):
    print("\n--- Tambah Data Barang ---")
    kode = input("Kode Barang   : ").strip()
    nama = input("Nama Barang   : ").strip()

    try:
        harga = int(input("Harga         : ").strip())
        stok = int(input("Stok          : ").strip())
    except ValueError:
        print("Harga dan Stok harus berupa angka!\n")
        return

    try:
        cursor = conn.cursor()
        cursor.execute(
            "INSERT INTO barang (kode, nama_barang, harga, stok) VALUES (%s, %s, %s, %s)",
            (kode, nama, harga, stok)
        )
        conn.commit()
        cursor.close()
        print(f"Data '{nama}' berhasil ditambahkan.\n")
    except Error as e:
        print(f"Gagal menambah data: {e}\n")


# ==============================
# 3. CARI DATA
# ==============================
def cari_data(conn):
    print("\n--- Cari Data Barang ---")
    keyword = input("Masukkan kode atau nama barang: ").strip()

    cursor = conn.cursor(dictionary=True)
    cursor.execute(
        "SELECT kode, nama_barang, harga, stok FROM barang "
        "WHERE kode LIKE %s OR nama_barang LIKE %s ORDER BY id",
        (f"%{keyword}%", f"%{keyword}%")
    )
    data = cursor.fetchall()
    cursor.close()

    print("\nHASIL PENCARIAN")
    cetak_tabel(data)


# ==============================
# 4. UBAH DATA
# ==============================
def ubah_data(conn):
    print("\n--- Ubah Data Barang ---")
    kode = input("Masukkan kode barang yang akan diubah: ").strip()

    cursor = conn.cursor(dictionary=True)
    cursor.execute("SELECT * FROM barang WHERE kode = %s", (kode,))
    row = cursor.fetchone()
    cursor.close()

    if not row:
        print(f"Data dengan kode '{kode}' tidak ditemukan.\n")
        return

    print(f"Data saat ini -> Nama: {row['nama_barang']}, Harga: {row['harga']}, Stok: {row['stok']}")
    print("Kosongkan input jika tidak ingin mengubah field tersebut.")

    nama_baru = input(f"Nama Barang baru [{row['nama_barang']}]: ").strip()
    harga_input = input(f"Harga baru [{row['harga']}]: ").strip()
    stok_input = input(f"Stok baru [{row['stok']}]: ").strip()

    nama_baru = nama_baru if nama_baru else row["nama_barang"]
    try:
        harga_baru = int(harga_input) if harga_input else row["harga"]
        stok_baru = int(stok_input) if stok_input else row["stok"]
    except ValueError:
        print("Harga dan Stok harus berupa angka!\n")
        return

    try:
        cursor = conn.cursor()
        cursor.execute(
            "UPDATE barang SET nama_barang=%s, harga=%s, stok=%s WHERE kode=%s",
            (nama_baru, harga_baru, stok_baru, kode)
        )
        conn.commit()
        cursor.close()
        print("Data berhasil diubah.\n")
    except Error as e:
        print(f"Gagal mengubah data: {e}\n")


# ==============================
# 5. HAPUS DATA
# ==============================
def hapus_data(conn):
    print("\n--- Hapus Data Barang ---")
    kode = input("Masukkan kode barang yang akan dihapus: ").strip()

    cursor = conn.cursor(dictionary=True)
    cursor.execute("SELECT * FROM barang WHERE kode = %s", (kode,))
    row = cursor.fetchone()
    cursor.close()

    if not row:
        print(f"Data dengan kode '{kode}' tidak ditemukan.\n")
        return

    konfirmasi = input(f"Yakin ingin menghapus '{row['nama_barang']}'? (y/n): ").strip().lower()
    if konfirmasi != "y":
        print("Penghapusan dibatalkan.\n")
        return

    try:
        cursor = conn.cursor()
        cursor.execute("DELETE FROM barang WHERE kode = %s", (kode,))
        conn.commit()
        cursor.close()
        print("Data berhasil dihapus.\n")
    except Error as e:
        print(f"Gagal menghapus data: {e}\n")


# ==============================
# PROGRAM UTAMA
# ==============================
def main():
    conn = get_connection()
    if conn is None:
        print("Program berhenti karena tidak bisa terhubung ke database.")
        return

    while True:
        tampilkan_menu()
        pilihan = input("Pilihan : ").strip()

        if pilihan == "1":
            tampil_semua_data(conn)
        elif pilihan == "2":
            tambah_data(conn)
        elif pilihan == "3":
            cari_data(conn)
        elif pilihan == "4":
            ubah_data(conn)
        elif pilihan == "5":
            hapus_data(conn)
        elif pilihan == "0":
            print("Terima kasih, sampai jumpa!")
            break
        else:
            print("Pilihan tidak valid, silakan coba lagi.\n")

    conn.close()


if __name__ == "__main__":
    main()
