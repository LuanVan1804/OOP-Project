package DU_LICH;

import DU_LICH.NH_KS_PT.DSKhachSan;
import DU_LICH.NH_KS_PT.DSNhaHang;
import DU_LICH.NH_KS_PT.KhachSan;
import DU_LICH.NH_KS_PT.NhaHang;

import java.io.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Scanner;

public class DSChiPhiKHTour {
    private ChiPhiKHTour[] dsChiPhi;
    private int soLuong;
    private final int MAX = 100; // Tăng lên cho thoải mái
    private DSKHTour dsKHTour;
    private DSKhachSan dsKhachSan;
    private DSNhaHang dsNhaHang;
    private DU_LICH.NH_KS_PT.DSPhuongTien dsPhuongTien;

    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private static final SimpleDateFormat SDF = new SimpleDateFormat(DATE_PATTERN, new Locale("vi", "VN"));
    private static final Scanner sc = new Scanner(System.in);

    public DSChiPhiKHTour(DSKHTour dsKHTour, DSKhachSan dsKhachSan, DSNhaHang dsNhaHang) {
        this.dsChiPhi = new ChiPhiKHTour[MAX];
        this.soLuong = 0;
        this.dsKHTour = dsKHTour;
        this.dsKhachSan = dsKhachSan;
        this.dsNhaHang = dsNhaHang;
        this.dsPhuongTien = null;
    }

    public void setDsPhuongTien(DU_LICH.NH_KS_PT.DSPhuongTien dsPhuongTien) {
        this.dsPhuongTien = dsPhuongTien;
    }

    public int getSoLuong() { return soLuong; }

    private int findIndexByMa(String ma) {
        if (ma == null || ma.trim().isEmpty()) return -1;
        for (int i = 0; i < soLuong; i++) {
            if (dsChiPhi[i] != null && ma.equalsIgnoreCase(dsChiPhi[i].getMaKHTour())) {
                return i;
            }
        }
        return -1;
    }

    public ChiPhiKHTour timTheoMa(String ma) {
        int idx = findIndexByMa(ma);
        return idx == -1 ? null : dsChiPhi[idx];
    }

    // Tìm theo từ khóa — trả về mảng kết quả
    public ChiPhiKHTour[] timTheoTen(String key) {
        if (key == null || key.trim().isEmpty()) return new ChiPhiKHTour[0];
        String tk = key.trim().toLowerCase();
        int count = 0;
        for (int i = 0; i < soLuong; i++) {
            if (dsChiPhi[i] != null && dsChiPhi[i].getMaKHTour() != null &&
                dsChiPhi[i].getMaKHTour().toLowerCase().contains(tk)) {
                count++;
            }
        }

        ChiPhiKHTour[] res = new ChiPhiKHTour[count];
        int idx = 0;
        for (int i = 0; i < soLuong; i++) {
            if (dsChiPhi[i] != null && dsChiPhi[i].getMaKHTour() != null &&
                dsChiPhi[i].getMaKHTour().toLowerCase().contains(tk)) {
                res[idx++] = dsChiPhi[i];
            }
        }
        return res;
    }

