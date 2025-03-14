package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import java.util.Iterator;

public interface ProductRepositoryInterface {
    Product create(Product product);
    Product findById(String productId);
    Product edit(Product product);
    Iterator<Product> findAll();
    Product delete(String productId);
}
