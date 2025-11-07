package DU_LICH.Nguoi;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class DSKhachHang implements DU_LICH.Interfaces<KhachHang> {
    private KhachHang[] list;
    private static final String FILE_PATH = "KhachHang.txt";

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

    // ---------------------- hiển thị danh sách khách hàng --------------------
    public void hienThiDanhSachKH() {
        if (list.length == 0) {
            System.out.println("Danh sach khach hang rong!");
            return;
        }

        System.out.println("Danh sach khach hang:");
        System.out.println("---------------------------");
        for (int i = 0; i < list.length; i++) {
            System.out.println("Khach hang " + (i + 1) + ":");
            System.out.println(list[i].toString());
            System.out.println("---------------------------");
        }
    }

    // ---------------------- thêm khách hàng --------------------
    public void them(KhachHang khachHang) {
        Scanner sc = new Scanner(System.in);
        while (!MaDuyNhat(khachHang.getMaKH())) {
            System.out.println("Ma khach hang da ton tai! Vui long nhap lai ma khach hang moi:");
            int maKHMoi = Integer.parseInt(sc.nextLine());
            khachHang.setMaKH(maKHMoi);
        }
        list = Arrays.copyOf(list, list.length + 1);
        list[list.length - 1] = khachHang;

        try {
            saveToFile(FILE_PATH);
            System.out.println("Da luu thong tin khach hang moi vao file thanh cong!");
        } catch (IOException e) {
            System.out.println("Loi khi luu file: " + e.getMessage());
        }
    }

    @Override
public void sua(int maKH) {
    Scanner sc = new Scanner(System.in);
    boolean found = false;

    for (int i = 0; i < list.length; i++) {
        if (list[i].getMaKH() == maKH) {
            found = true;
            System.out.println("Thong tin hien tai cua khach hang:");
            System.out.println(list[i].toString());

            System.out.println("\n--- Nhap thong tin moi (bo trong neu khong muon doi) ---");

            System.out.print("Nhap ma khach hang moi (hien tai: " + list[i].getMaKH() + "): ");
            String inputMa = sc.nextLine();
            if (!inputMa.isEmpty()) {
                int maMoi = Integer.parseInt(inputMa);
                if (maMoi != list[i].getMaKH() && !MaDuyNhat(maMoi)) {
                    System.out.println("Ma khach hang da ton tai! Giua nguyen ma cu.");
                } else {
                    list[i].setMaKH(maMoi);
                }
            }

            System.out.print("Nhap ten moi (hien tai: " + list[i].getTenKH() + "): ");
            String tenMoi = sc.nextLine();
            if (!tenMoi.isEmpty()) list[i].setTenKH(tenMoi);

            System.out.print("Nhap so dien thoai moi (hien tai: " + list[i].getSoDienThoai() + "): ");
            String sdtMoi = sc.nextLine();
            if (!sdtMoi.isEmpty()) list[i].setSoDienThoai(sdtMoi);

            System.out.print("Nhap CCCD moi (hien tai: " + list[i].getCCCD() + "): ");
            String cccdMoi = sc.nextLine();
            if (!cccdMoi.isEmpty()) list[i].setCCCD(cccdMoi);

            System.out.print("Nhap gioi tinh moi (hien tai: " + list[i].getGioiTinh() + "): ");
            String gtMoi = sc.nextLine();
            if (!gtMoi.isEmpty()) list[i].setGioiTinh(gtMoi);

            System.out.println("Cap nhat thong tin thanh cong!");
            break;
        }
    }

    if (found) {
        try {
            saveToFile(FILE_PATH);
            System.out.println("Da cap nhat thong tin khach hang va luu file thanh cong!");
        } catch (IOException e) {
            System.out.println("Loi khi luu file: " + e.getMessage());
        }
    } else {
        System.out.println("Khong tim thay khach hang voi ma: " + maKH);
    }
}

    // ---------------------- tìm kiếm khách hàng theo mã --------------------
    public void timKiemKHTheoMa(int maKH) {
        for (KhachHang kh : list) {
            if (kh.getMaKH() == maKH) {
                System.out.println("Khach hang tim thay:");
                System.out.println(kh.toString());
                return;
            }
        }
        System.out.println("Khong tim thay khach hang co ma: " + maKH);
    }

    // ---------------------- tìm kiếm khách hàng theo tên --------------------
    public void timKiemTheoTen(String tenKH) {
        boolean found = false;
        String keyword = tenKH.toLowerCase();
        for (KhachHang kh : list) {
            if (kh.getTenKH().toLowerCase().contains(keyword)) {
                if (!found) {
                    System.out.println("Khach hang tim thay:");
                    found = true;
                }
                System.out.println(kh.toString());
            }
        }
        if (!found) {
            System.out.println("Khong tim thay khach hang co ten: " + tenKH);
        }
    }

    // ---------------------- Thống kê theo giới tính --------------------
    public int[] thongKeTheoGioiTinh() {
        int countNam = 0, countNu = 0;
        for (KhachHang kh : list) {
            if (kh.getGioiTinh().equalsIgnoreCase("Nam"))
                countNam++;
            else if (kh.getGioiTinh().equalsIgnoreCase("Nu") || kh.getGioiTinh().equalsIgnoreCase("Nữ"))
                countNu++;
        }
        System.out.println("So luong khach hang Nam: " + countNam);
        System.out.println("So luong khach hang Nu: " + countNu);
        return new int[] { countNam, countNu };
    }

    // ---------------------- xóa khách hàng --------------------
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
            list[list.length - 1] = null;
            list = Arrays.copyOf(list, list.length - 1);

            try {
                saveToFile(FILE_PATH);
                System.out.println("Da xoa khach hang va luu file thanh cong!");
            } catch (IOException e) {
                System.out.println("Loi khi luu file: " + e.getMessage());
            }
        } else {
            System.out.println("Khong tim thay khach hang de xoa!");
        }
    }

    //instance menu không tham số
    public void menu() { menu((String) null); }
    // ---------------------- menu khách hàng --------------------
    public void menu(String providedPath) {
        Scanner sc = new Scanner(System.in);
        String savePath = (providedPath != null && !providedPath.trim().isEmpty()) ? providedPath : FILE_PATH;
        int choice;

        do {
            System.out.println("\n========== MENU ==========");
            System.out.println("1. Hien thi danh sach khach hang");
            System.out.println("2. Them khach hang moi");
            System.out.println("3. Sua thong tin khach hang");
            System.out.println("4. Xoa khach hang");
            System.out.println("5. Tim khach hang theo ma");
            System.out.println("6. Tim khach hang theo ten");
            System.out.println("7. Thong ke theo gioi tinh");
            System.out.println("0. Thoat (luu thay doi)");
            System.out.print("Chon chuc nang: ");
            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    this.hienThiDanhSachKH();
                    break;
                case 2: {
                    KhachHang kh = new KhachHang();
                    kh.nhapThongTinKH();
                    this.them(kh);
                }
                    break;
                case 3: {
                    System.out.print("Nhap ma khach hang can sua: ");
                    int maKH = Integer.parseInt(sc.nextLine());
                    this.sua(maKH);
                }
                    break;
                case 4: {
                    System.out.print("Nhap ma khach hang can xoa: ");
                    int maXoa = Integer.parseInt(sc.nextLine());
                    this.xoa(maXoa);
                }
                    break;
                case 5: {
                    System.out.print("Nhap ma khach hang can tim: ");
                    int maKH = Integer.parseInt(sc.nextLine());
                    this.timKiemKHTheoMa(maKH);
                }
                    break;
                case 6: {
                    System.out.print("Nhap ten khach hang can tim: ");
                    String tenKH = sc.nextLine();
                    this.timKiemTheoTen(tenKH);
                }
                    break;
                case 7: {
                    int[] thongKe = this.thongKeTheoGioiTinh();
                    System.out.println("Nam: " + thongKe[0] + ", Nu: " + thongKe[1]);
                }
                    break;
                case 0: {
                    // on exit, save
                    try {
                        this.saveToFile(savePath);
                    } catch (IOException e) {
                        System.out.println("Loi khi luu file: " + e.getMessage());
                    }
                    System.out.println("Thoat.");
                }
                    break;
                default: {
                    System.out.println("Lua chon khong hop le!");
                }
                    break;
            }
        } while (choice != 0);
    }
}