    // ==================== THÊM CHI PHÍ MỚI ====================
    public void themChiPhi() {
        if (soLuong >= MAX) {
            System.out.println("Danh sach chi phi da day (" + MAX + ")!");
            return;
        }

        System.out.print("Nhap ma ke hoach tour (KHTour): ");
        String maKHTour = sc.nextLine().trim();
        if (maKHTour.isEmpty()) {
            System.out.println("Ma KHTour khong duoc de trong!");
            return;
        }

        // Kiểm tra KHTour tồn tại
        if (dsKHTour != null && dsKHTour.timTheoMaObject(maKHTour) == null) {
            System.out.println("Ma KHTour '" + maKHTour + "' khong ton tai trong danh sach ke hoach tour!");
            return;
        }

        // Kiểm tra đã có chi phí chưa
        if (findIndexByMa(maKHTour) != -1) {
            System.out.println("Da ton tai chi phi cho ke hoach tour nay! Vui long dung chuc nang chinh sua.");
            return;
        }

        // Chọn khách sạn
        System.out.println("\n--- Danh sach khach san ---");
        dsKhachSan.xuatDanhSach();
        System.out.print("Nhap ma khach san: ");
        String maKS = sc.nextLine().trim();
        KhachSan ks = dsKhachSan.timTheoMa(maKS);
        if (ks == null) {
            System.out.println("Khach san khong ton tai!");
            return;
        }

        // Nhập ngày đến - đi
        Date ngayDen = nhapNgay("Nhap ngay den (dd/MM/yyyy): ");
        Date ngayDi = nhapNgay("Nhap ngay di (dd/MM/yyyy): ");
        if (ngayDen == null || ngayDi == null) return;

        if (!ngayDi.after(ngayDen)) {
            System.out.println("Ngay di phai sau ngay den!");
            return;
        }

        long soNgay = (ngayDi.getTime() - ngayDen.getTime()) / (1000 * 60 * 60 * 24) + 1;
        double tienPhong = soNgay * ks.getGiaDatPhong();

        // Chọn nhà hàng
        System.out.println("\n--- Danh sach nha hang ---");
        dsNhaHang.xuatDanhSach();
        System.out.print("Nhap ma nha hang: ");
        String maNH = sc.nextLine().trim();
        NhaHang nh = dsNhaHang.timTheoMa(maNH);
        if (nh == null) {
            System.out.println("Nha hang khong ton tai!");
            return;
        }

        System.out.print("Nhap so luong combo an: ");
        int soCombo = 0;
        try {
            soCombo = Integer.parseInt(sc.nextLine().trim());
            if (soCombo < 0) soCombo = 0;
        } catch (Exception e) {
            System.out.println("So luong khong hop le → mac dinh 0");
        }
        double tienAn = soCombo * nh.getGiaCombo();

        // Chọn phương tiện (tuỳ chọn)
        String maPT = "";
        double tienPT = 0.0;
        if (dsPhuongTien != null) {
            System.out.println("\n--- Danh sach phuong tien ---");
            dsPhuongTien.xuat();
            System.out.print("Nhap bien kiem soat phuong tien (de trong neu khong su dung): ");
            String bien = sc.nextLine().trim();
            if (!bien.isEmpty()) {
                DU_LICH.NH_KS_PT.PhuongTien pt = dsPhuongTien.timTheoBien(bien);
                if (pt == null) {
                    System.out.println("Phuong tien khong ton tai -> bo qua.");
                } else {
                    maPT = pt.getBienKiemSoat();
                    // reuse soNgay computed above
                    tienPT = pt.getPhiTheoNgay() * soNgay;
                    System.out.printf("Chi phi phuong tien duoc tinh: %,.0f VND (%d ngay)\n", tienPT, soNgay);
                }
            }
        }

        // Tao va them
        ChiPhiKHTour cp = new ChiPhiKHTour(maKHTour, maNH, maKS, maPT, tienPhong, tienAn, tienPT);
        dsChiPhi[soLuong++] = cp;

        System.out.println("=== THEM CHI PHI THANH CONG! ===");
        cp.xuatThongTin();
    }

