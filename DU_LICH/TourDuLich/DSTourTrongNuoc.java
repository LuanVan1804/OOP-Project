package DU_LICH.TourDuLich;

import java.util.Scanner;

import DU_LICH.DiaDiemDuLich.DSQuocGia;
import DU_LICH.DiaDiemDuLich.DSThanhPho;
import DU_LICH.DiaDiemDuLich.QuocGia;
import DU_LICH.DiaDiemDuLich.ThanhPho;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;

public class DSTourTrongNuoc {
    private TourTrongNuoc[] list;
    private int soLuongTour;
    private DSQuocGia dsQuocGia;
    private static final Scanner sc = new Scanner(System.in);

    // Constructor
    public DSTourTrongNuoc(DSQuocGia dsQuocGia) {
        this.list = new TourTrongNuoc[10]; // Kích thước tối đa, có thể điều chỉnh
        this.soLuongTour = 0;
        this.dsQuocGia = dsQuocGia;
    }

    // Getter
    public TourTrongNuoc[] getList() { return list; }
    public int getSoLuongTour() { return soLuongTour; }

    // Lấy quốc gia nội địa (quốc gia đầu tiên)
    private QuocGia getDomesticCountry() {
        QuocGia[] countries = dsQuocGia.getList();
        if (countries.length > 0) {
            return countries[dsQuocGia.getCurrentCountryIndex()];
        }
        return null;
    }

    // Hiển thị danh sách thành phố của quốc gia nội địa
    private void hienThiDanhSachThanhPho() {
        QuocGia domesticCountry = getDomesticCountry();
        if (domesticCountry == null) {
            System.out.println("Khong co quoc gia noi dia!");
            return;
        }
        System.out.println("Danh sach thanh pho cua " + domesticCountry.getTenNuoc() + ":");
        for (ThanhPho city : domesticCountry.getListTPhos()) {
            System.out.println(" - " + city.getTenTPho() + " (Ma: " + city.getMaTPho() + ")");
        }
    }

    // Kiểm tra mã thành phố có hợp lệ trong quốc gia nội địa
    private boolean isValidCityCode(int maThanhPho) {
        QuocGia domesticCountry = getDomesticCountry();
        if (domesticCountry == null) return false;
        for (ThanhPho city : domesticCountry.getListTPhos()) {
            if (city.getMaTPho() == maThanhPho) {
                return true;
            }
        }
        return false;
    }

    // Thêm tour trong nước
    public void themTour() {
        if (soLuongTour >= list.length) {
            System.out.println("Danh sach tour da day!");
            return;
        }

        // Hiển thị danh sách thành phố để người dùng chọn
        hienThiDanhSachThanhPho();

        // Nhập thông tin tour
        TourTrongNuoc tour = new TourTrongNuoc();
        System.out.println("Nhap thong tin tour trong nuoc:");
        tour.nhapThongTin();

        // Kiểm tra mã tour phải là duy nhất
        while (!isMaTourUnique(tour.getMaTour())) {
            System.out.println("Ma tour da ton tai. Vui long nhap ma tour khac:");
            System.out.print("Nhap Ma Tour: ");
            int newMa = sc.nextInt();
            sc.nextLine();
            tour.setMaTour(newMa);
        }

        // Kiểm tra mã thành phố hợp lệ
        while (!isValidCityCode(tour.getMaThanhPho())) {
            System.out.println("Ma thanh pho khong hop le! Vui long chon lai tu danh sach tren:");
            System.out.print("Nhap Ma Thanh Pho: ");
            tour.setMaThanhPho(sc.nextInt());
            sc.nextLine();
        }

        // Thêm tour vào danh sách
        list[soLuongTour] = tour;
        soLuongTour++;
        System.out.println("Them tour thanh cong!");
    }

    // Xóa tour theo mã tour
    public void xoaTour(int maTour) {
        int index = -1;
        for (int i = 0; i < soLuongTour; i++) {
            if (list[i].getMaTour() == maTour) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            System.out.println("Khong tim thay tour voi ma " + maTour);
            return;
        }

        // Dịch chuyển các phần tử để xóa
        for (int i = index; i < soLuongTour - 1; i++) {
            list[i] = list[i + 1];
        }
        list[soLuongTour - 1] = null; // Xóa tham chiếu cuối
        soLuongTour--;
        System.out.println("Xoa tour thanh cong!");
    }

