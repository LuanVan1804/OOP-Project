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

    public DSKHTour(DSTour dsTour, DSHDV dsHDV) {
        this();
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
            System.out.println("Danh sách kế hoạch tour đã đầy!");
            return;
        }

        KeHoachTour kht = new KeHoachTour();
        kht.nhapThongTin();

        // Kiểm tra mã trùng
        while (!isMaKHTourUnique(kht.getMaKHTour())) {
            System.out.print("Mã KHTour đã tồn tại! Nhập lại: ");
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
                System.out.println("Mã Tour không tồn tại trong danh sách tour!");
                return;
            }
        }

        // Validate mã hướng dẫn viên
        if (dsHDV != null && !kht.getMaHDV().trim().isEmpty()) {
            try {
                int maHDV = Integer.parseInt(kht.getMaHDV().trim());
                if (dsHDV.timTheoMa(maHDV) == null) {
                    System.out.println("Mã HDV không tồn tại!");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Mã HDV phải là số nguyên!");
                return;
            }
        }

        list[soLuongKHTour++] = kht;
        System.out.println("Thêm kế hoạch tour thành công!");
    }

    // Hiển thị danh sách (cần truyền DSChiPhiKHTour để lấy chi phí)
    public void hienThiDanhSachKHTour(DSChiPhiKHTour dsChiPhi) {
        if (soLuongKHTour == 0) {
            System.out.println("Danh sách kế hoạch tour trống!");
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
            System.out.println("Không tìm thấy kế hoạch tour với mã: " + maKHTour);
            return false;
        }

        // Dồn mảng
        for (int i = index; i < soLuongKHTour - 1; i++) {
            list[i] = list[i + 1];
        }
        list[soLuongKHTour - 1] = null;
        soLuongKHTour--;
        System.out.println("Xóa kế hoạch tour thành công!");
        return true;
    }

    // Chỉnh sửa kế hoạch tour (chỉ sửa thông tin cơ bản, không sửa chi phí)
    public void chinhSuaKHTour(String maKHTour) {
        KeHoachTour kht = timTheoMaObject(maKHTour);
        if (kht == null) {
            System.out.println("Không tìm thấy kế hoạch tour với mã: " + maKHTour);
            return;
        }

        System.out.println("Để trống và nhấn Enter nếu muốn giữ nguyên giá trị.");

        System.out.print("Mã Tour hiện tại (" + kht.getMaTour() + "): ");
        String input = sc.nextLine().trim();
        if (!input.isEmpty()) kht.setMaTour(input);

        System.out.print("Mã HDV hiện tại (" + kht.getMaHDV() + "): ");
        input = sc.nextLine().trim();
        if (!input.isEmpty()) kht.setMaHDV(input);

        System.out.print("Tổng số vé hiện tại (" + kht.getTongSoVe() + "): ");
        input = sc.nextLine().trim();
        if (!input.isEmpty()) {
            try {
                kht.setTongSoVe(Integer.parseInt(input));
            } catch (NumberFormatException e) {
                System.out.println("Số vé không hợp lệ → giữ nguyên.");
            }
        }

        System.out.print("Vé đã đặt hiện tại (" + kht.getTongVeDaDat() + "): ");
        input = sc.nextLine().trim();
        if (!input.isEmpty()) {
            try {
                kht.setTongVeDaDat(Integer.parseInt(input));
            } catch (NumberFormatException e) {
                System.out.println("Số vé không hợp lệ → giữ nguyên.");
            }
        }

        System.out.println("Chỉnh sửa kế hoạch tour thành công!");
        System.out.println("Lưu ý: Chi phí ăn ở được quản lý riêng trong menu 'Chi phí kế hoạch tour'.");
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
            System.out.println("Vui lòng nhập mã hợp lệ.");
            return;
        }
        KeHoachTour k = timTheoMaObject(ma.trim());
        if (k == null) System.out.println("Khong tim thay ke hoach tour voi ma: " + ma);
        else k.hienThiThongTin(dsChiPhi);
    }

    // Thống kê tổng quan (cần truyền DSChiPhiKHTour)
    public void thongKe(DSChiPhiKHTour dsChiPhi) {
        if (soLuongKHTour == 0) {
            System.out.println("Chưa có kế hoạch tour nào để thống kê!");
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
        System.out.printf("Tổng số kế hoạch tour       : %,d%n", soLuongKHTour);
        System.out.printf("Tổng số vé khả dụng         : %,d%n", tongVe);
        System.out.printf("Tổng vé đã đặt              : %,d%n", tongVeDaDat);
        System.out.printf("Tổng doanh thu từ vé        : %, .0f VND%n", tongThuVe);
        System.out.printf("Tổng chi phí ăn ở           : %, .0f VND%n", tongChiPhi);
        System.out.printf("LỢI NHUẬN DỰ KIẾN           : %, .0f VND%n", (tongThuVe - tongChiPhi));
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
                        System.out.println("Bỏ qua dòng lỗi: " + line);
                    }
                }
            }
        }
        System.out.println("Tải dữ liệu kế hoạch tour thành công từ: " + path);
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
        System.out.println("Lưu dữ liệu kế hoạch tour thành công vào: " + path);
    }
}