    // ==================== CHỈNH SỬA CHI PHÍ ====================
    public boolean chinhSuaTheoMa(String ma) {
        int idx = findIndexByMa(ma);
        if (idx == -1) {
            System.out.println("Khong tim thay chi phi cho ma KHTour: " + ma);
            return false;
        }

        ChiPhiKHTour cp = dsChiPhi[idx];
        KhachSan ks = dsKhachSan.timTheoMa(cp.getMaKhachSan());
        NhaHang nh = dsNhaHang.timTheoMa(cp.getMaNhaHang());

        System.out.println("\n--- CHINH SUA CHI PHI KE HOACH TOUR ---");
        System.out.println("De trong va nhan Enter neu muon giu nguyen.");

        // Sửa khách sạn
        System.out.printf("Ma khach san hien tai (%s): ", cp.getMaKhachSan());
        String newKS = sc.nextLine().trim();
        if (!newKS.isEmpty()) {
            KhachSan ksMoi = dsKhachSan.timTheoMa(newKS);
            if (ksMoi != null) {
                ks = ksMoi;
                cp.setMaKhachSan(newKS);
            } else {
                System.out.println("Khach san khong ton tai → giu nguyen.");
            }
        }

        // CCap nhat ngay → tinh lai tien phong
        System.out.print("CCap nhat ngay den/di de tinh lai tien phong? (y/n): ");
        if (sc.nextLine().trim().equalsIgnoreCase("y")) {
            Date d1 = nhapNgay("Nhap ngay den moi (dd/MM/yyyy): ");
            Date d2 = nhapNgay("Nhap ngay di moi (dd/MM/yyyy): ");
            if (d1 != null && d2 != null && d2.after(d1)) {
                long soNgay = (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24) + 1;
                if (ks != null) {
                    cp.setTongTienPhong(soNgay * ks.getGiaDatPhong());
                    System.out.printf("→ Cap nhat tien phong: %, .0f VND (%d dem)%n", cp.getTongTienPhong(), soNgay);
                }
            }
        }

        // Sua nha hang
        System.out.printf("Ma nha hang hien tai (%s): ", cp.getMaNhaHang());
        String newNH = sc.nextLine().trim();
        if (!newNH.isEmpty()) {
            NhaHang nhMoi = dsNhaHang.timTheoMa(newNH);
            if (nhMoi != null) {
                nh = nhMoi;
                cp.setMaNhaHang(newNH);
            } else {
                System.out.println("Nha hang khong ton tai → giu nguyen.");
            }
        }

        // Sua so combo
        double comboHienTai = nh != null && nh.getGiaCombo() > 0 ? cp.getTongTienAn() / nh.getGiaCombo() : 0;
        System.out.printf("So combo hien tai (%.0f combo → %, .0f VND): ", comboHienTai, cp.getTongTienAn());
        String input = sc.nextLine().trim();
        if (!input.isEmpty()) {
            try {
                int soCombo = Integer.parseInt(input);
                if (soCombo >= 0 && nh != null) {
                    cp.setTongTienAn(soCombo * nh.getGiaCombo());
                }
            } catch (Exception e) {
                System.out.println("So khong hop le → giu nguyen.");
            }
        }

        System.out.println("Chinh sua chi phi thanh cong!");
        return true;
    }

    // ==================== XÓA CHI PHÍ ====================
    public boolean xoaTheoMa(String ma) {
        int idx = findIndexByMa(ma);
        if (idx == -1) {
            System.out.println("Khong tim thay chi phi cho ma: " + ma);
            return false;
        }
        for (int i = idx; i < soLuong - 1; i++) {
            dsChiPhi[i] = dsChiPhi[i + 1];
        }
        dsChiPhi[--soLuong] = null;
        System.out.println("Xoa chi phi thanh cong!");
        return true;
    }

    // ==================== HIỂN THỊ DANH SÁCH ====================
    public void xuatDanhSach() {
        if (soLuong == 0) {
            System.out.println("Chua co chi phi ke hoach tour nao.");
            return;
        }
        System.out.println("════════════════════════════════════════════════════════════════════════════════");
        System.out.println("                          DANH SÁCH CHI PHÍ KẾ HOẠCH TOUR");
        System.out.println("════════════════════════════════════════════════════════════════════════════════");
        for (int i = 0; i < soLuong; i++) {
            System.out.printf("STT: %2d | ", i + 1);
            dsChiPhi[i].xuatThongTin();
            System.out.println();
        }
    }

