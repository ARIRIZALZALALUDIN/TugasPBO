public class NilaiMahasiswa extends Mahasiswa {
    private String grade;
    private String status;

    public NilaiMahasiswa(String nim, String nama, int nilai) {
        // Memanggil constructor Super Class menggunakan keyword 'super' 
        super(nim, nama, nilai);
        tentukanGrade();
    }

    // Logika penentuan grade sesuai instruksi tugas
    private void tentukanGrade() {
        if (nilai >= 80 && nilai <= 100) {
            grade = "A";
            status = "Lulus";
        } else if (nilai >= 70 && nilai <= 79) {
            grade = "B";
            status = "Lulus";
        } else if (nilai >= 60 && nilai <= 69) {
            grade = "C";
            status = "Lulus";
        } else if (nilai >= 50 && nilai <= 59) {
            grade = "D";
            status = "Tidak Lulus";
        } else if (nilai >= 0 && nilai < 50) {
            grade = "E";
            status = "Tidak Lulus";
        } else {
            grade = "Salah";
            status = "Input nilai anda salah";
        }
    }

    public String getGrade() { return grade; }
    public String getStatus() { return status; }
}