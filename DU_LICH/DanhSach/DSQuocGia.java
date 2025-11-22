package DU_LICH.DanhSach;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import DU_LICH.ClassDon.QuocGia;
import DU_LICH.ClassDon.ThanhPho;
import java.util.Arrays;

public class DSQuocGia {
    private QuocGia[] list;
    private int currentCountryIndex;

    public DSQuocGia() {
        this.list = new QuocGia[0];
        this.currentCountryIndex = 0;
    }

    public DSQuocGia(QuocGia[] list) {
        this.list = list == null ? new QuocGia[0] : Arrays.copyOf(list, list.length);
        this.currentCountryIndex = 0;
    }

    public DSQuocGia(DSQuocGia other) {
        if (other == null || other.list == null) {
            this.list = new QuocGia[0];
            this.currentCountryIndex = 0;
        } else {
            this.list = Arrays.copyOf(other.list, other.list.length);
            this.currentCountryIndex = other.currentCountryIndex;
        }
    }

    public QuocGia[] getList() { return list; }
    public void setList(QuocGia[] list) { this.list = list; }

    public int getCurrentCountryIndex() { return currentCountryIndex; }
    public void setCurrentCountryIndex(int idx) { this.currentCountryIndex = idx; }

    public DSQuocGia loadFromFile(String path) throws IOException {
        // Tao doi tuong DSQuocGia de luu danh sach quoc gia
        DSQuocGia dsqg = new DSQuocGia();
        // Mau file quocGia.txt: moi dong la "maQuocGia,tenQuocGia"
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

        // Tao mang quoc gia voi kich thuoc da dem
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
        // Gan mang quoc gia vao DSQuocGia va tra ve
        dsqg.setList(countries);
        dsqg.setCurrentCountryIndex(0);
        return dsqg;
    }

    // LAY QUOC GIA NOI DIA
    public QuocGia getDomesticCountry() {
        if (list.length == 0) return null;
        return list[currentCountryIndex];
    }

    // LAY DANH SACH QUOC GIA NUOC NGOAI (loai tru noi dia)
    public QuocGia[] getForeignCountries() {
        if (list.length <= 1) return new QuocGia[0];
        QuocGia[] res = new QuocGia[list.length - 1];
        int idx = 0;
        for (int i = 0; i < list.length; i++) {
            if (i != currentCountryIndex) {
                res[idx++] = list[i];
            }
        }
        return res;
    }

    // KIEM TRA MA QUOC GIA CO HOP LE VA KHONG PHAI NOI DIA
    public boolean isValidForeignCountryCode(String maQG) {
        if (maQG == null) return false;
        for (QuocGia qg : list) {
            if (qg.getMaQuocGia().equals(maQG)) {
                return !qg.getMaQuocGia().equals(getDomesticCountry().getMaQuocGia());
            }
        }
        return false;
    }

    // LAY QUOC GIA THEO MA
    public QuocGia getCountryByCode(String maQG) {
        for (QuocGia qg : list) {
            if (qg.getMaQuocGia().equals(maQG)) return qg;
        }
        return null;
    }
}