package DU_LICH.Nguoi;

import java.io.*;
import java.util.Arrays;

public class DSKhachHang implements DU_LICH.Interfaces<KhachHang> {
    private KhachHang[] list;
    public DSKhachHang() {
        this.list = new KhachHang[0];
    }

    // -------------------- load file --------------------
    @Override
    public void loadFromFile(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        int count = 0;

        // Đếm số dòng để tạo mảng
        while ((line = br.readLine()) != null) {
            count++;
        }
        br.close();

        if (count == 0) {
            list = new KhachHang[0];
            return;
        }

        list = new KhachHang[count];
        br = new BufferedReader(new FileReader(path));
        int index = 0;
        while ((line = br.readLine()) != null) {
            // Sử dụng dấu , thay vì |
            String[] parts = line.split(",");
            if (parts.length == 5) {
                int maKH = Integer.parseInt(parts[0]);
                String tenKH = parts[1];
                String soDienThoai = parts[2];
                String cccd = parts[3];
                String gioiTinh = parts[4];
                list[index++] = new KhachHang(maKH, tenKH, soDienThoai, cccd, gioiTinh);
            }
        }
        br.close();

    }

    // ---------------------- check mã duy nhất --------------------
    @Override
    public boolean MaDuyNhat(int maKH) {
        for (KhachHang kh : list) {
            if (kh != null && kh.getMaKH() == maKH)
                return false;
        }
        return true;
    }

    // ---------------------- save to file --------------------
    @Override
    public void saveToFile(String path) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (KhachHang kh : list) {
                // Sử dụng dấu , thay vì |
                String line = String.format("%d,%s,%s,%s,%s",
                        kh.getMaKH(),
                        kh.getTenKH(),
                        kh.getSoDienThoai(),
                        kh.getCCCD(),
                        kh.getGioiTinh());
                bw.write(line);
                bw.newLine();
            }
        }
    }

    // ---------------------- thêm khách hàng (thuần dữ liệu) --------------------
    public void them(KhachHang khachHang) {
        if (khachHang == null) return;
        if (!MaDuyNhat(khachHang.getMaKH())) return;
        list = Arrays.copyOf(list, list.length + 1);
        list[list.length - 1] = khachHang;
    }

    // Sửa dựa trên đối tượng đã validate ở tầng quản lý
    public boolean sua(int maKH, KhachHang updated) {
        if (updated == null) return false;
        for (int i = 0; i < list.length; i++) {
            if (list[i] != null && list[i].getMaKH() == maKH) {
                list[i].setTenKH(updated.getTenKH());
                list[i].setSoDienThoai(updated.getSoDienThoai());
                list[i].setCCCD(updated.getCCCD());
                list[i].setGioiTinh(updated.getGioiTinh());
                return true;
            }
        }
        return false;
    }

    @Override
    public void sua(int maKH) {
        // Không dùng trong flow mới; dùng sua(int, KhachHang) từ lớp quản lý.
    }

    // ---------------------- tìm kiếm khách hàng theo mã (trả về đối tượng) --------------------
    public KhachHang timKiemKHTheoMa(int maKH) {
        for (KhachHang kh : list) {
            if (kh != null && kh.getMaKH() == maKH) return kh;
        }
        return null;
    }

    // ---------------------- tìm kiếm khách hàng theo tên (trả về mảng) --------------------
    public KhachHang[] timKiemTheoTen(String tenKH) {
        if (tenKH == null) return new KhachHang[0];
        String keyword = tenKH.toLowerCase();
        int count = 0;
        for (KhachHang kh : list) {
            if (kh != null && kh.getTenKH().toLowerCase().contains(keyword)) count++;
        }
        KhachHang[] kq = new KhachHang[count];
        int idx = 0;
        for (KhachHang kh : list) {
            if (kh != null && kh.getTenKH().toLowerCase().contains(keyword)) kq[idx++] = kh;
        }
        return kq;
    }

    // ---------------------- Thống kê theo giới tính (trả về mảng kết quả) --------------------
    public int[] thongKeTheoGioiTinh() {
        int countNam = 0, countNu = 0;
        for (KhachHang kh : list) {
            if (kh == null) continue;
            if (kh.getGioiTinh().equalsIgnoreCase("Nam"))
                countNam++;
            else if (kh.getGioiTinh().equalsIgnoreCase("Nu") || kh.getGioiTinh().equalsIgnoreCase("Nữ"))
                countNu++;
        }
        return new int[] { countNam, countNu };
    }

    // ---------------------- xóa khách hàng --------------------
    @Override
    public void xoa(int maKH) {
        int index = -1;
        for (int i = 0; i < list.length; i++) {
            if (list[i].getMaKH() == maKH) { index = i; break; }
        }
        if (index != -1) {
            for (int i = index; i < list.length - 1; i++) {
                list[i] = list[i + 1];
            }
            list[list.length - 1] = null;
            list = Arrays.copyOf(list, list.length - 1);
        }
    }
}

