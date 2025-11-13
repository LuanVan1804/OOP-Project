package DU_LICH;

import java.io.IOException;
import java.util.Scanner;

import DU_LICH.DiaDiemDuLich.DSQuocGia;
import DU_LICH.DiaDiemDuLich.DSThanhPho;
import DU_LICH.TourDuLich.DSTour;
import DU_LICH.DiaDiemDuLich.QuocGia;
import DU_LICH.TourDuLich.Tour;
import DU_LICH.NH_KS_PT.KhachSan;
import DU_LICH.NH_KS_PT.NhaHang;
import DU_LICH.Nguoi.HDV;
import DU_LICH.Nguoi.KhachHang;
import DU_LICH.NH_KS_PT.DSKhachSan;
import DU_LICH.NH_KS_PT.DSNhaHang;
import DU_LICH.Nguoi.DSHDV;
import DU_LICH.Nguoi.DSKhachHang;

public class QuanLy {
	private DSQuocGia dsQuocGia;
	private DSThanhPho dsThanhPho;
	private DSTour dsTour;
	private DSKhachSan dsKhachSan;
	private DSNhaHang dsNhaHang;
	private DSHDV dsHDV;
	private DSKhachHang dsKhachHang;
	private DSKHTour dsKeHoach;
	private DSChiPhiKHTour dsChiPhi;

	// Các đường dẫn file mặc định (có thể thay đổi khi khởi tạo nếu cần)
	private String pathQuocGia = "D:\\doanOOP\\DU_LICH\\DiaDiemDuLich\\quocGia.txt";
	private String pathThanhPho = "D:\\doanOOP\\DU_LICH\\DiaDiemDuLich\\ThanhPho.txt";
	private String pathDSTour = "D:\\doanOOP\\DU_LICH\\TourDuLich\\DSTour.txt";
	private String pathKhachSan = "D:\\doanOOP\\DU_LICH\\NH_KS_PT\\KhachSan.txt";
	private String pathNhaHang = "D:\\doanOOP\\DU_LICH\\NH_KS_PT\\NhaHang.txt";
	private String pathKhachHang = "D:\\doanOOP\\DU_LICH\\Nguoi\\KhachHang.txt";
	private String pathHDV = "D:\\doanOOP\\DU_LICH\\Nguoi\\HDV.txt";
	private String pathKeHoach = "D:\\doanOOP\\DU_LICH\\kehoachtour.txt";
	private String pathChiPhi = "D:\\doanOOP\\DU_LICH\\ChiPhiKHTour.txt";

