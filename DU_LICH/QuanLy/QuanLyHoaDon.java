package DU_LICH.QuanLy;

import java.util.Scanner;
import DU_LICH.ClassDon.*;
import DU_LICH.DanhSach.*;

public class QuanLyHoaDon extends QuanLy {
    private DSHoaDon dsHoaDon;

    public QuanLyHoaDon() {
        super(false);
        dsHoaDon = QuanLy.getDsHoaDon();
    }

    public void menuHoaDon(Scanner sc) {
        while (true) {
            System.out.println("\n=== QUAN LY HOA DON TOUR ===");
            System.out.println("1. Them hoa don moi");
            System.out.println("2. Xoa hoa don");
            System.out.println("3. Hien thi danh sach");
            System.out.println("4. Tim kiem theo ma hoa don");
            System.out.println("5. Thong ke doanh thu");
            System.out.println("6. Thong ke nang cao theo quy (Doanh thu - Chi phi - Loi nhuan)");
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
                case 6: dsHoaDon.thongKeTheoQuy(); break;
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

        // ... nhập HDV, khách đại diện ...

        System.out.print("Nhap so khach nguoi lon: ");
        int slNguoiLon = nhapSo(sc);
        System.out.print("Nhap so khach tre em (duoi 12 tuoi): ");
        int slTreEm = nhapSo(sc);

        int tongKhach = slNguoiLon + slTreEm;
        hd.setSoKhach(tongKhach);
        hd.setSoVe(tongKhach);

        if (tongKhach > kht.getTongSoVe() - kht.getTongVeDaDat()) {
            System.out.println("Khong du ve! Con lai: " + (kht.getTongSoVe() - kht.getTongVeDaDat()));
            return;
        }

        double giaVeNguoiLon = kht.getGiaVe();

        // Tạo chi tiết hóa đơn cho từng khách
        System.out.println("Nhap ma khach hang (ID):");
        for (int i = 0; i < tongKhach; i++) {
            System.out.print("Khach thu " + (i+1) + " (ma KH): ");
            int maKHang = nhapSo(sc);

            String loai = (i < slNguoiLon) ? "NguoiLon" : "TreEm";

            ChiTietHD ct = new ChiTietHD(
                hd.getMaHD(),
                hd.getMaKHTour(),
                maKHang,
                loai,
                giaVeNguoiLon
            );

            QuanLy.getDsChiTietHD().them(ct);
        }

        // Cập nhật vé đã đặt
        kht.setTongVeDaDat(kht.getTongVeDaDat() + tongKhach);

        if (dsHoaDon.them(hd)) {
            System.out.println("Them hoa don thanh cong! Tong tien: " + String.format("%,.0f", hd.getTongTien()) + " VND");
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
        QuanLy.getDsChiTietHD().xoa(ma); // xoa chi tiet hoa don neu co
        // Hoan ve lai cho ke hoach tour
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