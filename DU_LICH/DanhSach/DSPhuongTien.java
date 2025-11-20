package DU_LICH.DanhSach;

import java.io.*;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

import DU_LICH.ClassDon.PhuongTien;

import java.util.Map;
import java.util.HashMap;

public class DSPhuongTien {
    private PhuongTien dspt[];
    private int soLuongpt;
    private final Scanner sc = new Scanner(System.in);

    public DSPhuongTien() {
        soLuongpt = 0;
        dspt = new PhuongTien[0];
    }

    public DSPhuongTien(PhuongTien[] dspt, int soLuongpt) {
        this.dspt = dspt == null ? new PhuongTien[0] : dspt;
        this.soLuongpt = Math.max(0, Math.min(soLuongpt, this.dspt.length));
    }

    public void them() {
        System.out.print("Nhập số lượng phương tiện cần thêm: ");
        int so;
        while (!sc.hasNextInt()) {
            System.out.print("Số nguyên, nhập lại: ");
            sc.next();
        }
        so = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < so; i++) {
            dspt = Arrays.copyOf(dspt, soLuongpt + 1);
            dspt[soLuongpt] = new PhuongTien();
            System.out.println("-- Nhập thông tin phương tiện thứ " + (soLuongpt + 1) + " --");
            dspt[soLuongpt].nhap(sc);
            soLuongpt++;
        }
        System.out.println("Thêm thành công.\n");
    }

    public void xoa() {
        xuat();
        if (soLuongpt == 0)
            return;

        System.out.print("Nhập biển số xe cần xóa: ");
        String bienso = sc.nextLine().trim();

        int idx = -1;
        for (int i = 0; i < soLuongpt; i++) {
            if (dspt[i].getBienKiemSoat().equalsIgnoreCase(bienso)) {
                idx = i;
                break;
            }
        }

        if (idx == -1) {
            System.out.println("Không tìm thấy phương tiện.\n");
            return;
        }

        // shift left
        for (int j = idx; j < soLuongpt - 1; j++) {
            dspt[j] = dspt[j + 1];
        }
        soLuongpt--;
        dspt = Arrays.copyOf(dspt, soLuongpt); // shrink array
        System.out.println("Xóa thành công.\n");
    }

    public void sua() {
        xuat();
        if (soLuongpt == 0)
            return;

        System.out.print("Nhập biển số xe cần sửa: ");
        String bienso = sc.nextLine().trim();

        for (int i = 0; i < soLuongpt; i++) {
            if (dspt[i].getBienKiemSoat().equalsIgnoreCase(bienso)) {
                System.out.println("1. Sửa biển số xe");
                System.out.println("2. Sửa số chỗ ngồi");
                System.out.println("3. Sửa loại phương tiện");
                System.out.print("Chọn thông tin cần sửa (1-3): ");

                int chon;
                while (!sc.hasNextInt()) {
                    System.out.print("Số nguyên, nhập lại: ");
                    sc.next();
                }
                chon = sc.nextInt();
                sc.nextLine();

                switch (chon) {
                    case 1:
                        System.out.print("Nhập biển kiểm soát mới: ");
                        String bienMoi = sc.nextLine().trim();
                        dspt[i].setBienKiemSoat(bienMoi);
                        break;
                    case 2:
                        System.out.print("Nhập số chỗ ngồi mới: ");
                        while (!sc.hasNextInt()) {
                            System.out.print("Số nguyên, nhập lại: ");
                            sc.next();
                        }
                        int soChoMoi = sc.nextInt();
                        sc.nextLine();
                        dspt[i].setSoChoNgoi(soChoMoi);
                        break;
                    case 3:
                        System.out.print("Nhập loại phương tiện mới: ");
                        String loaiMoi = sc.nextLine().trim();
                        dspt[i].setLoaiPhuongTien(loaiMoi);
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ.");
                        return;
                }

                System.out.println("=================================");
                System.out.println("========= Sửa thành công ========");
                System.out.println("=================================\n");
                return;
            }
        }
        System.out.println("Không tìm thấy phương tiện.\n");
    }

    public void timKiem() {
        System.out.print("Nhập biển số xe cần tìm: ");
        String tim = sc.nextLine().trim();
        for (int i = 0; i < soLuongpt; i++) {
            if (dspt[i].getBienKiemSoat().equalsIgnoreCase(tim)) {
                dspt[i].xuat();
                return;
            }
        }
        System.out.println("Không tìm thấy phương tiện.\n");
    }

    // Tìm phương tiện theo biển số, trả về đối tượng hoặc null
    public PhuongTien timTheoBien(String bien) {
        if (bien == null) return null;
        String b = bien.trim();
        for (int i = 0; i < soLuongpt; i++) {
            if (dspt[i] != null && dspt[i].getBienKiemSoat().equalsIgnoreCase(b)) return dspt[i];
        }
        return null;
    }

    // Tìm theo tên/loại phương tiện (case-insensitive, contains)
    public PhuongTien[] timTheoTen(String ten) {
        if (ten == null)
            return new PhuongTien[0];
        String key = ten.trim().toLowerCase(Locale.ROOT);
        int count = 0;
        for (int i = 0; i < soLuongpt; i++) {
            if (dspt[i] != null && dspt[i].getLoaiPhuongTien() != null
                    && dspt[i].getLoaiPhuongTien().toLowerCase(Locale.ROOT).contains(key))
                count++;
        }
        PhuongTien[] result = new PhuongTien[count];
        int idx = 0;
        for (int i = 0; i < soLuongpt; i++) {
            if (dspt[i] != null && dspt[i].getLoaiPhuongTien() != null
                    && dspt[i].getLoaiPhuongTien().toLowerCase(Locale.ROOT).contains(key)) {
                result[idx++] = dspt[i];
            }
        }
        return result;
    }

    // Thống kê đơn giản: tổng phương tiện, tổng chỗ, trung bình phí theo ngày, số
    // theo loại
    public void thongKe() {
        if (soLuongpt == 0) {
            System.out.println("Chua co phuong tien nao!");
            return;
        }
        int tong = soLuongpt;
        int tongCho = 0;
        double tongPhi = 0.0;
        Map<String, Integer> demLoai = new HashMap<>();
        for (int i = 0; i < soLuongpt; i++) {
            PhuongTien p = dspt[i];
            if (p == null)
                continue;
            tongCho += p.getSoChoNgoi();
            tongPhi += p.getPhiTheoNgay();
            String loai = p.getLoaiPhuongTien() == null ? "(Khac)" : p.getLoaiPhuongTien();
            demLoai.put(loai, demLoai.getOrDefault(loai, 0) + 1);
        }
        System.out.println("\n================== THONG KE PHUONG TIEN ==================");
        System.out.println("Tong phuong tien: " + tong);
        System.out.println("Tong cho ngoi: " + tongCho);
        System.out.println("Phi thue trung binh/ngay: " + String.format("%,.0f VND", (tongPhi / tong)));
        System.out.println("So luong theo loai:");
        for (Map.Entry<String, Integer> e : demLoai.entrySet()) {
            System.out.printf("  - %s: %d\n", e.getKey(), e.getValue());
        }
        System.out.println("==========================================================\n");
    }

    // Luu/Doc file CSV
    public void loadFromFile(String filePath) throws IOException {
        File f = new File(filePath);
        this.dspt = new PhuongTien[0];
        this.soLuongpt = 0;
        if (!f.exists()) return;

        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;
            String low = line.toLowerCase();
            if (low.startsWith("bien") || line.contains("|") || low.startsWith("mapt")) continue;
            String[] parts = line.split(",");
            if (parts.length < 4) continue;
            String bien = parts[0].trim();
            int cho = 0;
            try { cho = Integer.parseInt(parts[1].trim()); } catch (Exception ex) { cho = 0; }
            String loai = parts[2].trim();
            double phi = 0.0;
            try { phi = Double.parseDouble(parts[3].trim()); } catch (Exception ex) { phi = 0.0; }

            // append
            dspt = Arrays.copyOf(dspt, soLuongpt + 1);
            dspt[soLuongpt++] = new PhuongTien(bien, cho, loai, phi);
        }
        br.close();
    }

    public void saveToFile(String filePath) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
        // simple header
        bw.write("bienKiemSoat,soChoNgoi,loaiPhuongTien,phiTheoNgay"); bw.newLine();
        for (int i = 0; i < soLuongpt; i++) {
            PhuongTien p = dspt[i];
            if (p == null) continue;
            String line = String.format("%s,%d,%s,%.0f", p.getBienKiemSoat(), p.getSoChoNgoi(), p.getLoaiPhuongTien(), p.getPhiTheoNgay());
            bw.write(line); bw.newLine();
        }
        bw.close();
    }

    public void xuat() {
        if (soLuongpt == 0) {
            System.out.println("Danh sách phương tiện trống.\n");
            return;
        }
        System.out.println("Danh sách phương tiện:");
        // header for compact list
        System.out.println("# | Biển | Số chỗ | Loại | Phí/ngày");
        for (int i = 0; i < soLuongpt; i++) {
            System.out.print((i + 1) + ") ");
            if (dspt[i] != null) {
                dspt[i].xuat(); // PhuongTien.xuat() prints one-line summary
            } else {
                System.out.println("(null)");
            }
        }
    }

    

    public void showMenu() {
        int chon;
        do {
            System.out.println("=== MENU PHƯƠNG TIỆN ===");
            System.out.println("1. Thêm phương tiện");
            System.out.println("2. Xóa phương tiện");
            System.out.println("3. Sửa phương tiện");
            System.out.println("4. Tìm kiếm phương tiện");
            System.out.println("5. Xuất danh sách phương tiện");
            System.out.println("6. Thoát");
            System.out.print("Vui lòng chọn (1-6): ");

            while (!sc.hasNextInt()) {
                System.out.print("Số nguyên, nhập lại: ");
                sc.next();
            }
            chon = sc.nextInt();
            sc.nextLine();

            switch (chon) {
                case 1:
                    them();
                    break;
                case 2:
                    xoa();
                    break;
                case 3:
                    sua();
                    break;
                case 4:
                    timKiem();
                    break;
                case 5:
                    xuat();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Nhập sai! Vui lòng chọn lại.\n");
            }
        } while (true);
    }
}
