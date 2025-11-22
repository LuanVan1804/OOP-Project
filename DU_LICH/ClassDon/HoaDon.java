// File: DU_LICH/ClassDon/HoaDon.java
package DU_LICH.ClassDon;

import DU_LICH.QuanLy.QuanLy;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HoaDon {
    private String maHD;
    private String maKHTour;
    private int maHDV;
    private int maKHDaiDien;
    private int soKhach;
    private int soVe;
    private LocalDate ngayLap;

    // Constructor mặc định
    public HoaDon() {
        this.ngayLap = LocalDate.now();
    }

    // Constructor đầy đủ (dùng khi load từ file)
    public HoaDon(String maHD, String maKHTour, int maHDV, int maKHDaiDien, 
                  int soKhach, int soVe, LocalDate ngayLap) {
        this.maHD = maHD;
        this.maKHTour = maKHTour;
        this.maHDV = maHDV;
        this.maKHDaiDien = maKHDaiDien;
        this.soKhach = soKhach;
        this.soVe = soVe;
        this.ngayLap = ngayLap;
    }

    // Copy constructor
    public HoaDon(HoaDon other) {
        if (other == null) {
            this.maHD = null;
            this.maKHTour = null;
            this.maHDV = 0;
            this.maKHDaiDien = 0;
            this.soKhach = 0;
            this.soVe = 0;
            this.ngayLap = LocalDate.now();
        } else {
            this.maHD = other.maHD;
            this.maKHTour = other.maKHTour;
            this.maHDV = other.maHDV;
            this.maKHDaiDien = other.maKHDaiDien;
            this.soKhach = other.soKhach;
            this.soVe = other.soVe;
            this.ngayLap = other.ngayLap;
        }
    }

    // === GETTER & SETTER ===
    public String getMaHD() { return maHD; }
    public void setMaHD(String maHD) { this.maHD = maHD; }

    public String getMaKHTour() { return maKHTour; }
    public void setMaKHTour(String maKHTour) { this.maKHTour = maKHTour; }

    public int getMaHDV() { return maHDV; }
    public void setMaHDV(int maHDV) { this.maHDV = maHDV; }

    public int getMaKHDaiDien() { return maKHDaiDien; }
    public void setMaKHDaiDien(int maKHDaiDien) { this.maKHDaiDien = maKHDaiDien; }

    public int getSoKhach() { return soKhach; }
    public void setSoKhach(int soKhach) { this.soKhach = soKhach; }

    public int getSoVe() { return soVe; }
    public void setSoVe(int soVe) { this.soVe = soVe; }

    public LocalDate getNgayLap() { return ngayLap; }
    public void setNgayLap(LocalDate ngayLap) { this.ngayLap = ngayLap; }

    // === PHƯƠNG THỨC QUAN TRỌNG NHẤT: TÍNH TỔNG TIỀN TỪ CHI TIẾT ===
    public double getTongTien() {
        double tong = 0.0;
        for (ChiTietHD ct : QuanLy.getDsChiTietHD().getList()) {
            if (ct != null && ct.getMaHD().equalsIgnoreCase(this.maHD)) {
                tong += ct.getThanhTien();
            }
        }
        return tong;
    }

    // === HIỂN THỊ DÒNG TRONG DANH SÁCH HÓA ĐƠN ===
    public void hienThi() {
        System.out.printf("%-12s | %-12s | %-8d | %-8d | %-6d | %-6d | %-12s | %,18.0f VND%n",
            maHD,
            maKHTour,
            maHDV,
            maKHDaiDien,
            soKhach,
            soVe,
            ngayLap.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
            getTongTien()
        );
    }

    // === GHI RA FILE (chỉ lưu thông tin cơ bản) ===
    @Override
    public String toString() {
        return String.join(",",
            maHD,
            maKHTour,
            String.valueOf(maHDV),
            String.valueOf(maKHDaiDien),
            String.valueOf(soKhach),
            String.valueOf(soVe),
            ngayLap.toString()
        );
    }
}