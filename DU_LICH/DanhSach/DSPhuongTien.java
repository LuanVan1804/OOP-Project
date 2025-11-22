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

    public DSPhuongTien(DSPhuongTien other) {
        if (other == null) {
            this.dspt = new PhuongTien[0];
            this.soLuongpt = 0;
        } else {
            this.soLuongpt = other.soLuongpt;
            this.dspt = Arrays.copyOf(other.dspt, other.dspt.length);
        }
    }

    public void them() {
        System.out.print("Nhap so luong phuong tien can them: ");
        int so;
        while (!sc.hasNextInt()) {
            System.out.print("So nguyen, nhap lai: ");
            sc.next();
        }
        so = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < so; i++) {
            dspt = Arrays.copyOf(dspt, soLuongpt + 1);
            dspt[soLuongpt] = new PhuongTien();
            System.out.println("-- Nhap thong tin phuong tien thu " + (soLuongpt + 1) + " --");
            dspt[soLuongpt].nhap(sc);
            soLuongpt++;
        }
        System.out.println("Them thanh cong.\n");
    }

    public void xoa() {
        xuat();
        if (soLuongpt == 0)
            return;

        System.out.print("Nhap bien so xe can xoa: ");
        String bienso = sc.nextLine().trim();

        int idx = -1;
        for (int i = 0; i < soLuongpt; i++) {
            if (dspt[i].getBienKiemSoat().equalsIgnoreCase(bienso)) {
                idx = i;
                break;
            }
        }

        if (idx == -1) {
            System.out.println("Khong tim thay phuong tien.\n");
            return;
        }

        // shift left
        for (int j = idx; j < soLuongpt - 1; j++) {
            dspt[j] = dspt[j + 1];
        }
        soLuongpt--;
        dspt = Arrays.copyOf(dspt, soLuongpt); // shrink array
        System.out.println("Xoa thanh cong.\n");
    }

    public void sua() {
        xuat();
        if (soLuongpt == 0)
            return;

        System.out.print("Nhap bien so xe can sua: ");
        String bienso = sc.nextLine().trim();

        for (int i = 0; i < soLuongpt; i++) {
            if (dspt[i].getBienKiemSoat().equalsIgnoreCase(bienso)) {
                System.out.println("1. Sua bien so xe");
                System.out.println("2. Sua so cho ngoi");
                System.out.println("3. Sua loai phuong tien");
                System.out.print("Chon thong tin can sua (1-3): ");

                int chon;
                while (!sc.hasNextInt()) {
                    System.out.print("So nguyen, nhap lai: ");
                    sc.next();
                }
                chon = sc.nextInt();
                sc.nextLine();

                switch (chon) {
                    case 1:
                        System.out.print("Nhap bien kiem soat moi: ");
                        String bienMoi = sc.nextLine().trim();
                        dspt[i].setBienKiemSoat(bienMoi);
                        break;
                    case 2:
                        System.out.print("Nhap so cho ngoi moi: ");
                        while (!sc.hasNextInt()) {
                            System.out.print("So nguyen, nhap lai: ");
                            sc.next();
                        }
                        int soChoMoi = sc.nextInt();
                        sc.nextLine();
                        dspt[i].setSoChoNgoi(soChoMoi);
                        break;
                    case 3:
                        System.out.print("Nhap loai phuong tien moi: ");
                        String loaiMoi = sc.nextLine().trim();
                        dspt[i].setLoaiPhuongTien(loaiMoi);
                        break;
                    default:
                        System.out.println("Lua chon khong hop le.");
                        return;
                }

                System.out.println("=================================");
                System.out.println("========= Sua thanh cong ========");
                System.out.println("=================================\n");
                return;
            }
        }
        System.out.println("Khong tim thay phuong tien.\n");
    }

    public void timKiem() {
        System.out.print("Nhap bien so xe can tim: ");
        String tim = sc.nextLine().trim();
        for (int i = 0; i < soLuongpt; i++) {
            if (dspt[i].getBienKiemSoat().equalsIgnoreCase(tim)) {
                dspt[i].xuat();
                return;
            }
        }
        System.out.println("Khong tim thay phuong tien.\n");
    }

    // Tim phuong tien theo bien so, tra ve doi tuong hoac null
    public PhuongTien timTheoBien(String bien) {
        if (bien == null) return null;
        String b = bien.trim();
        for (int i = 0; i < soLuongpt; i++) {
            if (dspt[i] != null && dspt[i].getBienKiemSoat().equalsIgnoreCase(b)) return dspt[i];
        }
        return null;
    }

    // Tim theo ten/loai phuong tien (case-insensitive, contains)
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

    // Thong ke don gian: tong phuong tien, tong cho, trung binh phi theo ngay, so
    // theo loai
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
            System.out.println("Danh sach phuong tien trong.\n");
            return;
        }
        System.out.println("Danh sach phuong tien:");
        // header for compact list
        System.out.println("# | Bien | So cho | Loai | Phi/ngay");
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
            System.out.println("=== MENU PHUONG TIEN ===");
            System.out.println("1. Them phuong tien");
            System.out.println("2. Xoa phuong tien");
            System.out.println("3. Sua phuong tien");
            System.out.println("4. Tim kiem phuong tien");
            System.out.println("5. Xuat danh sach phuong tien");
            System.out.println("6. Thoat");
            System.out.print("Vui long chon (1-6): ");

            while (!sc.hasNextInt()) {
                System.out.print("So nguyen, nhap lai: ");
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
                    System.out.println("Nhap sai! Vui long chon lai.\n");
            }
        } while (true);
    }
}
