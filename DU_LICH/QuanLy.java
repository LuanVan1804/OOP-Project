package DU_LICH;

import java.io.IOException;

public interface QuanLy<T> {
    void loadFromFile(String fileName) throws IOException;

    boolean MaDuyNhat(int ma);

    void saveToFile(String fileName) throws IOException;

    void them(T obj);

    void sua(int maHDV);

    void xoa(int maHDV);
}


