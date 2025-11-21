package DU_LICH.QuanLy;

import java.io.IOException;
import java.util.Scanner;

import DU_LICH.DanhSach.*;
import DU_LICH.TourDuLich.*;

public class QuanLy {
    // Cac danh sach toan cuc (static de tat ca QuanLy con truy cap duoc)
    protected static DSQuocGia dsQuocGia;
    protected static DSThanhPho dsThanhPho;
    protected static DSTour dsTour;
    protected static DSKhachSan dsKhachSan;
    protected static DSNhaHang dsNhaHang;
    protected static DSHDV dsHDV;
    protected static DSKhachHang dsKhachHang;
    protected static DSKHTour dsKeHoach;
    protected static DSChiPhiKHTour dsChiPhi;
    protected static DSHoaDon dsHoaDon;
    protected static DSChiTietHD dsChiTietHD;
    protected static DSPhuongTien dsPhuongTien;

    // Duong dan file (su dung duong dan tuong doi)
    protected static final String PATH_QUOCGIA = "Data/quocGia.txt";
    protected static final String PATH_THANHPHO = "Data/ThanhPho.txt";
    protected static final String PATH_TOUR = "TourDuLich/DSTour.txt";
    protected static final String PATH_KHACHSAN = "Data/KhachSan.txt";
    protected static final String PATH_NHAHANG = "Data/NhaHang.txt";
    protected static final String PATH_KHACHHANG = "Data/KhachHang.txt";
    protected static final String PATH_HDV = "Data/HDV.txt";
    protected static final String PATH_KEHOACH = "Data/KeHoachTour.txt";
    protected static final String PATH_CHIPHI = "Data/ChiPhiKHTour.txt";
    protected static final String PATH_HOADON = "Data/DSHoaDon.txt";
    protected static final String PATH_CHITIET = "Data/DSChiTietHD.txt";
    protected static final String PATH_PHUONGTIEN = "Data/PhuongTien.txt";

    public QuanLy() {
        this(true);
    }

    protected QuanLy(boolean doLoad) {
        if (doLoad) {
            khoiTaoVaLoad();
        }
    }

    // Tao moi tat ca danh sach va doc file tu dong
    private static void khoiTaoVaLoad() {
        dsQuocGia = new DSQuocGia();
        dsThanhPho = new DSThanhPho();
        dsTour = new DSTour();
        dsKhachSan = new DSKhachSan();
        dsNhaHang = new DSNhaHang();
        dsHDV = new DSHDV();
        dsKhachHang = new DSKhachHang();
        dsKeHoach = new DSKHTour();
        dsChiPhi = new DSChiPhiKHTour();
        dsHoaDon = new DSHoaDon();
        dsChiTietHD = new DSChiTietHD();
        dsPhuongTien = new DSPhuongTien();

        try { dsQuocGia.loadFromFile(PATH_QUOCGIA);     } catch (Exception e) { System.out.println("Khong tai duoc QuocGia: " + e.getMessage()); }
        try { dsThanhPho.loadFromFile(PATH_THANHPHO);   } catch (Exception e) { System.out.println("Khong tai duoc ThanhPho: " + e.getMessage()); }
        try { dsTour.loadFromFile(PATH_TOUR);           } catch (Exception e) { System.out.println("Khong tai duoc Tour: " + e.getMessage()); }
        try { dsKhachSan.loadFromFile(PATH_KHACHSAN);   } catch (Exception e) { System.out.println("Khong tai duoc KhachSan: " + e.getMessage()); }
        try { dsNhaHang.loadFromFile(PATH_NHAHANG);     } catch (Exception e) { System.out.println("Khong tai duoc NhaHang: " + e.getMessage()); }
        try { dsKhachHang.loadFromFile(PATH_KHACHHANG); } catch (Exception e) { System.out.println("Khong tai duoc KhachHang: " + e.getMessage()); }
        try { dsHDV.loadFromFile(PATH_HDV);             } catch (Exception e) { System.out.println("Khong tai duoc HDV: " + e.getMessage()); }
        try { dsKeHoach.loadFromFile(PATH_KEHOACH);     } catch (Exception e) { System.out.println("Khong tai duoc KeHoachTour: " + e.getMessage()); }
        try { dsChiPhi.loadFromFile(PATH_CHIPHI);       } catch (Exception e) { System.out.println("Khong tai duoc ChiPhi: " + e.getMessage()); }
        try { dsHoaDon.loadFromFile(PATH_HOADON);       } catch (Exception e) { System.out.println("Khong tai duoc HoaDon: " + e.getMessage()); }
        try { dsChiTietHD.loadFromFile(PATH_CHITIET);   } catch (Exception e) { System.out.println("Khong tai duoc ChiTietHD: " + e.getMessage()); }
        try { dsPhuongTien.loadFromFile(PATH_PHUONGTIEN);}catch (Exception e) { System.out.println("Khong tai duoc PhuongTien: " + e.getMessage()); }
    }

