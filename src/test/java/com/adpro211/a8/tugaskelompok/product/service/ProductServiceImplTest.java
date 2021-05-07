package com.adpro211.a8.tugaskelompok.product.service;

import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import com.adpro211.a8.tugaskelompok.auths.models.account.Seller;
import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;

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
    void testCreateNewReviewSuccess() {
        productService.createNewReview(2, new Review(), this.buyer);
        verify(reviewRepository, times(1)).save(any());
    }

    @Test
    void testGetReviewByProductId() {
        Iterable<Review> listReview = new ArrayList<Review>();
        assertEquals(listReview, productService.getReviewByProductId(2));
    }
}
