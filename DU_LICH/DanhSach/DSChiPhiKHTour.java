package DU_LICH.DanhSach;

import DU_LICH.ClassDon.ChiPhiKHTour;
import java.io.*;
import java.util.Arrays;

public class DSChiPhiKHTour {
    private ChiPhiKHTour[] list;

    public DSChiPhiKHTour() {
        list = new ChiPhiKHTour[0];
    }

    public int getSoLuong() { return list.length; }
    
    public ChiPhiKHTour[] getList() {
        return Arrays.copyOf(list, list.length);
    }

    // Kiem tra trung ma ke hoach
    private boolean isExist(String maKHTour) {
        for (ChiPhiKHTour cp : list) {
            if (cp != null && cp.getMaKHTour().equalsIgnoreCase(maKHTour)) return true;
        }
        return false;
    }

    // 1. Them (khong validate ma KHTour o day)
    public boolean them(ChiPhiKHTour cp) {
        if (cp == null || isExist(cp.getMaKHTour())) {
            return false;
        }
        list = Arrays.copyOf(list, list.length + 1);
        list[list.length - 1] = cp;
        return true;
    }

    // 2. Xoa theo ma ke hoach
    public boolean xoa(String maKHTour) {
        int index = -1;
        for (int i = 0; i < list.length; i++) {
            if (list[i] != null && list[i].getMaKHTour().equalsIgnoreCase(maKHTour)) {
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
    public ChiPhiKHTour timTheoMa(String maKHTour) {
        for (ChiPhiKHTour cp : list) {
            if (cp != null && cp.getMaKHTour().equalsIgnoreCase(maKHTour)) {
                return cp;
            }
        }
        return null;
    }

    // 4. Hien thi danh sach
    public void hienThiDanhSach() {
        if (list.length == 0) {
            System.out.println("Chua co chi phi ke hoach tour nao!");
            return;
        }
        System.out.println("==================================================================================================");
        System.out.println("                                   DANH SACH CHI PHI KE HOACH TOUR");
        System.out.println("==================================================================================================");
        System.out.printf("%-10s | %-10s | %-10s | %-15s | %-12s | %-12s | %-12s | %-12s | %-14s%n",
                "Ma KH", "Nha Hang", "Khach San", "Phuong Tien", "Tien An", "Tien Phong", "Tien PT", "Tien Tour", "TONG CHI");
        System.out.println("--------------------------------------------------------------------------------------------------");
        for (ChiPhiKHTour cp : list) {
            if (cp != null) cp.hienThi();
        }
        System.out.println("==================================================================================================");
    }

    // 5. Thong ke don gian
    public void thongKe() {
        if (list.length == 0) {
            System.out.println("Khong co du lieu de thong ke!");
            return;
        }
        double tongAn = 0, tongPhong = 0, tongPT = 0, tongTour = 0, tongChi = 0;
        for (ChiPhiKHTour cp : list) {
            if (cp != null) {
                tongAn += cp.getTongTienAn();
                tongPhong += cp.getTongTienPhong();
                tongPT += cp.getTongTienPhuongTien();
                tongTour += cp.getTienTour();
                tongChi += cp.getTongChi();
            }
        }
        System.out.println("============================= THONG KE CHI PHI KE HOACH TOUR =============================");
        System.out.printf("So ke hoach co chi phi   : %,d%n", list.length);
        System.out.printf("Tong tien an             : %,15.0f VND%n", tongAn);
        System.out.printf("Tong tien phong          : %,15.0f VND%n", tongPhong);
        System.out.printf("Tong tien phuong tien    : %,15.0f VND%n", tongPT);
        System.out.printf("Tong tien tour           : %,15.0f VND%n", tongTour);
        System.out.printf("TONG CHI PHI             : %,15.0f VND%n", tongChi);
        System.out.println("========================================================================================");
    }

    // 6. Ghi file
    public void saveToFile(String path) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (ChiPhiKHTour cp : list) {
                if (cp != null) {
                    bw.write(cp.toString());
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Loi ghi file chi phi!");
        }
    }

    // 7. Doc file (tu dong khi khoi tao)
    public void loadFromFile(String path) {
        list = new ChiPhiKHTour[0];
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] p = line.split(",", -1);
                if (p.length >= 8) {
                    try {
                        ChiPhiKHTour cp = new ChiPhiKHTour(
                            p[0].trim(), p[1].trim(), p[2].trim(), p[3].trim(),
                            Double.parseDouble(p[4].trim()),
                            Double.parseDouble(p[5].trim()),
                            Double.parseDouble(p[6].trim()),
                            Double.parseDouble(p[7].trim())
                        );
                        list = Arrays.copyOf(list, list.length + 1);
                        list[list.length - 1] = cp;
                    } catch (Exception e) {
                        System.out.println("Bo qua dong loi trong file chi phi: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Loi doc file chi phi: " + e.getMessage());
        }
    }
}