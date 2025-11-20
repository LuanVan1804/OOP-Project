package DU_LICH.ClassDon;

import java.time.LocalDate;

public class HoaDon {
    private String maHD;
    private String maKHTour;      // Chi luu ma, khong luu object
    private int maHDV;
    private int maKHDaiDien;
    private int soKhach;
    private int soVe;
    private LocalDate ngayLap;
    private double giaVe;         // Lay tu ke hoach tour khi can tinh tien
    private double tongTien;      // Tu dong tinh = soVe * giaVe

    public HoaDon() {
        this.ngayLap = LocalDate.now();
        this.tongTien = 0.0;
    }

    public HoaDon(String maHD, String maKHTour, int maHDV, int maKHDaiDien,
                  int soKhach, int soVe, double giaVe) {
        this.maHD = maHD;
        this.maKHTour = maKHTour;
        this.maHDV = maHDV;
        this.maKHDaiDien = maKHDaiDien;
        this.soKhach = soKhach;
        this.soVe = soVe;
        this.giaVe = giaVe;
        this.ngayLap = LocalDate.now();
        capNhatTongTien();
    }

    // Getter & Setter
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
    public void setSoVe(int soVe) { this.soVe = soVe; capNhatTongTien(); }

    public LocalDate getNgayLap() { return ngayLap; }
    public void setNgayLap(LocalDate ngayLap) { this.ngayLap = ngayLap; }

    public double getGiaVe() { return giaVe; }
    public void setGiaVe(double giaVe) { this.giaVe = giaVe; capNhatTongTien(); }

    public double getTongTien() { return tongTien; }

    // Tu dong cap nhat tong tien
    private void capNhatTongTien() {
        this.tongTien = this.soVe * this.giaVe;
    }

    // Hien thi dep
    public void hienThi() {
        System.out.printf("%-12s | %-12s | %-8d | %-8d | %6d | %6d | %-12s | %,15.0f VND%n",
                maHD, maKHTour, maHDV, maKHDaiDien, soKhach, soVe,
                ngayLap.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                tongTien);
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%d,%d,%d,%d,%.0f,%s",
                maHD, maKHTour, maHDV, maKHDaiDien, soKhach, soVe, giaVe,
                ngayLap);
    }
}