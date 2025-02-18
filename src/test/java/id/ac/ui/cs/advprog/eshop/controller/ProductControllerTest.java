package id.ac.ui.cs.advprog.eshop.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import id.ac.ui.cs.advprog.eshop.controller.ProductController;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    public void testCreateProductPage() throws Exception {
        mockMvc.perform(get("/product/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("createProduct"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    public void testCreateProductPost() throws Exception {
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
    public void testProductListPage() throws Exception {
        when(productService.findAll()).thenReturn(List.of(new Product(), new Product()));

        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("productList"))
                .andExpect(model().attributeExists("products"));
    }

    @Test
    public void testEditProductPage_ProductExists() throws Exception {
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
    public void testEditProductPage_ProductNotFound() throws Exception {
        when(productService.findById("P003")).thenReturn(null);

        mockMvc.perform(get("/product/edit/P003"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));
    }

    @Test
    public void testEditProductPost() throws Exception {
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
    public void testDeleteProduct() throws Exception {
        doNothing().when(productService).delete("P005");

        mockMvc.perform(get("/product/delete/P005"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));
    }
}
