package DU_LICH;

public class ChiPhiKHTour {
    private String maKHTour;
    private String maNhaHang;
    private String maKhachSan;
    private double tongTienPhong;
    private double tongTienAn;

    public ChiPhiKHTour() {
        this.maKHTour = "";
        this.maNhaHang = "";
        this.maKhachSan = "";
        this.tongTienPhong = 0.0;
        this.tongTienAn = 0.0;
    }

    public ChiPhiKHTour(String maKHTour, String maNhaHang, String maKhachSan, double tongTienPhong, double tongTienAn) {
        this.maKHTour = maKHTour;
        this.maNhaHang = maNhaHang;
        this.maKhachSan = maKhachSan;
        this.tongTienPhong = tongTienPhong;
        this.tongTienAn = tongTienAn;
    }

    // Getter/Setter
    public String getMaKHTour() { return maKHTour; }
    public void setMaKHTour(String maKHTour) { this.maKHTour = maKHTour; }
    public String getMaNhaHang() { return maNhaHang; }
    public void setMaNhaHang(String maNhaHang) { this.maNhaHang = maNhaHang; }
    public String getMaKhachSan() { return maKhachSan; }
    public void setMaKhachSan(String maKhachSan) { this.maKhachSan = maKhachSan; }
    public double getTongTienPhong() { return tongTienPhong; }
    public void setTongTienPhong(double tongTienPhong) { this.tongTienPhong = tongTienPhong; }
    public double getTongTienAn() { return tongTienAn; }
    public void setTongTienAn(double tongTienAn) { this.tongTienAn = tongTienAn; }

    public void xuatThongTin() {
        System.out.println("===== THONG TIN CHI PHI KE HOACH TOUR =====");
        System.out.println("Ma ke hoach tour: " + maKHTour);
        System.out.println("Ma nha hang: " + maNhaHang);
        System.out.println("Ma khach san: " + maKhachSan);
        System.out.println("Tong tien phong: " + String.format("%,.0f VND", tongTienPhong));
        System.out.println("Tong tien an: " + String.format("%,.0f VND", tongTienAn));
        System.out.println("TONG CHI: " + String.format("%,.0f VND", (tongTienPhong + tongTienAn)));
    }
}