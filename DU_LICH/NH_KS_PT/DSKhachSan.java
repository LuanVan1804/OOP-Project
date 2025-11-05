package DU_LICH.NH_KS_PT;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Scanner;

public class DSKhachSan {
    private KhachSan[] dsKhachSan;  // Mảng chứa danh sách khách sạn
    private int soLuong;             // Số lượng khách sạn hiện có
    private final int MAX = 100;     // Số lượng tối đa

    // Khởi tạo danh sách rỗng
    public DSKhachSan() {
        this.dsKhachSan = new KhachSan[MAX];
        this.soLuong = 0;
    }

    // ================== CÁC HÀM HỖ TRỢ NGÀY THÁNG ==================
    private static final String DATE_PATTERN = "dd/MM/yyyy"; // Định dạng ngày trong file và nhập liệu
    private static final SimpleDateFormat SDF = new SimpleDateFormat(DATE_PATTERN, new Locale("vi", "VN"));

    private Date parseDate(String s) throws ParseException {
        // Chuyển chuỗi dd/MM/yyyy -> java.sql.Date
        java.util.Date utilDate = SDF.parse(s.trim());
        return new Date(utilDate.getTime());
    }

    private String formatDate(Date d) {
        if (d == null) return "";
        return SDF.format(new java.util.Date(d.getTime()));
    }

    // ================== CÁC CHỨC NĂNG CƠ BẢN ==================
    public int getSoLuong() { return soLuong; }

    private int findIndexByMa(String ma) {
        if (ma == null) return -1;
        for (int i = 0; i < soLuong; i++) {
            if (dsKhachSan[i] != null && ma.equalsIgnoreCase(dsKhachSan[i].getMaKhachSan())) {
                return i;
            }
        }
        return -1;
    }

    public boolean them(KhachSan ks) {
        if (ks == null) return false;
        if (soLuong >= MAX) {
            System.out.println("Danh sach da day (" + MAX + ")!");
            return false;
        }
        if (findIndexByMa(ks.getMaKhachSan()) != -1) {
            System.out.println("Trung ma khach san: " + ks.getMaKhachSan());
            return false;
        }
        dsKhachSan[soLuong++] = ks;
        return true;
    }

    public boolean xoaTheoMa(String ma) {
        int idx = findIndexByMa(ma);
        if (idx == -1) return false;
        // Dịch các phần tử phía sau lên
        for (int i = idx; i < soLuong - 1; i++) {
            dsKhachSan[i] = dsKhachSan[i + 1];
        }
        dsKhachSan[soLuong - 1] = null;
        soLuong--;
        return true;
    }

    public KhachSan timTheoMa(String ma) {
        int idx = findIndexByMa(ma);
        return idx == -1 ? null : dsKhachSan[idx];
    }

    public int timTheoTen(String tuKhoa, KhachSan[] ketQua) {
        // Trả về số lượng kết quả ghi vào mảng ketQua (có thể null -> chỉ đếm)
        if (tuKhoa == null) tuKhoa = "";
        String tk = tuKhoa.trim().toLowerCase();
        int dem = 0;
        for (int i = 0; i < soLuong; i++) {
            KhachSan ks = dsKhachSan[i];
            if (ks != null && ks.getTenKhachSan() != null && ks.getTenKhachSan().toLowerCase().contains(tk)) {
                if (ketQua != null && dem < ketQua.length) {
                    ketQua[dem] = ks;
                }
                dem++;
            }
        }
        return dem;
    }

    public void xuatDanhSach() {
        if (soLuong == 0) {
            System.out.println("Danh sach trong.");
            return;
        }
        System.out.println("===== DANH SACH KHACH SAN (" + soLuong + ") =====");
        for (int i = 0; i < soLuong; i++) {
            System.out.println("-- Khach san thu " + (i + 1) + " --");
            dsKhachSan[i].xuat();
            System.out.println();
        }
    }

