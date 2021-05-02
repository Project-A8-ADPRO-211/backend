package com.adpro211.a8.tugaskelompok.product.repository;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface ProductRepository extends JpaRepository<Product, String> {
    Product findProductById(int id);
    Iterable<Product> findAllByOwnerAccountIs(Account account);
    Iterable<Product> findAllByNameLike(String name);

    @Transactional
    void deleteById(int id);
}
