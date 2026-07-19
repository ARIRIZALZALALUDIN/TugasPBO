package model;
public class ATK extends Barang {
    private final int idAtk;
    private final String kategori;
    private final int stok;

    public ATK(int idAtk, String nama, String kategori, double harga, int stok) {
        super(nama, harga);
        this.idAtk = idAtk;
        this.kategori = kategori;
        this.stok = stok;
    }

    public int getIdAtk() { return idAtk; }
    public String getKategori() { return kategori; }
    public int getStok() { return stok; }

   @Override
public void tampilInfo() {
    System.out.printf("| %03d  | %-20s | %-12s | Rp%-10.2f | %-5d |\n", 
            idAtk, getNama(), kategori, getHarga(), stok);
    }
}