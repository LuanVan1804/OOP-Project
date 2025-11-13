
package DU_LICH.DiaDiemDuLich;

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

    public String getTenNuoc() { return tenNuoc; }
    public void setTenNuoc(String tenNuoc) { this.tenNuoc = tenNuoc; }

    public String getMaQuocGia() { return maQuocGia; }
    public void setMaQuocGia(String maQuocGia) { this.maQuocGia = maQuocGia; }

    public ThanhPho[] getListTPhos() { return listTPhos; }
    public void setListTPhos(ThanhPho[] listTPhos) { this.listTPhos = listTPhos; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(maQuocGia).append(" | ").append(tenNuoc).append(" | Thành phố: ");

        if (listTPhos == null || listTPhos.length == 0) {
            sb.append("Chưa có");
        } else {
            for (int i = 0; i < listTPhos.length; i++) {
                if (i > 0) sb.append(", ");
                sb.append(listTPhos[i].getMaTPho())   // mã thành phố
                .append("-")
                .append(listTPhos[i].getTenTPho()); // tên thành phố
            }
        }
        return sb.toString();
    }
}
