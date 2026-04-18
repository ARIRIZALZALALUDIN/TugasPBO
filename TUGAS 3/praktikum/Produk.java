public class Produk {
    private final String nama;
    private final double harga;
    private int stok;

    Produk(String nama, double harga, int stok) {
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
    }

    public String getInfo() {
        return "" + nama + " - Rp " + (int) harga + " (Stok: " + stok + ")";
    }

    public boolean kurangiStok(int jumlah) {
        if (jumlah <= stok) {
            stok -= jumlah;
            return true;
        }
        return false;
    }

    public String getNama() { 
        return nama; 
    }
    
    public double getHarga() { 
        return harga; 
    }
    
    public int getStok() { 
        return stok; 
    }
}
