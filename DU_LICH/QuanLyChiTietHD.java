package DU_LICH;

import java.io.IOException;
import java.util.Scanner;

public class QuanLyChiTietHD {
    private final DSChiTietHD dsChiTiet;
    private final Scanner sc;

    public QuanLyChiTietHD(DSChiTietHD dsChiTiet, Scanner sc) {
        this.dsChiTiet = dsChiTiet;
        this.sc = sc;
    }

    public void menu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n=== MENU CHI TIET HOA DON ===");
            System.out.println("1. Hien thi tat ca chi tiet");
            System.out.println("2. Xem chi tiet theo ma hoa don");
            System.out.println("0. Thoat");
            System.out.print("Chon: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Lua chon khong hop le.");
                continue;
            }

            switch (choice) {
                case 1:
                    hienThiDanhSach();
                    break;
                case 2:
                    xemChiTietTheoMa();
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Lua chon khong hop le.");
            }
        }
    }

    private void hienThiDanhSach() {
        dsChiTiet.hienThiDanhSach();
    }

    private void xemChiTietTheoMa() {
        System.out.print("Nhap ma hoa don: ");
        String maHD = sc.nextLine();
        ChiTietHD ct = dsChiTiet.tim(maHD);
        if (ct != null) {
            System.out.println("\nChi tiet hoa don:");
            ct.hienThiThongTin();
        } else {
            System.out.println("Khong tim thay chi tiet!");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DSChiTietHD ds = new DSChiTietHD();
        try {
            ds.loadFromFile("DU_LICH/DSChiTietHD.txt");
        } catch (IOException e) {
            System.out.println("Loi load file: " + e.getMessage());
        }
        QuanLyChiTietHD ql = new QuanLyChiTietHD(ds, sc);
        ql.menu();
        sc.close();
    }
}