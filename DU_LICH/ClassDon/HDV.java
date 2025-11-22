package DU_LICH.ClassDon;

import java.util.Scanner;

public class HDV {
    int maHDV;
    String tenHDV;
    String soDienThoai;
    String CCCD;
    String gioiTinh;
    double kinhNghiem;
    public HDV() {
        this.maHDV = 0;
        this.tenHDV = "";
        this.soDienThoai = "";
        this.CCCD = "";
        this.gioiTinh = "";
        this.kinhNghiem = 0.0;
    }
    public HDV(int maHDV, String tenHDV, String soDienThoai, String CCCD, String gioiTinh, double kinhNghiem) {
        this.maHDV = maHDV;
        this.tenHDV = tenHDV;
        this.soDienThoai = soDienThoai;
        this.CCCD = CCCD;
        this.gioiTinh = gioiTinh;
        this.kinhNghiem = kinhNghiem;
    }
    public int getMaHDV() {
        return maHDV;
    }
    public void setMaHDV(int maHDV) {
        this.maHDV = maHDV;
    }
    public String getTenHDV() {
        return tenHDV;
    }
    public void setTenHDV(String tenHDV) {
        this.tenHDV = tenHDV;
    }
    public String getSoDienThoai() {
        return soDienThoai;
    }
    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }
    public String getCCCD() {
        return CCCD;
    }
    public void setCCCD(String CCCD) {
        this.CCCD = CCCD;
    }
    public String getGioiTinh() {
        return gioiTinh;
    }
    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }
    public double getKinhNghiem() {
        return kinhNghiem;
    }
    public void setKinhNghiem(double kinhNghiem) {
        this.kinhNghiem = kinhNghiem;
    }

    // Copy constructor
    public HDV(HDV other) {
        if (other == null) {
            this.maHDV = 0;
            this.tenHDV = "";
            this.soDienThoai = "";
            this.CCCD = "";
            this.gioiTinh = "";
            this.kinhNghiem = 0.0;
        } else {
            this.maHDV = other.maHDV;
            this.tenHDV = other.tenHDV;
            this.soDienThoai = other.soDienThoai;
            this.CCCD = other.CCCD;
            this.gioiTinh = other.gioiTinh;
            this.kinhNghiem = other.kinhNghiem;
        }
    }

   @Override
    public String toString() {
        return String.format("HDV [Ma: %d, Ten: %s, SDT: %s, CCCD: %s, GT: %s, KN: %.1f nam]",
                maHDV, tenHDV, soDienThoai, CCCD, gioiTinh, kinhNghiem);
    }

    public void nhapThongTinHDV(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap ma HDV: ");
        this.maHDV = Integer.parseInt(sc.nextLine());
        System.out.print("Nhap ten HDV: ");
        this.tenHDV = sc.nextLine();
        System.out.print("Nhap so dien thoai: ");
        this.soDienThoai = sc.nextLine();
        System.out.print("Nhap CCCD: ");
        this.CCCD = sc.nextLine();
        System.out.print("Nhap gioi tinh: ");
        this.gioiTinh = sc.nextLine();
        System.out.print("Nhap kinh nghiem (nam): ");
        this.kinhNghiem = Double.parseDouble(sc.nextLine());
    }

    public void hienThiThongTinHDV() {
    System.out.printf("HDV [Ma: %d, Ten: %s, SDT: %s, CCCD: %s, GT: %s, KN: %.1f nam]%n",
            maHDV, tenHDV, soDienThoai, CCCD, gioiTinh, kinhNghiem);
}
}
