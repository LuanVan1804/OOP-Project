package DU_LICH.NH_KS_PT;

import java.util.Arrays;
import java.util.Scanner;

public class DSPhuongTien {
    private PhuongTien dspt[];
    private int soLuongpt;
    private final Scanner sc = new Scanner(System.in);

    public DSPhuongTien() {
        soLuongpt = 0;
        dspt = new PhuongTien[0];
    }

    public DSPhuongTien(PhuongTien[] dspt, int soLuongpt) {
        this.dspt = dspt == null ? new PhuongTien[0] : dspt;
        this.soLuongpt = Math.max(0, Math.min(soLuongpt, this.dspt.length));
    }

    public void them() {
        System.out.print("Nhập số lượng phương tiện cần thêm: ");
        int so;
        while (!sc.hasNextInt()) {
            System.out.print("Số nguyên, nhập lại: ");
            sc.next();
        }
        so = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < so; i++) {
            dspt = Arrays.copyOf(dspt, soLuongpt + 1);
            dspt[soLuongpt] = new PhuongTien();
            System.out.println("-- Nhập thông tin phương tiện thứ " + (soLuongpt + 1) + " --");
            dspt[soLuongpt].nhap();
            soLuongpt++;
        }
        System.out.println("Thêm thành công.\n");
    }

    public void xoa() {
        xuat();
        if (soLuongpt == 0) return;

        System.out.print("Nhập biển số xe cần xóa: ");
        String bienso = sc.nextLine().trim();

        int idx = -1;
        for (int i = 0; i < soLuongpt; i++) {
            if (dspt[i].getBienKiemSoat().equalsIgnoreCase(bienso)) {
                idx = i;
                break;
            }
        }

        if (idx == -1) {
            System.out.println("Không tìm thấy phương tiện.\n");
            return;
        }

        // shift left
        for (int j = idx; j < soLuongpt - 1; j++) {
            dspt[j] = dspt[j + 1];
        }
        soLuongpt--;
        dspt = Arrays.copyOf(dspt, soLuongpt); // shrink array
        System.out.println("Xóa thành công.\n");
    }

    public void sua() {
        xuat();
        if (soLuongpt == 0) return;

        System.out.print("Nhập biển số xe cần sửa: ");
        String bienso = sc.nextLine().trim();

        for (int i = 0; i < soLuongpt; i++) {
            if (dspt[i].getBienKiemSoat().equalsIgnoreCase(bienso)) {
                System.out.println("1. Sửa biển số xe");
                System.out.println("2. Sửa số chỗ ngồi");
                System.out.println("3. Sửa loại phương tiện");
                System.out.print("Chọn thông tin cần sửa (1-3): ");

                int chon;
                while (!sc.hasNextInt()) {
                    System.out.print("Số nguyên, nhập lại: ");
                    sc.next();
                }
                chon = sc.nextInt();
                sc.nextLine();

                switch (chon) {
                    case 1:
                        System.out.print("Nhập biển kiểm soát mới: ");
                        String bienMoi = sc.nextLine().trim();
                        dspt[i].setBienKiemSoat(bienMoi);
                        break;
                    case 2:
                        System.out.print("Nhập số chỗ ngồi mới: ");
                        while (!sc.hasNextInt()) {
                            System.out.print("Số nguyên, nhập lại: ");
                            sc.next();
                        }
                        int soChoMoi = sc.nextInt();
                        sc.nextLine();
                        dspt[i].setSoChoNgoi(soChoMoi);
                        break;
                    case 3:
                        System.out.print("Nhập loại phương tiện mới: ");
                        String loaiMoi = sc.nextLine().trim();
                        dspt[i].setLoaiPhuongTien(loaiMoi);
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ.");
                        return;
                }

                System.out.println("=================================");
                System.out.println("========= Sửa thành công ========");
                System.out.println("=================================\n");
                return;
            }
        }
        System.out.println("Không tìm thấy phương tiện.\n");
    }

    public void timKiem() {
        System.out.print("Nhập biển số xe cần tìm: ");
        String tim = sc.nextLine().trim();
        for (int i = 0; i < soLuongpt; i++) {
            if (dspt[i].getBienKiemSoat().equalsIgnoreCase(tim)) {
                dspt[i].xuat();
                return;
            }
        }
        System.out.println("Không tìm thấy phương tiện.\n");
    }

    public void xuat() {
        if (soLuongpt == 0) {
            System.out.println("Danh sách phương tiện trống.\n");
            return;
        }
        System.out.println("Danh sách phương tiện:");
        for (int i = 0; i < soLuongpt; i++) {
            System.out.println("Phương tiện thứ " + (i + 1) + ":");
            System.out.println("Biển kiểm soát : " + dspt[i].getBienKiemSoat());
            System.out.println("Số chỗ ngồi     : " + dspt[i].getSoChoNgoi());
            System.out.println("Loại phương tiện: " + dspt[i].getLoaiPhuongTien());
            System.out.println();
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("Danh sách phương tiện");
        for (int i = 0; i < soLuongpt; i++) {
            str.append("\n\nPhương tiện thứ ").append(i + 1).append(".");
            str.append(dspt[i].toString());
        }
        return str.toString();
    }

    public void showMenu() {
        int chon;
        do {
            System.out.println("=== MENU PHƯƠNG TIỆN ===");
            System.out.println("1. Thêm phương tiện");
            System.out.println("2. Xóa phương tiện");
            System.out.println("3. Sửa phương tiện");
            System.out.println("4. Tìm kiếm phương tiện");
            System.out.println("5. Xuất danh sách phương tiện");
            System.out.println("6. Thoát");
            System.out.print("Vui lòng chọn (1-6): ");

            while (!sc.hasNextInt()) {
                System.out.print("Số nguyên, nhập lại: ");
                sc.next();
            }
            chon = sc.nextInt();
            sc.nextLine();

            switch (chon) {
                case 1:
                    them();
                    break;
                case 2:
                    xoa();
                    break;
                case 3:
                    sua();
                    break;
                case 4:
                    timKiem();
                    break;
                case 5:
                    xuat();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Nhập sai! Vui lòng chọn lại.\n");
            }
        } while (true);
    }
}
