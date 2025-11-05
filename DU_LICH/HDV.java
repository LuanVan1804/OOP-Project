package DU_LICH;

public class HDV {
    int maHDV;
    String tenHDV;
    String soDienThoai;
    String CCCD;
    String gioiTinh;
    double kinhNghiem;
    public HDV() {
        this.maHDV = 0;
        this.tenHDV = "";
        this.soDienThoai = "";
        this.CCCD = "";
        this.gioiTinh = "";
        this.kinhNghiem = 0.0;
    }
    public HDV(int maHDV, String tenHDV, String soDienThoai, String CCCD, String gioiTinh, double kinhNghiem) {
        this.maHDV = maHDV;
        this.tenHDV = tenHDV;
        this.soDienThoai = soDienThoai;
        this.CCCD = CCCD;
        this.gioiTinh = gioiTinh;
        this.kinhNghiem = kinhNghiem;
    }
    public int getMaHDV() {
        return maHDV;
    }
    public void setMaHDV(int maHDV) {
        this.maHDV = maHDV;
    }
    public String getTenHDV() {
        return tenHDV;
    }
    public void setTenHDV(String tenHDV) {
        this.tenHDV = tenHDV;
    }
    public String getSoDienThoai() {
        return soDienThoai;
    }
    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }
    public String getCCCD() {
        return CCCD;
    }
    public void setCCCD(String CCCD) {
        this.CCCD = CCCD;
    }
    public String getGioiTinh() {
        return gioiTinh;
    }
    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }
    public double getKinhNghiem() {
        return kinhNghiem;
    }
    public void setKinhNghiem(double kinhNghiem) {
        this.kinhNghiem = kinhNghiem;
    }
    
}
