package id.ac.ui.cs.advprog.eshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class EshopApplicationTests {

    @Test
    void contextLoads() {
        // Tes ini sengaja dibiarkan kosong.
        // Ini memverifikasi bahwa konteks aplikasi Spring berhasil dimuat.
    }

    @Test
    void mainMethodTest() {
        // Pastikan metode main dapat berjalan tanpa melempar exception
        assertDoesNotThrow(() -> EshopApplication.main(new String[] {}));
    }
}
