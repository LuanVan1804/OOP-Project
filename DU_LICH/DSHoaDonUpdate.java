package DU_LICH;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import DU_LICH.Nguoi.*;

public class DSHoaDonUpdate {
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
    public DSHoaDonUpdate() {
        this.list = new HoaDon[0];
        this.soLuongHoaDon = 0;
    }

    public DSHoaDonUpdate(DSHDV dshdv, DSKhachHang dskhachHang, DSKHTour dsKHTour) {
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
                    System.out.println("[CANH BAO] Hoa don " + maHD + ": Khong tim thay KeHoachTour " + maKHTour);
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
        }
        else if (soVeDat > soKhach) {
            System.out.println("So ve dat khong hop le so voi so khach : " + soKhach);
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
                list[i].xuatThongTin();
            }
        }
        if (count == 0) {
            System.out.println("Khong co hoa don nao hop le!");
        }
    }

    // ===== THEM HOA DON THEO FORM CO GIAO =====
    // Them hoa don voi chi tiet, nhap tung khach hang, tinh tien ngay
    public void themHoaDon(HoaDon hoaDon) {
        // BUOC 1: NHAP THONG TIN CHUNG
        hoaDon = new HoaDon();
        System.out.println("\n" + "=".repeat(60));
        System.out.println("              TAO HOA DON TOUR DU LICH");
        System.out.println("=".repeat(60));
        hoaDon.nhap(sc);

        // kiem tra rang buoc ma hoa don (KHÔNG được rỗng VÀ KHÔNG được trùng)
        while (hoaDon.getMaHD() == null || hoaDon.getMaHD().trim().isEmpty() || kiemTraMaHoaDonTonTai(hoaDon.getMaHD())) {
            if (hoaDon.getMaHD() == null || hoaDon.getMaHD().trim().isEmpty()) {
                System.out.println("[LOI] Ma hoa don khong duoc de trong!");
            } else {
                System.out.println("[LOI] Ma hoa don da ton tai!");
            }
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
            if (hoaDon.getMaKHTour().getMaKHTour() == null || hoaDon.getMaKHTour().getMaKHTour().trim().isEmpty()) {
                System.out.println("[LOI] Ma ke hoach tour khong duoc de trong!");
            } else {
                System.out.println("[LOI] Ma ke hoach tour khong ton tai!");
            }
            System.out.print("Nhap lai ma ke hoach tour: ");
            String maKHTour = sc.nextLine().trim();
            hoaDon.getMaKHTour().setMaKHTour(maKHTour);
        }
        //lay ke hoach tour de lam viec tiep -> de làm cac buoc sau
        KeHoachTour kht = dsKHTour.timTheoMaObject(hoaDon.getMaKHTour().getMaKHTour());
        if (kht == null) {
            System.out.println("[LOI NGHIEM TRONG] Khong tim thay ke hoach tour! Huy tao hoa don.");
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
        System.out.println("\n[5] Nhap chi tiet khach hang di tour:");
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
                
                // Kiem tra trung lap --- dam bao khach hang khong bi trung lap trong cung 1 hoa don
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
        hoaDon.xuatThongTin();
        System.out.println("=".repeat(60));
 

        // Cap nhat hoa don
        hoaDon.setSoKhach(hoaDon.getSoKhach());
        hoaDon.setSoVe(hoaDon.getSoVe());

        // Them vao danh sach
        list = Arrays.copyOf(list, list.length + 1);
        list[list.length - 1] = hoaDon;
        soLuongHoaDon++;

        // Cap nhat ke hoach tour (giam ve con lai, tang ve da ban)
        kht.setTongSoVe(kht.getTongSoVe() - hoaDon.getSoKhach());
        kht.setTongVeDaDat(kht.getTongVeDaDat() + hoaDon.getSoKhach());

        // TU DONG TAO CHI TIET HOA DON (CASCADE INSERT)
        dsChiTiet.taoChiTiet(hoaDon, dsMaKH);

        // Luu file
        try {
            System.out.println("\n[DEBUG] Dang luu DSHoaDonUpdate.txt...");
            System.out.println("[DEBUG] So luong hoa don: " + soLuongHoaDon);
            System.out.println("[DEBUG] File path: " + FILE_PATH);
            
            saveToFile(FILE_PATH);
            System.out.println("[DEBUG] Da luu DSHoaDonUpdate.txt thanh cong!");
            
            dsKHTour.saveToFile("DU_LICH/KeHoachTour.txt");
            System.out.println("[DEBUG] Da luu KeHoachTour.txt thanh cong!");
            
            System.out.println("\n>>> TAO HOA DON THANH CONG! <<<");
        } catch (IOException e) {
            System.out.println("Loi luu file: " + e.getMessage());
            e.printStackTrace(); // In stack trace de debug
        }
    }

    // ===== SUA HOA DON =====
   public void suaHoaDon(String maHD) {
    for (int i = 0; i < soLuongHoaDon; i++) {

        // 🔍 Tìm hóa đơn
        if (list[i] != null && list[i].getMaHD().equals(maHD)) {

            HoaDon hdCu = list[i];
            System.out.println("\n===== THONG TIN HOA DON HIEN TAI =====");
            hdCu.xuatThongTin();

            System.out.println("\n===== NHAP THONG TIN MOI =====");

            // Tạo mới hóa đơn để nhập lại giống như thêm
            HoaDon hdMoi = new HoaDon();
            hdMoi.nhap(sc);
            
            // FIX: Giu nguyen ma hoa don (khong cho thay doi ma)
            System.out.println("Ma hoa don SE KHONG DUOC THAY DOI . Giữ nguyên ma: " + maHD);
            hdMoi.setMaHD(maHD);

            // ============ VALIDATE MÃ HDV ============
            while (!validateMaHDV(hdMoi.getMaHDV().getMaHDV())) {
                System.out.println("❌ Ma HDV khong ton tai! Nhap lai:");
                hdMoi.getMaHDV().setMaHDV(Integer.parseInt(sc.nextLine()));
            }

            // ============ VALIDATE MÃ KẾ HOẠCH ============
            while (!validateMaKHTour(hdMoi.getMaKHTour().getMaKHTour())) {
                System.out.println(" Ma ke hoach tour khong ton tai! Nhap lai:");
                hdMoi.getMaKHTour().setMaKHTour(sc.nextLine());
            }

            KeHoachTour khtMoi = dsKHTour.timTheoMaObject(
                    hdMoi.getMaKHTour().getMaKHTour()
            );
            
            // FIX: Gan day du thong tin KeHoachTour vao HoaDon (bao gom ca giaVe)
            hdMoi.setMaKHTour(khtMoi);

            // ============ VALIDATE KHÁCH HÀNG ĐẠI DIỆN ============
            while (!validateMaKH(hdMoi.getMaKHDaiDien().getMaKH())) {
                System.out.println(" Ma KH dai dien khong ton tai! Nhap lai:");
                hdMoi.getMaKHDaiDien().setMaKH(Integer.parseInt(sc.nextLine()));
            }

            // ============ VALIDATE SỐ KHÁCH ============
            while (!kiemTraSoKhachHopLe(khtMoi, hdMoi.getSoKhach())) {
                System.out.println(" So khach khong hop le! Nhap lai:");
                hdMoi.setSoKhach(Integer.parseInt(sc.nextLine()));
            }

            // ============ VALIDATE SỐ VÉ ============
            while (!checkSoVeHopLe(khtMoi, hdMoi.getSoVe(), hdMoi.getSoKhach())) {
                System.out.println(" So ve khong hop le! Nhap lai:");
                hdMoi.setSoVe(Integer.parseInt(sc.nextLine()));
            }

            // HOÀN TRẢ VÉ CŨ TRƯỚC
            KeHoachTour khtCu = dsKHTour.timTheoMaObject(
                    hdCu.getMaKHTour().getMaKHTour()
            );
            khtCu.setTongSoVe(khtCu.getTongSoVe() + hdCu.getSoVe());
            khtCu.setTongVeDaDat(khtCu.getTongVeDaDat() - hdCu.getSoVe());


            // ============ NHẬP DS KHÁCH HÀNG MỚI ============
            System.out.println("\n===== NHAP DS KHACH HANG DI TOUR =====");

            int[] dsMaKH = new int[hdMoi.getSoVe()];
            for (int j = 0; j < hdMoi.getSoVe(); j++) {
                while (true) {
                    System.out.print("Nhap ma KH thu " + (j + 1) + ": ");
                    int maKH = Integer.parseInt(sc.nextLine());

                    if (!validateMaKH(maKH)) {
                        System.out.println(" Ma KH khong ton tai!");
                        continue;
                    }

                    boolean trung = false;
                    for (int k = 0; k < j; k++) {
                        if (dsMaKH[k] == maKH) {
                            trung = true; break;
                        }
                    }
                    if (trung) {
                        System.out.println(" KH da nhap roi!");
                        continue;
                    }

                    dsMaKH[j] = maKH;
                    break;
                }
            }

            // ============ CẬP NHẬT VÉ CHO TOUR ============
            khtMoi.setTongSoVe(khtMoi.getTongSoVe() - hdMoi.getSoVe());
            khtMoi.setTongVeDaDat(khtMoi.getTongVeDaDat() + hdMoi.getSoVe());

            // ============ CASCADE UPDATE CHI TIẾT ============
            dsChiTiet.xoaChiTiet(maHD);  
            dsChiTiet.taoChiTiet(hdMoi, dsMaKH);

            // LƯU HOÁ ĐƠN MỚI
            list[i] = hdMoi;

            try {
                saveToFile(FILE_PATH);
                dsKHTour.saveToFile("DU_LICH/KeHoachTour.txt");
                System.out.println("\n>>> SUA HOA DON THANH CONG!");
            } catch (Exception e) {
                System.out.println("Loi luu file: " + e.getMessage());
            }

            return;
        }
    }

    System.out.println(" Khong tim thay ma hoa don!");
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
    public HoaDon[] timHoaDonTheoKhachHangDaiDien(int maKH) {
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
                hd.xuatThongTin();

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

    // // ===== SUA DU LIEU BI LOI =====
    // // Sua cac hoa don co giaVe = 0 trong chi tiet
    // public void suaDuLieuGiaVe() {
    //     System.out.println("\n[AUTO FIX] Dang kiem tra va sua du lieu giaVe = 0...");
    //     int count = 0;
    //     for (int i = 0; i < soLuongHoaDon; i++) {
    //         if (list[i] != null) {
    //             HoaDon hd = list[i];
    //             // Neu giaVe = 0, load lai tu DSKHTour
    //             if (hd.getMaKHTour().getGiaVe() == 0.0) {
    //                 KeHoachTour kht = dsKHTour.timTheoMaObject(hd.getMaKHTour().getMaKHTour());
    //                 if (kht != null && kht.getGiaVe() > 0) {
    //                     hd.setMaKHTour(kht);
    //                     count++;
    //                     System.out.println("  - Sua hoa don " + hd.getMaHD() + ": " + kht.getMaKHTour() + " -> giaVe = " + String.format("%,.0f", kht.getGiaVe()));
    //                 }
    //             }
    //         }
    //     }
    //     if (count > 0) {
    //         try {
    //             saveToFile(FILE_PATH);
    //             System.out.println("[AUTO FIX] Da sua " + count + " hoa don thanh cong!");
    //         } catch (IOException e) {
    //             System.out.println("[AUTO FIX] Loi luu file: " + e.getMessage());
    //         }
    //     } else {
    //         System.out.println("[AUTO FIX] Khong co hoa don nao can sua.");
    //     }
    // }
    
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
        DSHoaDonUpdate DSHoaDonUpdate = new DSHoaDonUpdate(dshdv, dskh, dsKHTour);
        DSHoaDonUpdate.setDsKHTour(dsKHTour);
        try {
            DSHoaDonUpdate.loadFromFile(FILE_PATH);
            DSHoaDonUpdate.dsChiTiet.loadFromFile("DU_LICH/DSChiTietHD.txt");
            
            // // Tu dong sua du lieu bi loi
            // DSHoaDonUpdate.suaDuLieuGiaVe();
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
                case 1: DSHoaDonUpdate.hienThiDanhSachHoaDon(); break;
                case 2:
                    // Tao hoa don moi (khong can nhap truoc)
                    HoaDon hdMoi = new HoaDon();
                    DSHoaDonUpdate.themHoaDon(hdMoi);
                    break;
                case 3:
                    System.out.print("Nhap ma hoa don can sua: ");
                    DSHoaDonUpdate.suaHoaDon(sc.nextLine());
                    break;
                case 4:
                    System.out.print("Nhap ma hoa don can xoa: ");
                    DSHoaDonUpdate.xoaHoaDon(sc.nextLine());
                    break;
                case 5:
                    System.out.print("Nhap ma hoa don: ");
                    HoaDon hdTim = DSHoaDonUpdate.timHoaDonTheoMa(sc.nextLine());
                    if (hdTim != null) {
                        System.out.println("\nTim thay:");
                        hdTim.xuatThongTin();
                    } else System.out.println("Khong tim thay!");
                    break;
                case 6:
                    System.out.print("Nhap ma khach hang: ");
                    HoaDon[] ketQua = DSHoaDonUpdate.timHoaDonTheoKhachHangDaiDien(Integer.parseInt(sc.nextLine()));
                    if (ketQua.length > 0) {
                        System.out.println("\nTim thay " + ketQua.length + " hoa don:");
                        for (int i = 0; i < ketQua.length; i++) {
                            System.out.println("\n[Hoa don " + (i + 1) + "]");
                            ketQua[i].xuatThongTin();
                        }
                    } else System.out.println("Khong tim thay!");
                    break;
                case 7: DSHoaDonUpdate.thongKeHoaDon(); break;
                case 8:
                    System.out.print("Nhap ma hoa don: ");
                    ChiTietHD ct = DSHoaDonUpdate.dsChiTiet.xemChiTiet(sc.nextLine());
                    if (ct != null) {
                        System.out.println("\nChi tiet hoa don:");
                        ct.xuatThongTin();
                    } else System.out.println("Khong tim thay chi tiet!");
                    break;
                case 9: DSHoaDonUpdate.dsChiTiet.hienThiDanhSach(); break;
                case 0: exit = true; break;
                default: System.out.println("Lua chon khong hop le.");
            }
        }

        // Luu file truoc khi thoat
        try {
            DSHoaDonUpdate.saveToFile(FILE_PATH);
            dsKHTour.saveToFile("DU_LICH/KeHoachTour.txt");
        } catch (IOException e) {
            System.out.println("Loi luu file: " + e.getMessage());
        }

        System.out.println("Thoat chuong trinh!");
        sc.close();
    }
}
