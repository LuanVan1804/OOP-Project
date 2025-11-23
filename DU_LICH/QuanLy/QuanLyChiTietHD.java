package DU_LICH.QuanLy;

import java.util.Arrays;
import java.util.Scanner;
import DU_LICH.ClassDon.*;

public class QuanLyChiTietHD extends QuanLy {
    public QuanLyChiTietHD() {
        super(false);
    }

    public void menuChiTietHD(Scanner sc) {
        while (true) {
            System.out.println("\n=== QUAN LY CHI TIET HOA DON ===");
            System.out.println("1. Xem danh sach chi tiet");
            System.out.println("2. Xem chi tiet hoa don (full thong tin)");
            System.out.println("3. Thong ke");
            System.out.println("0. Thoat (luu du lieu)");
            System.out.print("Chon: ");

            int ch = -1;
            try { 
                ch = Integer.parseInt(sc.nextLine().trim()); 
            } catch (Exception e) { 
                ch = -1; 
            }

            switch (ch) {
                case 1: 
                    dsChiTietHD.hienThiDanhSach(); 
                    break;
                case 2: 
                    xemChiTietDayDu(sc); 
                    break;
                case 3: 
                    dsChiTietHD.thongKe(); 
                    break;
                case 0:
                    dsChiTietHD.saveToFile(PATH_CHITIET);
                    System.out.println("Da luu chi tiet hoa don.");
                    return;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        }
    }

    // ===================================================================
    // HÀM XEM CHI TIẾT HÓA ĐƠN ĐẦY ĐỦ - ĐÃ SỬA HOÀN HẢO, KHÔNG BỊ LẶP DỮ LIỆU
    // ===================================================================
    private void xemChiTietDayDu(Scanner sc) {
        System.out.print("Nhap ma hoa don: ");
        String maHD = sc.nextLine().trim();

        // Lấy hóa đơn chính
        HoaDon hd = dsHoaDon.timTheoMa(maHD);
        if (hd == null) {
            System.out.println("Khong tim thay hoa don!");
            return;
        }

        KeHoachTour kht = dsKeHoach.timTheoMa(hd.getMaKHTour());
        ChiPhiKHTour cp = dsChiPhi.timTheoMa(hd.getMaKHTour());

        // Lọc tất cả chi tiết của hóa đơn này (chỉ duyệt 1 lần)
        ChiTietHD[] dsChiTiet = new ChiTietHD[0];
        for (ChiTietHD ct : QuanLy.getDsChiTietHD().getList()) {
            if (ct != null && ct.getMaHD().equalsIgnoreCase(maHD)) {
                dsChiTiet = Arrays.copyOf(dsChiTiet, dsChiTiet.length + 1);
                dsChiTiet[dsChiTiet.length - 1] = ct;
            }
        }

        if (dsChiTiet.length == 0) {
            System.out.println("Khong co khach hang nao trong hoa don nay!");
            return;
        }

        // Tính thống kê
        int khachNguoiLon = 0, khachTreEm = 0;
        double tongTienVe = 0;
        for (ChiTietHD ct : dsChiTiet) {
            tongTienVe += ct.getThanhTien();
            if ("NguoiLon".equalsIgnoreCase(ct.getLoaiVe())) khachNguoiLon++;
            else if ("TreEm".equalsIgnoreCase(ct.getLoaiVe())) khachTreEm++;
        }

        // === HIỂN THỊ THÔNG TIN CHUNG ===
        System.out.println("┌────────────────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                           CHI TIET HOA DON DAY DU                                 │");
        System.out.println("├────────────────────────────────────────────────────────────────────────────────────┤");
        System.out.printf("│ Ma hoa don          : %-56s │%n", maHD);
        System.out.printf("│ Ngay lap            : %-56s │%n", 
                hd.getNgayLap().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        System.out.printf("│ HDV phu trach       : %-56s │%n", 
                dsHDV.timTheoMa(hd.getMaHDV()) != null 
                    ? dsHDV.timTheoMa(hd.getMaHDV()).getTenHDV() : "Chua co");
        System.out.printf("│ Khach dai dien      : %-56s │%n", 
                dsKhachHang.timKiemKHTheoMa(hd.getMaKHDaiDien()) != null 
                    ? dsKhachHang.timKiemKHTheoMa(hd.getMaKHDaiDien()).getTenKH() : "Chua co");

        if (kht != null) {
            System.out.printf("│ Ten ke hoach        : %-56s │%n", kht.getTenKeHoach());
            System.out.printf("│ Ma tour             : %-56s │%n", kht.getMaTour());
            System.out.printf("│ Ngay di - ve        : %-56s │%n", kht.getNgayDi() + " → " + kht.getNgayVe());
        }
        if (cp != null) {
            System.out.printf("│ Nha hang            : %-56s │%n", cp.getMaNhaHang());
            System.out.printf("│ Khach san           : %-56s │%n", cp.getMaKhachSan());
            System.out.printf("│ Phuong tien         : %-56s │%n", cp.getMaPhuongTien());
        }

        System.out.println("├────────────────────────────────────────────────────────────────────────────────────┤");
        System.out.printf("│ Tong so khach       : %-56d │%n", dsChiTiet.length);
        System.out.printf("│   ├─ Nguoi lon      : %-56d │%n", khachNguoiLon);
        System.out.printf("│   └─ Tre em         : %-56d │%n", khachTreEm);
        System.out.printf("│ Tong tien ve        : %-56s │%n", String.format("%,.0f VND", tongTienVe));
        if (cp != null) {
            double chiPhiDV = cp.getTongChi();
            System.out.printf("│ Chi phi dich vu     : %-56s │%n", String.format("%,.0f VND", chiPhiDV));
            System.out.printf("│ TONG CONG (ve + DV) : %-56s │%n", String.format("%,.0f VND", tongTienVe + chiPhiDV));
        }

        // === DANH SÁCH KHÁCH HÀNG ===
        System.out.println("├────────────────────────────────────────────────────────────────────────────────────┤");
        System.out.println("│ DANH SACH KHACH HANG TRONG DOAN                                                           │");
        System.out.println("│ STT │ Ma KH   │ Loai ve     │       Gia ve        │     Thanh tien      │");
        System.out.println("├────────────────────────────────────────────────────────────────────────────────────┤");

        for (int i = 0; i < dsChiTiet.length; i++) {
            ChiTietHD ct = dsChiTiet[i];
            System.out.printf("│ %3d │ %-7d │ %-11s │ %,18.0f │ %,18.0f │%n",
                    (i + 1),
                    ct.getMaKhachHang(),
                    ct.getLoaiVe(),
                    ct.getGiaVe(),
                    ct.getThanhTien());
        }

        System.out.println("└────────────────────────────────────────────────────────────────────────────────────┘");
    }
}