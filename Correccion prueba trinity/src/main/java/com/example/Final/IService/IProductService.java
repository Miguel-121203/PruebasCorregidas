package com.example.Final.IService;

import com.example.Final.Entity.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {

    List<Product> findAll();

    Optional<Product> findById(Long id);

    Product save(Product product);

    Product update(Product product, Long id);

    void delete(Long id);

    void activateProduct(Long id);

    void desactivateProduct(Long id);

    void cancelateProduct(Long id);
}
