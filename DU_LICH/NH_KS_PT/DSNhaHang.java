package DU_LICH.NH_KS_PT;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
        System.out.println("Để trống và nhấn Enter nếu muốn giữ nguyên.");

        System.out.print("Tên nhà hàng hiện tại (" + nh.getTenNhaHang() + "): ");
        String ten = sc.nextLine();
        if (!ten.trim().isEmpty()) nh.setTenNhaHang(ten.trim());

        System.out.print("Combo hiện tại (" + nh.getCombo() + "): ");
        String combo = sc.nextLine();
        if (!combo.trim().isEmpty()) nh.setCombo(combo.trim());

        System.out.print("Giá combo hiện tại (" + nh.getGiaCombo() + "): ");
        String giaStr = sc.nextLine();
        if (!giaStr.trim().isEmpty()) {
            try { nh.setGiaCombo(Double.parseDouble(giaStr.trim())); } catch (NumberFormatException e) { System.out.println("Giá không hợp lệ, giữ nguyên."); }
        }
        return true;
    }

    // File format: ma;ten;combo;gia
    public int docFile(String filePath) {
        int dem = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line; this.soLuong = 0;
            while ((line = br.readLine()) != null) {
                line = line.trim(); if (line.isEmpty()) continue;
                String[] p = line.split(";");
                if (p.length < 4) { System.out.println("Bỏ qua dòng không hợp lệ: " + line); continue; }
                try {
                    NhaHang nh = new NhaHang();
                    nh.setMaNhaHang(p[0].trim());
                    nh.setTenNhaHang(p[1].trim());
                    nh.setCombo(p[2].trim());
                    nh.setGiaCombo(Double.parseDouble(p[3].trim()));
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

    public int ghiFile(String filePath) {
        int dem = 0;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (int i = 0; i < soLuong; i++) {
                NhaHang nh = dsNhaHang[i]; if (nh == null) continue;
                String dong = String.join(";",
                    nh.getMaNhaHang(),
                    nh.getTenNhaHang(),
                    nh.getCombo(),
                    String.valueOf(nh.getGiaCombo())
                );
                bw.write(dong); bw.newLine(); dem++;
            }
        } catch (IOException e) {
            System.out.println("Không thể ghi file: " + e.getMessage());
        }
        return dem;
    }

    public void menu() {
        Scanner sc = new Scanner(System.in);
        String duongDan = "DU_LICH\\NhaHang.txt";
        if (!new java.io.File(duongDan).exists()) duongDan = "D:\\DU_LICH\\NhaHang.txt";
        int chon;
        do {
            System.out.println("\n========= MENU QUẢN LÝ NHÀ HÀNG =========");
            System.out.println("1. Đọc danh sách từ file");
            System.out.println("2. Ghi danh sách ra file");
            System.out.println("3. Xem danh sách nhà hàng");
            System.out.println("4. Thêm nhà hàng");
            System.out.println("5. Xóa nhà hàng theo mã");
            System.out.println("6. Sửa thông tin nhà hàng theo mã");
            System.out.println("7. Tìm kiếm theo mã");
            System.out.println("8. Tìm kiếm theo tên (chứa từ khóa)");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");
            while (!sc.hasNextInt()) { System.out.print("Vui lòng nhập số: "); sc.nextLine(); }
            chon = sc.nextInt(); sc.nextLine();

            switch (chon) {
                case 1: {
                    System.out.print("Nhập đường dẫn file (Enter để dùng mặc định: " + duongDan + "): ");
                    String p = sc.nextLine().trim(); if (!p.isEmpty()) duongDan = p;
                    int n = docFile(duongDan);
                    System.out.println("Đã đọc " + n + " dòng hợp lệ.");
                    break;
                }
                case 2: {
                    System.out.print("Nhập đường dẫn file (Enter để dùng mặc định: " + duongDan + "): ");
                    String p = sc.nextLine().trim(); if (!p.isEmpty()) duongDan = p;
                    int n = ghiFile(duongDan);
                    System.out.println("Đã ghi " + n + " dòng.");
                    break;
                }
                case 3: { xuatDanhSach(); break; }
                case 4: {
                    NhaHang nh = new NhaHang();
                    nh.nhap();
                    if (them(nh)) System.out.println("Thêm thành công!");
                    break;
                }
                case 5: {
                    System.out.print("Nhập mã cần xóa: ");
                    String ma = sc.nextLine();
                    if (xoaTheoMa(ma)) System.out.println("Đã xóa!"); else System.out.println("Không tìm thấy mã.");
                    break;
                }
                case 6: {
                    System.out.print("Nhập mã cần sửa: ");
                    String ma = sc.nextLine();
                    if (suaTheoMaTuBanPhim(ma, sc)) System.out.println("Đã cập nhật!"); else System.out.println("Không tìm thấy mã.");
                    break;
                }
                case 7: {
                    System.out.print("Nhập mã cần tìm: ");
                    String ma = sc.nextLine();
                    NhaHang nh = timTheoMa(ma);
                    if (nh == null) System.out.println("Không tìm thấy."); else nh.xuat();
                    break;
                }
                case 8: {
                    System.out.print("Nhập từ khóa tên: ");
                    String tk = sc.nextLine();
                    NhaHang[] kq = new NhaHang[soLuong];
                    int n = timTheoTen(tk, kq);
                    if (n == 0) System.out.println("Không có kết quả.");
                    else {
                        System.out.println("Tìm thấy " + n + " kết quả:");
                        for (int i = 0; i < n && i < kq.length; i++) if (kq[i] != null) { kq[i].xuat(); System.out.println(); }
                    }
                    break;
                }
                case 0: break;
                default: System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (chon != 0);
    }
}

