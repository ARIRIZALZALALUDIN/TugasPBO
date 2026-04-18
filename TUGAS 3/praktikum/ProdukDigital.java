public class ProdukDigital extends Produk {
    private final String linkDownload;
    private final String formatFile; 
    
    ProdukDigital(String nama, double harga, int stok, String linkDownload, String formatFile) {
        super(nama, harga, stok);
        this.linkDownload = linkDownload;
        this.formatFile = formatFile;
    }

    @Override
    public String getInfo() {
        return super.getInfo() + " | Format: " + formatFile + " | Ongkir: Gratis";
    }

    public String getLinkDownload() {
        return linkDownload;
    }

    public String getFormatFile() { 
        return formatFile; 
    }
}
