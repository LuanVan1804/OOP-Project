package DU_LICH.ClassDon;

import java.util.Scanner;

public class PhuongTien {
    private String bienKiemSoat;
    private int soChoNgoi;
    private String loaiPhuongTien;
    private double phiTheoNgay;
    
    // Constructor mac dinh
    public PhuongTien() {
        this.bienKiemSoat = "";
        this.soChoNgoi = 0;
        this.loaiPhuongTien = "";
        this.phiTheoNgay = 0.0;
    }
 
    // Constructor co tham so
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

    // Copy constructor
    public PhuongTien(PhuongTien other) {
        if (other == null) return;
        this.bienKiemSoat = other.bienKiemSoat;
        this.soChoNgoi = other.soChoNgoi;
        this.loaiPhuongTien = other.loaiPhuongTien;
        this.phiTheoNgay = other.phiTheoNgay;
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
    
    // Nhap thong tin (co kiem tra nhap so nguyen cho so cho)
    public void nhap(Scanner sc){
        System.out.print("Nhap bien kiem soat: ");
        this.bienKiemSoat = sc.nextLine().trim();

        System.out.print("Nhap so cho ngoi: ");
        while (!sc.hasNextInt()) {
            System.out.print("  So nguyen, nhap lai: ");
            sc.next();
        }
        this.soChoNgoi = sc.nextInt();
        sc.nextLine(); // don bo dem

        System.out.print("Nhap loai phuong tien: ");
        this.loaiPhuongTien = sc.nextLine().trim();

        System.out.print("Nhap phi thue (VND) theo ngay: ");
        while (!sc.hasNextDouble()) {
            System.out.print("  So, nhap lai: ");
            sc.next();
        }
        this.phiTheoNgay = sc.nextDouble();
        sc.nextLine();
    }
    
    // Xuat thong tin
    public void xuat(){
        // Single-line summary: Bien | SoCho | Loai | Phi/ngay
        String fee = String.format("%,.0f VND", phiTheoNgay);
        System.out.printf("%s | %d cho ngoi | %s | %s\n", bienKiemSoat, soChoNgoi, loaiPhuongTien, fee);
    }
}
