public class BankBNI extends Bank {
    @Override
    public void sukuBunga() {
        System.out.println("Suku Bunga dari BNI adalah: 4%");
    }

    @Override
    public void transferUang(int jumlah, String rekeningTujuan, String bankTujuan) {
        // Logika Overriding: bankTujuan dipaksa menjadi BNI
        String bankFixed = "BNI";
        int biayaTransfer = 0; // Bonus: Gratis biaya admin sesama BNI
        System.out.println("[BNI] Berhasil transfer Rp" + jumlah + " ke " + bankFixed + " (" + rekeningTujuan + "). Biaya: Rp" + biayaTransfer);
    }
}