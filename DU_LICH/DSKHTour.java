package DU_LICH;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Scanner;

import DU_LICH.TourDuLich.DSTour;
import DU_LICH.Nguoi.DSHDV;

public class DSKHTour {
    private KeHoachTour[] list;
    private int soLuongKHTour;
    private static final Scanner sc = new Scanner(System.in);
    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private static final SimpleDateFormat SDF = new SimpleDateFormat(DATE_PATTERN, new Locale("vi", "VN"));

    // Tham chiếu tới các danh sách khác để validate
    private DSTour dsTour;
    private DSHDV dsHDV;
    
    public DSKHTour() {
        this.list = new KeHoachTour[50];
        this.soLuongKHTour = 0;
    }
    // Constructor với tham chiếu tới các danh sách khác
    public DSKHTour(DSTour dsTour, DSHDV dsHDV) {
        this.list = new KeHoachTour[50];
        this.soLuongKHTour = 0;
        this.dsTour = dsTour;
        this.dsHDV = dsHDV;
    }

    public KeHoachTour[] getList() {
        return list;
    }

    public int getSoLuongKHTour() {
        return soLuongKHTour;
    }

    // Kiểm tra mã kế hoạch tour có bị trùng không
    private boolean isMaKHTourUnique(String maKHTour) {
        if (maKHTour == null || maKHTour.trim().isEmpty()) return false;
        for (int i = 0; i < soLuongKHTour; i++) {
            if (list[i] != null && maKHTour.equalsIgnoreCase(list[i].getMaKHTour())) {
                return false;
            }
        }
        return true;
    }

