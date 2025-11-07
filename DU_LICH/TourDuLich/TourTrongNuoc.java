package DU_LICH.TourDuLich;

public class TourTrongNuoc extends Tour {
    private double phiDichVu;

    public TourTrongNuoc() {
        super();
        this.phiDichVu = 0.0;
    }

    public TourTrongNuoc(String maTour, String tenTour, int soNgay, double donGia, String maThanhPho,
                         String diaDiemDen, String diaDiemDi, double phiDichVu) {
        super(maTour, tenTour, soNgay, donGia, maThanhPho, diaDiemDen, diaDiemDi);
        this.phiDichVu = phiDichVu;
    }

    //constructor sao chép
    public TourTrongNuoc(TourTrongNuoc other) {
        super(other); 
        this.phiDichVu = other.phiDichVu;
    }

    public double getPhiDichVu() { return phiDichVu; }
    public void setPhiDichVu(double phiDichVu) { this.phiDichVu = phiDichVu; }

    @Override
    public void hienThiThongTin() {
        super.hienThiThongTin();
        System.out.println("Phi Dich Vu: " + phiDichVu);
        System.out.println("Tong Gia Tour: " + tinhGiaTour());
    }

    @Override
    public void nhapThongTin() {
       super.nhapThongTin();
        System.out.print("Nhap Phi Dich Vu: ");
        phiDichVu = sc.nextDouble();
        sc.nextLine(); // bỏ dòng trống
    }

    //Loại tour
    @Override
    public String loai() {
        return "TrongNuoc";
    }
    @Override
    public double tinhGiaTour() {
        return (donGia * soNgay) + phiDichVu;
    }
}
