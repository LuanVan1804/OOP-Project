package DU_LICH;

import DU_LICH.Nguoi.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

public class DSHoaDon {
    // THUOC TINH
    private HoaDon[] list;
    private int soLuongHoaDon;
    private DSChiPhiKHTour dsChiPhi;

    // CONSTRUCTOR
    public DSHoaDon() {
        this.list = new HoaDon[0];
        this.soLuongHoaDon = 0;
        this.dsChiPhi = null;
    }

    // Setter cho DSChiPhiKHTour
    public void setDsChiPhi(DSChiPhiKHTour dsChiPhi) {
        this.dsChiPhi = dsChiPhi;
        // Cap nhat dsChiPhi cho tat ca HoaDon da load
        for (int i = 0; i < soLuongHoaDon; i++) {
            if (list[i] != null) {
                list[i].setDsChiPhi(dsChiPhi);
            }
        }
    }

    public DSChiPhiKHTour getDsChiPhi() {
        return dsChiPhi;
    }

    // GETTER/SETTER
    public HoaDon[] getList() {
        return list;
    }

    public int getSoLuongHoaDon() {
        return soLuongHoaDon;
    }

    // ===== DOC FILE =====
    // Doc danh sach hoa don tu file txt
    public void loadFromFile(String filePath, DSKHTour dsKHTour) throws IOException {
        File f = new File(filePath);
        if (!f.exists()) {
            this.list = new HoaDon[0];
            this.soLuongHoaDon = 0;
            return;
        }

        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        int count = 0;
        while ((line = br.readLine()) != null)
            count++;
        br.close();

        if (count == 0) {
            this.list = new HoaDon[0];
            this.soLuongHoaDon = 0;
            return;
        }

        list = new HoaDon[count];
        br = new BufferedReader(new FileReader(filePath));
        int index = 0;

        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 6) {
                String maHD = parts[0];
                String maKHTour = parts[1];

                // FIX: Load day du thong tin KeHoachTour tu DSKHTour (bao gom giaVe)
                KeHoachTour kht = dsKHTour.timTheoMaObject(maKHTour);
                if (kht == null) {
                    kht = new KeHoachTour();
                    kht.setMaKHTour(maKHTour);
                    kht.setGiaVe(0.0);
                }

                HDV hdv = new HDV();
                hdv.setMaHDV(Integer.parseInt(parts[2]));

                KhachHang kh = new KhachHang();
                kh.setMaKH(Integer.parseInt(parts[3]));

                int soKhach = Integer.parseInt(parts[4]);
                int soVe = Integer.parseInt(parts[5]);

                LocalDate ngayLap = LocalDate.now();
                if (parts.length >= 7) {
                    ngayLap = LocalDate.parse(parts[6]);
                }

                HoaDon hd = new HoaDon(maHD, kht, hdv, kh, soKhach, soVe);
                hd.setDsChiPhi(dsChiPhi);
                list[index++] = hd;
            }
        }
        br.close();
        soLuongHoaDon = index;
    }

    // ===== GHI FILE =====
    // Luu danh sach hoa don vao file txt
    public void saveToFile(String filePath) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
        for (int i = 0; i < soLuongHoaDon; i++) {
            HoaDon hd = list[i];
            if (hd == null)
                continue;
            String line = String.format("%s,%s,%d,%d,%d,%d,%s",
                    hd.getMaHD(),
                    hd.getMaKHTour().getMaKHTour(),
                    hd.getMaHDV().getMaHDV(),
                    hd.getMaKHDaiDien().getMaKH(),
                    hd.getSoKhach(),
                    hd.getSoVe(),
                    hd.getNgayLap().toString());
            bw.write(line);
            bw.newLine();
        }
        bw.close();
    }

    // ===== KIEM TRA MA TON TAI =====
    public boolean kiemTraMaHoaDonTonTai(String maHD) {
        for (HoaDon hd : list) {
            if (hd != null && hd.getMaHD().equals(maHD))
                return true;
        }
        return false;
    }

    // ===== THEM HOA DON =====
    public void them(HoaDon hoaDon) {
        hoaDon.setDsChiPhi(dsChiPhi);
        list = Arrays.copyOf(list, list.length + 1);
        list[list.length - 1] = hoaDon;
        soLuongHoaDon++;
    }

    // ===== XOA HOA DON =====
    public boolean xoa(String maHD) {
        for (int i = 0; i < soLuongHoaDon; i++) {
            if (list[i] != null && list[i].getMaHD().equals(maHD)) {
                for (int j = i; j < soLuongHoaDon - 1; j++)
                    list[j] = list[j + 1];
                list = Arrays.copyOf(list, list.length - 1);
                soLuongHoaDon--;
                return true;
            }
        }
        return false;
    }

    // ===== TIM KIEM =====
    // Tim 1 hoa don theo ma (tra ve HoaDon hoac null)
    public HoaDon timHoaDonTheoMa(String maHD) {
        for (int i = 0; i < soLuongHoaDon; i++) {
            if (list[i] != null && list[i].getMaHD().equals(maHD)) {
                return list[i];
            }
        }
        return null;
    }

    // Tim nhieu hoa don theo ma khach hang (tra ve mang HoaDon[])
    public HoaDon[] timHoaDonTheoKhachHang(int maKH) {
        // Dem
        int count = 0;
        for (int i = 0; i < soLuongHoaDon; i++) {
            if (list[i] != null && list[i].getMaKHDaiDien().getMaKH() == maKH)
                count++;
        }

        // Tao mang va them
        HoaDon[] ketQua = new HoaDon[count];
        int index = 0;
        for (int i = 0; i < soLuongHoaDon; i++) {
            if (list[i] != null && list[i].getMaKHDaiDien().getMaKH() == maKH) {
                ketQua[index++] = list[i];
            }
        }
        return ketQua;
    }

    // ===== HIEN THI =====
    // Hien thi tat ca hoa don
    public void hienThiDanhSachHoaDon() {
        if (soLuongHoaDon == 0) {
            System.out.println("Danh sach hoa don trong!");
            return;
        }
        System.out.println("\n================================================================================");
        System.out.println("                        DANH SACH HOA DON");
        System.out.println("================================================================================");
        int count = 0;
        for (int i = 0; i < soLuongHoaDon; i++) {
            if (list[i] != null) {
                count++;
                list[i].xuatThongTin();
            }
        }
        if (count == 0) {
            System.out.println("Khong co hoa don nao hop le!");
        }
    }

    // ===== THONG KE =====
    // Tinh tong so hoa don, ve da ban va doanh thu
    public void thongKeHoaDon() {
        if (soLuongHoaDon == 0) {
            System.out.println("Chua co hoa don nao!");
            return;
        }

        System.out.println("\n================================================================================");
        System.out.println("                        THONG KE HOA DON");
        System.out.println("================================================================================");

        // Duyet va tinh tong
        int tongSoVe = 0;
        int tongSoKhach = 0;
        double tongDoanhThu = 0;
        for (int i = 0; i < soLuongHoaDon; i++) {
            if (list[i] != null) {
                tongSoVe += list[i].getSoVe();
                tongSoKhach += list[i].getSoKhach();
                tongDoanhThu += list[i].tongTienHoaDon();
            }
        }

        // In ket qua
        System.out.println("Tong so hoa don:       " + soLuongHoaDon);
        System.out.println("Tong so ve da ban:     " + tongSoVe);
        System.out.println("Tong so khach:         " + tongSoKhach);
        System.out.println("Tong doanh thu:        " + String.format("%,.0f VND", tongDoanhThu));
        System.out.println("Doanh thu trung binh:  " + String.format("%,.0f VND", tongDoanhThu / soLuongHoaDon));
        System.out.println("================================================================================\n");
    }


   public void ThongKeHoaDonTuNgayADenNgayB(LocalDate ngayA, LocalDate ngayB) {
    if (soLuongHoaDon == 0) {
        System.out.println("Chua co hoa don nao!");
        return;
    }

    if (ngayB.isBefore(ngayA)) {
        System.out.println("Ngay B phai sau hoac bang ngay A!");
        return; //  Không nhập lại trong hàm
    }

    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    System.out.println("\n================================================================================");
    System.out.println("               THONG KE HOA DON TU NGAY " + ngayA.format(fmt) +
                       " DEN NGAY " + ngayB.format(fmt));
    System.out.println("================================================================================");

    // Duyet danh sach hoa don
    int tongSoVe = 0, tongSoKhach = 0, soHoaDonTrongKhoang = 0;
    double tongDoanhThu = 0;

    for (HoaDon hd : list) {
        if (hd != null) {
            LocalDate ngayLap = hd.getNgayLap();
            if (!ngayLap.isBefore(ngayA) && !ngayLap.isAfter(ngayB)) {
                tongSoVe += hd.getSoVe();
                tongSoKhach += hd.getSoKhach();
                tongDoanhThu += hd.tongTienHoaDon();
                soHoaDonTrongKhoang++;
            }
        }
    }

    // In ket qua
    System.out.println("Tong so hoa don       : " + soHoaDonTrongKhoang);
    System.out.println("Tong so ve da ban     : " + tongSoVe);
    System.out.println("Tong so khach         : " + tongSoKhach);
    System.out.println("Tong doanh thu        : " + String.format("%,.0f VND", tongDoanhThu));
    if (soHoaDonTrongKhoang > 0) {
        System.out.println("Doanh thu trung binh  : " + String.format("%,.0f VND", tongDoanhThu / soHoaDonTrongKhoang));
    } else {
        System.out.println("Doanh thu trung binh  : 0 VND");
    }
    System.out.println("================================================================================\n");
}

}
