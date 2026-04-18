
public class DemoProduk {
    public static void main(String[] args) {
        System.out.println("=== DEMO INHERITANCE - PRODUK ===\n");

        // 1. Produk FISIK (buku) - punya berat & ongkir
        ProdukFisik buku = new ProdukFisik("Buku Pemrograman Java", 85000, 50, 350);
        System.out.println("Produk Fisik (buku):");
        System.out.println("  " + buku.getInfo());
        System.out.println("  Ongkir terpisah: Rp " + (int) buku.hitungOngkir());

        // 2. Produk DIGITAL (e-book) - tidak ada ongkir, ada link download
        ProdukDigital ebook = new ProdukDigital("E-book Panduan OOP", 45000, 70,
                "https://toko.com/download/ebook-oop", "PDF");
        System.out.println("\nProduk Digital (e-book):");
        System.out.println("  " + ebook.getInfo());
        System.out.println("  Link unduh: " + ebook.getLinkDownload());

        // 3. Method warisan: kurangiStok() dari superclass dipakai oleh semua
        System.out.println("\nMethod warisan: kurangiStok()");
        buku.kurangiStok(2);
        ebook.kurangiStok(2);
        System.out.println("  Stok buku setelah beli 2: " + buku.getStok());
        System.out.println("  Stok ebook setelah beli 1: " + ebook.getStok());

        System.out.println("\n===");
    }
}
