package DU_LICH.Nguoi;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class DSHDV implements DU_LICH.QuanLy<HDV> {
    private HDV[] dsHDV;
    private int soLuongHDV;
    private static final String File_PATH = "HDV.txt";

    public DSHDV() {
        this.dsHDV = new HDV[0];
        this.soLuongHDV = 0;
    }

    @Override
    public void loadFromFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        int count = 0;

        // đếm số dòng trong file
        while ((line = br.readLine()) != null) {
            count++;
        }
        br.close();

        if (count == 0) {
            this.dsHDV = new HDV[0];
            return;
        }
        dsHDV = new HDV[count];
        br = new BufferedReader(new FileReader(fileName));
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
                dsHDV[index++] = new HDV(maHDV, tenHDV, soDienThoai, CCCD, gioiTinh, kinhNghiem);
            }
        }
        br.close();
    }

    @Override
    public boolean MaDuyNhat(int ma) {
        for (HDV hdv : dsHDV) {
            if (hdv.getMaHDV() == ma) {
                return false;
            }
        }
        return true;
    }

  
    @Override
    public void saveToFile(String fileName) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        for (HDV hdv : dsHDV) {
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


    public void hienThiDanhSachKH() {
        for (HDV hdv : dsHDV) {
            System.out.println("----------------------------");
            System.out.println(hdv);
        }
    }

    @Override
    public void them(HDV obj) {
        Scanner sc = new Scanner(System.in);
        while (!MaDuyNhat(obj.getMaHDV())) {
            System.out.println("Ma HDV da ton tai. Khong the them.");
            int maMoi = Integer.parseInt(sc.nextLine());
            obj.setMaHDV(maMoi);
        }
        dsHDV = Arrays.copyOf(dsHDV, dsHDV.length + 1);
        dsHDV[dsHDV.length - 1] = obj;

        try {
            saveToFile(File_PATH);
        } catch (IOException e) {
            System.out.println("Loi khi luu file: " + e.getMessage());
        }
    }

    @Override
    public void sua(int maHDV) {
        Scanner sc = new Scanner(System.in);
        boolean found = false;

        for (int i = 0; i < dsHDV.length; i++) {
            if (dsHDV[i].getMaHDV() == maHDV) {
                found = true;
                System.out.println("Thong tin hien tai cua HDV:");
                System.out.println(dsHDV[i].toString());
                System.out.println("\n--- Nhap thong tin moi (bo trong neu khong muon doi) ---");

                System.out.print("Nhap ma HDV moi (hien tai: " + dsHDV[i].getMaHDV() + "): ");
                String inputMa = sc.nextLine();
                if (!inputMa.isEmpty()) {
                    int maMoi = Integer.parseInt(inputMa);
                    if (maMoi != dsHDV[i].getMaHDV() && !MaDuyNhat(maMoi)) {
                        System.out.println("Ma HDV da ton tai! Giu nguyen ma cu.");
                    } else {
                        dsHDV[i].setMaHDV(maMoi);
                    }
                }

                System.out.print("Nhap ten moi (hien tai: " + dsHDV[i].getTenHDV() + "): ");
                String tenMoi = sc.nextLine();
                if (!tenMoi.isEmpty()) dsHDV[i].setTenHDV(tenMoi);

                System.out.print("Nhap so dien thoai moi (hien tai: " + dsHDV[i].getSoDienThoai() + "): ");
                String sdtMoi = sc.nextLine();
                if (!sdtMoi.isEmpty()) dsHDV[i].setSoDienThoai(sdtMoi);

                System.out.print("Nhap CCCD moi (hien tai: " + dsHDV[i].getCCCD() + "): ");
                String cccdMoi = sc.nextLine();
                if (!cccdMoi.isEmpty()) dsHDV[i].setCCCD(cccdMoi);

                System.out.print("Nhap gioi tinh moi (hien tai: " + dsHDV[i].getGioiTinh() + "): ");
                String gtMoi = sc.nextLine();
                if (!gtMoi.isEmpty()) dsHDV[i].setGioiTinh(gtMoi);

                System.out.print("Nhap so nam kinh nghiem moi (hien tai: " + dsHDV[i].getKinhNghiem() + "): ");
                String knMoi = sc.nextLine();
                if (!knMoi.isEmpty()) {
                    double kn = Double.parseDouble(knMoi);
                    dsHDV[i].setKinhNghiem(kn);
                }

                System.out.println("Cap nhat thong tin HDV thanh cong!");
                break;
            }
        }

        if (found) {
            try {
                saveToFile(File_PATH);
                System.out.println("Da cap nhat thong tin HDV va luu file thanh cong!");
            } catch (IOException e) {
                System.out.println("Loi khi luu file: " + e.getMessage());
            }
        } else {
            System.out.println("Khong tim thay HDV voi ma: " + maHDV);
        }
    }


    @Override
    public void xoa(int maHDV) {
        int index = -1;
        for (int i = 0; i < dsHDV.length; i++) {
            if (dsHDV[i].getMaHDV() == maHDV) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            for (int i = index; i < dsHDV.length-1; i++) {
                dsHDV[i] = dsHDV[i + 1];
            }
            dsHDV[dsHDV.length - 1] = null; // optional: clear the last element
            dsHDV = Arrays.copyOf(dsHDV, dsHDV.length - 1);
            try {
                saveToFile(File_PATH);
            } catch (IOException e) {
                System.out.println("Loi khi luu file: " + e.getMessage());
            }
        } else {
            System.out.println("Khong tim thay HDV voi ma: " + maHDV);
        }
    }

    public void timKiemTheoKinhNghiem(double kinhNghiem) {
        System.out.println("Danh sach HDV co kinh nghiem " + kinhNghiem + " nam:");
        boolean found = false;
        for (HDV hdv : dsHDV) {
            if (hdv.getKinhNghiem() == kinhNghiem) {
                System.out.println(hdv);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Khong tim thay HDV voi kinh nghiem: " + kinhNghiem);
        }
    }

    public void timKiemTheoTen(String tenHDV) {
        boolean found = false;
        String keyword = tenHDV.toLowerCase();
        for (HDV hdv : dsHDV) {
            if (hdv.getTenHDV().toLowerCase().contains(keyword)) {
                if (!found) {
                    System.out.println("Danh sach HDV tim thay:");
                    found = true;
                }
                System.out.println(hdv);
            }
        }
        if (!found) {
            System.out.println("Khong tim thay HDV voi ten: " + tenHDV);
        }
    }

    public void thongKeTheoKinhNghiem() {
        if (dsHDV.length == 0) {
            System.out.println(" Danh sach HDV rong. Khong the thong ke.");
            return;
        }

        // tạo mảng chứa giá trị kinh nghiệm và mảng đếm
        double[] kinhNghiemValues = new double[dsHDV.length];
        int[] dem = new int[dsHDV.length];
        int soLoai = 0;

        // duyệt qua danh sách HDV để thống kê
        for (int i = 0; i < dsHDV.length; i++) {
            double kn = dsHDV[i].getKinhNghiem();
            boolean daCo = false;

            // ktra xem kinh nghiem nay da xuat hien trong mang chưa
            for (int j = 0; j < soLoai; j++) {
                if (kinhNghiemValues[j] == kn) {
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

        System.out.println("------------------Thong ke HDV theo kinh nghiem-------------------------");
        for (int i = 0; i < soLoai; i++) {
            System.out.printf("Kinh nghiem: %.1f - So luong HDV: %d%n", kinhNghiemValues[i], dem[i]);
        }
    }
    // hàm menu tương tác cho HDV
    public void menuHDV(String providedPath) {
        Scanner sc = new Scanner(System.in);

        int chon;
        do {
            System.out.println("\n========== MENU QUAN LY HUONG DAN VIEN ==========");
            System.out.println("1. Hien thi danh sach HDV");
            System.out.println("2. Them HDV moi");
            System.out.println("3. Sua thong tin HDV");
            System.out.println("4. Xoa HDV");
            System.out.println("5. Tim kiem theo ten");
            System.out.println("6. Tim kiem theo kinh nghiem");
            System.out.println("7. Thong ke theo kinh nghiem");
            System.out.println("0. Thoat (luu thay doi)");
            System.out.print("-- Chon chuc nang: ");
            chon = Integer.parseInt(sc.nextLine());

            switch (chon) {
                case 1:
                    this.hienThiDanhSachKH();
                    break;

                case 2:
                    System.out.println("----------------Nhap thong tin HDV moi-----------------");
                    HDV hdvMoi = new HDV();
                    hdvMoi.nhapThongTinHDV();
                    this.them(hdvMoi);
                    System.out.println("Da them HDV moi!");
                    break;
                case 3:
                    System.out.print("Nhap ma HDV can sua: ");
                    int maSua = Integer.parseInt(sc.nextLine());
                    this.sua(maSua);
                    break;
                case 4:
                    System.out.print("Nhap ma HDV can xoa: ");
                    int maXoa = Integer.parseInt(sc.nextLine());
                    this.xoa(maXoa);
                    break;
                case 5:
                    System.out.print("Nhap ten HDV can tim: ");
                    String tenTK = sc.nextLine();
                    this.timKiemTheoTen(tenTK);
                    break;
                case 6:
                    System.out.print("Nhap so nam kinh nghiem can tim: ");
                    double knTK = Double.parseDouble(sc.nextLine());
                    this.timKiemTheoKinhNghiem(knTK);
                    break;
                case 7:
                    this.thongKeTheoKinhNghiem();
                    break;
                case 0:
                    try {
                        this.saveToFile(providedPath);
                    } catch (IOException e) {
                        System.out.println("Loi khi luu file HDV: " + e.getMessage());
                    }
                    System.out.println("Thoat");
                    break;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        } while (chon != 0);
    }
}
