package DU_LICH;

import java.util.Scanner;
import DU_LICH.QuanLy.QuanLy;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Chuong trinh Quan ly Tour - Main ===");

        // Tạo Scanner và truyền vào QuanLy.menu(Scanner)
        Scanner sc = new Scanner(System.in);
        try {
            QuanLy ql = new QuanLy();
            ql.menu(sc);
        } finally {
            sc.close();
        }

        System.out.println("Chuong trinh ket thuc.");
    }
}

