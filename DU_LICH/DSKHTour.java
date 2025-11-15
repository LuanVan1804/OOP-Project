package DU_LICH;

import java.io.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Scanner;

public class DSKHTour {
    private KeHoachTour[] list;
    private int soLuongKHTour;
    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private static final SimpleDateFormat SDF = new SimpleDateFormat(DATE_PATTERN, new Locale("vi", "VN"));

    public DSKHTour() {
        this.list = new KeHoachTour[50];
        this.soLuongKHTour = 0;
    }

    public KeHoachTour[] getList() {
        return list;
    }

    public int getSoLuongKHTour() {
        return soLuongKHTour;
    }

    // Kiểm tra mã kế hoạch tour có bị trùng không (chỉ nội bộ)
    private boolean isMaKHTourUnique(String maKHTour) {
        if (maKHTour == null || maKHTour.trim().isEmpty()) return false;
        for (int i = 0; i < soLuongKHTour; i++) {
            if (list[i] != null && maKHTour.equalsIgnoreCase(list[i].getMaKHTour())) {
                return false;
            }
        }
        return true;
    }

    // Thêm kế hoạch tour (chỉ thêm, không validate ngoại lệ)
    public boolean themKHTour(KeHoachTour kht) {
        if (soLuongKHTour >= list.length) {
            System.out.println("Danh sách kế hoạch tour đã đầy!");
            return false;
        }
        if (kht == null || kht.getMaKHTour() == null || !isMaKHTourUnique(kht.getMaKHTour())) {
            System.out.println("Mã kế hoạch tour không hợp lệ hoặc đã tồn tại!");
            return false;
        }
        list[soLuongKHTour++] = kht;
        System.out.println("Thêm kế hoạch tour thành công!");
        return true;
    }

    // Hiển thị danh sách
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

    // Chỉnh sửa kế hoạch tour
    public void chinhSuaKHTour(String maKHTour) {
        Scanner sc = new Scanner(System.in);
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

    public void timKiemTheoMa(String ma, DSChiPhiKHTour dsChiPhi) {
        if (ma == null || ma.trim().isEmpty()) {
            System.out.println("Vui lòng nhập mã hợp lệ.");
            return;
        }
        KeHoachTour k = timTheoMaObject(ma.trim());
        if (k == null) System.out.println("Không tìm thấy kế hoạch tour với mã: " + ma);
        else k.hienThiThongTin(dsChiPhi);
    }

    public void thongKe(DSChiPhiKHTour dsChiPhi) {
        if (soLuongKHTour == 0) {
            System.out.println("Chưa có kế hoạch tour nào để thống kê!");
            return;
        }

        double tongThuVe = 0, tongChiPhi = 0;
        int tongVe = 0, tongVeDaDat = 0;

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