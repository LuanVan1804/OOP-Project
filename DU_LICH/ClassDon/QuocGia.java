
package DU_LICH.ClassDon;

import java.util.Arrays;

public class QuocGia {
    private String tenNuoc;
    private String maQuocGia;
    private ThanhPho[] listTPhos;

    public QuocGia() {
        this.tenNuoc = "";
        this.maQuocGia = "";
    this.listTPhos = new ThanhPho[0];
    }

    public QuocGia(String tenNuoc, String maQuocGia) {
        this.tenNuoc = tenNuoc;
        this.maQuocGia = maQuocGia;
    this.listTPhos = new ThanhPho[0];
    }

    // Full-arg constructor (including city list)
    public QuocGia(String tenNuoc, String maQuocGia, ThanhPho[] listTPhos) {
        this.tenNuoc = tenNuoc;
        this.maQuocGia = maQuocGia;
        this.listTPhos = listTPhos == null ? new ThanhPho[0] : Arrays.copyOf(listTPhos, listTPhos.length);
    }

    // Copy constructor
    public QuocGia(QuocGia other) {
        if (other == null) {
            this.tenNuoc = "";
            this.maQuocGia = "";
            this.listTPhos = new ThanhPho[0];
        } else {
            this.tenNuoc = other.tenNuoc;
            this.maQuocGia = other.maQuocGia;
            this.listTPhos = other.listTPhos == null ? new ThanhPho[0] : Arrays.copyOf(other.listTPhos, other.listTPhos.length);
        }
    }

    public String getTenNuoc() { return tenNuoc; }
    public void setTenNuoc(String tenNuoc) { this.tenNuoc = tenNuoc; }

    public String getMaQuocGia() { return maQuocGia; }
    public void setMaQuocGia(String maQuocGia) { this.maQuocGia = maQuocGia; }

    public ThanhPho[] getListTPhos() { return listTPhos; }
    public void setListTPhos(ThanhPho[] listTPhos) { this.listTPhos = listTPhos; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(maQuocGia).append(" | ").append(tenNuoc).append(" | Thanh pho: ");

        if (listTPhos == null || listTPhos.length == 0) {
            sb.append("Chua co");
        } else {
            for (int i = 0; i < listTPhos.length; i++) {
                if (i > 0) sb.append(", ");
                sb.append(listTPhos[i].getMaTPho())   // ma thanh pho
                .append("-")
                .append(listTPhos[i].getTenTPho()); // ten thanh pho
            }
        }
        return sb.toString();
    }

    public void hienThi() {
        System.out.println("Ma quoc gia: " + maQuocGia);
        System.out.println("Ten nuoc: " + tenNuoc);
        System.out.print("Danh sach thanh pho: ");
        if (listTPhos == null || listTPhos.length == 0) {
            System.out.println("Chua co thanh pho nao.");
        } else {
            for (int i = 0; i < listTPhos.length; i++) {
                if (i > 0) System.out.print(", ");
                System.out.print(listTPhos[i].getMaTPho() + "-" + listTPhos[i].getTenTPho());
            }
            System.out.println();
        }
    }
}
