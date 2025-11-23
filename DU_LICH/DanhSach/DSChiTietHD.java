package DU_LICH.DanhSach;

import DU_LICH.ClassDon.ChiTietHD;
import java.io.*;
import java.util.Arrays;

public class DSChiTietHD {
    private ChiTietHD[] list;

    public DSChiTietHD() {
        list = new ChiTietHD[0];
    }

    public DSChiTietHD(ChiTietHD[] list) {
        this.list = list == null ? new ChiTietHD[0] : Arrays.copyOf(list, list.length);
    }

    public DSChiTietHD(DSChiTietHD other) {
        if (other == null || other.list == null) this.list = new ChiTietHD[0];
        else this.list = Arrays.copyOf(other.list, other.list.length);
    }

    public int getSoLuong() { return list.length; }
    
    public ChiTietHD[] getList() {
        return Arrays.copyOf(list, list.length);
    }

    // === KIỂM TRA TRÙNG CHI TIẾT: cùng maHD + cùng maKhachHang ===
    private boolean isChiTietExist(String maHD, int maKhachHang) {
        for (ChiTietHD ct : list) {
            if (ct != null 
                && ct.getMaHD().equalsIgnoreCase(maHD) 
                && ct.getMaKhachHang() == maKhachHang) {
                return true;
            }
        }
        return false;
    }

    // === THÊM CHI TIẾT HÓA ĐƠN (ĐÃ SỬA LỖI LOGIC) ===
    public boolean them(ChiTietHD ct) {
        if (ct == null) {
            return false;
        }

        if (isChiTietExist(ct.getMaHD(), ct.getMaKhachHang())) {
            System.out.println("Canh bao: Khach hang " + ct.getMaKhachHang() + 
                " da ton tai trong hoa don " + ct.getMaHD() + "!");
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

    // Trong phương thức hienThiDanhSach()
    public void hienThiDanhSach() {
        if (list.length == 0) {
            System.out.println("Chua co chi tiet hoa don nao!");
            return;
        }
        System.out.println("==================================================================================================");
        System.out.println("                   DANH SACH CHI TIET HOA DON (MOI KHACH 1 DONG)");
        System.out.println("==================================================================================================");
        System.out.printf("%-8s | %-13s | %-11s | %15s | %15s%n",
                "Ma HD", "Ma Khach Hang", "Loai Ve", "Gia Ve", "Thanh Tien");
        System.out.println("--------------------------------------------------------------------------------------------------");

        for (ChiTietHD ct : list) {
            if (ct != null) {
                System.out.printf("%-8s | %-13d | %-11s | %,15.0f | %,15.0f%n",
                    ct.getMaHD(),
                    ct.getMaKhachHang(),
                    ct.getLoaiVe(),
                    ct.getGiaVe(),
                    ct.getThanhTien()
                );
            }
        }
        System.out.println("==================================================================================================");
    }

    public void thongKe() {
        if (list.length == 0) {
            System.out.println("Khong co du lieu de thong ke!");
            return;
        }

        int tongKhachNguoiLon = 0;
        int tongKhachTreEm = 0;
        int tongKhach = 0;
        double tongDoanhThu = 0;

        for (ChiTietHD ct : list) {
            if (ct != null) {
                tongKhach++;
                tongDoanhThu += ct.getThanhTien();

                if ("NguoiLon".equalsIgnoreCase(ct.getLoaiVe())) {
                    tongKhachNguoiLon++;
                } else if ("TreEm".equalsIgnoreCase(ct.getLoaiVe())) {
                    tongKhachTreEm++;
                }
            }
        }

        System.out.println("════════════════════════════════════════════════════════════════════════════════");
        System.out.println("                   THONG KE CHI TIET HOA DON (THEO TUNG KHACH HANG)");
        System.out.println("════════════════════════════════════════════════════════════════════════════════");
        System.out.printf("Tong so dong chi tiet         : %,d%n", list.length);
        System.out.printf("Tong so khach hang            : %,d khach%n", tongKhach);
        System.out.printf("  ├─ Khach nguoi lon           : %,d khach%n", tongKhachNguoiLon);
        System.out.printf("  └─ Khach tre em (<12 tuoi)   : %,d khach%n", tongKhachTreEm);
        System.out.println("────────────────────────────────────────────────────────────────────────────────");
        System.out.printf("TONG DOANH THU TU VE           : %,15.0f VND%n", tongDoanhThu);
        System.out.println("================================================================================\n");
    }

    // === SAVE TO FILE ===
    public void saveToFile(String path) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (ChiTietHD ct : list) {
                if (ct != null) {
                    // maHD, maKHTour, maKhachHang, loaiVe, thanhTien
                    bw.write(String.join(",",
                        ct.getMaHD(),
                        ct.getMaKHTour(),
                        String.valueOf(ct.getMaKhachHang()),
                        ct.getLoaiVe(),
                        String.valueOf((long)ct.getThanhTien())  // tránh .0
                    ));
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Loi ghi file ChiTietHD: " + e.getMessage());
        }
    }

    // === LOAD FROM FILE ===
    public void loadFromFile(String path) {
        list = new ChiTietHD[0];
        File f = new File(path);
        if (!f.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] p = line.split(",");
                if (p.length != 5) {
                    System.out.println("Bo qua dong sai dinh dang ChiTietHD: " + line);
                    continue;
                }

                try {
                    String maHD = p[0].trim();
                    String maKHTour = p[1].trim();
                    int maKH = Integer.parseInt(p[2].trim());
                    String loaiVe = p[3].trim(); // NguoiLon hoặc TreEm
                    double thanhTien = Double.parseDouble(p[4].trim());

                    // Tạo đối tượng mới (giá vé sẽ được suy ra từ thanhTien và loại vé)
                    ChiTietHD ct = new ChiTietHD();
                    ct = new ChiTietHD(maHD, maKHTour, maKH, loaiVe, thanhTien * 2); // giá cơ bản = thanhTien * 2 (vì trẻ em 50%)
                    // Nhưng tốt hơn: không cần giá cơ bản → dùng constructor đơn giản hơn

                    // Dùng constructor đầy đủ (khuyến nghị thêm constructor này vào ChiTietHD)
                    ChiTietHD ctNew = new ChiTietHD(maHD, maKHTour, maKH, loaiVe, thanhTien * (loaiVe.equals("TreEm") ? 2 : 1));
                    // → Nếu là trẻ em: thanhTien = 50% → giá cơ bản = thanhTien * 2

                    list = Arrays.copyOf(list, list.length + 1);
                    list[list.length - 1] = ctNew;

                } catch (Exception e) {
                    System.out.println("Loi doc dong ChiTietHD: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Loi mo file ChiTietHD: " + e.getMessage());
        }
    }
}