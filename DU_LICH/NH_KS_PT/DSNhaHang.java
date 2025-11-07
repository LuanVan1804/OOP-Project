package DU_LICH.NH_KS_PT;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class DSNhaHang {
    private NhaHang[] dsNhaHang;
    private int soLuong;
    private final int MAX = 50;

    public DSNhaHang() {
        this.dsNhaHang = new NhaHang[MAX];
        this.soLuong = 0;
    }

    public int getSoLuong() { return soLuong; }

    private int findIndexByMa(String ma) {
        if (ma == null) return -1;
        for (int i = 0; i < soLuong; i++) {
            if (dsNhaHang[i] != null && ma.equalsIgnoreCase(dsNhaHang[i].getMaNhaHang())) {
                return i;
            }
        }
        return -1;
    }

    public boolean them(NhaHang nh) {
        if (nh == null) return false;
        if (soLuong >= MAX) { System.out.println("Danh sách đã đầy (" + MAX + ")!"); return false; }
        if (findIndexByMa(nh.getMaNhaHang()) != -1) { System.out.println("Trùng mã nhà hàng: " + nh.getMaNhaHang()); return false; }
        dsNhaHang[soLuong++] = nh; return true;
    }

    public boolean xoaTheoMa(String ma) {
        int idx = findIndexByMa(ma);
        if (idx == -1) return false;
        for (int i = idx; i < soLuong - 1; i++) dsNhaHang[i] = dsNhaHang[i+1];
        dsNhaHang[soLuong - 1] = null; soLuong--; return true;
    }

    public NhaHang timTheoMa(String ma) {
        int idx = findIndexByMa(ma);
        return idx == -1 ? null : dsNhaHang[idx];
    }

    public int timTheoTen(String tuKhoa, NhaHang[] ketQua) {
        if (tuKhoa == null) tuKhoa = "";
        String tk = tuKhoa.trim().toLowerCase();
        int dem = 0;
        for (int i = 0; i < soLuong; i++) {
            NhaHang nh = dsNhaHang[i];
            if (nh != null && nh.getTenNhaHang() != null && nh.getTenNhaHang().toLowerCase().contains(tk)) {
                if (ketQua != null && dem < ketQua.length) ketQua[dem] = nh;
                dem++;
            }
        }
        return dem;
    }

    // Thống kê đơn giản cho nhà hàng
    public void thongKeDonGian() {
        System.out.println("--- Thong ke don gian ---");
        System.out.println("So luong nha hang: " + soLuong);
        if (soLuong > 0) {
            double tong = 0.0;
            int dem = 0;
            for (int i = 0; i < soLuong; i++) {
                NhaHang n = dsNhaHang[i];
                if (n != null) { tong += n.getGiaCombo(); dem++; }
            }
            double avg = dem == 0 ? 0.0 : (tong / dem);
            System.out.println("Gia trung binh combo: " + avg);
        }
    }

    public void xuatDanhSach() {
        if (soLuong == 0) { System.out.println("Danh sách trống."); return; }
        System.out.println("===== DANH SÁCH NHÀ HÀNG (" + soLuong + ") =====");
        for (int i = 0; i < soLuong; i++) {
            System.out.println("-- Nhà hàng thứ " + (i+1) + " --");
            dsNhaHang[i].xuat();
            System.out.println();
        }
    }

    public boolean suaTheoMaTuBanPhim(String ma, Scanner sc) {
        NhaHang nh = timTheoMa(ma);
        if (nh == null) return false;
        System.out.println("De trong va nhan Enter neu muon giu nguyen.");

        System.out.print("Ten nha hang hien tai (" + nh.getTenNhaHang() + "): ");
        String ten = sc.nextLine();
        if (!ten.trim().isEmpty()) nh.setTenNhaHang(ten.trim());

        System.out.print("Combo hien tai (" + nh.getCombo() + "): ");
        String combo = sc.nextLine();
        if (!combo.trim().isEmpty()) nh.setCombo(combo.trim());

        System.out.print("Gia combo hien tai (" + nh.getGiaCombo() + "): ");
        String giaStr = sc.nextLine();
        if (!giaStr.trim().isEmpty()) {
            try { nh.setGiaCombo(Double.parseDouble(giaStr.trim())); } catch (NumberFormatException e) { System.out.println("Gia khong hop le, giu nguyen."); }
        }
        return true;
    }

    // File format: ma,ten,combo,gia 
    // Đọc danh sách nhà hàng từ file (mỗi dòng: ma,ten,combo,gia)
    public int loadFromFile(String filePath) {
        int dem = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            soLuong = 0;
            String line;
            while ((line = br.readLine()) != null && soLuong < dsNhaHang.length) {
                line = line.trim(); if (line.isEmpty()) continue;
                String[] p = line.split("[,;]");
                if (p.length < 4) { System.out.println("Bo qua dong khong hop le: " + line); continue; }
                try {
                    NhaHang nh = new NhaHang(
                        p[0].trim(),        // maNhaHang
                        p[1].trim(),        // tenNhaHang
                        p[2].trim(),        // combo
                        Double.parseDouble(p[3].trim()) // giaCombo
                    );
                    if (them(nh)) dem++;
                } catch (Exception ex) {
                    System.out.println("Lỗi phân tích dòng: " + line + " -> " + ex.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Không thể đọc file: " + e.getMessage());
        }
        return dem;
    }

    // Ghi danh sách nhà hàng ra file (định dạng tương tự)
    public int saveToFile(String filePath) {
        int dem = 0;
        File f = new File(filePath);
        if (f.getParentFile() != null) f.getParentFile().mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
            for (int i = 0; i < soLuong; i++) {
                NhaHang nh = dsNhaHang[i];
                bw.write(String.join(",",
                    nh.getMaNhaHang(),
                    nh.getTenNhaHang(),
                    nh.getCombo(),
                    String.valueOf(nh.getGiaCombo())
                ));
                bw.newLine();
                dem++;
            }
        } catch (IOException e) {
            System.out.println("Không thể ghi file: " + e.getMessage());
        }
        return dem;
    }

    // instance menu không tham số
    public void menu() { menu((String) null); }
    // hàm menu quản lý nhà hàng
    public void menu(String providedPath) {
        Scanner sc = new Scanner(System.in);

        int chon;
        do {
            System.out.println("\n========= MENU QUẢN LÝ NHÀ HÀNG =========");
            System.out.println("1. Them");
            System.out.println("2. Sua");
            System.out.println("3. Xoa");
            System.out.println("4. Tim kiem theo ten");
            System.out.println("5. Tim kiem theo ma");
            System.out.println("6. Thong ke");
            System.out.println("7. Hien thi danh sach");
            System.out.println("0. Thoat");
            System.out.print("Chon: ");

            while (!sc.hasNextInt()) { System.out.print("Vui long nhap so: "); sc.nextLine(); }
            chon = sc.nextInt(); sc.nextLine();

            switch (chon) {
                case 1: {
                    NhaHang nh = new NhaHang(); nh.nhap(); 
                    if (them(nh)) 
                        System.out.println("Them thanh cong!"); 
                    break;
                }
                case 2: {
                    System.out.print("Nhap ma can sua: "); 
                    String ma = sc.nextLine(); 
                    if (suaTheoMaTuBanPhim(ma, sc)) 
                        System.out.println("Da cap nhat!"); 
                    else 
                        System.out.println("Khong tim thay ma."); 
                    break;
                }
                case 3: {
                    System.out.print("Nhap ma can xoa: "); 
                    String ma = sc.nextLine(); 
                    if (xoaTheoMa(ma)) 
                        System.out.println("Da xoa!"); 
                    else 
                        System.out.println("Khong tim thay ma."); 
                    break;
                }
                case 4: {
                    System.out.print("Nhap tu khoa tim theo ten: "); 
                    String tk = sc.nextLine(); 
                    NhaHang[] kq = new NhaHang[soLuong]; 
                    int n = timTheoTen(tk, kq);
                    if (n == 0) 
                        System.out.println("Khong co ket qua."); 
                    else { 
                        System.out.println("Tim thay " + n + " ket qua:"); 
                        for (int i = 0; i < n && i < kq.length; i++) 
                        if (kq[i] != null) { kq[i].xuat(); 
                            System.out.println(); 
                        } 
                    }
                    break;
                }
                case 5: {
                    System.out.print("Nhap ma can tim: "); 
                    String ma = sc.nextLine(); 
                    NhaHang nh = timTheoMa(ma); 
                    if (nh == null) 
                        System.out.println("Khong tim thay."); 
                    else nh.xuat(); break;
                }
                case 6: {
                    thongKeDonGian(); break;
                }
                case 7: {
                    xuatDanhSach(); break;
                }
                case 0: {
                    String savePath = providedPath != null ? providedPath : "D:\\doanOOP\\DU_LICH\\NH_KS_PT\\NhaHang.txt";
                    saveToFile(savePath); break;
                }
                default: System.out.println("Lua chon khong hop le.");
            }
        } while (chon != 0);
    }
}

