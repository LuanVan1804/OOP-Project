package DU_LICH;

import java.sql.Date;
public class KhachSan {
    int maKhachSan;
    String tenKhachSan;
    Date NgayDen;
    Date NgayDi;
    double giaDatPhong;

    public KhachSan() {
        this.maKhachSan = 0;
        this.tenKhachSan = "";
        this.NgayDen = null;
        this.NgayDi = null;
        this.giaDatPhong = 0.0;
    }
    public KhachSan(int maKhachSan, String tenKhachSan, Date NgayDen, Date NgayDi, double giaDatPhong) {
        this.maKhachSan = maKhachSan;
        this.tenKhachSan = tenKhachSan;
        this.NgayDen = NgayDen;
        this.NgayDi = NgayDi;
        this.giaDatPhong = giaDatPhong;
    }
    public int getMaKhachSan() {
        return maKhachSan;
    }
    public void setMaKhachSan(int maKhachSan) {
        this.maKhachSan = maKhachSan;
    }
    public String getTenKhachSan() {
        return tenKhachSan;
    }
    public void setTenKhachSan(String tenKhachSan) {
        this.tenKhachSan = tenKhachSan;
    }
    public Date getNgayDen() {
        return NgayDen;
    }
    public void setNgayDen(Date NgayDen) {
        this.NgayDen = NgayDen;
    }
    public Date getNgayDi() {
        return NgayDi;
    }
    public void setNgayDi(Date NgayDi) {
        this.NgayDi = NgayDi;
    }
    public double getGiaDatPhong() {
        return giaDatPhong;
    }
    public void setGiaDatPhong(double giaDatPhong) {
        this.giaDatPhong = giaDatPhong;
    }
}
