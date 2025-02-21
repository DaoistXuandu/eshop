package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        sampleProduct = new Product();
        sampleProduct.setProductId("1");
        sampleProduct.setProductName("Test Product");
    }

    @Test
    void testCreateWithNullProduct() {
        Product nullProduct = null;

        Product createdProduct = productService.create(nullProduct);

        assertNull(createdProduct);
    }


    @Test
    void testFindAll() {
        List<Product> productList = new ArrayList<>();
        productList.add(sampleProduct);

        Iterator<Product> mockIterator = productList.iterator();
        when(productRepository.findAll()).thenReturn(mockIterator);

        List<Product> retrievedProducts = productService.findAll();

        assertNotNull(retrievedProducts);
        assertEquals(1, retrievedProducts.size());
        assertEquals(sampleProduct, retrievedProducts.get(0));
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(productRepository.findById("1")).thenReturn(sampleProduct);

        Product foundProduct = productService.findById("1");

        assertNotNull(foundProduct);
        assertEquals(sampleProduct, foundProduct);
        verify(productRepository, times(1)).findById("1");
    }

    @Test
    void testEdit() {
        when(productRepository.edit(sampleProduct)).thenReturn(sampleProduct);

        Product editedProduct = productService.edit(sampleProduct);

        assertNotNull(editedProduct);
        assertEquals(sampleProduct, editedProduct);
        verify(productRepository, times(1)).edit(sampleProduct);
    }

    @Test
    void testDelete() {
        when(productRepository.delete("1")).thenReturn(sampleProduct);

        Product deletedProduct = productService.delete("1");

        assertNotNull(deletedProduct);
        assertEquals(sampleProduct, deletedProduct);
        verify(productRepository, times(1)).delete("1");
    }
}
