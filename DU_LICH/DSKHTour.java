package DU_LICH;

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

import DU_LICH.TourDuLich.DSTour;
import DU_LICH.Nguoi.DSHDV;

public class DSKHTour {
    private KeHoachTour[] list;
    private int soLuongKHTour;
    private static final Scanner sc = new Scanner(System.in);
    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private static final SimpleDateFormat SDF = new SimpleDateFormat(DATE_PATTERN, new Locale("vi", "VN"));

    // Tham chiếu tới các DS khác để kiểm tra tính hợp lệ khi thêm/sửa
    private DSTour dsTour;
    private DSHDV dsHDV;
    private DU_LICH.NH_KS_PT.DSNhaHang dsNhaHang;
    private DU_LICH.NH_KS_PT.DSKhachSan dsKhachSan;

    public DSKHTour() { // Hoặc DSKHTour(DSTour dsTour, ...) để pass
        this.list = new KeHoachTour[50];
        this.soLuongKHTour = 0;
        // Khởi tạo các ds khác nếu cần
    }

    // Constructor có tham chiếu để validate
    public DSKHTour(DSTour dsTour, DSHDV dsHDV, DU_LICH.NH_KS_PT.DSNhaHang dsNhaHang, DU_LICH.NH_KS_PT.DSKhachSan dsKhachSan) {
        this();
        this.dsTour = dsTour;
        this.dsHDV = dsHDV;
        this.dsNhaHang = dsNhaHang;
        this.dsKhachSan = dsKhachSan;
    }

    public KeHoachTour[] getList() {
        return list;
    }

    public int getSoLuongKHTour() {
        return soLuongKHTour;
    }

    // Kiểm tra mã KHTour có duy nhất không
    private boolean isMaKHTourUnique(String maKHTour) {
        if (maKHTour == null) return false;
        for (int i = 0; i < soLuongKHTour; i++) {
            if (list[i] != null && maKHTour.equals(list[i].getMaKHTour()))
                return false;
        }
        return true;
    }

    // Thêm kế hoạch tour
    public void themKHTour() {
        if (soLuongKHTour >= list.length) {
            System.out.println("Danh sach day!");
            return;
        }

        KeHoachTour kht = new KeHoachTour();
        kht.nhapThongTin();

        while (!isMaKHTourUnique(kht.getMaKHTour())) {
            System.out.print("Ma KHTour da ton tai! Nhap lai: ");
            kht.setMaKHTour(sc.nextLine());
        }

        // Kiểm tra tồn tại (nếu các DS được gán)
        if (this.dsTour != null) {
            boolean found = false;
            if (dsTour.getList() != null) {
                for (DU_LICH.TourDuLich.Tour t : dsTour.getList()) {
                    if (t != null && t.getMaTour() != null && t.getMaTour().equals(kht.getMaTour())) { found = true; break; }
                }
            }
            if (!found) { System.out.println("Ma Tour khong ton tai!"); return; }
        }

        if (this.dsHDV != null) {
            String maHDVStr = kht.getMaHDV();
            try {
                int maHDV = Integer.parseInt(maHDVStr.trim());
                if (dsHDV.timTheoMa(maHDV) == null) { System.out.println("Ma HDV khong ton tai!"); return; }
            } catch (Exception ex) {
                System.out.println("Ma HDV khong hop le!"); return;
            }
        }

        if (this.dsNhaHang != null) {
            String maNH = kht.getChiPhi() != null ? kht.getChiPhi().getMaNhaHang() : null;
            if (maNH != null && dsNhaHang.timTheoMa(maNH) == null) { System.out.println("Ma Nha Hang khong ton tai!"); return; }
        }

        if (this.dsKhachSan != null) {
            String maKS = kht.getChiPhi() != null ? kht.getChiPhi().getMaKhachSan() : null;
            if (maKS != null && dsKhachSan.timTheoMa(maKS) == null) { System.out.println("Ma Khach San khong ton tai!"); return; }
        }

        // Lấy giá vé từ DSTour (nếu có)
        if (this.dsTour != null) {
            boolean found = false;
            if (dsTour.getList() != null) {
                for (DU_LICH.TourDuLich.Tour t : dsTour.getList()) {
                    if (t != null && t.getMaTour() != null && t.getMaTour().equals(kht.getMaTour())) {
                        kht.setGiaVe(t.getDonGia());
                        found = true;
                        break;
                    }
                }
            }
            if (!found) {
                System.out.println("Canh bao: Khong tim thay Tour de lay don gia. Gia ve se mac dinh = 0.");
            }
        }

        list[soLuongKHTour++] = kht;
        System.out.println("Them ke hoach tour thanh cong!");
    }

