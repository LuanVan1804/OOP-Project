package DU_LICH.ClassDon;

public class ChiTietHD {
    private String maHD;
    private String maKHTour;
    private int[] dsMaKhachHang;  // Danh sách mã khách hàng trong đoàn
    private double giaVe;
    private double tongTien;      // Tự tính = số khách * giá vé (sẽ cộng chi phí ở QuanLy)

    public ChiTietHD() {
        this.dsMaKhachHang = new int[0];
        this.tongTien = 0.0;
    }

    public ChiTietHD(String maHD, String maKHTour, int[] dsMaKhachHang, double giaVe) {
        this.maHD = maHD;
        this.maKHTour = maKHTour;
        this.dsMaKhachHang = (dsMaKhachHang != null) ? dsMaKhachHang.clone() : new int[0];
        this.giaVe = giaVe;
        capNhatTongTien();
    }

    // Getter & Setter
    public String getMaHD() { return maHD; }
    public void setMaHD(String maHD) { this.maHD = maHD; }

    public String getMaKHTour() { return maKHTour; }
    public void setMaKHTour(String maKHTour) { this.maKHTour = maKHTour; }

    public int[] getDsMaKhachHang() { return dsMaKhachHang.clone(); }
    public void setDsMaKhachHang(int[] dsMaKhachHang) {
        this.dsMaKhachHang = (dsMaKhachHang != null) ? dsMaKhachHang.clone() : new int[0];
        capNhatTongTien();
    }

    public double getGiaVe() { return giaVe; }
    public void setGiaVe(double giaVe) { this.giaVe = giaVe; capNhatTongTien(); }

    public int getSoKhach() { return dsMaKhachHang.length; }

    public double getTongTienVe() { return tongTien; } // Chỉ tiền vé
    public double getTongTien() { return tongTien; }   // Sẽ được override ở QuanLy nếu có chi phí

    private void capNhatTongTien() {
        this.tongTien = this.dsMaKhachHang.length * this.giaVe;
    }

    // Hiển thị ngắn gọn (dùng trong danh sách)
    public void hienThiNgan() {
        System.out.printf("%-12s | %-12s | %4d khach | %,15.0f VND%n",
                maHD, maKHTour, getSoKhach(), tongTien);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(maHD).append(",").append(maKHTour).append(",").append(giaVe);
        for (int maKH : dsMaKhachHang) {
            sb.append(",").append(maKH);
        }
        return sb.toString();
    }
}