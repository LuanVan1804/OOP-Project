package DU_LICH.TourDuLich;

public class TourTrongNuoc extends Tour {
    private double phiDichVu;

    public TourTrongNuoc() {
        super();
        this.phiDichVu = 0.0;
    }

    public TourTrongNuoc(int maTour, String tenTour, int soNgay, double donGia, int maThanhPho,
                         String diaDiemDen, String diaDiemDi, double phiDichVu) {
        super(maTour, tenTour, soNgay, donGia, maThanhPho, diaDiemDen, diaDiemDi);
        this.phiDichVu = phiDichVu;
    }

    public double getPhiDichVu() { return phiDichVu; }
    public void setPhiDichVu(double phiDichVu) { this.phiDichVu = phiDichVu; }

    @Override
    public void hienThiThongTin() {
        // Hiển thị thông tin cơ bản
        System.out.println("Ma Tour: " + maTour);
        System.out.println("Ten Tour: " + tenTour);
        System.out.println("So Ngay: " + soNgay);
        System.out.println("Don Gia: " + donGia);
        System.out.println("Ma Thanh Pho: " + maThanhPho);
        System.out.println("Dia Diem Den: " + diaDiemDen);
        System.out.println("Dia Diem Di: " + diaDiemDi);
        System.out.println("Phi Dich Vu: " + phiDichVu);
        System.out.println("Tong Gia Tour: " + tinhGiaTour());
    }

    @Override
    public void nhapThongTin() {
        // Nhập các trường cơ bản
        System.out.print("Nhap Ma Tour: ");
        maTour = sc.nextInt(); sc.nextLine();
        System.out.print("Nhap Ten Tour: ");
        tenTour = sc.nextLine();
        System.out.print("Nhap So Ngay: ");
        soNgay = sc.nextInt(); sc.nextLine();
        System.out.print("Nhap Don Gia: ");
        donGia = sc.nextDouble(); sc.nextLine();
        System.out.print("Nhap Ma Thanh Pho: ");
        maThanhPho = sc.nextInt(); sc.nextLine();
        System.out.print("Nhap Dia Diem Den: ");
        diaDiemDen = sc.nextLine();
        System.out.print("Nhap Dia Diem Di: ");
        diaDiemDi = sc.nextLine();

        System.out.print("Nhap Phi Dich Vu: ");
        phiDichVu = sc.nextDouble();
        sc.nextLine(); // bỏ dòng trống
    }

    public double tinhGiaTour() {
        return (donGia * soNgay) + phiDichVu;
    }
}
