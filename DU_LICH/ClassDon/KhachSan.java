package DU_LICH.ClassDon;

import java.sql.Date;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
public class KhachSan {
    private String maKhachSan;
    private String tenKhachSan;
    private Date NgayDen;
    private Date NgayDi;
    private double giaDatPhong;

    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private static final SimpleDateFormat SDF = new SimpleDateFormat(DATE_PATTERN, new Locale("vi", "VN"));

    public KhachSan() {
        this.maKhachSan = "";
        this.tenKhachSan = "";
        this.NgayDen = null;
        this.NgayDi = null;
        this.giaDatPhong = 0.0;
    }

    public KhachSan(String maKhachSan, String tenKhachSan, Date NgayDen, Date NgayDi, double giaDatPhong) {
        this.maKhachSan = maKhachSan;
        this.tenKhachSan = tenKhachSan;
        this.NgayDen = NgayDen;
        this.NgayDi = NgayDi;
        this.giaDatPhong = giaDatPhong;
    }

    // Copy constructor
    public KhachSan(KhachSan other) {
        if (other == null) return;
        this.maKhachSan = other.maKhachSan;
        this.tenKhachSan = other.tenKhachSan;
        this.NgayDen = other.NgayDen == null ? null : new Date(other.NgayDen.getTime());
        this.NgayDi = other.NgayDi == null ? null : new Date(other.NgayDi.getTime());
        this.giaDatPhong = other.giaDatPhong;
    }

    public String getMaKhachSan() {
        return maKhachSan;
    }
    public void setMaKhachSan(String maKhachSan) {
        this.maKhachSan = maKhachSan;
    }

    public String getTenKhachSan() {
        return tenKhachSan;
    }
    public void setTenKhachSan(String tenKhachSan) {
        this.tenKhachSan = tenKhachSan;
    }

    public Date getNgayDen() {
        return NgayDen;
    }
    public void setNgayDen(Date NgayDen) {
        this.NgayDen = NgayDen;
    }

    public Date getNgayDi() {
        return NgayDi;
    }
    public void setNgayDi(Date NgayDi) {
        this.NgayDi = NgayDi;
    }

    public double getGiaDatPhong() {
        return giaDatPhong;
    }
    public void setGiaDatPhong(double giaDatPhong) {
        this.giaDatPhong = giaDatPhong;
    }
    
    public void nhap(Scanner sc){
        System.out.print("Nhap ma khach san: ");
        this.maKhachSan = sc.nextLine().trim();
        System.out.print("Nhap ten khach san: ");
        this.tenKhachSan = sc.nextLine().trim();

        while (true) {
            System.out.print("Nhap ngay den (dd/MM/yyyy): ");
            String s = sc.nextLine();
            try {
                java.util.Date util = SDF.parse(s.trim());
                this.NgayDen = new Date(util.getTime());
                break;
            } catch (ParseException e) {
                System.out.println("Sai dinh dang ngay. Vui long nhap lai theo dd/MM/yyyy.");
            }
        }

        while (true) {
            System.out.print("Nhap ngay di (dd/MM/yyyy): ");
            String s = sc.nextLine();
            try {
                java.util.Date util = SDF.parse(s.trim());
                this.NgayDi = new Date(util.getTime());
                break;
            } catch (ParseException e) {
                System.out.println("Sai dinh dang ngay. Vui long nhap lai theo dd/MM/yyyy.");
            }
        }

        System.out.print("Nhap gia dat phong: ");
        while (!sc.hasNextDouble()) {
            System.out.print("Gia khong hop le, nhap lai: ");
            sc.nextLine();
        }
        this.giaDatPhong = sc.nextDouble();
        sc.nextLine();
    }

    // Giu lai ban nhap() khong tham so (neu ai do dung), nhung se dung dd/MM/yyyy
    public void nhap(){
        Scanner sc = new Scanner(System.in);
        nhap(sc);
    }

    public void xuat(){
        String den = (this.NgayDen == null) ? "(trong)" : SDF.format(new java.util.Date(this.NgayDen.getTime()));
        String di  = (this.NgayDi  == null) ? "(trong)" : SDF.format(new java.util.Date(this.NgayDi.getTime()));
        System.out.printf("%s | %s | Den: %s | Di: %s | Gia dat phong: %, .0f VND\n",
            this.maKhachSan, this.tenKhachSan, den, di, this.giaDatPhong);
    }

    public String toString() {
        String den = (this.NgayDen == null) ? "(trong)" : SDF.format(new java.util.Date(this.NgayDen.getTime()));
        String di  = (this.NgayDi  == null) ? "(trong)" : SDF.format(new java.util.Date(this.NgayDi.getTime()));
        return String.format("%s | %s | Den: %s | Di: %s | Gia dat phong: %, .0f VND",
            maKhachSan, tenKhachSan, den, di, giaDatPhong);
    }
}
