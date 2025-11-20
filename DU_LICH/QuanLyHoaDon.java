package DU_LICH;

import DU_LICH.Nguoi.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

public class QuanLyHoaDon {
    private final DSHoaDon dsHoaDon;
    private final DSHDV dsHDV;
    private final DSKhachHang dsKhachHang;
    private final DSKHTour dsKHTour;
    private final DSChiTietHD dsChiTiet;
    private final DSChiPhiKHTour dsChiPhi;
    private final Scanner sc;

    public QuanLyHoaDon(DSHoaDon dsHoaDon,DSHDV dsHDV,DSKhachHang dsKhachHang,DSKHTour dsKHTour,DSChiTietHD dsChiTiet,DSChiPhiKHTour dsChiPhi,Scanner sc) {
        this.dsHoaDon = dsHoaDon;
        this.dsHDV = dsHDV;
        this.dsKhachHang = dsKhachHang;
        this.dsKHTour = dsKHTour;
        this.dsChiTiet = dsChiTiet;
        this.dsChiPhi = dsChiPhi;
        this.sc = sc;
        // Gan dsChiPhi vao dsHoaDon
        this.dsHoaDon.setDsChiPhi(dsChiPhi);
    }

    // ===== KIEM TRA HOP LE =====
    // Kiem tra ma hoa don da ton tai chua
    public boolean kiemTraMaHoaDonTonTai(String maHD) {
        for (HoaDon hd : dsHoaDon.getList()) {
            if (hd != null && hd.getMaHD().equals(maHD))
                return true;
        }
        return false;
    }

    // Kiem tra ma HDV co ton tai khong
    public boolean validateMaHDV(int maHDV) {
        for (HDV hdv : dsHDV.getList()) {
            if (hdv.getMaHDV() == maHDV)
                return true;
        }
        return false;
    }

    // Kiem tra ma khach hang co ton tai khong
    public boolean validateMaKH(int maKH) {
        for (KhachHang kh : dsKhachHang.getList()) {
            if (kh.getMaKH() == maKH)
                return true;
        }
        return false;
    }

    // Kiem tra ma ke hoach tour co ton tai khong
    public boolean validateMaKHTour(String maKHTour) {
        if (maKHTour == null)
            return false;
        for (KeHoachTour kht : dsKHTour.getList()) {
            if (kht != null && kht.getMaKHTour() != null && kht.getMaKHTour().equals(maKHTour))
                return true;
        }
        return false;
    }

    // Kiem tra so khach hop le (> 0 va <= so ve con lai)
    public boolean kiemTraSoKhachHopLe(KeHoachTour kht, int soKhach) {
        if (soKhach <= 0) {
            System.out.println("So khach phai lon hon 0.");
            return false;
        }
        if (kht != null && soKhach > kht.getTongSoVe()) {
            System.out.println("So khach vuot qua so ve con lai (" + kht.getTongSoVe() + " ve).");
            return false;
        }
        return true;
    }

    // Kiem tra so ve con lai du khong (tongSoVe = ve con lai)
    public boolean checkSoVeHopLe(KeHoachTour kht, int soVeDat, int soKhach) {
        if (kht == null) {
            System.out.println("Ke hoach tour khong ton tai.");
            return false;
        }
        int veConLai = kht.getTongSoVe();
        if (soVeDat <= 0) {
            System.out.println("So ve phai lon hon 0.");
            return false;
        } else if (soVeDat > veConLai) {
            System.out.println("So ve dat vuot qua so ve con lai: " + veConLai);
            return false;
        } else if (soVeDat > soKhach) {
            System.out.println("So ve dat khong hop le so voi so khach : " + soKhach);
            return false;
        }
        return true;
    }

