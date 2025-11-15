package DU_LICH;

import java.io.IOException;
import java.util.Scanner;
import DU_LICH.Nguoi.KhachHang;

public class QuanLyKhachHang extends QuanLy {
    public QuanLyKhachHang() { super(false); }

    public void khachHangMenu(Scanner sc) {
        while (true) {
            System.out.println("\n========== MENU KHACH HANG ==========");
            System.out.println("1. Hien thi danh sach khach hang");
            System.out.println("2. Them khach hang moi");
            System.out.println("3. Sua thong tin khach hang");
            System.out.println("4. Xoa khach hang");
            System.out.println("5. Tim khach hang theo ma");
            System.out.println("6. Tim khach hang theo ten");
            System.out.println("7. Thong ke theo gioi tinh");
            System.out.println("0. Thoat (luu)");
            System.out.print("Chon chuc nang: ");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    KhachHang[] all = dsKhachHang.timKiemTheoTen("");
                    if (all == null || all.length == 0) {
                        System.out.println("Danh sach khach hang rong!");
                    } else {
                        System.out.println("Danh sach khach hang:");
                        for (KhachHang kh : all) if (kh != null) System.out.println(kh);
                    }
                    break;
                case 2: {
                    KhachHang kh = new KhachHang();
                    kh.nhapThongTinKH();
                    if (!dsKhachHang.MaDuyNhat(kh.getMaKH())) {
                        System.out.println("Ma khach hang da ton tai, khong the them.");
                    } else {
                        dsKhachHang.them(kh);
                        System.out.println("Da them khach hang moi!");
                    }
                    break;
                }
                case 3: {
                    System.out.print("Nhap ma khach hang can sua: ");
                    int ma = Integer.parseInt(sc.nextLine());
                    KhachHang old = dsKhachHang.timKiemKHTheoMa(ma);
                    if (old == null) {
                        System.out.println("Khong tim thay khach hang voi ma: " + ma);
                        break;
                    }
                    System.out.println("Thong tin hien tai: " + old);
                    KhachHang updated = new KhachHang();
                    updated.nhapThongTinKH();
                    if (updated.getMaKH() != old.getMaKH() && !dsKhachHang.MaDuyNhat(updated.getMaKH())) {
                        System.out.println("Ma khach hang moi da ton tai, giu nguyen ma cu.");
                        updated.setMaKH(old.getMaKH());
                    }
                    if (dsKhachHang.sua(ma, updated)) System.out.println("Cap nhat khach hang thanh cong!");
                    else System.out.println("Cap nhat that bai!");
                    break;
                }
                case 4: {
                    System.out.print("Nhap ma khach hang can xoa: ");
                    int maX = Integer.parseInt(sc.nextLine());
                    if (dsKhachHang.timKiemKHTheoMa(maX) == null) {
                        System.out.println("Khong tim thay khach hang de xoa!");
                    } else {
                        dsKhachHang.xoa(maX);
                        System.out.println("Da xoa khach hang!");
                    }
                    break;
                }
                case 5: {
                    System.out.print("Nhap ma khach hang can tim: ");
                    int maT = Integer.parseInt(sc.nextLine());
                    KhachHang kh = dsKhachHang.timKiemKHTheoMa(maT);
                    if (kh == null) System.out.println("Khong tim thay khach hang co ma: " + maT);
                    else {
                        System.out.println("Khach hang tim thay:");
                        System.out.println(kh);
                    }
                    break;
                }
                case 6: {
                    System.out.print("Nhap ten khach hang can tim: ");
                    String ten = sc.nextLine();
                    KhachHang[] kq = dsKhachHang.timKiemTheoTen(ten);
                    if (kq == null || kq.length == 0) {
                        System.out.println("Khong tim thay khach hang co ten: " + ten);
                    } else {
                        System.out.println("Khach hang tim thay:");
                        for (KhachHang kh : kq) if (kh != null) System.out.println(kh);
                    }
                    break;
                }
                case 7: {
                    int[] stats = dsKhachHang.thongKeTheoGioiTinh();
                    System.out.println("So luong khach hang Nam: " + stats[0]);
                    System.out.println("So luong khach hang Nu: " + stats[1]);
                    break;
                }
                case 0:
                    try {
                        dsKhachHang.saveToFile(pathKhachHang);
                    } catch (IOException e) {
                        System.out.println("Loi khi luu khach hang: " + e.getMessage());
                    }
                    return;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        }
    }
}
