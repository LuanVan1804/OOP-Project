package DU_LICH.DanhSach;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import DU_LICH.ClassDon.NhaHang;

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
        if (soLuong >= MAX) { System.out.println("Danh sach da day (" + MAX + ")!"); return false; }
        if (findIndexByMa(nh.getMaNhaHang()) != -1) { System.out.println("Trung ma nha hang: " + nh.getMaNhaHang()); return false; }
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

    // Thong ke don gian cho nha hang
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
            System.out.printf("Gia trung binh combo: %,.0f\n", avg);
        }
    }

    public void xuatDanhSach() {
        if (soLuong == 0) { System.out.println("Danh sach trong."); return; }
        System.out.println("===== DANH SACH NHA HANG (" + soLuong + ") =====");
        for (int i = 0; i < soLuong; i++) {
            System.out.println("-- Nha hang thu " + (i+1) + " --");
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
    // Doc danh sach nha hang tu file (moi dong: ma,ten,combo,gia)
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
                    System.out.println("Loi phan tich dong: " + line + " -> " + ex.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Khong the doc file: " + e.getMessage());
        }
        return dem;
    }

    // Ghi danh sach nha hang ra file (dinh dang tuong tu)
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
            System.out.println("Khong the ghi file: " + e.getMessage());
        }
        return dem;
    }

    // Menus have been moved to QuanLy
}

