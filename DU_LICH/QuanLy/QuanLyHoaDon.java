package DU_LICH.QuanLy;

import java.util.Scanner;
import DU_LICH.ClassDon.*;
import DU_LICH.DanhSach.*;

public class QuanLyHoaDon extends QuanLy {
    private DSHoaDon dsHoaDon = new DSHoaDon();

    public QuanLyHoaDon() {
        super(false);
    }

    public void menuHoaDon(Scanner sc) {
        while (true) {
            System.out.println("\n=== QUAN LY HOA DON TOUR ===");
            System.out.println("1. Them hoa don moi");
            System.out.println("2. Xoa hoa don");
            System.out.println("3. Hien thi danh sach");
            System.out.println("4. Tim kiem theo ma hoa don");
            System.out.println("5. Thong ke doanh thu");
            System.out.println("0. Thoat (luu du lieu)");
            System.out.print("Chon: ");

            int ch = -1;
            try { ch = Integer.parseInt(sc.nextLine().trim()); }
            catch (Exception e) { ch = -1; }

            switch (ch) {
                case 1: themMoi(sc); break;
                case 2: xoa(sc); break;
                case 3: dsHoaDon.hienThiDanhSach(); break;
                case 4: timKiem(sc); break;
                case 5: dsHoaDon.thongKe(); break;
                case 0:
                    dsHoaDon.saveToFile(PATH_HOADON);
                    System.out.println("Da luu danh sach hoa don.");
                    return;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        }
    }

    private void themMoi(Scanner sc) {
        HoaDon hd = new HoaDon();

        System.out.print("Nhap ma hoa don: ");
        hd.setMaHD(sc.nextLine().trim());

        System.out.print("Nhap ma ke hoach tour: ");
        String maKH = sc.nextLine().trim();
        KeHoachTour kht = dsKeHoach.timTheoMa(maKH);
        if (kht == null) {
            System.out.println("Khong tim thay ke hoach tour!");
            return;
        }
        hd.setMaKHTour(maKH);
        hd.setGiaVe(kht.getGiaVe());

        System.out.print("Nhap ma HDV: ");
        try {
            int maHDV = Integer.parseInt(sc.nextLine().trim());
            if (dsHDV.timTheoMa(maHDV) == null) {
                System.out.println("HDV khong ton tai!");
                return;
            }
            hd.setMaHDV(maHDV);
        } catch (Exception e) {
            System.out.println("Ma HDV khong hop le!");
            return;
        }

        System.out.print("Nhap ma khach hang dai dien: ");
        try {
            int maKHang = Integer.parseInt(sc.nextLine().trim());
            if (dsKhachHang.timKiemKHTheoMa(maKHang) == null) {
                System.out.println("Khach hang khong ton tai!");
                return;
            }
            hd.setMaKHDaiDien(maKHang);
        } catch (Exception e) {
            System.out.println("Ma khach hang khong hop le!");
            return;
        }

        System.out.print("Nhap so khach: ");
        hd.setSoKhach(nhapSo(sc));

        System.out.print("Nhap so ve dat: ");
        int soVe = nhapSo(sc);
        if (soVe > kht.getTongSoVe() - kht.getTongVeDaDat()) {
            System.out.println("Khong du ve! Con lai: " + (kht.getTongSoVe() - kht.getTongVeDaDat()));
            return;
        }
        hd.setSoVe(soVe);

        // Cập nhật số vé đã đặt trong kế hoạch tour
        kht.setTongVeDaDat(kht.getTongVeDaDat() + soVe);

        if (dsHoaDon.them(hd)) {
            System.out.println("Them hoa don thanh cong!");
            hd.hienThi();
        } else {
            System.out.println("Them that bai (trung ma?)");
        }
    }

    private void xoa(Scanner sc) {
        System.out.print("Nhap ma hoa don can xoa: ");
        String ma = sc.nextLine().trim();
        HoaDon hd = dsHoaDon.timTheoMa(ma);
        if (hd == null) {
            System.out.println("Khong tim thay hoa don!");
            return;
        }

        // Hoàn vé lại cho kế hoạch tour
        KeHoachTour kht = dsKeHoach.timTheoMa(hd.getMaKHTour());
        if (kht != null) {
            kht.setTongVeDaDat(kht.getTongVeDaDat() - hd.getSoVe());
        }

        if (dsHoaDon.xoa(ma)) {
            System.out.println("Xoa hoa don thanh cong!");
        }
    }

    private void timKiem(Scanner sc) {
        System.out.print("Nhap ma hoa don: ");
        String ma = sc.nextLine().trim();
        HoaDon hd = dsHoaDon.timTheoMa(ma);
        if (hd == null) {
            System.out.println("Khong tim thay!");
        } else {
            System.out.println("Ket qua tim kiem:");
            System.out.printf("%-12s | %-12s | %-8s | %-8s | %-6s | %-6s | %-12s | %-18s%n",
                    "Ma HD", "Ma KH Tour", "Ma HDV", "Ma KH", "Khach", "So Ve", "Ngay Lap", "Tong Tien");
            hd.hienThi();
        }
    }

    private int nhapSo(Scanner sc) {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.print("Nhap lai (so nguyen): ");
            }
        }
    }
}