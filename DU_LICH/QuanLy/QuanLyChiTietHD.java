package DU_LICH.QuanLy;

import java.util.Scanner;
import DU_LICH.ClassDon.*;
import DU_LICH.DanhSach.*;

public class QuanLyChiTietHD extends QuanLy {
    private DSChiTietHD dsChiTiet = new DSChiTietHD();

    public QuanLyChiTietHD() {
        super(false);
    }

    public void menuChiTietHD(Scanner sc) {
        while (true) {
            System.out.println("\n=== QUAN LY CHI TIET HOA DON ===");
            System.out.println("1. Xem danh sach chi tiet");
            System.out.println("2. Xem chi tiet hoa don (full thong tin)");
            System.out.println("3. Thong ke");
            System.out.println("0. Thoat (luu du lieu)");
            System.out.print("Chon: ");

            int ch = -1;
            try { ch = Integer.parseInt(sc.nextLine().trim()); }
            catch (Exception e) { ch = -1; }

            switch (ch) {
                case 1: dsChiTiet.hienThiDanhSach(); break;
                case 2: xemChiTietDayDu(sc); break;
                case 3: dsChiTiet.thongKe(); break;
                case 0:
                    dsChiTiet.saveToFile(PATH_CHITIET);
                    System.out.println("Da luu chi tiet hoa don.");
                    return;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        }
    }

    private void xemChiTietDayDu(Scanner sc) {
        System.out.print("Nhap ma hoa don: ");
        String maHD = sc.nextLine().trim();
        ChiTietHD ct = dsChiTiet.tim(maHD);
        if (ct == null) {
            System.out.println("Khong tim thay chi tiet hoa don!");
            return;
        }

        HoaDon hd = dsHoaDon.timTheoMa(maHD);
        KeHoachTour kht = dsKeHoach.timTheoMa(ct.getMaKHTour());
        ChiPhiKHTour cp = dsChiPhi.timTheoMa(ct.getMaKHTour());

        System.out.println("┌──────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                       CHI TIET HOA DON DAY DU                        │");
        System.out.println("├──────────────────────────────────────────────────────────────────────┤");
        System.out.printf("│ Ma hoa don          : %-40s │%n", maHD);
        if (hd != null) {
            System.out.printf("│ Ngay lap            : %-40s │%n", hd.getNgayLap().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            System.out.printf("│ HDV phu trach       : %-40s │%n", dsHDV.timTheoMa(hd.getMaHDV()) != null ? dsHDV.timTheoMa(hd.getMaHDV()).getTenHDV() : hd.getMaHDV());
            System.out.printf("│ KH dai dien         : %-40s │%n", dsKhachHang.timKiemKHTheoMa(hd.getMaKHDaiDien()) != null ? dsKhachHang.timKiemKHTheoMa(hd.getMaKHDaiDien()).getTenKH() : hd.getMaKHDaiDien());
        }
        if (kht != null) {
            System.out.printf("│ Ten ke hoach        : %-40s │%n", kht.getTenKeHoach());
            System.out.printf("│ Ma tour             : %-40s │%n", kht.getMaTour());
            System.out.printf("│ Ngay di - ve        : %-40s │%n", 
                    kht.getNgayDi() + " → " + kht.getNgayVe());
        }
        if (cp != null) {
            System.out.printf("│ Nha hang            : %-40s │%n", cp.getMaNhaHang());
            System.out.printf("│ Khach san           : %-40s │%n", cp.getMaKhachSan());
            System.out.printf("│ Phuong tien         : %-40s │%n", cp.getMaPhuongTien());
        }

        System.out.printf("│ So khach trong doan : %-40d │%n", ct.getSoKhach());
        System.out.printf("│ Gia ve/khach        : %-40s │%n", String.format("%,.0f VND", ct.getGiaVe()));
        System.out.printf("│ Tong tien ve        : %-40s │%n", String.format("%,.0f VND", ct.getTongTienVe()));

        if (cp != null) {
            double chiPhi = cp.getTongChi();
            System.out.printf("│ Tong chi phi dich vu: %-40s │%n", String.format("%,.0f VND", chiPhi));
            System.out.printf("│ TONG CONG (ve + dv) : %-40s │%n", String.format("%,.0f VND", ct.getTongTienVe() + chiPhi));
        }
        System.out.println("└──────────────────────────────────────────────────────────────────────┘");
    }
}