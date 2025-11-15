package DU_LICH.NH_KS_PT;

import java.util.Scanner;

public class PhuongTien {
    private String bienKiemSoat;
    private int soChoNgoi;
    private String loaiPhuongTien;
    
    // Constructor mặc định
    public PhuongTien() {
        this.bienKiemSoat = "";
        this.soChoNgoi = 0;
        this.loaiPhuongTien = "";
    }
 
    // Constructor có tham số
    public PhuongTien(String bienKiemSoat, int soChoNgoi, String loaiPhuongTien) {
        this.bienKiemSoat = bienKiemSoat;
        this.soChoNgoi = soChoNgoi;
        this.loaiPhuongTien = loaiPhuongTien;
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
    
    // Nhập thông tin (có kiểm tra nhập số nguyên cho số chỗ)
    public void nhap(){
        Scanner sc = new Scanner(System.in);
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
    }
    
    // Xuất thông tin
    public void xuat(){
        System.out.println("Biển kiểm soát  : " + bienKiemSoat );
        System.out.println("Số chỗ ngồi     : " + soChoNgoi );
        System.out.println("Loại phương tiện : " + loaiPhuongTien );
    }
    
    @Override
    public String toString() {
        return "\nBiển kiểm soát  : " + bienKiemSoat
             + "\nSố chỗ ngồi     : " + soChoNgoi
             + "\nLoại phương tiện : " + loaiPhuongTien;
    }
}
