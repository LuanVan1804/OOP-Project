
package DU_LICH;

public class NhaHang {
    int maNhaHang;
    String tenNhaHang;
    String Combo;
    double giaCombo;

    public NhaHang() {
        this.maNhaHang = 0;
        this.tenNhaHang = "";
        this.Combo = "";
        this.giaCombo = 0.0;
    }
    public NhaHang(int maNhaHang, String tenNhaHang, String Combo, double giaCombo) {
        this.maNhaHang = maNhaHang;
        this.tenNhaHang = tenNhaHang;
        this.Combo = Combo;
        this.giaCombo = giaCombo;
    }
    public int getMaNhaHang() {
        return maNhaHang;
    }
    public void setMaNhaHang(int maNhaHang) {
        this.maNhaHang = maNhaHang;
    }
    public String getTenNhaHang() {
        return tenNhaHang;
    }
    public void setTenNhaHang(String tenNhaHang) {
        this.tenNhaHang = tenNhaHang;
    }
    public String getCombo() {
        return Combo;
    }
    public void setCombo(String Combo) {
        this.Combo = Combo;
    }
    public double getGiaCombo() {
        return giaCombo;
    }
    public void setGiaCombo(double giaCombo) {
        this.giaCombo = giaCombo;
    }
    
}