    public void menu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n=== MENU QUAN LY HOA DON ===");
            System.out.println("1. Hien thi danh sach hoa don");
            System.out.println("2. Them hoa don (tu dong tao chi tiet)");
            System.out.println("3. Sua hoa don (tu dong cap nhat chi tiet)");
            System.out.println("4. Xoa hoa don (tu dong xoa chi tiet)");
            System.out.println("5. Tim hoa don theo ma");
            System.out.println("6. Tim hoa don theo khach hang dai dien");
            System.out.println("7. Thong ke hoa don");
            System.out.println("8. Theo khoang ngay tu ngay A -  ngay B");
            System.out.println("9. Xem chi tiet hoa don");
            System.out.println("10. Xem tat ca chi tiet");
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
                    dsHoaDon.hienThiDanhSachHoaDon();
                    break;
                case 2:
                    dsKHTour.hienThiDanhSachKHTour(null);
                    themHoaDon();
                    break;
                case 3:
                    suaHoaDon();
                    break;
                case 4:
                    xoaHoaDon();
                    break;
                case 5:
                    timHoaDonTheoMa();
                    break;
                case 6:
                    timHoaDonTheoKhachHang();
                    break;
                case 7:
                    dsHoaDon.thongKeHoaDon();
                    break;
                case 8:
                    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate ngayA, ngayB;

                    while (true) {
                        try {
                            System.out.print("Nhap ngay A (dd/MM/yyyy): ");
                            ngayA = LocalDate.parse(sc.nextLine().trim(), fmt);

                            System.out.print("Nhap ngay B (dd/MM/yyyy): ");
                            ngayB = LocalDate.parse(sc.nextLine().trim(), fmt);

                            if (ngayB.isBefore(ngayA)) {
                                System.out.println("Ngay B phai sau ngay A! Nhap lai.");
                                continue;
                            }
                            break;
                        } catch (Exception e) {
                            System.out.println("Nhap sai dinh dang ngay! (dd/MM/yyyy), nhap lai.");
                        }
                    }

                    dsHoaDon.ThongKeHoaDonTuNgayADenNgayB(ngayA, ngayB);
                    break;