    // Hiển thị danh sách kế hoạch tour
    public void hienThiDanhSachKHTour() {
        if (soLuongKHTour == 0) {
            System.out.println("Danh sach ke hoach tour rong!");
            return;
        }
        System.out.println("Danh sach ke hoach tour:");
        for (int i = 0; i < soLuongKHTour; i++) {
            System.out.println("---------------------------");
            list[i].hienThiThongTin();
        }
    }

    // Xóa kế hoạch theo mã
    public boolean xoaKHTour(String maKHTour) {
        int index = -1;
        for (int i = 0; i < soLuongKHTour; i++) {
            if (list[i] != null && maKHTour.equals(list[i].getMaKHTour())) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            System.out.println("Khong tim thay ke hoach tour voi ma " + maKHTour);
            return false;
        }
        for (int i = index; i < soLuongKHTour - 1; i++)
            list[i] = list[i + 1];
        list[soLuongKHTour - 1] = null;
        soLuongKHTour--;
        System.out.println("Xoa ke hoach tour thanh cong!");
        return true;
    }

    // Chỉnh sửa kế hoạch theo mã
    public void chinhSuaKHTour(String maKHTour) {
        int index = -1;
        for (int i = 0; i < soLuongKHTour; i++) {
            if (list[i] != null && maKHTour.equals(list[i].getMaKHTour())) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            System.out.println("Khong tim thay ke hoach tour voi ma " + maKHTour);
            return;
        }

        KeHoachTour kht = list[index];
        System.out.println("De trong va nhan Enter neu muon giu nguyen.");

        System.out.print("Ma Tour hien tai (" + kht.getMaTour() + "): ");
        String maTourNew = sc.nextLine();
        if (!maTourNew.trim().isEmpty()) kht.setMaTour(maTourNew.trim());

        System.out.print("Ma HDV hien tai (" + kht.getMaHDV() + "): ");
        String maHDVNew = sc.nextLine();
        if (!maHDVNew.trim().isEmpty()) kht.setMaHDV(maHDVNew.trim());

        System.out.print("Tong So Ve hien tai (" + kht.getTongSoVe() + "): ");
        String tongSoVeStr = sc.nextLine();
        if (!tongSoVeStr.trim().isEmpty()) {
            try { kht.setTongSoVe(Integer.parseInt(tongSoVeStr.trim())); } catch (NumberFormatException e) { System.out.println("So khong hop le, giu nguyen."); }
        }

        System.out.print("Gia Ve hien tai (" + kht.getGiaVe() + "): ");
        String giaVeStr = sc.nextLine();
        if (!giaVeStr.trim().isEmpty()) {
            try { kht.setGiaVe(Double.parseDouble(giaVeStr.trim())); } catch (NumberFormatException e) { System.out.println("Gia khong hop le, giu nguyen."); }
        }

        System.out.print("Tong Ve Da Dat hien tai (" + kht.getTongVeDaDat() + "): ");
        String tongVeDaDatStr = sc.nextLine();
        if (!tongVeDaDatStr.trim().isEmpty()) {
            try { kht.setTongVeDaDat(Integer.parseInt(tongVeDaDatStr.trim())); } catch (NumberFormatException e) { System.out.println("So khong hop le, giu nguyen."); }
        }

        System.out.print("Ma Nha Hang hien tai (" + kht.getChiPhi().getMaNhaHang() + "): ");
        String maNhaHangNew = sc.nextLine();
        if (!maNhaHangNew.trim().isEmpty()) kht.getChiPhi().setMaNhaHang(maNhaHangNew.trim());

        System.out.print("Ma Khach San hien tai (" + kht.getChiPhi().getMaKhachSan() + "): ");
        String maKhachSanNew = sc.nextLine();
        if (!maKhachSanNew.trim().isEmpty()) kht.getChiPhi().setMaKhachSan(maKhachSanNew.trim());

        System.out.print("Tong Tien An hien tai (" + kht.getChiPhi().getTongTienAn() + "): ");
        String tongTienAnStr = sc.nextLine();
        if (!tongTienAnStr.trim().isEmpty()) {
            try { kht.getChiPhi().setTongTienAn(Double.parseDouble(tongTienAnStr.trim())); } catch (NumberFormatException e) { System.out.println("Gia khong hop le, giu nguyen."); }
        }

        System.out.print("Tong Tien Phong hien tai (" + kht.getChiPhi().getTongTienPhong() + "): ");
        String tongTienPhongStr = sc.nextLine();
        if (!tongTienPhongStr.trim().isEmpty()) {
            try { kht.getChiPhi().setTongTienPhong(Double.parseDouble(tongTienPhongStr.trim())); } catch (NumberFormatException e) { System.out.println("Gia khong hop le, giu nguyen."); }
        }

        System.out.print("Ngay Di hien tai (" + SDF.format(new java.util.Date(kht.getNgayDi().getTime())) + ") [dd/MM/yyyy]: ");
        String ngayDiStr = sc.nextLine();
        if (!ngayDiStr.trim().isEmpty()) {
            try { kht.setNgayDi(new Date(SDF.parse(ngayDiStr.trim()).getTime())); } catch (ParseException e) { System.out.println("Ngay khong hop le, giu nguyen."); }
        }

        System.out.print("Ngay Ve hien tai (" + SDF.format(new java.util.Date(kht.getNgayVe().getTime())) + ") [dd/MM/yyyy]: ");
        String ngayVeStr = sc.nextLine();
        if (!ngayVeStr.trim().isEmpty()) {
            try {
                Date newNgayVe = new Date(SDF.parse(ngayVeStr.trim()).getTime());
                if (newNgayVe.after(kht.getNgayDi())) {
                    kht.setNgayVe(newNgayVe);
                } else {
                    System.out.println("Ngay ve phai sau ngay di, giu nguyen.");
                }
            } catch (ParseException e) { System.out.println("Ngay khong hop le, giu nguyen."); }
        }

        // Kiểm tra tồn tại sau chỉnh sửa nếu cần

        System.out.println("Chinh sua ke hoach tour thanh cong!");
    }

