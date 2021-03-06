package com.adpro211.a8.tugaskelompok.product.service;

import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import com.adpro211.a8.tugaskelompok.auths.models.account.Seller;
import com.adpro211.a8.tugaskelompok.product.model.Product;
import com.adpro211.a8.tugaskelompok.product.model.Review;
import com.adpro211.a8.tugaskelompok.product.repository.ProductRepository;
import com.adpro211.a8.tugaskelompok.product.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @InjectMocks
    ProductServiceImpl productService;

    @Mock
    ProductRepository productRepository;

    @Mock
    ReviewRepository reviewRepository;

    private Product product;
    private Seller seller;
    private Buyer buyer;

    @BeforeEach
    void setUp() {
        seller = new Seller();
        seller.setName("advis");
        seller.setId(1);
        seller.setEmail("advistasyam@gmail.com");
        seller.setAccountType("seller");

        buyer = new Buyer();
        buyer.setName("pembeli");
        buyer.setId(3);
        buyer.setEmail("kangsabun71@gmail.com");
        buyer.setAccountType("buyer");
        buyer.setAlamat("dimana saja");

        product = new Product();
        product.setId(2);
        product.setName("bakso");
        product.setPrice(1000);
        product.setStock(50);
        product.setDescription("makanan enak");
        product.setImageUrl("https://google.com");
        product.setOwnerAccount(seller);
    }

    @Test
    void testGetProductById() {
        lenient().when(productRepository.findProductById(2)).thenReturn(product);
        assertEquals(product, productService.getProductById(2));
    }

    @Test
    void testCreateProductSuccess() {
        productService.createNewProduct("aa", "desc", 20, 15, "bb", this.seller);
        verify(productRepository, times(1)).save(any());
    }

    @Test
    void testCreateProductDuplicateName() {
        when(productRepository.save(any())).thenThrow(DataIntegrityViolationException.class);
        assertThrows(ResponseStatusException.class, () -> {
            productService.createNewProduct("aa", "desc", 20, 15, "bb", this.seller);
        });
    }

    @Test
    void testCreateNewReviewSuccess() {
        Review toBeDuplicated = new Review();
        productService.createNewReview(2, toBeDuplicated, this.buyer);
        verify(reviewRepository, times(1)).save(any());
    }

    @Test
    void testCreateNewReviewDuplicate() {
        Review toBeDuplicated = new Review();
        when(reviewRepository.findByReviewerAndProductReview(any(), any())).thenReturn(toBeDuplicated);
        assertThrows(ResponseStatusException.class, () -> {
            productService.createNewReview(2, toBeDuplicated, this.buyer);
        });
    }

    @Test
    void testGetReviewByProductId() {
        Iterable<Review> listReview = new ArrayList<Review>();
        assertEquals(listReview, productService.getReviewByProductId(2));
    }

    @Test
    void testUpdateProductSuccess() {
        product.setPrice(20000);
        productService.updateProduct(2, product);
        verify(productRepository, times(1)).save(any());
    }

    @Test
    void testGetProductByAccountSuccess() {
        List<Product> listProducts = new ArrayList<Product>();
        listProducts.add(product);
        lenient().when(productRepository.findAllByOwnerAccountIs(seller)).thenReturn(listProducts);
        assertEquals(listProducts, productService.getProductByAccount(seller));
    }

    @Test
    void testSearchProductByNameSuccess() {
        Iterable<Product> listProduct = new ArrayList<Product>();
        assertEquals(listProduct, productService.getProductByName("sate"));
    }

    @Test
    void testGetAllProductsSuccess() {
        List<Product> listProducts = new ArrayList<Product>();
        listProducts.add(product);
        lenient().when(productRepository.findAll()).thenReturn(listProducts);
        assertEquals(listProducts, productService.getAllProduct());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testDeleteProductsSuccess() {
        productService.deleteProduct(2);
        verify(productRepository, times(1)).deleteById(2);
    }
}
