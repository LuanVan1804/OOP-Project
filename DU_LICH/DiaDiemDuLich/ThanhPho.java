package DU_LICH.DiaDiemDuLich;

public class ThanhPho {
    private String maQuocGia;
    private String maTPho;
    private String tenTPho;

    public ThanhPho() {
        this.maQuocGia = "";
        this.maTPho = "";
        this.tenTPho = "";
    }

    public ThanhPho(String maQuocGia, String maTPho, String tenTPho) {
        this.maQuocGia = maQuocGia;
        this.maTPho = maTPho;
        this.tenTPho = tenTPho;
    }

    public String getMaQuocGia() { return maQuocGia; }
    public void setMaQuocGia(String maQuocGia) { this.maQuocGia = maQuocGia; }

    public String getMaTPho() { return maTPho; }
    public void setMaTPho(String maTPho) { this.maTPho = maTPho; }

    public String getTenTPho() { return tenTPho; }
    public void setTenTPho(String tenTPho) { this.tenTPho = tenTPho; }

    @Override
    public String toString() {
        return "ThanhPho{" + "maQG=" + maQuocGia + ", maTP=" + maTPho + ", ten=" + tenTPho + "}";
    }
}
