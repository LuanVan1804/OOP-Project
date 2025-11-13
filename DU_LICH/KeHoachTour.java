package DU_LICH;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Scanner;

public class KeHoachTour {
    private String maKHTour;
    private String maTour;
    private String maHDV;
    private int tongSoVe;
    private double giaVe;
    private int tongVeDaDat;
    private double tongTienThuVe; // Tính tự động
    private Date ngayDi;
    private Date ngayVe;
    private ChiPhiKHTour chiPhi;

    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private static final SimpleDateFormat SDF = new SimpleDateFormat(DATE_PATTERN, new Locale("vi", "VN"));

    // Scanner dùng chung
    protected static final Scanner sc = new Scanner(System.in);

    public KeHoachTour() {
        this.maKHTour = "";
        this.maTour = "";
        this.maHDV = "";
        this.tongSoVe = 0;
        this.giaVe = 0.0;
        this.tongVeDaDat = 0;
        this.tongTienThuVe = 0.0;
        this.ngayDi = null;
        this.ngayVe = null;
        this.chiPhi = new ChiPhiKHTour();
    }

    public KeHoachTour(String maKHTour, String maTour, String maHDV, int tongSoVe, double giaVe,
                       int tongVeDaDat, Date ngayDi, Date ngayVe, String maNhaHang, String maKhachSan,
                       double tongTienAn, double tongTienPhong) {
        this.maKHTour = maKHTour;
        this.maTour = maTour;
        this.maHDV = maHDV;
        this.tongSoVe = tongSoVe;
        this.giaVe = giaVe;
        this.tongVeDaDat = tongVeDaDat;
        this.ngayDi = ngayDi;
        this.ngayVe = ngayVe;
        this.chiPhi = new ChiPhiKHTour(maKHTour, maNhaHang, maKhachSan, tongTienPhong, tongTienAn);
        updateTongTienThuVe();
    }

    // Getter/Setter
    public String getMaKHTour() { return maKHTour; }
    public void setMaKHTour(String maKHTour) { 
        this.maKHTour = maKHTour; 
        this.chiPhi.setMaKHTour(maKHTour); 
    }
    public String getMaTour() { return maTour; }
    public void setMaTour(String maTour) { this.maTour = maTour; }
    public String getMaHDV() { return maHDV; }
    public void setMaHDV(String maHDV) { this.maHDV = maHDV; }
    public int getTongSoVe() { return tongSoVe; }
    public void setTongSoVe(int tongSoVe) { this.tongSoVe = tongSoVe; }
    public double getGiaVe() { return giaVe; }
    public void setGiaVe(double giaVe) { 
        this.giaVe = giaVe; 
        updateTongTienThuVe(); 
    }
    public int getTongVeDaDat() { return tongVeDaDat; }
    public void setTongVeDaDat(int tongVeDaDat) { 
        this.tongVeDaDat = tongVeDaDat; 
        updateTongTienThuVe(); 
    }
    public double getTongTienThuVe() { return tongTienThuVe; }
    public Date getNgayDi() { return ngayDi; }
    public void setNgayDi(Date ngayDi) { this.ngayDi = ngayDi; }
    public Date getNgayVe() { return ngayVe; }
    public void setNgayVe(Date ngayVe) { this.ngayVe = ngayVe; }
    public ChiPhiKHTour getChiPhi() { return chiPhi; }
    public void setChiPhi(ChiPhiKHTour chiPhi) { this.chiPhi = chiPhi; }

    // Methods tính chi phí từ chiPhi
    public double getTongChiPhiAn() { return chiPhi.getTongTienAn(); }
    public double getTongChiPhiO() { return chiPhi.getTongTienPhong(); }
    public double getTongChiPhiAll() { 
        return getTongChiPhiAn() + getTongChiPhiO(); // + các chi phí khác nếu có
    }

    private void updateTongTienThuVe() {
        this.tongTienThuVe = this.tongVeDaDat * this.giaVe;
    }

    // Nhập thông tin kế hoạch tour
    public void nhapThongTin() {
        System.out.print("Nhap Ma KHTour: ");
        maKHTour = sc.nextLine();
        chiPhi.setMaKHTour(maKHTour);

        System.out.print("Nhap Ma Tour: ");
        maTour = sc.nextLine();

        System.out.print("Nhap Ma HDV: ");
        maHDV = sc.nextLine();

        System.out.print("Nhap Tong So Ve: ");
        tongSoVe = sc.nextInt(); sc.nextLine();
        // Lưu ý: không nhập giá vé ở đây; giá vé sẽ được lấy từ đối tượng Tour (DSTour)
        System.out.print("Nhap Tong Ve Da Dat: ");
        tongVeDaDat = sc.nextInt(); sc.nextLine();
        updateTongTienThuVe();

        // Không nhập thông tin chi phí (maNhaHang, maKhachSan, tongTienAn, tongTienPhong)
        // Các giá trị này sẽ được quản lý và thêm vào thông qua DSChiPhiKHTour.

        while (true) {
            System.out.print("Nhap Ngay Di (dd/MM/yyyy): ");
            String s = sc.nextLine();
            try {
                java.util.Date util = SDF.parse(s.trim());
                this.ngayDi = new Date(util.getTime());
                break;
            } catch (ParseException e) {
                System.out.println("Sai dinh dang ngay. Vui long nhap lai theo dd/MM/yyyy.");
            }
        }

        while (true) {
            System.out.print("Nhap Ngay Ve (dd/MM/yyyy): ");
            String s = sc.nextLine();
            try {
                java.util.Date util = SDF.parse(s.trim());
                this.ngayVe = new Date(util.getTime());
                if (this.ngayVe.after(this.ngayDi)) {
                    break;
                } else {
                    System.out.println("Ngay ve phai sau ngay di. Nhap lai.");
                }
            } catch (ParseException e) {
                System.out.println("Sai dinh dang ngay. Vui long nhap lai theo dd/MM/yyyy.");
            }
        }
    }

    // Hiển thị thông tin
    public void hienThiThongTin() {
        String di = (this.ngayDi == null) ? "(trong)" : SDF.format(new java.util.Date(this.ngayDi.getTime()));
        String ve = (this.ngayVe == null) ? "(trong)" : SDF.format(new java.util.Date(this.ngayVe.getTime()));
        System.out.println("Ma KHTour: " + maKHTour);
        System.out.println("Ma Tour: " + maTour);
        System.out.println("Ma HDV: " + maHDV);
        System.out.println("Tong So Ve: " + tongSoVe);
        System.out.println("Gia Ve: " + giaVe);
        System.out.println("Tong Ve Da Dat: " + tongVeDaDat);
        System.out.println("Tong Tien Thu Ve: " + tongTienThuVe);
        System.out.println("Tong Chi Phi An: " + getTongChiPhiAn());
        System.out.println("Tong Chi Phi O: " + getTongChiPhiO());
        System.out.println("Tong Chi Phi All: " + getTongChiPhiAll());
        System.out.println("Ngay Di: " + di);
        System.out.println("Ngay Ve: " + ve);
        System.out.println("Chi Phi Chi Tiet:");
        System.out.println("  Ma Nha Hang: " + chiPhi.getMaNhaHang());
        System.out.println("  Ma Khach San: " + chiPhi.getMaKhachSan());
    }
}