                case 9:
                    xemChiTietTheoMaHD();
                    break;
                case 10:
                    dsChiTiet.hienThiDanhSach();
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Lua chon khong hop le.");
            }
        }
    }

    // ===== THEM HOA DON THEO FORM CO GIAO =====
    // Them hoa don voi chi tiet, nhap tung khach hang, tinh tien ngay
    public void themHoaDon() {
        // BUOC 1: NHAP THONG TIN CHUNG
        HoaDon hoaDon = new HoaDon();
        System.out.println("\n" + "=".repeat(60));
        System.out.println("              TAO HOA DON TOUR DU LICH");
        System.out.println("=".repeat(60));
        hoaDon.nhap(sc);

        // kiem tra rang buoc ma hoa don (KHÔNG được rỗng VÀ KHÔNG được trùng)
        while (hoaDon.getMaHD() == null || hoaDon.getMaHD().trim().isEmpty()
                || kiemTraMaHoaDonTonTai(hoaDon.getMaHD())) {
            System.out.print("Nhap lai ma hoa don: ");
            String maHD = sc.nextLine().trim();
            hoaDon.setMaHD(maHD);
        }
        // BUOC 2: kiem rang buoc ma HDV ton tai
        while (!validateMaHDV(hoaDon.getMaHDV().getMaHDV())) {
            System.out.println("Ma HDV khong ton tai! Vui long chon lai.");
            System.out.print("Nhap ma HDV phu trach: ");
            int maHDV = Integer.parseInt(sc.nextLine().trim());
            hoaDon.getMaHDV().setMaHDV(maHDV);
        }

        // BUOC 3: KIEM TRA MA KE HOACH TOUR (không rỗng VÀ tồn tại)
        while (hoaDon.getMaKHTour().getMaKHTour() == null ||
                hoaDon.getMaKHTour().getMaKHTour().trim().isEmpty() ||
                !validateMaKHTour(hoaDon.getMaKHTour().getMaKHTour())) {
            System.out.print("Nhap lai ma ke hoach tour: ");
            String maKHTour = sc.nextLine().trim();
            hoaDon.getMaKHTour().setMaKHTour(maKHTour);
        }
        // lay ke hoach tour de lam viec tiep -> de làm cac buoc sau
        KeHoachTour kht = dsKHTour.timTheoMaObject(hoaDon.getMaKHTour().getMaKHTour());
        if (kht == null) {
            return;
        }

        // FIX: Gan day du thong tin KeHoachTour vao HoaDon (bao gom ca giaVe)
        hoaDon.setMaKHTour(kht);

        // BUOC 4: validate ma khach hang dai dien
        while (!validateMaKH(hoaDon.getMaKHDaiDien().getMaKH())) {
            System.out.println("Ma khach hang dai dien khong ton tai! Vui long chon lai.");
            System.out.print("Nhap ma khach hang dai dien: ");
            int maKHDaiDien = Integer.parseInt(sc.nextLine().trim());
            hoaDon.getMaKHDaiDien().setMaKH(maKHDaiDien);
        }

        // BUOC 5: validate so khach hop le
        while (!kiemTraSoKhachHopLe(kht, hoaDon.getSoKhach())) {
            System.out.println("So khach khong hop le! Vui long nhap lai.");
            System.out.print("Nhap so khach di tour: ");
            int soKhach = Integer.parseInt(sc.nextLine().trim());
            hoaDon.setSoKhach(soKhach);

        }

        // BUOC 6: validate so ve hop le
        while (!checkSoVeHopLe(kht, hoaDon.getSoVe(), hoaDon.getSoKhach())) {
            System.out.println("So ve khong hop le! Vui long nhap lai.");
            System.out.print("Nhap so ve: ");
            int soVe = Integer.parseInt(sc.nextLine().trim());
            hoaDon.setSoVe(soVe);
        }

        // buoc 7 : Nhap chi tiet khach hang di tour theo so ve da dat
        System.out.println("\nNhap chi tiet khach hang di tour:");
        int[] dsMaKH = new int[hoaDon.getSoVe()];
        double tongTien = 0.0;
        double giaVe = kht.getGiaVe();

        System.out.println("\n" + "=".repeat(60));
        System.out.println("                NHAP TUNG KHACH HANG");
        System.out.println("=".repeat(60));

        for (int i = 0; i < hoaDon.getSoVe(); i++) {
            System.out.println("\n--- Khach hang thu " + (i + 1) + " ---");
            // Nhap ma khach hang voi cac kiem tra
            int maKH = 0;
            while (true) {
                System.out.print("Nhap ma khach hang: ");
                maKH = Integer.parseInt(sc.nextLine());

                // Kiem tra ton tai
                if (!validateMaKH(maKH)) {
                    System.out.println("Ma KH khong ton tai! Nhap lai.");
                    continue;
                }

                // Kiem tra trung lap --- dam bao khach hang khong bi trung lap trong cung 1 hoa
                // don
                boolean daTonTai = false;
                for (int j = 0; j < i; j++) {
                    if (dsMaKH[j] == maKH) {
                        daTonTai = true;
                        break;
                    }
                }

                if (daTonTai) {
                    System.out.println("Khach hang nay da duoc nhap! Vui long chon khach khac.");
                    continue;
                }

                // Hop le - luu vao danh sach
                dsMaKH[i] = maKH;
                break;
            }

            // Tinh tien cho khach nay
            int soLuong = 1; // Moi khach 1 ve
            double thanhTien = soLuong * giaVe;
            tongTien += thanhTien;

            System.out.println("  + Don gia: " + String.format("%,.0f VND", giaVe));
            System.out.println("  + Thanh tien: " + String.format("%,.0f VND", thanhTien));
            System.out.println("  + Tong tich luy: " + String.format("%,.0f VND", tongTien));
        }

        // BUOC 6: HIEN THI TONG KET
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                   TONG KET HOA DON");
        hoaDon.xuatThongTin();
        System.out.println("=".repeat(60));

        // // Cap nhat hoa don
        // hoaDon.setSoKhach(hoaDon.getSoKhach());
        // hoaDon.setSoVe(hoaDon.getSoVe());

        // // Them vao danh sach
        // dsHoaDon.them(hoaDon);

        // // Cap nhat ke hoach tour (giam ve con lai, tang ve da ban)
        // kht.setTongSoVe(kht.getTongSoVe() - hoaDon.getSoKhach());
        // kht.setTongVeDaDat(kht.getTongVeDaDat() + hoaDon.getSoKhach());

        // // TU DONG TAO CHI TIET HOA DON (CASCADE INSERT)
        // dsChiTiet.taoChiTiet(hoaDon, dsMaKH);

        // System.out.println("\n>>> TAO HOA DON THANH CONG! <<<");
        // } catch (IOException e) {
        // System.out.println("Loi luu file: " + e.getMessage());
        // e.printStackTrace(); // In stack trace de debug
        // }
        // Cap nhat ve trong ke hoach tour
        kht.setTongSoVe(kht.getTongSoVe() - hoaDon.getSoKhach());
        kht.setTongVeDaDat(kht.getTongVeDaDat() + hoaDon.getSoKhach());

        // Tao chi tiet hoa don
        ChiTietHD chiTiet = new ChiTietHD(hoaDon.getMaHD(), kht.getMaKHTour(), dsMaKH, giaVe);
        dsChiTiet.them(chiTiet);

        // Them vao danh sach hoa don
        dsHoaDon.them(hoaDon);

        try {
            dsHoaDon.saveToFile("DU_LICH/DSHoaDon.txt");
            dsKHTour.saveToFile("DU_LICH/KeHoachTour.txt");
            dsChiTiet.saveToFile("DU_LICH/DSChiTietHD.txt");
            System.out.println("Da tao hoa don va chi tiet thanh cong!");
        } catch (IOException e) {
            System.out.println("Loi luu file: " + e.getMessage());
        }
    }

    private void suaHoaDon() {
        System.out.print("Nhap ma hoa don can sua: ");
        String maHD = sc.nextLine();
        HoaDon hdCu = dsHoaDon.timHoaDonTheoMa(maHD);

        if (hdCu == null) {
            System.out.println("Khong tim thay hoa don.");
            return;
        }

        System.out.println("Thong tin hoa don hien tai:");
        hdCu.xuatThongTin();

        // ===== Hoàn trả vé cũ =====
        KeHoachTour khtCu = dsKHTour.timTheoMaObject(hdCu.getMaKHTour().getMaKHTour());
        if (khtCu != null) {
            khtCu.setTongSoVe(khtCu.getTongSoVe() + hdCu.getSoVe());
            khtCu.setTongVeDaDat(khtCu.getTongVeDaDat() - hdCu.getSoVe());
        }

        System.out.println("\n===== NHAP THONG TIN MOI =====");
        HoaDon hdMoi = new HoaDon();

        // Nhập HD mới nhưng giữ nguyên mã cũ
        hdMoi.nhap(sc);
        hdMoi.setMaHD(maHD);
        System.out.println("Ma hoa don SE KHONG DUOC THAY DOI: " + maHD);

        // ===== Validate mã HDV =====
        while (!validateMaHDV(hdMoi.getMaHDV().getMaHDV())) {
            System.out.println("Ma HDV khong ton tai! Nhap lai:");
            hdMoi.getMaHDV().setMaHDV(Integer.parseInt(sc.nextLine()));
        }

        // ===== Validate mã Kế Hoạch Tour =====
        while (!validateMaKHTour(hdMoi.getMaKHTour().getMaKHTour())) {
            System.out.println("Ma ke hoach tour khong ton tai! Nhap lai:");
            hdMoi.getMaKHTour().setMaKHTour(sc.nextLine());
        }

        // Nếu tour đổi, lấy đối tượng mới, nếu không thì dùng khtCu
        KeHoachTour khtMoi = dsKHTour.timTheoMaObject(hdMoi.getMaKHTour().getMaKHTour());
        hdMoi.setMaKHTour(khtMoi);

        // ===== Validate khách hàng đại diện =====
        while (!validateMaKH(hdMoi.getMaKHDaiDien().getMaKH())) {
            System.out.println("Ma KH dai dien khong ton tai! Nhap lai:");
            hdMoi.getMaKHDaiDien().setMaKH(Integer.parseInt(sc.nextLine()));
        }

        // ===== Validate số khách =====
        while (!kiemTraSoKhachHopLe(khtMoi, hdMoi.getSoKhach())) {
            System.out.println("So khach khong hop le! Nhap lai:");
            hdMoi.setSoKhach(Integer.parseInt(sc.nextLine()));
        }

        // ===== Validate số vé =====
        while (!checkSoVeHopLe(khtMoi, hdMoi.getSoVe(), hdMoi.getSoKhach())) {
            System.out.println("So ve khong hop le! Nhap lai:");
            hdMoi.setSoVe(Integer.parseInt(sc.nextLine()));
        }

        // ===== Nhập danh sách khách đi tour =====
        System.out.println("\n===== NHAP DS KHACH HANG DI TOUR =====");
        int[] dsMaKH = new int[hdMoi.getSoVe()];
        for (int j = 0; j < hdMoi.getSoVe(); j++) {
            while (true) {
                System.out.print("Nhap ma KH thu " + (j + 1) + ": ");
                int maKH = Integer.parseInt(sc.nextLine());

                if (!validateMaKH(maKH)) {
                    System.out.println("Ma KH khong ton tai!");
                    continue;
                }

                boolean trung = false;
                for (int k = 0; k < j; k++) {
                    if (dsMaKH[k] == maKH) {
                        trung = true;
                        break;
                    }
                }
                if (trung) {
                    System.out.println("KH da nhap roi!");
                    continue;
                }

                dsMaKH[j] = maKH;
                break;
            }
        }

        // ===== Cập nhật vé cho tour =====
        khtMoi.setTongSoVe(khtMoi.getTongSoVe() - hdMoi.getSoVe());
        khtMoi.setTongVeDaDat(khtMoi.getTongVeDaDat() + hdMoi.getSoVe());

        // ===== Cascade update chi tiết HD =====
        dsChiTiet.xoa(maHD);
        ChiTietHD chiTietMoi = new ChiTietHD(maHD, khtMoi.getMaKHTour(), dsMaKH, khtMoi.getGiaVe());
        dsChiTiet.them(chiTietMoi);

        // ===== Cập nhật HD trong danh sách =====
        for (int i = 0; i < dsHoaDon.getSoLuongHoaDon(); i++) {
            if (dsHoaDon.getList()[i].getMaHD().equals(maHD)) {
                dsHoaDon.getList()[i] = hdMoi;
                break;
            }
        }

        // ===== Lưu file =====
        try {
            dsHoaDon.saveToFile("DU_LICH/DSHoaDon.txt");
            dsKHTour.saveToFile("DU_LICH/KeHoachTour.txt");
            dsChiTiet.saveToFile("DU_LICH/DSChiTietHD.txt");
            System.out.println("Sua hoa don va cap nhat chi tiet thanh cong!");
        } catch (IOException e) {
            System.out.println("Loi luu file: " + e.getMessage());
        }
    }

    private void xoaHoaDon() {
        System.out.print("Nhap ma hoa don can xoa: ");
        String maHD = sc.nextLine();
        HoaDon hd = dsHoaDon.timHoaDonTheoMa(maHD);
        if (hd == null) {
            System.out.println("Khong tim thay hoa don.");
            return;
        }

        System.out.println("Dang xoa hoa don: " + maHD);
        hd.xuatThongTin();

        // Hoan tra ve
        KeHoachTour kht = dsKHTour.timTheoMaObject(hd.getMaKHTour().getMaKHTour());
        if (kht != null) {
            kht.setTongSoVe(kht.getTongSoVe() + hd.getSoVe());
            kht.setTongVeDaDat(kht.getTongVeDaDat() - hd.getSoVe());
        }

        // TU DONG XOA CHI TIET HOA DON (CASCADE DELETE)
        dsChiTiet.xoa(maHD);

        // Xoa hoa don
        dsHoaDon.xoa(maHD);

        // Luu file
        try {
            dsHoaDon.saveToFile("DU_LICH/DSHoaDon.txt");
            dsKHTour.saveToFile("DU_LICH/KeHoachTour.txt");
            dsChiTiet.saveToFile("DU_LICH/DSChiTietHD.txt");
            System.out.println("Xoa hoa don va chi tiet thanh cong!");
        } catch (IOException e) {
            System.out.println("Loi luu file: " + e.getMessage());
        }
    }

    private void timHoaDonTheoMa() {
        System.out.print("Nhap ma hoa don: ");
        String maHD = sc.nextLine();
        HoaDon hd = dsHoaDon.timHoaDonTheoMa(maHD);
        if (hd != null) {
            System.out.println("\nTim thay:");
            hd.xuatThongTin();
        } else
            System.out.println("Khong tim thay!");
    }

    private void timHoaDonTheoKhachHang() {
        System.out.print("Nhap ma khach hang: ");
        int maKH = Integer.parseInt(sc.nextLine());
        HoaDon[] ketQua = dsHoaDon.timHoaDonTheoKhachHang(maKH);
        if (ketQua.length > 0) {
            System.out.println("Tim thay " + ketQua.length + " hoa don:");
            for (int i = 0; i < ketQua.length; i++) {
                ketQua[i].xuatThongTin();
            }
        } else
            System.out.println("Khong tim thay!");
    }

    private void xemChiTietTheoMaHD() {
        System.out.print("Nhap ma hoa don: ");
        String maHD = sc.nextLine();
        ChiTietHD ct = dsChiTiet.tim(maHD);
        if (ct != null) {
            System.out.println("\nChi tiet hoa don:");
            ct.xuatThongTin();
        } else {
            System.out.println("Khong tim thay chi tiet!");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        DSHDV dshdv = new DSHDV();
        DSKhachHang dskh = new DSKhachHang();
        DSKHTour dsKHTour = new DSKHTour();
        DSChiTietHD dsChiTiet = new DSChiTietHD();
        DSHoaDon dsHoaDon = new DSHoaDon();
        
        // Khoi tao DSChiPhiKHTour (can thiet de tinh tong tien hoa don)
        DU_LICH.NH_KS_PT.DSKhachSan dsKhachSan = new DU_LICH.NH_KS_PT.DSKhachSan();
        DU_LICH.NH_KS_PT.DSNhaHang dsNhaHang = new DU_LICH.NH_KS_PT.DSNhaHang();
        DSChiPhiKHTour dsChiPhi = new DSChiPhiKHTour(dsKHTour, dsKhachSan, dsNhaHang);

        try {
            dskh.loadFromFile("DU_LICH/Nguoi/KhachHang.txt");
            dshdv.loadFromFile("DU_LICH/Nguoi/HDV.txt");
            
            dsKHTour.loadFromFile("DU_LICH/KeHoachTour.txt");
            // FIX: Truyen dsKHTour de load day du thong tin KeHoachTour voi giaVe
            dsHoaDon.loadFromFile("DU_LICH/DSHoaDon.txt", dsKHTour);
            dsChiTiet.loadFromFile("DU_LICH/DSChiTietHD.txt");
            
            // Load danh sach chi phi
            dsKhachSan.loadFromFile("DU_LICH/NH_KS_PT/KhachSan.txt");
            dsNhaHang.loadFromFile("DU_LICH/NH_KS_PT/NhaHang.txt");
            dsChiPhi.loadFromFile("DU_LICH/ChiPhiKHTour.txt");
        } catch (IOException e) {
            System.out.println("Loi load file: " + e.getMessage());
        }

        QuanLyHoaDon ql = new QuanLyHoaDon(dsHoaDon, dshdv, dskh, dsKHTour, dsChiTiet, dsChiPhi, sc);
        ql.menu();

        try {
            dsHoaDon.saveToFile("DU_LICH/DSHoaDon.txt");
            dsKHTour.saveToFile("DU_LICH/KeHoachTour.txt");
            dsChiTiet.saveToFile("DU_LICH/DSChiTietHD.txt");
        } catch (IOException e) {
            System.out.println("Loi luu file: " + e.getMessage());
        }

        sc.close();
    }
}