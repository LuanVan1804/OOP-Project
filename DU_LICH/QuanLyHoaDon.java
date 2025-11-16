package DU_LICH;

import DU_LICH.Nguoi.*;
import java.io.IOException;
import java.util.Scanner;

public class QuanLyHoaDon {
    private final DSHoaDon dsHoaDon;
    private final DSHDV dsHDV;
    private final DSKhachHang dsKhachHang;
    private final DSKHTour dsKHTour;
    private final DSChiTietHD dsChiTiet;
    private final Scanner sc;

    public QuanLyHoaDon(DSHoaDon dsHoaDon,
                        DSHDV dsHDV,
                        DSKhachHang dsKhachHang,
                        DSKHTour dsKHTour,
                        DSChiTietHD dsChiTiet,
                        Scanner sc) {
        this.dsHoaDon = dsHoaDon;
        this.dsHDV = dsHDV;
        this.dsKhachHang = dsKhachHang;
        this.dsKHTour = dsKHTour;
        this.dsChiTiet = dsChiTiet;
        this.sc = sc;
    }

    // ===== KIEM TRA HOP LE =====
    // Kiem tra ma HDV co ton tai khong
    public boolean validateMaHDV(int maHDV) {
        for (HDV hdv : dsHDV.getList()) {
            if (hdv.getMaHDV() == maHDV) return true;
        }
        return false;
    }

    // Kiem tra ma khach hang co ton tai khong
    public boolean validateMaKH(int maKH) {
        for (KhachHang kh : dsKhachHang.getList()) {
            if (kh.getMaKH() == maKH) return true;
        }
        return false;
    }

    // Kiem tra ma ke hoach tour co ton tai khong
    public boolean validateMaKHTour(String maKHTour) {
        if (maKHTour == null) return false;
        for (KeHoachTour kht : dsKHTour.getList()) {
            if (kht != null && kht.getMaKHTour() != null && kht.getMaKHTour().equals(maKHTour))
                return true;
        }
        return false;
    }

    // Kiem tra so khach hop le (> 0)
    public boolean kiemTraSoKhachHopLe(int soKhach) {
        if (soKhach <= 0) {
            System.out.println("So khach phai lon hon 0.");
            return false;
        }
        return true;
    }

