import java.util.ArrayList;
import java.util.Scanner;

public class CetakNilai {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<NilaiMahasiswa> daftarMhs = new ArrayList<>();
        
        System.out.print("Masukkan jumlah mahasiswa: ");
        int jml = input.nextInt();
        input.nextLine(); 

        for (int i = 0; i < jml; i++) {
            System.out.println("\n--- Input Data Mahasiswa ke-" + (i + 1) + " ---");
            System.out.print("NIM  : "); String nim = input.nextLine();
            System.out.print("Nama : "); String nama = input.nextLine();
            System.out.print("Nilai: "); int nilai = input.nextInt();
            input.nextLine();

            // Membuat objek baru (instansiasi)
            daftarMhs.add(new NilaiMahasiswa(nim, nama, nilai));
        }

        // --- Proses Perhitungan Statistik ---
        double totalNilai = 0;
        ArrayList<String> mhsLulus = new ArrayList<>();
        ArrayList<String> mhsGagal = new ArrayList<>();
        int countA = 0, countB = 0, countD = 0;

        System.out.println("\n--- HASIL DATA MAHASISWA ---");
        for (NilaiMahasiswa m : daftarMhs) {
            System.out.println("NIM: " + m.getNim());
            System.out.println("Nama: " + m.getNama());
            System.out.println("Nilai: " + m.getNilai());
            System.out.println("Grade: " + m.getGrade());
            System.out.println("===");

            totalNilai += m.getNilai();
            if (m.getStatus().equals("Lulus")) mhsLulus.add(m.getNama());
            else mhsGagal.add(m.getNama());

            if (m.getGrade().equals("A")) countA++;
            else if (m.getGrade().equals("B")) countB++;
            else if (m.getGrade().equals("D")) countD++;
        }

        // Output Ringkasan
        System.out.println("Jumlah Mahasiswa: " + daftarMhs.size());
        System.out.println("Jumlah Mahasiswa yg Lulus: " + mhsLulus.size() + " yaitu " + String.join(", ", mhsLulus));
        System.out.println("Jumlah Mahasiswa yg Tidak Lulus: " + mhsGagal.size() + " yaitu " + String.join(", ", mhsGagal));
        System.out.println("Jumlah Mahasiswa dengan Nilai A = " + countA);
        System.out.println("Jumlah Mahasiswa dengan Nilai B = " + countB);
        System.out.println("Jumlah Mahasiswa dengan Nilai D = " + countD);
        
        if (daftarMhs.size() > 0) {
            System.out.println("Rata-rata nilai mahasiswa adalah: " + (totalNilai / daftarMhs.size()));
        }
        input.close();
    }//
}