package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("677d09c6-a419-4853-9353-c42ae5ab3624");
        product.setProductName("Product 1");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> products = productRepository.findAll();
        assertTrue(products.hasNext());

        Product savedProduct = products.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> products = productRepository.findAll();
        assertFalse(products.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("677d09c6-a419-4853-9353-c42ae5ab3624");
        product1.setProductName("Product 1");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("b9bcf849-6c65-4b74-8cf9-97c1c34e83e7");
        product2.setProductName("Product 2");
        product2.setProductQuantity(200);
        productRepository.create(product2);

        Iterator<Product> products = productRepository.findAll();
        assertTrue(products.hasNext());

        Product savedProduct = products.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());

        savedProduct = products.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());

        assertFalse(products.hasNext());
    }

    @Test
    void testUpdate() {
        Product product = new Product();
        product.setProductId("677d09c6-a419-4853-9353-c42ae5ab3624");
        product.setProductName("Product 1");
        product.setProductQuantity(100);
        productRepository.create(product);

        // Update produk yang sudah ada (branch if condition terpenuhi)
        product.setProductName("Product 2");
        product.setProductQuantity(200);
        productRepository.update(product);

        Iterator<Product> products = productRepository.findAll();
        assertTrue(products.hasNext());

        Product savedProduct = products.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals("Product 2", savedProduct.getProductName());
        assertEquals(200, savedProduct.getProductQuantity());
    }

    @Test
    void testUpdateIfEmpty() {
        // Tidak ada produk di repository, sehingga update tidak mengubah apapun
        Product product = new Product();
        product.setProductId("677d09c6-a419-4853-9353-c42ae5ab3624");
        product.setProductName("Product 1");
        product.setProductQuantity(100);
        productRepository.update(product);

        Iterator<Product> products = productRepository.findAll();
        assertFalse(products.hasNext());
    }

    @Test
    void testUpdateNoMatch() {
        // Kasus: ada produk dengan id "A", tetapi kita coba update dengan id "B"
        Product product = new Product();
        product.setProductId("A");
        product.setProductName("Original");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("B");
        updatedProduct.setProductName("Updated");
        updatedProduct.setProductQuantity(200);
        productRepository.update(updatedProduct);

        // Produk dengan id "A" seharusnya tetap tidak berubah
        Product found = productRepository.findById("A");
        assertNotNull(found);
        assertEquals("Original", found.getProductName());
        // Tidak ada produk dengan id "B"
        assertNull(productRepository.findById("B"));
    }

    @Test
    void testDeleteById() {
        Product product = new Product();
        product.setProductId("677d09c6-a419-4853-9353-c42ae5ab3624");
        product.setProductName("Product 1");
        product.setProductQuantity(100);
        productRepository.create(product);

        productRepository.delete(product.getProductId());

        Iterator<Product> products = productRepository.findAll();
        assertFalse(products.hasNext());
    }

    @Test
    void testDeleteIfEmpty() {
        // Hapus dengan id ketika repository kosong
        productRepository.delete("677d09c6-a419-4853-9353-c42ae5ab3624");
        Iterator<Product> products = productRepository.findAll();
        assertFalse(products.hasNext());
    }

    @Test
    void testDeleteIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("677d09c6-a419-4853-9353-c42ae5ab3624");
        product1.setProductName("Product 1");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("b9bcf849-6c65-4b74-8cf9-97c1c34e83e7");
        product2.setProductName("Product 2");
        product2.setProductQuantity(200);
        productRepository.create(product2);

        productRepository.delete(product1.getProductId());

        Iterator<Product> products = productRepository.findAll();
        assertTrue(products.hasNext());
        Product savedProduct = products.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(products.hasNext());
    }

    @Test
    void testDeleteByProduct() {
        // Test langsung method delete(Product)
        Product product = new Product();
        product.setProductId("X");
        product.setProductName("Test Delete");
        product.setProductQuantity(10);
        productRepository.create(product);

        productRepository.delete(product);
        assertNull(productRepository.findById("X"));
    }

    @Test
    void testFindById() {
        // Kasus: produk ditemukan dan tidak ditemukan
        Product product = new Product();
        product.setProductId("Z");
        product.setProductName("Find Me");
        product.setProductQuantity(50);
        productRepository.create(product);

        Product found = productRepository.findById("Z");
        assertNotNull(found);
        assertEquals("Find Me", found.getProductName());

        // Jika produk tidak ada
        Product notFound = productRepository.findById("non-existent");
        assertNull(notFound);
    }
}
