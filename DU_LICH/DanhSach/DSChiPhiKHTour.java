package DU_LICH.DanhSach;

import DU_LICH.ClassDon.ChiPhiKHTour;
import java.io.*;

public class DSChiPhiKHTour {
    private ChiPhiKHTour[] list;
    private int soLuong;
    private static final int MAX = 100;

    public DSChiPhiKHTour() {
        list = new ChiPhiKHTour[MAX];
        soLuong = 0;
    }

    public int getSoLuong() { return soLuong; }

    // Kiểm tra trùng mã kế hoạch
    private boolean isExist(String maKHTour) {
        for (int i = 0; i < soLuong; i++) {
            if (list[i].getMaKHTour().equalsIgnoreCase(maKHTour)) return true;
        }
        return false;
    }

    // 1. Thêm (không validate mã KHTour ở đây)
    public boolean them(ChiPhiKHTour cp) {
        if (soLuong >= MAX || cp == null || isExist(cp.getMaKHTour())) {
            return false;
        }
        list[soLuong++] = cp;
        return true;
    }

    // 2. Xóa theo mã kế hoạch
    public boolean xoa(String maKHTour) {
        for (int i = 0; i < soLuong; i++) {
            if (list[i].getMaKHTour().equalsIgnoreCase(maKHTour)) {
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
    public ChiPhiKHTour timTheoMa(String maKHTour) {
        for (int i = 0; i < soLuong; i++) {
            if (list[i].getMaKHTour().equalsIgnoreCase(maKHTour)) {
                return list[i];
            }
        }
        return null;
    }

    // 4. Hiển thị danh sách
    public void hienThiDanhSach() {
        if (soLuong == 0) {
            System.out.println("Chua co chi phi ke hoach tour nao!");
            return;
        }
        System.out.println("==================================================================================================");
        System.out.println("                                   DANH SACH CHI PHI KE HOACH TOUR");
        System.out.println("==================================================================================================");
        System.out.printf("%-10s | %-10s | %-10s | %-15s | %-12s | %-12s | %-12s | %-12s | %-14s%n",
                "Ma KH", "Nha Hang", "Khach San", "Phuong Tien", "Tien An", "Tien Phong", "Tien PT", "Tien Tour", "TONG CHI");
        System.out.println("--------------------------------------------------------------------------------------------------");
        for (int i = 0; i < soLuong; i++) {
            list[i].hienThi();
        }
        System.out.println("==================================================================================================");
    }

    // 5. Thống kê đơn giản
    public void thongKe() {
        if (soLuong == 0) {
            System.out.println("Khong co du lieu de thong ke!");
            return;
        }
        double tongAn = 0, tongPhong = 0, tongPT = 0, tongTour = 0, tongChi = 0;
        for (int i = 0; i < soLuong; i++) {
            ChiPhiKHTour cp = list[i];
            tongAn += cp.getTongTienAn();
            tongPhong += cp.getTongTienPhong();
            tongPT += cp.getTongTienPhuongTien();
            tongTour += cp.getTienTour();
            tongChi += cp.getTongChi();
        }
        System.out.println("============================= THONG KE CHI PHI KE HOACH TOUR =============================");
        System.out.printf("So ke hoach co chi phi   : %,d%n", soLuong);
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
            for (int i = 0; i < soLuong; i++) {
                bw.write(list[i].toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Loi ghi file chi phi!");
        }
    }

    // 7. Đọc file (tự động khi khởi tạo)
    public void loadFromFile(String path) {
        soLuong = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null && soLuong < MAX) {
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
                        list[soLuong++] = cp;
                    } catch (Exception e) {
                        System.out.println("Bo qua dong loi trong file chi phi: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Loi doc file chi phi!");
        }
    }
}