package DU_LICH;

import java.io.*;
import java.util.Arrays;

public class DSChiTietHD {
    // THUOC TINH
    private ChiTietHD[] list;
    private int soLuong;
    
    // NEW: Tham chieu den cac DS de truyen vao ChiTietHD
    private DSKHTour dsKHTour;
    private DSChiPhiKHTour dsChiPhi;

    // CONSTRUCTOR
    public DSChiTietHD() {
        this.list = new ChiTietHD[0];
        this.soLuong = 0;
    }

    // GETTER
    public ChiTietHD[] getList() { return list; }
    public int getSoLuong() { return soLuong; }
    
    // NEW: Setter cho cac DS
    public void setDsKHTour(DSKHTour dsKHTour) {
        this.dsKHTour = dsKHTour;
        // Cap nhat cho tat ca ChiTietHD da load
        for (int i = 0; i < soLuong; i++) {
            if (list[i] != null) {
                list[i].setDsKHTour(dsKHTour);
            }
        }
    }
    
    public void setDsChiPhi(DSChiPhiKHTour dsChiPhi) {
        this.dsChiPhi = dsChiPhi;
        // Cap nhat cho tat ca ChiTietHD da load
        for (int i = 0; i < soLuong; i++) {
            if (list[i] != null) {
                list[i].setDsChiPhi(dsChiPhi);
            }
        }
    }

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
        chiTiet.setDsKHTour(dsKHTour);
        chiTiet.setDsChiPhi(dsChiPhi);
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
            list[i].xuatThongTin();
        }
    }

    // ===== THONG KE CHI TIET HOA DON =====
    
    public void thongKeChiTiet() {
        if (soLuong == 0) {
            System.out.println("Chua co chi tiet hoa don nao!");
            return;
        }

        // Use a BufferedWriter wrapping System.out so we can reuse the same write logic
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
        try {
            writeThongKeTo(out);
            out.flush();
        } catch (IOException e) {
            // If writing to console fails, print a short message to stderr
            System.err.println("Loi khi in thong ke: " + e.getMessage());
        }
    }

    // Internal helper: write the report to any BufferedWriter (does NOT close the writer)
    private void writeThongKeTo(BufferedWriter bw) throws IOException {
        // Step 1: compute aggregates
        int totalDetails = 0;
        int totalCustomers = 0;
        double totalRevenue = 0.0;

        // We will keep a parallel set of arrays for tour id, revenue and count
        String[] tourIds = new String[0];
        double[] tourRevenue = new double[0];
        int[] tourCounts = new int[0];

        for (int i = 0; i < soLuong; i++) {
            ChiTietHD ct = list[i];
            if (ct == null) continue;

            totalDetails++;
            double tongTien = ct.getTongTien();
            totalRevenue += tongTien;

            int numCustomers = (ct.getDsMaKhachHang() == null) ? 0 : ct.getDsMaKhachHang().length;
            totalCustomers += numCustomers;

            String tourId = ct.getMaKHTour();

            // find tourId in tourIds
            int found = -1;
            for (int k = 0; k < tourIds.length; k++) {
                if (tourIds[k].equals(tourId)) { found = k; break; }
            }

            if (found == -1) {
                // add new entry
                int newLen = tourIds.length + 1;
                tourIds = Arrays.copyOf(tourIds, newLen);
                tourRevenue = Arrays.copyOf(tourRevenue, newLen);
                tourCounts = Arrays.copyOf(tourCounts, newLen);
                tourIds[newLen - 1] = tourId;
                tourRevenue[newLen - 1] = tongTien;
                tourCounts[newLen - 1] = 1;
            } else {
                // update existing entry
                tourRevenue[found] += tongTien;
                tourCounts[found] += 1;
            }
        }

        double avgCustomersPerDetail = (totalDetails == 0) ? 0.0 : (double) totalCustomers / totalDetails;

        // Step 2: write the report (header, totals, per-tour, per-invoice)
        bw.write("\n================================================================================\n");
        bw.write("                     THONG KE CHI TIET HOA DON\n");
        bw.write("================================================================================\n");
        bw.write(String.format("Tong so chi tiet HD:    %d\n", totalDetails));
        bw.write(String.format("Tong so khach:          %d\n", totalCustomers));
        bw.write(String.format("Tong doanh thu:         %,.0f VND\n", totalRevenue));
        bw.write(String.format("Trung binh khach/HD:    %.2f\n", avgCustomersPerDetail));

        bw.write("\nDoanh thu theo KeHoachTour (maKHTour):\n");
        for (int k = 0; k < tourIds.length; k++) {
            bw.write(String.format("  - %s : %,.0f VND ( %d hoa don )\n", tourIds[k], tourRevenue[k], tourCounts[k]));
        }

        bw.write("\nDoanh thu theo hoa don (maHD):\n");
        for (int i = 0; i < soLuong; i++) {
            ChiTietHD ct = list[i];
            if (ct == null) continue;
            bw.write(String.format("  - %s : %,.0f VND\n", ct.getMaHD(), ct.getTongTien()));
        }

        bw.write("================================================================================\n");
    }

    // Luu thong ke vao file
    public void saveThongKeToFile(String filePath) throws IOException {
        File f = new File(filePath);
        File parent = f.getParentFile();
        if (parent != null && !parent.exists()) parent.mkdirs();
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        try {
            writeThongKeTo(bw);
        } finally {
            bw.close();
        }
    }
}