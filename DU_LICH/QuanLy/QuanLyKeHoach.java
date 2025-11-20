package DU_LICH.QuanLy;

import java.io.IOException;
import java.util.Scanner;
import DU_LICH.ClassDon.KeHoachTour;
import DU_LICH.DanhSach.*;

public class QuanLyKeHoach extends QuanLy {
    private DSKHTour dsKHTour = new DSKHTour();
    private final String FILE_KH = "KeHoachTour.txt";

    public QuanLyKeHoach() {
        super(false);
        try {
            dsKHTour.loadFromFile(FILE_KH);
        } catch (Exception e) {
            System.out.println("Khong doc duoc file KeHoachTour.txt");
        }
    }

    public void menuKeHoach(Scanner sc) {
        while (true) {
            System.out.println("\n=== QUAN LY KE HOACH TOUR ===");
            System.out.println("1. Them ke hoach moi");
            System.out.println("2. Xoa ke hoach");
            System.out.println("3. Hien thi danh sach");
            System.out.println("4. Tim kiem theo ma");
            System.out.println("5. Tim kiem theo ten ke hoach");
            System.out.println("6. Thong ke");
            System.out.println("0. Thoat (luu du lieu)");
            System.out.print("Chon: ");

            int ch = -1;
            try { ch = Integer.parseInt(sc.nextLine().trim()); }
            catch (Exception e) { ch = -1; }

            switch (ch) {
                case 1: themMoi(sc); break;
                case 2: xoa(sc); break;
                case 3: dsKHTour.hienThiDanhSach(); break;
                case 4: timMa(sc); break;
                case 5: timTen(sc); break;
                case 6: dsKHTour.thongKe(); break;
                case 0:
                    try {
                        dsKHTour.saveToFile(PATH_KEHOACH);
                        System.out.println("Da luu du lieu ke hoach tour.");
                    } catch (IOException e) {
                        System.out.println("Loi luu file!");
                    }
                    return;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        }
    }

    private void themMoi(Scanner sc) {
        KeHoachTour k = new KeHoachTour();
        k.nhap();

        // Validate mã tour tồn tại
        if (dsTour.timTheoMa(k.getMaTour()) == null) {
            System.out.println("Ma tour khong ton tai!");
            return;
        }

        // Validate HDV nếu có nhập
        if (!k.getMaHDV().isEmpty()) {
            try {
                int maHDV = Integer.parseInt(k.getMaHDV());
                if (dsHDV.timTheoMa(maHDV) == null) {
                    System.out.println("Ma HDV khong ton tai!");
                    return;
                }
            } catch (Exception e) {
                System.out.println("Ma HDV phai la so!");
                return;
            }
        }

        if (dsKHTour.them(k)) {
            System.out.println("Them thanh cong!");
        } else {
            System.out.println("Them that bai (trung ma hoac day danh sach)!");
        }
    }

    private void xoa(Scanner sc) {
        System.out.print("Nhap ma ke hoach can xoa: ");
        String ma = sc.nextLine().trim();
        if (dsKHTour.xoa(ma)) {
            System.out.println("Xoa thanh cong!");
        } else {
            System.out.println("Khong tim thay ma ke hoach!");
        }
    }

    private void timMa(Scanner sc) {
        System.out.print("Nhap ma ke hoach: ");
        String ma = sc.nextLine().trim();
        KeHoachTour k = dsKHTour.timTheoMa(ma);
        if (k == null) {
            System.out.println("Khong tim thay!");
        } else {
            System.out.println("Ket qua tim kiem:");
            System.out.printf("%-10s | %-20s | %-10s | %-23s | %-12s | %-12s | %-12s%n",
                    "Ma KH", "Ten Ke Hoach", "Ma Tour", "Ngay Di - Ve", "Ve Dat/Tong", "Doanh Thu", "Loi Nhuan");
            k.hienThi();
        }
    }

    private void timTen(Scanner sc) {
        System.out.print("Nhap tu khoa ten ke hoach: ");
        String key = sc.nextLine().trim();
        KeHoachTour[] kq = dsKHTour.timTheoTen(key);
        if (kq.length == 0) {
            System.out.println("Khong tim thay ket qua nao!");
        } else {
            System.out.println("Tim thay " + kq.length + " ket qua:");
            System.out.printf("%-10s | %-20s | %-10s | %-23s | %-12s | %-12s | %-12s%n",
                    "Ma KH", "Ten Ke Hoach", "Ma Tour", "Ngay Di - Ve", "Ve Dat/Tong", "Doanh Thu", "Loi Nhuan");
            for (KeHoachTour k : kq) {
                k.hienThi();
            }
        }
    }
}