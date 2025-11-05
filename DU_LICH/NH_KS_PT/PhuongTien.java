

package DU_LICH.NH_KS_PT;

public class PhuongTien {
    int maPTien;
    String tenPTien;
    String loaiPTien;
    double chiPhiSuDung;
    public PhuongTien() {
        this.maPTien = 0;
        this.tenPTien = "";
        this.loaiPTien = "";
        this.chiPhiSuDung = 0.0;
    }
    public PhuongTien(int maPTien, String tenPTien, String loaiPTien, double chiPhiSuDung) {
        this.maPTien = maPTien;
        this.tenPTien = tenPTien;
        this.loaiPTien = loaiPTien;
        this.chiPhiSuDung = chiPhiSuDung;
    }
    public int getMaPTien() {
        return maPTien;
    }
    public void setMaPTien(int maPTien) {
        this.maPTien = maPTien;
    }
    public String getTenPTien() {
        return tenPTien;
    }
    public void setTenPTien(String tenPTien) {
        this.tenPTien = tenPTien;
    }
    public String getLoaiPTien() {
        return loaiPTien;
    }
    public void setLoaiPTien(String loaiPTien) {
        this.loaiPTien = loaiPTien;
    }
    public double getChiPhiSuDung() {
        return chiPhiSuDung;
    }
    public void setChiPhiSuDung(double chiPhiSuDung) {
        this.chiPhiSuDung = chiPhiSuDung;
    }
    
}
