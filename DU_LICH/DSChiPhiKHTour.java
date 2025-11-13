package DU_LICH;

import DU_LICH.NH_KS_PT.DSKhachSan;
import DU_LICH.NH_KS_PT.DSNhaHang;
import DU_LICH.NH_KS_PT.KhachSan;
import DU_LICH.NH_KS_PT.NhaHang;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Scanner;

public class DSChiPhiKHTour {
    private ChiPhiKHTour[] dsChiPhi;
    private int soLuong;
    private final int MAX = 50;
    private DSKHTour dsKHTour;
    private DSKhachSan dsKhachSan;
    private DSNhaHang dsNhaHang;

    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private static final SimpleDateFormat SDF = new SimpleDateFormat(DATE_PATTERN, new Locale("vi", "VN"));
    private static final Scanner sc = new Scanner(System.in);

    public DSChiPhiKHTour(DSKHTour dsKHTour, DSKhachSan dsKhachSan, DSNhaHang dsNhaHang) {
        this.dsChiPhi = new ChiPhiKHTour[MAX];
        this.soLuong = 0;
        this.dsKHTour = dsKHTour;
        this.dsKhachSan = dsKhachSan;
        this.dsNhaHang = dsNhaHang;
    }

    public int getSoLuong() { return soLuong; }

    private int findIndexByMa(String ma) {
        if (ma == null) return -1;
        for (int i = 0; i < soLuong; i++) {
            if (dsChiPhi[i] != null && ma.equalsIgnoreCase(dsChiPhi[i].getMaKHTour())) {
                return i;
            }
        }
        return -1;
    }

    // Hàm thêm chi phí (tương tác)
    public void themChiPhi() {
        if (soLuong >= MAX) {
            System.out.println("Danh sach da day (" + MAX + ")!");
            return;
        }

        System.out.print("Nhap ma KHTour: ");
        String maKHTour = sc.nextLine().trim();

        // Check maKHTour tồn tại trong DSKHTour
        KeHoachTour kht = null;
        if (dsKHTour != null) {
            kht = dsKHTour.timTheoMaObject(maKHTour);
        }
        if (kht == null) {
            System.out.println("Ma KHTour khong ton tai! Khong the tao chi phi.");
            return;
        }

        if (findIndexByMa(maKHTour) != -1) {
            System.out.println("Da ton tai chi phi cho ma KHTour nay!");
            return;
        }

        // Chọn khách sạn
        System.out.println("Danh sach khach san:");
        dsKhachSan.xuatDanhSach();
        System.out.print("Nhap ma khach san: ");
        String maKhachSan = sc.nextLine().trim();
        KhachSan ks = dsKhachSan.timTheoMa(maKhachSan);
        if (ks == null) {
            System.out.println("Khach san khong ton tai!");
            return;
        }

        Date ngayDen = null, ngayDi = null;
        while (true) {
            System.out.print("Nhap ngay den (dd/MM/yyyy): ");
            String nd = sc.nextLine().trim();
            try {
                java.util.Date util = SDF.parse(nd);
                ngayDen = new Date(util.getTime());
                break;
            } catch (ParseException e) {
                System.out.println("Ngay khong hop le!");
            }
        }

        while (true) {
            System.out.print("Nhap ngay di (dd/MM/yyyy): ");
            String ndi = sc.nextLine().trim();
            try {
                java.util.Date util = SDF.parse(ndi);
                ngayDi = new Date(util.getTime());
                if (ngayDi.after(ngayDen)) {
                    break;
                } else {
                    System.out.println("Ngay di phai sau ngay den!");
                }
            } catch (ParseException e) {
                System.out.println("Ngay khong hop le!");
            }
        }

        long diff = ngayDi.getTime() - ngayDen.getTime();
        long soNgay = (diff / (1000 * 60 * 60 * 24)) + 1; // Bao gồm cả ngày đến và đi
        double tongTienPhong = soNgay * ks.getGiaDatPhong();

        // Chọn nhà hàng
        System.out.println("Danh sach nha hang:");
        dsNhaHang.xuatDanhSach();
        System.out.print("Nhap ma nha hang: ");
        String maNhaHang = sc.nextLine().trim();
        NhaHang nh = dsNhaHang.timTheoMa(maNhaHang);
        if (nh == null) {
            System.out.println("Nha hang khong ton tai!");
            return;
        }

        System.out.print("Nhap so luong combo: ");
        int soLuongCombo = 0;
        try {
            soLuongCombo = Integer.parseInt(sc.nextLine().trim());
        } catch (Exception ex) {
            System.out.println("So luong combo khong hop le, mac dinh = 0");
            soLuongCombo = 0;
        }
        double tongTienAn = soLuongCombo * nh.getGiaCombo();

        ChiPhiKHTour cp = new ChiPhiKHTour(maKHTour, maNhaHang, maKhachSan, tongTienPhong, tongTienAn);
        dsChiPhi[soLuong++] = cp;
        System.out.println("Them chi phi thanh cong!");
    }

