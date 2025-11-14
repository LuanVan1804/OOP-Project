package DU_LICH;

import java.util.Scanner;

public class QuanLyChiPhi extends QuanLy {
    public QuanLyChiPhi() { super(false); }

    public void chiPhiMenu(Scanner sc) {
        while (true) {
            System.out.println("\n===== MENU QUAN LY CHI PHI KE HOACH TOUR =====");
            System.out.println("1. Them chi phi");
            System.out.println("2. Xoa chi phi theo ma KHTour");
            System.out.println("3. Chinh sua chi phi theo ma KHTour");
            System.out.println("4. Hien thi danh sach chi phi");
            System.out.println("5. Tim kiem theo ma KHTour");
            System.out.println("6. Tim kiem theo tu khoa (ma KHTour)");
            System.out.println("7. Thong ke don gian");
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
                    dsChiPhi.themChiPhi();
                    break;
                case 2: {
                    System.out.print("Nhap ma KHTour can xoa chi phi: ");
                    String ma = sc.nextLine().trim();
                    if (dsChiPhi.xoaTheoMa(ma))
                        System.out.println("Da xoa!");
                    else
                        System.out.println("Khong tim thay ma.");
                    break;
                }
                case 3: {
                    System.out.print("Nhap ma KHTour can chinh sua chi phi: ");
                    String ma = sc.nextLine().trim();
                    if (dsChiPhi.chinhSuaTheoMa(ma))
                        System.out.println("Da cap nhat!");
                    else
                        System.out.println("Khong tim thay ma.");
                    break;
                }
                case 4:
                    dsChiPhi.xuatDanhSach();
                    break;
                case 5: {
                    System.out.print("Nhap ma KHTour can tim: ");
                    String ma = sc.nextLine().trim();
                    ChiPhiKHTour cp = dsChiPhi.timTheoMa(ma);
                    if (cp == null)
                        System.out.println("Khong tim thay.");
                    else
                        cp.xuatThongTin();
                    break;
                }
                case 6: {
                    System.out.print("Nhap tu khoa tim: ");
                    String tk = sc.nextLine().trim();
                    ChiPhiKHTour[] arr = dsChiPhi.timTheoTen(tk);
                    if (arr == null || arr.length == 0)
                        System.out.println("Khong co ket qua.");
                    else {
                        System.out.println("Tim thay " + arr.length + " ket qua:");
                        for (int i = 0; i < arr.length; i++)
                            if (arr[i] != null) {
                                arr[i].xuatThongTin();
                                System.out.println();
                            }
                    }
                    break;
                }
                case 7:
                    dsChiPhi.thongKeDonGian();
                    break;
                case 0:
                    dsChiPhi.saveToFile(pathChiPhi);
                    return;
                default:
                    System.out.println("Lua chon khong hop le.");
            }
        }
    }
}
