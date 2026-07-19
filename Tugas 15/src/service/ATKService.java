package service;
import database.Koneksi;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.ATK;

public class ATKService {

    public void tambahATK(String nama, String kategori, double harga, int stok) {
        String sql = "{CALL tambah_atk(?, ?, ?, ?)}";
        try (Connection conn = Koneksi.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            cstmt.setString(1, nama);
            cstmt.setString(2, kategori);
            cstmt.setDouble(3, harga);
            cstmt.setInt(4, stok);
            cstmt.execute();
            System.out.println("Data ATK berhasil ditambahkan via Stored Procedure!");
        } catch (SQLException e) {
            System.out.println("Gagal tambah ATK: " + e.getMessage());
        }
    }

    public List<ATK> tampilkanSemuaATK() {
        List<ATK> listAtk = new ArrayList<>();
        String sql = "SELECT * FROM atk";
        try (Connection conn = Koneksi.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ATK atk = new ATK(
                    rs.getInt("id_atk"),
                    rs.getString("nama_atk"),
                    rs.getString("kategori"),
                    rs.getDouble("harga"),
                    rs.getInt("stok")
                );
                listAtk.add(atk);
            }
        } catch (SQLException e) {
            System.out.println("Gagal memuat data: " + e.getMessage());
        }
        return listAtk;
    }

    public int ambilTotalStok() {
        String sql = "{? = call total_stok()}";
        try (Connection conn = Koneksi.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            cstmt.registerOutParameter(1, Types.INTEGER);
            cstmt.execute();
            return cstmt.getInt(1);
        } catch (SQLException e) {
            System.out.println("Gagal mengambil fungsi total stok: " + e.getMessage());
            return 0;
        }
    }
}