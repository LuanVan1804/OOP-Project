package DU_LICH;


public class Main {
    public static void main(String[] args) {
        System.out.println("=== Chuong trinh Quan ly Tour - Main ===");

        // Sử dụng lớp QuanLy làm entrypoint trung tâm cho các menu
        QuanLy ql = new QuanLy();
        // QuanLy.menu() sẽ tự tạo và đóng Scanner khi thoát
        ql.menu();

        System.out.println("Chuong trinh ket thuc.");
    }
}

