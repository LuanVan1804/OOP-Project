package DU_LICH.DanhSach;

import DU_LICH.ClassDon.ChiPhiKHTour;
import DU_LICH.ClassDon.ChiTietHD;
import DU_LICH.ClassDon.HoaDon;
import DU_LICH.QuanLy.QuanLy;

import java.io.*;
import java.time.LocalDate;
import java.util.Arrays;

public class DSHoaDon {
    private HoaDon[] list;

    public DSHoaDon() {
        list = new HoaDon[0];
    }

    public int getSoLuong() { return list.length; }
    
    public HoaDon[] getList() { 
        return Arrays.copyOf(list, list.length);
    }

    private boolean isMaExist(String maHD) {
        for (HoaDon hd : list) {
            if (hd != null && hd.getMaHD().equalsIgnoreCase(maHD)) return true;
        }
        return false;
    }

    public boolean them(HoaDon hd) {
        if (hd == null || isMaExist(hd.getMaHD())) {
            return false;
        }
        list = Arrays.copyOf(list, list.length + 1);
        list[list.length - 1] = hd;
        return true;
    }

    public boolean xoa(String maHD) {
        int index = -1;
        for (int i = 0; i < list.length; i++) {
            if (list[i] != null && list[i].getMaHD().equalsIgnoreCase(maHD)) {
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

    public HoaDon timTheoMa(String maHD) {
        for (HoaDon hd : list) {
            if (hd != null && hd.getMaHD().equalsIgnoreCase(maHD)) {
                return hd;
            }
        }
        return null;
    }

    public void hienThiDanhSach() {
        if (list.length == 0) {
            System.out.println("Chua co hoa don nao!");
            return;
        }
        System.out.println("==================================================================================================");
        System.out.println("                                      DANH SACH HOA DON");
        System.out.println("==================================================================================================");
        System.out.printf("%-12s | %-12s | %-8s | %-8s | %-6s | %-6s | %-12s | %-18s%n",
                "Ma HD", "Ma KH Tour", "Ma HDV", "Ma KH", "Khach", "So Ve", "Ngay Lap", "Tong Tien");
        System.out.println("--------------------------------------------------------------------------------------------------");
        for (HoaDon hd : list) {
            if (hd != null) hd.hienThi();
        }
        System.out.println("==================================================================================================");
    }

    public void thongKe() {
        if (list.length == 0) {
            System.out.println("Khong co du lieu de thong ke!");
            return;
        }
        int tongVe = 0;
        int tongKhach = 0;
        double tongDoanhThu = 0;
        for (HoaDon hd : list) {
            if (hd != null) {
                tongVe += hd.getSoVe();
                tongKhach += hd.getSoKhach();
                tongDoanhThu += hd.getTongTien();
            }
        }
        System.out.println("================================ THONG KE HOA DON =================================");
        System.out.printf("Tong so hoa don       : %,d%n", list.length);
        System.out.printf("Tong so khach         : %,d%n", tongKhach);
        System.out.printf("Tong so ve da ban     : %,d%n", tongVe);
        System.out.printf("TONG DOANH THU        : %,15.0f VND%n", tongDoanhThu);
        System.out.printf("Doanh thu trung binh  : %,15.0f VND%n", list.length > 0 ? tongDoanhThu / list.length : 0);
        System.out.println("===================================================================================");
    }


    // THỐNG KÊ NÂNG CAO THEO QUÝ (DOANH THU - CHI PHÍ - LỢI NHUẬN)
public void thongKeTheoQuy() {
    if (list.length == 0) {
        System.out.println("Khong co du lieu hoa don de thong ke!");
        return;
    }

    // 4 quý + tổng cộng
    double[] doanhThu = new double[5];  // index 0-3: Q1-Q4, index 4: tổng
    double[] chiPhi   = new double[5];
    double[] loiNhuan = new double[5];

    for (HoaDon hd : list) {
        if (hd == null) continue;

        LocalDate ngayLap = hd.getNgayLap();
        if (ngayLap == null) continue;

        int thang = ngayLap.getMonthValue();
        int quy = (thang - 1) / 3; // 0: Q1 (1-3), 1: Q2 (4-6), 2: Q3 (7-9), 3: Q4 (10-12)

        double tienVe = hd.getTongTien(); // Doanh thu từ vé

        // Lấy chi phí từ ChiTietHD + ChiPhiKHTour (nếu có)
        double chi = 0.0;
        ChiTietHD ct = QuanLy.getDsChiTietHD().tim(hd.getMaHD());
        if (ct != null) {
            ChiPhiKHTour cp = QuanLy.getDsChiPhi().timTheoMa(ct.getMaKHTour());
            if (cp != null) {
                chi = cp.getTongChi(); // Tổng chi phí dịch vụ + tour
            }
        }

        double loi = tienVe - chi;

        // Cộng vào quý tương ứng
        doanhThu[quy] += tienVe;
        chiPhi[quy]   += chi;
        loiNhuan[quy] += loi;

        // Cộng vào tổng cộng
        doanhThu[4] += tienVe;
        chiPhi[4]   += chi;
        loiNhuan[4] += loi;
    }

    // In bảng thống kê đẹp
    System.out.println();
    System.out.println("════════════════════════════════════════════════════════════════════════════════");
    System.out.println("                   THỐNG KÊ DOANH THU - CHI PHÍ - LỢI NHUẬN THEO QUÝ");
    System.out.println("════════════════════════════════════════════════════════════════════════════════");
    System.out.printf("%-15s | %-15s | %-15s | %-15s | %-15s | %-15s%n",
            "", "Quý 1", "Quý 2", "Quý 3", "Quý 4", "TỔNG CỘNG");
    System.out.println("--------------------------------------------------------------------------------");

    System.out.printf("%-15s | %,15.0f | %,15.0f | %,15.0f | %,15.0f | %,15.0f VND%n",
            "DOANH THU", 
            doanhThu[0], doanhThu[1], doanhThu[2], doanhThu[3], doanhThu[4]);

    System.out.printf("%-15s | %,15.0f | %,15.0f | %,15.0f | %,15.0f | %,15.0f VND%n",
            "CHI PHÍ", 
            chiPhi[0], chiPhi[1], chiPhi[2], chiPhi[3], chiPhi[4]);

    System.out.printf("%-15s | %,15.0f | %,15.0f | %,15.0f | %,15.0f | %,15.0f VND%n",
            "LỢI NHUẬN", 
            loiNhuan[0], loiNhuan[1], loiNhuan[2], loiNhuan[3], loiNhuan[4]);

    System.out.println("--------------------------------------------------------------------------------");
    System.out.println("* Quý 1: Tháng 1-3 | Quý 2: Tháng 4-6 | Quý 3: Tháng 7-9 | Quý 4: Tháng 10-12");
    System.out.println("================================================================================\n");
}

    public void saveToFile(String path) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (HoaDon hd : list) {
                if (hd != null) {
                    bw.write(hd.toString());
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Loi ghi file HoaDon!");
        }
    }

    public void loadFromFile(String path) {
    list = new HoaDon[0];
    File f = new File(path);
    if (!f.exists()) {
        System.out.println("Khong tim thay file HoaDon: " + path);
        return;
    }

    try (BufferedReader br = new BufferedReader(new FileReader(f))) {
        String line;
        int lineNum = 0;
        while ((line = br.readLine()) != null) {
            lineNum++;
            line = line.trim();
            if (line.isEmpty()) continue;

            String[] p = line.split(",", -1);  // -1 de giu phan tu rong
            if (p.length < 8) {
                System.out.println("Bo qua dong " + lineNum + " (qua it cot): " + line);
                continue;
            }

            try {
                HoaDon hd = new HoaDon(
                    p[0].trim(),
                    p[1].trim(),
                    Integer.parseInt(p[2].trim()),
                    Integer.parseInt(p[3].trim()),
                    Integer.parseInt(p[4].trim()),
                    Integer.parseInt(p[5].trim()),
                    Double.parseDouble(p[6].trim())
                );
                hd.setNgayLap(LocalDate.parse(p[7].trim()));
                list = Arrays.copyOf(list, list.length + 1);
                list[list.length - 1] = hd;
                System.out.println("Da doc thanh cong HD: " + hd.getMaHD()); // DEBUG
            } catch (Exception e) {
                System.out.println("Loi doc dong " + lineNum + ": " + line + " -> " + e.getMessage());
            }
        }
        System.out.println("=== DA TAI XONG HOA DON: " + list.length + " hoa don ===");
    } catch (IOException e) {
        System.out.println("Loi mo file HoaDon: " + e.getMessage());
    }
}
}