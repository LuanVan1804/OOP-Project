package DU_LICH;

import DU_LICH.NH_KS_PT.PhuongTien;
import DU_LICH.Nguoi.HDV;
import DU_LICH.TourDuLich.Tour;

public class KeHoachTour {
    int maKHTour;
    Tour maTour;
    HDV maHDV;
    PhuongTien maPTien;
    

    public KeHoachTour() {
        this.maKHTour = 0;
        this.maTour = null; // tour là abstract nên không thể khởi tạo trực tiếp
        this.maHDV = new HDV();
        this.maPTien = new PhuongTien();
       
    }
    public KeHoachTour(int maKHTour, Tour maTour, HDV maHDV, PhuongTien maPTien) {
        this.maKHTour = maKHTour;
        this.maTour = maTour;
        this.maHDV = maHDV;
        this.maPTien = maPTien;
        
    }
    public int getMaKHTour() {
        return maKHTour;
    }
    public void setMaKHTour(int maKHTour) {
        this.maKHTour = maKHTour;
    }
    public Tour getMaTour() {
        return maTour;
    }
    public void setMaTour(Tour maTour) {
        this.maTour = maTour;
    }
    public HDV getMaHDV() {
        return maHDV;
    }
    public void setMaHDV(HDV maHDV) {
        this.maHDV = maHDV;
    }
    public PhuongTien getMaPTien() {
        return maPTien;
    }
    public void setMaPTien(PhuongTien maPTien) {
        this.maPTien = maPTien;
    }
    
     int tongSoSoVe() {
        int tongSoSoVe = 0;
        return tongSoSoVe;
    }
    int tongVeDaDat() {
        int tongVeDaDat = 0;
        return tongVeDaDat;
    }
    double tongTienThuDuoc() {
        double tongTienThuDuoc = 0.0;
        return tongTienThuDuoc;
    }
    double tongChiPhiAn() {
        double tongChiPhiAn = 0.0;
        return tongChiPhiAn;
    }
    double tongChiPhiO() {
        double tongChiPhiO = 0.0;
        return tongChiPhiO;
    }
    double tongChiPhiDiLai() {
        double tongChiPhiDiLai = 0.0;
        return tongChiPhiDiLai;
    }
    double tongChiPhiChoTatCa() {
        double tongChiPhiChoTatCa = 0.0;

        return tongChiPhiChoTatCa;
    }
}
