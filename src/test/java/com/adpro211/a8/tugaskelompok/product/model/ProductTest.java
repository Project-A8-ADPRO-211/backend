package com.adpro211.a8.tugaskelompok.product.model;

import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import com.adpro211.a8.tugaskelompok.auths.models.account.Seller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    private Product product;
    private Seller seller;
    private Buyer buyer;
    private Review review;

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

        review = new Review();
        review.setStar(5);
        review.setId(4);
        review.setContent("ini review");
        review.setReviewer(buyer);
        review.setProductReview(product);

        product = new Product();
        product.setId(2);
        product.setName("bakso");
        product.setPrice(1000);
        product.setStock(50);
        product.setDescription("makanan enak");
        product.setImageUrl("https://google.com");
        product.setOwnerAccount(seller);

        List<Review> reviewList = new ArrayList<Review>();
        reviewList.add(review);
        product.setReviewList(reviewList);
    }

    @Test
    void testGetId() {
        assertEquals(2, product.getId());
    }

    @Test
    void testGetName() {
        assertEquals("bakso", product.getName());
    }

    @Test
    void testGetPrice() {
        assertEquals(1000, product.getPrice());
    }

    @Test
    void testGetStock() {
        assertEquals(50, product.getStock());
    }

    @Test
    void testGetDescription() {
        assertEquals("makanan enak", product.getDescription());
    }

    @Test
    void testGetImageUrl() {
        assertEquals("https://google.com", product.getImageUrl());
    }

    @Test
    void testGetOwner() {
        assertEquals(this.seller, product.getOwnerAccount());
    }

    @Test
    void testGetArray() {
        assertEquals(review, product.getReviewList().get(0));
    }

    @Test
    void testEquals() {
        Product yaProduct = new Product();
        assertTrue(yaProduct.equals(yaProduct));
    }

    @Test
    void testEqualsNotNull() {
        Product yaProduct = new Product();
        assertFalse(yaProduct.equals(null));
    }

    @Test
    void testEqualsNotSame() {
        Product yaProduct = new Product();
        assertFalse(yaProduct.equals(product));
    }
}
