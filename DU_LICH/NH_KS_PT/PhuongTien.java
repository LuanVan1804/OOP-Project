package DU_LICH.NH_KS_PT;

import java.util.Scanner;

public class PhuongTien {
    private String bienKiemSoat;
    private int soChoNgoi;
    private String loaiPhuongTien;
    private double phiTheoNgay;
    
    // Constructor mặc định
    public PhuongTien() {
        this.bienKiemSoat = "";
        this.soChoNgoi = 0;
        this.loaiPhuongTien = "";
        this.phiTheoNgay = 0.0;
    }
 
    // Constructor có tham số
    public PhuongTien(String bienKiemSoat, int soChoNgoi, String loaiPhuongTien) {
        this.bienKiemSoat = bienKiemSoat;
        this.soChoNgoi = soChoNgoi;
        this.loaiPhuongTien = loaiPhuongTien;
        this.phiTheoNgay = 0.0;
    }

    public PhuongTien(String bienKiemSoat, int soChoNgoi, String loaiPhuongTien, double phiTheoNgay) {
        this.bienKiemSoat = bienKiemSoat;
        this.soChoNgoi = soChoNgoi;
        this.loaiPhuongTien = loaiPhuongTien;
        this.phiTheoNgay = phiTheoNgay;
    }

    // Getter
    public String getBienKiemSoat() {
        return bienKiemSoat;
    }

    public int getSoChoNgoi() {
        return soChoNgoi;
    }

    public String getLoaiPhuongTien() {
        return loaiPhuongTien;
    }

    public double getPhiTheoNgay() {
        return phiTheoNgay;
    }

    // Setter
    public void setBienKiemSoat(String bienKiemSoat) {
        this.bienKiemSoat = bienKiemSoat;
    }

    public void setSoChoNgoi(int soChoNgoi) {
        this.soChoNgoi = soChoNgoi;
    }

    public void setLoaiPhuongTien(String loaiPhuongTien) {
        this.loaiPhuongTien = loaiPhuongTien;
    }
    public void setPhiTheoNgay(double phiTheoNgay) {
        this.phiTheoNgay = phiTheoNgay;
    }
    
    // Nhập thông tin (có kiểm tra nhập số nguyên cho số chỗ)
    public void nhap(Scanner sc){
        System.out.print("Nhập biển kiểm soát: ");
        this.bienKiemSoat = sc.nextLine().trim();

        System.out.print("Nhập số chỗ ngồi: ");
        while (!sc.hasNextInt()) {
            System.out.print("  Số nguyên, nhập lại: ");
            sc.next();
        }
        this.soChoNgoi = sc.nextInt();
        sc.nextLine(); // dọn bộ đệm

        System.out.print("Nhập loại phương tiện: ");
        this.loaiPhuongTien = sc.nextLine().trim();

        System.out.print("Nhập phí thuê (VND) theo ngày: ");
        while (!sc.hasNextDouble()) {
            System.out.print("  Số, nhập lại: ");
            sc.next();
        }
        this.phiTheoNgay = sc.nextDouble();
        sc.nextLine();
    }
    
    // Xuất thông tin
    public void xuat(){
        // Single-line summary: Bien | SoCho | Loai | Phi/ngay
        String fee = String.format("%,.0f VND", phiTheoNgay);
        System.out.printf("%s | %d chỗ | %s | %s\n", bienKiemSoat, soChoNgoi, loaiPhuongTien, fee);
    }
}
