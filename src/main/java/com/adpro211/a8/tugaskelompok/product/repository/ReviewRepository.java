package com.adpro211.a8.tugaskelompok.product.repository;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.product.model.Product;
import com.adpro211.a8.tugaskelompok.product.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, String> {
    Iterable<Review> findAllByProductReview(Product product);
    Review findByReviewerAndProductReview(Account account, Product product);
}
