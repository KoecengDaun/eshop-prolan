package id.ac.ui.cs.advprog.eshop.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;  // Buat mock dependency

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        ProductController productController = new ProductController();
        // Suntikkan dependency ke field 'service' menggunakan ReflectionTestUtils
        ReflectionTestUtils.setField(productController, "service", productService);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void testCreateProductPage() throws Exception {
        mockMvc.perform(get("/product/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("createProduct"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    void testCreateProductPost() throws Exception {
        when(productService.create(any(Product.class))).thenReturn(new Product());
        mockMvc.perform(post("/product/create")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("productId", "P001")
                        .param("productName", "Produk Test")
                        .param("productQuantity", "5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));
    }

    @Test
    void testProductListPage() throws Exception {
        when(productService.findAll()).thenReturn(List.of(new Product(), new Product()));
        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("productList"))
                .andExpect(model().attributeExists("products"));
    }

    @Test
    void testEditProductPage_ProductExists() throws Exception {
        Product product = new Product();
        product.setProductId("P002");
        product.setProductName("Produk Edit");
        product.setProductQuantity(10);
        when(productService.findById("P002")).thenReturn(product);
        mockMvc.perform(get("/product/edit/P002"))
                .andExpect(status().isOk())
                .andExpect(view().name("editProduct"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    void testEditProductPage_ProductNotFound() throws Exception {
        when(productService.findById("P003")).thenReturn(null);
        mockMvc.perform(get("/product/edit/P003"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));
    }

    @Test
    void testEditProductPost() throws Exception {
        doNothing().when(productService).update(anyString(), anyString(), anyInt());
        mockMvc.perform(post("/product/edit")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("productId", "P004")
                        .param("productName", "Produk Updated")
                        .param("productQuantity", "15"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));
    }

    @Test
    void testDeleteProduct() throws Exception {
        doNothing().when(productService).delete("P005");
        mockMvc.perform(get("/product/delete/P005"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));
    }
}
