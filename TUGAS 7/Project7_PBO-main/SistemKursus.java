import java.util.ArrayList;
import java.util.List;

interface Sertifikat {
    void cetakSertifikat();
}

abstract class Person {
    private String nama; 

    public Person(String nama) {
        this.nama = nama;
    }

    public String getNama() { return nama; }
    
    // Abstract Method
    abstract void tampilkanPeran();
}

class Siswa extends Person implements Sertifikat {
    private String idSiswa;

    public Siswa(String nama, String idSiswa) {
        super(nama);
        this.idSiswa = idSiswa;
    }

   
    @Override
    void tampilkanPeran() {
        System.out.println("Peran: Siswa Aktif - ID: " + idSiswa);
    }

    @Override
    public void cetakSertifikat() {
        System.out.println("Mencetak Sertifikat Kelulusan untuk: " + getNama());
    }
}

class KelasIT<T extends Person> {
    private String namaKelas;

    private List<T> daftarPeserta = new ArrayList<>();

    public KelasIT(String namaKelas) {
        this.namaKelas = namaKelas;
    }

    public void tambahPeserta(T peserta) {
        daftarPeserta.add(peserta);
    }

    public void tampilkanInfoKelas() {
        System.out.println("=== KELAS: " + namaKelas + " ===");
        for (T p : daftarPeserta) {
            System.out.print("Nama: " + p.getNama() + " | ");
            p.tampilkanPeran();
        }
    }
}


public class SistemKursus {
    public static void main(String[] args) {
  
        KelasIT<Siswa> kelasJava = new KelasIT<>("Backend Java Developer");

        Siswa s1 = new Siswa("Aririzal", "20240040042");
        Siswa s2 = new Siswa("Hengky", "20240040262");

        kelasJava.tambahPeserta(s1);
        kelasJava.tambahPeserta(s2);

        kelasJava.tampilkanInfoKelas();

        System.out.println("\n--- Fitur Cetak ---");
        s1.cetakSertifikat(); 
    }
}