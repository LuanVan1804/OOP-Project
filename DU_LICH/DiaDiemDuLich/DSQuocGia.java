package DU_LICH.DiaDiemDuLich;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DSQuocGia {
    private QuocGia[] list;
    private int currentCountryIndex;

    public DSQuocGia() {
        this.list = new QuocGia[0];
        this.currentCountryIndex = 0;
    }

    public QuocGia[] getList() { return list; }
    public void setList(QuocGia[] list) { this.list = list; }

    public int getCurrentCountryIndex() { return currentCountryIndex; }
    public void setCurrentCountryIndex(int idx) { this.currentCountryIndex = idx; }

    public static DSQuocGia loadFromFile(String path) throws IOException {
        // Tạo đối tượng DSQuocGia để lưu danh sách quốc gia
        DSQuocGia dsqg = new DSQuocGia();
        // Mẫu file quocGia.txt: mỗi dòng là "maQuocGia,tenQuocGia"
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",", 2);
                if (parts.length < 2) continue;
                String ma = parts[0].trim();
                String name = parts[1].trim();
                if (!ma.isEmpty() && !name.isEmpty()) count++;
            }
        }

        // Tạo mảng quốc gia với kích thước đã đếm
        QuocGia[] countries = new QuocGia[count];
        if (count > 0) {
            int idx = 0;
            try (BufferedReader br = new BufferedReader(new FileReader(path))) {
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) continue;
                    String[] parts = line.split(",", 2);
                    if (parts.length < 2) continue;
                    String ma = parts[0].trim();
                    String name = parts[1].trim();
                    QuocGia q = new QuocGia(name, ma);
                    q.setListTPhos(new ThanhPho[0]);
                    countries[idx++] = q;
                }
            }
        }
        // Gán mảng quốc gia vào DSQuocGia và trả về
        dsqg.setList(countries);
        dsqg.setCurrentCountryIndex(0);
        return dsqg;
    }
}