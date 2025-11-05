package DU_LICH.TourDuLich;

import java.util.Scanner;

public abstract class Tour {
    protected int maTour;
    protected String tenTour;
    protected int soNgay;
    protected double donGia;
    protected int maThanhPho;
    protected String diaDiemDen;
    protected String diaDiemDi;

    // Scanner dùng chung
    protected static final Scanner sc = new Scanner(System.in);

    // constructor không tham số
    public Tour() {
        this.maTour = 0;
        this.tenTour = "";
        this.soNgay = 0;
        this.donGia = 0.0;
        this.maThanhPho = 0;
        this.diaDiemDen = "";
        this.diaDiemDi = "";
    }

    // constructor có tham số
    public Tour(int maTour, String tenTour, int soNgay, double donGia, int maThanhPho, String diaDiemDen, String diaDiemDi) {
        this.maTour = maTour;
        this.tenTour = tenTour;
        this.soNgay = soNgay;
        this.donGia = donGia;
        this.maThanhPho = maThanhPho;
        this.diaDiemDen = diaDiemDen;
        this.diaDiemDi = diaDiemDi;
    }

    // getter/setter
    public int getMaTour() { return maTour; }
    public void setMaTour(int maTour) { this.maTour = maTour; }
    public String getTenTour() { return tenTour; }
    public void setTenTour(String tenTour) { this.tenTour = tenTour; }
    public int getSoNgay() { return soNgay; }
    public void setSoNgay(int soNgay) { this.soNgay = soNgay; }
    public double getDonGia() { return donGia; }
    public void setDonGia(double donGia) { this.donGia = donGia; }
    public int getMaThanhPho() { return maThanhPho; }
    public void setMaThanhPho(int maThanhPho) { this.maThanhPho = maThanhPho; }
    public String getDiaDiemDen() { return diaDiemDen; }
    public void setDiaDiemDen(String diaDiemDen) { this.diaDiemDen = diaDiemDen; }
    public String getDiaDiemDi() { return diaDiemDi; }
    public void setDiaDiemDi(String diaDiemDi) { this.diaDiemDi = diaDiemDi; }


   
// hien thi tour 
    public abstract void hienThiThongTin();
// ham nhap thong tin tour
    public abstract void nhapThongTin();
}
