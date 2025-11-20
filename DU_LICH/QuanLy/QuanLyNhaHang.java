package DU_LICH.QuanLy;

import java.util.Scanner;

import DU_LICH.ClassDon.NhaHang;

public class QuanLyNhaHang extends QuanLy {
    public QuanLyNhaHang() { super(false); }

    public void nhaHangMenu(Scanner sc) {
        while (true) {
            System.out.println("\n========= MENU QUẢN LÝ NHÀ HÀNG =========");
            System.out.println("1. Them");
            System.out.println("2. Sua");
            System.out.println("3. Xoa");
            System.out.println("4. Tim kiem theo ten");
            System.out.println("5. Tim kiem theo ma");
            System.out.println("6. Thong ke");
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
                    NhaHang nh = new NhaHang();
                    nh.nhap();
                    if (dsNhaHang.them(nh))
                        System.out.println("Them thanh cong!");
                    break;
                }
                case 2: {
                    System.out.print("Nhap ma can sua: ");
                    String ma = sc.nextLine();
                    if (dsNhaHang.suaTheoMaTuBanPhim(ma, sc))
                        System.out.println("Da cap nhat!");
                    else
                        System.out.println("Khong tim thay ma.");
                    break;
                }
                case 3: {
                    System.out.print("Nhap ma can xoa: ");
                    String ma = sc.nextLine();
                    if (dsNhaHang.xoaTheoMa(ma))
                        System.out.println("Da xoa!");
                    else
                        System.out.println("Khong tim thay ma.");
                    break;
                }
                case 4: {
                    System.out.print("Nhap tu khoa tim theo ten: ");
                    String tk = sc.nextLine();
                    NhaHang[] kq = new NhaHang[dsNhaHang.getSoLuong()];
                    int n = dsNhaHang.timTheoTen(tk, kq);
                    if (n == 0)
                        System.out.println("Khong co ket qua.");
                    else {
                        System.out.println("Tim thay " + n + " ket qua:");
                        for (int i = 0; i < n && i < kq.length; i++)
                            if (kq[i] != null) {
                                kq[i].xuat();
                                System.out.println();
                            }
                    }
                    break;
                }
                case 5: {
                    System.out.print("Nhap ma can tim: ");
                    String ma = sc.nextLine();
                    NhaHang nh = dsNhaHang.timTheoMa(ma);
                    if (nh == null)
                        System.out.println("Khong tim thay.");
                    else
                        nh.xuat();
                    break;
                }
                case 6:
                    dsNhaHang.thongKeDonGian();
                    break;
                case 7:
                    dsNhaHang.xuatDanhSach();
                    break;
                case 0:
                    dsNhaHang.saveToFile(PATH_NHAHANG);
                    return;
                default:
                    System.out.println("Lua chon khong hop le.");
            }
        }
    }
}
