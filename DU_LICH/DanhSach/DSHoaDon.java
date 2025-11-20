package DU_LICH.DanhSach;

import DU_LICH.ClassDon.HoaDon;
import java.io.*;
import java.time.LocalDate;

public class DSHoaDon {
    private HoaDon[] list;
    private int soLuong;
    private static final int MAX = 200;

    public DSHoaDon() {
        list = new HoaDon[MAX];
        soLuong = 0;
    }

    public int getSoLuong() { return soLuong; }
    public HoaDon[] getList() { return list; }

    private boolean isMaExist(String maHD) {
        for (int i = 0; i < soLuong; i++) {
            if (list[i].getMaHD().equalsIgnoreCase(maHD)) return true;
        }
        return false;
    }

    public boolean them(HoaDon hd) {
        if (soLuong >= MAX || hd == null || isMaExist(hd.getMaHD())) {
            return false;
        }
        list[soLuong++] = hd;
        return true;
    }

    public boolean xoa(String maHD) {
        for (int i = 0; i < soLuong; i++) {
            if (list[i].getMaHD().equalsIgnoreCase(maHD)) {
                for (int j = i; j < soLuong - 1; j++) {
                    list[j] = list[j + 1];
                }
                list[--soLuong] = null;
                return true;
            }
        }
        return false;
    }

    public HoaDon timTheoMa(String maHD) {
        for (int i = 0; i < soLuong; i++) {
            if (list[i].getMaHD().equalsIgnoreCase(maHD)) {
                return list[i];
            }
        }
        return null;
    }

    public void hienThiDanhSach() {
        if (soLuong == 0) {
            System.out.println("Chua co hoa don nao!");
            return;
        }
        System.out.println("==================================================================================================");
        System.out.println("                                      DANH SACH HOA DON");
        System.out.println("==================================================================================================");
        System.out.printf("%-12s | %-12s | %-8s | %-8s | %-6s | %-6s | %-12s | %-18s%n",
                "Ma HD", "Ma KH Tour", "Ma HDV", "Ma KH", "Khach", "So Ve", "Ngay Lap", "Tong Tien");
        System.out.println("--------------------------------------------------------------------------------------------------");
        for (int i = 0; i < soLuong; i++) {
            list[i].hienThi();
        }
        System.out.println("==================================================================================================");
    }

    public void thongKe() {
        if (soLuong == 0) {
            System.out.println("Khong co du lieu de thong ke!");
            return;
        }
        int tongVe = 0;
        int tongKhach = 0;
        double tongDoanhThu = 0;
        for (int i = 0; i < soLuong; i++) {
            HoaDon hd = list[i];
            tongVe += hd.getSoVe();
            tongKhach += hd.getSoKhach();
            tongDoanhThu += hd.getTongTien();
        }
        System.out.println("================================ THONG KE HOA DON =================================");
        System.out.printf("Tong so hoa don       : %,d%n", soLuong);
        System.out.printf("Tong so khach         : %,d%n", tongKhach);
        System.out.printf("Tong so ve da ban     : %,d%n", tongVe);
        System.out.printf("TONG DOANH THU        : %,15.0f VND%n", tongDoanhThu);
        System.out.printf("Doanh thu trung binh  : %,15.0f VND%n", tongDoanhThu / soLuong);
        System.out.println("===================================================================================");
    }

    public void saveToFile(String path) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (int i = 0; i < soLuong; i++) {
                bw.write(list[i].toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Loi ghi file HoaDon!");
        }
    }

    public void loadFromFile(String path) {
        File f = new File(path);
        if (!f.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null && soLuong < MAX) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] p = line.split(",", -1);
                if (p.length >= 8) {
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
                        list[soLuong++] = hd;
                    } catch (Exception e) {
                        System.out.println("Loi dong file HoaDon: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Loi doc file HoaDon!");
        }
    }
}