package DU_LICH.DiaDiemDuLich;

public class ThanhPho {
    private int maQuocGia;
    private int maTPho;
    private String tenTPho;

    public ThanhPho() {
        this.maQuocGia = 0;
        this.maTPho = 0;
        this.tenTPho = "";
    }

    public ThanhPho(int maQuocGia, int maTPho, String tenTPho) {
        this.maQuocGia = maQuocGia;
        this.maTPho = maTPho;
        this.tenTPho = tenTPho;
    }

    public int getMaQuocGia() { return maQuocGia; }
    public void setMaQuocGia(int maQuocGia) { this.maQuocGia = maQuocGia; }

    public int getMaTPho() { return maTPho; }
    public void setMaTPho(int maTPho) { this.maTPho = maTPho; }

    public String getTenTPho() { return tenTPho; }
    public void setTenTPho(String tenTPho) { this.tenTPho = tenTPho; }

    @Override
    public String toString() {
        return "ThanhPho{" + "maQG=" + maQuocGia + ", maTP=" + maTPho + ", ten=" + tenTPho + "}";
    }
}
