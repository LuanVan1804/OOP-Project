package DU_LICH.DanhSach;

import DU_LICH.ClassDon.ChiTietHD;
import java.io.*;
import java.util.Arrays;

public class DSChiTietHD {
    private ChiTietHD[] list;

    public DSChiTietHD() {
        list = new ChiTietHD[0];
    }

    public int getSoLuong() { return list.length; }
    
    public ChiTietHD[] getList() {
        return Arrays.copyOf(list, list.length);
    }

    private boolean isMaHDExist(String maHD) {
        for (ChiTietHD ct : list) {
            if (ct != null && ct.getMaHD().equalsIgnoreCase(maHD)) return true;
        }
        return false;
    }

    public boolean them(ChiTietHD ct) {
        if (ct == null || isMaHDExist(ct.getMaHD())) {
            return false;
        }
        list = Arrays.copyOf(list, list.length + 1);
        list[list.length - 1] = ct;
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

    public ChiTietHD tim(String maHD) {
        for (ChiTietHD ct : list) {
            if (ct != null && ct.getMaHD().equalsIgnoreCase(maHD)) {
                return ct;
            }
        }
        return null;
    }

    public void hienThiDanhSach() {
        if (list.length == 0) {
            System.out.println("Chua co chi tiet hoa don nao!");
            return;
        }
        System.out.println("====================================================================");
        System.out.println("                       DANH SACH CHI TIET HOA DON");
        System.out.println("====================================================================");
        System.out.printf("%-12s | %-12s | %-10s | %-18s%n",
                "Ma HD", "Ma KH Tour", "So Khach", "Tong Tien Ve");
        System.out.println("--------------------------------------------------------------------");
        for (ChiTietHD ct : list) {
            if (ct != null) ct.hienThiNgan();
        }
        System.out.println("====================================================================");
    }

    public void thongKe() {
        if (list.length == 0) {
            System.out.println("Khong co du lieu de thong ke!");
            return;
        }
        int tongKhach = 0;
        double tongDoanhThu = 0;
        for (ChiTietHD ct : list) {
            if (ct != null) {
                tongKhach += ct.getSoKhach();
                tongDoanhThu += ct.getTongTienVe();
            }
        }
        System.out.println("============================ THONG KE CHI TIET HOA DON ===========================");
        System.out.printf("Tong so chi tiet HD   : %,d%n", list.length);
        System.out.printf("Tong so khach         : %,d%n", tongKhach);
        System.out.printf("TONG DOANH THU VE     : %,15.0f VND%n", tongDoanhThu);
        System.out.println("=================================================================================");
    }

    public void saveToFile(String path) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (ChiTietHD ct : list) {
                if (ct != null) {
                    bw.write(ct.toString());
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Loi ghi file ChiTietHoaDon!");
        }
    }

    public void loadFromFile(String path) {
        list = new ChiTietHD[0];

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
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

                        list = Arrays.copyOf(list, list.length + 1);
                        list[list.length - 1] = new ChiTietHD(maHD, maKHTour, dsKH, giaVe);
                    } catch (Exception e) {
                        System.out.println("Loi dong file ChiTietHD: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Loi doc file ChiTietHD: " + e.getMessage());
        }
    }
}