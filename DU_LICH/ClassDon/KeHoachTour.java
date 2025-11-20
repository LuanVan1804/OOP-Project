package DU_LICH.ClassDon;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Scanner;

public class KeHoachTour {
    private String maKHTour;
    private String tenKeHoach;      // THÊM MỚI
    private String maTour;
    private String maHDV;
    private int tongSoVe;
    private double giaVe;
    private int tongVeDaDat;        // Sẽ được cập nhật từ Hóa đơn
    private Date ngayDi;
    private Date ngayVe;
    private double tongChi;         // Sẽ được cập nhật từ Chi phí

    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private static final SimpleDateFormat SDF = new SimpleDateFormat(DATE_PATTERN, new Locale("vi", "VN"));
    private static final Scanner sc = new Scanner(System.in);

    // Constructor
    public KeHoachTour() {
        this.tongVeDaDat = 0;
        this.tongChi = 0.0;
    }

    public KeHoachTour(String maKHTour, String tenKeHoach, String maTour, String maHDV,
                       int tongSoVe, double giaVe, Date ngayDi, Date ngayVe) {
        this.maKHTour = maKHTour;
        this.tenKeHoach = tenKeHoach;
        this.maTour = maTour;
        this.maHDV = maHDV;
        this.tongSoVe = tongSoVe;
        this.giaVe = giaVe;
        this.ngayDi = ngayDi;
        this.ngayVe = ngayVe;
        this.tongVeDaDat = 0;
        this.tongChi = 0.0;
    }

    // Getter & Setter
    public String getMaKHTour() { return maKHTour; }
    public void setMaKHTour(String maKHTour) { this.maKHTour = maKHTour; }
    public String getTenKeHoach() { return tenKeHoach; }
    public void setTenKeHoach(String tenKeHoach) { this.tenKeHoach = tenKeHoach; }
    public String getMaTour() { return maTour; }
    public void setMaTour(String maTour) { this.maTour = maTour; }
    public String getMaHDV() { return maHDV; }
    public void setMaHDV(String maHDV) { this.maHDV = maHDV; }
    public int getTongSoVe() { return tongSoVe; }
    public void setTongSoVe(int tongSoVe) { this.tongSoVe = tongSoVe; }
    public double getGiaVe() { return giaVe; }
    public void setGiaVe(double giaVe) { this.giaVe = giaVe; }
    public int getTongVeDaDat() { return tongVeDaDat; }
    public void setTongVeDaDat(int tongVeDaDat) { this.tongVeDaDat = tongVeDaDat; }
    public Date getNgayDi() { return ngayDi; }
    public void setNgayDi(Date ngayDi) { this.ngayDi = ngayDi; }
    public Date getNgayVe() { return ngayVe; }
    public void setNgayVe(Date ngayVe) { this.ngayVe = ngayVe; }
    public double getTongChi() { return tongChi; }
    public void setTongChi(double tongChi) { this.tongChi = tongChi; }

    // Hàm nhập (không nhập tongVeDaDat và tongChi)
    public void nhap() {
        System.out.print("Nhap Ma Ke Hoach Tour: ");
        this.maKHTour = sc.nextLine().trim();

        System.out.print("Nhap Ten Ke Hoach Tour: ");
        this.tenKeHoach = sc.nextLine().trim();

        System.out.print("Nhap Ma Tour: ");
        this.maTour = sc.nextLine().trim();

        System.out.print("Nhap Ma HDV (de trong neu chua co): ");
        this.maHDV = sc.nextLine().trim();

        System.out.print("Nhap Tong So Ve: ");
        while (!sc.hasNextInt()) {
            System.out.print("Vui long nhap so! Nhap lai: ");
            sc.next();
        }
        this.tongSoVe = sc.nextInt();
        sc.nextLine();

        System.out.print("Nhap Gia Ve: ");
        while (!sc.hasNextDouble()) {
            System.out.print("Vui long nhap so! Nhap lai: ");
            sc.next();
        }
        this.giaVe = sc.nextDouble();
        sc.nextLine();

        try {
            System.out.print("Nhap Ngay Di (dd/MM/yyyy): ");
            java.util.Date d = SDF.parse(sc.nextLine().trim());
            this.ngayDi = new Date(d.getTime());
        } catch (Exception e) {
            System.out.println("Ngay khong hop le, dat mac dinh hom nay.");
            this.ngayDi = new Date(System.currentTimeMillis());
        }

        try {
            System.out.print("Nhap Ngay Ve (dd/MM/yyyy): ");
            java.util.Date d = SDF.parse(sc.nextLine().trim());
            Date ve = new Date(d.getTime());
            if (ve.before(this.ngayDi)) {
                System.out.println("Ngay ve phai sau ngay di! Tu dong dat = ngay di + 3.");
                ve = new Date(this.ngayDi.getTime() + 3 * 24 * 60 * 60 * 1000L);
            }
            this.ngayVe = ve;
        } catch (Exception e) {
            System.out.println("Ngay ve khong hop le, tu dong dat.");
            this.ngayVe = new Date(this.ngayDi.getTime() + 3 * 24 * 60 * 60 * 1000L);
        }
    }

    // Hàm hiển thị
    public void hienThi() {
        String di = (ngayDi != null) ? SDF.format(ngayDi) : "Chua co";
        String ve = (ngayVe != null) ? SDF.format(ngayVe) : "Chua co";
        double doanhThu = tongVeDaDat * giaVe;
        System.out.printf("%-10s | %-20s | %-10s | %-23s | %4d/%-4d | %,12.0f | %,12.0f | %,12.0f%n",
                maKHTour, tenKeHoach.length() > 18 ? tenKeHoach.substring(0,18)+"..." : tenKeHoach,
                maTour, di + " - " + ve, tongVeDaDat, tongSoVe,
                doanhThu, tongChi, doanhThu - tongChi);
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%d,%.0f,%s,%s",
                maKHTour, tenKeHoach, maTour, maHDV, tongSoVe, giaVe,
                SDF.format(ngayDi), SDF.format(ngayVe));
    }
}