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

    // Tìm theo từ khóa (tìm trong mã KHTour) — trả về mảng kết quả
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
            System.out.println("Danh sách chi phí đã đầy (" + MAX + ")!");
            return;
        }

        System.out.print("Nhập mã kế hoạch tour (KHTour): ");
        String maKHTour = sc.nextLine().trim();
        if (maKHTour.isEmpty()) {
            System.out.println("Mã KHTour không được để trống!");
            return;
        }

        // Kiểm tra KHTour tồn tại
        if (dsKHTour != null && dsKHTour.timTheoMaObject(maKHTour) == null) {
            System.out.println("Mã KHTour '" + maKHTour + "' không tồn tại trong danh sách kế hoạch tour!");
            return;
        }

        // Kiểm tra đã có chi phí chưa
        if (findIndexByMa(maKHTour) != -1) {
            System.out.println("Đã tồn tại chi phí cho kế hoạch tour này! Vui lòng dùng chức năng chỉnh sửa.");
            return;
        }

        // Chọn khách sạn
        System.out.println("\n--- Danh sách khách sạn ---");
        dsKhachSan.xuatDanhSach();
        System.out.print("Nhập mã khách sạn: ");
        String maKS = sc.nextLine().trim();
        KhachSan ks = dsKhachSan.timTheoMa(maKS);
        if (ks == null) {
            System.out.println("Khách sạn không tồn tại!");
            return;
        }

        // Nhập ngày đến - đi
        Date ngayDen = nhapNgay("Nhập ngày đến (dd/MM/yyyy): ");
        Date ngayDi = nhapNgay("Nhập ngày đi (dd/MM/yyyy): ");
        if (ngayDen == null || ngayDi == null) return;

        if (!ngayDi.after(ngayDen)) {
            System.out.println("Ngày đi phải sau ngày đến!");
            return;
        }

        long soNgay = (ngayDi.getTime() - ngayDen.getTime()) / (1000 * 60 * 60 * 24) + 1;
        double tienPhong = soNgay * ks.getGiaDatPhong();

        // Chọn nhà hàng
        System.out.println("\n--- Danh sách nhà hàng ---");
        dsNhaHang.xuatDanhSach();
        System.out.print("Nhập mã nhà hàng: ");
        String maNH = sc.nextLine().trim();
        NhaHang nh = dsNhaHang.timTheoMa(maNH);
        if (nh == null) {
            System.out.println("Nhà hàng không tồn tại!");
            return;
        }

        System.out.print("Nhập số lượng combo ăn: ");
        int soCombo = 0;
        try {
            soCombo = Integer.parseInt(sc.nextLine().trim());
            if (soCombo < 0) soCombo = 0;
        } catch (Exception e) {
            System.out.println("Số lượng không hợp lệ → mặc định 0");
        }
        double tienAn = soCombo * nh.getGiaCombo();

        // Tạo và thêm
        ChiPhiKHTour cp = new ChiPhiKHTour(maKHTour, maNH, maKS, tienPhong, tienAn);
        dsChiPhi[soLuong++] = cp;

        System.out.println("=== THÊM CHI PHÍ THÀNH CÔNG! ===");
        cp.xuatThongTin();
    }

    // ==================== CHỈNH SỬA CHI PHÍ ====================
    public boolean chinhSuaTheoMa(String ma) {
        int idx = findIndexByMa(ma);
        if (idx == -1) {
            System.out.println("Không tìm thấy chi phí cho mã KHTour: " + ma);
            return false;
        }

        ChiPhiKHTour cp = dsChiPhi[idx];
        KhachSan ks = dsKhachSan.timTheoMa(cp.getMaKhachSan());
        NhaHang nh = dsNhaHang.timTheoMa(cp.getMaNhaHang());

        System.out.println("\n--- CHỈNH SỬA CHI PHÍ KẾ HOẠCH TOUR ---");
        System.out.println("Để trống và nhấn Enter nếu muốn giữ nguyên.");

        // Sửa khách sạn
        System.out.printf("Mã khách sạn hiện tại (%s): ", cp.getMaKhachSan());
        String newKS = sc.nextLine().trim();
        if (!newKS.isEmpty()) {
            KhachSan ksMoi = dsKhachSan.timTheoMa(newKS);
            if (ksMoi != null) {
                ks = ksMoi;
                cp.setMaKhachSan(newKS);
            } else {
                System.out.println("Khách sạn không tồn tại → giữ nguyên.");
            }
        }

        // Cập nhật ngày → tính lại tiền phòng
        System.out.print("Cập nhật ngày đến/di để tính lại tiền phòng? (y/n): ");
        if (sc.nextLine().trim().equalsIgnoreCase("y")) {
            Date d1 = nhapNgay("Nhập ngày đến mới (dd/MM/yyyy): ");
            Date d2 = nhapNgay("Nhập ngày đi mới (dd/MM/yyyy): ");
            if (d1 != null && d2 != null && d2.after(d1)) {
                long soNgay = (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24) + 1;
                if (ks != null) {
                    cp.setTongTienPhong(soNgay * ks.getGiaDatPhong());
                    System.out.printf("→ Cập nhật tiền phòng: %, .0f VND (%d đêm)%n", cp.getTongTienPhong(), soNgay);
                }
            }
        }

        // Sửa nhà hàng
        System.out.printf("Mã nhà hàng hiện tại (%s): ", cp.getMaNhaHang());
        String newNH = sc.nextLine().trim();
        if (!newNH.isEmpty()) {
            NhaHang nhMoi = dsNhaHang.timTheoMa(newNH);
            if (nhMoi != null) {
                nh = nhMoi;
                cp.setMaNhaHang(newNH);
            } else {
                System.out.println("Nhà hàng không tồn tại → giữ nguyên.");
            }
        }

        // Sửa số combo
        double comboHienTai = nh != null && nh.getGiaCombo() > 0 ? cp.getTongTienAn() / nh.getGiaCombo() : 0;
        System.out.printf("Số combo hiện tại (%.0f combo → %, .0f VND): ", comboHienTai, cp.getTongTienAn());
        String input = sc.nextLine().trim();
        if (!input.isEmpty()) {
            try {
                int soCombo = Integer.parseInt(input);
                if (soCombo >= 0 && nh != null) {
                    cp.setTongTienAn(soCombo * nh.getGiaCombo());
                }
            } catch (Exception e) {
                System.out.println("Số không hợp lệ → giữ nguyên.");
            }
        }

        System.out.println("Chỉnh sửa chi phí thành công!");
        return true;
    }

    // ==================== XÓA CHI PHÍ ====================
    public boolean xoaTheoMa(String ma) {
        int idx = findIndexByMa(ma);
        if (idx == -1) {
            System.out.println("Không tìm thấy chi phí cho mã: " + ma);
            return false;
        }
        for (int i = idx; i < soLuong - 1; i++) {
            dsChiPhi[i] = dsChiPhi[i + 1];
        }
        dsChiPhi[--soLuong] = null;
        System.out.println("Xóa chi phí thành công!");
        return true;
    }

    // ==================== HIỂN THỊ DANH SÁCH ====================
    public void xuatDanhSach() {
        if (soLuong == 0) {
            System.out.println("Chưa có chi phí kế hoạch tour nào.");
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

                    dsChiPhi[soLuong++] = new ChiPhiKHTour(ma, maNH, maKS, tienPhong, tienAn);
                    dem++;
                } catch (Exception e) {
                    System.out.println("Bỏ qua dòng lỗi: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi đọc file chi phí: " + e.getMessage());
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
                    String.valueOf(cp.getTongTienPhong())
                ));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi ghi file chi phí: " + e.getMessage());
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
                System.out.println("Không được để trống!");
                continue;
            }
            try {
                java.util.Date d = SDF.parse(s);
                return new Date(d.getTime());
            } catch (ParseException e) {
                System.out.println("Định dạng ngày không đúng! Vui lòng nhập theo dd/MM/yyyy");
            }
        }
    }

    // ==================== THỐNG KÊ ĐƠN GIẢN ====================
    // In ra tổng số bản ghi, tổng tiền ăn, tổng tiền phòng
    public void thongKeDonGian() {
        if (soLuong == 0) {
            System.out.println("Chưa có chi phí kế hoạch tour để thống kê.");
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
        System.out.println("===== THỐNG KÊ CHI PHÍ KẾ HOẠCH TOUR (ĐƠN GIẢN) =====");
        System.out.println("Số bản ghi: " + soLuong);
        System.out.printf("Tổng tiền ăn : %, .0f VND%n", tongAn);
        System.out.printf("Tổng tiền phòng: %, .0f VND%n", tongPhong);
        System.out.printf("Tổng chi phí  : %, .0f VND%n", (tongAn + tongPhong));
        System.out.println("=====================================================");
    }
}