public class Main {
    public static void main(String[] args) {
        // Menguji Kelas Induk (Bank)
        Bank bankUmum = new Bank();
        System.out.println("--- Test Bank Umum (Overloading) ---");
        bankUmum.transferUang(100000, "990011");
        bankUmum.transferUang(500000, "880022", "Mandiri", "Bayar Hutang");
        
        // PENTING: Tambahkan ini karena diminta di soal poin 1.a.4
        bankUmum.sukuBunga();

        System.out.println("\n--- Test Bank BNI (Overriding) ---");
        BankBNI bni = new BankBNI();
        bni.sukuBunga();
        bni.transferUang(250000, "112233", "Sembarang"); 

        System.out.println("\n--- Test Bank BCA (Overriding) ---");
        BankBCA bca = new BankBCA();
        bca.sukuBunga();
        bca.transferUang(750000, "445566", "Sembarang");
    }
}