    // Chỉnh sửa tour theo mã tour
    public void chinhSuaTour(int maTour) {
        int index = -1;
        for (int i = 0; i < soLuongTour; i++) {
            if (list[i].getMaTour() == maTour) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            System.out.println("Khong tim thay tour voi ma " + maTour);
            return;
        }

        // Hiển thị thông tin tour hiện tại
        System.out.println("Thong tin tour hien tai:");
        list[index].hienThiThongTin();

        // Hiển thị danh sách thành phố để chọn lại
        hienThiDanhSachThanhPho();

        // Nhập thông tin mới
        System.out.println("Nhap thong tin moi cho tour:");
        TourTrongNuoc newTour = new TourTrongNuoc();
        newTour.nhapThongTin();

        // Kiểm tra mã thành phố hợp lệ
        while (!isValidCityCode(newTour.getMaThanhPho())) {
            System.out.println("Ma thanh pho khong hop le! Vui long chon lai tu danh sach tren:");
            System.out.print("Nhap Ma Thanh Pho: ");
            newTour.setMaThanhPho(sc.nextInt());
            sc.nextLine();
        }

        // Cập nhật tour
        list[index] = newTour;
        System.out.println("Chinh sua tour thanh cong!");
    }

    // Hiển thị danh sách tour
    public void hienThiDanhSachTour() {
        if (soLuongTour == 0) {
            System.out.println("Danh sach tour rong!");
            return;
        }

        System.out.println("Danh sach tour trong nuoc:");
        System.out.println("---------------------------");
        for (int i = 0; i < soLuongTour; i++) {
            System.out.println("Tour " + (i + 1) + ":");
            list[i].hienThiThongTin();
            System.out.println("---------------------------");
        }
    }

    //tim kiem tour theo ma
    public void timKiemTheoMa(){
        System.out.print("Nhap ma can tim: ");
        int maCanTim = sc.nextInt();
        sc.nextLine();
        for (int i=0; i<soLuongTour; i++){
            if (maCanTim == list[i].getMaTour()) {
                list[i].hienThiThongTin();
                return;
            }
        }
        System.out.println("Khong tim thay tour voi ma " + maCanTim);
    }

    // Tìm kiếm tour theo tên (theo chuoi con, không phân biệt hoa thuong)
    public void timKiemTheoTen() {
        System.out.print("Nhap ten hoac chuoi can tim: ");
        String key = sc.nextLine().trim().toLowerCase();
        boolean found = false;
        for (int i = 0; i < soLuongTour; i++) {
            String tname = list[i].getTenTour() == null ? "" : list[i].getTenTour().toLowerCase();
            if (tname.contains(key)) {
                list[i].hienThiThongTin();
                found = true;
            }
        }
        if (!found) System.out.println("Khong tim thay tour voi ten: " + key);
    }

    // Thống kê tour trong nước
    public void thongKeTourTrongNuoc(){
        if (soLuongTour ==0){
            System.out.println("danh sach rong");
        } else {
            System.out.println("---thong ke tour trong nuoc---");
            System.out.println("|So luong tour trong nuoc: " + soLuongTour);
            double tongdoanhthu=0;
            for (int i=0; i< soLuongTour; i++){
                tongdoanhthu += list[i].tinhGiaTour();
            }
            System.out.println("|Tong doanh thu tour trong nuoc: " + tongdoanhthu);
        }

    }

