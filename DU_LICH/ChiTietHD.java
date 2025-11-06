package DU_LICH;

import DU_LICH.Nguoi.KhachHang;

public class ChiTietHD {
    private HoaDon hoaDon;
    private KeHoachTour keHoachTour;
    private KhachHang[] danhSachKhachHang;
    private double giaVe;

    public ChiTietHD() {
        this.hoaDon = new HoaDon();
        this.keHoachTour = new KeHoachTour();
        this.danhSachKhachHang = new KhachHang[0];
        this.giaVe = 0.0;
    }

    public ChiTietHD(HoaDon hoaDon, KeHoachTour keHoachTour, KhachHang[] danhSachKhachHang, double giaVe) {
        this.hoaDon = hoaDon;
        this.keHoachTour = keHoachTour;
        this.danhSachKhachHang = danhSachKhachHang;
        this.giaVe = giaVe;
    }

    // Getter - Setter
    public HoaDon getHoaDon() { return hoaDon; }
    public void setHoaDon(HoaDon hoaDon) { this.hoaDon = hoaDon; }

    public KeHoachTour getKeHoachTour() { return keHoachTour; }
    public void setKeHoachTour(KeHoachTour keHoachTour) { this.keHoachTour = keHoachTour; }

    public KhachHang[] getDanhSachKhachHang() { return danhSachKhachHang; }
    public void setDanhSachKhachHang(KhachHang[] danhSachKhachHang) { this.danhSachKhachHang = danhSachKhachHang; }

    public double getGiaVe() { return giaVe; }
    public void setGiaVe(double giaVe) { this.giaVe = giaVe; }


}