	public QuanLy() {
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
		dsKeHoach = new DSKHTour(dsTour, dsHDV);
		try {
			dsKeHoach.loadFromFile(pathKeHoach);
		} catch (Exception e) {
			System.out.println("Loi doc kehoachtour.txt: " + e.getMessage());
		}

		// Chi phi ke hoach tour (phu thuoc vao dsKeHoach, dsKhachSan, dsNhaHang)
		dsChiPhi = new DSChiPhiKHTour(dsKeHoach, dsKhachSan, dsNhaHang);
		try {
			dsChiPhi.loadFromFile(pathChiPhi);
		} catch (Exception e) {
			System.out.println("Loi doc ChiPhiKHTour.txt: " + e.getMessage());
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
					// Gọi menu quản lý tour (được quản lý tại đây)
					this.tourMenu(sc);
					break;
				case 2:
					this.khachSanMenu(sc);
					break;
				case 3:
					this.nhaHangMenu(sc);
					break;
				case 4:
					this.hdvMenu(sc);
					break;
				case 5:
					this.khachHangMenu(sc);
					break;
				case 6:
					this.keHoachMenu(sc);
					break;
				case 7:
					this.chiPhiMenu(sc);
					break;
				case 0:
					// Khi thoát, lưu các danh sách nếu cần (DSTour đã lưu trong menu của nó)
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

	// ----- Menu cho từng DS (đã di chuyển từ các lớp DS) -----
	public void tourMenu(Scanner sc) {
		while (true) {
			System.out.println("\n=== MENU QUAN LY TOUR ===");
			System.out.println("1. Them tour");
			System.out.println("2. Xoa tour");
			System.out.println("3. Chinh sua tour");
			System.out.println("4. Hien thi danh sach tour");
			System.out.println("5. Tim kiem theo ten");
			System.out.println("6. Tim kiem theo ma");
			System.out.println("7. Thong ke");
			System.out.println("0. Thoat (luu)");
			System.out.print("Chon: ");
			String line = sc.nextLine();
			int ch = -1;
			try {
				ch = Integer.parseInt(line.trim());
			} catch (Exception e) {
				ch = -1;
			}
			switch (ch) {
				case 1:
					dsTour.themTour();
					break;
				case 2:
					System.out.print("Nhap ma tour can xoa: ");
					String maXoa = sc.nextLine();
					dsTour.xoaTour(maXoa);
					break;
				case 3:
					System.out.print("Nhap ma tour can chinh sua: ");
					String maSua = sc.nextLine();
					dsTour.chinhSuaTour(maSua);
					break;
				case 4:
					dsTour.hienThiDanhSachTour();
					break;
				case 5:
					System.out.print("Nhap ten tour can tim: ");
					String ten = sc.nextLine();
					Tour[] kq = dsTour.timKiemTheoTen(ten);
					if (kq == null || kq.length == 0)
						System.out.println("Khong tim thay tour.");
					else {
						for (Tour t : kq)
							t.hienThiThongTin();
					}
					break;
				case 6:
					System.out.print("Nhap ma tour can tim: ");
					String ma = sc.nextLine();
					dsTour.timKiemTheoMa(ma);
					break;
				case 7:
					dsTour.thongKe();
					break;
				case 0:
					try {
						dsTour.saveToFile(pathDSTour);
					} catch (IOException e) {
						System.out.println("Loi khi luu DSTour: " + e.getMessage());
					}
					return;
				default:
					System.out.println("Lua chon khong hop le.");
			}
		}
	}

	public void khachSanMenu(Scanner sc) {
		while (true) {
			System.out.println("\n===== MENU QUAN LY KHACH SAN =====");
			System.out.println("1. Them");
			System.out.println("2. Sua");
			System.out.println("3. Xoa");
			System.out.println("4. Tim kiem theo ten");
			System.out.println("5. Tim kiem theo ma");
			System.out.println("6. Thong ke don gian");
			System.out.println("7. Hien thi danh sach");
			System.out.println("0. Thoat (luu)");
			System.out.print("Chon: ");
			while (!sc.hasNextInt()) {
				System.out.print("Vui long nhap so: ");
				sc.nextLine();
			}
			int chon = sc.nextInt();
			sc.nextLine();
			switch (chon) {
				case 1: {
					KhachSan ks = dsKhachSan.nhapKhachSan(sc);
					if (ks != null && dsKhachSan.them(ks))
						System.out.println("Them thanh cong!");
					break;
				}
				case 2: {
					System.out.print("Nhap ma khach san can sua: ");
					String ma = sc.nextLine();
					if (dsKhachSan.suaTheoMaTuBanPhim(ma, sc))
						System.out.println("Da cap nhat!");
					else
						System.out.println("Khong tim thay ma.");
					break;
				}
				case 3: {
					System.out.print("Nhap ma khach san can xoa: ");
					String ma = sc.nextLine();
					if (dsKhachSan.xoaTheoMa(ma))
						System.out.println("Da xoa!");
					else
						System.out.println("Khong tim thay ma.");
					break;
				}
				case 4: {
					System.out.print("Nhap tu khoa tim theo ten: ");
					String tk = sc.nextLine();
					KhachSan[] kq = new KhachSan[dsKhachSan.getSoLuong()];
					int n = dsKhachSan.timTheoTen(tk, kq);
					if (n == 0)
						System.out.println("Khong co ket qua.");
					else {
						System.out.println("Tim thay " + n + " ket qua:");
						for (int i = 0; i < n; i++) {
							if (kq[i] != null) {
								kq[i].xuat();
								System.out.println();
							}
						}
					}
					break;
				}
				case 5: {
					System.out.print("Nhap ma can tim: ");
					String ma = sc.nextLine();
					KhachSan ks = dsKhachSan.timTheoMa(ma);
					if (ks == null)
						System.out.println("Khong tim thay.");
					else
						ks.xuat();
					break;
				}
				case 6:
					dsKhachSan.thongKeDonGian();
					break;
				case 7:
					dsKhachSan.xuatDanhSach();
					break;
				case 0:
					dsKhachSan.saveToFile(pathKhachSan);
					return;
				default:
					System.out.println("Lua chon khong hop le.");
			}
		}
	}

	public void nhaHangMenu(Scanner sc) {
		while (true) {
			System.out.println("\n========= MENU QUẢN LÝ NHÀ HÀNG =========");
			System.out.println("1. Them");
			System.out.println("2. Sua");
			System.out.println("3. Xoa");
			System.out.println("4. Tim kiem theo ten");
			System.out.println("5. Tim kiem theo ma");
			System.out.println("6. Thong ke");
			System.out.println("7. Hien thi danh sach");
			System.out.println("0. Thoat (luu)");
			System.out.print("Chon: ");
			while (!sc.hasNextInt()) {
				System.out.print("Vui long nhap so: ");
				sc.nextLine();
			}
			int chon = sc.nextInt();
			sc.nextLine();
			switch (chon) {
				case 1: {
					NhaHang nh = new NhaHang();
					nh.nhap();
					if (dsNhaHang.them(nh))
						System.out.println("Them thanh cong!");
					break;
				}
				case 2: {
					System.out.print("Nhap ma can sua: ");
					String ma = sc.nextLine();
					if (dsNhaHang.suaTheoMaTuBanPhim(ma, sc))
						System.out.println("Da cap nhat!");
					else
						System.out.println("Khong tim thay ma.");
					break;
				}
				case 3: {
					System.out.print("Nhap ma can xoa: ");
					String ma = sc.nextLine();
					if (dsNhaHang.xoaTheoMa(ma))
						System.out.println("Da xoa!");
					else
						System.out.println("Khong tim thay ma.");
					break;
				}
				case 4: {
					System.out.print("Nhap tu khoa tim theo ten: ");
					String tk = sc.nextLine();
					NhaHang[] kq = new NhaHang[dsNhaHang.getSoLuong()];
					int n = dsNhaHang.timTheoTen(tk, kq);
					if (n == 0)
						System.out.println("Khong co ket qua.");
					else {
						System.out.println("Tim thay " + n + " ket qua:");
						for (int i = 0; i < n && i < kq.length; i++)
							if (kq[i] != null) {
								kq[i].xuat();
								System.out.println();
							}
					}
					break;
				}
				case 5: {
					System.out.print("Nhap ma can tim: ");
					String ma = sc.nextLine();
					NhaHang nh = dsNhaHang.timTheoMa(ma);
					if (nh == null)
						System.out.println("Khong tim thay.");
					else
						nh.xuat();
					break;
				}
				case 6:
					dsNhaHang.thongKeDonGian();
					break;
				case 7:
					dsNhaHang.xuatDanhSach();
					break;
				case 0:
					dsNhaHang.saveToFile(pathNhaHang);
					return;
				default:
					System.out.println("Lua chon khong hop le.");
			}
		}
	}

	public void hdvMenu(Scanner sc) {
		while (true) {
			System.out.println("\n========== MENU QUAN LY HUONG DAN VIEN ==========");
			System.out.println("1. Hien thi danh sach HDV");
			System.out.println("2. Them HDV moi");
			System.out.println("3. Sua thong tin HDV");
			System.out.println("4. Xoa HDV");
			System.out.println("5. Tim kiem theo ten");
			System.out.println("6. Tim kiem theo kinh nghiem");
			System.out.println("7. Thong ke theo kinh nghiem");
			System.out.println("0. Thoat (luu)");
			System.out.print("Chon: ");
			int chon = Integer.parseInt(sc.nextLine());
			switch (chon) {
				case 1:
					dsHDV.hienThiDanhSachKH();
					break;
				case 2: {
					HDV hdv = new HDV();
					hdv.nhapThongTinHDV();
					dsHDV.them(hdv);
					System.out.println("Da them HDV moi!");
					break;
				}
				case 3: {
					System.out.print("Nhap ma HDV can sua: ");
					int ma = Integer.parseInt(sc.nextLine());
					dsHDV.sua(ma);
					break;
				}
				case 4: {
					System.out.print("Nhap ma HDV can xoa: ");
					int maX = Integer.parseInt(sc.nextLine());
					dsHDV.xoa(maX);
					break;
				}
				case 5: {
					System.out.print("Nhap ten HDV can tim: ");
					String ten = sc.nextLine();
					dsHDV.timKiemTheoTen(ten);
					break;
				}
				case 6: {
					System.out.print("Nhap so nam kinh nghiem can tim: ");
					double kn = Double.parseDouble(sc.nextLine());
					dsHDV.timKiemTheoKinhNghiem(kn);
					break;
				}
				case 7:
					dsHDV.thongKeTheoKinhNghiem();
					break;
				case 0:
					try {
						dsHDV.saveToFile(pathHDV);
					} catch (IOException e) {
						System.out.println("Loi khi luu HDV: " + e.getMessage());
					}
					return;
				default:
					System.out.println("Lua chon khong hop le!");
			}
		}
	}

	public void khachHangMenu(Scanner sc) {
		while (true) {
			System.out.println("\n========== MENU KHACH HANG ==========");
			System.out.println("1. Hien thi danh sach khach hang");
			System.out.println("2. Them khach hang moi");
			System.out.println("3. Sua thong tin khach hang");
			System.out.println("4. Xoa khach hang");
			System.out.println("5. Tim khach hang theo ma");
			System.out.println("6. Tim khach hang theo ten");
			System.out.println("7. Thong ke theo gioi tinh");
			System.out.println("0. Thoat (luu)");
			System.out.print("Chon chuc nang: ");
			int choice = Integer.parseInt(sc.nextLine());
			switch (choice) {
				case 1:
					dsKhachHang.hienThiDanhSachKH();
					break;
				case 2: {
					KhachHang kh = new KhachHang();
					kh.nhapThongTinKH();
					dsKhachHang.them(kh);
					break;
				}
				case 3: {
					System.out.print("Nhap ma khach hang can sua: ");
					int ma = Integer.parseInt(sc.nextLine());
					dsKhachHang.sua(ma);
					break;
				}
				case 4: {
					System.out.print("Nhap ma khach hang can xoa: ");
					int maX = Integer.parseInt(sc.nextLine());
					dsKhachHang.xoa(maX);
					break;
				}
				case 5: {
					System.out.print("Nhap ma khach hang can tim: ");
					int maT = Integer.parseInt(sc.nextLine());
					dsKhachHang.timKiemKHTheoMa(maT);
					break;
				}
				case 6: {
					System.out.print("Nhap ten khach hang can tim: ");
					String ten = sc.nextLine();
					dsKhachHang.timKiemTheoTen(ten);
					break;
				}
				case 7: {
					int[] stats = dsKhachHang.thongKeTheoGioiTinh();
					System.out.println("Nam: " + stats[0] + ", Nu: " + stats[1]);
					break;
				}
				case 0:
					try {
						dsKhachHang.saveToFile(pathKhachHang);
					} catch (IOException e) {
						System.out.println("Loi khi luu khach hang: " + e.getMessage());
					}
					return;
				default:
					System.out.println("Lua chon khong hop le!");
			}
		}
	}

	public void keHoachMenu(Scanner sc) {
		while (true) {
			System.out.println("\n===== MENU QUAN LY KE HOACH TOUR =====");
			System.out.println("1. Them ke hoach");
			System.out.println("2. Xoa ke hoach theo ma");
			System.out.println("3. Sua ke hoach theo ma");
			System.out.println("4. Hien thi danh sach");
			System.out.println("5. Tim kiem theo ma");
			System.out.println("6. Tim kiem theo ten tour");
			System.out.println("7. Thong ke don gian");
			System.out.println("0. Thoat (luu)");
			System.out.print("Chon: ");
			String line = sc.nextLine();
			int ch = -1;
			try {
				ch = Integer.parseInt(line.trim());
			} catch (Exception e) {
				ch = -1;
			}
			switch (ch) {
				case 1: {
					// Thêm kế hoạch: DSKHTour có phương thức họp nhập từ bàn phím
					dsKeHoach.themKHTour();
					break;
				}
				case 2: {
					System.out.print("Nhap ma ke hoach can xoa: ");
					String ma = sc.nextLine().trim();
					boolean ok = dsKeHoach.xoaKHTour(ma);
					if (ok) {
						// Cascade delete chi phi lien quan
						if (dsChiPhi != null) {
							if (dsChiPhi.xoaTheoMa(ma))
								System.out.println("Chi phi lien quan da bi xoa.");
						}
					}
					break;
				}
				case 3: {
					System.out.print("Nhap ma ke hoach can sua: ");
					String ma = sc.nextLine().trim();
					dsKeHoach.chinhSuaKHTour(ma);
					break;
				}
				case 4:
					dsKeHoach.hienThiDanhSachKHTour(dsChiPhi);
					break;
				case 5: {
					System.out.print("Nhap ma can tim: ");
					String ma = sc.nextLine().trim();
					dsKeHoach.timKiemTheoMa(ma, dsChiPhi);
					break;
				}
				case 6: {
					System.out.print("Nhap tu khoa ten tour: ");
					String tk = sc.nextLine().trim();
					KeHoachTour[] kq = dsKeHoach.timKiemTheoTen(tk);
					if (kq == null || kq.length == 0)
						System.out.println("Khong co ket qua.");
					else {
						System.out.println("Tim thay " + kq.length + " ket qua:");
						for (KeHoachTour kk : kq) {
							if (kk != null) {
								kk.hienThiThongTin(dsChiPhi);
								System.out.println();
							}
						}
					}
					break;
				}
				case 7:
					dsKeHoach.thongKe(dsChiPhi);
					break;
				case 0:
					try {
						dsKeHoach.saveToFile(pathKeHoach);
					} catch (IOException e) {
						System.out.println("Loi khi luu: " + e.getMessage());
					}
					return;
				default:
					System.out.println("Lua chon khong hop le.");
			}
		}
	}

	public void chiPhiMenu(Scanner sc) {
		while (true) {
			System.out.println("\n===== MENU QUAN LY CHI PHI KE HOACH TOUR =====");
			System.out.println("1. Them chi phi");
			System.out.println("2. Xoa chi phi theo ma KHTour");
			System.out.println("3. Chinh sua chi phi theo ma KHTour");
			System.out.println("4. Hien thi danh sach chi phi");
			System.out.println("5. Tim kiem theo ma KHTour");
			System.out.println("6. Tim kiem theo tu khoa (ma KHTour)");
			System.out.println("7. Thong ke don gian");
			System.out.println("0. Thoat (luu)");
			System.out.print("Chon: ");
			String line = sc.nextLine();
			int ch = -1;
			try {
				ch = Integer.parseInt(line.trim());
			} catch (Exception e) {
				ch = -1;
			}
			switch (ch) {
				case 1:
					dsChiPhi.themChiPhi();
					break;
				case 2: {
					System.out.print("Nhap ma KHTour can xoa chi phi: ");
					String ma = sc.nextLine().trim();
					if (dsChiPhi.xoaTheoMa(ma))
						System.out.println("Da xoa!");
					else
						System.out.println("Khong tim thay ma.");
					break;
				}
				case 3: {
					System.out.print("Nhap ma KHTour can chinh sua chi phi: ");
					String ma = sc.nextLine().trim();
					if (dsChiPhi.chinhSuaTheoMa(ma))
						System.out.println("Da cap nhat!");
					else
						System.out.println("Khong tim thay ma.");
					break;
				}
				case 4:
					dsChiPhi.xuatDanhSach();
					break;
				case 5: {
					System.out.print("Nhap ma KHTour can tim: ");
					String ma = sc.nextLine().trim();
					ChiPhiKHTour cp = dsChiPhi.timTheoMa(ma);
					if (cp == null)
						System.out.println("Khong tim thay.");
					else
						cp.xuatThongTin();
					break;
				}
				case 6: {
					System.out.print("Nhap tu khoa tim: ");
					String tk = sc.nextLine().trim();
					ChiPhiKHTour[] arr = dsChiPhi.timTheoTen(tk);
					if (arr == null || arr.length == 0)
						System.out.println("Khong co ket qua.");
					else {
						System.out.println("Tim thay " + arr.length + " ket qua:");
						for (int i = 0; i < arr.length; i++)
							if (arr[i] != null) {
								arr[i].xuatThongTin();
								System.out.println();
							}
					}
					break;
				}
				case 7:
					dsChiPhi.thongKeDonGian();
					break;
				case 0:
					dsChiPhi.saveToFile(pathChiPhi);
					return;
				default:
					System.out.println("Lua chon khong hop le.");
			}
		}
	}

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
}
