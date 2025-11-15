package DU_LICH;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import DU_LICH.Nguoi.*;

public class DSHoaDon {
    // THUOC TINH
    private HoaDon[] list;
    private int soLuongHoaDon;
    private static final String FILE_PATH = "DU_LICH/DSHoaDon.txt";
    private Scanner sc = new Scanner(System.in);
    private DSHDV dshdv;
    private DSKhachHang dskhachHang;
    private DSKHTour dsKHTour;
    private DSChiTietHD dsChiTiet = new DSChiTietHD(); // Tu dong quan ly chi tiet

    // CONSTRUCTOR
    public DSHoaDon() {
        this.list = new HoaDon[0];
        this.soLuongHoaDon = 0;
    }

    public DSHoaDon(DSHDV dshdv, DSKhachHang dskhachHang, DSKHTour dsKHTour) {
        this();
        this.dshdv = dshdv;
        this.dskhachHang = dskhachHang;
        this.dsKHTour = dsKHTour;
    }

    // GETTER/SETTER
    public HoaDon[] getList() { return list; }
    public int getSoLuongHoaDon() { return soLuongHoaDon; }
    public void setDsKHTour(DSKHTour dsKHTour) { this.dsKHTour = dsKHTour; }

    
    // ===== DOC FILE =====
    // Doc danh sach hoa don tu file txt
    public void loadFromFile(String filePath) throws IOException {
        File f = new File(filePath);
        if (!f.exists()) {
            this.list = new HoaDon[0];
            this.soLuongHoaDon = 0;
            return;
        }

        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        int count = 0;
        while ((line = br.readLine()) != null) count++;
        br.close();

        if (count == 0) {
            this.list = new HoaDon[0];
            this.soLuongHoaDon = 0;
            return;
        }

        list = new HoaDon[count];
        br = new BufferedReader(new FileReader(filePath));
        int index = 0;

        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 6) {
                String maHD = parts[0];
                String maKHTour = parts[1];
                KeHoachTour kht = dsKHTour.timTheoMaObject(maKHTour);
                if (kht == null) {
                    kht = new KeHoachTour();
                    kht.setMaKHTour(maKHTour);
                    kht.setGiaVe(0.0);
                }

                HDV hdv = new HDV();
                hdv.setMaHDV(Integer.parseInt(parts[2]));

                KhachHang kh = new KhachHang();
                kh.setMaKH(Integer.parseInt(parts[3]));

                int soKhach = Integer.parseInt(parts[4]);
                int soVe = Integer.parseInt(parts[5]);

                list[index++] = new HoaDon(maHD, kht, hdv, kh, soKhach, soVe);
            }
        }
        br.close();
        soLuongHoaDon = index;
    }

    // ===== GHI FILE =====
    // Luu danh sach hoa don vao file txt
    public void saveToFile(String filePath) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
        for (int i = 0; i < soLuongHoaDon; i++) {
            HoaDon hd = list[i];
            if (hd == null) continue;
            String line = String.format("%s,%s,%d,%d,%d,%d",
                    hd.getMaHD(),
                    hd.getMaKHTour().getMaKHTour(),
                    hd.getMaHDV().getMaHDV(),
                    hd.getMaKHDaiDien().getMaKH(),
                    hd.getSoKhach(),
                    hd.getSoVe());
            bw.write(line);
            bw.newLine();
        }
        bw.close();
    }
                             // ===== KIEM TRA HOP LE =====
    // Kiem tra ma hoa don da ton tai chua
    public boolean kiemTraMaHoaDonTonTai(String maHD) {
        for (HoaDon hd : list) {
            if (hd != null && hd.getMaHD().equals(maHD)) return true;
        }
        return false;
    }

    // Kiem tra ma HDV co ton tai khong
    public boolean validateMaHDV(int maHDV) {
        for (HDV hdv : dshdv.getList()) {
            if (hdv.getMaHDV() == maHDV) return true;
        }
        return false;
    }

    // Kiem tra ma khach hang co ton tai khong
    public boolean validateMaKH(int maKH) {
        for (KhachHang kh : dskhachHang.getList()) {
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

                        // ===== HIEN THI =====
    // Hien thi tat ca hoa don
    public void hienThiDanhSachHoaDon() {
        if (soLuongHoaDon == 0) {
            System.out.println("Danh sach hoa don trong!");
            return;
        }
        System.out.println("\n" + "=".repeat(80));
        System.out.println("                        DANH SACH HOA DON");
        System.out.println("=".repeat(80));
        int count = 0;
        for (int i = 0; i < soLuongHoaDon; i++) {
            if (list[i] != null) {
                count++;
                System.out.println("\n[Hoa don " + count + "]");
                list[i].hienThiThongTin();
            }
        }
        if (count == 0) {
            System.out.println("Khong co hoa don nao hop le!");
        }
    }

    // ===== THEM HOA DON THEO FORM CO GIAO =====
    // Them hoa don voi chi tiet, nhap tung khach hang, tinh tien ngay
    public void themHoaDon(HoaDon hoaDon) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("              TAO HOA DON TOUR DU LICH");
        System.out.println("=".repeat(60));

        // BUOC 1: NHAP MA HOA DON
        System.out.print("[1] Nhap ma hoa don: ");
        String maHD = sc.nextLine();
        hoaDon.setMaHD(maHD);

        // BUOC 2: CHON HDV (hien thi danh sach)
        System.out.println("\n[2] Chon huong dan vien:");
        System.out.println("-".repeat(60));
        for (HDV hdv : dshdv.getList()) {
            if (hdv != null) {
                System.out.printf("  [%d] %s\n", hdv.getMaHDV(), hdv.getTenHDV());
            }
        }
        System.out.println("-".repeat(60));
        
        int maHDV = 0;
        while (true) {
            System.out.print("Nhap ma HDV phu trach: ");
            maHDV = Integer.parseInt(sc.nextLine());
            if (validateMaHDV(maHDV)) {
                hoaDon.getMaHDV().setMaHDV(maHDV);
                break;
            }
            System.out.println("Ma HDV khong ton tai! Vui long chon lai.");
        }

        // BUOC 3: CHON KE HOACH TOUR (hien thi danh sach)
        System.out.println("\n[3] Chon ke hoach tour:");
        System.out.println("-".repeat(60));
        for (KeHoachTour kht : dsKHTour.getList()) {
            if (kht != null) {
                System.out.printf("  [%s] Tour: %s - Gia: %,.0f VND - Con: %d ve\n",
                    kht.getMaKHTour(), kht.getMaTour(), kht.getGiaVe(), kht.getTongSoVe());
            }
        }
        System.out.println("-".repeat(60));
        
        KeHoachTour kht = null;
        while (true) {
            System.out.print("Nhap ma KH Tour: ");
            String maKHTour = sc.nextLine();
            if (validateMaKHTour(maKHTour)) {
                kht = dsKHTour.timTheoMaObject(maKHTour);
                if (kht != null) {
                    hoaDon.setMaKHTour(kht);
                    break;
                }
            }
            System.out.println("Ma tour khong ton tai! Vui long chon lai.");
        }

        // BUOC 4: CHON KHACH HANG DAI DIEN (hien thi danh sach)
        System.out.println("\n[4] Chon khach hang dai dien:");
        System.out.println("-".repeat(60));
        for (KhachHang kh : dskhachHang.getList()) {
            if (kh != null) {
                System.out.printf("  [%d] %s\n", kh.getMaKH(), kh.getTenKH());
            }
        }
        System.out.println("-".repeat(60));
        
        int maKHDaiDien = 0;
        while (true) {
            System.out.print("Nhap ma khach hang dai dien: ");
            maKHDaiDien = Integer.parseInt(sc.nextLine());
            if (validateMaKH(maKHDaiDien)) {
                hoaDon.getMaKHDaiDien().setMaKH(maKHDaiDien);
                break;
            }
            System.out.println("Ma KH khong ton tai! Vui long chon lai.");
        }

        // BUOC 5: NHAP CHI TIET - TUNG KHACH HANG (VONG FOR)
        System.out.println("\n[5] Nhap chi tiet khach hang di tour:");
        System.out.print("So luong khach hang: ");
        int soKhach = Integer.parseInt(sc.nextLine());

        // Kiem tra so ve con lai
        if (soKhach > kht.getTongSoVe()) {
            System.out.println("Khong du ve! Chi con " + kht.getTongSoVe() + " ve.");
            return;
        }

        int[] dsMaKH = new int[soKhach];
        double tongTien = 0.0;
        double giaVe = kht.getGiaVe();

        System.out.println("\n" + "=".repeat(60));
        System.out.println("                NHAP TUNG KHACH HANG");
        System.out.println("=".repeat(60));

        for (int i = 0; i < soKhach; i++) {
            System.out.println("\n--- Khach hang thu " + (i + 1) + " ---");
            
            // Hien thi danh sach khach hang
            System.out.println("Danh sach khach hang:");
            for (KhachHang kh : dskhachHang.getList()) {
                if (kh != null) {
                    System.out.printf("  [%d] %s\n", kh.getMaKH(), kh.getTenKH());
                }
            }
            
            int maKH = 0;
            while (true) {
                System.out.print("Nhap ma khach hang: ");
                maKH = Integer.parseInt(sc.nextLine());
                
                // Kiem tra ton tai
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
            
            System.out.println("  + Don gia: " + String.format("%,.0f VND", giaVe));
            System.out.println("  + Thanh tien: " + String.format("%,.0f VND", thanhTien));
            System.out.println("  + Tong tich luy: " + String.format("%,.0f VND", tongTien));
        }

        // BUOC 6: HIEN THI TONG KET
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                   TONG KET HOA DON");
        System.out.println("=".repeat(60));
        System.out.println("Ma hoa don         : " + maHD);
        System.out.println("Ma KH Tour         : " + kht.getMaKHTour());
        System.out.println("Ma HDV             : " + maHDV);
        System.out.println("Ma KH dai dien     : " + maKHDaiDien);
        System.out.println("So khach           : " + soKhach);
        System.out.println("So ve              : " + soKhach);
        System.out.println("Gia moi ve         : " + String.format("%,.0f VND", giaVe));
        System.out.println("TONG TIEN HOA DON  : " + String.format("%,.0f VND", tongTien));
        System.out.println("=".repeat(60));

        // Cap nhat hoa don
        hoaDon.setSoKhach(soKhach);
        hoaDon.setSoVe(soKhach);

        // Them vao danh sach
        list = Arrays.copyOf(list, list.length + 1);
        list[list.length - 1] = hoaDon;
        soLuongHoaDon++;

        // Cap nhat ke hoach tour (giam ve con lai, tang ve da ban)
        kht.setTongSoVe(kht.getTongSoVe() - soKhach);
        kht.setTongVeDaDat(kht.getTongVeDaDat() + soKhach);

        // TU DONG TAO CHI TIET HOA DON (CASCADE INSERT)
        dsChiTiet.taoChiTiet(hoaDon, dsMaKH);

        // Luu file
        try {
            System.out.println("\n[DEBUG] Dang luu DSHoaDon.txt...");
            System.out.println("[DEBUG] So luong hoa don: " + soLuongHoaDon);
            System.out.println("[DEBUG] File path: " + FILE_PATH);
            
            saveToFile(FILE_PATH);
            System.out.println("[DEBUG] Da luu DSHoaDon.txt thanh cong!");
            
            dsKHTour.saveToFile("DU_LICH/KeHoachTour.txt");
            System.out.println("[DEBUG] Da luu KeHoachTour.txt thanh cong!");
            
            System.out.println("\n>>> TAO HOA DON THANH CONG! <<<");
        } catch (IOException e) {
            System.out.println("Loi luu file: " + e.getMessage());
            e.printStackTrace(); // In stack trace de debug
        }
    }

    // ===== SUA HOA DON =====
    // Sua hoa don va TU DONG cap nhat chi tiet (CASCADE UPDATE)
    public void suaHoaDon(String maHD) {
        for (int i = 0; i < soLuongHoaDon; i++) {
            if (list[i] != null && list[i].getMaHD().equals(maHD)) {
                HoaDon hdCu = list[i];
                System.out.println("Thong tin hoa don hien tai:");
                hdCu.hienThiThongTin();
                
                // Nhap thong tin moi
                System.out.println("\nNhap thong tin hoa don moi:");
                HoaDon hdMoi = new HoaDon();
                hdMoi.nhap(sc);

                // Validate
                while (!validateMaHDV(hdMoi.getMaHDV().getMaHDV())) {
                    System.out.println("Ma HDV khong ton tai. Nhap lai:");
                    hdMoi.getMaHDV().setMaHDV(Integer.parseInt(sc.nextLine()));
                }
                while (!validateMaKH(hdMoi.getMaKHDaiDien().getMaKH())) {
                    System.out.println("Ma KH khong ton tai. Nhap lai:");
                    hdMoi.getMaKHDaiDien().setMaKH(Integer.parseInt(sc.nextLine()));
                }

                // Tim ke hoach tour moi
                KeHoachTour khtMoi = null;
                while (true) {
                    String maKHTour = hdMoi.getMaKHTour().getMaKHTour();
                    if (validateMaKHTour(maKHTour)) {
                        khtMoi = dsKHTour.timTheoMaObject(maKHTour);
                        if (khtMoi != null) {
                            hdMoi.setMaKHTour(khtMoi);
                            break;
                        }
                    }
                    System.out.println("Ma ke hoach tour khong ton tai. Nhap lai:");
                    hdMoi.getMaKHTour().setMaKHTour(sc.nextLine());
                }

                while (!kiemTraSoKhachHopLe(hdMoi.getSoKhach())) {
                    System.out.println("So khach khong hop le. Nhap lai:");
                    hdMoi.setSoKhach(Integer.parseInt(sc.nextLine()));
                }

                // BUOC 1: Hoan tra ve cu (tang ve con lai, giam ve da ban)
                KeHoachTour khtCu = dsKHTour.timTheoMaObject(hdCu.getMaKHTour().getMaKHTour());
                if (khtCu != null) {
                    khtCu.setTongSoVe(khtCu.getTongSoVe() + hdCu.getSoVe());
                    khtCu.setTongVeDaDat(khtCu.getTongVeDaDat() - hdCu.getSoVe());
                }

                // Validate so ve moi
                while (!checkSoVeHopLe(khtMoi, hdMoi.getSoVe())) {
                    System.out.println("So ve khong hop le. Nhap lai:");
                    hdMoi.setSoVe(Integer.parseInt(sc.nextLine()));
                }

                // BUOC 2: Ban ve moi (giam ve con lai, tang ve da ban)
                khtMoi.setTongSoVe(khtMoi.getTongSoVe() - hdMoi.getSoVe());
                khtMoi.setTongVeDaDat(khtMoi.getTongVeDaDat() + hdMoi.getSoVe());

                // Nhap danh sach khach hang moi
                System.out.println("\nNhap danh sach ma khach hang di tour (cach nhau dau phay):");
                System.out.print("Vi du: 1,2,3 hoac chi 1 nguoi: 5\nNhap: ");
                String[] inputKH = sc.nextLine().split(",");
                int[] dsMaKH = new int[inputKH.length];
                for (int j = 0; j < inputKH.length; j++) {
                    dsMaKH[j] = Integer.parseInt(inputKH[j].trim());
                }

                // TU DONG XOA CHI TIET CU va TAO MOI (CASCADE UPDATE)
                dsChiTiet.xoaChiTiet(maHD);
                dsChiTiet.taoChiTiet(hdMoi, dsMaKH);

                // Cap nhat hoa don
                list[i] = hdMoi;

                // Luu file
                try {
                    saveToFile(FILE_PATH);
                    dsKHTour.saveToFile("DU_LICH/KeHoachTour.txt");
                    System.out.println("Sua hoa don thanh cong!");
                } catch (IOException e) {
                    System.out.println("Loi luu file: " + e.getMessage());
                }
                return;
            }
        }
        System.out.println("Khong tim thay ma hoa don.");
    }

    // ===== TIM KIEM =====
    // Tim 1 hoa don theo ma (tra ve HoaDon hoac null)
    public HoaDon timHoaDonTheoMa(String maHD) {
        for (int i = 0; i < soLuongHoaDon; i++) {
            if (list[i] != null && list[i].getMaHD().equals(maHD)) {
                return list[i];
            }
        }
        return null;
    }
    
    // Tim nhieu hoa don theo ma khach hang (tra ve mang HoaDon[])
    public HoaDon[] timHoaDonTheoKhachHang(int maKH) {
        // Dem
        int count = 0;
        for (int i = 0; i < soLuongHoaDon; i++) {
            if (list[i] != null && list[i].getMaKHDaiDien().getMaKH() == maKH) count++;
        }
        
        // Tao mang va them
        HoaDon[] ketQua = new HoaDon[count];
        int index = 0;
        for (int i = 0; i < soLuongHoaDon; i++) {
            if (list[i] != null && list[i].getMaKHDaiDien().getMaKH() == maKH) {
                ketQua[index++] = list[i];
            }
        }
        return ketQua;
    }
    
    // ===== THONG KE =====
    // Tinh tong so hoa don, ve da ban va doanh thu
    public void thongKeHoaDon() {
        if (soLuongHoaDon == 0) {
            System.out.println("Chua co hoa don nao!");
            return;
        }
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("                        THONG KE HOA DON");
        System.out.println("=".repeat(80));
        
        // Duyet va tinh tong
        int tongSoVe = 0;
        int tongSoKhach = 0;
        double tongDoanhThu = 0;
        for (int i = 0; i < soLuongHoaDon; i++) {
            if (list[i] != null) {
                tongSoVe += list[i].getSoVe();
                tongSoKhach += list[i].getSoKhach();
                tongDoanhThu += list[i].tongTienVe();
            }
        }
        
        // In ket qua
        System.out.println("Tong so hoa don:       " + soLuongHoaDon);
        System.out.println("Tong so ve da ban:     " + tongSoVe);
        System.out.println("Tong so khach:         " + tongSoKhach);
        System.out.println("Tong doanh thu:        " + String.format("%,.0f VND", tongDoanhThu));
        System.out.println("Doanh thu trung binh:  " + String.format("%,.0f VND", tongDoanhThu / soLuongHoaDon));
        System.out.println("=".repeat(80) + "\n");
    }

    // ===== XOA HOA DON =====
    // Xoa hoa don va hoan tra ve cho ke hoach tour
    public void xoaHoaDon(String maHD) {
        for (int i = 0; i < soLuongHoaDon; i++) {
            if (list[i] != null && list[i].getMaHD().equals(maHD)) {
                HoaDon hd = list[i];
                System.out.println("Dang xoa hoa don: " + maHD);
                hd.hienThiThongTin();

                // Hoan tra ve (tang ve con lai, giam ve da ban)
                KeHoachTour kht = dsKHTour.timTheoMaObject(hd.getMaKHTour().getMaKHTour());
                if (kht != null) {
                    kht.setTongSoVe(kht.getTongSoVe() + hd.getSoVe());
                    kht.setTongVeDaDat(kht.getTongVeDaDat() - hd.getSoVe());
                }

                // TU DONG XOA CHI TIET HOA DON (CASCADE DELETE)
                dsChiTiet.xoaChiTiet(maHD);

                // Xoa khoi mang (dich sang trai)
                for (int j = i; j < soLuongHoaDon - 1; j++) list[j] = list[j + 1];
                list = Arrays.copyOf(list, list.length - 1);
                soLuongHoaDon--;

                // Luu file
                try {
                    saveToFile(FILE_PATH);
                    dsKHTour.saveToFile("DU_LICH/KeHoachTour.txt");
                    System.out.println("Xoa hoa don thanh cong!");
                } catch (IOException e) {
                    System.out.println("Loi luu file: " + e.getMessage());
                }
                return;
            }
        }
        System.out.println("Khong tim thay ma hoa don.");
    }

    // ===== MAIN =====
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Khoi tao cac danh sach lien quan
        DSHDV dshdv = new DSHDV();
        DSKhachHang dskh = new DSKhachHang();
        DSKHTour dsKHTour = new DSKHTour();

        try {
            dshdv.loadFromFile("DU_LICH/Nguoi/HDV.txt");
            dskh.loadFromFile("DU_LICH/Nguoi/KhachHang.txt");
            dsKHTour.loadFromFile("DU_LICH/KeHoachTour.txt");
        } catch (IOException e) {
            System.out.println("Loi load file: " + e.getMessage());
        }

        // Khoi tao danh sach hoa don
        DSHoaDon dsHoaDon = new DSHoaDon(dshdv, dskh, dsKHTour);
        dsHoaDon.setDsKHTour(dsKHTour);
        try {
            dsHoaDon.loadFromFile(FILE_PATH);
            dsHoaDon.dsChiTiet.loadFromFile("DU_LICH/DSChiTietHD.txt");
        } catch (IOException e) {
            System.out.println("Loi load hoa don: " + e.getMessage());
        }

        // Menu chinh
        boolean exit = false;
        while (!exit) {
            System.out.println("\n=== MENU QUAN LY HOA DON ===");
            System.out.println("1. Hien thi danh sach hoa don");
            System.out.println("2. Them hoa don (tu dong tao chi tiet)");
            System.out.println("3. Sua hoa don (tu dong cap nhat chi tiet)");
            System.out.println("4. Xoa hoa don (tu dong xoa chi tiet)");
            System.out.println("5. Tim theo ma");
            System.out.println("6. Tim theo khach hang dai dien mua tour");
            System.out.println("7. Thong ke");
            System.out.println("8. Xem chi tiet hoa don");
            System.out.println("9. Xem tat ca chi tiet");
            System.out.println("0. Thoat");
            System.out.print("Chon: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1: dsHoaDon.hienThiDanhSachHoaDon(); break;
                case 2:
                    // Tao hoa don moi (khong can nhap truoc)
                    HoaDon hdMoi = new HoaDon();
                    dsHoaDon.themHoaDon(hdMoi);
                    break;
                case 3:
                    System.out.print("Nhap ma hoa don can sua: ");
                    dsHoaDon.suaHoaDon(sc.nextLine());
                    break;
                case 4:
                    System.out.print("Nhap ma hoa don can xoa: ");
                    dsHoaDon.xoaHoaDon(sc.nextLine());
                    break;
                case 5:
                    System.out.print("Nhap ma hoa don: ");
                    HoaDon hdTim = dsHoaDon.timHoaDonTheoMa(sc.nextLine());
                    if (hdTim != null) {
                        System.out.println("\nTim thay:");
                        hdTim.hienThiThongTin();
                    } else System.out.println("Khong tim thay!");
                    break;
                case 6:
                    System.out.print("Nhap ma khach hang: ");
                    HoaDon[] ketQua = dsHoaDon.timHoaDonTheoKhachHang(Integer.parseInt(sc.nextLine()));
                    if (ketQua.length > 0) {
                        System.out.println("\nTim thay " + ketQua.length + " hoa don:");
                        for (int i = 0; i < ketQua.length; i++) {
                            System.out.println("\n[Hoa don " + (i + 1) + "]");
                            ketQua[i].hienThiThongTin();
                        }
                    } else System.out.println("Khong tim thay!");
                    break;
                case 7: dsHoaDon.thongKeHoaDon(); break;
                case 8:
                    System.out.print("Nhap ma hoa don: ");
                    ChiTietHD ct = dsHoaDon.dsChiTiet.xemChiTiet(sc.nextLine());
                    if (ct != null) {
                        System.out.println("\nChi tiet hoa don:");
                        ct.hienThiThongTin();
                    } else System.out.println("Khong tim thay chi tiet!");
                    break;
                case 9: dsHoaDon.dsChiTiet.hienThiDanhSach(); break;
                case 0: exit = true; break;
                default: System.out.println("Lua chon khong hop le.");
            }
        }

        // Luu file truoc khi thoat
        try {
            dsHoaDon.saveToFile(FILE_PATH);
            dsKHTour.saveToFile("DU_LICH/KeHoachTour.txt");
        } catch (IOException e) {
            System.out.println("Loi luu file: " + e.getMessage());
        }

        System.out.println("Thoat chuong trinh!");
        sc.close();
    }
}
