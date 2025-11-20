package DU_LICH.ClassDon;

public class ChiPhiKHTour {
    private String maKHTour;
    private String maNhaHang;
    private String maKhachSan;
    private String maPhuongTien;
    private double tongTienAn;
    private double tongTienPhong;
    private double tongTienPhuongTien;
    private double tienTour;        // NEW: tiền từ Tour (lấy từ giá tour * số vé)
    private double tongChi;         // Tổng chi = ăn + ở + phương tiện + tiền tour

    public ChiPhiKHTour() {
        this.tongChi = 0.0;
        this.tienTour = 0.0;
    }

    public ChiPhiKHTour(String maKHTour, String maNhaHang, String maKhachSan, String maPhuongTien,
                        double tongTienAn, double tongTienPhong, double tongTienPhuongTien, double tienTour) {
        this.maKHTour = maKHTour;
        this.maNhaHang = maNhaHang;
        this.maKhachSan = maKhachSan;
        this.maPhuongTien = maPhuongTien;
        this.tongTienAn = tongTienAn;
        this.tongTienPhong = tongTienPhong;
        this.tongTienPhuongTien = tongTienPhuongTien;
        this.tienTour = tienTour;
        capNhatTongChi();
    }

    // Getter & Setter
    public String getMaKHTour() { return maKHTour; }
    public void setMaKHTour(String maKHTour) { this.maKHTour = maKHTour; }
    public String getMaNhaHang() { return maNhaHang; }
    public void setMaNhaHang(String maNhaHang) { this.maNhaHang = maNhaHang; }
    public String getMaKhachSan() { return maKhachSan; }
    public void setMaKhachSan(String maKhachSan) { this.maKhachSan = maKhachSan; }
    public String getMaPhuongTien() { return maPhuongTien; }
    public void setMaPhuongTien(String maPhuongTien) { this.maPhuongTien = maPhuongTien; }

    public double getTongTienAn() { return tongTienAn; }
    public void setTongTienAn(double tongTienAn) { this.tongTienAn = tongTienAn; capNhatTongChi(); }
    public double getTongTienPhong() { return tongTienPhong; }
    public void setTongTienPhong(double tongTienPhong) { this.tongTienPhong = tongTienPhong; capNhatTongChi(); }
    public double getTongTienPhuongTien() { return tongTienPhuongTien; }
    public void setTongTienPhuongTien(double tongTienPhuongTien) { this.tongTienPhuongTien = tongTienPhuongTien; capNhatTongChi(); }
    public double getTienTour() { return tienTour; }
    public void setTienTour(double tienTour) { this.tienTour = tienTour; capNhatTongChi(); }

    public double getTongChi() { return tongChi; }

    // Tự động cập nhật tổng chi
    private void capNhatTongChi() {
        this.tongChi = tongTienAn + tongTienPhong + tongTienPhuongTien + tienTour;
    }

    // Hiển thị đẹp
    public void hienThi() {
        System.out.printf("%-10s | %-10s | %-10s | %-15s | %,12.0f | %,12.0f | %,12.0f | %,12.0f | %,14.0f%n",
                maKHTour,
                maNhaHang.isEmpty() ? "-" : maNhaHang,
                maKhachSan.isEmpty() ? "-" : maKhachSan,
                maPhuongTien.isEmpty() ? "-" : maPhuongTien,
                tongTienAn, tongTienPhong, tongTienPhuongTien, tienTour, tongChi);
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%.0f,%.0f,%.0f,%.0f",
                maKHTour, maNhaHang, maKhachSan, maPhuongTien,
                tongTienAn, tongTienPhong, tongTienPhuongTien, tienTour);
    }
}