    // Kiem tra so ve con lai du khong (tongSoVe = ve con lai)
    public boolean checkSoVeHopLe(KeHoachTour kht, int soVeDat) {
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
            System.out.println("8. Xem chi tiet hoa don");
            System.out.println("9. Xem tat ca chi tiet");
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
                    xemChiTietTheoMaHD();
                    break;
                case 9:
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

    private void themHoaDon() {
        System.out.println("\n============================================================");
        System.out.println("              TAO HOA DON TOUR DU LICH");
        System.out.println("============================================================");

        HoaDon hoaDon = new HoaDon();

        // 1. Nhap ma hoa don (kiem tra trung)
        String maHD;
        while (true) {
            System.out.print("[1] Nhap ma hoa don: ");
            maHD = sc.nextLine();
            if (maHD == null || maHD.trim().isEmpty()) {
                System.out.println("Ma hoa don khong duoc rong.");
                continue;
            }
            if (dsHoaDon.kiemTraMaHoaDonTonTai(maHD)) {
                System.out.println("Ma hoa don da ton tai, vui long nhap ma khac.");
            } else {
                break;
            }
        }
        hoaDon.setMaHD(maHD);

        // 2. Chon HDV
        System.out.println("\n[2] Chon huong dan vien:");
        System.out.println("------------------------------------------------------------");
        for (HDV hdv : dsHDV.getList()) {
            if (hdv != null) {
                System.out.printf("  [%d] %s\n", hdv.getMaHDV(), hdv.getTenHDV());
            }
        }
        System.out.println("------------------------------------------------------------");
        HDV chonHDV = null;
        while (chonHDV == null) {
            System.out.print("Nhap ma HDV phu trach: ");
            int maHDV;
            try {
                maHDV = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ma HDV khong hop le.");
                continue;
            }
            for (HDV hdv : dsHDV.getList()) {
                if (hdv != null && hdv.getMaHDV() == maHDV) {
                    chonHDV = hdv;
                    break;
                }
            }
            if (chonHDV == null) {
                System.out.println("Ma HDV khong ton tai, nhap lai.");
            }
        }
        hoaDon.setMaHDV(chonHDV);

        // 3. Chon ke hoach tour
        System.out.println("\n[3] Chon ke hoach tour:");
        System.out.println("------------------------------------------------------------");
        for (KeHoachTour kht : dsKHTour.getList()) {
            if (kht != null) {
                System.out.printf("  [%s] Tour: %s - Gia: %,.0f VND - Con: %d ve\n",
                        kht.getMaKHTour(), kht.getMaTour(), kht.getGiaVe(), kht.getTongSoVe());
            }
        }
        System.out.println("------------------------------------------------------------");
        KeHoachTour khtChon = null;
        while (khtChon == null) {
            System.out.print("Nhap ma KH Tour: ");
            String maKHTour = sc.nextLine();
            for (KeHoachTour kht : dsKHTour.getList()) {
                if (kht != null && maKHTour.equals(kht.getMaKHTour())) {
                    khtChon = kht;
                    break;
                }
            }
            if (khtChon == null) {
                System.out.println("Ma ke hoach tour khong ton tai, nhap lai.");
            }
        }
        hoaDon.setMaKHTour(khtChon);

        // 4. Chon khach hang dai dien
        System.out.println("\n[4] Chon khach hang dai dien:");
        System.out.println("------------------------------------------------------------");
        for (KhachHang kh : dsKhachHang.getList()) {
            if (kh != null) {
                System.out.printf("  [%d] %s\n", kh.getMaKH(), kh.getTenKH());
            }
        }
        System.out.println("------------------------------------------------------------");
        KhachHang khDaiDien = null;
        while (khDaiDien == null) {
            System.out.print("Nhap ma khach hang dai dien: ");
            int maKH;
            try {
                maKH = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ma KH khong hop le.");
                continue;
            }
            for (KhachHang kh : dsKhachHang.getList()) {
                if (kh != null && kh.getMaKH() == maKH) {
                    khDaiDien = kh;
                    break;
                }
            }
            if (khDaiDien == null) {
                System.out.println("Ma KH khong ton tai, nhap lai.");
            }
        }
        hoaDon.setMaKHDaiDien(khDaiDien);

        // 5. Nhap so khach, danh sach khach va cap nhat ve
        System.out.println("\n[5] Nhap chi tiet khach hang di tour:");
        int soKhach;
        while (true) {
            System.out.print("So luong khach hang: ");
            try {
                soKhach = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("So khach khong hop le.");
                continue;
            }
            if (!kiemTraSoKhachHopLe(soKhach)) continue;
            if (!checkSoVeHopLe(khtChon, soKhach)) continue;
            break;
        }

        int[] dsMaKH = new int[soKhach];
        double tongTien = 0.0;
        double giaVe = khtChon.getGiaVe();

        System.out.println("\n============================================================");
        System.out.println("                NHAP TUNG KHACH HANG");
        System.out.println("============================================================");

        for (int i = 0; i < soKhach; i++) {
            System.out.println("\n--- Khach hang thu " + (i + 1) + " ---");
            System.out.println("Danh sach khach hang:");
            for (KhachHang kh : dsKhachHang.getList()) {
                if (kh != null) {
                    System.out.printf("  [%d] %s\n", kh.getMaKH(), kh.getTenKH());
                }
            }
            
            int maKH;
            while (true) {
                System.out.print("Nhap ma khach hang: ");
                try {
                    maKH = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Ma KH khong hop le.");
                    continue;
                }

                if (!validateMaKH(maKH)) {
                    System.out.println("Ma KH khong ton tai! Nhap lai.");
                    continue;
                }

                // Kiem tra trung lap
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
            int soLuong = 1;  // Moi khach 1 ve
            double thanhTien = soLuong * giaVe;
            tongTien += thanhTien;
        }

        // Tong ket
        System.out.println("\n============================================================");
        System.out.println("                   TONG KET HOA DON");
        System.out.println("============================================================");
        System.out.println("Ma hoa don         : " + maHD);
        System.out.println("Ma KH Tour         : " + khtChon.getMaKHTour());
        System.out.println("Ma HDV             : " + chonHDV.getMaHDV());
        System.out.println("Ma KH dai dien     : " + khDaiDien.getMaKH());
        System.out.println("So khach           : " + soKhach);
        System.out.println("So ve              : " + soKhach);
        System.out.println("Gia moi ve         : " + String.format("%,.0f VND", giaVe));
        System.out.println("TONG TIEN HOA DON  : " + String.format("%,.0f VND", tongTien));
        System.out.println("============================================================");

        hoaDon.setSoKhach(soKhach);
        hoaDon.setSoVe(soKhach);

        // Cap nhat ve trong ke hoach tour
        khtChon.setTongSoVe(khtChon.getTongSoVe() - soKhach);
        khtChon.setTongVeDaDat(khtChon.getTongVeDaDat() + soKhach);

        // Tao chi tiet hoa don
        ChiTietHD chiTiet = new ChiTietHD(maHD, khtChon.getMaKHTour(), dsMaKH, giaVe);
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
        hdCu.hienThiThongTin();

        // Hoan tra ve cu
        KeHoachTour khtCu = dsKHTour.timTheoMaObject(hdCu.getMaKHTour().getMaKHTour());
        if (khtCu != null) {
            khtCu.setTongSoVe(khtCu.getTongSoVe() + hdCu.getSoVe());
            khtCu.setTongVeDaDat(khtCu.getTongVeDaDat() - hdCu.getSoVe());
        }

        // Nhap thong tin moi
        HoaDon hdMoi = new HoaDon();
        hdMoi.setMaHD(maHD);  // Giữ maHD cũ

        // Chon HDV moi
        System.out.println("\nChon huong dan vien moi:");
        System.out.println("------------------------------------------------------------");
        for (HDV hdv : dsHDV.getList()) {
            if (hdv != null) {
                System.out.printf("  [%d] %s\n", hdv.getMaHDV(), hdv.getTenHDV());
            }
        }
        System.out.println("------------------------------------------------------------");
        HDV chonHDV = null;
        while (chonHDV == null) {
            System.out.print("Nhap ma HDV phu trach moi: ");
            int maHDV = Integer.parseInt(sc.nextLine());
            if (validateMaHDV(maHDV)) {
                chonHDV = new HDV();
                chonHDV.setMaHDV(maHDV);
                hdMoi.setMaHDV(chonHDV);
                break;
            }
            System.out.println("Ma HDV khong ton tai! Vui long chon lai.");
        }

        // Chon KH dai dien moi
        System.out.println("\nChon khach hang dai dien moi:");
        System.out.println("------------------------------------------------------------");
        for (KhachHang kh : dsKhachHang.getList()) {
            if (kh != null) {
                System.out.printf("  [%d] %s\n", kh.getMaKH(), kh.getTenKH());
            }
        }
        System.out.println("------------------------------------------------------------");
        KhachHang khDaiDien = null;
        while (khDaiDien == null) {
            System.out.print("Nhap ma khach hang dai dien moi: ");
            int maKH = Integer.parseInt(sc.nextLine());
            if (validateMaKH(maKH)) {
                khDaiDien = new KhachHang();
                khDaiDien.setMaKH(maKH);
                hdMoi.setMaKHDaiDien(khDaiDien);
                break;
            }
            System.out.println("Ma KH khong ton tai! Vui long chon lai.");
        }

        // Chon ke hoach tour moi
        System.out.println("\nChon ke hoach tour moi:");
        System.out.println("------------------------------------------------------------");
        for (KeHoachTour kht : dsKHTour.getList()) {
            if (kht != null) {
                System.out.printf("  [%s] Tour: %s - Gia: %,.0f VND - Con: %d ve\n",
                        kht.getMaKHTour(), kht.getMaTour(), kht.getGiaVe(), kht.getTongSoVe());
            }
        }
        System.out.println("------------------------------------------------------------");
        KeHoachTour khtMoi = null;
        while (khtMoi == null) {
            System.out.print("Nhap ma KH Tour moi: ");
            String maKHTour = sc.nextLine();
            if (validateMaKHTour(maKHTour)) {
                khtMoi = dsKHTour.timTheoMaObject(maKHTour);
                if (khtMoi != null) {
                    hdMoi.setMaKHTour(khtMoi);
                    break;
                }
            }
            System.out.println("Ma tour khong ton tai! Vui long chon lai.");
        }

        // Nhap so khach moi
        int soKhach;
        while (true) {
            System.out.print("Nhap so khach moi: ");
            soKhach = Integer.parseInt(sc.nextLine());
            if (kiemTraSoKhachHopLe(soKhach) && checkSoVeHopLe(khtMoi, soKhach)) {
                hdMoi.setSoKhach(soKhach);
                hdMoi.setSoVe(soKhach);
                break;
            }
        }

        // Nhap danh sach khach hang moi
        System.out.println("\nNhap danh sach ma khach hang di tour moi (cach nhau dau phay):");
        String[] inputKH = sc.nextLine().split(",");
        int[] dsMaKH = new int[inputKH.length];
        for (int j = 0; j < inputKH.length; j++) {
            int maKH = Integer.parseInt(inputKH[j].trim());
            if (!validateMaKH(maKH)) {
                System.out.println("Ma KH " + maKH + " khong ton tai. Vui long nhap lai.");
                return;
            }
            dsMaKH[j] = maKH;
        }

        // Cap nhat ve moi
        khtMoi.setTongSoVe(khtMoi.getTongSoVe() - soKhach);
        khtMoi.setTongVeDaDat(khtMoi.getTongVeDaDat() + soKhach);

        // TU DONG XOA CHI TIET CU va TAO MOI (CASCADE UPDATE)
        dsChiTiet.xoa(maHD);
        ChiTietHD chiTietMoi = new ChiTietHD(maHD, khtMoi.getMaKHTour(), dsMaKH, khtMoi.getGiaVe());
        dsChiTiet.them(chiTietMoi);

        // Cap nhat hoa don trong DS
        for (int i = 0; i < dsHoaDon.getSoLuongHoaDon(); i++) {
            if (dsHoaDon.getList()[i].getMaHD().equals(maHD)) {
                dsHoaDon.getList()[i] = hdMoi;
                break;
            }
        }

        // Luu file
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
        hd.hienThiThongTin();

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
            hd.hienThiThongTin();
        } else System.out.println("Khong tim thay!");
    }

    private void timHoaDonTheoKhachHang() {
        System.out.print("Nhap ma khach hang: ");
        int maKH = Integer.parseInt(sc.nextLine());
        HoaDon[] ketQua = dsHoaDon.timHoaDonTheoKhachHang(maKH);
        if (ketQua.length > 0) {
            System.out.println("\nTim thay " + ketQua.length + " hoa don:");
            for (int i = 0; i < ketQua.length; i++) {
                System.out.println("\n[Hoa don " + (i + 1) + "]");
                ketQua[i].hienThiThongTin();
            }
        } else System.out.println("Khong tim thay!");
    }

    private void xemChiTietTheoMaHD() {
        System.out.print("Nhap ma hoa don: ");
        String maHD = sc.nextLine();
        ChiTietHD ct = dsChiTiet.tim(maHD);
        if (ct != null) {
            System.out.println("\nChi tiet hoa don:");
            ct.hienThiThongTin();
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

        try {
            dshdv.loadFromFile("DU_LICH/Nguoi/HDV.txt");
            dskh.loadFromFile("DU_LICH/Nguoi/KhachHang.txt");
            dsKHTour.loadFromFile("DU_LICH/KeHoachTour.txt");
            dsHoaDon.loadFromFile("DU_LICH/DSHoaDon.txt");
            dsChiTiet.loadFromFile("DU_LICH/DSChiTietHD.txt");
        } catch (IOException e) {
            System.out.println("Loi load file: " + e.getMessage());
        }

        QuanLyHoaDon ql = new QuanLyHoaDon(dsHoaDon, dshdv, dskh, dsKHTour, dsChiTiet, sc);
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