    // MENU CHINH - SIEU SACH
    public void menu(Scanner sc) {
        while (true) {
            System.out.println("\n════════════════════════════════════════════════");
            System.out.println("       CHAO MUNG DEN HE THONG QUAN LY DU LICH");
            System.out.println("════════════════════════════════════════════════");
            System.out.println("1. Quan ly Tour");
            System.out.println("2. Quan ly Khach san");
            System.out.println("3. Quan ly Nha hang");
            System.out.println("4. Quan ly Huong dan vien");
            System.out.println("5. Quan ly Khach hang");
            System.out.println("6. Quan ly Ke hoach tour");
            System.out.println("7. Quan ly Chi phi ke hoach tour");
            System.out.println("8. Quan ly Hoa don");
            System.out.println("9. Quan ly Chi tiet hoa don");
            System.out.println("10. Quan ly Phuong tien");
            System.out.println("0. Thoat chuong trinh");
            System.out.print("=> Chon chuc nang: ");

            int choice = -1;
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Vui long nhap so!");
                continue;
            }

            switch (choice) {
                case 1:
                    new QuanLyTour().tourMenu(sc);
                    break;
                case 2:
                    new QuanLyKhachSan().khachSanMenu(sc);
                    break;
                case 3:
                    new QuanLyNhaHang().nhaHangMenu(sc);
                    break;
                case 4:
                    new QuanLyHDV().hdvMenu(sc);
                    break;
                case 5:
                    new QuanLyKhachHang().khachHangMenu(sc);
                    break;
                case 6:
                    new QuanLyKeHoach().menuKeHoach(sc);
                    break;
                case 7:
                    new QuanLyChiPhi().menuChiPhi(sc);
                    break;
                case 8:
                    new QuanLyHoaDon().menuHoaDon(sc);
                    break;
                case 9:
                    new QuanLyChiTietHD().menuChiTietHD(sc);
                    break;
                case 10:
                    new QuanLyPhuongTien(dsPhuongTien, sc, PATH_PHUONGTIEN).menu();
                    break;
				case 0:
					luuTatCa();
					System.out.println("Cam on ban da su dung he thong. Tam biet!");
					return;
				default:
					System.out.println("Chuc nang khong ton tai!");
					break;
			}
        }
    }

    // Luu tat ca du lieu truoc khi thoat
    private static void luuTatCa() {
        try {
            dsTour.saveToFile(PATH_TOUR);
            dsKeHoach.saveToFile(PATH_KEHOACH);
            dsChiPhi.saveToFile(PATH_CHIPHI);
            dsHoaDon.saveToFile(PATH_HOADON);
            dsChiTietHD.saveToFile(PATH_CHITIET);
            dsPhuongTien.saveToFile(PATH_PHUONGTIEN);
            dsKhachHang.saveToFile(PATH_KHACHHANG);
            dsHDV.saveToFile(PATH_HDV);
            dsKhachSan.saveToFile(PATH_KHACHSAN);
            dsNhaHang.saveToFile(PATH_NHAHANG);
        } catch (IOException e) {
            System.out.println("Loi khi luu du lieu: " + e.getMessage());
            e.printStackTrace();
        }
    }


    // Getters de cac lop con truy cap (neu can)
    public static DSKHTour getDsKeHoach() { return dsKeHoach; }
    public static DSChiPhiKHTour getDsChiPhi() { return dsChiPhi; }
    public static DSHoaDon getDsHoaDon() { return dsHoaDon; }
    public static DSChiTietHD getDsChiTietHD() { return dsChiTietHD; }
    public static DSHDV getDsHDV() { return dsHDV; }
    public static DSKhachHang getDsKhachHang() { return dsKhachHang; }
    public static DSTour getDsTour() { return dsTour; }
    public static DSPhuongTien getDsPhuongTien() { return dsPhuongTien; }
}