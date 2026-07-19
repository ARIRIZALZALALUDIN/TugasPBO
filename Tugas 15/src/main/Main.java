package main;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import model.ATK;
import service.ATKService;
import service.PenjualanService;

public class Main {
    private static final ATKService atkService = new ATKService();
    private static final PenjualanService penjualanService = new PenjualanService();
  

    public static void main(String[] args) {
        // Menggunakan System.in secara eksplisit
        Scanner input = new Scanner(System.in);
        int pilihan = 0;

        do { 
            System.out.println("\n==================================");
            System.out.println(" SISTEM MANAJEMEN TOKO ATK (CLI) ");
            System.out.println("==================================");
            System.out.println("1. Kelola ATK (Tambah & Lihat)");
            System.out.println("2. Penjualan (Transaksi & Riwayat)");
            System.out.println("3. Keluar");
            System.out.print("Pilih Menu [1-3]: ");

            try {
                pilihan = input.nextInt();
                switch (pilihan) {
                    case 1: menuAtk(input); break;
                    case 2: menuPenjualan(input); break;
                    case 3: System.out.println("Terima kasih! Program selesai."); break;
                    default: System.out.println("Pilihan tidak valid!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Input harus berupa angka!");
                input.nextLine(); 
            }
        } while (pilihan != 3);
    }

    private static void menuAtk(Scanner input) {
        System.out.println("\n>> SUB-MENU KELOLA ATK");
        System.out.println("1. Tambah ATK");
        System.out.println("2. Lihat Data ATK");
        System.out.print("Pilih [1-2]: ");
        int subPilihan = input.nextInt();
        input.nextLine(); // clear buffer

        if (subPilihan == 1) {
            System.out.print("Nama ATK   : "); String nama = input.nextLine();
            System.out.print("Kategori   : "); String kat = input.nextLine();
            System.out.print("Harga      : "); double harga = input.nextDouble();
            System.out.print("Stok Awal  : "); int stok = input.nextInt();
            atkService.tambahATK(nama, kat, harga, stok);
        } else if (subPilihan == 2) {
            List<ATK> daftar = atkService.tampilkanSemuaATK();
            System.out.println("\n=== DAFTAR ATK ===");
            System.out.printf("| %-4s | %-20s | %-12s | %-12s | %-5s |\n", "ID", "Nama ATK", "Kategori", "Harga", "Stok");
            System.out.println("-----------------------------------------------------------------");
            for (ATK atk : daftar) {
                atk.tampilInfo(); 
            }
            System.out.println("Total Akumulasi Stok Gudang (FUNGSI DB): " + atkService.ambilTotalStok());
        }
    }

    private static void menuPenjualan(Scanner input) {
        System.out.println("\n>> SUB-MENU PENJUALAN");
        System.out.println("1. Jual ATK (Transaksi)");
        System.out.println("2. Lihat Riwayat Penjualan");
        System.out.print("Pilih [1-2]: ");
        int subPilihan = input.nextInt();

        if (subPilihan == 1) {
            System.out.print("Masukkan ID ATK yang dibeli: "); int id = input.nextInt();
            System.out.print("Jumlah beli                : "); int jumlah = input.nextInt();
            penjualanService.inputTransaksi(id, jumlah);
        } else if (subPilihan == 2) {
            penjualanService.tampilkanRiwayatPenjualan();
        }
    }
}