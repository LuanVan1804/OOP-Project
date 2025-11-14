package DU_LICH;

import java.io.IOException;
import java.util.Scanner;
import DU_LICH.TourDuLich.Tour;

public class QuanLyKeHoach extends QuanLy {
    public QuanLyKeHoach() {
        super(false);
    }

    public void keHoachMenu(Scanner sc) {
        while (true) {
            System.out.println("\n===== MENU QUẢN LÝ KẾ HOẠCH TOUR =====");
            System.out.println("1. Thêm kế hoạch");
            System.out.println("2. Xóa kế hoạch theo mã");
            System.out.println("3. Sửa kế hoạch theo mã");
            System.out.println("4. Hiển thị danh sách");
            System.out.println("5. Tìm kiếm theo mã");
            System.out.println("6. Tìm kiếm theo tên tour");
            System.out.println("7. Thống kê doanh thu - chi phí");
            System.out.println("0. Thoát (lưu dữ liệu)");
            System.out.print("Chọn: ");

            String line = sc.nextLine().trim();
            int ch = -1;
            try {
                ch = Integer.parseInt(line);
            } catch (Exception e) {
                ch = -1;
            }

            switch (ch) {
                case 1:
                    themKeHoachMoi(sc);
                    break;
                case 2:
                    xoaKeHoach(sc);
                    break;
                case 3:
                    suaKeHoach(sc);
                    break;
                case 4:
                    dsKeHoach.hienThiDanhSachKHTour(dsChiPhi);
                    break;
                case 5:
                    timKiemTheoMa(sc);
                    break;
                case 6:
                    timKiemTheoTuKhoa(sc);
                    break;
                case 7:
                    dsKeHoach.thongKe(dsChiPhi);
                    break;
                case 0:
                    try {
                        dsKeHoach.saveToFile(pathKeHoach);
                        if (dsChiPhi != null) dsChiPhi.saveToFile(pathChiPhi);
                        System.out.println("Đã lưu dữ liệu kế hoạch tour.");
                    } catch (IOException e) {
                        System.out.println("Lỗi khi lưu: " + e.getMessage());
                    }
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private void themKeHoachMoi(Scanner sc) {
        KeHoachTour kht = new KeHoachTour();
        kht.nhapThongTin();

        // Validate mã Tour tồn tại
        Tour tour = (dsTour == null) ? null : dsTour.timTheoMa(kht.getMaTour());
        if (tour == null) {
            System.out.println("Mã Tour không tồn tại trong hệ thống!");
            return;
        }
        kht.setGiaVe(tour.getDonGia());

        // Validate mã HDV (nếu có nhập)
        if (!kht.getMaHDV().trim().isEmpty()) {
            try {
                int maHDV = Integer.parseInt(kht.getMaHDV().trim());
                if (dsHDV.timTheoMa(maHDV) == null) {
                    System.out.println("Mã HDV không tồn tại!");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Mã HDV phải là số nguyên!");
                return;
            }
        }

        boolean ok = dsKeHoach.themKHTour(kht);
        if (ok) System.out.println("Đã thêm kế hoạch mới.");
        else System.out.println("Thêm thất bại: mã đã tồn tại hoặc danh sách đã đầy.");
    }

    private void xoaKeHoach(Scanner sc) {
        System.out.print("Nhập mã kế hoạch cần xóa: ");
        String ma = sc.nextLine().trim();
        if (dsKeHoach.xoaKHTour(ma)) {
            if (dsChiPhi != null && dsChiPhi.xoaTheoMa(ma)) {
                System.out.println("Đã xóa chi phí ăn ở liên quan.");
            }
        }
    }

    private void suaKeHoach(Scanner sc) {
        System.out.print("Nhập mã kế hoạch cần sửa: ");
        String ma = sc.nextLine().trim();
        KeHoachTour k = dsKeHoach.timTheoMaObject(ma);
        if (k == null) {
            System.out.println("Không tìm thấy kế hoạch tour với mã: " + ma);
            return;
        }

        System.out.println("Để trống và nhấn Enter nếu muốn giữ nguyên giá trị.");

        // Mã KHTour là khóa chính — không cho phép sửa
        System.out.println("Mã KHTour (không thể sửa): " + k.getMaKHTour());

        System.out.print("Mã Tour hiện tại (" + k.getMaTour() + "): ");
        String input = sc.nextLine().trim();
        if (!input.isEmpty()) {
            // validate tour exists
            if (dsTour != null && dsTour.timTheoMa(input) == null) {
                System.out.println("Mã Tour không tồn tại; bỏ qua thay đổi.");
            } else {
                k.setMaTour(input);
                // update giaVe từ DSTour
                if (dsTour != null) {
                    Tour t = dsTour.timTheoMa(input);
                    if (t != null) k.setGiaVe(t.getDonGia());
                }
            }
        }

        System.out.print("Mã HDV hiện tại (" + k.getMaHDV() + "): ");
        input = sc.nextLine().trim();
        if (!input.isEmpty()) {
            try {
                int maHDV = Integer.parseInt(input);
                if (dsHDV != null && dsHDV.timTheoMa(maHDV) == null) {
                    System.out.println("Mã HDV không tồn tại; bỏ qua thay đổi.");
                } else {
                    k.setMaHDV(input);
                }
            } catch (NumberFormatException ex) {
                System.out.println("Mã HDV phải là số nguyên; bỏ qua thay đổi.");
            }
        }

        System.out.print("Tổng số vé hiện tại (" + k.getTongSoVe() + "): ");
        input = sc.nextLine().trim();
        if (!input.isEmpty()) {
            try {
                int val = Integer.parseInt(input);
                k.setTongSoVe(val);
            } catch (NumberFormatException ex) {
                System.out.println("Số vé không hợp lệ; bỏ qua thay đổi.");
            }
        }

        System.out.print("Vé đã đặt hiện tại (" + k.getTongVeDaDat() + "): ");
        input = sc.nextLine().trim();
        if (!input.isEmpty()) {
            try {
                int val = Integer.parseInt(input);
                k.setTongVeDaDat(val);
            } catch (NumberFormatException ex) {
                System.out.println("Số vé không hợp lệ; bỏ qua thay đổi.");
            }
        }

        System.out.println("Chỉnh sửa kế hoạch tour hoàn tất.");
    }

    private void timKiemTheoMa(Scanner sc) {
        System.out.print("Nhập mã kế hoạch cần tìm: ");
        String ma = sc.nextLine().trim();
        dsKeHoach.timKiemTheoMa(ma, dsChiPhi);
    }

    private void timKiemTheoTuKhoa(Scanner sc) {
        System.out.print("Nhập từ khóa mã tour: ");
        String tk = sc.nextLine().trim();
        KeHoachTour[] kq = dsKeHoach.timKiemTheoTen(tk);
        if (kq.length == 0) {
            System.out.println("Không tìm thấy kết quả nào.");
        } else {
            System.out.println("Tìm thấy " + kq.length + " kết quả:");
                for (KeHoachTour k : kq) {
                    k.hienThiThongTin(dsChiPhi);
                    System.out.println(new String(new char[50]).replace('\0','-'));
                }
        }
    }
}