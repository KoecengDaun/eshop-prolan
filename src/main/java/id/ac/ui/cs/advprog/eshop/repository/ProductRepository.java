package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ProductRepository {

    private final List<Product> products = new ArrayList<>();

    public Product create(Product product) {
        products.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return products.iterator();
    }

    public void update(Product updatedProduct) {
        for (int i = 0; i < products.size(); i++) {
            Product existingProduct = products.get(i);
            if (existingProduct.getProductId().equals(updatedProduct.getProductId())) {
                products.set(i, updatedProduct);
                return;
            }
        }
    }

    public void delete(Product product) {
        products.remove(product);
    }

    public void delete(String id) {
        Product product = findById(id);
        if (product != null) {
            delete(product);
        }
    }

    public Product findById(String id) {
        for (Product product : products) {
            if (product.getProductId().equals(id)) {
                return product;
            }
        }
        return null;
    }
}
