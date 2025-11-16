package DU_LICH;

import java.io.*;
import java.util.Arrays;

public class DSChiTietHD {
    // THUOC TINH
    private ChiTietHD[] list;
    private int soLuong;

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

    // ===== THEM CHI TIET =====
    public void them(ChiTietHD chiTiet) {
        list = Arrays.copyOf(list, list.length + 1);
        list[list.length - 1] = chiTiet;
        soLuong++;
    }

    // ===== XOA CHI TIET =====
    public boolean xoa(String maHD) {
        for (int i = 0; i < soLuong; i++) {
            if (list[i].getMaHD().equals(maHD)) {
                // Xoa khoi mang
                for (int j = i; j < soLuong - 1; j++) list[j] = list[j + 1];
                list = Arrays.copyOf(list, list.length - 1);
                soLuong--;
                return true;
            }
        }
        return false;
    }

    // ===== TIM CHI TIET =====
    public ChiTietHD tim(String maHD) {
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
        System.out.println("\n================================================================================");
        System.out.println("                    DANH SACH CHI TIET HOA DON");
        System.out.println("================================================================================");
        for (int i = 0; i < soLuong; i++) {
            System.out.println("\n[Chi tiet " + (i + 1) + "]");
            list[i].hienThiThongTin();
        }
    }
}