public class ProdukFisik extends Produk {
    private final double beratGram;  

    ProdukFisik(String nama, double harga, int stok, double beratGram) {
        super(nama, harga, stok); 
        this.beratGram = beratGram;
    }

    @Override
    public String getInfo() {
        return super.getInfo() + " | Berat: " + (int) beratGram + " g | Ongkir: Rp " + (int) hitungOngkir();
    }

    public double hitungOngkir() {
        // Rp 5.000 per 100 gram, minimal Rp 10.000
        double ongkir = (beratGram / 100) * 5000;
        return ongkir < 10000 ? 10000 : ongkir;
    }

    public double getBeratGram() { 
        return beratGram; 
    }
}
