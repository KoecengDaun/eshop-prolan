package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductServiceImpl(productRepository);
    }

    @Test
    void testCreateWithoutProductId() {
        Product product = new Product();
        product.setProductId("");
        product.setProductName("Test Product");
        product.setProductQuantity(10);

        // Stub repository: mengembalikan product yang dikirim
        when(productRepository.create(any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Product created = productService.create(product);
        assertNotNull(created.getProductId());
        assertFalse(created.getProductId().isEmpty());
        assertNotEquals("", created.getProductId());
        assertEquals("Test Product", created.getProductName());
    }

    @Test
    void testCreateWithNullProductId() {
        Product product = new Product();
        product.setProductId(null); // Pastikan null untuk memicu branch
        product.setProductName("Null ID Product");
        product.setProductQuantity(10);

        when(productRepository.create(any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Product created = productService.create(product);
        assertNotNull(created.getProductId());
        assertFalse(created.getProductId().isEmpty());
    }

    @Test
    void testCreateWithProductId() {
        Product product = new Product();
        product.setProductId("123");
        product.setProductName("Test Product");
        product.setProductQuantity(10);

        when(productRepository.create(any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Product created = productService.create(product);
        assertEquals("123", created.getProductId());
    }

    @Test
    void testFindAll() {
        List<Product> list = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("1");
        product1.setProductName("Prod1");
        product1.setProductQuantity(100);
        list.add(product1);

        // Stub repository: mengembalikan iterator dari list
        when(productRepository.findAll()).thenReturn(list.iterator());

        List<Product> result = productService.findAll();
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getProductId());
    }

    @Test
    void testFindById() {
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Prod1");
        product.setProductQuantity(100);

        when(productRepository.findById("1")).thenReturn(product);

        Product found = productService.findById("1");
        assertNotNull(found);
        assertEquals("Prod1", found.getProductName());
    }

    @Test
    void testUpdateWhenProductFound() {
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Old Name");
        product.setProductQuantity(100);

        when(productRepository.findById("1")).thenReturn(product);

        productService.update("1", "New Name", 200);

        // Verifikasi bahwa productRepository.update dipanggil dengan product yang telah diperbarui
        verify(productRepository).update(argThat(updatedProduct ->
                updatedProduct.getProductName().equals("New Name") &&
                        updatedProduct.getProductQuantity() == 200
        ));
    }

    @Test
    void testUpdateWhenProductNotFound() {
        when(productRepository.findById("1")).thenReturn(null);

        productService.update("1", "New Name", 200);
        verify(productRepository, never()).update(any(Product.class));
    }

    @Test
    void testDelete() {
        productService.delete("1");
        verify(productRepository).delete("1");
    }
}
