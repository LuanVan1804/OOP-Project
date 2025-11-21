package DU_LICH.QuanLy;

import java.io.IOException;
import java.util.Scanner;

import DU_LICH.ClassDon.HDV;
import DU_LICH.DanhSach.DSHDV;

public class QuanLyHDV extends QuanLy {
    public QuanLyHDV() {
        super(false);
    }

    public void hdvMenu(Scanner sc) {
        while (true) {
            System.out.println("\n========== MENU QUAN LY HUONG DAN VIEN ==========");
            System.out.println("1. Hien thi danh sach HDV");
            System.out.println("2. Them HDV moi");
            System.out.println("3. Sua thong tin HDV");
            System.out.println("4. Xoa HDV");
            System.out.println("5. Tim kiem theo ten");
            System.out.println("6. Thong ke theo kinh nghiem");
            System.out.println("0. Thoat (luu)");
            System.out.print("Chon: ");
            int chon = Integer.parseInt(sc.nextLine());
            switch (chon) {
                case 1: {
                    HDV[] all = dsHDV.timKiemTheoTen(""); // lay toan bo neu DS ho tro; o day co the them getter neu can
                    if (all == null || all.length == 0) {
                        System.out.println("Danh sach HDV rong!");
                    } else {
                        System.out.println("Danh sach HDV:");
                        for (HDV h : all) {
                            if (h != null)
                                System.out.println(h);
                        }
                    }
                    break;
                }
                case 2: {
                    HDV hdv = new HDV();
                    hdv.nhapThongTinHDV();
                    if (!dsHDV.MaDuyNhat(hdv.getMaHDV())) {
                        System.out.println("Ma HDV da ton tai, khong the them.");
                    } else {
                        dsHDV.them(hdv);
                        System.out.println("Da them HDV moi!");
                    }
                    break;
                }
                case 3: {
                    System.out.print("Nhap ma HDV can sua: ");
                    String input = sc.nextLine();
                    if (input == null || input.trim().isEmpty()) {
                        System.out.println("Ma HDV khong hop le!");
                        break;
                    }
                    try {
                        int ma = Integer.parseInt(input);
                        dsHDV.sua(ma);
                    } catch (NumberFormatException e) {
                        System.out.println("Ma HDV phai la so nguyen!");
                    }
                    break;
                }
                case 4: {
                    System.out.print("Nhap ma HDV can xoa: ");
                    int maX = Integer.parseInt(sc.nextLine());
                    if (dsHDV.timTheoMa(maX) == null) {
                        System.out.println("Khong tim thay HDV de xoa!");
                    } else {
                        dsHDV.xoa(maX);
                        System.out.println("Da xoa HDV!");
                    }
                    break;
                }
                case 5: {
                    System.out.print("Nhap ten HDV can tim: ");
                    String ten = sc.nextLine();
                    HDV[] kq = dsHDV.timKiemTheoTen(ten);
                    if (kq == null || kq.length == 0) {
                        System.out.println("Khong tim thay HDV voi ten: " + ten);
                    } else {
                        System.out.println("Danh sach HDV tim thay:");
                        for (HDV h : kq)
                            if (h != null)
                                System.out.println(h);
                    }
                    break;
                }
                case 6: {
                    DSHDV.ThongKeKinhNghiem tk = dsHDV.thongKeTheoKinhNghiem();
                    if (tk.soLoai == 0) {
                        System.out.println("Danh sach HDV rong, khong the thong ke.");
                    } else {
                        System.out
                                .println("------------------ Thong ke HDV theo kinh nghiem -------------------------");
                        for (int i = 0; i < tk.soLoai; i++) {
                            System.out.printf("Kinh nghiem: %.1f - So luong HDV: %d%n", tk.kinhNghiemValues[i],
                                    tk.dem[i]);
                        }
                    }
                    break;
                }
                case 0:
                    try {
                        dsHDV.saveToFile(PATH_HDV);
                    } catch (IOException e) {
                        System.out.println("Loi khi luu HDV: " + e.getMessage());
                    }
                    return;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        }
    }
}
