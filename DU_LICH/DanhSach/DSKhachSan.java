package DU_LICH.DanhSach;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Scanner;

import DU_LICH.ClassDon.KhachSan;

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

        System.out.print("Ten khach san hien tai (" + ks.getTenKhachSan() + "): ");
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
    // Định dạng dòng: ma,ten,dd/MM/yyyy,dd/MM/yyyy,gia
    public int loadFromFile(String filePath) {
        int dem = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            soLuong = 0;
            String line;
            while ((line = br.readLine()) != null && soLuong < dsKhachSan.length) {
                if (line.trim().isEmpty()) continue;
                // accept comma or semicolon as separator
                String[] p = line.split("[,;]");
                if (p.length < 5) { System.out.println("Bỏ qua dòng không hợp lệ: " + line); continue; }
                try {
                    KhachSan ks = new KhachSan(
                        p[0].trim(),
                        p[1].trim(),
                        parseDate(p[2].trim()),
                        parseDate(p[3].trim()),
                        Double.parseDouble(p[4].trim())
                    );
                    if (them(ks)) dem++;
                } catch (Exception e) {
                    System.out.println("Loi dinh dang du lieu trong file: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Không thể đọc file: " + e.getMessage());
        }
        return dem;
    }

    public int saveToFile(String filePath) {
        int dem = 0;
        File f = new File(filePath);
        if (f.getParentFile() != null) f.getParentFile().mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
            for (int i = 0; i < soLuong; i++) {
                KhachSan ks = dsKhachSan[i];
                bw.write(String.join(",",
                    ks.getMaKhachSan(),
                    ks.getTenKhachSan(),
                    formatDate(ks.getNgayDen()),
                    formatDate(ks.getNgayDi()),
                    String.valueOf(ks.getGiaDatPhong())
                ));
                bw.newLine();
                dem++;
            }
        } catch (IOException e) {
            System.out.println("Không thể ghi file: " + e.getMessage());
        }
        return dem;
    }

    // hàm thống kê đơn giản
    public void thongKeDonGian() {
        System.out.println("--- Thong ke don gian ---");
        System.out.println("So luong khach san: " + soLuong);
        if (soLuong > 0) {
            double tong = 0.0;
            int dem = 0;
            for (int i = 0; i < soLuong; i++) {
                KhachSan k = dsKhachSan[i];
                if (k != null) { tong += k.getGiaDatPhong(); dem++; }
            }
            double avg = dem == 0 ? 0.0 : (tong / dem);
            System.out.println("Gia trung binh dat phong: " + avg);
        }
    }
    // Menus have been moved to QuanLy
}
