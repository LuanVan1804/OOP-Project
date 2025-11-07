package DU_LICH.TourDuLich;

import java.util.Scanner;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;

public class DSTour {
    private Tour[] list;
    private int soLuongTour;
    private static final Scanner sc = new Scanner(System.in);

    public DSTour() {
        this.list = new Tour[20];
        this.soLuongTour = 0;
    }

    public Tour[] getList() {
        return list;
    }

    public int getSoLuongTour() {
        return soLuongTour;
    }

    // Kiểm tra mã tour có duy nhất không
    private boolean isMaTourUnique(String maTour) {
        if (maTour == null)
            return false;
        for (int i = 0; i < soLuongTour; i++) {
            if (list[i] != null && maTour.equals(list[i].getMaTour()))
                return false;
        }
        return true;
    }

    // 1. THÊM TOUR
    // ========================================
    public void themTour() {
        if (soLuongTour >= list.length) {
            System.out.println("Danh sach day!");
            return;
        }

        System.out.println("1. Tour trong nuoc");
        System.out.println("2. Tour nuoc ngoai");
        System.out.print("Chon: ");
        int choice = sc.nextInt();
        sc.nextLine();

        Tour tour = null;

        if (choice == 1) {
           tour = new TourTrongNuoc();
           tour.nhapThongTin();
        } else if (choice == 2) {
            tour = new TourNuocNgoai();
            tour.nhapThongTin();
        } else {
            System.out.println("Khong hop le!");
            return;
        }

        while (!isMaTourUnique(tour.getMaTour())) {
            System.out.print("Ma tour da ton tai! Nhap lai: ");
            tour.setMaTour(sc.nextLine());
        }

        list[soLuongTour++] = tour;
        String kind = tour.loai().equals("TrongNuoc") ? "Trong nuoc" : "Nuoc ngoai";
        System.out.println("Them tour [" + kind + "] thanh cong!");
    }
    
    // ========================================
    // 2. HIỂN THỊ DANH SÁCH TOUR
    public void hienThiDanhSachTour() {
        if (soLuongTour == 0) {
            System.out.println("Danh sach tour rong!");
            return;
        }
        System.out.println("Danh sach tour:");
        for (int i = 0; i < soLuongTour; i++) {
            System.out.println("---------------------------");
            list[i].hienThiThongTin();
        }
    }


    
    // 3. XÓA TOUR THEO MÃ
    public void xoaTour(String maTour) {
        int index = -1;
        for (int i = 0; i < soLuongTour; i++) {
            if (list[i] != null && maTour.equals(list[i].getMaTour())) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            System.out.println("Khong tim thay tour voi ma " + maTour);
            return;
        }
        for (int i = index; i < soLuongTour - 1; i++)
            list[i] = list[i + 1];
        list[soLuongTour - 1] = null;
        soLuongTour--;
        System.out.println("Xoa tour thanh cong!");
    }

    // 4. CHỈNH SỬA TOUR THEO MÃ
    public void chinhSuaTour(String maTour) {
        boolean found=false;
    }
    //---------------------tim kiem theo ten-------------------
    public Tour[] timKiemTheoTen(String key) {
        if (key == null || key.trim().isEmpty()) {
            return new Tour[0]; // mảng rỗng
        }

        String tuKhoa = key.trim().toLowerCase();
        int count = 0;

        // 1. Đếm số tour khớp
        for (int i = 0; i < soLuongTour; i++) {
            String tenTour = list[i].getTenTour();
            if (tenTour != null && tenTour.toLowerCase().contains(tuKhoa)) {
                count++;
            }
        }

        // 2. Tạo mảng kết quả
        Tour[] ketQua = new Tour[count];
        int index = 0;

        // 3. Đổ dữ liệu vào mảng
        for (int i = 0; i < soLuongTour; i++) {
            String tenTour = list[i].getTenTour();
            if (tenTour != null && tenTour.toLowerCase().contains(tuKhoa)) {
                ketQua[index++] = list[i];
            }
        }

        return ketQua;
    }
    //---------------------tim kiem theo ma-------------------
    public void timKiemTheoMa(String ma) {
        for (int i = 0; i < soLuongTour; i++) {
            if (list[i].getMaTour() != null && list[i].getMaTour().equals(ma)) {
                list[i].hienThiThongTin();
                return;
            }
        }
        System.out.println("Khong tim thay tour voi ma " + ma);
    }
    //-----------------------thong ke----------------------
    public void thongKe() {
        if (soLuongTour == 0) {
            System.out.println("Danh sach tour rong!");
            return;
        }

        int countTrongNuoc = 0, countNuocNgoai = 0;
        double doanhThuTrongNuoc = 0.0, doanhThuNuocNgoai = 0.0;

        for (int i = 0; i < soLuongTour; i++) {
            Tour t = list[i];
            double gia = t.tinhGiaTour();
            String loai = t.loai();

            if (loai.equals("TrongNuoc")) {
                countTrongNuoc++;
                doanhThuTrongNuoc += gia;
            } else if (loai.equals("NuocNgoai")) {
                countNuocNgoai++;
                doanhThuNuocNgoai += gia;
            }
        }

        // Tổng
        int tongSoTour = countTrongNuoc + countNuocNgoai;
        double tongDoanhThu = doanhThuTrongNuoc + doanhThuNuocNgoai;

        // In kết quả
        System.out.println("=== THONG KE DOANH THU TOUR ===");
        System.out.printf("Tour trong nuoc: %,d tour | Doanh thu: %, .0f\n", countTrongNuoc, doanhThuTrongNuoc);
        System.out.printf("Tour nuoc ngoai: %,d tour | Doanh thu: %, .0f\n", countNuocNgoai, doanhThuNuocNgoai);
        System.out.println("--------------------------------");
        System.out.printf("TONG CONG      : %,d tour | Doanh thu: %, .0f\n", tongSoTour, tongDoanhThu);
    }

