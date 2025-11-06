package DU_LICH;

import DU_LICH.Nguoi.HDV;
import DU_LICH.Nguoi.KhachHang;

public class HoaDon {
    int maHD;
    KeHoachTour maKHTour;
    HDV maHDV;
    KhachHang maKHDaiDien;
    int soKhach;
    

    public HoaDon() {
        this.maHD = 0;
        this.maKHTour = new KeHoachTour();
        this.maHDV = new HDV();
        this.maKHDaiDien = new KhachHang();
        this.soKhach = 0;
       
    }
    public HoaDon(int maHD, KeHoachTour maKHTour, HDV maHDV, KhachHang maKHDaDien, int soKhach) {
        this.maHD = maHD;
        this.maKHTour = maKHTour;
        this.maHDV = maHDV;
        this.maKHDaiDien = maKHDaDien;
        this.soKhach = soKhach;
        
    }
    public int getMaHD() {
        return maHD;
    }
    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }
    public KeHoachTour getMaKHTour() {
        return maKHTour;
    }
    public void setMaKHTour(KeHoachTour maKHTour) {
        this.maKHTour = maKHTour;
    }
    public HDV getMaHDV() {
        return maHDV;
    }
    public void setMaHDV(HDV maHDV) {
        this.maHDV = maHDV;
    }
    public KhachHang getMaKHDaiDien() {
        return maKHDaiDien;
    }
    public void setMaKHDaiDien(KhachHang maKHDaiDien) {
        this.maKHDaiDien = maKHDaiDien;
    }
    public int getSoKhach() {
        return soKhach;
    }
    public void setSoKhach(int soKhach) {
        this.soKhach = soKhach;
    }
   
    public double tongTienVe() {
        double tien = 0.0;
        return tien;
    }

}
