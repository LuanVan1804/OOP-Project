package DU_LICH;
import java.sql.Date;

import DU_LICH.NH_KS_PT.KhachSan;
import DU_LICH.NH_KS_PT.NhaHang;
public class ChiPhiKHTour {
    KeHoachTour keHoachTour;
    Date ngay;
    KhachSan makhachSan;
    NhaHang maNhaHang;

    public ChiPhiKHTour() {
        this.keHoachTour = new KeHoachTour();
        this.ngay = null;
        this.makhachSan = new KhachSan();
        this.maNhaHang = new NhaHang();
    }

    public ChiPhiKHTour(KeHoachTour keHoachTour, Date ngay, KhachSan makhachSan, NhaHang maNhaHang) {
        this.keHoachTour = keHoachTour;
        this.ngay = ngay;
        this.makhachSan = makhachSan;
        this.maNhaHang = maNhaHang;
    }
    public KeHoachTour getKeHoachTour() {
        return keHoachTour;
    }
    public void setKeHoachTour(KeHoachTour keHoachTour) {
        this.keHoachTour = keHoachTour;
    }
    public Date getNgay() {
        return ngay;
    }
    public void setNgay(Date ngay) {
        this.ngay = ngay;
    }
    public KhachSan getMakhachSan() {
        return makhachSan;
    }
    public void setMakhachSan(KhachSan makhachSan) {
        this.makhachSan = makhachSan;
    }
    public NhaHang getMaNhaHang() {
        return maNhaHang;
    }
    public void setMaNhaHang(NhaHang maNhaHang) {
        this.maNhaHang = maNhaHang;
    }

    public double tongChiChoNgay() {
        double tongTien = 0.0;
        return tongTien;
    }
    public double tongTienPhong() {
        double tongTien = 0.0;
        return tongTien;
    }

    public double tongDiLai() {
        double tongTien = 0.0;
        return tongTien;
    }
}
