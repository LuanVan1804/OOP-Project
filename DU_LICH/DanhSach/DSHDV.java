package DU_LICH.DanhSach;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

import DU_LICH.ClassDon.HDV;
import DU_LICH.DanhSach.sdhdv.ThongKe;

public class DSHDV implements DU_LICH.Interfaces<HDV> {
    private HDV[] list;

    public DSHDV() {
        this.list = new HDV[0];
    }

    public DSHDV(HDV[] list) {
        this.list = list;
    }

    // Getter for compatibility
    public HDV[] getList() {
        return list;
    }

    // -------------------- load file --------------------

    @Override
    public void loadFromFile(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        int count = 0;

        // dem so dong trong file
        while ((line = br.readLine()) != null) {
            count++;
        }
        br.close();

        if (count == 0) {
            this.list = new HDV[0];
            return;
        }
        list = new HDV[count];
        br = new BufferedReader(new FileReader(path));
        int index = 0;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 6) {
                int maHDV = Integer.parseInt(parts[0]);
                String tenHDV = parts[1];
                String soDienThoai = parts[2];
                String CCCD = parts[3];
                String gioiTinh = parts[4];
                double kinhNghiem = Double.parseDouble(parts[5]);
                list[index++] = new HDV(maHDV, tenHDV, soDienThoai, CCCD, gioiTinh, kinhNghiem);
            }
        }
        br.close();
    }

    @Override
    public boolean MaDuyNhat(int ma) {
        for (HDV hdv : list) {
            if (hdv.getMaHDV() == ma) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void saveToFile(String Path) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(Path));
        for (HDV hdv : list) {
            String line = String.format("%d,%s,%s,%s,%s,%.2f",
                    hdv.getMaHDV(),
                    hdv.getTenHDV(),
                    hdv.getSoDienThoai(),
                    hdv.getCCCD(),
                    hdv.getGioiTinh(),
                    hdv.getKinhNghiem());
            bw.write(line);
            bw.newLine();
        }
        bw.close();
    }

    // xuat danh sach HDV
    public void xuatDanhSach() {
        if (list == null || list.length == 0) {
            System.out.println("Danh sach HDV rong.");
            return;
        }
        System.out.println("===== DANH SACH HUONG DAN VIEN (" + list.length + ") =====");
        for (HDV hdv : list) {
            hdv.hienThiThongTinHDV();
        }
    }

    // Them HDV (thuan du lieu, khong in, khong hoi lai ma)
    @Override
    public void them(HDV obj) {
        if (obj == null)
            return;
        Scanner sc = new Scanner(System.in);
        while (!MaDuyNhat(obj.getMaHDV())) {
            System.out.println("Ma HDV da ton tai, vui long nhap ma khac!");
            System.out.print("Nhap lai ma HDV: ");
            int maHDV = Integer.parseInt(sc.nextLine());
            obj.setMaHDV(maHDV);
        }
        list = Arrays.copyOf(list, list.length + 1);
        list[list.length - 1] = obj;
    }

    public void chinhSuaHDV(int maHDV) {
        HDV hdv = timTheoMa(maHDV);
        if (hdv == null) {
            System.out.println("Khong tim thay HDV voi ma: " + maHDV);
            return;
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("=== THONG TIN HIEN TAI ===");
        hdv.hienThiThongTinHDV();
        System.out.println("=== NHAP THONG TIN MOI (ENTER DE BO QUA) ===");

        System.out.print("Ten HDV [" + hdv.getTenHDV() + "]: ");
        String s = sc.nextLine();
        if (!s.trim().isEmpty())
            hdv.setTenHDV(s);

        System.out.print("So dien thoai [" + hdv.getSoDienThoai() + "]: ");
        s = sc.nextLine();
        if (!s.trim().isEmpty())
            hdv.setSoDienThoai(s);

        System.out.print("CCCD [" + hdv.getCCCD() + "]: ");
        s = sc.nextLine();
        if (!s.trim().isEmpty())
            hdv.setCCCD(s);

        System.out.print("Gioi tinh [" + hdv.getGioiTinh() + "]: ");
        s = sc.nextLine();
        if (!s.trim().isEmpty())
            hdv.setGioiTinh(s);

        System.out.print("Kinh nghiem [" + hdv.getKinhNghiem() + "]: ");
        s = sc.nextLine();
        if (!s.trim().isEmpty()) {
            try {
                hdv.setKinhNghiem(Double.parseDouble(s));
            } catch (NumberFormatException e) {
                System.out.println("Gia tri khong hop le, giu nguyen kinh nghiem cu!");
            }
        }

        System.out.println("=== Cap nhat thanh cong! ===");
    }

    @Override
    public void sua(int maHDV) {
        // Khong su dung trong flow moi (tuong tac console se nam o lop quan ly)
    }

    // Xoa HDV theo ma (thuan du lieu, khong in)
    @Override
    public void xoa(int maHDV) {
        int index = -1;
        for (int i = 0; i < list.length; i++) {
            if (list[i] != null && list[i].getMaHDV() == maHDV) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            for (int i = index; i < list.length - 1; i++) {
                list[i] = list[i + 1];
            }
            list[list.length - 1] = null;
            list = Arrays.copyOf(list, list.length - 1);
        }
    }

    // Tim kiem theo ten, tra ve mang ket qua
    public HDV[] timKiemTheoTen(String tenHDV) {
        if (tenHDV == null)
            return new HDV[0];
        String keyword = tenHDV.toLowerCase();
        int count = 0;
        for (HDV hdv : list) {
            if (hdv != null && hdv.getTenHDV().toLowerCase().contains(keyword))
                count++;
        }
        HDV[] kq = new HDV[count];
        int idx = 0;
        for (HDV hdv : list) {
            if (hdv != null && hdv.getTenHDV().toLowerCase().contains(keyword))
                kq[idx++] = hdv;
        }
        return kq;
    }

    // Tra ve doi tuong HDV theo ma (neu co) — ho tro resolve khi load cac lien ket
    public HDV timTheoMa(int maHDV) {
        if (list == null)
            return null;
        for (HDV hdv : list) {
            if (hdv != null && hdv.getMaHDV() == maHDV)
                return hdv;
        }
        return null;
    }

    // Thong ke theo kinh nghiem: tra ve cau truc du lieu, khong in
    public static class ThongKeKinhNghiem {
        public double[] kinhNghiemValues;
        public int[] dem;
        public int soLoai;
    }

    public ThongKeKinhNghiem thongKeTheoKinhNghiem() {
        ThongKeKinhNghiem tk = new ThongKeKinhNghiem();
        if (list == null || list.length == 0) {
            tk.kinhNghiemValues = new double[0];
            tk.dem = new int[0];
            tk.soLoai = 0;
            return tk;
        }

        double[] kinhNghiemValues = new double[list.length];
        int[] dem = new int[list.length];
        int soLoai = 0;

        for (int i = 0; i < list.length; i++) {
            HDV hdv = list[i];
            if (hdv == null)
                continue;
            double kn = hdv.getKinhNghiem();
            boolean daCo = false;
            for (int j = 0; j < soLoai; j++) {
                if (Double.compare(kinhNghiemValues[j], kn) == 0) {
                    dem[j]++;
                    daCo = true;
                    break;
                }
            }
            if (!daCo) {
                kinhNghiemValues[soLoai] = kn;
                dem[soLoai] = 1;
                soLoai++;
            }
        }

        tk.kinhNghiemValues = Arrays.copyOf(kinhNghiemValues, soLoai);
        tk.dem = Arrays.copyOf(dem, soLoai);
        tk.soLoai = soLoai;
        return tk;
    }
}
