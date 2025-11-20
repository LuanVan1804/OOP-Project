package DU_LICH.QuanLy;

import java.io.IOException;
import java.util.Scanner;
import DU_LICH.TourDuLich.Tour;

public class QuanLyTour extends QuanLy {
    public QuanLyTour() { super(false); }

    public void tourMenu(Scanner sc) {
        while (true) {
            System.out.println("\n=== MENU QUAN LY TOUR ===");
            System.out.println("1. Them tour");
            System.out.println("2. Xoa tour");
            System.out.println("3. Chinh sua tour");
            System.out.println("4. Hien thi danh sach tour");
            System.out.println("5. Tim kiem theo ten");
            System.out.println("6. Tim kiem theo ma");
            System.out.println("7. Thong ke");
            System.out.println("0. Thoat (luu)");
            System.out.print("Chon: ");
            String line = sc.nextLine();
            int ch = -1;
            try {
                ch = Integer.parseInt(line.trim());
            } catch (Exception e) {
                ch = -1;
            }
            switch (ch) {
                case 1:
                    dsTour.themTour();
                    break;
                case 2:
                    System.out.print("Nhap ma tour can xoa: ");
                    String maXoa = sc.nextLine();
                    dsTour.xoaTour(maXoa);
                    break;
                case 3:
                    System.out.print("Nhap ma tour can chinh sua: ");
                    String maSua = sc.nextLine();
                    dsTour.chinhSuaTour(maSua);
                    break;
                case 4:
                    dsTour.hienThiDanhSachTour();
                    break;
                case 5:
                    System.out.print("Nhap ten tour can tim: ");
                    String ten = sc.nextLine();
                    Tour[] kq = dsTour.timKiemTheoTen(ten);
                    if (kq == null || kq.length == 0)
                        System.out.println("Khong tim thay tour.");
                    else {
                        for (Tour t : kq)
                            t.hienThiThongTin();
                    }
                    break;
                case 6:
                    System.out.print("Nhap ma tour can tim: ");
                    String ma = sc.nextLine();
                    dsTour.timKiemTheoMa(ma);
                    break;
                case 7:
                    dsTour.thongKe();
                    break;
                case 0:
                    try {
                        dsTour.saveToFile(PATH_TOUR);
                    } catch (IOException e) {
                        System.out.println("Loi khi luu DSTour: " + e.getMessage());
                    }
                    return;
                default:
                    System.out.println("Lua chon khong hop le.");
            }
        }
    }
}
