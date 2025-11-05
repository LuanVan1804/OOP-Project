package DU_LICH;
import java.io.IOException;
import java.util.Scanner;

import DU_LICH.TourDuLich.DSTourNuocNgoai;
import DU_LICH.TourDuLich.DSTourTrongNuoc;
import DU_LICH.TourDuLich.TourNuocNgoai;
import DU_LICH.TourDuLich.TourTrongNuoc;
import DU_LICH.DiaDiemDuLich.DSQuocGia;
import DU_LICH.DiaDiemDuLich.DSThanhPho;
import DU_LICH.DiaDiemDuLich.QuocGia;
import DU_LICH.DiaDiemDuLich.ThanhPho;


public class Main {
    public static void main(String[] args) {
        System.out.println("=== Chuong trinh Quan ly Tour - Main ===");

        DSQuocGia dsQuocGia = null;
        DSThanhPho dsThanhPho = null;

        // === ĐỌC FILE CHUNG MỘT LẦN DUY NHẤT ===
        try {
            dsQuocGia = DSQuocGia.loadFromFile("D:\\doanOOP\\DU_LICH\\DiaDiemDuLich\\quocGia.txt");
        } catch (IOException e) {
            System.out.println("Loi doc quocGia.txt: " + e.getMessage());
            dsQuocGia = new DSQuocGia();
        }

        try {
            dsThanhPho = DSThanhPho.loadFromFile("D:\\doanOOP\\DU_LICH\\DiaDiemDuLich\\ThanhPho.txt");
        } catch (IOException e) {
            System.out.println("Loi doc ThanhPho.txt: " + e.getMessage());
            dsThanhPho = new DSThanhPho();
        }

        // Gán thành phố cho từng quốc gia
        if (dsQuocGia != null && dsThanhPho != null) {
            for (QuocGia q : dsQuocGia.getList()) {
                if (q != null) {
                    q.setListTPhos(dsThanhPho.getCitiesByCountry(q.getMaQuocGia()));
                }
            }
        }

        // Tạo các bộ quản lý tour (sử dụng DSQuocGia đã load)
        DSTourTrongNuoc dsTrong = new DSTourTrongNuoc(dsQuocGia);
        DSTourNuocNgoai dsNgoai = new DSTourNuocNgoai(dsQuocGia);

        // Load dữ liệu tour từ file
        try {
            dsTrong.loadFromFile("D:\\doanOOP\\DU_LICH\\TourDuLich\\DSTourTrongNuoc.txt");
        } catch (IOException e) { /* ignore */ }

        try {
            dsNgoai.loadFromFile("D:\\doanOOP\\DU_LICH\\TourDuLich\\DSTourNuocNgoai.txt");
        } catch (IOException e) { /* ignore */ }

        // Menu chinh
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== MENU CHINH ===");
            System.out.println("1. Quan ly tour trong nuoc (menu)");
            System.out.println("2. Quan ly tour nuoc ngoai (menu)");
            System.out.println("3. Thoat");
            System.out.print("Chon (1-3): ");

            int choice;
            // Doc lua chon va xu ly ngoai le
            try {
                choice = Integer.parseInt(sc.nextLine().trim()); // doc dong va chuyen sang so nguyen
            } catch (Exception ex) {
                System.out.println("Lua chon khong hop le. Thu lai.");
                continue;
            }

            switch (choice) {
                case 1:
                    // Gọi menu quản lý tour trong nước
                    DSTourTrongNuoc.menuTourTrongNuoc(dsTrong);
                    break;
                case 2:
                    // Gọi menu quản lý tour nước ngoài
                    DSTourNuocNgoai.menuTourNuocNgoai(dsNgoai);
                    break;
                case 3:
                    System.out.println("Thoat chuong trinh. Tam biet!");
                    sc.close();
                    return;
                default:
                    System.out.println("Chon tu 1 den 3.");
            }
        }
    }
}

