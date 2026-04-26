public class Toshiba implements Laptop {
    private int volume;
    private boolean is_power_on;

    public Toshiba() {
        this.volume = 50;
    }

    @Override
    public void powerOn() {
        is_power_on = true;
        System.out.println("Laptop Toshiba Menyala...");
        System.out.println("Sayonara!");
    }

    @Override
    public void powerOff() {
        is_power_on = false;
        System.out.println("Mematikan Toshiba...");
    }

    @Override
    public void volumeUp() {
        if (is_power_on) {
            if (this.volume == MAX_VOL) {
                System.out.println("Volume Toshiba Full!");
            } else {
                this.volume += 10;
                System.out.println("Volume Toshiba Sekarang: " + this.volume);
            }
        }
    }

    @Override
    public void volumeDown() {
        if (is_power_on) {
            if (this.volume == MIN_VOL) {
                System.out.println("Volume Toshiba 0%");
            } else {
                this.volume -= 10;
                System.out.println("Volume Toshiba Sekarang: " + this.volume);
            }
        }
    }
}