    // doc/ghi file
    public void loadFromFile(String path) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            soLuongTour = 0;
            String line;
            while ((line = br.readLine()) != null && soLuongTour < list.length) {
                if (line.trim().isEmpty())
                    continue;
                String[] p = line.split(",");
                try {
                    if (p.length == 8) {
                        TourTrongNuoc t = new TourTrongNuoc(
                                p[0].trim(),
                                p[1].trim(),
                                Integer.parseInt(p[2].trim()),
                                Double.parseDouble(p[3].trim()),
                                p[4].trim(),
                                p[5].trim(),
                                p[6].trim(),
                                Double.parseDouble(p[7].trim()));
                        list[soLuongTour++] = t;
                    } else if (p.length == 10) {
                        TourNuocNgoai t = new TourNuocNgoai(
                                p[0].trim(),
                                p[1].trim(),
                                Integer.parseInt(p[2].trim()),
                                Double.parseDouble(p[3].trim()),
                                p[4].trim(),
                                p[5].trim(),
                                p[6].trim(),
                                p[7].trim(),
                                Double.parseDouble(p[8].trim()), p[9].trim());
                        list[soLuongTour++] = t;
                    } else {
                        System.out.println("Bo qua dong khong hop le: " + line);
                    }
                } catch (Exception ex) {
                    System.out.println("Loi phan tich dong: " + line + " -> " + ex.getMessage());
                }
            }
        }
    }

    public void saveToFile(String path) throws IOException {
        File f = new File(path);
        if (f.getParentFile() != null) f.getParentFile().mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
            for (int i = 0; i < soLuongTour; i++) {
                Tour t = list[i];
                String loai = t.loai();  // "TrongNuoc" hoặc "NuocNgoai"

                if (loai.equals("TrongNuoc")) {
                    TourTrongNuoc tt = (TourTrongNuoc) t;
                    bw.write(String.join(",",
                        tt.getMaTour(), tt.getTenTour(), String.valueOf(tt.getSoNgay()),
                        String.valueOf(tt.getDonGia()),
                        tt.getMaThanhPho(), tt.getDiaDiemDen(), tt.getDiaDiemDi(),
                        String.valueOf(tt.getPhiDichVu())
                    ));
                    bw.newLine();

                } else if (loai.equals("NuocNgoai")) {
                    TourNuocNgoai tn = (TourNuocNgoai) t;
                    bw.write(String.join(",",
                        tn.getMaTour(), 
                        tn.getTenTour(), 
                        String.valueOf(tn.getSoNgay()),
                        String.valueOf(tn.getDonGia()),
                        tn.getMaQuocGia(), 
                        tn.getMaThanhPho(), 
                        tn.getDiaDiemDen(), 
                        tn.getDiaDiemDi(),
                        String.valueOf(tn.getVisa()), 
                        tn.getDonViTienTe()
                    ));
                    bw.newLine();
                }
            }
        }
    }

    // instance menu
    public void menu() {
        menu((String) null);
    }

    public void menu(String providedPath) {
        String savePath = (providedPath != null && !providedPath.trim().isEmpty()) ? providedPath
                : "D:\\doanOOP\\DU_LICH\\TourDuLich\\DSTour.txt";
        while (true) {
            System.out.println("\n=== Quan ly tour (tong hop) ===");
            System.out.println("1. Them tour");
            System.out.println("2. Xoa tour");
            System.out.println("3. Chinh sua tour");
            System.out.println("4. Hien thi danh sach tour");
            System.out.println("5. Tim kiem theo ten");
            System.out.println("6. Tim kiem theo ma");
            System.out.println("7. Thong ke");
            System.out.println("0. Thoat");
            System.out.print("Chon chuc nang: ");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    themTour();
                    break;
                case 2:
                    System.out.print("Nhap ma tour can xoa: ");
                    String maTour = sc.nextLine();
                    xoaTour(maTour);
                    break;
                case 3:
                    System.out.print("Nhap ma tour can chinh sua: ");
                    String maTourSua = sc.nextLine();
                    chinhSuaTour(maTourSua);
                    break;
                case 4:
                    hienThiDanhSachTour();
                    break;
                case 5:
                    System.out.print("Nhap ten tour can tim: ");
                    String tenTour = sc.nextLine().toLowerCase().trim();
                    Tour[] ketQua = timKiemTheoTen(tenTour);
                    if (ketQua == null || ketQua.length == 0) {
                        System.out.println("Khong tim thay tour voi ten: " + tenTour);
                    } else {
                        System.out.println("-------Ket qua tim kiem:--------");
                        for (Tour t : ketQua) {
                            t.hienThiThongTin();
                        }
                    }
                    break;
                case 6:
                    System.out.print("Nhap ma tour can tim: ");
                    String maTourTim = sc.nextLine();
                    timKiemTheoMa(maTourTim);
                    break;
                case 7:
                    thongKe();
                    break;
                case 0:
                    try {
                        saveToFile(savePath);
                    } catch (IOException ex) {
                        System.out.println("Loi khi luu DSTour.txt: " + ex.getMessage());
                    }
                    System.out.println("Thoat ra menu chinh");
                    return;
                default:
                    System.out.println("Lua chon khong hop le");
            }
        }
    }
}