    // Tìm kiếm theo tên (giả sử theo maTour như "tên")
    public KeHoachTour[] timKiemTheoTen(String key) {
        if (key == null || key.trim().isEmpty()) {
            return new KeHoachTour[0];
        }

        String tuKhoa = key.trim().toLowerCase();
        int count = 0;

        for (int i = 0; i < soLuongKHTour; i++) {
            String tenTour = list[i].getMaTour();
            if (tenTour != null && tenTour.toLowerCase().contains(tuKhoa)) {
                count++;
            }
        }

        KeHoachTour[] ketQua = new KeHoachTour[count];
        int index = 0;

        for (int i = 0; i < soLuongKHTour; i++) {
            String tenTour = list[i].getMaTour();
            if (tenTour != null && tenTour.toLowerCase().contains(tuKhoa)) {
                ketQua[index++] = list[i];
            }
        }

        return ketQua;
    }

    // Tìm kiếm theo mã
    public void timKiemTheoMa(String ma) {
        for (int i = 0; i < soLuongKHTour; i++) {
            if (list[i].getMaKHTour() != null && list[i].getMaKHTour().equals(ma)) {
                list[i].hienThiThongTin();
                return;
            }
        }
        System.out.println("Khong tim thay ke hoach tour voi ma " + ma);
    }

    // Trả về đối tượng KeHoachTour theo mã (hữu ích cho các thao tác khác như DSChiPhiKHTour)
    public KeHoachTour timTheoMaObject(String ma) {
        if (ma == null) return null;
        for (int i = 0; i < soLuongKHTour; i++) {
            if (list[i] != null && ma.equalsIgnoreCase(list[i].getMaKHTour())) {
                return list[i];
            }
        }
        return null;
    }

    // Thống kê đơn giản
    public void thongKe() {
        if (soLuongKHTour == 0) {
            System.out.println("Danh sach ke hoach tour rong!");
            return;
        }

        double tongThuVe = 0.0, tongChiPhi = 0.0;
        int tongVe = 0, tongVeDaDat = 0;

        for (int i = 0; i < soLuongKHTour; i++) {
            KeHoachTour k = list[i];
            tongThuVe += k.getTongTienThuVe();
            tongChiPhi += k.getTongChiPhiAll();
            tongVe += k.getTongSoVe();
            tongVeDaDat += k.getTongVeDaDat();
        }

        System.out.println("=== THONG KE KE HOACH TOUR ===");
        System.out.printf("Tong so ke hoach: %,d\n", soLuongKHTour);
        System.out.printf("Tong ve: %,d | Ve da dat: %,d\n", tongVe, tongVeDaDat);
        System.out.printf("Tong thu ve: %, .0f\n", tongThuVe);
        System.out.printf("Tong chi phi: %, .0f\n", tongChiPhi);
        System.out.printf("Loi nhuan: %, .0f\n", (tongThuVe - tongChiPhi));
    }

