package DU_LICH;

import java.io.IOException;
import java.util.Scanner;

import DU_LICH.DiaDiemDuLich.DSQuocGia;
import DU_LICH.DiaDiemDuLich.DSThanhPho;
import DU_LICH.TourDuLich.DSTour;
import DU_LICH.DiaDiemDuLich.QuocGia;
import DU_LICH.NH_KS_PT.DSKhachSan;
import DU_LICH.NH_KS_PT.DSNhaHang;
import DU_LICH.Nguoi.DSHDV;
import DU_LICH.Nguoi.DSKhachHang;

public class QuanLy {
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
	protected static DU_LICH.NH_KS_PT.DSPhuongTien dsPhuongTien;

	// Các đường dẫn file mặc định (có thể thay đổi khi khởi tạo nếu cần)
	protected static String pathQuocGia = "D:\\doanOOP\\DU_LICH\\DiaDiemDuLich\\quocGia.txt";
	protected static String pathThanhPho = "D:\\doanOOP\\DU_LICH\\DiaDiemDuLich\\ThanhPho.txt";
	protected static String pathDSTour = "D:\\doanOOP\\DU_LICH\\TourDuLich\\DSTour.txt";
	protected static String pathKhachSan = "D:\\doanOOP\\DU_LICH\\NH_KS_PT\\KhachSan.txt";
	protected static String pathNhaHang = "D:\\doanOOP\\DU_LICH\\NH_KS_PT\\NhaHang.txt";
	protected static String pathKhachHang = "D:\\doanOOP\\DU_LICH\\Nguoi\\KhachHang.txt";
	protected static String pathHDV = "D:\\doanOOP\\DU_LICH\\Nguoi\\HDV.txt";
	protected static String pathKeHoach = "D:\\doanOOP\\DU_LICH\\kehoachtour.txt";
	protected static String pathChiPhi = "D:\\doanOOP\\DU_LICH\\ChiPhiKHTour.txt";
	protected static String pathDSHoaDon = "D:\\doanOOP\\DU_LICH\\DSHoaDon.txt";
	protected static String pathDSChiTiet = "D:\\doanOOP\\DU_LICH\\DSChiTietHD.txt";
	protected static String pathPhuongTien = "D:\\doanOOP\\DU_LICH\\NH_KS_PT\\PhuongTien.txt";

	public QuanLy() {
		this(true);
	}

	// protected constructor for subclasses to avoid re-loading files
	protected QuanLy(boolean doLoad) {
		if (doLoad)
			loadAll();
	}

	// Tải tất cả dữ liệu từ file vào các DS tương ứng
	public final void loadAll() {
		// Quoc gia
		try {
			dsQuocGia = DSQuocGia.loadFromFile(pathQuocGia);
		} catch (IOException e) {
			System.out.println("Loi doc quocGia.txt: " + e.getMessage());
			dsQuocGia = new DSQuocGia();
		}

		// Thanh pho
		try {
			dsThanhPho = DSThanhPho.loadFromFile(pathThanhPho);
		} catch (IOException e) {
			System.out.println("Loi doc ThanhPho.txt: " + e.getMessage());
			dsThanhPho = new DSThanhPho();
		}

		// Gán danh sách thành phố cho từng quốc gia nếu có
		if (dsQuocGia != null && dsThanhPho != null) {
			for (QuocGia q : dsQuocGia.getList()) {
				if (q != null)
					q.setListTPhos(dsThanhPho.getCitiesByCountry(q.getMaQuocGia()));
			}
		}

		// DSTour
		dsTour = new DSTour();
		try {
			dsTour.loadFromFile(pathDSTour);
		} catch (IOException e) {
			System.out.println("Loi doc DSTour.txt: " + e.getMessage());
		}

		// Khach san
		dsKhachSan = new DSKhachSan();
		try {
			dsKhachSan.loadFromFile(pathKhachSan);
		} catch (Exception e) {
			System.out.println("Loi doc KhachSan.txt: " + e.getMessage());
		}

		// Nha hang
		dsNhaHang = new DSNhaHang();
		try {
			dsNhaHang.loadFromFile(pathNhaHang);
		} catch (Exception e) {
			System.out.println("Loi doc NhaHang.txt: " + e.getMessage());
		}

		//Phuong Tien
		dsPhuongTien = new DU_LICH.NH_KS_PT.DSPhuongTien();
		try {
			dsPhuongTien.loadFromFile(pathPhuongTien);
		} catch (Exception e) {
			System.out.println("Loi doc PhuongTien.txt: " + e.getMessage());
		}

		// Khach hang
		dsKhachHang = new DSKhachHang();
		try {
			dsKhachHang.loadFromFile(pathKhachHang);
		} catch (Exception e) {
			System.out.println("Loi doc KhachHang.txt: " + e.getMessage());
		}

		// HDV
		dsHDV = new DSHDV();
		try {
			dsHDV.loadFromFile(pathHDV);
		} catch (Exception e) {
			System.out.println("Loi doc HDV.txt: " + e.getMessage());
		}

		// Ke hoach tour (pass các DS để xác thực khi thêm/sửa)
		dsKeHoach = new DSKHTour();
		try {
			dsKeHoach.loadFromFile(pathKeHoach);
		} catch (Exception e) {
			System.out.println("Loi doc kehoachtour.txt: " + e.getMessage());
		}

		// Hoa don va chi tiet hoa don
		dsHoaDon = new DSHoaDon();
		try {
			dsHoaDon.loadFromFile(pathDSHoaDon);
		} catch (Exception e) {
			System.out.println("Loi doc DSHoaDon.txt: " + e.getMessage());
		}

		dsChiTietHD = new DSChiTietHD();
		try {
			dsChiTietHD.loadFromFile(pathDSChiTiet);
		} catch (Exception e) {
			System.out.println("Loi doc DSChiTietHD.txt: " + e.getMessage());
		}

		// Chi phi ke hoach tour (phu thuoc vao dsKeHoach, dsKhachSan, dsNhaHang)
		dsChiPhi = new DSChiPhiKHTour(dsKeHoach, dsKhachSan, dsNhaHang);
		try {
			dsChiPhi.loadFromFile(pathChiPhi);
		} catch (Exception e) {
			System.out.println("Loi doc ChiPhiKHTour.txt: " + e.getMessage());
		}

		// Wire DSPhuongTien into DSChiPhi so chi phi creation can list/select vehicles
		if (dsChiPhi != null && dsPhuongTien != null) {
			dsChiPhi.setDsPhuongTien(dsPhuongTien);
		}


	}

