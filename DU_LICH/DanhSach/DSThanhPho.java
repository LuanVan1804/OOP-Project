package DU_LICH.DanhSach;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import DU_LICH.ClassDon.QuocGia;
import DU_LICH.ClassDon.ThanhPho;

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
    public DSThanhPho loadFromFile(String path) throws IOException {
        DSThanhPho ds = new DSThanhPho();

        // đếm số dòng hợp lệ trước
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",", 3);
                if (parts.length < 3) continue;
                String country = parts[0].trim();
                String city = parts[1].trim();
                String name = parts[2].trim();
                if (country.isEmpty() || city.isEmpty() || name.isEmpty()) continue;
                count++;
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
                String[] parts = line.split(",", 3);
                if (parts.length < 3) continue;
                String countryCode = parts[0].trim();
                String cityCode = parts[1].trim();
                String cityName = parts[2].trim();
                if (countryCode.isEmpty() || cityCode.isEmpty() || cityName.isEmpty()) continue;
                tp[idx++] = new ThanhPho(countryCode, cityCode, cityName);
            }
        }
        // gán mảng vào danh sách
        ds.setList(tp);
        return ds;
    }
    // hàm lấy danh sách thành phố theo mã quốc gia
    public ThanhPho[] getCitiesByCountry(String countryCode) {
        if (list == null || list.length == 0) return new ThanhPho[0];
        // đếm số thành phố thuộc quốc gia đó
        int c = 0;
        for (int i = 0; i < list.length; i++) {
            if (list[i] != null && countryCode.equals(list[i].getMaQuocGia())) c++;
        }
        if (c == 0) return new ThanhPho[0];
        ThanhPho[] res = new ThanhPho[c];
        int j = 0;
        for (int i = 0; i < list.length; i++) {
            if (list[i] != null && countryCode.equals(list[i].getMaQuocGia())) {
                res[j++] = list[i];
            }
        }
        // trả về danh sách thành phố thuộc quốc gia đó
        return res;
    }
    // HIỂN THỊ DANH SÁCH THÀNH PHỐ THEO QUỐC GIA
    public void hienThiDanhSachTheoQuocGia(String maQG) {
        ThanhPho[] cities = getCitiesByCountry(maQG);
        if (cities.length == 0) {
            System.out.println("Khong co thanh pho nao cho ma quoc gia: " + maQG);
            return;
        }
        System.out.println("Danh sach thanh pho (Quoc gia: " + maQG + "):");
        for (ThanhPho tp : cities) {
            System.out.println(" - " + tp.getTenTPho() + " (Ma: " + tp.getMaTPho() + ")");
        }
    }

    // KIỂM TRA MÃ THÀNH PHỐ HỢP LỆ TRONG QUỐC GIA
    public boolean isValidCityCode(String maQG, String maTP) {
        ThanhPho[] cities = getCitiesByCountry(maQG);
        for (ThanhPho tp : cities) {
            if (tp.getMaTPho().equals(maTP)) return true;
        }
        return false;
    }

    // HIỂN THỊ DANH SÁCH THÀNH PHỐ NỘI ĐỊA
    public void hienThiDanhSachNoiDia(DSQuocGia dsqg) {
        QuocGia domestic = dsqg.getDomesticCountry();
        if (domestic == null) {
            System.out.println("Khong co quoc gia noi dia!");
            return;
        }
        hienThiDanhSachTheoQuocGia(domestic.getMaQuocGia());
    }

    // KIỂM TRA MÃ THÀNH PHỐ NỘI ĐỊA
    public boolean isValidDomesticCityCode(DSQuocGia dsqg, String maTP) {
        QuocGia domestic = dsqg.getDomesticCountry();
        if (domestic == null) return false;
        return isValidCityCode(domestic.getMaQuocGia(), maTP);
    }
}