    public boolean xoaTheoMa(String ma) {
        int idx = findIndexByMa(ma);
        if (idx == -1) return false;
        for (int i = idx; i < soLuong - 1; i++) dsChiPhi[i] = dsChiPhi[i + 1];
        dsChiPhi[soLuong - 1] = null;
        soLuong--;
        System.out.println("Xoa chi phi thanh cong!");
        return true;
    }

    public boolean chinhSuaTheoMa(String ma) {
        int idx = findIndexByMa(ma);
        if (idx == -1) {
            System.out.println("Khong tim thay chi phi voi ma " + ma);
            return false;
        }

        ChiPhiKHTour cp = dsChiPhi[idx];
        System.out.println("De trong va nhan Enter neu muon giu nguyen.");

        // Sửa khách sạn và tính lại tongTienPhong
        System.out.print("Ma khach san hien tai (" + cp.getMaKhachSan() + "): ");
        String maKSNew = sc.nextLine().trim();
        KhachSan ks = null;
        if (!maKSNew.isEmpty()) {
            ks = dsKhachSan.timTheoMa(maKSNew);
            if (ks == null) {
                System.out.println("Khach san khong ton tai, giu nguyen.");
            } else {
                cp.setMaKhachSan(maKSNew);
            }
        }
        if (ks == null) ks = dsKhachSan.timTheoMa(cp.getMaKhachSan()); // Giữ nguyên

    Date ngayDen = null, ngayDi = null;
        System.out.print("Cap nhat ngay den/di de tinh lai tong tien phong? (y/n): ");
        if (sc.nextLine().trim().equalsIgnoreCase("y")) {
            while (true) {
                System.out.print("Nhap ngay den moi (dd/MM/yyyy): ");
                String nd = sc.nextLine().trim();
                if (nd.isEmpty()) break;
                try {
                    java.util.Date util = SDF.parse(nd);
                    ngayDen = new Date(util.getTime());
                    break;
                } catch (ParseException e) {
                    System.out.println("Ngay khong hop le!");
                }
            }

            while (true) {
                System.out.print("Nhap ngay di moi (dd/MM/yyyy): ");
                String ndi = sc.nextLine().trim();
                if (ndi.isEmpty()) break;
                try {
                    java.util.Date util = SDF.parse(ndi);
                    ngayDi = new Date(util.getTime());
                    if (ngayDi.after(ngayDen)) {
                        break;
                    } else {
                        System.out.println("Ngay di phai sau ngay den!");
                    }
                } catch (ParseException e) {
                    System.out.println("Ngay khong hop le!");
                }
            }

            if (ngayDen != null && ngayDi != null) {
                long diff = ngayDi.getTime() - ngayDen.getTime();
                long soNgay = (diff / (1000 * 60 * 60 * 24)) + 1;
                if (ks != null) {
                    cp.setTongTienPhong(soNgay * ks.getGiaDatPhong());
                } else {
                    System.out.println("Canh bao: Khong co thong tin khach san hop le de tinh tien phong. Bo qua cap nhat tien phong.");
                }
            }
        }

        // Sửa nhà hàng và tính lại tongTienAn
        System.out.print("Ma nha hang hien tai (" + cp.getMaNhaHang() + "): ");
        String maNHNew = sc.nextLine().trim();
        NhaHang nh = null;
        if (!maNHNew.isEmpty()) {
            nh = dsNhaHang.timTheoMa(maNHNew);
            if (nh == null) {
                System.out.println("Nha hang khong ton tai, giu nguyen.");
            } else {
                cp.setMaNhaHang(maNHNew);
            }
        }
        if (nh == null) nh = dsNhaHang.timTheoMa(cp.getMaNhaHang()); // Giữ nguyên

        System.out.print("Nhap so luong combo moi (hien tai tinh cho tong tien an " + cp.getTongTienAn() + "): ");
        String soComboStr = sc.nextLine().trim();
        if (!soComboStr.isEmpty()) {
            try {
                int soLuongCombo = Integer.parseInt(soComboStr);
                cp.setTongTienAn(soLuongCombo * nh.getGiaCombo());
            } catch (NumberFormatException e) {
                System.out.println("So khong hop le, giu nguyen.");
            }
        }

        System.out.println("Chinh sua chi phi thanh cong!");
        return true;
    }

    public ChiPhiKHTour timTheoMa(String ma) {
        int idx = findIndexByMa(ma);
        return idx == -1 ? null : dsChiPhi[idx];
    }

