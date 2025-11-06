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
        // Hiển thị thông tin cơ bản của Tour (Tour không còn triển khai phương thức này),
        // do đó in trực tiếp các trường được kế thừa.
        System.out.println("Ma Tour: " + maTour);
        System.out.println("Ten Tour: " + tenTour);
        System.out.println("So Ngay: " + soNgay);
        System.out.println("Don Gia: " + donGia);
        System.out.println("Ma Quoc Gia: " + maQuocGia);
        System.out.println("Ma Thanh Pho: " + maThanhPho);
        System.out.println("Dia Diem Den: " + diaDiemDen);
        System.out.println("Dia Diem Di: " + diaDiemDi);
        System.out.println("Visa: " + visa);
        System.out.println("Don Vi Tien Te: " + donViTienTe);
        System.out.println("TTong Gia Tour: " + tinhGiaTour());
    }

    @Override
    public void nhapThongTin() {
        // Nhập các trường cơ bản
        System.out.print("Nhap Ma Tour: ");
        maTour = sc.nextLine();
        System.out.print("Nhap Ten Tour: ");
        tenTour = sc.nextLine();
        System.out.print("Nhap So Ngay: ");
        soNgay = sc.nextInt(); sc.nextLine();
        System.out.print("Nhap Don Gia: ");
        donGia = sc.nextDouble(); sc.nextLine();
        System.out.print("Nhap Ma Thanh Pho: ");
        maThanhPho = sc.nextLine();
        System.out.print("Nhap Dia Diem Den: ");
        diaDiemDen = sc.nextLine();
        System.out.print("Nhap Dia Diem Di: ");
        diaDiemDi = sc.nextLine();

        // Nhập phần mở rộng
        System.out.print("Nhap Phi Visa: ");
        visa = sc.nextDouble();
        sc.nextLine(); // bỏ dòng trống
        System.out.print("Nhap Don Vi Tien Te: ");
        donViTienTe = sc.nextLine();
    }

    public double tinhGiaTour() {
        return (donGia * soNgay) + visa;
    }
}
