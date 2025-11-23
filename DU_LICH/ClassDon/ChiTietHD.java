// DU_LICH/ClassDon/ChiTietHD.java
package DU_LICH.ClassDon;

public class ChiTietHD {
    private String maHD;
    private String maKHTour;
    private int maKhachHang;        // ID khách hàng
    private String loaiVe;          // "NguoiLon" hoặc "TreEm"
    private double giaVe;           // Giá vé áp dụng (người lớn hoặc trẻ em)
    private double thanhTien;       // = giaVe (vì 1 khách 1 vé)

    public ChiTietHD() {}

    public ChiTietHD(String maHD, String maKHTour, int maKhachHang, String loaiVe, double giaVeCoBan) {
        this.maHD = maHD;
        this.maKHTour = maKHTour;
        this.maKhachHang = maKhachHang;
        this.loaiVe = loaiVe;
        this.giaVe = loaiVe.equals("TreEm") ? giaVeCoBan * 0.5 : giaVeCoBan;
        this.thanhTien = this.giaVe;
    }

    // Copy constructor
    public ChiTietHD(ChiTietHD other) {
        if (other == null) return;
        this.maHD = other.maHD;
        this.maKHTour = other.maKHTour;
        this.maKhachHang = other.maKhachHang;
        this.loaiVe = other.loaiVe;
        this.giaVe = other.giaVe;
        this.thanhTien = other.thanhTien;
    }

    // Getters
    public String getMaHD() { return maHD; }
    public String getMaKHTour() { return maKHTour; }
    public int getMaKhachHang() { return maKhachHang; }
    public String getLoaiVe() { return loaiVe; }
    public double getGiaVe() { return giaVe; }
    public double getThanhTien() { return thanhTien; }

    // toString cho file
    @Override
    public String toString() {
        return String.join(",", maHD, maKHTour, String.valueOf(maKhachHang), loaiVe, String.valueOf(giaVe));
    }

    // Hiển thị ngắn (dùng trong danh sách chi tiết)
    public void hienThiNgan() {
        System.out.printf("%-8s | %-13d | %-11s | %,15.0f | %,15.0f%n",
                maHD, maKhachHang, loaiVe, giaVe, thanhTien);
    }

    // Hiển thị đầy đủ (nếu cần)
    public void hienThi() {
        System.out.printf("Khach: %d | Loai ve: %-8s | Gia ve: %,15.0f | Thanh tien: %,15.0f%n",
                maKhachHang, loaiVe, giaVe, thanhTien);
    }
}