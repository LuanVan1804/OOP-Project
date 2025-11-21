package DU_LICH.QuanLy;

import java.util.Scanner;

import DU_LICH.ClassDon.PhuongTien;
import DU_LICH.DanhSach.DSPhuongTien;

public class QuanLyPhuongTien {
    private DSPhuongTien ds;
    private final Scanner sc;
    private String dataFile;

    public QuanLyPhuongTien(DSPhuongTien ds, Scanner sc, String dataFile) {
        this.ds = ds == null ? new DSPhuongTien() : ds;
        this.sc = sc == null ? new Scanner(System.in) : sc;
        this.dataFile = (dataFile == null || dataFile.trim().isEmpty()) ? "DU_LICH/NH_KS_PT/PhuongTien.txt" : dataFile;
    }

    public void menu() {
        int chon;
        do {
            System.out.println("=== QUAN LY PHUONG TIEN ===");
            System.out.println("1. Them phuong tien");
            System.out.println("2. Xoa phuong tien");
            System.out.println("3. Sua phuong tien");
            System.out.println("4. Tim kiem theo bien so");
            System.out.println("5. Tim theo loai");
            System.out.println("6. Xuat danh sach");
            System.out.println("7. Thong ke");
            System.out.println("0. Quay lai");
            System.out.print("Chon (0-7): ");

            while (!sc.hasNextInt()) {
                System.out.print("So nguyen, nhap lai: "); sc.next();
            }
            chon = sc.nextInt(); sc.nextLine();

            switch (chon) {
                case 1:
                    ds.them();
                    break;
                case 2:
                    ds.xoa();
                    break;
                case 3:
                    ds.sua();
                    break;
                case 4:
                    ds.timKiem();
                    break;
                case 5:
                    System.out.print("Nhap ten phuong tien can tim: ");
                    String ten = sc.nextLine();
                    PhuongTien[] found = ds.timTheoTen(ten);
                    if (found.length == 0) System.out.println("Khong tim thay phuong tien theo loai.");
                    else {
                        for (PhuongTien p : found) p.xuat();
                    }
                    break;
                case 6:
                    ds.xuat();
                    break;
                case 7:
                    ds.thongKe();
                    break;
                case 0:
                    try {
                        ds.saveToFile(this.dataFile);
                    } catch (java.io.IOException e) {
                        System.err.println("Loi khi luu file: " + e.getMessage());
                    }
                    return;
                default:
                    System.out.println("Lua chon khong hop le.");
            }

        } while (true);
    }

    // For integration points
    public DSPhuongTien getDs() { return ds; }
}
