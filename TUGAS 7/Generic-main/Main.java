
import java.util.ArrayDeque;
import java.util.ArrayList;  

public class Main {
    public static void main(String[] args) {
        
        
        ArrayList<String> listMahasiswa = new ArrayList<>();
        
        
        listMahasiswa.add("Ari Rizal");
        listMahasiswa.add("Hengky");
        listMahasiswa.add("Fatur");

        System.out.println("=== Contoh ArrayList ===");
        System.out.println("Daftar Mahasiswa: " + listMahasiswa);
        System.out.println("Jumlah data: " + listMahasiswa.size());
        System.out.println("Mahasiswa pertama: " + listMahasiswa.get(0));

        ArrayDeque<String> antreanKantin = new ArrayDeque<>();

        antreanKantin.addLast("Mahasiswa 1");
        antreanKantin.addLast("Mahasiswa 2");
        
        antreanKantin.addFirst("Dosen (Prioritas)");

        System.out.println("\n=== Contoh ArrayDeque ===");
        System.out.println("Urutan Antrean: " + antreanKantin);
        
        String dilayani = antreanKantin.pollFirst();
        System.out.println("Sedang dilayani: " + dilayani);
        System.out.println("Sisa antrean: " + antreanKantin);
    }
}