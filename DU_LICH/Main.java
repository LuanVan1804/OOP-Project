package DU_LICH;
import java.io.IOException;
import java.util.Scanner;

import DU_LICH.TourDuLich.DSTour;
import DU_LICH.DiaDiemDuLich.DSQuocGia;
import DU_LICH.DiaDiemDuLich.DSThanhPho;
import DU_LICH.DiaDiemDuLich.QuocGia;

import DU_LICH.NH_KS_PT.DSKhachSan;
import DU_LICH.NH_KS_PT.DSNhaHang;
import DU_LICH.Nguoi.DSHDV;
import DU_LICH.Nguoi.DSKhachHang;


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


        DSTour dsTour = new DSTour();

        String dsTourPath = "D:\\doanOOP\\DU_LICH\\TourDuLich\\DSTour.txt";
        try {
            dsTour.loadFromFile(dsTourPath);
        } catch (IOException e) {
            System.out.println("Loi doc DSTour.txt: " + e.getMessage());
        }
        
        // Load dữ liệu từ file KhachSan.txt, NhaHang.txt, PhuongTien.txt
        DSKhachSan dsKhachSan = new DSKhachSan();
        DSNhaHang dsNhaHang = new DSNhaHang();
        String khachSanPath = "D:\\doanOOP\\DU_LICH\\NH_KS_PT\\KhachSan.txt";
        String nhaHangPath = "D:\\doanOOP\\DU_LICH\\NH_KS_PT\\NhaHang.txt";
        try {
            dsKhachSan.loadFromFile(khachSanPath);
        } catch (Exception ex) {
            System.out.println("Loi doc KhachSan.txt: " + ex.getMessage());
        }
        try {
            dsNhaHang.loadFromFile(nhaHangPath);
        } catch (Exception ex) {
            System.out.println("Loi doc NhaHang.txt: " + ex.getMessage());
        }
        // Load dữ liệu từ file KhachHang.txt
        DSKhachHang dsKhachHang = new DSKhachHang();
        String khachHangPath = "D:\\doanOOP\\DU_LICH\\Nguoi\\KhachHang.txt";
        try {
            dsKhachHang.loadFromFile(khachHangPath);
        } catch (Exception ex) {
            System.out.println("Loi doc KhachHang.txt: " + ex.getMessage());
        }
        // Load dữ liệu từ file HDV.txt
        DSHDV dsHDV = new DSHDV();
        String hdvPath = "D:\\doanOOP\\DU_LICH\\Nguoi\\HDV.txt";
        try {
            dsHDV.loadFromFile(hdvPath);
        } catch (Exception ex) {
            System.out.println("Loi doc HDV.txt: " + ex.getMessage());
        }



        // Menu chinh
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== MENU CHINH ===");
            System.out.println("1. Quan ly tour");
            System.out.println("3. Quan ly khach san");
            System.out.println("4. Quan ly nha hang");
            System.out.println("5. Quan ly huong dan vien");
            System.out.println("6. Quan ly khach hang");
            System.out.println("0. Thoat");
            System.out.print("Chon chuc nang: ");

            int choice = sc.nextInt();
            sc.nextLine();          
            switch (choice) {
                case 1:
                    // Gọi menu quản lý tour tổng hợp
                    dsTour.menu(dsTourPath);
                    break;
                case 3:
                    // Gọi menu quản lý khách sạn 
                    dsKhachSan.menu(khachSanPath);
                    break;
                case 4:
                    // Gọi menu quản lý nhà hàng
                    dsNhaHang.menu(nhaHangPath);
                    break;
                case 5:
                    // Gọi menu quản lý huong dan vien
                    dsHDV.menu(hdvPath);
                    break;
                case 6:
                    // Gọi menu quản lý khách hàng
                    dsKhachHang.menu(khachHangPath);
                    break;
                case 0:
                    System.out.println("Thoat chuong trinh. Tam biet!");
                    sc.close();
                    return;
                default:
                    System.out.println("Lua chon khong hop le.");
            }   
        }
    }
}

