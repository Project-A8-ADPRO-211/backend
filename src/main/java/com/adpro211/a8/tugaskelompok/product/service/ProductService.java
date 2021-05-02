package com.adpro211.a8.tugaskelompok.product.service;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.product.model.Product;

public interface ProductService {

    Product createNewProduct(String name, String description, int price, int stock, int id, String imageUrl);
    Product updateProduct(int id, Product product);
    Product getProductById(int id);
    Iterable<Product> getProductByAccount(Account account);
    Iterable<Product> getProductByName(String name);
    Iterable<Product> getAllProduct();
    void deleteProduct(int id);
}
