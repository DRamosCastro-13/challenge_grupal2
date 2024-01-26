package com.mindhub.wireit.service;

import com.mindhub.wireit.dto.ProductDTO;
import com.mindhub.wireit.dto.bodyjson.NewProduct;
import com.mindhub.wireit.models.Product;
import com.mindhub.wireit.models.enums.ProductCategory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    List<ProductDTO> getAllProductsDTO();

    List<ProductDTO> getAllProductsFiltered(ProductCategory productCategory);

    ResponseEntity<String> createProduct(NewProduct newProduct, Authentication authentication);

    Product getProductById(Long id);
}