	// Menu chính của lớp QuanLy (sử dụng scanner do caller truyền vào)
	// Caller phải truyền Scanner hợp lệ. Nếu muốn tạo scanner mới, dùng menu()
	// không tham số.
	public void menu(Scanner sc) {
		if (sc == null)
			throw new IllegalArgumentException("Scanner cannot be null. Call menu() to use default scanner.");

		while (true) {
			System.out.println("\n=== MENU QUAN LY (Tong hop) ===");
			System.out.println("1. Quan ly tour");
			System.out.println("2. Quan ly khach san");
			System.out.println("3. Quan ly nha hang");
			System.out.println("4. Quan ly huong dan vien");
			System.out.println("5. Quan ly khach hang");
			System.out.println("6. Quan ly ke hoach tour");
			System.out.println("7. Quan ly chi phi ke hoach tour");
			System.out.println("8. Quan ly hoa don");
			System.out.println("9. Quan ly chi tiet hoa don");
			System.out.println("10. Quan ly phuong tien");
			System.out.println("0. Thoat");
			System.out.print("Chon chuc nang: ");

			String line = sc.nextLine();
			int choice = -1;
			try {
				choice = Integer.parseInt(line.trim());
			} catch (Exception ex) {
				choice = -1;
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
						new QuanLyKeHoach().keHoachMenu(sc);
					break;
				case 7:
						new QuanLyChiPhi().chiPhiMenu(sc);
					break;
				case 8:
					new QuanLyHoaDon(dsHoaDon, dsHDV, dsKhachHang, dsKeHoach, dsChiTietHD, sc).menu();
					break;
				case 9:
					new QuanLyChiTietHD(dsChiTietHD, sc).menu();
					break;
				case 10:
					new DU_LICH.QuanLyPhuongTien(dsPhuongTien, sc, pathPhuongTien).menu();
					break;
				case 0:
					try {
						dsTour.saveToFile(pathDSTour);
					} catch (IOException e) {
						System.out.println("Loi khi luu DSTour: " + e.getMessage());
					}
					try {
						dsKeHoach.saveToFile(pathKeHoach);
					} catch (IOException e) {
						System.out.println("Loi khi luu ke hoach: " + e.getMessage());
					}
					try {
						dsChiPhi.saveToFile(pathChiPhi);
					} catch (Exception e) {
						System.out.println("Loi khi luu chi phi: " + e.getMessage());
					}
					try {
						dsHoaDon.saveToFile(pathDSHoaDon);
					} catch (IOException e) {
						System.out.println("Loi luu DSHoaDon: " + e.getMessage());
					}
					try {
						dsChiTietHD.saveToFile(pathDSChiTiet);
					} catch (IOException e) {
						System.out.println("Loi luu DSChiTietHD: " + e.getMessage());
					}
					try {
						dsPhuongTien.saveToFile(pathPhuongTien);
					} catch (IOException e) {
						System.out.println("Loi luu PhuongTien: " + e.getMessage());
					}
					System.out.println("Thoat QuanLy.");
					return;
				default:
					System.out.println("Lua chon khong hop le. Vui long thu lai.");
			}
		}
	}

	// instance menu không tham số (tạo scanner mới và tự đóng khi thoát)
	public void menu() {
		try (Scanner sc = new Scanner(System.in)) {
			menu(sc);
		}
	}

	// Menu implementations moved to dedicated subclasses (QuanLyTour, QuanLyKhachSan, ...)

	// Getters để Main hoặc các class khác có thể truy xuất các DS đã nạp
	public DSQuocGia getDsQuocGia() {
		return dsQuocGia;
	}

	public DSThanhPho getDsThanhPho() {
		return dsThanhPho;
	}

	public DSTour getDsTour() {
		return dsTour;
	}

	public DSKhachSan getDsKhachSan() {
		return dsKhachSan;
	}

	public DSNhaHang getDsNhaHang() {
		return dsNhaHang;
	}

	public DSHDV getDsHDV() {
		return dsHDV;
	}

	public DSKhachHang getDsKhachHang() {
		return dsKhachHang;
	}

	public DSKHTour getDsKeHoach() {
		return dsKeHoach;
	}

	public DSChiPhiKHTour getDsChiPhi() {
		return dsChiPhi;
	}

	public DU_LICH.NH_KS_PT.DSPhuongTien getDsPhuongTien() {
		return dsPhuongTien;
	}
}
