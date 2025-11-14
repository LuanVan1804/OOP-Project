package DU_LICH.Nguoi;

import java.io.*;
import java.util.Arrays;

public class DSHDV implements DU_LICH.Interfaces<HDV> {
    private HDV[] dsHDV;
    private int soLuongHDV;

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


    // Thêm HDV (thuần dữ liệu, không in, không hỏi lại mã)
    @Override
    public void them(HDV obj) {
        if (obj == null) return;
        if (!MaDuyNhat(obj.getMaHDV())) return;
        dsHDV = Arrays.copyOf(dsHDV, dsHDV.length + 1);
        dsHDV[dsHDV.length - 1] = obj;
    }

    // Sửa theo đối tượng đã được validate ở tầng quản lý
    public boolean sua(int maHDV, HDV updated) {
        if (updated == null) return false;
        for (int i = 0; i < dsHDV.length; i++) {
            if (dsHDV[i] != null && dsHDV[i].getMaHDV() == maHDV) {
                dsHDV[i].setTenHDV(updated.getTenHDV());
                dsHDV[i].setSoDienThoai(updated.getSoDienThoai());
                dsHDV[i].setCCCD(updated.getCCCD());
                dsHDV[i].setGioiTinh(updated.getGioiTinh());
                dsHDV[i].setKinhNghiem(updated.getKinhNghiem());
                return true;
            }
        }
        return false;
    }

    @Override
    public void sua(int maHDV) {
        // Không sử dụng trong flow mới (tương tác console sẽ nằm ở lớp quản lý)
    }

    // Xóa HDV theo mã (thuần dữ liệu, không in)
    @Override
    public void xoa(int maHDV) {
        int index = -1;
        for (int i = 0; i < dsHDV.length; i++) {
            if (dsHDV[i] != null && dsHDV[i].getMaHDV() == maHDV) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            for (int i = index; i < dsHDV.length - 1; i++) {
                dsHDV[i] = dsHDV[i + 1];
            }
            dsHDV[dsHDV.length - 1] = null;
            dsHDV = Arrays.copyOf(dsHDV, dsHDV.length - 1);
        }
    }

    // Tìm kiếm theo kinh nghiệm, trả về mảng kết quả
    public HDV[] timKiemTheoKinhNghiem(double kinhNghiem) {
        int count = 0;
        for (HDV hdv : dsHDV) {
            if (hdv != null && hdv.getKinhNghiem() == kinhNghiem) count++;
        }
        HDV[] kq = new HDV[count];
        int idx = 0;
        for (HDV hdv : dsHDV) {
            if (hdv != null && hdv.getKinhNghiem() == kinhNghiem) kq[idx++] = hdv;
        }
        return kq;
    }

    // Tìm kiếm theo tên, trả về mảng kết quả
    public HDV[] timKiemTheoTen(String tenHDV) {
        if (tenHDV == null) return new HDV[0];
        String keyword = tenHDV.toLowerCase();
        int count = 0;
        for (HDV hdv : dsHDV) {
            if (hdv != null && hdv.getTenHDV().toLowerCase().contains(keyword)) count++;
        }
        HDV[] kq = new HDV[count];
        int idx = 0;
        for (HDV hdv : dsHDV) {
            if (hdv != null && hdv.getTenHDV().toLowerCase().contains(keyword)) kq[idx++] = hdv;
        }
        return kq;
    }

    // Trả về đối tượng HDV theo mã (nếu có) — hỗ trợ resolve khi load các liên kết
    public HDV timTheoMa(int maHDV) {
        if (dsHDV == null) return null;
        for (HDV hdv : dsHDV) {
            if (hdv != null && hdv.getMaHDV() == maHDV) return hdv;
        }
        return null;
    }

    // Thống kê theo kinh nghiệm: trả về cấu trúc dữ liệu, không in
    public static class ThongKeKinhNghiem {
        public double[] kinhNghiemValues;
        public int[] dem;
        public int soLoai;
    }

    public ThongKeKinhNghiem thongKeTheoKinhNghiem() {
        ThongKeKinhNghiem tk = new ThongKeKinhNghiem();
        if (dsHDV.length == 0) {
            tk.kinhNghiemValues = new double[0];
            tk.dem = new int[0];
            tk.soLoai = 0;
            return tk;
        }

        double[] kinhNghiemValues = new double[dsHDV.length];
        int[] dem = new int[dsHDV.length];
        int soLoai = 0;

        for (int i = 0; i < dsHDV.length; i++) {
            HDV hdv = dsHDV[i];
            if (hdv == null) continue;
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
