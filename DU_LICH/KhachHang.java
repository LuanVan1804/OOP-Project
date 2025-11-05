package DU_LICH;
public class KhachHang {
    int maKH;
    String tenKH;
    String soDienThoai;
    String CCCD;
    String gioiTinh;

    public KhachHang() {
        this.maKH = 0;
        this.tenKH = "";
        this.soDienThoai = "";
        this.CCCD = "";
        this.gioiTinh = "";
    }

    public KhachHang(int maKH, String tenKH, String soDienThoai, String CCCD, String gioiTinh) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.soDienThoai = soDienThoai;
        this.CCCD = CCCD;
        this.gioiTinh = gioiTinh;
    }

    public int getMaKH() { return maKH; }
    public void setMaKH(int maKH) { this.maKH = maKH; }

    public String getTenKH() { return tenKH; }
    public void setTenKH(String tenKH) { this.tenKH = tenKH; }

    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }

    public String getCCCD() { return CCCD; }
    public void setCCCD(String CCCD) { this.CCCD = CCCD; }

    public String getGioiTinh() { return gioiTinh; }
    public void setGioiTinh(String gioiTinh) { this.gioiTinh = gioiTinh; }
}
