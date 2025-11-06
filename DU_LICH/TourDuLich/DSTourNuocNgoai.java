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

public class DSTourNuocNgoai {
    private TourNuocNgoai[] list;
    private int soLuongTour;
    private DSQuocGia dsQuocGia;
    private static final Scanner sc = new Scanner(System.in);

    // Constructor
    public DSTourNuocNgoai(DSQuocGia dsQuocGia) {
        this.list = new TourNuocNgoai[10]; // Kích thước tối đa, có thể điều chỉnh
        this.soLuongTour = 0;
        this.dsQuocGia = dsQuocGia;
    }

    // Getter
    public TourNuocNgoai[] getList() { return list; }
    public int getSoLuongTour() { return soLuongTour; }

    // Hiển thị danh sách quốc gia nước ngoài (không bao gồm quốc gia nội địa)
    private void hienThiDanhSachQuocGiaNuocNgoai() {
        QuocGia[] countries = dsQuocGia.getList();
        if (countries.length <= 1) {
            System.out.println("Khong co quoc gia nuoc ngoai!");
            return;
        }
        System.out.println("Danh sach quoc gia nuoc ngoai:");
        for (int i = 1; i < countries.length; i++) { // Bỏ qua quốc gia nội địa (i=0)
            System.out.println(" - " + countries[i].getTenNuoc() + " (Ma " + countries[i].getMaQuocGia() + ")");
        }
    }

    // Hiển thị danh sách thành phố của một quốc gia dựa trên mã quốc gia
    private void hienThiDanhSachThanhPho(String maQuocGia) {
        QuocGia country = getCountryByMa(maQuocGia);
        if (country == null || maQuocGia.equals(dsQuocGia.getList()[dsQuocGia.getCurrentCountryIndex()].getMaQuocGia())) {
            System.out.println("Quoc gia khong hop le hoac la quoc gia noi dia!");
            return;
        }
        System.out.println("Danh sach thanh pho cua " + country.getTenNuoc() + ":");
        for (ThanhPho city : country.getListTPhos()) {
            System.out.println(" - " + city.getTenTPho() + " (Ma: " + city.getMaTPho() + ")");
        }
    }

    // Tìm quốc gia theo mã
    private QuocGia getCountryByMa(String maQuocGia) {
        for (QuocGia qg : dsQuocGia.getList()) {
            if (qg.getMaQuocGia() != null && qg.getMaQuocGia().equals(maQuocGia)) {
                return qg;
            }
        }
        return null;
    }

    // Kiểm tra mã thành phố có hợp lệ trong quốc gia đã chọn
    private boolean isValidCityCode(String maQuocGia, String maThanhPho) {
        QuocGia country = getCountryByMa(maQuocGia);
        if (country == null || maQuocGia.equals(dsQuocGia.getList()[dsQuocGia.getCurrentCountryIndex()].getMaQuocGia())) {
            return false;
        }
        for (ThanhPho city : country.getListTPhos()) {
            if (city.getMaTPho() != null && city.getMaTPho().equals(maThanhPho)) {
                return true;
            }
        }
        return false;
    }

    // Thêm tour nước ngoài
    public void themTour() {
        if (soLuongTour >= list.length) {
            System.out.println("Danh sach tour da day!");
            return;
        }

        // Hiển thị danh sách quốc gia nước ngoài
        hienThiDanhSachQuocGiaNuocNgoai();

        // Nhập mã quốc gia
        System.out.print("Nhap ma quoc gia nuoc ngoai: ");
        String maQuocGia = sc.nextLine();

        // Kiểm tra mã quốc gia hợp lệ và không phải nội địa
        QuocGia country = getCountryByMa(maQuocGia);
        if (country == null || maQuocGia.equals(dsQuocGia.getList()[dsQuocGia.getCurrentCountryIndex()].getMaQuocGia())) {
            System.out.println("Ma quoc gia khong hop le hoac la quoc gia noi dia!");
            return;
        }

        // Hiển thị danh sách thành phố của quốc gia đã chọn
        hienThiDanhSachThanhPho(maQuocGia);

        // Nhập thông tin tour
        TourNuocNgoai tour = new TourNuocNgoai();
        System.out.println("Nhap thong tin tour nuoc ngoai:");
        tour.nhapThongTin();
        // gắn mã quốc gia đã chọn vào tour
        tour.setMaQuocGia(maQuocGia);

        // Kiểm tra mã tour phải là duy nhất
        while (!isMaTourUnique(tour.getMaTour())) {
            System.out.println("Ma tour da ton tai. Vui long nhap ma tour khac:");
            System.out.print("Nhap Ma Tour: ");
            String newMa = sc.nextLine();
            tour.setMaTour(newMa);
        }

        // Kiểm tra mã thành phố hợp lệ
        while (!isValidCityCode(maQuocGia, tour.getMaThanhPho())) {
            System.out.println("Ma thanh pho khong hop le! Vui long chon lai tu danh sach tren:");
            System.out.print("Nhap Ma Thanh Pho: ");
            tour.setMaThanhPho(sc.nextLine());
        }

        // Thêm tour vào danh sách
        list[soLuongTour] = tour;
        soLuongTour++;
        System.out.println("Them tour thanh cong!");
    }

