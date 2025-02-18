package id.ac.ui.cs.advprog.eshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EshopApplicationTests {

    @Test
    void contextLoads() {
        // Tes ini sengaja dibiarkan kosong.
        // Ini memverifikasi bahwa konteks aplikasi Spring berhasil dimuat.
    }

    @Test
    void mainMethodTest() {
        EshopApplication.main(new String[] {});
    }
}
