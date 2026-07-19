package service;
import database.Koneksi;
import java.sql.*;

public class PenjualanService {

    public void inputTransaksi(int idAtk, int jumlah) {
        // Kita cari dulu harganya untuk menghitung total_harga
        String sqlCari = "SELECT harga, stok FROM atk WHERE id_atk = ?";
        String sqlInsert = "INSERT INTO penjualan (id_atk, jumlah, total_harga) VALUES (?, ?, ?)";

        try (Connection conn = Koneksi.getConnection()) {
            // Cek ketersediaan barang & harga
            try (PreparedStatement pstmtCari = conn.prepareStatement(sqlCari)) {
                pstmtCari.setInt(1, idAtk);
                try (ResultSet rs = pstmtCari.executeQuery()) {
                    if (rs.next()) {
                        double harga = rs.getDouble("harga");
                        int stokSekarang = rs.getInt("stok");

                        if (stokSekarang < jumlah) {
                            System.out.println("Transaksi Gagal! Stok tidak mencukupi.");
                            return;
                        }

                        double totalHarga = harga * jumlah;

                        // Eksekusi insert transaksi
                        try (PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert)) {
                            pstmtInsert.setInt(1, idAtk);
                            pstmtInsert.setInt(2, jumlah);
                            pstmtInsert.setDouble(3, totalHarga);
                            pstmtInsert.executeUpdate();
                            System.out.println("Transaksi Berhasil! Stok berkurang otomatis via Trigger.");
                        }
                    } else {
                        System.out.println("Data ATK dengan ID tersebut tidak ditemukan!");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Kesalahan Transaksi: " + e.getMessage());
        }
    }

    public void tampilkanRiwayatPenjualan() {
        String sql = "SELECT * FROM view_penjualan"; // Memanggil View
        try (Connection conn = Koneksi.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n=== RIWAYAT PENJUALAN (VIEW) ===");
            System.out.printf("| %-4s | %-20s | %-6s | %-12s | %-12s | %-19s |\n", "ID", "Nama ATK", "Jumlah", "Harga Satuan", "Total Harga", "Tanggal");
            System.out.println("-----------------------------------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("| %03d | %-20s | %-5d | Rp%-9.2f | Rp%-10.2f | %-19s |\n",
                    rs.getInt("id_penjualan"),
                    rs.getString("nama_atk"),
                    rs.getInt("jumlah"),
                    rs.getDouble("harga"),
                    rs.getDouble("total_harga"),
                    rs.getTimestamp("tanggal").toString()
                );
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil riwayat: " + e.getMessage());
        }
    }
}