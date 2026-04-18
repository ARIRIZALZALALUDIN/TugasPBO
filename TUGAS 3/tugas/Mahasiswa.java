public class Mahasiswa {
    protected String nim;
    protected String nama;
    protected int nilai;

    // Constructor sesuai materi Bagian B (halaman 5)
    public Mahasiswa(String nim, String nama, int nilai) {
        this.nim = nim;
        this.nama = nama;
        this.nilai = nilai;
    }

    // Method getter agar data bisa diambil oleh class lain
    public String getNim() { return nim; }
    public String getNama() { return nama; }
    public int getNilai() { return nilai; }
}