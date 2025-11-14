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

    // Getter & Setter
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

    public double getTongChiPhi() { return tongTienPhong + tongTienAn; }

    public void xuatThongTin() {
        System.out.printf("%s | %s | %s | Tong an: %,.0f VND | Tong phong: %,.0f VND | Tong: %,.0f VND%n",
                maKHTour, maNhaHang, maKhachSan, tongTienAn, tongTienPhong, getTongChiPhi());
    }

    @Override
    public String toString() {
        return String.format("%s | %s | %s | Tong an: %,.0f VND | Tong phong: %,.0f VND | Tong: %,.0f VND",
                maKHTour, maNhaHang, maKhachSan, tongTienAn, tongTienPhong, getTongChiPhi());
    }
}