    public int timTheoTen(String tuKhoa, ChiPhiKHTour[] ketQua) {
        if (tuKhoa == null) tuKhoa = "";
        String tk = tuKhoa.trim().toLowerCase();
        int dem = 0;
        for (int i = 0; i < soLuong; i++) {
            ChiPhiKHTour cp = dsChiPhi[i];
            if (cp != null && cp.getMaKHTour() != null && cp.getMaKHTour().toLowerCase().contains(tk)) {
                if (ketQua != null && dem < ketQua.length) ketQua[dem] = cp;
                dem++;
            }
        }
        return dem;
    }

    // Thống kê đơn giản cho chi phí kế hoạch tour
    public void thongKeDonGian() {
        System.out.println("--- Thong ke don gian chi phi ke hoach tour ---");
        System.out.println("So luong chi phi ke hoach: " + soLuong);
        if (soLuong > 0) {
            double tongAn = 0.0, tongPhong = 0.0;
            int dem = 0;
            for (int i = 0; i < soLuong; i++) {
                ChiPhiKHTour c = dsChiPhi[i];
                if (c != null) { 
                    tongAn += c.getTongTienAn(); 
                    tongPhong += c.getTongTienPhong(); 
                    dem++; 
                }
            }
            double avgAn = dem == 0 ? 0.0 : (tongAn / dem);
            double avgPhong = dem == 0 ? 0.0 : (tongPhong / dem);
            System.out.println("Tong tien an trung binh: " + avgAn);
            System.out.println("Tong tien phong trung binh: " + avgPhong);
        }
    }

    public void xuatDanhSach() {
        if (soLuong == 0) { System.out.println("Danh sách trống."); return; }
        System.out.println("===== DANH SÁCH CHI PHI KE HOACH TOUR (" + soLuong + ") =====");
        for (int i = 0; i < soLuong; i++) {
            System.out.println("-- Chi phi ke hoach thu " + (i+1) + " --");
            ChiPhiKHTour cp = dsChiPhi[i];
            System.out.println("Ma KHTour: " + cp.getMaKHTour());
            System.out.println("Ma Nha Hang: " + cp.getMaNhaHang());
            System.out.println("Ma Khach San: " + cp.getMaKhachSan());
            System.out.println("Tong Tien An: " + cp.getTongTienAn());
            System.out.println("Tong Tien Phong: " + cp.getTongTienPhong());
            System.out.println();
        }
    }

    // File format: maKHTour,maNhaHang,maKhachSan,tongTienAn,tongTienPhong 
    public int loadFromFile(String filePath) {
        int dem = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            soLuong = 0;
            String line;
            while ((line = br.readLine()) != null && soLuong < dsChiPhi.length) {
                line = line.trim(); if (line.isEmpty()) continue;
                String[] p = line.split("[,;]");
                if (p.length < 5) { System.out.println("Bo qua dong khong hop le: " + line); continue; }
                try {
                    String ma = p[0].trim();
                    String maNH = p[1].trim();
                    String maKS = p[2].trim();
                    double tongAn = Double.parseDouble(p[3].trim());
                    double tongPhong = Double.parseDouble(p[4].trim());

                    // Validate referenced IDs if corresponding DS are provided
                    boolean ok = true;
                    if (dsKHTour != null && dsKHTour.timTheoMaObject(ma) == null) {
                        System.out.println("Canh bao: Ma KHTour '" + ma + "' khong ton tai trong DSKHTour. Bo qua."); ok = false;
                    }
                    if (dsNhaHang != null && dsNhaHang.timTheoMa(maNH) == null) {
                        System.out.println("Canh bao: Ma NhaHang '" + maNH + "' khong ton tai. Bo qua."); ok = false;
                    }
                    if (dsKhachSan != null && dsKhachSan.timTheoMa(maKS) == null) {
                        System.out.println("Canh bao: Ma KhachSan '" + maKS + "' khong ton tai. Bo qua."); ok = false;
                    }

                    if (!ok) continue;

                    // ChiPhiKHTour constructor expects (maKHTour, maNhaHang, maKhachSan, tongTienPhong, tongTienAn)
                    ChiPhiKHTour cp = new ChiPhiKHTour(
                        ma,
                        maNH,
                        maKS,
                        tongPhong,
                        tongAn
                    );
                    dsChiPhi[soLuong++] = cp;
                    dem++;
                } catch (Exception ex) {
                    System.out.println("Lỗi phân tích dòng: " + line + " -> " + ex.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Không thể đọc file: " + e.getMessage());
        }
        return dem;
    }

    public int saveToFile(String filePath) {
        int dem = 0;
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
                    String.valueOf(cp.getTongTienPhong())
                ));
                bw.newLine();
                dem++;
            }
        } catch (IOException e) {
            System.out.println("Không thể ghi file: " + e.getMessage());
        }
        return dem;
    }
}
