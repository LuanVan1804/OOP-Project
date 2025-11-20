package DU_LICH.DanhSach;

import java.io.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;

import DU_LICH.ClassDon.KeHoachTour;
import DU_LICH.QuanLy.QuanLy;

public class DSKHTour {
    private KeHoachTour[] list;
    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private static final SimpleDateFormat SDF = new SimpleDateFormat(DATE_PATTERN, new Locale("vi", "VN"));

    public DSKHTour() {
        list = new KeHoachTour[0];
    }

    public int getSoLuong() { return list.length; }
    
    public KeHoachTour[] getList() { 
        return Arrays.copyOf(list, list.length);
    }

    // Kiem tra trung ma
    private boolean isMaUnique(String ma) {
        for (KeHoachTour k : list) {
            if (k != null && k.getMaKHTour().equalsIgnoreCase(ma)) return false;
        }
        return true;
    }

    // 1. Them
    public boolean them(KeHoachTour k) {
        if (k == null || !isMaUnique(k.getMaKHTour())) {
            return false;
        }
        list = Arrays.copyOf(list, list.length + 1);
        list[list.length - 1] = k;
        return true;
    }

    // 2. Xoa theo ma
    public boolean xoa(String ma) {
        int index = -1;
        for (int i = 0; i < list.length; i++) {
            if (list[i] != null && list[i].getMaKHTour().equalsIgnoreCase(ma)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            for (int i = index; i < list.length - 1; i++) {
                list[i] = list[i + 1];
            }
            list[list.length - 1] = null;
            list = Arrays.copyOf(list, list.length - 1);
            return true;
        }
        return false;
    }

    // 3. Tim theo ma (tra ve object)
    public KeHoachTour timTheoMa(String ma) {
        for (KeHoachTour k : list) {
            if (k != null && k.getMaKHTour().equalsIgnoreCase(ma)) {
                return k;
            }
        }
        return null;
    }

    // 4. Tim theo ten ke hoach (tra ve mang)
    public KeHoachTour[] timTheoTen(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return new KeHoachTour[0];
        keyword = keyword.toLowerCase().trim();
        int count = 0;
        for (KeHoachTour k : list) {
            if (k != null && k.getTenKeHoach().toLowerCase().contains(keyword)) count++;
        }
        KeHoachTour[] kq = new KeHoachTour[count];
        int idx = 0;
        for (KeHoachTour k : list) {
            if (k != null && k.getTenKeHoach().toLowerCase().contains(keyword)) {
                kq[idx++] = k;
            }
        }
        return Arrays.copyOf(kq, idx);
    }

    // 5. Hien thi toan bo
    public void hienThiDanhSach() {
    if (list.length == 0) {
        System.out.println("Danh sach trong!");
        return;
    }
    // CAP NHAT CHI PHI + VE DA DAT CHO TAT CA KE HOACH
    for (KeHoachTour k : list) {
        if (k != null) {
            k.capNhatTongChi(QuanLy.getDsChiPhi());
            k.capNhatVeDaDatVaDoanhThu(QuanLy.getDsHoaDon());
        }
    }

    System.out.println("════════════════════════════════════════════════════════════════════════════════════════");
    System.out.printf("%-10s | %-20s | %-10s | %-23s | %-12s | %-12s | %-14s%n",
            "Ma KH", "Ten Ke Hoach", "Ma Tour", "Ngay Di - Ve", "Ve Dat/Tong", "Doanh Thu", "Loi Nhuan");
    System.out.println("════════════════════════════════════════════════════════════════════════════════════════");
    for (KeHoachTour k : list) {
        if (k != null) k.hienThi();
    }
    System.out.println("════════════════════════════════════════════════════════════════════════════════════════");
}

    // 6. Thong ke don gian
    public void thongKe() {
        for (KeHoachTour k : list) {
            if (k != null) {
                k.capNhatTongChi(QuanLy.getDsChiPhi());
            }
        }

        if (list.length == 0) {
            System.out.println("Khong co du lieu thong ke!");
            return;
        }
        double tongDoanhThu = 0, tongChi = 0;
        int tongVe = 0, tongVeDat = 0;

        for (KeHoachTour k : list) {
            if (k != null) {
                double thu = k.getTongVeDaDat() * k.getGiaVe();
                tongDoanhThu += thu;
                tongChi += k.getTongChi();
                tongVe += k.getTongSoVe();
                tongVeDat += k.getTongVeDaDat();
            }
        }

        System.out.println("================================ THONG KE KE HOACH TOUR ===============================");
        System.out.printf("So ke hoach tour      : %,d%n", list.length);
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
            for (KeHoachTour k : list) {
                if (k != null) {
                    bw.write(k.toString());
                    bw.newLine();
                }
            }
        }
    }

    // 8. Doc file
    public void loadFromFile(String path) throws IOException {

        list = new KeHoachTour[0];
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] p = line.split(",", -1); // -1 de giu phan tu rong
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
                        list = Arrays.copyOf(list, list.length + 1);
                        list[list.length - 1] = k;
                    } catch (Exception e) {
                        System.out.println("Loi dong: " + line);
                    }
                }
            }
        }
    }
}