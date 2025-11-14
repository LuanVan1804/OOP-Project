package DU_LICH;

import java.util.Scanner;
import DU_LICH.NH_KS_PT.KhachSan;

public class QuanLyKhachSan extends QuanLy {
    public QuanLyKhachSan() { super(false); }

    public void khachSanMenu(Scanner sc) {
        while (true) {
            System.out.println("\n===== MENU QUAN LY KHACH SAN =====");
            System.out.println("1. Them");
            System.out.println("2. Sua");
            System.out.println("3. Xoa");
            System.out.println("4. Tim kiem theo ten");
            System.out.println("5. Tim kiem theo ma");
            System.out.println("6. Thong ke don gian");
            System.out.println("7. Hien thi danh sach");
            System.out.println("0. Thoat (luu)");
            System.out.print("Chon: ");
            while (!sc.hasNextInt()) {
                System.out.print("Vui long nhap so: ");
                sc.nextLine();
            }
            int chon = sc.nextInt();
            sc.nextLine();
            switch (chon) {
                case 1: {
                    KhachSan ks = dsKhachSan.nhapKhachSan(sc);
                    if (ks != null && dsKhachSan.them(ks))
                        System.out.println("Them thanh cong!");
                    break;
                }
                case 2: {
                    System.out.print("Nhap ma khach san can sua: ");
                    String ma = sc.nextLine();
                    if (dsKhachSan.suaTheoMaTuBanPhim(ma, sc))
                        System.out.println("Da cap nhat!");
                    else
                        System.out.println("Khong tim thay ma.");
                    break;
                }
                case 3: {
                    System.out.print("Nhap ma khach san can xoa: ");
                    String ma = sc.nextLine();
                    if (dsKhachSan.xoaTheoMa(ma))
                        System.out.println("Da xoa!");
                    else
                        System.out.println("Khong tim thay ma.");
                    break;
                }
                case 4: {
                    System.out.print("Nhap tu khoa tim theo ten: ");
                    String tk = sc.nextLine();
                    KhachSan[] kq = new KhachSan[dsKhachSan.getSoLuong()];
                    int n = dsKhachSan.timTheoTen(tk, kq);
                    if (n == 0)
                        System.out.println("Khong co ket qua.");
                    else {
                        System.out.println("Tim thay " + n + " ket qua:");
                        for (int i = 0; i < n; i++) {
                            if (kq[i] != null) {
                                kq[i].xuat();
                                System.out.println();
                            }
                        }
                    }
                    break;
                }
                case 5: {
                    System.out.print("Nhap ma can tim: ");
                    String ma = sc.nextLine();
                    KhachSan ks = dsKhachSan.timTheoMa(ma);
                    if (ks == null)
                        System.out.println("Khong tim thay.");
                    else
                        ks.xuat();
                    break;
                }
                case 6:
                    dsKhachSan.thongKeDonGian();
                    break;
                case 7:
                    dsKhachSan.xuatDanhSach();
                    break;
                case 0:
                    dsKhachSan.saveToFile(pathKhachSan);
                    return;
                default:
                    System.out.println("Lua chon khong hop le.");
            }
        }
    }
}
