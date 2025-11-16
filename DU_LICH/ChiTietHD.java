package DU_LICH;

public class ChiTietHD {
    // THUOC TINH
    private String maHD;                    // Ma hoa don (FK)
    private String maKHTour;                // Ma ke hoach tour
    private int[] dsMaKhachHang;            // Danh sach ma khach hang di tour
    private double giaVe;
    private double tongTien;

    // CONSTRUCTOR
    public ChiTietHD() {
        this.maHD = "";
        this.maKHTour = "";
        this.dsMaKhachHang = new int[0];
        this.giaVe = 0.0;
        this.tongTien = 0.0;
    }

    public ChiTietHD(String maHD, String maKHTour, int[] dsMaKhachHang, double giaVe) {
        this.maHD = maHD;
        this.maKHTour = maKHTour;
        this.dsMaKhachHang = dsMaKhachHang;
        this.giaVe = giaVe;
        this.tongTien = giaVe * dsMaKhachHang.length;
    }

    // Constructor sao chep
    public ChiTietHD(ChiTietHD ct) {
        this.maHD = ct.maHD;
        this.maKHTour = ct.maKHTour;
        this.dsMaKhachHang = ct.dsMaKhachHang.clone();
        this.giaVe = ct.giaVe;
        this.tongTien = ct.tongTien;
    }

    // GETTER/SETTER
    public String getMaHD() { return maHD; }
    public void setMaHD(String maHD) { this.maHD = maHD; }

    public String getMaKHTour() { return maKHTour; }
    public void setMaKHTour(String maKHTour) { this.maKHTour = maKHTour; }

    public int[] getDsMaKhachHang() { return dsMaKhachHang; }
    public void setDsMaKhachHang(int[] dsMaKhachHang) { 
        this.dsMaKhachHang = dsMaKhachHang; 
        this.tongTien = this.giaVe * dsMaKhachHang.length;
    }

    public double getGiaVe() { return giaVe; }
    public void setGiaVe(double giaVe) { 
        this.giaVe = giaVe; 
        this.tongTien = giaVe * dsMaKhachHang.length;
    }

    public double getTongTien() { return tongTien; }

    // XUAT THONG TIN (in dep)
    public void xuatThongTin() {
        System.out.println("┌──────────────────────────────────────────────────────────────┐");
        System.out.println("│                  CHI TIET HOA DON                           │");
        System.out.println("├──────────────────────────────────────────────────────────────┤");
        
        // Validation: Hien thi canh bao neu maHD null/rong
        String displayMaHD = (maHD == null || maHD.trim().isEmpty()) 
                           ? "[LOI: KHONG CO MA HD]" 
                           : maHD;
        System.out.printf("│ Ma hoa don         : %-35s │%n", displayMaHD);
        System.out.printf("│ Ma ke hoach tour   : %-35s │%n", maKHTour);
        System.out.print("│ DS khach hang (theo id)     : ");
        StringBuilder dsKH = new StringBuilder();
        for (int i = 0; i < dsMaKhachHang.length; i++) {
            dsKH.append(dsMaKhachHang[i]);
            if (i < dsMaKhachHang.length - 1) dsKH.append(", ");
        }
        System.out.printf("%-35s │%n", dsKH.toString());
        System.out.printf("│ Gia ve             : %-35s │%n", String.format("%,.0f VND", giaVe));
        System.out.printf("│ So khach           : %-35d │%n", dsMaKhachHang.length);
        System.out.printf("│ Tong tien          : %-35s │%n", String.format("%,.0f VND", tongTien));
        System.out.println("└──────────────────────────────────────────────────────────────┘");
    }

    // HIEN THI (giu de backward compatible)
    public void hienThiThongTin() {
        xuatThongTin();
    }
}
