import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Laptop laptopKu = null;

        System.out.println("Pilih Laptop (1. Lenovo, 2. Toshiba, 3. MacBook): ");
        int pilihan = input.nextInt();
        input.nextLine(); 

        if (pilihan == 1) {
            laptopKu = new Lenovo();
        } else if (pilihan == 2) {
            laptopKu = new Toshiba();
        } else if (pilihan == 3) {
            laptopKu = new Macbook();
        } else {
            System.out.println("Pilihan tidak ada, default ke Lenovo.");
            laptopKu = new Lenovo();
        }

        LaptopUser user = new LaptopUser(laptopKu);

        
        String action;
        while (true) {
            System.out.print("\nInput (ON/OFF/UP/DOWN/EXIT): ");
            action = input.nextLine();

            if (action.equalsIgnoreCase("ON")) {
                user.turnOnLaptop();
            } else if (action.equalsIgnoreCase("OFF")) {
                user.turnOffLaptop();
            } else if (action.equalsIgnoreCase("UP")) {
                user.makeLaptopLouder();
            } else if (action.equalsIgnoreCase("DOWN")) {
                user.makeLaptopSilence();
            } else if (action.equalsIgnoreCase("EXIT")) {
                System.out.println("Program selesai.");
                break;
            } else {
                System.out.println("Perintah salah!");
            }
        }
        input.close();
    }
}