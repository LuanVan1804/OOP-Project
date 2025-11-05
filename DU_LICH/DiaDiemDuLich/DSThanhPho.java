package DU_LICH.DiaDiemDuLich;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DSThanhPho {
    private ThanhPho[] list;

    public DSThanhPho() {
        this.list = new ThanhPho[0];
    }

    public ThanhPho[] getList() { return list; }
    public void setList(ThanhPho[] list) { this.list = list; }

    /**
     * Đọc file thành phố có dạng: countryCode,cityCode,cityName
     * Ví dụ:
     * 1,1,Ha Noi
     * 1,2,Ho Chi Minh
     */
    public static DSThanhPho loadFromFile(String path) throws IOException {
        DSThanhPho ds = new DSThanhPho();

        // đếm số dòng hợp lệ trước
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length < 3) continue;
                // kiểm tra phần mã quốc gia và mã thành phố có phải số không
                try {
                    Integer.parseInt(parts[0].trim());
                    Integer.parseInt(parts[1].trim());
                    count++;
                } catch (NumberFormatException ex) {
                    // skip invalid
                    continue;
                }
            }
        }

        if (count == 0) {
            ds.setList(new ThanhPho[0]);
            return ds;
        }

        // Tạo mảng thành phố với kích thước đã đếm
        ThanhPho[] tp = new ThanhPho[count];
        int idx = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length < 3) continue;
                try {
                    int countryCode = Integer.parseInt(parts[0].trim());
                    int cityCode = Integer.parseInt(parts[1].trim());
                    String cityName = parts[2].trim();
                    tp[idx++] = new ThanhPho(countryCode, cityCode, cityName);
                } catch (NumberFormatException ex) {
                    // skip invalid
                    continue;
                }
            }
        }
        // gán mảng vào danh sách
        ds.setList(tp);
        return ds;
    }
    // hàm lấy danh sách thành phố theo mã quốc gia
    public ThanhPho[] getCitiesByCountry(int countryCode) {
        if (list == null || list.length == 0) return new ThanhPho[0];
        // đếm số thành phố thuộc quốc gia đó
        int c = 0;
        for (int i = 0; i < list.length; i++) {
            if (list[i] != null && list[i].getMaQuocGia() == countryCode) c++;
        }
        if (c == 0) return new ThanhPho[0];
        ThanhPho[] res = new ThanhPho[c];
        int j = 0;
        for (int i = 0; i < list.length; i++) {
            if (list[i] != null && list[i].getMaQuocGia() == countryCode) {
                res[j++] = list[i];
            }
        }
        // trả về danh sách thành phố thuộc quốc gia đó
        return res;
    }
}
