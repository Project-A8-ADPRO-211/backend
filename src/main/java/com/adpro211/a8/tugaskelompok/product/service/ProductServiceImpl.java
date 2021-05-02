package com.adpro211.a8.tugaskelompok.product.service;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.auths.service.AccountService;
import com.adpro211.a8.tugaskelompok.product.model.Product;
import com.adpro211.a8.tugaskelompok.product.repository.ProductRepository;
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

    @Override
    public Product createNewProduct(String name, String description, int price, int stock, int id, String imageUrl) {

        Product product = new Product();
        Account account = accountService.getAccountById(id);

        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);
        product.setOwnerAccount(account);
        product.setImageUrl(imageUrl);

        try {
            productRepository.save(product);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your Product Name Already Exist");
        }

        return product;
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
