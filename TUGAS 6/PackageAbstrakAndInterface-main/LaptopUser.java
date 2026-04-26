public class LaptopUser {
    private final Laptop laptop;

    public LaptopUser(Laptop laptop) {
        this.laptop = laptop;
    }

    void turnOnLaptop() {
        this.laptop.powerOn();
    }

    void turnOffLaptop() {
        this.laptop.powerOff();
    }

    void makeLaptopLouder() {
        this.laptop.volumeUp();
    }

    void makeLaptopSilence() {
        this.laptop.volumeDown();
    }

    
    public static void main(String[] args) {
        Laptop myLenovo = new Lenovo();
        LaptopUser user = new LaptopUser(myLenovo);

        user.turnOnLaptop();
        user.makeLaptopLouder();
        user.makeLaptopSilence();
        user.turnOffLaptop();
    }
}