    // Nhập 1 khách sạn từ bàn phím theo định dạng dd/MM/yyyy
    public KhachSan nhapKhachSan(Scanner sc) {
        KhachSan ks = new KhachSan();
        System.out.print("Nhap ma khach san: ");
        ks.setMaKhachSan(sc.nextLine().trim());
        System.out.print("Nhap ten khach san: ");
        ks.setTenKhachSan(sc.nextLine().trim());
        try {
            System.out.print("Nhap ngay den (dd/MM/yyyy): ");
            ks.setNgayDen(parseDate(sc.nextLine()));
            System.out.print("Nhap ngay di (dd/MM/yyyy): ");
            ks.setNgayDi(parseDate(sc.nextLine()));
        } catch (ParseException e) {
            System.out.println("Dinh dang ngay khong hop le. Vui long thao tac lai!");
            return null;
        }
        System.out.print("Nhap gia dat phong: ");
        while (!sc.hasNextDouble()) {
            System.out.print("Gia khong hop le, nhap lai: ");
            sc.nextLine();
        }
        ks.setGiaDatPhong(sc.nextDouble());
        sc.nextLine(); // bỏ dòng
        return ks;
    }

    public boolean suaTheoMaTuBanPhim(String ma, Scanner sc) {
        KhachSan ks = timTheoMa(ma);
        if (ks == null) return false;
        System.out.println("De trong va nhan Enter neu muon giu nguyen.");

        System.out.print("TTen khach san hien tai (" + ks.getTenKhachSan() + "): ");
        String ten = sc.nextLine();
        if (!ten.trim().isEmpty()) ks.setTenKhachSan(ten.trim());

        System.out.print("Ngay den hien tai (" + formatDate(ks.getNgayDen()) + ") [dd/MM/yyyy]: ");
        String nd = sc.nextLine();
        if (!nd.trim().isEmpty()) {
            try { ks.setNgayDen(parseDate(nd)); } catch (ParseException e) { System.out.println("Ngay den khong hop le, giu nguyen."); }
        }

        System.out.print("Ngay di hien tai (" + formatDate(ks.getNgayDi()) + ") [dd/MM/yyyy]: ");
        String ndi = sc.nextLine();
        if (!ndi.trim().isEmpty()) {
            try { ks.setNgayDi(parseDate(ndi)); } catch (ParseException e) { System.out.println("Ngay di khong hop le, giu nguyen."); }
        }

        System.out.print("Gia dat phong hien tai (" + ks.getGiaDatPhong() + "): ");
        String giaStr = sc.nextLine();
        if (!giaStr.trim().isEmpty()) {
            try { ks.setGiaDatPhong(Double.parseDouble(giaStr.trim())); } catch (NumberFormatException e) { System.out.println("Giá không hợp lệ, giữ nguyên."); }
        }
        return true;
    }

    // ================== ĐỌC / GHI FILE ==================
    // Định dạng dòng: ma;ten;dd/MM/yyyy;dd/MM/yyyy;gia
    public int docFile(String filePath) {
        int dem = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Reset danh sách trước khi đọc
            this.soLuong = 0;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(";");
                if (parts.length < 5) {
                    System.out.println("Bo qua dong khong hop le: " + line);
                    continue;
                }
                try {
                    KhachSan ks = new KhachSan();
                    ks.setMaKhachSan(parts[0].trim());
                    ks.setTenKhachSan(parts[1].trim());
                    ks.setNgayDen(parseDate(parts[2].trim()));
                    ks.setNgayDi(parseDate(parts[3].trim()));
                    ks.setGiaDatPhong(Double.parseDouble(parts[4].trim()));
                    if (them(ks)) dem++;
                } catch (Exception ex) {
                    System.out.println("LLoi phan tich dong: " + line + " -> " + ex.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Khong the doc file: " + e.getMessage());
        }
        return dem;
    }

    public int ghiFile(String filePath) {
        int dem = 0;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (int i = 0; i < soLuong; i++) {
                KhachSan ks = dsKhachSan[i];
                if (ks == null) continue;
                String dong = String.join(";",
                        ks.getMaKhachSan(),
                        ks.getTenKhachSan(),
                        formatDate(ks.getNgayDen()),
                        formatDate(ks.getNgayDi()),
                        String.valueOf(ks.getGiaDatPhong())
                );
                bw.write(dong);
                bw.newLine();
                dem++;
            }
        } catch (IOException e) {
            System.out.println("Khong the ghi file: " + e.getMessage());
        }
        return dem;
    }