    // ==================== TẢI & LƯU FILE ====================
    public int loadFromFile(String filePath) {
        int dem = 0;
        File f = new File(filePath);
        if (!f.exists()) return 0;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            soLuong = 0;
            String line;
            while ((line = br.readLine()) != null && soLuong < MAX) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] p = line.split(",", -1);
                if (p.length < 5) continue;

                try {
                    String ma = p[0].trim();
                    String maNH = p[1].trim();
                    String maKS = p[2].trim();
                    double tienAn = Double.parseDouble(p[3].trim());
                    double tienPhong = Double.parseDouble(p[4].trim());

                    // Validate tham chiếu
                    if (dsKHTour != null && dsKHTour.timTheoMaObject(ma) == null) continue;
                    if (dsNhaHang != null && dsNhaHang.timTheoMa(maNH) == null) continue;
                    if (dsKhachSan != null && dsKhachSan.timTheoMa(maKS) == null) continue;

                    String maPT = "";
                    double tienPT = 0.0;
                    if (p.length >= 7) {
                        maPT = p[5].trim();
                        try { tienPT = Double.parseDouble(p[6].trim()); } catch (Exception ex) { tienPT = 0.0; }
                    }
                    dsChiPhi[soLuong++] = new ChiPhiKHTour(ma, maNH, maKS, maPT, tienPhong, tienAn, tienPT);
                    dem++;
                } catch (Exception e) {
                    System.out.println("Bo qua dong loi: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Loi doc file chi phi: " + e.getMessage());
        }
        return dem;
    }

    public int saveToFile(String filePath) {
        File f = new File(filePath);
        if (f.getParentFile() != null) f.getParentFile().mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
            for (int i = 0; i < soLuong; i++) {
                ChiPhiKHTour cp = dsChiPhi[i];
                bw.write(String.join(",",
                    cp.getMaKHTour(),
                    cp.getMaNhaHang(),
                    cp.getMaKhachSan(),
                    String.valueOf(cp.getTongTienAn()),
                    String.valueOf(cp.getTongTienPhong()),
                    (cp.getMaPhuongTien() == null ? "" : cp.getMaPhuongTien()),
                    String.valueOf(cp.getTongTienPhuongTien())
                ));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Loi ghi file chi phi: " + e.getMessage());
            return 0;
        }
        return soLuong;
    }

    // ==================== HELPER ====================
    private Date nhapNgay(String msg) {
        while (true) {
            System.out.print(msg);
            String s = sc.nextLine().trim();
            if (s.isEmpty()) {
                System.out.println("Khong duoc de trong!");
                continue;
            }
            try {
                java.util.Date d = SDF.parse(s);
                return new Date(d.getTime());
            } catch (ParseException e) {
                System.out.println("Dinh dang ngay khong dung! Vui long nhap theo dd/MM/yyyy");
            }
        }
    }

    // ==================== THỐNG KÊ ĐƠN GIẢN ====================
    // In ra tổng số bản ghi, tổng tiền ăn, tổng tiền phòng
    public void thongKeDonGian() {
        if (soLuong == 0) {
            System.out.println("Chua co chi phi ke hoach tour de thong ke.");
            return;
        }
        double tongAn = 0;
        double tongPhong = 0;
        for (int i = 0; i < soLuong; i++) {
            ChiPhiKHTour cp = dsChiPhi[i];
            if (cp == null) continue;
            tongAn += cp.getTongTienAn();
            tongPhong += cp.getTongTienPhong();
        }
        System.out.println("===== THONG KE CHI PHI KE HOACH TOUR (DON GIAN) =====");
        System.out.println("So ban ghi: " + soLuong);
        System.out.printf("Tong tien an : %, .0f VND%n", tongAn);
        System.out.printf("Tong tien phong: %, .0f VND%n", tongPhong);
        System.out.printf("Tong chi phi  : %, .0f VND%n", (tongAn + tongPhong));
        System.out.println("=====================================================");
    }
}