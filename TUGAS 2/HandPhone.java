public class HandPhone {
    String jenis_hp;
    int tahun_pembuatan;

    public void setDataHP(String jenis, int tahun) {
        this.jenis_hp = jenis;
        this.tahun_pembuatan = tahun;
    }

    public String getJenisHP() {
        return jenis_hp;
    }

    public int getTahunPembuatan() {
        return tahun_pembuatan;
    }

    public static void main(String[] args) {
        HandPhone hp = new HandPhone();

        hp.setDataHP("Samsung", 2023);

        System.out.println("Jenis HP: " + hp.getJenisHP());
        System.out.println("Tahun: " + hp.getTahunPembuatan());
    }
}