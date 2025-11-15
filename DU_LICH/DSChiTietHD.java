package DU_LICH;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class DSChiTietHD {
    // THUOC TINH
    private ChiTietHD[] list;
    private int soLuong;
    private static final String FILE_PATH = "DU_LICH/DSChiTietHD.txt";

    // CONSTRUCTOR
    public DSChiTietHD() {
        this.list = new ChiTietHD[0];
        this.soLuong = 0;
    }

    // GETTER
    public ChiTietHD[] getList() { return list; }
    public int getSoLuong() { return soLuong; }

    // ===== DOC FILE =====
    // Doc danh sach chi tiet hoa don tu file txt
    public void loadFromFile(String filePath) throws IOException {
        File f = new File(filePath);
        if (!f.exists()) {
            this.list = new ChiTietHD[0];
            this.soLuong = 0;
            return;
        }

        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        int count = 0;
        while ((line = br.readLine()) != null) count++;
        br.close();

        if (count == 0) {
            this.list = new ChiTietHD[0];
            this.soLuong = 0;
            return;
        }

        list = new ChiTietHD[count];
        br = new BufferedReader(new FileReader(filePath));
        int index = 0;

        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 4) {
                String maHD = parts[0].trim();
                String maKHTour = parts[1].trim();
                
                // Validation: Bo qua dong co maHD rong
                if (maHD == null || maHD.isEmpty()) {
                    System.out.println("[CANH BAO] Bo qua chi tiet hoa don co ma HD rong!");
                    continue;
                }
                
                double giaVe = Double.parseDouble(parts[2]);
                
                // Doc danh sach ma khach hang (tu parts[3] tro di)
                int soKhach = parts.length - 3;
                int[] dsMaKH = new int[soKhach];
                for (int i = 0; i < soKhach; i++) {
                    dsMaKH[i] = Integer.parseInt(parts[3 + i]);
                }

                list[index++] = new ChiTietHD(maHD, maKHTour, dsMaKH, giaVe);
            }
        }
        br.close();
        soLuong = index;
    }

    // ===== GHI FILE =====
    // Luu danh sach chi tiet hoa don vao file txt
    public void saveToFile(String filePath) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
        for (int i = 0; i < soLuong; i++) {
            ChiTietHD ct = list[i];
            if (ct == null) continue;
            
            StringBuilder line = new StringBuilder();
            line.append(ct.getMaHD()).append(",");
            line.append(ct.getMaKHTour()).append(",");
            line.append(ct.getGiaVe());
            
            // Ghi danh sach ma khach hang
            int[] dsKH = ct.getDsMaKhachHang();
            for (int maKH : dsKH) {
                line.append(",").append(maKH);
            }
            
            bw.write(line.toString());
            bw.newLine();
        }
        bw.close();
    }

    // ===== TU DONG TAO CHI TIET =====
    // Tu dong tao chi tiet khi them hoa don (CASCADE INSERT)
    public void taoChiTiet(HoaDon hd, int[] dsMaKhachHang) {
        String maHD = hd.getMaHD();
        
        // Validation: Khong tao chi tiet neu maHD rong
        if (maHD == null || maHD.trim().isEmpty()) {
            System.out.println("[LOI] Khong the tao chi tiet: Ma hoa don rong!");
            return;
        }
        
        String maKHTour = hd.getMaKHTour().getMaKHTour();
        double giaVe = hd.getMaKHTour().getGiaVe();
        
        ChiTietHD chiTiet = new ChiTietHD(maHD, maKHTour, dsMaKhachHang, giaVe);
        
        // Them vao mang
        list = Arrays.copyOf(list, list.length + 1);
        list[list.length - 1] = chiTiet;
        soLuong++;
        
        // Luu file
        try {
            saveToFile(FILE_PATH);
            System.out.println("Da tao chi tiet hoa don tu dong!");
        } catch (IOException e) {
            System.out.println("Loi luu chi tiet: " + e.getMessage());
        }
    }

    // ===== TU DONG XOA CHI TIET =====
    // Tu dong xoa chi tiet khi xoa hoa don (CASCADE DELETE)
    public void xoaChiTiet(String maHD) {
        for (int i = 0; i < soLuong; i++) {
            if (list[i].getMaHD().equals(maHD)) {
                // Xoa khoi mang
                for (int j = i; j < soLuong - 1; j++) list[j] = list[j + 1];
                list = Arrays.copyOf(list, list.length - 1);
                soLuong--;
                
                // Luu file
                try {
                    saveToFile(FILE_PATH);
                    System.out.println("Da xoa chi tiet hoa don tu dong!");
                } catch (IOException e) {
                    System.out.println("Loi luu chi tiet: " + e.getMessage());
                }
                return;
            }
        }
    }

    // ===== XEM CHI TIET =====
    // Xem chi tiet cua 1 hoa don
    public ChiTietHD xemChiTiet(String maHD) {
        for (int i = 0; i < soLuong; i++) {
            if (list[i] != null && list[i].getMaHD().equals(maHD)) {
                return list[i];
            }
        }
        return null;
    }

    // ===== HIEN THI TAT CA =====
    public void hienThiDanhSach() {
        if (soLuong == 0) {
            System.out.println("Chua co chi tiet hoa don nao!");
            return;
        }
        System.out.println("\n" + "=".repeat(80));
        System.out.println("                    DANH SACH CHI TIET HOA DON");
        System.out.println("=".repeat(80));
        for (int i = 0; i < soLuong; i++) {
            System.out.println("\n[Chi tiet " + (i + 1) + "]");
            list[i].hienThiThongTin();
        }
    }

    // ===== MAIN TEST =====
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DSChiTietHD dsChiTiet = new DSChiTietHD();

        // Load file
        try {
            dsChiTiet.loadFromFile(FILE_PATH);
        } catch (IOException e) {
            System.out.println("Loi load file: " + e.getMessage());
        }

        boolean exit = false;
        while (!exit) {
            System.out.println("\n=== MENU CHI TIET HOA DON ===");
            System.out.println("1. Hien thi tat ca chi tiet");
            System.out.println("2. Xem chi tiet theo ma hoa don");
            System.out.println("3. Thoat");
            System.out.print("Chon: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    dsChiTiet.hienThiDanhSach();
                    break;
                case 2:
                    System.out.print("Nhap ma hoa don: ");
                    String maHD = sc.nextLine();
                    ChiTietHD ct = dsChiTiet.xemChiTiet(maHD);
                    if (ct != null) {
                        System.out.println("\nChi tiet hoa don:");
                        ct.hienThiThongTin();
                    } else {
                        System.out.println("Khong tim thay chi tiet!");
                    }
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    System.out.println("Lua chon khong hop le.");
            }
        }

        System.out.println("Thoat chuong trinh!");
        sc.close();
    }
}
