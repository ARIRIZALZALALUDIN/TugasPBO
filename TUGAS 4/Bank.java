public class Bank {
    // Overloading 1: Sesama Bank
    public void transferUang(int jumlah, String rekeningTujuan) {
        System.out.println("Transfer Rp" + jumlah + " ke rekening " + rekeningTujuan + " (Sesama Bank).");
    }

    // Overloading 2: Bank Berbeda
    public void transferUang(int jumlah, String rekeningTujuan, String bankTujuan) {
        System.out.println("Transfer Rp" + jumlah + " ke rekening " + rekeningTujuan + " di bank " + bankTujuan + ".");
    }

    // Overloading 3: Dengan Berita
    public void transferUang(int jumlah, String rekeningTujuan, String bankTujuan, String berita) {
        System.out.println("Transfer Rp" + jumlah + " ke " + bankTujuan + " (" + rekeningTujuan + ") Pesan: " + berita);
    }

    public void sukuBunga() {
        System.out.println("Suku Bunga standar adalah 3%");
    }
}