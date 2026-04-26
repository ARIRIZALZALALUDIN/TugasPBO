public class Macbook implements Laptop {
    private int volume;
    private boolean is_power_on;

    public Macbook() {
        this.volume = 50;
    }

    @Override
    public void powerOn() {
        is_power_on = true;
        System.out.println("MacBook is Starting...");
        System.out.println("Welcome to macOS");
    }

    @Override
    public void powerOff() {
        is_power_on = false;
        System.out.println("MacBook Shutting Down...");
    }

    @Override
    public void volumeUp() {
        if (is_power_on) {
            if (this.volume == MAX_VOL) {
                System.out.println("Volume is at Max Level");
            } else {
                this.volume += 10;
                System.out.println("MacBook Volume: " + this.volume);
            }
        }
    }

    @Override
    public void volumeDown() {
        if (is_power_on) {
            if (this.volume == MIN_VOL) {
                System.out.println("Volume is Muted");
            } else {
                this.volume -= 10;
                System.out.println("MacBook Volume: " + this.volume);
            }
        }
    }
}
