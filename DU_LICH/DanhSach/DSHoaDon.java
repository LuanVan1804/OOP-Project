package DU_LICH.DanhSach;

import DU_LICH.ClassDon.HoaDon;
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