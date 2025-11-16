package DU_LICH;

public class ChiPhiKHTour {
    private String maKHTour;
    private String maNhaHang;
    private String maKhachSan;
    private String maPhuongTien;
    private double tongTienPhong;
    private double tongTienAn;
    private double tongTienPhuongTien;

    public ChiPhiKHTour() {
        this.maKHTour = "";
        this.maNhaHang = "";
        this.maKhachSan = "";
        this.maPhuongTien = "";
        this.tongTienPhong = 0.0;
        this.tongTienAn = 0.0;
        this.tongTienPhuongTien = 0.0;
    }

    public ChiPhiKHTour(String maKHTour, String maNhaHang, String maKhachSan, String maPhuongTien, double tongTienPhong, double tongTienAn, double tongTienPhuongTien) {
        this.maKHTour = maKHTour;
        this.maNhaHang = maNhaHang;
        this.maKhachSan = maKhachSan;
        this.maPhuongTien = maPhuongTien;
        this.tongTienPhong = tongTienPhong;
        this.tongTienAn = tongTienAn;
        this.tongTienPhuongTien = tongTienPhuongTien;
    }
    //constructor sao chép
    public ChiPhiKHTour(ChiPhiKHTour other) {
        this.maKHTour = other.maKHTour;
        this.maNhaHang = other.maNhaHang;
        this.maKhachSan = other.maKhachSan;
        this.tongTienPhong = other.tongTienPhong;
        this.tongTienAn = other.tongTienAn;
        this.maPhuongTien = other.maPhuongTien;
        this.tongTienPhuongTien = other.tongTienPhuongTien;
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

    public double getTongTienPhuongTien() { return tongTienPhuongTien; }

    public String getMaPhuongTien() { return maPhuongTien; }

    public void setMaPhuongTien(String maPhuongTien) { this.maPhuongTien = maPhuongTien; }

    public void setTongTienPhuongTien(double tongTienPhuongTien) { this.tongTienPhuongTien = tongTienPhuongTien; }

    public void xuatThongTin() {
        System.out.printf("%s | %s | %s | %s | Tong an: %,.0f VND | Tong phong: %,.0f VND | Tong phuong tien: %,.0f VND | Tong: %,.0f VND%n",
            maKHTour, maNhaHang, maKhachSan, (maPhuongTien == null ? "" : maPhuongTien), tongTienAn, tongTienPhong, tongTienPhuongTien, (getTongChiPhi() + tongTienPhuongTien));
    }

    @Override
    public String toString() {
        return String.format("%s | %s | %s | %s | Tong an: %,.0f VND | Tong phong: %,.0f VND | Tong phuong tien: %,.0f VND | Tong: %,.0f VND",
            maKHTour, maNhaHang, maKhachSan, (maPhuongTien == null ? "" : maPhuongTien), tongTienAn, tongTienPhong, tongTienPhuongTien, (getTongChiPhi() + tongTienPhuongTien));
    }
}