package DU_LICH.TourDuLich;

import java.util.Scanner;

public abstract class Tour {
    protected String maTour;
    protected String tenTour;
    protected int soNgay;
    protected double donGia;
    protected String maThanhPho;
    protected String diaDiemDen;
    protected String diaDiemDi;

    // Scanner dung chung
    protected static final Scanner sc = new Scanner(System.in);

    // constructor khong tham so
    public Tour() {
        this.maTour = "";
        this.tenTour = "";
        this.soNgay = 0;
        this.donGia = 0.0;
        this.maThanhPho = "";
        this.diaDiemDen = "";
        this.diaDiemDi = "";
    }

    // constructor co tham so
    public Tour(String maTour, String tenTour, int soNgay, double donGia, String maThanhPho, String diaDiemDen, String diaDiemDi) {
        this.maTour = maTour;
        this.tenTour = tenTour;
        this.soNgay = soNgay;
        this.donGia = donGia;
        this.maThanhPho = maThanhPho;
        this.diaDiemDen = diaDiemDen;
        this.diaDiemDi = diaDiemDi;
    }

    // Constructor sao chep
    public Tour(Tour other) {
        this.maTour = other.maTour;
        this.tenTour = other.tenTour;
        this.soNgay = other.soNgay;
        this.donGia = other.donGia;
        this.maThanhPho = other.maThanhPho;
    }

    // getter/setter
    public String getMaTour() { return maTour; }
    public void setMaTour(String maTour) { this.maTour = maTour; }
    public String getTenTour() { return tenTour; }
    public void setTenTour(String tenTour) { this.tenTour = tenTour; }
    public int getSoNgay() { return soNgay; }
    public void setSoNgay(int soNgay) { this.soNgay = soNgay; }
    public double getDonGia() { return donGia; }
    public void setDonGia(double donGia) { this.donGia = donGia; }
    public String getMaThanhPho() { return maThanhPho; }
    public void setMaThanhPho(String maThanhPho) { this.maThanhPho = maThanhPho; }
    public String getDiaDiemDen() { return diaDiemDen; }
    public void setDiaDiemDen(String diaDiemDen) { this.diaDiemDen = diaDiemDen; }
    public String getDiaDiemDi() { return diaDiemDi; }
    public void setDiaDiemDi(String diaDiemDi) { this.diaDiemDi = diaDiemDi; }


//abstract Loai()
    public abstract String loai();
// Tinh gia tour (tuong doi) - de cac lop con override
    public abstract double tinhGiaTour();
// hien thi tour 
    
   public String toString() {
    // Sap xep lai thong tin cho truc quan: Ma | Ten | So ngay | Don gia | Diem di -> Diem den (Thanh pho)
    return String.format("%-8s | %-28s | %2d ngay | %,12.0f d | %s -> %s (TP: %s)",
        maTour,
        tenTour,
        soNgay,
        donGia,
        diaDiemDi,
        diaDiemDen,
        maThanhPho
    );
}
// ham nhap thong tin tour
    public void nhapThongTin(){
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
    };
    
    // Hien thi thong tin co ban cua tour (cac lop con co the override va goi super)
    public void hienThiThongTin() {
        System.out.println(this.toString());
    }

}