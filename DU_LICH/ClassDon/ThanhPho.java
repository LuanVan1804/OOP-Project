package DU_LICH.ClassDon;

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

    // Copy constructor
    public ThanhPho(ThanhPho other) {
        if (other == null) return;
        this.maQuocGia = other.maQuocGia;
        this.maTPho = other.maTPho;
        this.tenTPho = other.tenTPho;
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

    public void hienThi() {
        System.out.println("Ma quoc gia: " + maQuocGia);
        System.out.println("Ma thanh pho: " + maTPho);
        System.out.println("Ten thanh pho: " + tenTPho);
    }
}