    // Xóa tour theo mã tour
    public void xoaTour() {
        System.out.print("Nhap ma tour can xoa: ");
        String maTourXoa = sc.nextLine();
        int index = -1;
        for (int i = 0; i < soLuongTour; i++) {
            if (list[i].getMaTour() != null && list[i].getMaTour().equals(maTourXoa)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            System.out.println("Khong tim thay tour voi ma " + maTourXoa);
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
    public void chinhSuaTour() {
        System.out.print("Nhap ma tour can chinh sua: ");
        String maTourSua = sc.nextLine();
        int index = -1;
        for (int i = 0; i < soLuongTour; i++) {
            if (list[i].getMaTour() != null && list[i].getMaTour().equals(maTourSua)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            System.out.println("Khong tim thay tour voi ma " + maTourSua);
            return;
        }

        // Hiển thị thông tin tour hiện tại
        System.out.println("Thong tin tour hien tai:");
        list[index].hienThiThongTin();

        // Hiển thị danh sách quốc gia nước ngoài
        hienThiDanhSachQuocGiaNuocNgoai();

        // Nhập mã quốc gia
        System.out.print("Nhap ma quoc gia nuoc ngoai: ");
        String maQuocGia = sc.nextLine();

        // Kiểm tra mã quốc gia hợp lệ và không phải nội địa
        QuocGia country = getCountryByMa(maQuocGia);
        if (country == null || maQuocGia.equals(dsQuocGia.getList()[dsQuocGia.getCurrentCountryIndex()].getMaQuocGia())) {
            System.out.println("Ma quoc gia khong hop le hoac la quoc gia noi dia!");
            return;
        }

        // Hiển thị danh sách thành phố của quốc gia đã chọn
        hienThiDanhSachThanhPho(maQuocGia);

        // Nhập thông tin mới
        System.out.println("Nhap thong tin moi cho tour:");
        TourNuocNgoai newTour = new TourNuocNgoai();
        newTour.nhapThongTin();
        // gắn mã quốc gia đã chọn vào tour mới
        newTour.setMaQuocGia(maQuocGia);

        // Kiểm tra mã thành phố hợp lệ
        while (!isValidCityCode(maQuocGia, newTour.getMaThanhPho())) {
            System.out.println("Ma thanh pho khong hop le! Vui long chon lai tu danh sach tren:");
            System.out.print("Nhap Ma Thanh Pho: ");
            newTour.setMaThanhPho(sc.nextLine());
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

        System.out.println("Danh sach tour nuoc ngoai:");
        System.out.println("---------------------------");
        for (int i = 0; i < soLuongTour; i++) {
            System.out.println("Tour " + (i + 1) + ":");
            list[i].hienThiThongTin();
            System.out.println("---------------------------");
        }
    }

    // Thống kê tour nước ngoài (số tour và tổng doanh thu)
    public void thongKeTourNuocNgoai() {
        if (soLuongTour == 0) {
            System.out.println("Danh sach rong");
            return;
        }
        System.out.println("--- thong ke tour nuoc ngoai ---");
        System.out.println("So luong tour nuoc ngoai: " + soLuongTour);
        double tong = 0.0;
        for (int i = 0; i < soLuongTour; i++) {
            tong += list[i].tinhGiaTour();
        }
        System.out.println("Tong doanh thu tour nuoc ngoai: " + tong);
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

    // Tìm kiếm tour theo mã
    public void timKiemTheoMa() {
        System.out.print("Nhap ma can tim: ");
        String maCanTim = sc.nextLine();
        for (int i = 0; i < soLuongTour; i++) {
            if (list[i].getMaTour() != null && list[i].getMaTour().equals(maCanTim)) {
                list[i].hienThiThongTin();
                return;
            }
        }
        System.out.println("Khong tim thay tour voi ma " + maCanTim);
    }

    // Đọc danh sách tour từ file (mỗi dòng: maTour,tenTour,soNgay,donGia,maQuocGia,maThanhPho,diaDiemDen,diaDiemDi,visa,donViTienTe)
    public void loadFromFile(String path) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            soLuongTour = 0;
            String line;
            while ((line = br.readLine()) != null && soLuongTour < list.length) {
                if (line.trim().isEmpty()) continue;
                String[] p = line.split(",");
                if (p.length < 10) {
                    System.out.println("Bỏ qua dòng không hợp lệ (thiếu trường): " + line);
                    continue;
                }
                try {
                    TourNuocNgoai t = new TourNuocNgoai(
                        p[0].trim(),   // maTour
                        p[1].trim(),   // tenTour
                        Integer.parseInt(p[2].trim()),   // soNgay
                        Double.parseDouble(p[3].trim()), // donGia
                        p[4].trim(),   // maQuocGia
                        p[5].trim(),   // maThanhPho
                        p[6].trim(),   // diaDiemDen
                        p[7].trim(),   // diaDiemDi
                        Double.parseDouble(p[8].trim()), // visa
                        p[9].trim()    // donViTienTe
                    );
                    list[soLuongTour++] = t;
                } catch (Exception ex) {
                    System.out.println("Lỗi phân tích dòng: " + line + " -> " + ex.getMessage());
                }
            }
        }
    }

    // Ghi danh sách tour nước ngoài vào file (định dạng như trên)
    public void saveToFile(String path) throws IOException {
        File f = new File(path);
        if (f.getParentFile() != null) f.getParentFile().mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
            for (int i = 0; i < soLuongTour; i++) {
                TourNuocNgoai t = list[i];
                bw.write(String.join(",",
                    t.getMaTour(),
                    t.getTenTour(),
                    String.valueOf(t.getSoNgay()),
                    String.valueOf(t.getDonGia()),
                    t.getMaQuocGia(),
                    t.getMaThanhPho(),
                    t.getDiaDiemDen(),
                    t.getDiaDiemDi(),
                    String.valueOf(t.getVisa()),
                    t.getDonViTienTe()
                ));
                bw.newLine();
            }
        }
    }

    // Kiểm tra mã tour có duy nhất trong danh sách hiện tại hay không
    private boolean isMaTourUnique(String maTour) {
        if (maTour == null) return false;
        for (int i = 0; i < soLuongTour; i++) {
            if (list[i] != null && maTour.equals(list[i].getMaTour())) {
                return false;
            }
        }
        return true;
    }

    // Menu quản lý tour nước ngoài
    // Instance-style menu giống DSTourTrongNuoc: cho phép truyền đường dẫn lưu
    public void menu() { menu((String) null); }

    public void menu(String providedPath) {
        String defaultPath = "D:\\doanOOP\\DU_LICH\\TourDuLich\\DSTourNuocNgoai.txt";
        String savePath = (providedPath != null && !providedPath.trim().isEmpty()) ? providedPath : defaultPath;

        // Menu để kiểm tra chức năng
        while (true) {
            System.out.println("\n=== Quan ly tour nuoc ngoai ===");
            System.out.println("1. Them tour");
            System.out.println("2. Xoa tour");
            System.out.println("3. Chinh sua tour");
            System.out.println("4. Hien thi danh sach tour");
            System.out.println("5. Thong ke");
            System.out.println("6. Tim kiem theo ten");
            System.out.println("7. Tim kiem theo ma");
            System.out.println("0. Thoat (luu thay doi)");
            System.out.print("Chon chuc nang: ");

            int choice = sc.nextInt();
            sc.nextLine(); // Xóa bộ đệm

            switch (choice) {
                case 1:
                    this.themTour();
                    break;
                case 2:
                    this.xoaTour();
                    break;
                case 3:
                    this.chinhSuaTour();
                    break;
                case 4:
                    this.hienThiDanhSachTour();
                    break;
                case 5:
                    this.thongKeTourNuocNgoai();
                    break;
                case 6:
                    this.timKiemTheoTen();
                    break;
                case 7:
                    this.timKiemTheoMa();
                    break;
                case 0:
                    // Before exiting, save current list to file
                    try {
                        this.saveToFile(savePath);
                    } catch (IOException ex) {
                        System.out.println("Loi khi luu DSTourNuocNgoai.txt: " + ex.getMessage());
                    }
                    System.out.println("Thoat ra trang chu");
                    return;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        }
    }
}