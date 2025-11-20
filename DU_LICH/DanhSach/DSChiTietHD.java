package DU_LICH.DanhSach;

import DU_LICH.ClassDon.ChiTietHD;
import java.io.*;

public class DSChiTietHD {
    private ChiTietHD[] list;
    private int soLuong;
    private static final int MAX = 200;

    public DSChiTietHD() {
        list = new ChiTietHD[MAX];
        soLuong = 0;
    }

    public int getSoLuong() { return soLuong; }

    private boolean isMaHDExist(String maHD) {
        for (int i = 0; i < soLuong; i++) {
            if (list[i].getMaHD().equalsIgnoreCase(maHD)) return true;
        }
        return false;
    }

    public boolean them(ChiTietHD ct) {
        if (soLuong >= MAX || ct == null || isMaHDExist(ct.getMaHD())) {
            return false;
        }
        list[soLuong++] = ct;
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

    public ChiTietHD tim(String maHD) {
        for (int i = 0; i < soLuong; i++) {
            if (list[i].getMaHD().equalsIgnoreCase(maHD)) {
                return list[i];
            }
        }
        return null;
    }

    public void hienThiDanhSach() {
        if (soLuong == 0) {
            System.out.println("Chua co chi tiet hoa don nao!");
            return;
        }
        System.out.println("====================================================================");
        System.out.println("                       DANH SACH CHI TIET HOA DON");
        System.out.println("====================================================================");
        System.out.printf("%-12s | %-12s | %-10s | %-18s%n",
                "Ma HD", "Ma KH Tour", "So Khach", "Tong Tien Ve");
        System.out.println("--------------------------------------------------------------------");
        for (int i = 0; i < soLuong; i++) {
            list[i].hienThiNgan();
        }
        System.out.println("====================================================================");
    }

    public void thongKe() {
        if (soLuong == 0) {
            System.out.println("Khong co du lieu de thong ke!");
            return;
        }
        int tongKhach = 0;
        double tongDoanhThu = 0;
        for (int i = 0; i < soLuong; i++) {
            ChiTietHD ct = list[i];
            tongKhach += ct.getSoKhach();
            tongDoanhThu += ct.getTongTienVe();
        }
        System.out.println("============================ THONG KE CHI TIET HOA DON ===========================");
        System.out.printf("Tong so chi tiet HD   : %,d%n", soLuong);
        System.out.printf("Tong so khach         : %,d%n", tongKhach);
        System.out.printf("TONG DOANH THU VE     : %,15.0f VND%n", tongDoanhThu);
        System.out.println("=================================================================================");
    }

    public void saveToFile(String path) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (int i = 0; i < soLuong; i++) {
                bw.write(list[i].toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Loi ghi file ChiTietHoaDon!");
        }
    }

    public void loadFromFile(String path) {
        soLuong = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null && soLuong < MAX) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] p = line.split(",");
                if (p.length >= 3) {
                    try {
                        String maHD = p[0].trim();
                        String maKHTour = p[1].trim();
                        double giaVe = Double.parseDouble(p[2].trim());

                        int[] dsKH = new int[p.length - 3];
                        for (int i = 0; i < dsKH.length; i++) {
                            dsKH[i] = Integer.parseInt(p[3 + i].trim());
                        }

                        list[soLuong++] = new ChiTietHD(maHD, maKHTour, dsKH, giaVe);
                    } catch (Exception e) {
                        System.out.println("Loi dong file ChiTietHD: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Loi doc file ChiTietHD!");
        }
    }
}