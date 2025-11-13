package DU_LICH.TourDuLich;

public class TourNuocNgoai extends Tour {
    private double visa;
    private String donViTienTe;
    private String maQuocGia;

    public TourNuocNgoai() {
        super();
        this.visa = 0.0;
        this.donViTienTe = "";
        this.maQuocGia = "";
    }

    //constructor sao chép
    public TourNuocNgoai(TourNuocNgoai other) {
        super(other); 
        this.maQuocGia = other.maQuocGia;
        this.visa = other.visa;
        this.donViTienTe = other.donViTienTe;
    }

    // New constructor includes maQuocGia for foreign tours
    public TourNuocNgoai(String maTour, String tenTour, int soNgay, double donGia, String maQuocGia,
                         String maThanhPho, String diaDiemDen, String diaDiemDi, double visa, String donViTienTe) {
        super(maTour, tenTour, soNgay, donGia, maThanhPho, diaDiemDen, diaDiemDi);
        this.maQuocGia = maQuocGia;
        this.visa = visa;
        this.donViTienTe = donViTienTe;
    }

    public double getVisa() { return visa; }
    public void setVisa(double visa) { this.visa = visa; }
    public String getDonViTienTe() { return donViTienTe; }
    public void setDonViTienTe(String donViTienTe) { this.donViTienTe = donViTienTe; }

    public String getMaQuocGia() { return maQuocGia; }
    public void setMaQuocGia(String maQuocGia) { this.maQuocGia = maQuocGia; }

    @Override
    public void hienThiThongTin() {
        // Hiển thị tất cả thông tin trên một hàng, dựa trên toString() của lớp cha
        System.out.printf("%s | Ma Quoc Gia: %s | Visa: %,.0f | Don Vi Tien Te: %s | Tong Gia Tour: %,.0f%n",
                super.toString(), maQuocGia, visa, donViTienTe, tinhGiaTour());
    }

    @Override
    public void nhapThongTin() {
        // kế thừa nhập thông tin cơ bản
        super.nhapThongTin();
        // Nhập phần mở rộng
        System.out.print("Nhap Ma Quoc Gia: ");
        maQuocGia = sc.nextLine();
        System.out.print("Nhap Phi Visa: ");
        visa = sc.nextDouble();
        sc.nextLine();
        System.out.print("Nhap Don Vi Tien Te: ");
        donViTienTe = sc.nextLine();
    }

    //Loại tour
    @Override
    public String loai() {
        return "NuocNgoai";
    }
    @Override
    public double tinhGiaTour() {
        return (donGia * soNgay) + visa;
    }
}
