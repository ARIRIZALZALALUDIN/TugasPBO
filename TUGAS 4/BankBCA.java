public class BankBCA extends Bank {
    @Override
    public void sukuBunga() {
        System.out.println("Suku Bunga dari BCA adalah: 4.5%");
    }

    @Override
    public void transferUang(int jumlah, String rekeningTujuan, String bankTujuan) {
        // Logika Overriding: bankTujuan dipaksa menjadi BCA
        String bankFixed = "BCA";
        int biayaTransfer = 6500; // Bonus: Biaya admin antar bank
        System.out.println("[BCA] Berhasil transfer Rp" + jumlah + " ke " + bankFixed + " (" + rekeningTujuan + "). Biaya: Rp" + biayaTransfer);
    }
}