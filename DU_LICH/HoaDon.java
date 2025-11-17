package DU_LICH;

import DU_LICH.Nguoi.HDV;
import DU_LICH.Nguoi.KhachHang;

import java.time.LocalDate;
import java.util.Scanner;

public class HoaDon {
    private String maHD;
    private KeHoachTour maKHTour;
    private HDV maHDV;
    private KhachHang maKHDaiDien;
    private int soKhach;
    private int soVe;
    private LocalDate ngayLap;

    public HoaDon() {
        this.maHD = "";
        this.maKHTour = new KeHoachTour();
        this.maHDV = new HDV();
        this.maKHDaiDien = new KhachHang();
        this.soKhach = 0;
        this.soVe = 0;
        this.ngayLap = LocalDate.now();
    }

    public HoaDon(String maHD, KeHoachTour maKHTour, HDV maHDV, KhachHang maKHDaiDien, int soKhach, int soVe) {
        this.maHD = maHD;
        this.maKHTour = maKHTour;
        this.maHDV = maHDV;
        this.maKHDaiDien = maKHDaiDien;
        this.soKhach = soKhach;
        this.soVe = soVe;
        this.ngayLap = LocalDate.now();
    }

    // Constructor sao chep
    public HoaDon(HoaDon hd) {
        this.maHD = hd.maHD;
        this.maKHTour = hd.maKHTour;
        this.maHDV = hd.maHDV;
        this.maKHDaiDien = hd.maKHDaiDien;
        this.soKhach = hd.soKhach;
        this.soVe = hd.soVe;
        this.ngayLap = hd.ngayLap;
    }

    // Getter / Setter
    public String getMaHD() { return maHD; }
    public void setMaHD(String maHD) { this.maHD = maHD; }

    public KeHoachTour getMaKHTour() { return maKHTour; }
    public void setMaKHTour(KeHoachTour maKHTour) { this.maKHTour = maKHTour; }

    public HDV getMaHDV() { return maHDV; }
    public void setMaHDV(HDV maHDV) { this.maHDV = maHDV; }

    public KhachHang getMaKHDaiDien() { return maKHDaiDien; }
    public void setMaKHDaiDien(KhachHang maKHDaiDien) { this.maKHDaiDien = maKHDaiDien; }

    public int getSoKhach() { return soKhach; }
    public void setSoKhach(int soKhach) { this.soKhach = soKhach; }

    public int getSoVe() { return soVe; }
    public void setSoVe(int soVe) { this.soVe = soVe; }

    public LocalDate getNgayLap() { return ngayLap; }
    public void setNgayLap(LocalDate ngayLap) { this.ngayLap = ngayLap; }

    // Nhap thong tin hoa don (nhan Scanner tu ben ngoai)
    public void nhap(Scanner sc) {
        System.out.println("Ngay lap hoa don: " + LocalDate.now());
        this.ngayLap = LocalDate.now();
        System.out.print("Nhap ma hoa don: ");
        this.maHD = sc.nextLine().trim();
        System.out.print("Nhap ma ke hoach tour: ");
        this.maKHTour.setMaKHTour(sc.nextLine().trim());
        System.out.print("Nhap ma HDV phu trach: ");
        this.maHDV.setMaHDV(Integer.parseInt(sc.nextLine().trim()));
        System.out.print("Nhap ma khach hang dai dien: ");
        this.maKHDaiDien.setMaKH(Integer.parseInt(sc.nextLine().trim()));
        System.out.print("Nhap so khach di tour: ");
        this.soKhach = Integer.parseInt(sc.nextLine().trim());
        System.out.print("Nhap so ve: ");
        this.soVe = Integer.parseInt(sc.nextLine().trim());
    }

    // Xuat thong tin hoa don (in dep)
    public void xuatThongTin() {
        System.out.println("┌──────────────────────────────────────────────────────────────┐");
        System.out.println("│                    HOA DON TOUR                              │");
        System.out.println("├──────────────────────────────────────────────────────────────┤");
        System.out.printf("│ Ngay lap              : %-33s │%n", getNgayLap());
        System.out.printf("│ Ma hoa don             : %-33s │%n", getMaHD());
        System.out.printf("│ Ma ke hoach tour       : %-33s │%n", getMaKHTour().getMaKHTour());
        System.out.printf("│ Ma HDV phu trach       : %-33d │%n", getMaHDV().getMaHDV());
        System.out.printf("│ Ma khach hang dai dien : %-33d │%n", getMaKHDaiDien().getMaKH());
        System.out.printf("│ So khach di tour       : %-33d │%n", getSoKhach());
        System.out.printf("│ So ve                  : %-33d │%n", getSoVe());
        System.out.printf("│ Tong tien              : %-33s │%n", String.format("%,.0f VND", tongTienVe()));
        System.out.println("└──────────────────────────────────────────────────────────────┘");
    }



    @Override
    public String toString() {
        return "HoaDon {maHD=" + maHD + ", soKhach=" + soKhach +
               ", maKHTour=" + maKHTour +
               ", maHDV=" + maHDV +
               ", maKHDaiDien=" + maKHDaiDien +
               ", soVe=" + soVe + "}";
    }

    // Tinh tong tien ve
    public double tongTienVe() {
        // Kiểm tra maKHTour và giaVe hợp lệ
        if (maKHTour != null && maKHTour.getGiaVe() > 0) {
            return (double) soVe * maKHTour.getGiaVe();
        }
        return 0.0;
    }
}