    // Đọc từ file
    public void loadFromFile(String path) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            soLuongKHTour = 0;
            String line;
            while ((line = br.readLine()) != null && soLuongKHTour < list.length) {
                if (line.trim().isEmpty()) continue;
                String[] p = line.split(",");
                try {
                    if (p.length == 12) {
                        KeHoachTour kht = new KeHoachTour(
                                p[0].trim(),
                                p[1].trim(),
                                p[2].trim(),
                                Integer.parseInt(p[3].trim()),
                                Double.parseDouble(p[4].trim()),
                                Integer.parseInt(p[5].trim()),
                                new Date(SDF.parse(p[6].trim()).getTime()),
                                new Date(SDF.parse(p[7].trim()).getTime()),
                                p[8].trim(),
                                p[9].trim(),
                                Double.parseDouble(p[10].trim()),
                                Double.parseDouble(p[11].trim())
                        );
                        list[soLuongKHTour++] = kht;
                    } else {
                        System.out.println("Bo qua dong khong hop le: " + line);
                    }
                } catch (Exception ex) {
                    System.out.println("Loi phan tich dong: " + line + " -> " + ex.getMessage());
                }
            }
        }
    }

    /**
     * Load từ file và ngay lập tức kiểm tra/validate liên kết với DSTour và DSHDV.
     * Nếu tham số dst hoặc dshdv là null, chỉ đọc mà không kiểm tra.
     */
    public void loadFromFile(String path, DSTour dst, DSHDV dshdv) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            soLuongKHTour = 0;
            String line;
            while ((line = br.readLine()) != null && soLuongKHTour < list.length) {
                if (line.trim().isEmpty()) continue;
                String[] p = line.split(",");
                try {
                    if (p.length == 12) {
                        KeHoachTour kht = new KeHoachTour(
                                p[0].trim(),
                                p[1].trim(),
                                p[2].trim(),
                                Integer.parseInt(p[3].trim()),
                                Double.parseDouble(p[4].trim()),
                                Integer.parseInt(p[5].trim()),
                                new Date(SDF.parse(p[6].trim()).getTime()),
                                new Date(SDF.parse(p[7].trim()).getTime()),
                                p[8].trim(),
                                p[9].trim(),
                                Double.parseDouble(p[10].trim()),
                                Double.parseDouble(p[11].trim())
                        );

                        // Validate liên kết với DSTour
                        if (dst != null) {
                            boolean found = false;
                            if (dst.getList() != null) {
                                for (DU_LICH.TourDuLich.Tour t : dst.getList()) {
                                    if (t != null && t.getMaTour() != null && t.getMaTour().equals(kht.getMaTour())) { found = true; break; }
                                }
                            }
                            if (!found) System.out.println("Canh bao: Ma tour '" + kht.getMaTour() + "' khong ton tai trong DSTour.");
                        }

                        // Validate lien ket voi DSHDV (maHDV duoc luu la String — parse sang int de tim)
                        if (dshdv != null) {
                            String maHDVStr = kht.getMaHDV();
                            try {
                                int maHDV = Integer.parseInt(maHDVStr.trim());
                                if (dshdv.timTheoMa(maHDV) == null) {
                                    System.out.println("Canh bao: Ma HDV '" + maHDVStr + "' khong ton tai trong DSHDV.");
                                }
                            } catch (Exception ex) {
                                if (maHDVStr != null && !maHDVStr.trim().isEmpty())
                                    System.out.println("Canh bao: Ma HDV '" + maHDVStr + "' khong phai so nguyen.");
                            }
                        }

                        list[soLuongKHTour++] = kht;
                    } else {
                        System.out.println("Bo qua dong khong hop le: " + line);
                    }
                } catch (Exception ex) {
                    System.out.println("Loi phan tich dong: " + line + " -> " + ex.getMessage());
                }
            }
        }
    }

    // Ghi vào file
    public void saveToFile(String path) throws IOException {
        File f = new File(path);
        if (f.getParentFile() != null) f.getParentFile().mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
            for (int i = 0; i < soLuongKHTour; i++) {
                KeHoachTour k = list[i];
                bw.write(String.join(",",
                        k.getMaKHTour(),
                        k.getMaTour(),
                        k.getMaHDV(),
                        String.valueOf(k.getTongSoVe()),
                        String.valueOf(k.getGiaVe()),
                        String.valueOf(k.getTongVeDaDat()),
                        SDF.format(new java.util.Date(k.getNgayDi().getTime())),
                        SDF.format(new java.util.Date(k.getNgayVe().getTime())),
                        k.getChiPhi().getMaNhaHang(),
                        k.getChiPhi().getMaKhachSan(),
                        String.valueOf(k.getChiPhi().getTongTienAn()),
                        String.valueOf(k.getChiPhi().getTongTienPhong())
                ));
                bw.newLine();
            }
        }
    }
}