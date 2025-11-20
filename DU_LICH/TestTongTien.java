package DU_LICH;

public class TestTongTien {
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║          TEST TONG TIEN HOA DON - PHIEN BAN MOI           ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
        
        QuanLy ql = new QuanLy();
        
        System.out.println("\n[1] HIEN THI DANH SACH CHI PHI KE HOACH TOUR:");
        System.out.println("═══════════════════════════════════════════════════════════");
        ql.dsChiPhi.xuatDanhSach();
        
        System.out.println("\n[2] HIEN THI DANH SACH HOA DON VA TONG TIEN:");
        System.out.println("═══════════════════════════════════════════════════════════");
        ql.dsHoaDon.hienThiDanhSachHoaDon();
        
        System.out.println("\n[3] THONG KE HOA DON:");
        System.out.println("═══════════════════════════════════════════════════════════");
        ql.dsHoaDon.thongKeHoaDon();
        
        System.out.println("\n[4] TEST TRUY CAP TRUC TIEP MOT HOA DON:");
        System.out.println("═══════════════════════════════════════════════════════════");
        HoaDon hd = ql.dsHoaDon.timHoaDonTheoMa("HD01");
        if (hd != null) {
            System.out.println("Test goi method tongTienHoaDon() truc tiep:");
            System.out.println("  => Tong tien: " + String.format("%,.0f VND", hd.tongTienHoaDon()));
            System.out.println("\nTest goi method xuatThongTin():");
            hd.xuatThongTin();
        }
        
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║  KET THUC TEST - CODE MOI GON GANG, DE HIEU, DE HOC THUOC ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
    }
}