    // ================== MENU TƯƠNG TÁC ==================
    public void menu() {
        Scanner sc = new Scanner(System.in);
        String macDinh = "DU_LICH\\NH_KS_PT\\KhachSan.txt"; // Dành cho khi chạy từ ổ d:, có thể thay đổi khi cần
        String duongDan = macDinh;
        if (!new java.io.File(duongDan).exists()) {
            // Thử đường dẫn tuyệt đối như trong workspace
            duongDan = "D:\\DU_LICH\\NH_KS_PT\\KhachSan.txt";
        }
        int chon;
        do {
            System.out.println("\n========= MENU QUAN LY KHACH SAN =========");
            System.out.println("1. Doc danh sach tu file");
            System.out.println("2. Ghi danh sach ra file");
            System.out.println("3. Xem danh sach khach san");
            System.out.println("4. Them khach san");
            System.out.println("5. Xoa khach san theo ma");
            System.out.println("6. Sua thong tin khach san theo ma");
            System.out.println("7. Tim kiem theo ma");
            System.out.println("8. Tim kiem theo ten (chua tu khoa)");
            System.out.println("0. Thoat");
            System.out.print("Chon: ");

            while (!sc.hasNextInt()) { System.out.print("Vui long nhap so: "); sc.nextLine(); }
            chon = sc.nextInt(); sc.nextLine();

            switch (chon) {
                case 1: {
                    System.out.print("Nhap duong dan file (Enter de dung mac dinh: " + duongDan + "): ");
                    String p = sc.nextLine().trim();
                    if (!p.isEmpty()) duongDan = p;
                    int n = docFile(duongDan);
                    System.out.println("Da doc " + n + " dong hop le.");
                    break;
                }
                case 2: {
                    System.out.print("Nhap duong dan file (Enter de dung mac dinh: " + duongDan + "): ");
                    String p = sc.nextLine().trim();
                    if (!p.isEmpty()) duongDan = p;
                    int n = ghiFile(duongDan);
                    System.out.println("Da ghi " + n + " dong.");
                    break;
                }
                case 3: {
                    xuatDanhSach();
                    break;
                }
                case 4: {
                    KhachSan ks = new KhachSan();
                    ks.nhap(sc);
                    if (them(ks)) System.out.println("Them thanh cong!");
                    break;
                }
                case 5: {
                    System.out.print("Nhap ma can xoa: ");
                    String ma = sc.nextLine();
                    if (xoaTheoMa(ma)) System.out.println("Da xoa!"); else System.out.println("Khong tim thay ma.");
                    break;
                }
                case 6: {
                    System.out.print("Nhap ma can sua: ");
                    String ma = sc.nextLine();
                    if (suaTheoMaTuBanPhim(ma, sc)) System.out.println("Da cap nhat!"); else System.out.println("Khong tim thay ma.");
                    break;
                }
                case 7: {
                    System.out.print("Nhap ma can tim: ");
                    String ma = sc.nextLine();
                    KhachSan ks = timTheoMa(ma);
                    if (ks == null) System.out.println("Khong tim thay."); else ks.xuat();
                    break;
                }
                case 8: {
                    System.out.print("Nhap tu khoa ten: ");
                    String tk = sc.nextLine();
                    KhachSan[] kq = new KhachSan[soLuong];
                    int n = timTheoTen(tk, kq);
                    if (n == 0) System.out.println("Khong co ket qua.");
                    else {
                        System.out.println("Tim thay " + n + " ket qua:");
                        for (int i = 0; i < n && i < kq.length; i++) {
                            if (kq[i] != null) { kq[i].xuat(); System.out.println(); }
                        }
                    }
                    break;
                }
                case 0: {
                    break;
                }
                default:
                    System.out.println("Lua chon khong hop le.");
            }
        } while (chon != 0);
    }
}
