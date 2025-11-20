package DU_LICH.DanhSach;

import java.io.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

import DU_LICH.ClassDon.KeHoachTour;
import DU_LICH.QuanLy.QuanLy;

public class DSKHTour {
    private KeHoachTour[] list;
    private int soLuong;
    private static final int MAX = 100;
    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private static final SimpleDateFormat SDF = new SimpleDateFormat(DATE_PATTERN, new Locale("vi", "VN"));

    public DSKHTour() {
        list = new KeHoachTour[MAX];
        soLuong = 0;
    }

    public int getSoLuong() { return soLuong; }
    public KeHoachTour[] getList() { return list; }

    // Kiểm tra trùng mã
    private boolean isMaUnique(String ma) {
        for (int i = 0; i < soLuong; i++) {
            if (list[i].getMaKHTour().equalsIgnoreCase(ma)) return false;
        }
        return true;
    }

    // 1. Thêm
    public boolean them(KeHoachTour k) {
        if (soLuong >= MAX || k == null || !isMaUnique(k.getMaKHTour())) {
            return false;
        }
        list[soLuong++] = k;
        return true;
    }

    // 2. Xóa theo mã
    public boolean xoa(String ma) {
        for (int i = 0; i < soLuong; i++) {
            if (list[i].getMaKHTour().equalsIgnoreCase(ma)) {
                for (int j = i; j < soLuong - 1; j++) {
                    list[j] = list[j + 1];
                }
                list[--soLuong] = null;
                return true;
            }
        }
        return false;
    }

    // 3. Tìm theo mã (trả về object)
    public KeHoachTour timTheoMa(String ma) {
        for (int i = 0; i < soLuong; i++) {
            if (list[i].getMaKHTour().equalsIgnoreCase(ma)) {
                return list[i];
            }
        }
        return null;
    }

    // 4. Tìm theo tên kế hoạch (trả về mảng)
    public KeHoachTour[] timTheoTen(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return new KeHoachTour[0];
        keyword = keyword.toLowerCase().trim();
        int count = 0;
        for (int i = 0; i < soLuong; i++) {
            if (list[i].getTenKeHoach().toLowerCase().contains(keyword)) count++;
        }
        KeHoachTour[] kq = new KeHoachTour[count];
        int idx = 0;
        for (int i = 0; i < soLuong; i++) {
            if (list[i].getTenKeHoach().toLowerCase().contains(keyword)) {
                kq[idx++] = list[i];
            }
        }
        return kq;
    }

    // 5. Hiển thị toàn bộ
    public void hienThiDanhSach() {
    if (soLuong == 0) {
        System.out.println("Danh sach trong!");
        return;
    }
    // CẬP NHẬT CHI PHÍ + VÉ ĐÃ ĐẶT CHO TẤT CẢ KẾ HOẠCH
    for (int i = 0; i < soLuong; i++) {
        list[i].capNhatTongChi(QuanLy.getDsChiPhi());
        list[i].capNhatVeDaDatVaDoanhThu(QuanLy.getDsHoaDon());
    }

    System.out.println("════════════════════════════════════════════════════════════════════════════════════════");
    System.out.printf("%-10s | %-20s | %-10s | %-23s | %-12s | %-12s | %-14s%n",
            "Ma KH", "Ten Ke Hoach", "Ma Tour", "Ngay Di - Ve", "Ve Dat/Tong", "Doanh Thu", "Loi Nhuan");
    System.out.println("════════════════════════════════════════════════════════════════════════════════════════");
    for (int i = 0; i < soLuong; i++) {
        list[i].hienThi();
    }
    System.out.println("════════════════════════════════════════════════════════════════════════════════════════");
}

    // 6. Thống kê đơn giản
    public void thongKe() {
        for (int i = 0; i < soLuong; i++) {
            list[i].capNhatTongChi(QuanLy.getDsChiPhi());
        }

        if (soLuong == 0) {
            System.out.println("Khong co du lieu thong ke!");
            return;
        }
        double tongDoanhThu = 0, tongChi = 0;
        int tongVe = 0, tongVeDat = 0;

        for (int i = 0; i < soLuong; i++) {
            KeHoachTour k = list[i];
            double thu = k.getTongVeDaDat() * k.getGiaVe();
            tongDoanhThu += thu;
            tongChi += k.getTongChi();
            tongVe += k.getTongSoVe();
            tongVeDat += k.getTongVeDaDat();
        }

        System.out.println("================================ THONG KE KE HOACH TOUR ===============================");
        System.out.printf("So ke hoach tour      : %,d%n", soLuong);
        System.out.printf("Tong so ve            : %,d%n", tongVe);
        System.out.printf("Tong ve da dat        : %,d%n", tongVeDat);
        System.out.printf("Tong doanh thu        : %,12.0f VND%n", tongDoanhThu);
        System.out.printf("Tong chi phi          : %,12.0f VND%n", tongChi);
        System.out.printf("LOI NHUAN DU KIEN     : %,12.0f VND%n", tongDoanhThu - tongChi);
        System.out.println("======================================================================================");
    }

    // 7. Ghi file
    public void saveToFile(String path) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (int i = 0; i < soLuong; i++) {
                bw.write(list[i].toString());
                bw.newLine();
            }
        }
    }

    // 8. Đọc file
    public void loadFromFile(String path) throws IOException {

        soLuong = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null && soLuong < MAX) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] p = line.split(",", -1); // -1 để giữ phần tử rỗng
                if (p.length >= 8) {
                    try {
                        KeHoachTour k = new KeHoachTour(
                            p[0].trim(),
                            p[1].trim(),
                            p[2].trim(),
                            p[3].trim(),
                            Integer.parseInt(p[4].trim()),
                            Double.parseDouble(p[5].trim()),
                            new Date(SDF.parse(p[6].trim()).getTime()),
                            new Date(SDF.parse(p[7].trim()).getTime())
                        );
                        list[soLuong++] = k;
                    } catch (Exception e) {
                        System.out.println("Loi dong: " + line);
                    }
                }
            }
        }
    }
}