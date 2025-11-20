package DU_LICH.QuanLy;

import java.util.Scanner;
import DU_LICH.ClassDon.*;
import DU_LICH.TourDuLich.Tour;

public class QuanLyChiPhi extends QuanLy {
    public QuanLyChiPhi() {
        super(false);
    }

    public void menuChiPhi(Scanner sc) {
        while (true) {
            System.out.println("\n=== QUAN LY CHI PHI KE HOACH TOUR ===");
            System.out.println("1. Them chi phi moi");
            System.out.println("2. Xoa chi phi");
            System.out.println("3. Chinh sua chi phi");
            System.out.println("4. Hien thi danh sach");
            System.out.println("5. Tim kiem theo ma ke hoach");
            System.out.println("6. Thong ke");
            System.out.println("0. Thoat (luu du lieu)");
            System.out.print("Chon: ");

            int ch = -1;
            try { ch = Integer.parseInt(sc.nextLine().trim()); }
            catch (Exception e) { ch = -1; }

            switch (ch) {
                case 1: themMoi(sc); break;
                case 2: xoa(sc); break;
                case 3: chinhSua(sc); break;
                case 4: dsChiPhi.hienThiDanhSach(); break;
                case 5: timKiem(sc); break;
                case 6: dsChiPhi.thongKe(); break;
                case 0:
                    dsChiPhi.saveToFile(PATH_CHIPHI);
                    return;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        }
    }

    private void themMoi(Scanner sc) {
        System.out.print("Nhap ma ke hoach tour: ");
        String maKH = sc.nextLine().trim();
        if (maKH.isEmpty()) {
            System.out.println("Ma khong duoc de trong!");
            return;
        }

        // Kiem tra ke hoach ton tai
        if (dsKeHoach.timTheoMa(maKH) == null) {
            System.out.println("Khong tim thay ke hoach tour nay!");
            return;
        }

        // Kiem tra da co chi phi chua
        if (dsChiPhi.timTheoMa(maKH) != null) {
            System.out.println("Da co chi phi cho ke hoach nay! Hay chinh sua.");
            return;
        }

        ChiPhiKHTour cp = new ChiPhiKHTour();
        cp.setMaKHTour(maKH);

        // Nhap cac chi phi (ban co the mo rong sau)
        System.out.print("Tong tien an (VND): ");
        cp.setTongTienAn(nhapSoDouble(sc));
        System.out.print("Tong tien phong (VND): ");
        cp.setTongTienPhong(nhapSoDouble(sc));
        System.out.print("Tong tien phuong tien (VND): ");
        cp.setTongTienPhuongTien(nhapSoDouble(sc));

        // Tu dong lay tien tour tu KeHoachTour
        KeHoachTour kh = dsKeHoach.timTheoMa(maKH);
        if (kh != null) {
            Tour t = dsTour.timTheoMa(kh.getMaTour());
            double tienTour = (t != null) ? t.getDonGia() : 0.0;
            cp.setTienTour(tienTour);
            System.out.printf("Tu dong lay tien tour: %, .0f VND%n", tienTour);
        }

        if (dsChiPhi.them(cp)) {
            System.out.println("Them chi phi thanh cong!");
            cp.hienThi();
        } else {
            System.out.println("Them that bai!");
        }
    }

    private void xoa(Scanner sc) {
        System.out.print("Nhap ma ke hoach can xoa chi phi: ");
        String ma = sc.nextLine().trim();
        if (dsChiPhi.xoa(ma)) {
            System.out.println("Xoa thanh cong!");
        } else {
            System.out.println("Khong tim thay hoac da xoa!");
        }
    }

    private void chinhSua(Scanner sc) {
        System.out.print("Nhap ma ke hoach can sua chi phi: ");
        String ma = sc.nextLine().trim();
        ChiPhiKHTour cp = dsChiPhi.timTheoMa(ma);
        if (cp == null) {
            System.out.println("Khong tim thay chi phi!");
            return;
        }

        System.out.println("De trong neu giu nguyen:");
        System.out.print("Tien an moi (" + String.format("%,.0f", cp.getTongTienAn()) + "): ");
        String input = sc.nextLine().trim();
        if (!input.isEmpty()) cp.setTongTienAn(Double.parseDouble(input));

        System.out.print("Tien phong moi (" + String.format("%,.0f", cp.getTongTienPhong()) + "): ");
        input = sc.nextLine().trim();
        if (!input.isEmpty()) cp.setTongTienPhong(Double.parseDouble(input));

        System.out.print("Tien phuong tien moi (" + String.format("%,.0f", cp.getTongTienPhuongTien()) + "): ");
        input = sc.nextLine().trim();
        if (!input.isEmpty()) cp.setTongTienPhuongTien(Double.parseDouble(input));

        System.out.println("Cap nhat thanh cong!");
        cp.hienThi();
    }

    private void timKiem(Scanner sc) {
        System.out.print("Nhap ma ke hoach: ");
        String ma = sc.nextLine().trim();
        ChiPhiKHTour cp = dsChiPhi.timTheoMa(ma);
        if (cp == null) {
            System.out.println("Khong tim thay!");
        } else {
            System.out.println("Ket qua:");
            System.out.printf("%-10s | %-10s | %-10s | %-15s | %-12s | %-12s | %-12s | %-12s | %-14s%n",
                    "Ma KH", "Nha Hang", "Khach San", "Phuong Tien", "Tien An", "Tien Phong", "Tien PT", "Tien Tour", "TONG CHI");
            cp.hienThi();
        }
    }

    private double nhapSoDouble(Scanner sc) {
        while (true) {
            String s = sc.nextLine().trim();
            try {
                return Double.parseDouble(s);
            } catch (Exception e) {
                System.out.print("Nhap lai (so): ");
            }
        }
    }
}