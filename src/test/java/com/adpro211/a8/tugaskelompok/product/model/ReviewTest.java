package com.adpro211.a8.tugaskelompok.product.model;

import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import com.adpro211.a8.tugaskelompok.auths.models.account.Seller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReviewTest {

    private Review review;
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

        review = new Review();
        review.setStar(5);
        review.setId(4);
        review.setContent("ini review");
        review.setReviewer(buyer);
        review.setProductReview(product);
    }

    @Test
    void testGetId() {
        assertEquals(4, review.getId());
    }

    @Test
    void testGetStar() {
        assertEquals(5, review.getStar());
    }

    @Test
    void testGetContent() {
        assertEquals("ini review", review.getContent());
    }

    @Test
    void testGetReviewer() {
        assertEquals(this.buyer, review.getReviewer());
    }

    @Test
    void testGetProductReview() {
        assertEquals(this.product, review.getProductReview());
    }

}
