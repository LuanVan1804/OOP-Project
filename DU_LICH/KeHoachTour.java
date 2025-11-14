package DU_LICH;

import java.sql.Date;
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
    private Date ngayDi;
    private Date ngayVe;

    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private static final SimpleDateFormat SDF = new SimpleDateFormat(DATE_PATTERN, new Locale("vi", "VN"));
    protected static final Scanner sc = new Scanner(System.in);

    // Constructor mặc định
    public KeHoachTour() {
        this.maKHTour = "";
        this.maTour = "";
        this.maHDV = "";
        this.tongSoVe = 0;
        this.giaVe = 0.0;
        this.tongVeDaDat = 0;
        this.ngayDi = null;
        this.ngayVe = null;
    }

    // Constructor đầy đủ (dùng khi load từ file – không cần chi phí)
    public KeHoachTour(String maKHTour, String maTour, String maHDV, int tongSoVe, double giaVe,
                       int tongVeDaDat, Date ngayDi, Date ngayVe) {
        this.maKHTour = maKHTour;
        this.maTour = maTour;
        this.maHDV = maHDV;
        this.tongSoVe = tongSoVe;
        this.giaVe = giaVe;
        this.tongVeDaDat = tongVeDaDat;
        this.ngayDi = ngayDi;
        this.ngayVe = ngayVe;
    }
    // constructor sao chép
    public KeHoachTour(KeHoachTour other) {
        this.maKHTour = other.maKHTour;
        this.maTour = other.maTour;
        this.maHDV = other.maHDV;
        this.tongSoVe = other.tongSoVe;
        this.giaVe = other.giaVe;
        this.tongVeDaDat = other.tongVeDaDat;
        this.ngayDi = other.ngayDi;
        this.ngayVe = other.ngayVe;
    }

    // Getter & Setter
    public String getMaKHTour() { return maKHTour; }
    public void setMaKHTour(String maKHTour) { this.maKHTour = maKHTour; }
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

    // Tự động tính tổng tiền thu vé
    public double getTongTienThuVe() {
        return tongVeDaDat * giaVe;
    }

    // Lấy chi phí từ DSChiPhiKHTour (không lưu trong đối tượng)
    public ChiPhiKHTour getChiPhi(DSChiPhiKHTour dsChiPhi) {
        return dsChiPhi != null ? dsChiPhi.timTheoMa(this.maKHTour) : null;
    }

    public double getTongChiPhiAll(DSChiPhiKHTour dsChiPhi) {
        ChiPhiKHTour cp = getChiPhi(dsChiPhi);
        return cp != null ? cp.getTongTienAn() + cp.getTongTienPhong() : 0.0;
    }

    public double getTongChiPhiAn(DSChiPhiKHTour dsChiPhi) {
        ChiPhiKHTour cp = getChiPhi(dsChiPhi);
        return cp != null ? cp.getTongTienAn() : 0.0;
    }

    public double getTongChiPhiO(DSChiPhiKHTour dsChiPhi) {
        ChiPhiKHTour cp = getChiPhi(dsChiPhi);
        return cp != null ? cp.getTongTienPhong() : 0.0;
    }

    public String getMaNhaHang(DSChiPhiKHTour dsChiPhi) {
        ChiPhiKHTour cp = getChiPhi(dsChiPhi);
        return cp != null ? cp.getMaNhaHang() : "Chưa có";
    }

    public String getMaKhachSan(DSChiPhiKHTour dsChiPhi) {
        ChiPhiKHTour cp = getChiPhi(dsChiPhi);
        return cp != null ? cp.getMaKhachSan() : "Chưa có";
    }

    // Tính lợi nhuận
    public double getLoiNhuan(DSChiPhiKHTour dsChiPhi) {
        return getTongTienThuVe() - getTongChiPhiAll(dsChiPhi);
    }

    // Nhập thông tin (không nhập chi phí)
    public void nhapThongTin() {
        System.out.print("Nhap Ma KHTour: ");
        maKHTour = sc.nextLine().trim();

        System.out.print("Nhap Ma Tour: ");
        maTour = sc.nextLine().trim();

        System.out.print("Nhap Ma HDV: ");
        maHDV = sc.nextLine().trim();

        System.out.print("Nhap Tong So Ve: ");
        while (!sc.hasNextInt()) {
            System.out.println("Vui long nhap so!");
            sc.next();
        }
        tongSoVe = sc.nextInt();
        sc.nextLine();

        System.out.print("Nhap Tong Ve Da Dat: ");
        while (!sc.hasNextInt()) {
            System.out.println("Vui long nhap so!");
            sc.next();
        }
        tongVeDaDat = sc.nextInt();
        sc.nextLine();

        // Nhập ngày đi
        while (true) {
            System.out.print("Nhap Ngay Di (dd/MM/yyyy): ");
            String s = sc.nextLine().trim();
            if (s.isEmpty()) {
                System.out.println("Ngay di khong duoc trong!");
                continue;
            }
            try {
                java.util.Date util = SDF.parse(s);
                this.ngayDi = new Date(util.getTime());
                break;
            } catch (Exception e) {
                System.out.println("Sai dinh dang ngay! Vui long nhap lai (dd/MM/yyyy)");
            }
        }

        // Nhập ngày về
        while (true) {
            System.out.print("Nhap Ngay Ve (dd/MM/yyyy): ");
            String s = sc.nextLine().trim();
            if (s.isEmpty()) {
                System.out.println("Ngay ve khong duoc trong!");
                continue;
            }
            try {
                java.util.Date util = SDF.parse(s);
                this.ngayVe = new Date(util.getTime());
                if (this.ngayVe.after(this.ngayDi)) {
                    break;
                } else {
                    System.out.println("Ngay ve phai sau ngay di! Nhap lai.");
                }
            } catch (Exception e) {
                System.out.println("Sai dinh dang ngay! Vui long nhap lai (dd/MM/yyyy)");
            }
        }
    }

    // Hiển thị thông tin (cần truyền dsChiPhi để lấy chi phí)
    public void hienThiThongTin(DSChiPhiKHTour dsChiPhi) {
        String di = (ngayDi != null) ? SDF.format(ngayDi) : "Chua co";
        String ve = (ngayVe != null) ? SDF.format(ngayVe) : "Chua co";

        System.out.println("==================================");
        System.out.println("Ma KHTour       : " + maKHTour);
        System.out.println("Ma Tour         : " + maTour);
        System.out.println("Ma HDV          : " + maHDV);
        System.out.println("Tong So Ve      : " + tongSoVe);
        System.out.println("Gia Ve          : " + String.format("%,.0f", giaVe));
        System.out.println("Ve Da Dat       : " + tongVeDaDat);
        System.out.println("Tong Thu Ve     : " + String.format("%,.0f", getTongTienThuVe()));
        System.out.println("Ngay Di         : " + di);
        System.out.println("Ngay Ve         : " + ve);
        System.out.println("--- Chi Phi ---");
        System.out.println("Ma Nha Hang     : " + getMaNhaHang(dsChiPhi));
        System.out.println("Ma Khach San    : " + getMaKhachSan(dsChiPhi));
        System.out.println("Tong Tien An    : " + String.format("%,.0f", getTongChiPhiAn(dsChiPhi)));
        System.out.println("Tong Tien Phong : " + String.format("%,.0f", getTongChiPhiO(dsChiPhi)));
        System.out.println("Tong Chi Phi    : " + String.format("%,.0f", getTongChiPhiAll(dsChiPhi)));
        System.out.println("Loi Nhuan       : " + String.format("%,.0f", getLoiNhuan(dsChiPhi)));
        System.out.println("==================================");
    }

    // Hiển thị ngắn gọn (dùng trong danh sách)
    public void hienThiNgan(DSChiPhiKHTour dsChiPhi) {
        String di = (ngayDi != null) ? SDF.format(ngayDi) : "?";
        String ve = (ngayVe != null) ? SDF.format(ngayVe) : "?";
        System.out.printf("%-12s | %-10s | %-8s → %-8s | %-6d/%-6d | Thu: %,12.0f | Chi: %,12.0f | Loi nhuan: %,12.0f%n",
                maKHTour, maTour, di, ve, tongVeDaDat, tongSoVe,
                getTongTienThuVe(), getTongChiPhiAll(dsChiPhi), getLoiNhuan(dsChiPhi));
    }
}