package DU_LICH;

public class ChiTietHD {
    // THUOC TINH
    private String maHD;
    private String maKHTour;
    private int[] dsMaKhachHang;
    private double giaVe;

    // NEW: Tham chieu den cac DS de lay thong tin chi tiet
    private DSKHTour dsKHTour;
    private DSChiPhiKHTour dsChiPhi;

    // ------------------------------------
    // CONSTRUCTOR
    // ------------------------------------
    public ChiTietHD() {
        this.maHD = "";
        this.maKHTour = "";
        this.dsMaKhachHang = new int[0];
        this.giaVe = 0.0;
    }

    public ChiTietHD(String maHD, String maKHTour, int[] dsMaKhachHang, double giaVe) {
        this.maHD = maHD;
        this.maKHTour = maKHTour;
        this.dsMaKhachHang = dsMaKhachHang;
        this.giaVe = giaVe;
    }

    public ChiTietHD(ChiTietHD ct) {
        this.maHD = ct.maHD;
        this.maKHTour = ct.maKHTour;
        this.dsMaKhachHang = ct.dsMaKhachHang.clone();
        this.giaVe = ct.giaVe;
        this.dsKHTour = ct.dsKHTour;
        this.dsChiPhi = ct.dsChiPhi;
    }

    // ------------------------------------
    // GETTER/SETTER
    // ------------------------------------
    public String getMaHD() { return maHD; }
    public void setMaHD(String maHD) { this.maHD = maHD; }

    public String getMaKHTour() { return maKHTour; }
    public void setMaKHTour(String maKHTour) { this.maKHTour = maKHTour; }

    public int[] getDsMaKhachHang() { return dsMaKhachHang; }
    public void setDsMaKhachHang(int[] dsMaKhachHang) {
        this.dsMaKhachHang = dsMaKhachHang;
    }

    public double getGiaVe() { return giaVe; }
    public void setGiaVe(double giaVe) { this.giaVe = giaVe; }

    // NEW: Setter cho cac DS
    public void setDsKHTour(DSKHTour dsKHTour) { this.dsKHTour = dsKHTour; }
    public void setDsChiPhi(DSChiPhiKHTour dsChiPhi) { this.dsChiPhi = dsChiPhi; }

    // ------------------------------------
    // TINH TONG TIEN
    // ------------------------------------
    public double getTongTien() {
        double tienVe = giaVe * dsMaKhachHang.length;
        
        if (dsChiPhi == null)
            return tienVe;

        ChiPhiKHTour chiPhi = dsChiPhi.timTheoMa(maKHTour);
        if (chiPhi == null)
            return tienVe;

        return tienVe + chiPhi.getTongTienAn() + 
               chiPhi.getTongTienPhuongTien() + 
               chiPhi.getTongTienPhong();
    }

    // ------------------------------------
    // XUAT THONG TIN CHI TIET
    // ------------------------------------
    public void xuatThongTin() {
        System.out.println("┌────────────────────────────────────────────────────────────────┐");
        System.out.println("│                    CHI TIET HOA DON                            │");
        System.out.println("├────────────────────────────────────────────────────────────────┤");
        
        System.out.printf("│ Ma hoa don          : %-36s │%n", maHD);
        
        // Lay thong tin ke hoach tour
        if (dsKHTour != null) {
            KeHoachTour kht = dsKHTour.timTheoMaObject(maKHTour);
            if (kht != null) {
                System.out.printf("│ Ma tour             : %-36s │%n", kht.getMaTour());
                System.out.printf("│ Ngay di             : %-36s │%n", kht.getNgayDi());
                System.out.printf("│ Ngay ve             : %-36s │%n", kht.getNgayVe());
            }
        }
        
        // Lay thong tin chi phi (nha hang, khach san, phuong tien)
        if (dsChiPhi != null) {
            ChiPhiKHTour chiPhi = dsChiPhi.timTheoMa(maKHTour);
            if (chiPhi != null) {
                System.out.printf("│ Nha hang            : %-36s │%n", chiPhi.getMaNhaHang());
                System.out.printf("│ Khach san           : %-36s │%n", chiPhi.getMaKhachSan());
                System.out.printf("│ Phuong tien         : %-36s │%n", chiPhi.getMaPhuongTien());
            }
        }
        
        // Danh sach khach hang
        StringBuilder dsKH = new StringBuilder();
        for (int i = 0; i < dsMaKhachHang.length; i++) {
            dsKH.append(dsMaKhachHang[i]);
            if (i < dsMaKhachHang.length - 1) dsKH.append(", ");
        }
        System.out.printf("│ DS khach hang       : %-36s │%n", dsKH.toString());
        System.out.printf("│ So khach            : %-36d │%n", dsMaKhachHang.length);
        System.out.printf("│ Gia ve/khach        : %-36s │%n", String.format("%,.0f VND", giaVe));
        System.out.printf("│ Tong tien           : %-36s │%n", String.format("%,.0f VND", getTongTien()));
        System.out.println("└────────────────────────────────────────────────────────────────┘");
    }

    @Override
    public String toString() {
        return "ChiTietHD {maHD=" + maHD + 
               ", maKHTour=" + maKHTour + 
               ", soKhach=" + dsMaKhachHang.length + 
               ", tongTien=" + getTongTien() + "}";
    }
}
