
package DU_LICH.DiaDiemDuLich;

public class QuocGia {
    private String tenNuoc;
    private int maQuocGia;
    private ThanhPho[] listTPhos;

    public QuocGia() {
        this.tenNuoc = "";
        this.maQuocGia = 0;
    this.listTPhos = new ThanhPho[0];
    }

    public QuocGia(String tenNuoc, int maQuocGia) {
        this.tenNuoc = tenNuoc;
        this.maQuocGia = maQuocGia;
    this.listTPhos = new ThanhPho[0];
    }

    public String getTenNuoc() { return tenNuoc; }
    public void setTenNuoc(String tenNuoc) { this.tenNuoc = tenNuoc; }

    public int getMaQuocGia() { return maQuocGia; }
    public void setMaQuocGia(int maQuocGia) { this.maQuocGia = maQuocGia; }

    public ThanhPho[] getListTPhos() { return listTPhos; }
    public void setListTPhos(ThanhPho[] listTPhos) { this.listTPhos = listTPhos; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("QuocGia{").append("ten=").append(tenNuoc).append(", ma=").append(maQuocGia).append(", cities=[");
        for (int i = 0; i < listTPhos.length; i++) {
            if (i > 0) sb.append(", ");
            sb.append(listTPhos[i].getTenTPho());
        }
        sb.append("]}");
        return sb.toString();
    }
}
