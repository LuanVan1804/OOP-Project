package DU_LICH.DanhSach;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

import DU_LICH.ClassDon.KhachHang;

public class DSKhachHang implements DU_LICH.Interfaces<KhachHang> {
    private KhachHang[] list;

    // Getter for compatibility
    public KhachHang[] getList() {
        return list;
    }

    public DSKhachHang() {
        this.list = new KhachHang[0];
    }

    public DSKhachHang(KhachHang[] list) {
        this.list = list == null ? new KhachHang[0] : Arrays.copyOf(list, list.length);
    }

    public DSKhachHang(DSKhachHang other) {
        if (other == null || other.list == null) this.list = new KhachHang[0];
        else this.list = Arrays.copyOf(other.list, other.list.length);
    }

    // -------------------- load file --------------------
    @Override
    public void loadFromFile(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        int count = 0;

        // Dem so dong de tao mang
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
            // Su dung dau , thay vi |
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

    // ---------------------- check ma duy nhat --------------------
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
                // Su dung dau , thay vi |
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

    // hien thi danh sach khach hang
    public void hienThiDanhSachKH() {
        if (list == null || list.length == 0) {
            System.out.println("Danh sach khach hang rong!");
            return;
        }
        System.out.println("Danh sach khach hang:");
        for (KhachHang kh : list) {
            kh.hienThiThongTinKH();
            System.out.println("-----------------------");
        }
    }
    // -

    // ---------------------- them khach hang voi Scanner (cho phep nhap lai ma)
    // --------------------
    public void them(KhachHang kh) {
        Scanner sc = new Scanner(System.in);
        if (kh == null)
            return;

        // Kiem tra ma duy nhat
        while (!MaDuyNhat(kh.getMaKH())) {
            System.out.print("Ma khach hang da ton tai, vui long nhap ma khac: ");
            int maMoi = Integer.parseInt(sc.nextLine());
            kh.setMaKH(maMoi);
        }

        // Them vao mang
        list = Arrays.copyOf(list, list.length + 1);
        list[list.length - 1] = kh;
    }
    @Override
    public void sua(int maKH) {
        KhachHang kh = timKiemKHTheoMa(maKH); // giả sử có phương thức tìm theo mã
        if (kh == null) {
            System.out.println("Khong tim thay khach hang voi ma: " + maKH);
            return;
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("=== THONG TIN HIEN TAI ===");
        kh.hienThiThongTinKH();
        System.out.println("=== NHAP THONG TIN MOI (ENTER DE BO QUA) ===");

        System.out.print("Ten KH [" + kh.getTenKH() + "]: ");
        String s = sc.nextLine();
        if (!s.trim().isEmpty())
            kh.setTenKH(s);

        System.out.print("So dien thoai [" + kh.getSoDienThoai() + "]: ");
        s = sc.nextLine();
        if (!s.trim().isEmpty())
            kh.setSoDienThoai(s);

        System.out.print("CCCD [" + kh.getCCCD() + "]: ");
        s = sc.nextLine();
        if (!s.trim().isEmpty())
            kh.setCCCD(s);

        System.out.print("Gioi tinh [" + kh.getGioiTinh() + "]: ");
        s = sc.nextLine();
        if (!s.trim().isEmpty())
            kh.setGioiTinh(s);

        System.out.println("=== Cap nhat khach hang thanh cong! ===");
    }


    public KhachHang timKiemKHTheoMa(int maKH) {
        for (KhachHang kh : list) {
            if (kh.getMaKH() == maKH)
                return kh;
        }
        return null;
    }

    // ---------------------- tim kiem khach hang theo ten (tra ve mang)
    // --------------------
    public KhachHang[] timKiemTheoTen(String tenKH) {
        if (tenKH == null)
            return new KhachHang[0];
        String keyword = tenKH.toLowerCase();
        int count = 0;
        for (KhachHang kh : list) {
            if (kh != null && kh.getTenKH().toLowerCase().contains(keyword))
                count++;
        }
        KhachHang[] kq = new KhachHang[count];
        int idx = 0;
        for (KhachHang kh : list) {
            if (kh != null && kh.getTenKH().toLowerCase().contains(keyword))
                kq[idx++] = kh;
        }
        return kq;
    }

    // ---------------------- Thong ke theo gioi tinh (tra ve mang ket qua)
    // --------------------
    public int[] thongKeTheoGioiTinh() {
        int countNam = 0, countNu = 0;
        for (KhachHang kh : list) {
            if (kh == null)
                continue;
            if (kh.getGioiTinh().equalsIgnoreCase("Nam"))
                countNam++;
            else if (kh.getGioiTinh().equalsIgnoreCase("Nu") || kh.getGioiTinh().equalsIgnoreCase("Nu"))
                countNu++;
        }
        return new int[] { countNam, countNu };
    }

    // ---------------------- xoa khach hang --------------------
    @Override
    public void xoa(int maKH) {
        int index = -1;
        for (int i = 0; i < list.length; i++) {
            if (list[i].getMaKH() == maKH) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            for (int i = index; i < list.length - 1; i++) {
                list[i] = list[i + 1];
            }
        }
        list[list.length - 1] = null;
        list = Arrays.copyOf(list, list.length - 1);
    }
}