    // Đọc danh sách tour từ file (mỗi dòng: maTour,tenTour,soNgay,donGia,maThanhPho,diaDiemDen,diaDiemDi,phiDichVu)
    public void loadFromFile(String path) throws IOException {
        File f = new File(path);
        if (!f.exists()) return;
        BufferedReader br = new BufferedReader(new FileReader(f));
        try {
            String line;
            soLuongTour = 0; // reset
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length < 8) continue; // invalid line
                int maTour = safeParseInt(parts[0].trim(), 0);
                String tenTour = parts[1].trim().replace(";", ",");
                int soNgay = safeParseInt(parts[2].trim(), 0);
                double donGia = safeParseDouble(parts[3].trim(), 0.0);
                int maThanhPho = safeParseInt(parts[4].trim(), 0);
                String diaDiemDen = parts[5].trim().replace(";", ",");
                String diaDiemDi = parts[6].trim().replace(";", ",");
                double phiDichVu = safeParseDouble(parts[7].trim(), 0.0);

                TourTrongNuoc t = new TourTrongNuoc(maTour, tenTour, soNgay, donGia, maThanhPho, diaDiemDen, diaDiemDi, phiDichVu);
                if (soLuongTour < list.length) {
                    list[soLuongTour++] = t;
                } else {
                    // array full, stop reading
                    break;
                }
            }
        } finally {
            br.close();
        }
    }

    // Ghi danh sách tour hiện tại vào file theo định dạng tương tự
    public void saveToFile(String path) throws IOException {
        File f = new File(path);
        // chắc chắn thư mục cha tồn tại
        if (f.getParentFile() != null && !f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        try {
            for (int i = 0; i < soLuongTour; i++) {
                TourTrongNuoc t = list[i];
                if (t == null) continue;
                String ten = t.getTenTour() == null ? "" : t.getTenTour().replace(",", ";");
                String den = t.getDiaDiemDen() == null ? "" : t.getDiaDiemDen().replace(",", ";");
                String di = t.getDiaDiemDi() == null ? "" : t.getDiaDiemDi().replace(",", ";");
                String line = t.getMaTour() + "," + ten + "," + t.getSoNgay() + "," + t.getDonGia() + "," + t.getMaThanhPho() + "," + den + "," + di + "," + t.getPhiDichVu();
                bw.write(line);
                bw.newLine();
            }
            bw.flush();
        } finally {
            bw.close();
        }
    }

    private static int safeParseInt(String s, int fallback) {
        try { return Integer.parseInt(s); } catch (NumberFormatException e) { return fallback; }
    }

    private static double safeParseDouble(String s, double fallback) {
        try { return Double.parseDouble(s); } catch (NumberFormatException e) { return fallback; }
    }

    // Kiểm tra mã tour có duy nhất trong danh sách hiện tại hay không
    private boolean isMaTourUnique(int maTour) {
        for (int i = 0; i < soLuongTour; i++) {
            if (list[i] != null && list[i].getMaTour() == maTour) {
                return false;
            }
        }
        return true;
    }

    //Hàm manu quan ly tour trong nuoc
    public static void menuTourTrongNuoc(DSTourTrongNuoc dsTour) {
        Scanner sc = new Scanner(System.in);
        // Menu để kiểm tra chức năng
        while (true) {
            System.out.println("\n=== Quan ly tour trong nuoc ===");
            System.out.println("1. Them tour");
            System.out.println("2. Xoa tour");
            System.out.println("3. Chinh sua tour");
            System.out.println("4. Hien thi danh sach tour");
            System.out.println("5. Xem thong ke");
            System.out.println("6. Tim kiem theo ten");
            System.out.println("7. Tim kiem theo ma");
            System.out.println("8. Thoat");
            System.out.print("Chon chuc nang (1-8): ");

            int choice = sc.nextInt();
            sc.nextLine(); // Xóa bộ đệm dòng

            switch (choice) {
                case 1:
                    dsTour.themTour();
                    break;
                case 2:
                    System.out.print("Nhap ma tour can xoa: ");
                    int maTourXoa = sc.nextInt();
                    sc.nextLine(); // Tránh lỗi nhập liệu
                    dsTour.xoaTour(maTourXoa);
                    break;
                case 3:
                    System.out.print("Nhap ma tour can chinh sua: ");
                    int maTourSua = sc.nextInt();
                    sc.nextLine();
                    dsTour.chinhSuaTour(maTourSua);
                    break;
                case 4:
                    dsTour.hienThiDanhSachTour();
                    break;
                case 5:
                    dsTour.thongKeTourTrongNuoc();
                    break;
                case 6:
                    dsTour.timKiemTheoTen();
                    break;
                case 7:
                    dsTour.timKiemTheoMa();
                    break;
                case 8:
                    // Before exiting, save current list to file
                    try {
                        dsTour.saveToFile("D:\\doanOOP\\DU_LICH\\TourDuLich\\DSTourTrongNuoc.txt");
                    } catch (IOException ex) {
                        System.out.println("Loi khi luu DSTourTrongNuoc.txt: " + ex.getMessage());
                    }
                    System.out.println("Thoat ra menu chinh");
                    return;
                default:
                    System.out.println("Lua chuc khong hop le!");
            }
        }
    }
}
