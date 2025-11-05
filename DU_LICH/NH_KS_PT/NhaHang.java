
package DU_LICH.NH_KS_PT;

import java.util.Scanner;

public class NhaHang {
    private String maNhaHang;
    private String tenNhaHang;
    private String Combo;
    private double giaCombo;

    public NhaHang() {
        this.maNhaHang = "";
        this.tenNhaHang = "";
        this.Combo = "";
        this.giaCombo = 0.0;
    }

    public NhaHang(String maNhaHang, String tenNhaHang, String Combo, double giaCombo) {
        this.maNhaHang = maNhaHang;
        this.tenNhaHang = tenNhaHang;
        this.Combo = Combo;
        this.giaCombo = giaCombo;
    }

    public String getMaNhaHang() {
        return maNhaHang;
    }
    public void setMaNhaHang(String maNhaHang) {
        this.maNhaHang = maNhaHang;
    }

    public String getTenNhaHang() {
        return tenNhaHang;
    }
    public void setTenNhaHang(String tenNhaHang) {
        this.tenNhaHang = tenNhaHang;
    }

    public String getCombo() {
        return Combo;
    }
    public void setCombo(String Combo) {
        this.Combo = Combo;
    }

    public double getGiaCombo() {
        return giaCombo;
    }
    public void setGiaCombo(double giaCombo) {
        this.giaCombo = giaCombo;
    }

    public void nhap(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap ma nha hang: "); 
        this.maNhaHang = sc.nextLine();
        System.out.print("Nhap ten nha hang: ");
        this.tenNhaHang = sc.nextLine();
        System.out.print("Nhap combo: ");
        this.Combo = sc.nextLine();
        System.out.print("Nhap gia combo: ");
        this.giaCombo = sc.nextDouble();
        sc.nextLine();  
    }

    public void xuat(){
        System.out.printf("Ma nha hang: %s\n", this.maNhaHang);
        System.out.printf("Ten nha hang: %s\n", this.tenNhaHang);
        System.out.printf("Combo: %s\n", this.Combo);
        System.out.printf("Gia combo: %.2f\n", this.giaCombo);
    }
    
    public String toString() {
        return "Nha hang: " + maNhaHang + "\n" + "Ten nha hang: " + tenNhaHang + "\n" + "Combo: " + Combo + "\n" + "Gia combo: " + giaCombo;
    }
    
}