    // Thêm kế hoạch tour mới
    public void themKHTour() {
        if (soLuongKHTour >= list.length) {
            System.out.println("Danh sach ke hoach tour da day!");
            return;
        }

        KeHoachTour kht = new KeHoachTour();
        kht.nhapThongTin();

        // Kiểm tra mã trùng
        while (!isMaKHTourUnique(kht.getMaKHTour())) {
            System.out.print("Ma KHTour da ton tai! Nhap lai: ");
            kht.setMaKHTour(sc.nextLine().trim());
        }

        // Validate mã tour tồn tại
        if (dsTour != null) {
            boolean found = false;
            for (DU_LICH.TourDuLich.Tour t : dsTour.getList()) {
                if (t != null && t.getMaTour() != null && t.getMaTour().equalsIgnoreCase(kht.getMaTour())) {
                    kht.setGiaVe(t.getDonGia());
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Ma Tour khong ton tai trong danh sach tour!");
                return;
            }
        }

        // Validate mã hướng dẫn viên
        if (dsHDV != null && !kht.getMaHDV().trim().isEmpty()) {
            try {
                int maHDV = Integer.parseInt(kht.getMaHDV().trim());
                if (dsHDV.timTheoMa(maHDV) == null) {
                    System.out.println("Ma HDV khong ton tai!");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Ma HDV phai la so nguyen!");
                return;
            }
        }

        list[soLuongKHTour++] = kht;
        System.out.println("Them ke hoach tour thanh cong!");
    }

    // Hiển thị danh sách (cần truyền DSChiPhiKHTour để lấy chi phí)
    public void hienThiDanhSachKHTour(DSChiPhiKHTour dsChiPhi) {
        if (soLuongKHTour == 0) {
            System.out.println("Danh sach ke hoach tour trong!");
            return;
        }
        System.out.println("════════════════════════════════════════════════════════════════════════════════");
        System.out.println("                             DANH SÁCH KẾ HOẠCH TOUR");
        System.out.println("════════════════════════════════════════════════════════════════════════════════");
        for (int i = 0; i < soLuongKHTour; i++) {
            if (list[i] != null) {
                System.out.printf("STT: %2d | ", i + 1);
                list[i].hienThiNgan(dsChiPhi);
            }
        }
        System.out.println("════════════════════════════════════════════════════════════════════════════════");
    }

    // Xóa kế hoạch tour theo mã
    public boolean xoaKHTour(String maKHTour) {
        int index = -1;
        for (int i = 0; i < soLuongKHTour; i++) {
            if (list[i] != null && maKHTour.equalsIgnoreCase(list[i].getMaKHTour())) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            System.out.println("Khong tim thay ke hoach tour voi ma: " + maKHTour);
            return false;
        }

        // Dồn mảng
        for (int i = index; i < soLuongKHTour - 1; i++) {
            list[i] = list[i + 1];
        }
        list[soLuongKHTour - 1] = null;
        soLuongKHTour--;
        System.out.println("XXoa ke hoach tour thanh cong!");
        return true;
    }

    // Chỉnh sửa kế hoạch tour (chỉ sửa thông tin cơ bản, không sửa chi phí)
    public void chinhSuaKHTour(String maKHTour) {
        KeHoachTour kht = timTheoMaObject(maKHTour);
        if (kht == null) {
            System.out.println("Khong tim thay ke hoach tour voi ma: " + maKHTour);
            return;
        }

        System.out.println("De trong va nhan Enter neu muon giu nguyen gia tri.");

        System.out.print("Ma Tour hien tai (" + kht.getMaTour() + "): ");
        String input = sc.nextLine().trim();
        if (!input.isEmpty()) kht.setMaTour(input);

        System.out.print("Ma HDV hien tai (" + kht.getMaHDV() + "): ");
        input = sc.nextLine().trim();
        if (!input.isEmpty()) kht.setMaHDV(input);

        System.out.print("Tong so ve hien tai (" + kht.getTongSoVe() + "): ");
        input = sc.nextLine().trim();
        if (!input.isEmpty()) {
            try {
                kht.setTongSoVe(Integer.parseInt(input));
            } catch (NumberFormatException e) {
                System.out.println("So ve khong hop le → giu nguyen.");
            }
        }

        System.out.print("Ve da dat hien tai (" + kht.getTongVeDaDat() + "): ");
        input = sc.nextLine().trim();
        if (!input.isEmpty()) {
            try {
                kht.setTongVeDaDat(Integer.parseInt(input));
            } catch (NumberFormatException e) {
                System.out.println("So ve khong hop le → giu nguyen.");
            }
        }

        System.out.println("Chinh sua ke hoach tour thanh cong!");
    }

    // Tìm kiếm theo mã (trả về object)
    public KeHoachTour timTheoMaObject(String ma) {
        if (ma == null) return null;
        for (int i = 0; i < soLuongKHTour; i++) {
            if (list[i] != null && ma.equalsIgnoreCase(list[i].getMaKHTour())) {
                return list[i];
            }
        }
        return null;
    }

    // Tìm kiếm theo từ khóa (mã tour)
    public KeHoachTour[] timKiemTheoTen(String key) {
        if (key == null || key.trim().isEmpty()) return new KeHoachTour[0];

        String tuKhoa = key.trim().toLowerCase();
        int count = 0;
        for (int i = 0; i < soLuongKHTour; i++) {
            if (list[i] != null && list[i].getMaTour() != null &&
                list[i].getMaTour().toLowerCase().contains(tuKhoa)) {
                count++;
            }
        }

        KeHoachTour[] ketQua = new KeHoachTour[count];
        int idx = 0;
        for (int i = 0; i < soLuongKHTour; i++) {
            if (list[i] != null && list[i].getMaTour() != null &&
                list[i].getMaTour().toLowerCase().contains(tuKhoa)) {
                ketQua[idx++] = list[i];
            }
        }
        return ketQua;
    }

    // Tìm kiếm theo mã (in kết quả ra màn hình) — dùng bởi menu
    public void timKiemTheoMa(String ma, DSChiPhiKHTour dsChiPhi) {
        if (ma == null || ma.trim().isEmpty()) {
            System.out.println("Vui long nhap ma hop le.");
            return;
        }
        KeHoachTour k = timTheoMaObject(ma.trim());
        if (k == null) System.out.println("Khong tim thay ke hoach tour voi ma: " + ma);
        else k.hienThiThongTin(dsChiPhi);
    }

    // Thống kê tổng quan (cần truyền DSChiPhiKHTour)
    public void thongKe(DSChiPhiKHTour dsChiPhi) {
        if (soLuongKHTour == 0) {
            System.out.println("Chua co ke hoach tour nao de thong ke!");
            return;
        }

        double tongThuVe = 0;
        double tongChiPhi = 0;
        int tongVe = 0;
        int tongVeDaDat = 0;

        for (int i = 0; i < soLuongKHTour; i++) {
            KeHoachTour k = list[i];
            tongThuVe += k.getTongTienThuVe();
            tongChiPhi += k.getTongChiPhiAll(dsChiPhi);
            tongVe += k.getTongSoVe();
            tongVeDaDat += k.getTongVeDaDat();
        }

        System.out.println("================================ THỐNG KÊ KẾ HOẠCH TOUR ================================");
        System.out.printf("Tong so ke hoach tour       : %,d%n", soLuongKHTour);
        System.out.printf("TTong so ve kha dung         : %,d%n", tongVe);
        System.out.printf("TTong ve da dat              : %,d%n", tongVeDaDat);
        System.out.printf("TTong doanh thu tu ve        : %, .0f VND%n", tongThuVe);
        System.out.printf("TTong chi phi an o           : %, .0f VND%n", tongChiPhi);
        System.out.printf("LOI NHUAN DU KIEN           : %, .0f VND%n", (tongThuVe - tongChiPhi));
        System.out.println("========================================================================================");
    }

    // Đọc từ file (chỉ lưu thông tin cơ bản của kế hoạch tour)
    public void loadFromFile(String path) throws IOException {
        File f = new File(path);
        if (!f.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            soLuongKHTour = 0;
            String line;
            while ((line = br.readLine()) != null && soLuongKHTour < list.length) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] p = line.split(",");
                if (p.length >= 8) {
                    try {
                        KeHoachTour kht = new KeHoachTour(
                            p[0].trim(),
                            p[1].trim(),
                            p[2].trim(),
                            Integer.parseInt(p[3].trim()),
                            Double.parseDouble(p[4].trim()),
                            Integer.parseInt(p[5].trim()),
                            new Date(SDF.parse(p[6].trim()).getTime()),
                            new Date(SDF.parse(p[7].trim()).getTime())
                        );
                        list[soLuongKHTour++] = kht;
                    } catch (Exception e) {
                        System.out.println("Bo qua dong loi: " + line);
                    }
                }
            }
        }
    }

    // Ghi ra file (chỉ ghi thông tin cơ bản)
    public void saveToFile(String path) throws IOException {
        File f = new File(path);
        if (f.getParentFile() != null) f.getParentFile().mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
            for (int i = 0; i < soLuongKHTour; i++) {
                KeHoachTour k = list[i];
                if (k == null) continue;
                bw.write(String.join(",",
                    k.getMaKHTour(),
                    k.getMaTour(),
                    k.getMaHDV(),
                    String.valueOf(k.getTongSoVe()),
                    String.valueOf(k.getGiaVe()),
                    String.valueOf(k.getTongVeDaDat()),
                    SDF.format(k.getNgayDi()),
                    SDF.format(k.getNgayVe())
                ));
                bw.newLine();
            }
        }
    }
}