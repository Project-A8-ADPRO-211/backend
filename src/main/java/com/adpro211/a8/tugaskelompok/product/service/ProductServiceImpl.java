package com.adpro211.a8.tugaskelompok.product.service;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.auths.models.account.Seller;
import com.adpro211.a8.tugaskelompok.auths.service.AccountService;
import com.adpro211.a8.tugaskelompok.product.model.Product;
import com.adpro211.a8.tugaskelompok.product.model.Review;
import com.adpro211.a8.tugaskelompok.product.repository.ProductRepository;
import com.adpro211.a8.tugaskelompok.product.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductRepository productRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    ReviewRepository reviewRepository;

    @Override
    public Product createNewProduct(String name, String description, int price, int stock, String imageUrl, Seller seller) {

        Product product = new Product();

        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);
        product.setOwnerAccount(seller);
        product.setImageUrl(imageUrl);

        try {
            productRepository.save(product);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your Product Name Already Exist");
        }

        return product;
    }

    @Override
    public Review createNewReview(int id, Review review, Account account) {
        review.setReviewer(account);
        Product product = productRepository.findProductById(id);
        review.setProductReview(product);

        Review duplicated = reviewRepository.findByReviewerAndProductReview(account, product);

        if (duplicated != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You Already Review This Product");
        }

        reviewRepository.save(review);

        return review;
    }

    @Override
    public Iterable<Review> getReviewByProductId(int id) {
        Product toBeSearch = productRepository.findProductById(id);
        Iterable<Review> listReview = reviewRepository.findAllByProductReview(toBeSearch);

        return listReview;
    }

    @Override
    public Product updateProduct(int id, Product product) {
        product.setId(id);
        productRepository.save(product);
        return product;
    }

    @Override
    public Product getProductById(int id) {
        return productRepository.findProductById(id);
    }

    @Override
    public Iterable<Product> getProductByAccount(Account account) {
        return productRepository.findAllByOwnerAccountIs(account);
    }

    @Override
    public Iterable<Product> getProductByName(String name) {
        return productRepository.findAllByNameLike("%"+name+"%");
    }

    @Override
    public Iterable<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }
}
